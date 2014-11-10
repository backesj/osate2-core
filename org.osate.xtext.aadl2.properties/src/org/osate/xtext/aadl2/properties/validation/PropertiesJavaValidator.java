/*
 *
 * <copyright>
 * Copyright  2012 by Carnegie Mellon University, all rights reserved.
 *
 * Use of the Open Source AADL Tool Environment (OSATE) is subject to the terms of the license set forth
 * at http://www.eclipse.org/org/documents/epl-v10.html.
 *
 * NO WARRANTY
 *
 * ANY INFORMATION, MATERIALS, SERVICES, INTELLECTUAL PROPERTY OR OTHER PROPERTY OR RIGHTS GRANTED OR PROVIDED BY
 * CARNEGIE MELLON UNIVERSITY PURSUANT TO THIS LICENSE (HEREINAFTER THE "DELIVERABLES") ARE ON AN "AS-IS" BASIS.
 * CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED AS TO ANY MATTER INCLUDING,
 * BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR A PARTICULAR PURPOSE, MERCHANTABILITY, INFORMATIONAL CONTENT,
 * NONINFRINGEMENT, OR ERROR-FREE OPERATION. CARNEGIE MELLON UNIVERSITY SHALL NOT BE LIABLE FOR INDIRECT, SPECIAL OR
 * CONSEQUENTIAL DAMAGES, SUCH AS LOSS OF PROFITS OR INABILITY TO USE SAID INTELLECTUAL PROPERTY, UNDER THIS LICENSE,
 * REGARDLESS OF WHETHER SUCH PARTY WAS AWARE OF THE POSSIBILITY OF SUCH DAMAGES. LICENSEE AGREES THAT IT WILL NOT
 * MAKE ANY WARRANTY ON BEHALF OF CARNEGIE MELLON UNIVERSITY, EXPRESS OR IMPLIED, TO ANY PERSON CONCERNING THE
 * APPLICATION OF OR THE RESULTS TO BE OBTAINED WITH THE DELIVERABLES UNDER THIS LICENSE.
 *
 * Licensee hereby agrees to defend, indemnify, and hold harmless Carnegie Mellon University, its trustees, officers,
 * employees, and agents from all claims or demands made against them (and any related losses, expenses, or
 * attorney's fees) arising out of, or relating to Licensee's and/or its sub licensees' negligent use or willful
 * misuse of or negligent conduct or willful misconduct regarding the Software, facilities, or other rights or
 * assistance granted by Carnegie Mellon University under this License, including, but not limited to, any claims of
 * product liability, personal injury, death, damage to property, or violation of any laws or regulations.
 *
 * Carnegie Mellon Carnegie Mellon University Software Engineering Institute authored documents are sponsored by the U.S. Department
 * of Defense under Contract F19628-00-C-0003. Carnegie Mellon University retains copyrights in all material produced
 * under this contract. The U.S. Government retains a non-exclusive, royalty-free license to publish or reproduce these
 * documents, or allow others to do so, for U.S. Government purposes only pursuant to the copyright license
 * under the contract clause at 252.227.7013.
 * </copyright>
 */
package org.osate.xtext.aadl2.properties.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.AadlBoolean;
import org.osate.aadl2.AadlInteger;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AadlReal;
import org.osate.aadl2.AadlString;
import org.osate.aadl2.AbstractNamedValue;
import org.osate.aadl2.BasicPropertyAssociation;
import org.osate.aadl2.BooleanLiteral;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ClassifierType;
import org.osate.aadl2.ClassifierValue;
import org.osate.aadl2.ContainedNamedElement;
import org.osate.aadl2.ContainmentPathElement;
import org.osate.aadl2.Element;
import org.osate.aadl2.EnumerationLiteral;
import org.osate.aadl2.EnumerationType;
import org.osate.aadl2.IntegerLiteral;
import org.osate.aadl2.InternalFeature;
import org.osate.aadl2.ListType;
import org.osate.aadl2.ListValue;
import org.osate.aadl2.MetaclassReference;
import org.osate.aadl2.ModalPropertyValue;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.NamedValue;
import org.osate.aadl2.Namespace;
import org.osate.aadl2.NumberType;
import org.osate.aadl2.NumberValue;
import org.osate.aadl2.Operation;
import org.osate.aadl2.PackageSection;
import org.osate.aadl2.ProcessorFeature;
import org.osate.aadl2.Property;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.PropertyConstant;
import org.osate.aadl2.PropertyExpression;
import org.osate.aadl2.PropertySet;
import org.osate.aadl2.PropertyType;
import org.osate.aadl2.RangeType;
import org.osate.aadl2.RangeValue;
import org.osate.aadl2.RealLiteral;
import org.osate.aadl2.RecordType;
import org.osate.aadl2.RecordValue;
import org.osate.aadl2.ReferenceType;
import org.osate.aadl2.ReferenceValue;
import org.osate.aadl2.StringLiteral;
import org.osate.aadl2.UnitLiteral;
import org.osate.aadl2.UnitsType;
import org.osate.aadl2.modelsupport.util.AadlUtil;
import org.osate.aadl2.util.Aadl2Util;

public class PropertiesJavaValidator extends AbstractPropertiesJavaValidator {

	public static final String INVALID_ASSIGNMENT = "edu.cmu.sei.invalid.assignment";
	public static final String MISSING_WITH = "org.osate.xtext.aadl2.properties.missing_with";

	@Check(CheckType.FAST)
	public void caseRangeValue(final RangeValue rv) {
		final NumberValue deltaNV = rv.getDeltaValue();
		if (deltaNV != null) {
			final double delta = deltaNV.getScaledValue();
			if (delta < 0) {
				error(rv, "Range value has a negative delta component");
			}
		}
	}

	@Check(CheckType.FAST)
	public void casePropertyAssociation(PropertyAssociation pa) {
		checkPropertyAssociation(pa);
	}

	/**
	 * Check Classifier reference for right type.
	 */

	@Check(CheckType.FAST)
	public void caseClassifierValue(ClassifierValue nt) {
		checkClassifierReferenceInWith(nt.getClassifier(), nt);
	}

	@Check(CheckType.FAST)
	public void caseContainmentPathElement(ContainmentPathElement pathElement) {
		typeCheckContainmentPathElement(pathElement);
	}

	// checking methods

	protected void checkPropertyAssociation(PropertyAssociation pa) {
		// type check value against type
		Property pdef = pa.getProperty();
		checkPropertySetElementReference(pdef, pa);
		if (Aadl2Util.isNull(pdef)) {
			return;
		}

		PropertyType pt = pdef.getPropertyType();
		if (Aadl2Util.isNull(pt)) {
			return;
		}

		EList<ModalPropertyValue> pvl = pa.getOwnedValues();
		for (ModalPropertyValue modalPropertyValue : pvl) {
			typeCheckPropertyValues(pt, modalPropertyValue.getOwnedValue(), pa, pdef.getQualifiedName());
		}
		checkAssociationAppliesTo(pa);
		checkInBinding(pa);
		if (pa.getProperty() != null && "Byte_Count".equalsIgnoreCase(pa.getProperty().getName())) {
			warning(pa, "Byte_Count is deprecated. Please use Memory_Size.");
		}
	}

	protected void checkInBinding(final PropertyAssociation pa) {
		for (Classifier c : pa.getInBindings()) {
			checkClassifierReferenceInWith(c, pa);
		}
	}

	/**
	 * Check constraints that property applies to the element it is associated
	 * with per Section 10.3:
	 *
	 * <blockquote>The property named by a property association must list the
	 * category of the component type, component implementation, subcomponent,
	 * feature, connection, flow, or mode the property association is declared
	 * for in its Property_Owner_Category list. </blockquote>
	 */
	private void checkAssociationAppliesTo(final PropertyAssociation pa) {
		final Property pn = pa.getProperty();
		final EList<ContainedNamedElement> appliesTo = pa.getAppliesTos();
		if (appliesTo == null || appliesTo.size() == 0) {
			Element element = pa.getOwner();
			if (element instanceof NamedElement) {
				final boolean applies = ((NamedElement) element).acceptsProperty(pn);
				if (!applies) {
					error(pa, "Property " + pa.getProperty().getQualifiedName() + " does not apply to "
							+ ((NamedElement) element).getName());
					// error(pa,
					// "Property " + pa.getQualifiedName() +
					// " does not apply to " + element.eClass().getName());
				}
			}
		} else {
			for (ContainedNamedElement cna : appliesTo) {
				EList<ContainmentPathElement> path = cna.getContainmentPathElements();
				if (!path.isEmpty()) {
					// only the last value is interesting to us
					final ContainmentPathElement ph = path.get(path.size() - 1);
					if (!Aadl2Util.isNull(ph.getNamedElement())) {
						final boolean applies = ph.getNamedElement().acceptsProperty(pn);
						if (!applies) {
							error(pa, "Property " + pa.getProperty().getQualifiedName() + " does not apply to "
									+ unparseAppliesTo(cna));
						}
					}
				}
			}
		}
	}

	private static String unparseAppliesTo(final ContainedNamedElement cna) {
		final StringBuffer sb = new StringBuffer();
		EList<ContainmentPathElement> path = cna.getContainmentPathElements();
		for (final Iterator<ContainmentPathElement> it = path.iterator(); it.hasNext();) {
			final ContainmentPathElement pc = it.next();
			sb.append(pc.getNamedElement().getName());
			if (it.hasNext()) {
				sb.append(".");
			}
		}
		return sb.toString();
	}

	/**
	 * Make sure that a NamedValue object pointing to a property or property constant referenced as a
	 * subclause of a boolean expression actually refers to a boolean-valued
	 * property.  Also make sure that if the property reference is to a
	 * property definition, then the property holder or property definition
	 * that it is a part of should have a compatible applies to clause.
	 */
	protected void checkPropertyReference(final NamedValue pr) {
		final EObject parent = pr.eContainer();
		final AbstractNamedValue anv = pr.getNamedValue();
		if (anv instanceof Property) {
			Property rp = (Property) anv;
			if (parent instanceof Operation) {
				final PropertyType pt = rp.getPropertyType();
				if (!(pt instanceof AadlBoolean) || rp.isList()) {
					error(pr, "Not a reference to a boolean-valued property");
				}
			}
			final Property refPD = rp;
			/*
			 * Find the property making reference to us. It is either the PD
			 * from a property association, or the enclosing PD if our use is
			 * as a default value.
			 */
			Property pd = null;
			EObject current = parent;
			while (current != null) {
				if (current instanceof PropertyAssociation) {
					pd = ((PropertyAssociation) current).getProperty();
					break;
				}
				if (current instanceof Property) {
					pd = (Property) current;
					break;
				}
				current = current.eContainer();
			}

			if (current == null) {
				error("Couldn't find enclosing property association or property definition for property reference");
			} else {
				final List refAppliesTo = refPD.getAppliesTos();
				final List appliesTo = pd.getAppliesTos();
				if (!refAppliesTo.containsAll(appliesTo)) {
					error(pr,
							"Referenced property definition does not apply to all the categories that the referring property applies to");
				}

				final List refAppliesToClass = new ArrayList();
				for (final Iterator i = refPD.getAppliesToClassifiers().iterator(); i.hasNext();) {
					final ClassifierValue cv = (ClassifierValue) i.next();
					refAppliesToClass.add(cv.getClassifier());
				}
				final List appliesToClass = new ArrayList();
				for (final Iterator i = pd.getAppliesToClassifiers().iterator(); i.hasNext();) {
					final ClassifierValue cv = (ClassifierValue) i.next();
					appliesToClass.add(cv.getClassifier());
				}
				if (!refAppliesToClass.containsAll(appliesToClass)) {
					error(pr,
							"Referenced property definition does not apply to all the classifiers that the referring property applies to");
				}

			}
		}
	}

	/**
	 * checks and report mismatch in type of value and type
	 * @param pt: PropertyType or unresolved proxy or null
	 * @param pv: PropertyExpression or null
	 */
	protected void typeCheckPropertyValues(PropertyType pt, PropertyExpression pv, Element holder, String defName) {
		typeCheckPropertyValues(pt, pv, "", holder, defName);
	}

	/**
	 * checks and report mismatch in type of value and type
	 * @param pt: PropertyType or unresolved proxy or null
	 * @param pv: PropertyExpression or null
	 * @param prefix: String prefix to error message used for lists
	 */
	protected void typeCheckPropertyValues(PropertyType pt, PropertyExpression pv, String prefix, Element holder,
			String defName) {

		if (Aadl2Util.isNull(pt) || pv == null || holder == null) {
			return;
		}
		String msg = " to property '" + defName + "' of type '" + pt.eClass().getName() + "'";
		if (!prefix.isEmpty() && !prefix.startsWith(" ")) {
			prefix = prefix + " ";
		}
		if (pv instanceof ListValue) {
			if (pt instanceof ListType) {
				typeMatchListElements(((ListType) pt).getElementType(), ((ListValue) pv).getOwnedListElements(),
						holder, defName);
			} else {
				error(holder, prefix + "Assigning a list of values" + msg);
			}
		} else if (pv instanceof Operation || pv instanceof BooleanLiteral) {
			if (!(pt instanceof AadlBoolean)) {
				error(holder, prefix + "Assigning a Boolean value" + msg);
			}
		} else if (pv instanceof StringLiteral) {
			if (!(pt instanceof AadlString)) {
				error(prefix + "Assigning String value" + msg, holder, null,
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX, Diagnostic.LINKING_DIAGNOSTIC);

			}
		} else if (pv instanceof EnumerationLiteral
				|| (pv instanceof NamedValue && ((NamedValue) pv).getNamedValue() instanceof EnumerationLiteral)) {
			if (!(pt instanceof EnumerationType)) {
				error(holder, prefix + "Assigning Enumeration literal" + msg);
			}
		} else if (pv instanceof UnitLiteral
				|| (pv instanceof NamedValue && ((NamedValue) pv).getNamedValue() instanceof UnitLiteral)) {
			if (!(pt instanceof UnitsType)) {
				error(holder, prefix + "Assigning Unit literal" + msg);
			}
		} else if (pv instanceof IntegerLiteral) {
			if (!(pt instanceof AadlInteger)) {
				error(holder, prefix + "Assigning Integer value" + msg);
			} else {
				checkUnits((AadlInteger) pt, (IntegerLiteral) pv);
			}
		} else if (pv instanceof RealLiteral) {
			if (!(pt instanceof AadlReal)) {
				error(holder, prefix + "Assigning Real value" + msg);
			} else {
				checkUnits((AadlReal) pt, (RealLiteral) pv);
			}
		} else if (pv instanceof RangeValue) {
			if (!(pt instanceof RangeType)) {
				error(holder, prefix + "Assigning Range value" + msg);
			} else {
				typeCheckPropertyValues(((RangeType) pt).getNumberType(), ((RangeValue) pv).getMinimumValue(), holder,
						defName);
				typeCheckPropertyValues(((RangeType) pt).getNumberType(), ((RangeValue) pv).getMaximumValue(), holder,
						defName);
				typeCheckPropertyValues(((RangeType) pt).getNumberType(), ((RangeValue) pv).getDeltaValue(), holder,
						defName);
			}
		} else if (pv instanceof ClassifierValue) {
			if (!(pt instanceof ClassifierType)) {
				error(pv, prefix + "Assigning incorrect Classifier value" + msg);
				return;
			}
			ClassifierValue cv = (ClassifierValue) pv;
			ClassifierType ct = (ClassifierType) pt;

			for (MetaclassReference mcri : ct.getClassifierReferences()) {
				if (mcri.getMetaclass().isSuperTypeOf(cv.getClassifier().eClass())) {
					return;
				}
			}
			error(holder, prefix + "Assigning classifier value with incorrect Classifier" + msg);
		} else if (pv instanceof RecordValue) {
			if (!(pt instanceof RecordType)) {
				error(pv, prefix + "Assinging Record value" + msg);
			} else {
				typeMatchRecordFields(((RecordValue) pv).getOwnedFieldValues(), holder, defName);
			}
		} else if (pv instanceof ReferenceValue) {
			if (!(pt instanceof ReferenceType)) {
				error(holder, prefix + "Assigning incorrect reference value" + msg);
			} else {
				ReferenceType ptrt = (ReferenceType) pt;
				ReferenceValue pvrv = (ReferenceValue) pv;
				EList<ContainmentPathElement> cpes = pvrv.getContainmentPathElements();
				if (!cpes.isEmpty()) {
					NamedElement ne = cpes.get(cpes.size() - 1).getNamedElement();
					for (MetaclassReference mcri : ptrt.getNamedElementReferences()) {
						if (mcri.getMetaclass().isSuperTypeOf(ne.eClass())) {
							return;
						}
					}
					error(holder, prefix + "Assigning reference value with incorrect Named Element class" + msg);
				}
			}
		} else if (pv instanceof NamedValue) {
			AbstractNamedValue nv = ((NamedValue) pv).getNamedValue();
			if (nv instanceof PropertyConstant) {
				typeCheckPropertyValues(pt, ((PropertyConstant) nv).getConstantValue(), holder, defName);
			} else if (nv instanceof Property) {
				PropertyType pvt = ((Property) nv).getPropertyType();
				if (!Aadl2Util.isNull(pvt) && pvt.eClass() != pt.eClass()) {
					error(holder, "Assigning property of incorrect type" + msg);
				}
			} else {
				error(holder, "Enum/Unit literal validation should have happened before");
			}
		}
	}

	protected void typeMatchListElements(PropertyType pt, EList<PropertyExpression> pel, Element holder, String defName) {
		for (PropertyExpression propertyExpression : pel) {
			typeCheckPropertyValues(pt, propertyExpression, "list element", holder, defName);
		}
	}

	protected void typeMatchRecordFields(EList<BasicPropertyAssociation> rfl, Element holder, String defName) {
		for (BasicPropertyAssociation field : rfl) {
			if (field.getProperty() != null) {
				typeCheckPropertyValues(field.getProperty().getPropertyType(), field.getValue(), holder, defName);
			}
		}
	}

	protected void checkUnits(NumberType nt, NumberValue nv) {
		UnitsType ut = nt.getUnitsType();
		UnitLiteral ul = nv.getUnit();
		if (Aadl2Util.isNull(ut) && Aadl2Util.isNull(ul)) {
			return;
		}
		if (ul == null) {
			error(nv, "Number value is missing a unit");
		} else if (!ut.getOwnedLiterals().contains(ul)) {
			error(nv, "Unit '" + ul.getName() + "'of number value is not of Units type " + ut.getQualifiedName());
		}
	}

	public void checkClassifierReferenceInWith(Classifier cl, Element context) {
		if (Aadl2Util.isNull(cl)) {
			return;
		}
		Namespace contextNS = AadlUtil.getContainingTopLevelNamespace(context);
		PackageSection referenceNS = (PackageSection) AadlUtil.getContainingTopLevelNamespace(cl);
		if (contextNS != referenceNS) {
			AadlPackage referencePackage = AadlUtil.getContainingPackage(referenceNS);
			if (!AadlUtil.isImportedPackage(referencePackage, contextNS)) {
				error("The referenced package '" + referencePackage.getName() + "' of classifier '" + cl.getName()
						+ "' is not listed in a with clause.", context, null, MISSING_WITH, referencePackage.getName(),
						EcoreUtil.getURI(referencePackage).toString(), EcoreUtil.getURI(contextNS).toString());
			}
		}
	}

	public void checkPropertySetElementReference(NamedElement pse, Element context) {
		if (Aadl2Util.isNull(pse)) {
			return;
		}
		Namespace contextNS = AadlUtil.getContainingTopLevelNamespace(context);
		PropertySet referenceNS = (PropertySet) AadlUtil.getContainingTopLevelNamespace(pse);
		if (contextNS != referenceNS) {
			if (!AadlUtil.isImportedPropertySet(referenceNS, contextNS)) {
				error("The referenced property set '"
						+ referenceNS.getName()
						+ "' of "
						+ (pse instanceof Property ? "property '" : (pse instanceof PropertyType ? "property type '"
								: "property constant '")) + pse.getName() + "' is not listed in a with clause.",
						context, null, MISSING_WITH, referenceNS.getName(), EcoreUtil.getURI(referenceNS).toString(),
						EcoreUtil.getURI(contextNS).toString());
			}
		}
	}

	private void typeCheckContainmentPathElement(ContainmentPathElement pathElement) {
		if (pathElement.getOwner() instanceof ContainmentPathElement
				&& (pathElement.getNamedElement() instanceof InternalFeature || pathElement.getNamedElement() instanceof ProcessorFeature)) {
			error(StringExtensions.toFirstUpper(getEClassDisplayNameWithIndefiniteArticle(pathElement.getNamedElement()
					.eClass()))
					+ " is not visible outside of its component implementation or extending implementations.",
					pathElement, Aadl2Package.eINSTANCE.getContainmentPathElement_NamedElement());
		}
	}

	protected static String getEClassDisplayNameWithIndefiniteArticle(EClass eClass) {
		StringBuilder displayName = new StringBuilder(eClass.getName());
		for (int i = displayName.length() - 1; i > 0; i--) {
			if (Character.isUpperCase(displayName.charAt(i))) {
				displayName.insert(i, ' ');
			}
		}
		if ("AEIOU".indexOf(displayName.charAt(0)) == -1) {
			displayName.insert(0, "a '");
		} else {
			displayName.insert(0, "an '");
		}
		displayName.append('\'');
		return displayName.toString().toLowerCase();
	}

	// helper methods

	@Override
	protected void error(String message, EObject source, EStructuralFeature feature) {
		error(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

	protected void error(EObject source, String message) {
		error(message, source, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

	protected void error(String message) {
		error(message, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

	@Override
	protected void warning(String message, EObject source, EStructuralFeature feature) {
		warning(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

	protected void warning(EObject source, String message) {
		warning(message, source, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

	protected void warning(String message) {
		warning(message, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

	protected void info(EObject source, String message) {
		info(message, source, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

	protected void info(String message) {
		info(message, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
	}

}
