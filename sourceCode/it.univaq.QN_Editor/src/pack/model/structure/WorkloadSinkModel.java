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

public class WorkloadSinkModel implements IRequest {

	private static final long serialVersionUID = 1L;

	private transient OpenWorkloadModel openWorkloadModel;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        {
								new TextPropertyDescriptor(XPOS_PROP, "X"), // id and description pair
								new TextPropertyDescriptor(YPOS_PROP, "Y"),
								new TextPropertyDescriptor("Workload.Sink", "Departs at")
		                        };
	
	private Point point;
	
	private transient Etichetta etichetta;

	public WorkloadSinkModel(OpenWorkloadModel openWorkloadModel, Point point)
		{
		super();
		this.openWorkloadModel = openWorkloadModel;
		this.point = point;
		}

	@Override
	public void addSourceTransit(ITransit transit) 
		{
		// non è possibile aggiungere un transit ad un nodo sink
		}

	@Override
	public Point getLocation() 
		{
		return point.getCopy();
		}

	@Override
	public IQNM getQNM() 
		{
		return openWorkloadModel.getQNM();
		}

	@Override
	public String getServerID() 
		{
		return openWorkloadModel.getSinkName();
		}

	@Override
	public List<ITransit> getSourceTransits() 
		{
		// la lista dei transit deve essere vuota
		List<ITransit> list = new ArrayList<ITransit>();
		return list;
		}

	@Override
	public List<ITransit> getTargetTransits() 
		{
		return openWorkloadModel.getTargetTransits();
		}

	@Override
	public String getWorkloadName() 
		{
		return openWorkloadModel.getWorkloadName();
		}

	@Override
	public boolean hasTransit(ITransit transit) 
		{
		// il sink non può avere transit
		return false;
		}

	@Override
	public void removeSourceTransit(ITransit transit) 
		{
		// il nodo sink non ha transits
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
		if ("Workload.Sink".equals(id))
			{
			return openWorkloadModel.getSinkName();
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
				firePropertyChange(LOCATION_PROP, null, value);
				} 
			else if (YPOS_PROP.equals(id)) 
				{
				int y = Integer.parseInt((String) value);
				point.setLocation(point.x, y);
				firePropertyChange(LOCATION_PROP, null, value);
				}
			else if ("Workload.Sink".equals(id))
				{
				openWorkloadModel.setSinkName(value.toString());
				// si notifica il cambio del nodo pozzo
				firePropertyChange(OpenWorkloadModel.SINK_NAME, null, value);
				}
			else Finestra.mostraIE("bad property");
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void setServerID(String string) 
		{
		openWorkloadModel.setSinkName(string);
		}

	@Override
	public void setQNM(IQNM iqnm) 
		{
		openWorkloadModel.setQNM(iqnm);
		}

	@Override
	public void setPcsDelegate(PropertyChangeSupport pcsDelegate) 
		{
		this.pcsDelegate = pcsDelegate;
		}

	@Override
	public void setPoint(Point point) 
		{
		point = point.getCopy();
		}

	@Override
	public void setSourceTransits(List<ITransit> list) 
		{}

	@Override
	public void setTargetTransits(List<ITransit> list) 
		{
		this.openWorkloadModel.setTargetTransits(list);
		}

	@Override
	public void removeTargetTransit(ITransit transitModel) 
		{
		this.openWorkloadModel.removeTargetTransit(transitModel);
		// si notifica il cambio
		firePropertyChange(IRequest.TARGET_TRANSIT_PROP, null, transitModel);
		}

	@Override
	public void addTargetTransit(ITransit transitModel) 
		{
		this.openWorkloadModel.addTargetTransit(transitModel);
		// si notifica il cambio
		firePropertyChange(IRequest.TARGET_TRANSIT_PROP, null, transitModel);
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof WorkloadSinkModel) || arg0 == null)
			return false;
		WorkloadSinkModel workloadSinkModel = (WorkloadSinkModel)arg0;
		if (this.openWorkloadModel.hasArrivesAt() && workloadSinkModel.openWorkloadModel.hasArrivesAt())
			{
			if (!(this.openWorkloadModel.getSinkName().equals(workloadSinkModel.openWorkloadModel.getSinkName())))
				return false;
			}
		else return false;
		return true;
		}

	@Override
	public List<TransitType> getTransitTypes() 
		{
		return this.openWorkloadModel.getTransitTypes();
		}

	public void setOpenWorkloadModel(OpenWorkloadModel openWorkloadModel) 
		{
		this.openWorkloadModel = openWorkloadModel;
		}

	public ITransit findSourceTransit(String string) 
		{
		return openWorkloadModel.findSourceTransit(string);
		}

	@Override
	public void setTransitTypesToNull() 
		{
		this.openWorkloadModel.setTransitTypesToNull();
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
		for (ITransit transit : this.openWorkloadModel.getSourceTransits())
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
		// dal nodo sink non è possibile cambiare
		// carico di lavoro
		}
	}
