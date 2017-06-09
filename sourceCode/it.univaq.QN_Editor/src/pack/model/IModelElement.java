/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *******************************************************************************/
package pack.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Abstract prototype of a model element.
 * <p>This class provides features necessary for all model elements, like:</p>
 * <ul>
 * <li>property-change support (used to notify edit parts of model changes),</li> 
 * <li>property-source support (used to display property values in the Property View) and</li>
 * <li>serialization support (the model hierarchy must be serializable, so that the editor can
 * save and restore a binary representation. You might not need this, if you store the model
 * a non-binary form like XML).</li>
 * </ul>
 * @author Elias Volanakis
 */
public interface IModelElement extends IPropertySource, Serializable 
	{
	
	/** ID for the X property value (used for by the corresponding property descriptor).  */
	static final String XPOS_PROP = "Node.xPos";
	
	/** ID for the Y property value (used for by the corresponding property descriptor).  */
	static final String YPOS_PROP = "Node.yPos";
	
	static final String NAME = "Node.name";
	
	static final String JOBS = "Workload.jobs";
	
	static final String TIMEUNIT = "Workload.TimeUnits";
	
	static final String WORKLOAD = "Node.workload";
	
	static final String VISITS = "Workload.Visits";
	
	static final String QUANTITY = "Node.quantity";
	
	static final String SCHEDULING = "Node.scheduling";
		
	/**
	 * Children should override this. The default implementation returns an empty array.
	 * 
	 * A descriptor for a property to be presented by a standard property sheet page (PropertySheetPage). 
	 * These descriptors originate with property sources (IPropertySource).
	 * A property descriptor carries the following information:
	 * 
	 * 	•property id (required)
	 *  •display name (required)
	 *  •brief description of the property (optional)
	 *  •category for grouping related properties (optional)
	 *  •label provider used to display the property value (optional)
	 *  •cell editor for changing the property value (optional)
	 *  •help context id (optional)
	 *  
	 *  Clients may implement this interface to provide specialized property descriptors; 
	 *  however, there are standard implementations declared in this package that take care 
	 *  of the most common cases:
	 *  
	 *  •PropertyDescriptor - read-only property
	 *  •TextPropertyDescriptor - edits with a TextCellEditor
	 *  •CheckboxPropertyDescriptor - edits with a CheckboxCellEditor
	 *  •ComboBoxPropertyDescriptor - edits with a ComboBoxCellEditor
	 *  •ColorPropertyDescriptor - edits with a ColorCellEditor
	 */
	public IPropertyDescriptor[] getPropertyDescriptors(); 

	public PropertyChangeSupport getPcsDelegate();
	
	public void setPcsDelegate(PropertyChangeSupport pcsDelegate);
	
	/** 
	 * Attach a non-null PropertyChangeListener to this object.
	 * 
	 * @param l a non-null PropertyChangeListener instance
	 */
	public void addPropertyChangeListener(PropertyChangeListener l); 

	/** 
	 * Report a property change to registered listeners (for example edit parts).
	 * @param property the programmatic name of the property that changed
	 * @param oldValue the old value of this property
	 * @param newValue the new value of this property
	 */
	public void firePropertyChange(String property, Object oldValue, Object newValue);

	
	/**
	 * Returns a value for this property source that can be edited in a property sheet.
	 * <p>My personal rule of thumb:</p>
	 * <ul>
	 * <li>model elements should return themselves and</li> 
	 * <li>custom IPropertySource implementations (like DimensionPropertySource in the GEF-logic
	 * example) should return an editable value.</li>
	 * </ul>
	 * <p>Override only if necessary.</p>
	 * 
	 * Returns a value for this property source that can be edited in a property sheet.
	 * This value is used when this IPropertySource is appearing in the property sheet 
	 * as the value of a property of some other IPropertySource
	 * This value is passed as the input to a cell editor opening on an IPropertySource.
	 * This value is also used when an IPropertySource is being used as the value in a 
	 * setPropertyValue message. The reciever of the message would then typically 
	 * use the editable value to update the original property source or construct a new instance.
	 * For example an email address which is a property source may have an editable value which 
	 * is a string so that it can be edited in a text cell editor. 
	 * The email address would also have a constructor or setter that takes the edited string 
	 * so that an appropriate instance can be created or the original instance modified 
	 * when the edited value is set.
	 * This behavior is important for another reason. 
	 * When the property sheet is showing properties for more than one object (multiple selection), 
	 * a property sheet entry will display and edit a single value (typically coming from the first 
	 * selected object). After a property has been edited in a cell editor, 
	 * the same value is set as the property value for all of the objects. This is fine for primitive 
	 * types but otherwise all of the objects end up with a reference to the same value. 
	 * Thus by creating an editable value and using it to update the state of the original 
	 * property source object, one is able to edit several property source objects at once 
	 * (multiple selection). 
	 * 
	 * @return this instance
	 */
	public Object getEditableValue(); 


	/**
	 * Children should override this. The default implementation returns null.
	 */
	public Object getPropertyValue(Object id);

	/**
	 * Children should override this.
	 */
	public boolean isPropertySet(Object id);

	/** 
	 * Remove a PropertyChangeListener from this component.
	 * Le implementazioni dovrebbero essere metodi con modificatore synchronized.
	 * 
	 * @param l a PropertyChangeListener instance
	 */
	public void removePropertyChangeListener(PropertyChangeListener l);

	/**
	 * Children should override this.
	 * 
	 * Does nothing if the notion of a default value is not meaningful for the specified property, 
	 * or if the property's value cannot be changed, or if this source does not have the specified property. 
	 */
	public void resetPropertyValue(Object id);

	/**
	 * Children should override this.
	 */
	public void setPropertyValue(Object id, Object value);
	
	}
