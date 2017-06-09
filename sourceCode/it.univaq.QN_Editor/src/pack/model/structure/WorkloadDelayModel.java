package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.figures.Etichetta;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.schema.TransitType;

public class WorkloadDelayModel 
	implements IRequest 
	{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient ClosedWorkloadModel closedWorkloadModel;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        {
								new TextPropertyDescriptor(XPOS_PROP, "X"), // id and description pair
								new TextPropertyDescriptor(YPOS_PROP, "Y"),
								new TextPropertyDescriptor("Workload.delay", "Think Device"),
		                        };
	
	private Point point;
	
	private transient Etichetta etichetta;
	
	public WorkloadDelayModel(ClosedWorkloadModel closedWorkloadModel, Point point) 
		{
		super();
		this.closedWorkloadModel = closedWorkloadModel;
		this.point = point;
		}

	// il seguente costruttore viene utilizzato nella factory di questa classe
	public WorkloadDelayModel(ClosedWorkloadModel closedWorkloadModel)
		{
		super();
		this.closedWorkloadModel = closedWorkloadModel;
		}
	
	@Override
	public void addSourceTransit(ITransit transit) 
		{
		closedWorkloadModel.addSourceTransit(transit);
		// si notifica il cambio
		firePropertyChange(IRequest.SOURCE_TRANSIT_PROP, null, transit);
		}

	@Override
	public Point getLocation() 
		{
		return point.getCopy();
		}

	@Override
	public IQNM getQNM() 
		{
		return closedWorkloadModel.getQNM();
		}

	@Override
	public String getServerID() 
		{
		return closedWorkloadModel.getThinkDevice();
		}

	@Override
	public List<ITransit> getSourceTransits() 
		{
		return closedWorkloadModel.getSourceTransits();
		}

	@Override
	public List<ITransit> getTargetTransits() 
		{
		return closedWorkloadModel.getTargetTransits();
		}

	@Override
	public String getWorkloadName() 
		{
		return closedWorkloadModel.getWorkloadName();
		}

	@Override
	public boolean hasTransit(ITransit transit) 
		{
		return closedWorkloadModel.hasTransit(transit);
		}

	@Override
	public void removeSourceTransit(ITransit transit) 
		{
		closedWorkloadModel.removeSourceTransit(transit);
		// si notifica il cambiamento
		firePropertyChange(IRequest.SOURCE_TRANSIT_PROP, null, transit);
		}

	@Override
	public void setLocation(Point location) throws Exception 
		{
		point = location.getCopy();
		firePropertyChange(LOCATION_PROP, null, location);
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
		if (this.pcsDelegate == null)
			{
			this.pcsDelegate = new PropertyChangeSupport(this);
			return this.pcsDelegate;
			}
		else
			return this.pcsDelegate;
		}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() 
		{
		return descriptors;
		}

	@Override
	public Object getPropertyValue(Object id) 
		{
		if (XPOS_PROP.equals(id))
			return Integer.toString(point.x);
		if (YPOS_PROP.equals(id))
			return Integer.toString(point.y);
		if ("Workload.delay".equals(id))
			{
			return closedWorkloadModel.getThinkDevice();
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
	public void setPropertyValue(Object id, Object value) 
		{
		try {
			if (XPOS_PROP.equals(id)) 
				{
				int x = Integer.parseInt((String) value);
				point.setLocation(x, point.y);
				// si notifica il cambio di posizione
				firePropertyChange(LOCATION_PROP, null, value);
				} 
			else if (YPOS_PROP.equals(id)) 
				{
				int y = Integer.parseInt((String) value);
				point.setLocation(point.x, y);
				firePropertyChange(LOCATION_PROP, null, value);
				}
			else if ("Workload.delay".equals(id))
				{
				setServerID(value.toString());
				// si notifica il cambio del centro di delay
				firePropertyChange(ClosedWorkloadModel.DELAY_NAME, null, value);
				}
			else Finestra.mostraIE("bad property");
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void setServerID(String string) 
		{
		closedWorkloadModel.setThinkDevice(string);
		}

	@Override
	public void setQNM(IQNM iqnm) 
		{
		closedWorkloadModel.setQNM(iqnm);
		}

	@Override
	public void setPcsDelegate(PropertyChangeSupport pcsDelegate) 
		{
		this.pcsDelegate = pcsDelegate;
		}

	@Override
	public void setPoint(Point point) 
		{
		this.point = point.getCopy();
		}

	@Override
	public void setSourceTransits(List<ITransit> list) 
		{
		this.closedWorkloadModel.setSourceTransits(list);
		}

	@Override
	public void setTargetTransits(List<ITransit> list) 
		{
		this.closedWorkloadModel.setTargetTransits(list);
		}

	@Override
	public void removeTargetTransit(ITransit transitModel) 
		{
		this.closedWorkloadModel.removeTargetTransit(transitModel);
		// si notifica il cambio
		firePropertyChange(IRequest.TARGET_TRANSIT_PROP, null, transitModel);
		}

	@Override
	public void addTargetTransit(ITransit transitModel) 
		{
		this.closedWorkloadModel.addTargetTransit(transitModel);
		// si notifica il cambio
		firePropertyChange(IRequest.TARGET_TRANSIT_PROP, null, transitModel);
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof WorkloadDelayModel) || arg0 == null)
			return false;
		WorkloadDelayModel workloadDelayModel = (WorkloadDelayModel)arg0;
		if (this.closedWorkloadModel.hasThinkDevice() && workloadDelayModel.closedWorkloadModel.hasThinkDevice())
			{
			if (!(this.closedWorkloadModel.getThinkDevice().equals(workloadDelayModel.closedWorkloadModel.getThinkDevice())))
				return false;
			}
		else return false;
		return true;
		}

	public ClosedWorkloadModel getClosedWorkloadModel() 
		{
		return closedWorkloadModel;
		}

	@Override
	public List<TransitType> getTransitTypes() 
		{
		return this.closedWorkloadModel.getTransitTypes();
		}

	public void setClosedWorkloadModel(ClosedWorkloadModel closedWorkloadModel) 
		{
		this.closedWorkloadModel = closedWorkloadModel;
		}

	public ITransit findSourceTransit(String string) 
		{
		return closedWorkloadModel.findSourceTransit(string);
		}

	@Override
	public void setTransitTypesToNull() 
		{
		this.closedWorkloadModel.setTransitTypesToNull();
		}

	@Override
	public Etichetta getEtichetta() 
		{
		return this.etichetta;
		}

	@Override
	public void setEtichetta(Etichetta etichetta) 
		{
		this.etichetta = etichetta;
		}
	
	@Override
	public List<ITransit> getSourceTransitsCloned() throws Exception 
		{
		List<ITransit> list = new ArrayList<ITransit>();
		for (ITransit transit : this.closedWorkloadModel.getSourceTransits())
			{
			ITransit transit2 = null;
			try {
				transit2 = (ITransit)transit.clone();
				} 
			catch (CloneNotSupportedException e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
			list.add(transit2);
			}
		return list;
		}
	
	@Override
	public List<ITransit> getTargetTransitsCloned() throws Exception 
		{
		List<ITransit> list = new ArrayList<ITransit>();
		for (ITransit transit : this.getTargetTransits())
			{
			ITransit transit2 = null;
			try {
				transit2 = (ITransit)transit.clone();
				} 
			catch (CloneNotSupportedException e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
			list.add(transit2);
			}
		return list;
		}

	@Override
	public void setWorkloadName(String string) 
		{
		// dal delay non è possibile cambiare carico
		// di lavoro
		}
	}
