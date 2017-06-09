package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.LoadingException;
import pack.errorManagement.SavingException;
import pack.figures.Etichetta;
import pack.model.IArc;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.schema.WorkUnitServerType;

public class WorkUnitServerModel implements INode {

	private static final long serialVersionUID = 1L;

	private Point point;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        { 
								new TextPropertyDescriptor(XPOS_PROP, "X"), // id and description pair
								new TextPropertyDescriptor(YPOS_PROP, "Y"),
								new TextPropertyDescriptor(NAME, "Name"),
								new TextPropertyDescriptor(QUANTITY, "Quantity"),
								new TextPropertyDescriptor(SCHEDULING, "Scheduling policy"),
								new TextPropertyDescriptor(TIMEUNIT, "Time Units"),
								new TextPropertyDescriptor("WorkUnitServer.ServiceTime","Service Time")
		                        };
	
	private transient List<IArc> sourceArcs = new ArrayList<IArc>();
	
	private transient List<IArc> targetArcs = new ArrayList<IArc>();
	
	private transient Etichetta etichetta;
	
	private transient IQNM iqnm;
	
	// i seguenti attributi si riferiscono all'xml
	
	private String name = "";
	
	private transient BigInteger quantity;
	
	private transient SchedulingModel schedulingPolicy;
	
	private transient TimeUnitsModel timeUnits;
	
	// bisogna effettuare i controlli di positività
	// sul tempo di servizio
	private transient Float serviceTime;
	
	// il seguente costruttore viene utilizzato per l'inserimento di un nuovo nodo
	public WorkUnitServerModel() 
		{
		super();
		}

	public WorkUnitServerModel(WorkUnitServerType workUnitServerType2, Point point, IQNM iqnm)
		throws LoadingException
		{
		this.iqnm = iqnm;
		if (workUnitServerType2.hasName())
			{
			String string = null;
			try {
				string = workUnitServerType2.getName().asString(); 
				this.name = string;
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (this.iqnm.duplicateNodeFromXML(string))
				{
				// siamo nel caso in cui abbiamo un duplicato nel nome del nodo
				Finestra.mostraLE("Loading error: nodes with duplicate names");
				}
			}
		else
			{
			// siamo nel caso in cui il server non ha un nome
			Finestra.mostraLE("Loading error: server without name");
			}
		this.point = point;
		}

	@Override
	public void addSourceArc(IArc arc) 
		{
		sourceArcs.add(arc);
		// si notifica il cambio
		firePropertyChange(SOURCE_ARCS_PROP, null, arc);
		}

	@Override
	public Point getLocation() 
		{
		return point.getCopy();
		}

	@Override
	public String getServerName() 
		{
		String string;
		string = this.name;
		return string;
		}

	@Override
	public List<IArc> getSourceArcs() 
		{
		return sourceArcs;
		}

	@Override
	public List<IArc> getTargetArcs() 
		{
		return targetArcs;
		}

	@Override
	public void removeSourceArc(IArc arc) 
		{
		sourceArcs.remove(arc);
		// si notifica il cambio
		firePropertyChange(SOURCE_ARCS_PROP, null, arc);
		}

	@Override
	public void setLocation(Point location) throws Exception 
		{
		if (location == null) 
			{
			Finestra.mostraIE("location is null");
			}
		if (point == null )
			point = location.getCopy();
		else
			point.setLocation(location.getCopy());
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
			{
			return Integer.toString(point.x);
			}
		if (YPOS_PROP.equals(id)) 
			{
			return Integer.toString(point.y);
			}
		if (NAME.equals(id))
			{
			if (this.name != null)
				return this.name;
			else
				return "";
			}
		if (QUANTITY.equals(id))
			{
			if (this.quantity != null)
				return this.quantity.toString();
			else
				return "";
			}
		if (SCHEDULING.equals(id))
			{
			if (this.schedulingPolicy != null)
				return this.schedulingPolicy.toString();
			else
				return "";
			}
		if (TIMEUNIT.equals(id))
			{
			if (this.timeUnits != null)
				return this.timeUnits.toString();
			else
				return "";
			}
		if ("WorkUnitServer.ServiceTime".equals(id))
			{
			if (this.serviceTime != null)
				return this.serviceTime.toString();
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
			if (XPOS_PROP.equals(id)) 
				{
				int x = Integer.parseInt((String) value);
				// si imposta la nuova posizione e lo si notifica
				setLocation(new Point(x, point.y));
				} 
			else if (YPOS_PROP.equals(id)) 
				{
				int y = Integer.parseInt((String) value);
				// si imposta la nuova posizione e lo si notifica
				setLocation(new Point(point.x, y));
				}
			else if (NAME.equals(id))
				{
				String oldName = this.name;
				this.name = value.toString();
				if (this.iqnm.duplicateNodeFromPMIF(this.name))
					{
					// siamo nel caso in cui ci sono due nodi con lo stesso nome
					Finestra.mostraIE("Setting error: node name "+this.name+" already exists");
					}
				// si aggiornano le richieste relative e gli archi in cui risulta
				// essere un'estremo
				updateNameElements(oldName, this.name);
				// si notifica il cambio di nome
				firePropertyChange(INode.NAME, null, value);
				}
			else if (QUANTITY.equals(id))
				{
				BigInteger bigInteger = new BigInteger(value.toString());
				if (bigInteger.compareTo(new BigInteger("0")) < 0)
					{
					Finestra.mostraIE("Setting error: the "+getServerName()
						+" server has negative quantity");
					}
				this.quantity = bigInteger;
				// si notifica la proprietà di cambio
				firePropertyChange(QUANTITY, null, value);
				}
			else if (SCHEDULING.equals(id))
				{
				try {
					this.schedulingPolicy = SchedulingModel.valueOf(value.toString());
					// si notifica la proprietà di cambio
					firePropertyChange(SCHEDULING, null, value);
					}
				catch (IllegalArgumentException e) 
					{
					// corrisponde al caso in cui il valore impostato per la politica di scheduling
					// non appartiene
					// ad uno dei valori dell'enumerazione SchedulingModel
					Finestra.mostraIE("Setting error: the server node with name equal to "+
							this.name+" has not scheduling policy between: "+
							SchedulingModel.stampa());
					} 
				}
			else if (TIMEUNIT.equals(id))
				{
				try {
					this.timeUnits = TimeUnitsModel.valueOf(value.toString());
					// si notifica il cambio dell'unità di tempo
					firePropertyChange(INode.TIMEUNIT, null, value);
					}
				catch (IllegalArgumentException e) 
					{
					// corrisponde al caso in cui l'unità di tempo non appartiene ad uno dei valori
					// dell'enumerazione TimeUnitsModel
					Finestra.mostraIE("Setting error: the server "+
							getServerName()+
							" has not time units between: "+
							TimeUnitsModel.stampa());
					}
				}
			else if ("WorkUnitServer.ServiceTime".equals(id))
				{
				try {
					Float float1 = new Float(value.toString()); 
					if (float1.floatValue() < 0)
						{
						// corrisponde al caso in cui il tempo di servizio sia minore di zero
						Finestra.mostraIE("Setting error: the server node with name equal to "+
								getServerName()+" has not valid service time: "+
								float1.toString());
						}
					setServiceTime(float1);
					} 
				catch (NumberFormatException e) 
					{
					// corrisponde al caso in cui il tempo di servizio non sia un float
					Finestra.mostraIE("Setting error: the server node with name equal to "+
						getServerName()+" has not valid service time: "+
						value.toString());
					} 
				this.serviceTime = new Float(value.toString());
				// si notifica il cambio del tempo di servizio
				firePropertyChange("WorkUnitServer.ServiceTime", null, value);
				}
			else Finestra.mostraIE("bad property");
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void setSourceArcs(List<IArc> list) 
		{
		this.sourceArcs = list;
		}

	@Override
	public void setTargetArcs(List<IArc> list) 
		{
		this.targetArcs = list;
		}

	public WorkUnitServerType generaDom() throws SavingException
		{
		WorkUnitServerType workUnitServerType = new WorkUnitServerType();
		// si generano gli attributi
		if (this.name != null)
			{
			try {
				workUnitServerType.addName(this.name);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il server non ha un nome
			Finestra.mostraSE("Saving error: server without name");
		if (this.quantity != null)
			{
			try {
				workUnitServerType.addQuantity(this.quantity.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il server non ha una quantità
			Finestra.mostraSE("Saving error: the "+getServerName()+" has not quantity");
		if (this.schedulingPolicy != null)
			{
			try {
				workUnitServerType.addSchedulingPolicy(this.schedulingPolicy.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il server non ha una politica di scheduling
			Finestra.mostraSE("Saving error: the "+getServerName()+" has not scheduling policy");
		// timeUnits è un campo facoltativo
		if (this.timeUnits != null)
			{
			try {
				workUnitServerType.addTimeUnits(this.timeUnits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.serviceTime != null)
			{
			try {
				workUnitServerType.addServiceTime(this.serviceTime.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// caso in cui il server non ha tempo di servizio
			Finestra.mostraSE("Saving error: the "+getServerName()+" has not "+
				"service time");
		return workUnitServerType;
		}

	@Override
	public void addTargetArc(IArc arcModel) 
		{
		this.targetArcs.add(arcModel);
		// si notifica il cambio
		firePropertyChange(INode.TARGET_ARCS_PROP, null, arcModel);
		}

	@Override
	public void removeTargetArc(IArc arcModel) 
		{
		this.targetArcs.remove(arcModel);
		// si notifica il cambio
		firePropertyChange(INode.TARGET_ARCS_PROP, null, arcModel);
		}
	
	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof WorkUnitServerModel) || arg0 == null)
			return false;
		WorkUnitServerModel workUnitServerModel = (WorkUnitServerModel)arg0;
		if (this.name != null && workUnitServerModel.name != null)
			{
			if (!(this.getServerName().equals(workUnitServerModel.getServerName())))
				return false;
			}
		else return false;
		return true;
		}

	public void setQuantity(BigInteger quantity) 
		{
		this.quantity = quantity;
		}

	public void setSchedulingPolicy(SchedulingModel schedulingPolicy) 
		{
		this.schedulingPolicy = schedulingPolicy;
		}
	
	public void setTimeUnits(TimeUnitsModel timeUnits) 
		{
		this.timeUnits = timeUnits;
		}

	public void setServiceTime(Float serviceTime) 
		{
		this.serviceTime = serviceTime;
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
	public IQNM getQNM() 
		{
		return this.iqnm;
		}

	@Override
	public void setQNM(IQNM iqnm) 
		{
		this.iqnm = iqnm;
		}

	@Override
	public void updateNameElements(String string, String string2) 
		{
		IQNM iqnm = this.iqnm;
		List<IRequest> list = iqnm.getRequestsFromNodeName(string);
		List<IArc> list2 = iqnm.getSourceArcsFromID(string);
		List<IArc> list3 = iqnm.getTargetArcsFromID(string);
		List<ITransit> list4 = iqnm.getTransitsFromTo(string);
		for (IRequest request : list)
			{
			request.setServerID(string2);
			}
		for (IArc arc : list2)
			{
			arc.setFromNode(string2);
			}
		for (IArc arc : list3)
			{
			arc.setToNode(string2);
			}
		for (ITransit transit : list4)
			{
			transit.setTo(string2);
			}
		}
	}
