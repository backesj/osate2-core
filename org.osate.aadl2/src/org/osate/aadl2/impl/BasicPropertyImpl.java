/**
 * <copyright>
 * Copyright  2008 by Carnegie Mellon University, all rights reserved.
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
 * Carnegie Mellon University Software Engineering Institute authored documents are sponsored by the U.S. Department
 * of Defense under Contract F19628-00-C-0003. Carnegie Mellon University retains copyrights in all material produced
 * under this contract. The U.S. Government retains a non-exclusive, royalty-free license to publish or reproduce these
 * documents, or allow others to do so, for U.S. Government purposes only pursuant to the copyright license
 * under the contract clause at 252.227.7013.
 * </copyright>
 *
 * $Id: BasicPropertyImpl.java,v 1.14 2010-04-15 14:15:01 lwrage Exp $
 */
package org.osate.aadl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.BasicProperty;
import org.osate.aadl2.ListType;
import org.osate.aadl2.PropertyType;
import org.osate.aadl2.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Basic Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.osate.aadl2.impl.BasicPropertyImpl#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link org.osate.aadl2.impl.BasicPropertyImpl#getOwnedPropertyType <em>Owned Property Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BasicPropertyImpl extends TypedElementImpl implements BasicProperty {
	/**
	 * The cached value of the '{@link #getPropertyType() <em>Property Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyType()
	 * @generated
	 * @ordered
	 */
	protected PropertyType propertyType;
	/**
	 * The cached value of the '{@link #getOwnedPropertyType() <em>Owned Property Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedPropertyType()
	 * @generated
	 * @ordered
	 */
	protected PropertyType ownedPropertyType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BasicPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Aadl2Package.eINSTANCE.getBasicProperty();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getPropertyTypeGen() {
		if (propertyType != null && ((EObject) propertyType).eIsProxy()) {
			InternalEObject oldPropertyType = (InternalEObject) propertyType;
			propertyType = (PropertyType) eResolveProxy(oldPropertyType);
			if (propertyType != oldPropertyType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							Aadl2Package.BASIC_PROPERTY__PROPERTY_TYPE, oldPropertyType, propertyType));
			}
		}
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyType getPropertyType() {
		if (propertyType == null) {
			propertyType = ownedPropertyType;
		}
		PropertyType pt = getPropertyTypeGen();
		return (pt == null || pt.eIsProxy()) ? null : pt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType basicGetPropertyTypeGen() {
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyType basicGetPropertyType() {
		if (propertyType == null) {
			propertyType = ownedPropertyType;
		}
		return basicGetPropertyTypeGen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setPropertyType(PropertyType newPropertyType) {
		PropertyType oldPropertyType = propertyType;
		propertyType = newPropertyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Aadl2Package.BASIC_PROPERTY__PROPERTY_TYPE,
					oldPropertyType, propertyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPropertyTypeGen() {
		return propertyType != null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isSetPropertyType() {
		if (propertyType == null) {
			propertyType = ownedPropertyType;
		}
		return isSetPropertyTypeGen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getOwnedPropertyType() {
		return ownedPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwnedPropertyType(PropertyType newOwnedPropertyType, NotificationChain msgs) {
		PropertyType oldOwnedPropertyType = ownedPropertyType;
		ownedPropertyType = newOwnedPropertyType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE, oldOwnedPropertyType, newOwnedPropertyType);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (newOwnedPropertyType != null) {
				if (newOwnedPropertyType != propertyType) {
					setPropertyType(newOwnedPropertyType);
				}
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnedPropertyType(PropertyType newOwnedPropertyType) {
		if (newOwnedPropertyType != ownedPropertyType) {
			NotificationChain msgs = null;
			if (ownedPropertyType != null)
				msgs = ((InternalEObject) ownedPropertyType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE, null, msgs);
			if (newOwnedPropertyType != null)
				msgs = ((InternalEObject) newOwnedPropertyType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE, null, msgs);
			msgs = basicSetOwnedPropertyType(newOwnedPropertyType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE,
					newOwnedPropertyType, newOwnedPropertyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType createOwnedPropertyType(EClass eClass) {
		PropertyType newOwnedPropertyType = (PropertyType) create(eClass);
		setOwnedPropertyType(newOwnedPropertyType);
		return newOwnedPropertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE:
			return basicSetOwnedPropertyType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type getType() {
		return getPropertyType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type basicGetType() {
		return basicGetPropertyType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(Type newType) {
		if (newType != null && !(newType instanceof PropertyType)) {
			throw new IllegalArgumentException("newType must be an instance of PropertyType");
		}
		setPropertyType((PropertyType) newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetType() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case Aadl2Package.BASIC_PROPERTY__PROPERTY_TYPE:
			if (resolve)
				return getPropertyType();
			return basicGetPropertyType();
		case Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE:
			return getOwnedPropertyType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case Aadl2Package.BASIC_PROPERTY__PROPERTY_TYPE:
			setPropertyType((PropertyType) newValue);
			return;
		case Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE:
			setOwnedPropertyType((PropertyType) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case Aadl2Package.BASIC_PROPERTY__PROPERTY_TYPE:
			setPropertyType((PropertyType) null);
			return;
		case Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE:
			setOwnedPropertyType((PropertyType) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case Aadl2Package.BASIC_PROPERTY__PROPERTY_TYPE:
			return isSetPropertyType();
		case Aadl2Package.BASIC_PROPERTY__TYPE:
			return isSetType();
		case Aadl2Package.BASIC_PROPERTY__OWNED_PROPERTY_TYPE:
			return ownedPropertyType != null;
		}
		return super.eIsSet(featureID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osate.aadl2.BasicProperty#isList()
	 */
	public boolean isList() {
		return getType() instanceof ListType;
	}

} // BasicPropertyImpl
