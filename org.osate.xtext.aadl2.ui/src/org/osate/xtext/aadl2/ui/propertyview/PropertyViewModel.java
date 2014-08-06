package org.osate.xtext.aadl2.ui.propertyview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.serializer.ISerializer;
import org.osate.aadl2.ComponentClassifier;
import org.osate.aadl2.ListValue;
import org.osate.aadl2.ModalPropertyValue;
import org.osate.aadl2.Mode;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.Property;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.PropertyExpression;
import org.osate.aadl2.PropertySet;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.aadl2.instance.InstanceReferenceValue;
import org.osate.aadl2.modelsupport.resources.OsateResourceUtil;
import org.osate.aadl2.properties.PropertyAcc;
import org.osate.xtext.aadl2.properties.util.EMFIndexRetrieval;
import org.osate.xtext.aadl2.ui.MyAadl2Activator;

/**
 * Model for the AADL Property View.
 *
 * @author aarong
 */
public class PropertyViewModel extends LabelProvider implements IColorProvider, ITreeContentProvider,
		ITableLabelProvider {
	private static final String MODE_ICON = "icons/propertyview/mode.gif";
	private static final String SCALAR_ICON = "icons/propertyview/scalar.gif";
	private static final String LIST_ICON = "icons/propertyview/list.gif";
	private static final String PROPERTY_SET_ICON = "icons/propertyview/property_set.gif";

	// Constants

	private static final String UNDEFINED = "undefined";
	private static final String EQUALS = " => ";
	private static final String EMPTY = "";

	// Locals

	/** Flag to indicate whether we should show undefined properties */
	private boolean showUndefined = false;

	/** Cached Icon for property set nodes */
	private Image propSetImage = null;
	/** Cached Icon for list property value nodes */
	private Image listImage = null;
	/** Cached Icon for scalar property value nodes */
	private Image scalarImage = null;
	/** Cached Icon for modes */
	private Image modeImage = null;

	/**
	 * Selected NamedElement in the editor or outline.  This field is set
	 * in rebuildModel() and used in getColumnText().
	 */
	private NamedElement currentElement = null;

	/** Model is a List of PropSet items */
	final List<PropSet> input = new ArrayList<PropSet>();

	/** Immutable wrapped root list for external use */
	final List<PropSet> inputLeaked = Collections.unmodifiableList(input);

	private final ISerializer serializer;

	// Inner Classes

	private class PropSet {
		final PropertySet ps;
		final List<AbstractModelProperty> properties;

		public PropSet(final PropertySet ps) {
			this.ps = ps;
			properties = new ArrayList<AbstractModelProperty>();
		}

		public void addProperty(final AbstractModelProperty prop) {
			properties.add(prop);
		}

		public void removeProperty(final AbstractModelProperty prop) {
			properties.remove(prop);
		}

		public Object[] getProperties() {
			return properties.toArray();
		}
	}

	private abstract class InMode {
		final String modeName;
		final AbstractModelProperty parent;

		public InMode(final ModedProperty mp, final List<Mode> modes) {
			final StringBuilder sb = new StringBuilder();
			for (Iterator<Mode> iter = modes.iterator(); iter.hasNext();) {
				sb.append(iter.next().getName());
				if (iter.hasNext()) {
					sb.append(", ");
				}
			}
			modeName = sb.toString();
			parent = mp;
			mp.addMode(this);
		}

		public abstract String getValue();

		public abstract Color getColor();
	}

	private class UndefinedMode extends InMode {
		public UndefinedMode(final ModedProperty mp, final List<Mode> modes) {
			super(mp, modes);
		}

		@Override
		public String getValue() {
			return UNDEFINED;
		}

		@Override
		public Color getColor() {
			return Display.getDefault().getSystemColor(SWT.COLOR_RED);
		}
	}

	private class ValuedMode extends InMode {
		final String value;

		public ValuedMode(ModedProperty prop, PropertyExpression expression, List<Mode> elementModes) {
			super(prop, elementModes);
			value = getValueAsString(expression);
		}

		public ValuedMode(ModedProperty prop, ModalPropertyValue mpv) {
			this(prop, mpv.getOwnedValue(), mpv.getAllInModes());
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public Color getColor() {
			return null;
		}
	}

	private class InheritedMode extends InMode {
		final String value;

		public InheritedMode(ModedProperty prop, PropertyExpression expression, List<Mode> elementModes) {
			super(prop, elementModes);
			value = getValueAsString(expression);
		}

		public InheritedMode(ModedProperty prop, ModalPropertyValue mpv) {
			this(prop, mpv.getOwnedValue(), mpv.getAllInModes());
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public Color getColor() {
			return null;
		}
	}

	private class DefaultMode extends InMode {
		final String value;

		public DefaultMode(final ModedProperty prop, final List<Mode> modes) {
			super(prop, modes);
			value = getValueAsString(prop.definition.getDefaultValue());
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public Color getColor() {
			return null;
		}
	}

	private abstract class AbstractModelProperty {
		final String propertyName;
		final boolean isList;
		final PropSet parent;
		final Property definition;

		public AbstractModelProperty(final PropSet ps, final Property pn) {
			propertyName = pn.getName();
			isList = pn.isList();
			parent = ps;
			definition = pn;
			ps.addProperty(this);
		}

		public abstract String getValue();

		public abstract Color getColor();
	}

	private class ModedProperty extends AbstractModelProperty {
		final List<InMode> modes;

		public ModedProperty(final PropSet ps, final Property pn) {
			super(ps, pn);
			modes = new ArrayList<InMode>();
		}

		public void addMode(final InMode mode) {
			modes.add(mode);
		}

		@Override
		public String getValue() {
			return EMPTY;
		}

		@Override
		public Color getColor() {
			return null;
		}

		public Object[] getModes() {
			return modes.toArray();
		}
	}

	private class UndefinedProperty extends AbstractModelProperty {
		public UndefinedProperty(final PropSet ps, final Property pn) {
			super(ps, pn);
		}

		@Override
		public String getValue() {
			return UNDEFINED;
		}

		@Override
		public Color getColor() {
			return Display.getDefault().getSystemColor(SWT.COLOR_RED);
		}
	}

	private class ValuedProperty extends AbstractModelProperty {
		final String value;
		final PropertyAssociation pa;

		public ValuedProperty(final PropSet ps, final Property pn, final PropertyAssociation pa) {
			super(ps, pn);
			value = getValueAsString(pa.getOwnedValues().get(0).getOwnedValue());
			this.pa = pa;
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public Color getColor() {
			return null;
		}
	}

	private class InheritedProperty extends AbstractModelProperty {
		final String value;
		final PropertyAssociation pa;

		public InheritedProperty(final PropSet ps, final Property pn, final PropertyAssociation pa) {
			super(ps, pn);
			value = getValueAsString(pa.getOwnedValues().get(0).getOwnedValue());
			this.pa = pa;
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public Color getColor() {
			return null;
		}
	}

	private class DefaultProperty extends AbstractModelProperty {
		final String value;

		public DefaultProperty(final PropSet ps, final Property pn) {
			super(ps, pn);
			value = getValueAsString(pn.getDefaultValue());
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public Color getColor() {
			return null;
		}
	}

	public PropertyViewModel(final ISerializer serializer) {
		this.serializer = serializer;
	}

	private String getValueAsString(PropertyExpression expression) {
		if (expression instanceof InstanceReferenceValue) {
			InstanceObject referencedObject = ((InstanceReferenceValue) expression).getReferencedInstanceObject();
			if (referencedObject != null) {
				return referencedObject.getInstanceObjectPath();
			} else {
				return "null";
			}
		} else if (expression instanceof ListValue && hasInstanceReferenceValue((ListValue) expression)) {
			return serializeListWithInstanceReferenceValue((ListValue) expression);
		} else {
			String value = serializer.serialize(expression).replaceAll("\n", "").replaceAll("\t", "");
			// TODO: Test this to see what cleanup is truly necessary.
			return value;
		}
	}

	private boolean hasInstanceReferenceValue(ListValue topList) {
		for (PropertyExpression subExpression : topList.getOwnedListElements()) {
			if (subExpression instanceof InstanceReferenceValue) {
				return true;
			} else if (subExpression instanceof ListValue && hasInstanceReferenceValue((ListValue) subExpression)) {
				return true;
			}
		}
		return false;
	}

	private String serializeListWithInstanceReferenceValue(ListValue topList) {
		StringBuilder result = new StringBuilder("(");
		for (Iterator<PropertyExpression> iter = topList.getOwnedListElements().iterator(); iter.hasNext();) {
			PropertyExpression subExpression = iter.next();
			if (subExpression instanceof InstanceReferenceValue) {
				InstanceObject referencedObject = ((InstanceReferenceValue) subExpression)
						.getReferencedInstanceObject();
				if (referencedObject != null) {
					result.append(referencedObject.getInstanceObjectPath());
				} else {
					result.append("null");
				}
			} else if (subExpression instanceof ListValue) {
				result.append(serializeListWithInstanceReferenceValue((ListValue) subExpression));
			} else {
				result.append(serializer.serialize(subExpression).replaceAll("\n", "").replaceAll("\t", ""));
			}
			if (iter.hasNext()) {
				result.append(", ");
			}
		}
		result.append(")");
		return result.toString();
	}

	// Label Provider Methods

	@Override
	public Image getImage(Object element) {
		if (element instanceof PropSet) {
			if (propSetImage == null) {
				ImageDescriptor descriptor = MyAadl2Activator.getImageDescriptor(PROPERTY_SET_ICON);
				propSetImage = descriptor.createImage();
			}
			return propSetImage;
		} else if (element instanceof AbstractModelProperty) {
			if (((AbstractModelProperty) element).isList) {
				if (listImage == null) {
					ImageDescriptor descriptor = MyAadl2Activator.getImageDescriptor(LIST_ICON);
					listImage = descriptor.createImage();
				}
				return listImage;
			} else {
				if (scalarImage == null) {
					ImageDescriptor descriptor = MyAadl2Activator.getImageDescriptor(SCALAR_ICON);
					scalarImage = descriptor.createImage();
				}
				return scalarImage;
			}
		} else if (element instanceof InMode) {
			if (modeImage == null) {
				ImageDescriptor descriptor = MyAadl2Activator.getImageDescriptor(MODE_ICON);
				modeImage = descriptor.createImage();
			}
			return modeImage;
		} else {
			return null;
		}
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			return getImage(element);
		} else {
			return null;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof PropSet) {
			return ((PropSet) element).ps.getName();
		} else if (element instanceof AbstractModelProperty) {
			final StringBuilder sb = new StringBuilder();
			sb.append(((AbstractModelProperty) element).propertyName);
			sb.append(EQUALS);
			sb.append(((AbstractModelProperty) element).getValue());
			return sb.toString();
		} else if (element instanceof InMode) {
			final StringBuilder sb = new StringBuilder();
			sb.append(((InMode) element).getValue());
			sb.append(" in modes (");
			sb.append(((InMode) element).modeName);
			sb.append(")");
			return sb.toString();
		} else {
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex == 0) {
			return getText(element);
		} else if (columnIndex == 1) {
			if (element instanceof ValuedProperty || element instanceof ValuedMode) {
				return "Property exists locally.";
			} else if (element instanceof InheritedProperty || element instanceof InheritedMode) {
				return "Property is inherited";
			} else if (element instanceof DefaultProperty || element instanceof DefaultMode) {
				return "Property taking default value.";
			} else if (element instanceof UndefinedProperty || element instanceof UndefinedMode) {
				return "No property value is defined.";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// BAD: same dispose for the label provider and content provider...
	@Override
	public void dispose() {
		if (propSetImage != null) {
			propSetImage.dispose();
			propSetImage = null;
		}
		if (listImage != null) {
			listImage.dispose();
			listImage = null;
		}
		if (scalarImage != null) {
			scalarImage.dispose();
			scalarImage = null;
		}
		if (modeImage != null) {
			modeImage.dispose();
			modeImage = null;
		}
	}

	// Color Provider Methods

	@Override
	public Color getForeground(Object element) {
		if (element instanceof AbstractModelProperty) {
			return ((AbstractModelProperty) element).getColor();
		} else if (element instanceof InMode) {
			return ((InMode) element).getColor();
		} else {
			return null;
		}
	}

	/**
	 * Return <code>null</code>; always use the default color.
	 */
	@Override
	public Color getBackground(Object element) {
		return null;
	}

	// Content Provider methods

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof PropSet) {
			return ((PropSet) parentElement).getProperties();
		} else if (parentElement instanceof ModedProperty) {
			return ((ModedProperty) parentElement).getModes();
		} else {
			return null;
		}
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof AbstractModelProperty) {
			return ((AbstractModelProperty) element).parent;
		} else if (element instanceof InMode) {
			return ((InMode) element).parent;
		} else {
			return null;
		}
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element instanceof PropSet || element instanceof ModedProperty);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object[] getElements(Object inputElement) {
		return ((List) inputElement).toArray();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	// model management

	public List<PropSet> getInput() {
		return inputLeaked;
	}

	public void setShowUndefined(final boolean flag) {
		showUndefined = flag;
	}

	public void toggleShowUndefined() {
		showUndefined = !showUndefined;
	}

	public boolean getShowUndefined() {
		return showUndefined;
	}

	/**
	 * Rebuild list of property values.
	 * @param element
	 *            The property holder whose property values are to be displayed
	 * @return Whether the property values were successfully retrieved or not.
	 *         If this method returns <code>false</code>, then the displayed
	 *         tree is empty.
	 */
	public void rebuildModel(final NamedElement element) {
		currentElement = element;
//		try {
		input.clear();

		/*
		 * Walk through all the property sets, and query the component for
		 * each property.
		 */
		// get a sorted set of all the property sets
		final EList<IEObjectDescription> propSets = EMFIndexRetrieval.getAllPropertySetsInWorkspace(element);
		final SortedSet<PropertySet> alphabetizedPropSets = new TreeSet<PropertySet>(
				PropertySetNameComparator.prototype);
		for (IEObjectDescription description : propSets) {
			alphabetizedPropSets.add((PropertySet) OsateResourceUtil.getResourceSet().getEObject(
					description.getEObjectURI(), true));
		}

		final Iterator<PropertySet> i = alphabetizedPropSets.iterator();
		while (i.hasNext()) {
			final PropertySet ps = i.next();
			if (ps.getName() == null) {
				continue;
			}
			final PropSet propSet = new PropSet(ps);
			final EList<Property> pnList = ps.getOwnedProperties();
			if (pnList != null) {
				final Iterator<Property> j = pnList.iterator();
				while (j.hasNext()) {
					final Property pn = j.next();
					if (pn != null && element.acceptsProperty(pn)) {
//							try {
						// Don't worry about PropertyDoesNotApplyToHolderException,
						// we already check if property is acceptable
						final PropertyAcc propertyAccumulator = element.getPropertyValue(pn);
						PropertyAssociation firstAssociation = propertyAccumulator.first();
						if (firstAssociation != null) {
							if (firstAssociation.isModal() && element instanceof ComponentClassifier) {
								final ModedProperty prop = new ModedProperty(propSet, pn);
								EList<Mode> elementModes = ((ComponentClassifier) element).getAllModes();
								for (ModalPropertyValue mpv : firstAssociation.getOwnedValues()) {
									if (mpv.getAllInModes().size() == 0) {
										if (firstAssociation.getOwner() == element) {
											new ValuedMode(prop, mpv.getOwnedValue(), elementModes);
										} else {
											new InheritedMode(prop, mpv.getOwnedValue(), elementModes);
										}
										elementModes.clear();
									} else {
										if (firstAssociation.getOwner() == element) {
											new ValuedMode(prop, mpv);
										} else {
											new InheritedMode(prop, mpv);
										}
										elementModes.removeAll(mpv.getAllInModes());
									}
								}
								/*
								 * If prop has no children (i.e., undefined
								 * in all modes, remove from the property
								 * set (don't show it)
								 */
								if (prop.getModes().length == 0) {
									propSet.removeProperty(prop);
								} else if (!elementModes.isEmpty()) {
									if (pn.getDefaultValue() != null) {
										new DefaultMode(prop, elementModes);
									} else if (showUndefined) {
										new UndefinedMode(prop, elementModes);
									}
								}
							} else if (firstAssociation.getOwner() == element) {
								new ValuedProperty(propSet, pn, firstAssociation);
							} else {
								new InheritedProperty(propSet, pn, firstAssociation);
							}
						} else {
							if (pn.getDefaultValue() != null) {
								new DefaultProperty(propSet, pn);
							} else if (showUndefined) {
								new UndefinedProperty(propSet, pn);
							}
						}
//							}
					}
				}
			}
			// Don't add property sets that have no children
			if (propSet.properties.size() > 0) {
				input.add(propSet);
			}
		}
//		}
	}

	/**
	 * Comparator that compares the names of two property sets and sorts
	 * them alphabetically, ignoring case.
	 */
	private static final class PropertySetNameComparator implements Comparator<PropertySet> {
		public static final PropertySetNameComparator prototype = new PropertySetNameComparator();

		/**
		 * Private constructor to enforce singleton pattern.
		 */
		private PropertySetNameComparator() {
			// empty
		}

		@Override
		public int compare(PropertySet ps1, PropertySet ps2) {
			/*
			 * PropertySet object may be incomplete: null object or
			 * object with null name is greater than non-null object
			 * or object with non-null name.
			 */
			final boolean firstNull = ps1 == null || ps1.getName() == null;
			final boolean secondNull = ps2 == null || ps2.getName() == null;
			if (firstNull) {
				return secondNull ? 0 : 1;
			} else {
				return secondNull ? -1 : ps1.getName().compareToIgnoreCase(ps2.getName());
			}
		}
	}
}