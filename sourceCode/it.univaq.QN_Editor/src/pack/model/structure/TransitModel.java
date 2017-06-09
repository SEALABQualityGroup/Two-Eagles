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
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.TransitTypeBendpoint;
import pack.model.schema.TransitType;

public class TransitModel implements ITransit {

	private static final long serialVersionUID = 1L;

	private transient IRequest source;
	
	private transient IRequest target;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);

	private static IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[] 
	    { 
		new TextPropertyDescriptor("Transit.probability", "Probability")
        };

	/** True, if the transit is attached to its endpoints. */ 
	private transient boolean isConnected;
	
	private List<TransitTypeBendpoint> bendpoints = new ArrayList<TransitTypeBendpoint>();
	
	private IQNM iqnm;
	
	// i seguenti attributi si riferiscono all'xml
	private String to;
	
	private transient Float probability;
	
	public TransitModel(IRequest request, IRequest request2)
		throws Exception
		{
		reconnect(request,request2);
		}
	
	public TransitModel(TransitType transitType, IQNM iqnm)
		throws LoadingException
		{
		this.iqnm = iqnm;
		// costruttore utilizzato per il caricamento della struttura da xml
		if (transitType.hasTo())
			{
			String string = null;
			try {
				string = transitType.getTo().asString();
				this.to = string;
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (!this.iqnm.existsNodeFromXML(string))
				{
				// caso in cui l'attributo to indica un nodo inesistente
				Finestra.mostraLE("XML Loading error: the destination node"+string+" don't exists");
				}
			}
		else
			{
			Finestra.mostraLE("XML Loading error: there is transit without destination");
			}
		}
	
	// il seguente costruttore viene utilizzato per la clonazione
	public TransitModel() 
		{
		super();
		}

	@Override
	public void disconnect() 
		{
		if (isConnected) 
			{
			source.removeSourceTransit(this);
			target.removeTargetTransit(this);
			isConnected = false;
			}
		}

	@Override
	public List<TransitTypeBendpoint> getBendpoints() 
		{
		return bendpoints;
		}

	@Override
	public IRequest getSource() 
		{
		return source;
		}

	@Override
	public IRequest getTarget() 
		{
		return target;
		}

	@Override
	public void insertBendpoint(int index, TransitTypeBendpoint bendpoint) 
		{
		getBendpoints().add(index, bendpoint);
		firePropertyChange("bendpoint", null, null);
		}

	@Override
	public void reconnect() 
		{
		if (!isConnected) 
			{
			source.addSourceTransit(this);
			target.addTargetTransit(this);
			isConnected = true;
			}
		}

	@Override
	public void reconnect(IRequest newSource, IRequest oldTarget) throws Exception 
		{
		if (newSource == null || oldTarget == null) 
			Finestra.mostraIE("source is null");
		if (oldTarget == null)
			Finestra.mostraIE("target is null");
		disconnect();
		this.source = newSource;
		this.target = oldTarget;
		this.to = oldTarget.getServerID();
		reconnect();
		}

	@Override
	public void removeBendpoint(int index) 
		{
		getBendpoints().remove(index);
		firePropertyChange("bendpoint", null, null);
		}

	@Override
	public void setBendpoint(int index, TransitTypeBendpoint bendpoint) 
		{
		getBendpoints().set(index, bendpoint);
		firePropertyChange("bendpoint", null, null);
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
		return this;
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
		if ("Transit.probability".equals(id)) 
			{
			if (this.probability != null)
				return this.probability.toString();
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
		try {
			if ("Transit.probability".equals(id))
				{
				try {
					Float float1 = new Float(value.toString());
					if (float1 < 0)
						{
						Finestra.mostraIE("Setting error: the service request with workload equal to "+
							this.source.getWorkloadName()+
							" and node equal to "+
							this.source.getServerID()+" has transit with negative probability");
						}
					this.probability = float1;
					// si notifica il cambio
					firePropertyChange("Transit.probability", null, value);
					} 
				catch (NumberFormatException e) 
					{
					// caso in cui la probabilità non è un float
					Finestra.mostraIE("Setting error: the service request with workload equal to "+
							this.source.getWorkloadName()+" and node equal to "+
							this.source.getServerID()+" has transit with bad probability");
					}
				}
			else Finestra.mostraIE("bad property");
			}
		catch (Exception exception)
			{}
		}
	
	public void setConnected(boolean isConnected) 
		{
		this.isConnected = isConnected;
		}

	@Override
	public String getTo() 
		{
		return this.to;
		}

	@Override
	public TransitType generaDom() throws SavingException
		{
		TransitType transitType = new TransitType();
		if (this.probability != null)
			{
			try {
				transitType.addProbability(this.probability.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// caso in cui il transit non ha la probabilità
			Finestra.mostraSE("Saving error: the transit with server equal to "+getSource().getServerID()+
					" and workload equal to "+getSource().getWorkloadName()+" has no probability");
		if (this.to != null)
			{
			try {
				transitType.addTo(this.to);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// caso in cui l'attributo to indica un nodo inesistente
			Finestra.mostraSE("Saving error: there is transit without destination");
		return transitType;
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof TransitModel) || arg0 == null)
			return false;
		TransitModel transitModel = (TransitModel)arg0;
		if (this.to != null && transitModel.to != null)
			{
			if (!(this.to.equals(transitModel.to)))
				return false;
			}
		else return false;
		// il transit deve essere identificato anche dal nome del sorgente
		if (this.source != null && transitModel.source != null)
			{
			if (!(this.source.getServerID().equals(transitModel.source.getServerID())))
				return false;
			}
		else return false;
		return true;
		}

	@Override
	public void setProbability(Float float1) 
		{
		this.probability = float1;
		}

	public void setSource(IRequest source) 
		{
		this.source = source;
		}

	public void setTarget(IRequest target) 
		{
		this.target = target;
		}

	@Override
	public Object clone() throws CloneNotSupportedException 
		{
		TransitModel transitModel = new TransitModel();
		// si clonano anche i bendpoint
		List<TransitTypeBendpoint> list = new ArrayList<TransitTypeBendpoint>();
		for (TransitTypeBendpoint transitTypeBendpoint : this.bendpoints)
			{
			TransitTypeBendpoint transitTypeBendpoint2 = (TransitTypeBendpoint)transitTypeBendpoint.clone();
			list.add(transitTypeBendpoint2);
			}
		transitModel.bendpoints = list;
		transitModel.isConnected = this.isConnected;
		// si clona il PropertyChangeSupport
		PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
		for (PropertyChangeListener propertyChangeListener : this.pcsDelegate.getPropertyChangeListeners())
			{
			propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
			}
		transitModel.pcsDelegate = propertyChangeSupport;
		transitModel.probability = new Float(this.probability);
		transitModel.source = this.source;
		transitModel.target = this.target;
		transitModel.to = new String(this.to);
		return transitModel;
		}

	@Override
	public void setTo(String string) 
		{
		this.to = string;
		}
	}
