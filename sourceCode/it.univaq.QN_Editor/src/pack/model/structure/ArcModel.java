package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.LoadingException;
import pack.errorManagement.SavingException;
import pack.model.ArcTypeBendpoint;
import pack.model.IArc;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.schema.ArcType;

public class ArcModel implements IArc {

	private static final long serialVersionUID = 1L;
	
	private transient INode source;
	
	private transient INode target;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);


	private static IPropertyDescriptor[] propertyDescriptors = 
		new IPropertyDescriptor[] 
	       { 
	       new TextPropertyDescriptor("Arc.description", "Description")
	       };
	
	/** True, if the transit is attached to its endpoints. */ 
	private transient boolean isConnected;

	private List<ArcTypeBendpoint> bendpoints = new ArrayList<ArcTypeBendpoint>();
	
	private transient IQNM iqnm;

	// i seguenti attributi si riferiscono all'xml
	private transient String description;
	
	private String fromNode;
	
	private String toNode;
	
	public ArcModel(ArcType arcType, IQNM iqnm)
		throws LoadingException
		{
		if (arcType.hasFromNode())
			try {
				fromNode = arcType.getFromNode().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (arcType.hasToNode())
			try {
				toNode = arcType.getToNode().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		this.iqnm = iqnm;
		}
	
	// il seguente costruttore viene utilizzato nei comandi
	public ArcModel(INode source, INode target, IQNM iqnm)
		throws Exception
		{
		super();
		this.iqnm = iqnm;
		String string = source.getServerName();
		String string2 = target.getServerName();
		this.fromNode = string;
		this.toNode = string2;
		reconnect(source, target);
		}

	@Override
	public void disconnect() 
		{
		if (isConnected) 
			{
			source.removeSourceArc(this);
			target.removeTargetArc(this);
			isConnected = false;
			iqnm.removeChild(this);
			}
		}

	@Override
	public List<ArcTypeBendpoint> getBendpoints() 
		{
		return bendpoints;
		}

	@Override
	public INode getSource() 
		{
		return source;
		}

	@Override
	public INode getTarget() 
		{
		return target;
		}

	@Override
	public void insertBendpoint(int index, ArcTypeBendpoint bendpoint) 
		{
		getBendpoints().add(index, bendpoint);
		firePropertyChange("bendpoint", null, null);
		}

	@Override
	public void reconnect() 
		{
		if (!isConnected) 
			{
			source.addSourceArc(this);
			target.addTargetArc(this);
			isConnected = true;
			iqnm.addChild(this);
			}
		}

	@Override
	public void reconnect(INode newSource, INode oldTarget) throws Exception 
		{
		if (newSource == null) 
			Finestra.mostraIE("source is null");
		if (oldTarget == null)
			Finestra.mostraIE("target is null");
		disconnect();
		this.source = newSource;
		this.fromNode = newSource.getServerName();
		this.target = oldTarget;
		this.toNode = oldTarget.getServerName();
		reconnect();
		}

	@Override
	public void removeBendpoint(int index) 
		{
		getBendpoints().remove(index);
		firePropertyChange("bendpoint", null, null);
		}

	@Override
	public void setBendpoint(int index, ArcTypeBendpoint bendpoint) 
		{
		getBendpoints().set(index, bendpoint);
		firePropertyChange("bendpoint", null, null);
		}

	@Override
	public void setSource(INode source) 
		{
		this.source = source;
		}

	@Override
	public void setTarget(INode target) 
		{
		this.target = target;
		}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) 
		{
		try {
			if (l == null) 
				{
				Finestra.mostraIE("listener is null");
				}
			getPcsDelegate().addPropertyChangeListener(l);
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void firePropertyChange(String property, Object oldValue,
			Object newValue) 
		{
		if (getPcsDelegate().hasListeners(property)) 
			{
			getPcsDelegate().firePropertyChange(property, oldValue, newValue);
			}
		}

	@Override
	public Object getEditableValue() 
		{
		return null;
		}

	@Override
	public PropertyChangeSupport getPcsDelegate() 
		{
		return this.pcsDelegate;
		}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() 
		{
		return propertyDescriptors;
		}

	@Override
	public Object getPropertyValue(Object id) 
		{
		if ("Arc.description".equals(id)) 
			{
			if (this.description != null)
				return this.description;
			else
				return "";
			}
		return null;
		}

	@Override
	public boolean isPropertySet(Object id) 
		{
		return false;
		}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) 
		{
		if (l != null) 
			{
			pcsDelegate.removePropertyChangeListener(l);
			}
		}

	@Override
	public void resetPropertyValue(Object id) 
		{}

	@Override
	public void setPcsDelegate(PropertyChangeSupport pcsDelegate) 
		{
		this.pcsDelegate = pcsDelegate;
		}

	@Override
	public void setPropertyValue(Object id, Object value) 
		{
		if ("Arc.description".equals(id))
			{
			this.description = value.toString();
				// si notifica il tutto
			firePropertyChange("Arc.description", null, value);
			}
		else 
			{
			try {
				Finestra.mostraIE("bad property");
				} 
			catch (Exception e) 
				{}
			}		
		}

	@Override
	public String getFromNode() 
		{
		return this.fromNode;
		}

	@Override
	public String getToNode() 
		{
		return this.toNode;
		}

	@Override
	public void setConnected(boolean b) 
		{
		this.isConnected = b;
		}

	@Override
	public ArcType generaDom() throws SavingException
		{
		ArcType arcType = new ArcType();
		if (this.description != null)
			{
			try {
				arcType.addDescription(this.description);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.fromNode != null)
			{
			try {
				arcType.addFromNode(this.fromNode);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.toNode != null)
			{
			try {
				arcType.addToNode(this.toNode);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		return arcType;
		}
	
	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof ArcModel) || arg0 == null)
			return false;
		ArcModel arcModel = (ArcModel)arg0;
		if (this.fromNode != null && arcModel.toNode != null)
			{
			if (!(this.fromNode.equals(arcModel.fromNode)))
				return false;
			}
		else return false;
		if (this.toNode != null && arcModel.toNode != null)
			{
			if (!(this.toNode.equals(arcModel.toNode)))
				return false;
			}
		return true;
		}

	@Override
	public void setDescription(String string) 
		{
		this.description = string;
		}
	
	public boolean isConnected() 
		{
		return isConnected;
		}

	public void setIqnm(IQNM iqnm) 
		{
		this.iqnm = iqnm;
		}

	@Override
	public void setFromNode(String string) 
		{
		this.fromNode = string;
		}

	@Override
	public void setToNode(String string) 
		{
		this.toNode = string;
		}
	}
