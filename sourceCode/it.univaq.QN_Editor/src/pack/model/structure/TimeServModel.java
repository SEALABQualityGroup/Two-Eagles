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
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.IWorkload;
import pack.model.schema.TimeServType;
import pack.model.schema.TransitType;

public class TimeServModel implements IRequest {

	private static final long serialVersionUID = 1L;
	
	private Point point;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        { 
								new TextPropertyDescriptor(XPOS_PROP, "X"), // id and description pair
								new TextPropertyDescriptor(YPOS_PROP, "Y"),
								new TextPropertyDescriptor(WORKLOAD, "Workload Name"),
								new TextPropertyDescriptor(NAME, "Server Name"),
								new TextPropertyDescriptor(TIMEUNIT, "Time Units"),
								new TextPropertyDescriptor("Workload.Time", "Service Time"),	
								new TextPropertyDescriptor(VISITS, "Number of visits")
		                        };

	private transient IQNM iqnm;
	
	private List<ITransit> sourceTransits = new ArrayList<ITransit>();
	
	private transient List<ITransit> targetTransits = new ArrayList<ITransit>();
	
	private transient Etichetta etichetta;
	
	// i seguenti attributi si riferiscono all'xml
	private String workloadName;
	
	private String serverID = "";
	
	private transient TimeUnitsModel timeUnits;
	
	private transient Float serviceTime;
	
	private transient BigInteger numberOfVisits;
	
	// i seguenti attributi vengono utilizzati durante il caricamento PMIF
	private transient List<TransitType> transits;
	
	// il seguente costruttore viene utilizzato per l'inserimento 
	// di una nuova richiesta di servizio
	public TimeServModel(IWorkload workload) 
		{
		super();
		this.iqnm = workload.getQNM();
		this.workloadName = workload.getWorkloadName();
		}
	
	public TimeServModel(TimeServType timeServType, Point point, IQNM iqnm)
		throws LoadingException
		{
		this.iqnm = iqnm;
		this.point = point;
		if (timeServType.hasServerID())
			{
			String string = null;
			try {
				string = timeServType.getServerID().asString();
				this.serverID = string; 
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (!this.iqnm.existsNodeFromXML(string))
				{
				Finestra.mostraLE("XML Loading error: the node "+string+" don't exists");
				}
			}
		else
			{
			// siamo nel caso in cui non ci sia un riferimento ad un nodo
			Finestra.mostraLE("XML Loading error: time service request without node id");
			}	
		if (timeServType.hasWorkloadName())
			{
			String wname = null;
			try {
				wname = timeServType.getWorkloadName().asString();
				this.workloadName = wname;
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (!this.iqnm.existsWorkloadFromXML(wname))
				{
				Finestra.mostraLE("XML Loading error: the workload name "+wname+" of the "+
						this.serverID+" service request don't exists");
				}
			}
		else
			{
			// caso in cui la richiesta di servizio non referenzia nessun carico di lavoro
			Finestra.mostraLE("XML Loading error: time service request without workload id");
			}	
		this.transits = new ArrayList<TransitType>();
		if (timeServType.getTransitCount() == 0)
			{
			Finestra.mostraLE("XML Loading error: the service request with server equal to "+this.serverID+
				" and workload equal to "+this.workloadName+" has no transits");
			}
		for (int i = 0; i < timeServType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = timeServType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			this.transits.add(transitType);
			}
		}

	@Override
	public void addSourceTransit(ITransit value) 
		{
		sourceTransits.add(value);
		firePropertyChange(SOURCE_TRANSIT_PROP, null, value);
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
	public IQNM getQNM() 
		{
		return iqnm;
		}

	@Override
	public String getServerID() 
		{
		return this.serverID;
		}

	@Override
	public List<ITransit> getSourceTransits() 
		{
		return sourceTransits;
		}

	@Override
	public List<ITransit> getTargetTransits() 
		{
		return targetTransits;
		}

	@Override
	public String getWorkloadName() 
		{
		return this.workloadName;
		}

	@Override
	public boolean hasTransit(ITransit transit) 
		{
		for (int i = 0; i < sourceTransits.size(); i++)
			{
			ITransit transit2 = sourceTransits.get(i);
			if (transit2.equals(transit))
				return true;
			}
		return false;
		}

	@Override
	public void removeSourceTransit(ITransit transit) 
		{
		sourceTransits.remove(transit);
		// si notifica il cambio
		firePropertyChange(SOURCE_TRANSIT_PROP, null, transit);
		}

	@Override
	public Point getLocation() 
		{
		return point.getCopy();
		}

	@Override
	public void setPoint(Point point) 
		{
		this.point = point.getCopy();
		}

	@Override
	public void setQNM(IQNM iqnm) 
		{
		this.iqnm = iqnm;
		}

	@Override
	public void setServerID(String string) 
		{
		this.serverID = string;
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
		if (WORKLOAD.equals(id))
			{
			if (this.workloadName != null)
				return this.workloadName;
			else
				return "";
			}
		if (NAME.equals(id))
			{
			if (this.serverID != null)
				return this.serverID;
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
		if ("Workload.Time".equals(id))
			{
			if (this.serviceTime != null)
				return this.serviceTime.toString();
			else
				return "";
			}
		if (VISITS.equals(id))
			{
			if (this.numberOfVisits != null)
				return this.numberOfVisits.toString();
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
				setLocation(new Point(x, point.y));
				} 
			else if (YPOS_PROP.equals(id)) 
				{
				int y = Integer.parseInt((String) value);
				setLocation(new Point(point.x, y));
				}
			else if (WORKLOAD.equals(id))
				{
				if (!this.iqnm.existsWorkloadFromPMIF(value.toString()))
					{
					// siamo nel caso in cui il nuovo workload non esiste
					Finestra.mostraIE("Setting error: the "+value.toString()+" workload don't exists");
					}
				this.workloadName = value.toString();
				// si notifica il cambio del carico di lavoro
				firePropertyChange(IRequest.WORKLOAD, null, value);
				}
			else if (NAME.equals(id))
				{
				if (!this.iqnm.existsNodeFromPMIF(value.toString()))
					{
					// siamo nel caso in cui il nome del server nuovo non referenzia nessun nodo
					Finestra.mostraIE("Setting error: the "+value.toString()+" node don't exists");
					}
				this.serverID = value.toString();
				// si notifica il cambio di nome
				firePropertyChange(IRequest.NAME, null, value);
				}
			else if (TIMEUNIT.equals(id))
				{
				try {
					this.timeUnits = TimeUnitsModel.valueOf(value.toString());
					// si notifica il cambio delle unità di tempo
					firePropertyChange(IRequest.TIMEUNIT, null, value);
					} 
				catch (IllegalArgumentException e) 
					{
					// corrisponde al caso in cui il valore impostato per l'unità di tempo non appartiene
					// ad uno dei valori dell'enumerazione TimeUnitsModel
					Finestra.mostraIE("Setting error: the service demand with server equal to "+
							this.serverID+" and workload equal to "+this.workloadName+
							" has not time units between: "+
							TimeUnitsModel.stampa());
					}
				}
			else if ("Workload.Time".equals(id))
				{
				try {
					Float float1 = new Float(value.toString()); 
					if (float1.floatValue() < 0)
						{
						// corrisponde al caso in cui il tempo di servizio è negativo
						String string = getServerID();
						String string2 = getWorkloadName();
						Finestra.mostraIE("Setting error: the service request with server equal to "+
								string+" and workload equal to "+string2+" has not valid service time");
						}
					this.serviceTime = float1;
					// si notifica il cambio del tempo di servizio
					firePropertyChange("Workload.Time", null, value);
					} 
				catch (NumberFormatException e) 
					{
					// corrisponde al caso in cui il tempo di servizio non è un float
					String string = getServerID();
					String string2 = getWorkloadName();
					Finestra.mostraIE("Setting error: the service request with server equal to "+
						string+" and workload equal to "+string2+" has not valid service time");
					} 
				}
			else if (VISITS.equals(id))
				{
				try {
					BigInteger bigInteger = new BigInteger(value.toString()); 
					if (bigInteger.compareTo(new BigInteger("0")) < 0)
						{
						// corrisponde al caso in cui il numero di visite non è positivo
						String string = getServerID();
						String string2 = getWorkloadName();
						Finestra.mostraIE("Setting error: the service request with server equal to "+string+
							" and workload equal to "+
							string2+" has not valid number of visits: "+this.numberOfVisits.toString());
						}
					this.numberOfVisits = bigInteger; 
					// si notifica il cambio di proprietà
					firePropertyChange(VISITS, null, value);
					}
				catch (NumberFormatException e) 
					{
					// corrisponde al caso in cui il numero di visite non è un BigInteger
					String string = getServerID();
					String string2 = getWorkloadName();
					Finestra.mostraIE("Setting error: the service request with server equal to "+string+
						" and workload equal to "+
						string2+" has not valid number of visits: "+
						value.toString());
					}
				}
			else Finestra.mostraIE("bad property");
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void setSourceTransits(List<ITransit> list) 
		{
		this.sourceTransits = list;
		}

	@Override
	public void setTargetTransits(List<ITransit> list) 
		{
		this.targetTransits = list;
		}

	public TimeServType getXMLmodel()
		throws LoadingException
		{
		TimeServType timeServType = new TimeServType();
		if (this.numberOfVisits != null)
			try {
				timeServType.addNumberOfVisits(this.numberOfVisits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.serverID != null)
			try {
				timeServType.addServerID(this.serverID);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.serviceTime != null)
			try {
				timeServType.addServiceTime(this.serviceTime.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.timeUnits != null)
			try {
				timeServType.addTimeUnits(this.timeUnits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.workloadName != null)
			try {
				timeServType.addWorkloadName(this.workloadName);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		for (TransitType transitType : this.transits)
			{
			timeServType.addTransit(transitType);
			}
		return timeServType;
		}

	public TimeServType generaDom() throws SavingException
		{
		// si generano dapprima gli attibuti
		TimeServType timeServType = new TimeServType();
		if (this.workloadName != null)
			{
			try {
				timeServType.addWorkloadName(this.workloadName);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// caso in cui la richiesta di servizio non referenzia nessun carico di lavoro
			Finestra.mostraSE("Saving error: time service request without workload id");
		if (this.serverID != null)
			{
			try {
				timeServType.addServerID(this.serverID);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui non ci sia un riferimento ad un nodo
			Finestra.mostraSE("Saving error: time service request without node id");
		// timeUnits è un campo facoltativo
		if (this.timeUnits != null)
			{
			try {
				timeServType.addTimeUnits(this.timeUnits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.serviceTime != null)
			{
			try {
				timeServType.addServiceTime(this.serviceTime.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui la richiesta di servizio non
			// ha tempo di servizio
			Finestra.mostraSE("Saving error: the service request with server equal to "+getServerID()+
					" and workload equal to "+
				getWorkloadName()+" has not a service time");
		// numberOfVisits è un campo facoltativo
		if (this.numberOfVisits != null)
			{
			try {
				timeServType.addNumberOfVisits(this.numberOfVisits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.sourceTransits.size() == 0)
			{
			// corrisponde al caso in cui la richiesta di servizio non ha transit
			Finestra.mostraSE("Saving error: the service request with server equal to "+this.serverID+
				" and workload equal to "+this.workloadName+" has no transits");
			}
		else
			{
			// si generano gli elementi associati
			for (ITransit transit : this.sourceTransits)
				{
				TransitType transitType = transit.generaDom();
				timeServType.addTransit(transitType);
				}
			}
		return timeServType;
		}

	@Override
	public void removeTargetTransit(ITransit transitModel) 
		{
		this.targetTransits.remove(transitModel);
		// si notifica il cambio
		firePropertyChange(IRequest.TARGET_TRANSIT_PROP, null, transitModel);
		}

	@Override
	public void addTargetTransit(ITransit transitModel) 
		{
		this.targetTransits.add(transitModel);
		// si notifica il cambio
		firePropertyChange(IRequest.TARGET_TRANSIT_PROP, null, transitModel);
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof TimeServModel) || arg0 == null)
			return false;
		TimeServModel timeServModel = (TimeServModel)arg0;
		if (this.serverID != null && timeServModel.serverID != null)
			{
			if (!(this.serverID.equals(timeServModel.serverID)))
				return false;
			}
		else return false;
		if (this.workloadName != null && timeServModel.workloadName != null)
			{
			if (!(this.workloadName.equals(timeServModel.workloadName)))
				return false;
			}
		else return false;
		return true;
		}

	@Override
	public List<TransitType> getTransitTypes() 
		{
		return this.transits;
		}

	public void setTimeUnits(TimeUnitsModel timeUnits) 
		{
		this.timeUnits = timeUnits;
		}

	public void setServiceTime(Float serviceTime) 
		{
		this.serviceTime = serviceTime;
		}
	
	public void setNumberOfVisits(BigInteger numberOfVisits) 
		{
		this.numberOfVisits = numberOfVisits;
		}

	@Override
	public ITransit findSourceTransit(String string) 
		{
		for (ITransit transit : this.sourceTransits)
			{
			String string2 = transit.getTo();
			if (string.equals(string2))
				return transit;
			}
		return null;
		}

	@Override
	public void setTransitTypesToNull() 
		{
		this.transits = null;
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
		for (ITransit transit : this.sourceTransits)
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
		this.workloadName = string;
		}
	}
