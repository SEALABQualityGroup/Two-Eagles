package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.LoadingException;
import pack.errorManagement.SavingException;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.IWorkload;
import pack.model.schema.OpenWorkloadType;
import pack.model.schema.TransitType;

public class OpenWorkloadModel implements IWorkload {

	private static final long serialVersionUID = 1L;

	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate= new PropertyChangeSupport(this);
	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        { 
								new TextPropertyDescriptor(NAME, "Name"),
								new TextPropertyDescriptor("Workload.ArrivalRate", "Arrival Rate"),
								new TextPropertyDescriptor(TIMEUNIT, "Time Unit"),
								new TextPropertyDescriptor("Workload.ArrivesAt", "Arrives at"),
								new TextPropertyDescriptor("Workload.DepartsAt", "Departs at")
		                        };

	private transient IQNM iqnm;
	
	public static String SOURCE_NAME = "Workload.Source";
	
	public static String SINK_NAME = "Workload.Sink";
	
	public static String SOURCE_REMOVED_PROP = "OpenWorkload.SourceRemoved";
	
	public static String SINK_REMOVED_PROP = "OpenWorkload.SinkRemoved";
	
	public static String SOURCE_ADDED_PROP = "OpenWorkload.SourceAdded";
	
	public static String SINK_ADDED_PROP = "OpenWorkload.SinkAdded";
	
	private List<ITransit> sourceTransits = new ArrayList<ITransit>();
	
	private transient List<ITransit> targetTransits = new ArrayList<ITransit>();
	
	private WorkloadSourceModel workloadSourceModel;
	
	private WorkloadSinkModel workloadSinkModel;
	
	// i seguenti attributi derivano dall'xml
	
	private String workloadName;
	
	private transient Float arrivalRate;
	
	private transient TimeUnitsModel timeUnits;
	
	private transient String arrivesAt;
	
	private transient String departsAt;
	
	// i seguenti attributi vengono utilizzati per il caricamento
	// PMIF
	private transient List<TransitType> transits;
	
	
	public OpenWorkloadModel(OpenWorkloadType openWorkloadType)
		throws LoadingException
		{
		if (openWorkloadType.hasWorkloadName())
			try {
				this.workloadName = openWorkloadType.getWorkloadNameAt(0).asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		// si caricano i transits
		transits = new ArrayList<TransitType>();
		if (openWorkloadType.getTransitCount() == 0)
			{
			// siamo nel caso in cui il carico di lavoro non ha transits associati
			Finestra.mostraLE("XML Loading error for "+this.workloadName+" workload: " +
				"have not transits");
			}
		for (int i = 0; i < openWorkloadType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = openWorkloadType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			transits.add(transitType);
			}
		}
	
	// il seguente comando viene utilizzato per l'inserimento di un carico di lavoro aperto
	// tramite azione
	public OpenWorkloadModel(IQNM iqnm)
		{
		this.iqnm = iqnm;
		this.workloadName = "";
		}

	@Override
	public IQNM getQNM() 
		{
		return iqnm;
		}

	@Override
	public List<IRequest> getRequests() 
		{
		String string;
		string = workloadName;
		return getQNM().getChildrenRequest(string);
		}

	public void addSourceTransit(ITransit transit) 
		{
		sourceTransits.add(transit);
		}

	@Override
	public List<ITransit> getSourceTransits() 
		{
		return sourceTransits;
		}

	public List<ITransit> getTargetTransits() 
		{
		return targetTransits;
		}

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

	public void removeSourceTransit(ITransit transit) 
		{
		sourceTransits.remove(transit);
		}

	public void setQNM(IQNM iqnm) 
		{
		this.iqnm = iqnm;
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
		if (NAME.equals(id))
			{
			if (this.workloadName != null)
				return this.workloadName;
			else
				return "";
			}
		if ("Workload.ArrivalRate".equals(id))
			{
			if (this.arrivalRate != null)
				return this.arrivalRate.toString();
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
		if ("Workload.ArrivesAt".equals(id))
			{
			if (this.arrivesAt != null)
				return this.arrivesAt;
			else
				return "";
			}
		if ("Workload.DepartsAt".equals(id))
			{
			if (this.departsAt != null)
				return this.departsAt;
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
			if (NAME.equals(id))
				{
				this.workloadName = value.toString();
				if (this.iqnm.duplicateWorkloadFromPMIF(value.toString()))
					{
					// siamo nel caso in cui sia stato impostato un nome di workload già
					// esistente
					Finestra.mostraIE("Setting error: workload name "+this.workloadName+" already exists");
					}
				// si aggiornano tutte le richieste relative a questo carico
				// di lavoro
				List<IRequest> list = this.iqnm.getChildrenRequest(this.workloadName);
				for (IRequest request : list)
					request.setWorkloadName(value.toString());
				// si aggiorna il nome di workload
				this.workloadName = value.toString();
				// si notifica il cambio del nome del carico di lavoro
				firePropertyChange(NAME, null, value);
				}
			else if ("Workload.ArrivalRate".equals(id))
				{
				try {
					Float float1 = new Float(value.toString());
					if (float1 < 0)
						{
						// siamo nel caso in cui il tasso di arrivo è negativo
						Finestra.mostraIE("Property setting error: the "
							+getWorkloadName()+" workload has negative arrival rate");
						}
					this.arrivalRate = float1;
					// si notifica il cambio di proprietà
					firePropertyChange("Workload.ArrivalRate", null, value);
					}
				catch (NumberFormatException e)
					{
					// corrisponde al caso in cui il tasso di arrivo non è un float
					Finestra.mostraIE("Property setting error: the "
						+getWorkloadName()+" workload has not float arrival rate");
					}
				}
			else if (TIMEUNIT.equals(id))
				{
				try {
					TimeUnitsModel timeUnitsModel = TimeUnitsModel.valueOf(value.toString()); 
					this.timeUnits = timeUnitsModel;
					// si notifica il cambio di proprietà
					firePropertyChange(TIMEUNIT, null, value);
					}
				catch (IllegalArgumentException e) 
					{
					// corrisponde al caso in cui il valore impostato per l'unità di tempo non appartiene
					// ad uno dei valori dell'enumerazione TimeUnitsModel
					Finestra.mostraIE("Property setting error: the "+getWorkloadName()+
						" workload has not time units between: "+
						TimeUnitsModel.stampa());
					}
				}
			else if ("Workload.ArrivesAt".equals(id))
				{
				if (!this.iqnm.existsNodeFromPMIF(value.toString()))
					{
					// corrisponde al caso in cui il nodo sorgente non corrisponde
					// a nessun nodo
					Finestra.mostraIE("Property setting error for "+getWorkloadName()+
							" workload: the source node "+value.toString()+" don't exists");
					}
				this.arrivesAt = value.toString();
				// si imposta il modello per il nodo sorgente se tale modello è null
				if (getWorkloadSourceModel() == null)
					{
					INode source = this.iqnm.getStructureNodeFromName(this.arrivesAt);
					Point point = source.getLocation();
					WorkloadSourceModel workloadSourceModel = new WorkloadSourceModel(this,point);
					setSourceModel(workloadSourceModel);
					}
				// si notifica il cambio del nodo sorgente
				firePropertyChange(SOURCE_NAME, null, value);
				}
			else if ("Workload.DepartsAt".equals(id))
				{
				if (!this.iqnm.existsNodeFromPMIF(value.toString()))
					{
					// corrisponde al caso in cui il nodo pozzo è indefinito
					Finestra.mostraLE("Property setting error for "+getWorkloadName()+
							" workload: the sink node "+value.toString()+" don't exists");
					}
				this.departsAt = value.toString();
				// si imposta il modello per il nodo pozzo se tale modello è null
				if (getWorkloadSinkModel() == null)
					{
					INode sink = this.iqnm.getStructureNodeFromName(this.departsAt);
					Point point = sink.getLocation();
					WorkloadSinkModel workloadSinkModel = new WorkloadSinkModel(this,point);
					setSinkModel(workloadSinkModel);
					}
				// si notifica il cambio del nodo pozzo
				firePropertyChange(SINK_NAME, null, value);
				}
			else new IllegalArgumentException();
			}
		catch (Exception exception)
			{}
		}
	
	@Override
	public String getWorkloadName() 
		{
		return this.workloadName;
		}
		
	public String getSourceName()
		{
		return this.arrivesAt;
		}
	
	public String getSinkName()
		{
		return this.departsAt;
		}
	
	public void setSourceName(String string)
		{
		this.departsAt = string;
		}
	
	public void setSinkName(String string)
		{
		this.arrivesAt = string;
		}

	public void setSourceTransits(List<ITransit> list) 
		{
		this.sourceTransits = list;
		}

	@Override
	public void setTargetTransits(List<ITransit> list) 
		{
		this.targetTransits = list;
		}

	// viene utilizzato per il caricamento di un file xml
	public OpenWorkloadType getXMLmodel()
		throws LoadingException
		{
		OpenWorkloadType openWorkloadType = new OpenWorkloadType();
		if (this.arrivalRate != null)
			try {
				openWorkloadType.addArrivalRate(this.arrivalRate.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.arrivesAt != null)
			try {
				openWorkloadType.addArrivesAt(this.arrivesAt);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.departsAt != null)
			try {
				openWorkloadType.addDepartsAt(this.departsAt);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.timeUnits != null)
			try {
				openWorkloadType.addTimeUnits(this.timeUnits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.workloadName != null)
			try {
				openWorkloadType.addWorkloadName(this.workloadName);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		for (TransitType transitType : this.transits)
			{
			openWorkloadType.addTransit(transitType);
			}
		return openWorkloadType;
		}

	public void setSourceModel(WorkloadSourceModel workloadSourceModel2) 
		{
		this.workloadSourceModel = workloadSourceModel2;
		}

	public void setSinkModel(WorkloadSinkModel workloadSinkModel2) 
		{
		this.workloadSinkModel = workloadSinkModel2;
		}

	public boolean hasArrivesAt() 
		{
		return this.arrivesAt != null;
		}

	public WorkloadSourceModel getWorkloadSourceModel() 
		{
		return workloadSourceModel;
		}

	public WorkloadSinkModel getWorkloadSinkModel() 
		{
		return workloadSinkModel;
		}

	public boolean hasDepartsAt() 
		{
		return this.departsAt != null;
		}

	public OpenWorkloadType generaDom() throws SavingException
		{
		OpenWorkloadType openWorkloadType = new OpenWorkloadType();
		// dapprima si generano gli attributi
		if (this.workloadName != null)
			{
			try {
				openWorkloadType.addWorkloadName(this.workloadName);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il carico di lavoro non ha un nome
			Finestra.mostraSE("Saving error: open workload without name");
		if (this.arrivalRate != null)
			{
			try {
				openWorkloadType.addArrivalRate(this.arrivalRate.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il carico di lavoro aperto non ha un tasso di arrivo
			Finestra.mostraSE("Saving error for "+getWorkloadName()+" workload: " +
					"have not arrival rate");
		// timeUnits è un campo facoltativo
		if (this.timeUnits != null)
			{
			try {
				openWorkloadType.addTimeUnits(this.timeUnits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.arrivesAt != null)
			{
			try {
				openWorkloadType.addArrivesAt(this.arrivesAt);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il carico di lavoro aperto non ha la sorgente di job
			Finestra.mostraSE("Saving error: the "+getWorkloadName()+" workload has not source node");
		if (this.departsAt != null)
			{
			try {
				openWorkloadType.addDepartsAt(this.departsAt);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il carico di lavoro aperto non ha il nodo pozzo
			Finestra.mostraSE("Saving error: the "+getWorkloadName()+" workload has not sink node");		
		// successivamente si generano gli elementi associati
		if (this.sourceTransits.size() == 0)
			{
			// siamo nel caso in cui non ci sono transit associati
			Finestra.mostraSW("Saving error for "+getWorkloadName()+" workload: " +
					"has not transits");
			}
		else
			{
			for (ITransit transit : this.sourceTransits)
				{
				TransitType transitType = transit.generaDom();
				openWorkloadType.addTransit(transitType);
				}
			}
		return openWorkloadType;
		}

	public void removeTargetTransit(ITransit transitModel) 
		{
		this.targetTransits.remove(transitModel);
		}

	public void addTargetTransit(ITransit transitModel) 
		{
		this.targetTransits.add(transitModel);
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof OpenWorkloadModel) || arg0 == null)
			return false;
		OpenWorkloadModel openWorkloadModel = (OpenWorkloadModel)arg0;
		if (this.workloadName != null && openWorkloadModel.workloadName != null)
			{
			if (!(this.workloadName.equals(openWorkloadModel.workloadName)))
				return false;
			}
		else return false;
		return true;
		}

	public List<TransitType> getTransitTypes() 
		{
		return transits;
		}

	public void setArrivalRate(Float arrivalRate) 
		{
		this.arrivalRate = arrivalRate;
		}

	public void setTimeUnits(TimeUnitsModel timeUnits) 
		{
		this.timeUnits = timeUnits;
		}

	public void setArrivesAt(String arrivesAt) 
		{
		this.arrivesAt = arrivesAt;
		}

	public void setDepartsAt(String departsAt) 
		{
		this.departsAt = departsAt;
		}

	public void sinkPMIFLoading() 
		{
		this.workloadSinkModel.setOpenWorkloadModel(this);
		this.workloadSinkModel.setPcsDelegate(new PropertyChangeSupport(this.workloadSinkModel));
		}

	public void sourcePMIFLoading() 
		{
		this.workloadSourceModel.setOpenWorkloadModel(this);
		this.workloadSourceModel.setPcsDelegate(new PropertyChangeSupport(this.workloadSourceModel));
		}
	
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

	public void setTransitTypesToNull() 
		{
		this.transits = null;
		}
	}
