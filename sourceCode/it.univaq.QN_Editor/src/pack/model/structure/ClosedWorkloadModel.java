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
import pack.model.INode;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.IWorkload;
import pack.model.schema.ClosedWorkloadType;
import pack.model.schema.TransitType;

public class ClosedWorkloadModel implements IWorkload {

	private static final long serialVersionUID = 1L;
	
	public static final String DELAY_NAME = "Workload.Delay";
	
	public static final String DELAY_REMOVED_PROP = "ClosedWorkload.Delay.Removed";
	
	public static final String DELAY_ADDED_PROP = "ClosedWorkload.Delay.Added";
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);

	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        { 
								new TextPropertyDescriptor(NAME, "Name"),
								new TextPropertyDescriptor(JOBS, "Jobs"),
								new TextPropertyDescriptor("Workload.thinkTime", "Thinking time"),
								new TextPropertyDescriptor(TIMEUNIT, "Time Unit"),
								new TextPropertyDescriptor(DELAY_NAME, "Think Device")
		                        };

	private transient IQNM iqnm;

	private WorkloadDelayModel workloadDelayModel;

	// i seguenti asono attributi dell'elemento ClosedWorkloadType
	private String workloadName;
	
	private transient BigInteger numbersOfJobs;
	
	private transient Float thinkTime;
	
	private transient TimeUnitsModel timeUnits;
	
	private transient String thinkDevice;
	
	// viene utilizzato per il caricamento da xml
	private transient List<TransitType> transits;
	
	private List<ITransit> sourceTransits = new ArrayList<ITransit>();
	
	private transient List<ITransit> targetTransits = new ArrayList<ITransit>();
		
	public ClosedWorkloadModel(ClosedWorkloadType closedWorkloadType)
		throws LoadingException
		{
		if (closedWorkloadType.hasWorkloadName())
			try {
				workloadName = closedWorkloadType.getWorkloadNameAt(0).asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		// si caricano i transits
		this.transits = new ArrayList<TransitType>();
		if (closedWorkloadType.getTransitCount() == 0)
			{
			// siamo nel caso in cui il carico di lavoro non ha transits associati
			Finestra.mostraLE("XML Loading error for "+this.workloadName+" workload: " +
					"have not transits");
			}
		for (int i = 0; i < closedWorkloadType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = closedWorkloadType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			this.transits.add(transitType);
			}
		}
	
	// il seguente comando viene utilizzato nell'inserimento di un carico di lavoro
	public ClosedWorkloadModel(IQNM iqnm)
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
		string = this.workloadName;
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
		if (JOBS.equals(id))
			{
			if (this.numbersOfJobs != null)
				return this.numbersOfJobs.toString();
			else
				return "";
			}
		if ("Workload.thinkTime".equals(id))
			{
			if (this.thinkTime != null)
				return this.thinkTime.toString();
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
		if (DELAY_NAME.equals(id))
			{
			if (this.thinkDevice != null)
				return this.thinkDevice;
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
				// si aggiorna il nome di workload
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
				// si notifica il cambio del nome del carico di lavoro
				firePropertyChange(NAME, null, value);
				}
			else if (JOBS.equals(id))
				{
				try {
					this.numbersOfJobs = new BigInteger(value.toString());
					if (this.numbersOfJobs.compareTo(new BigInteger("0")) < 0)
						{
						// corrisponde al caso in cui il numero di jobs è una quantità negativa
						Finestra.mostraIE("Property setting error: the "+getWorkloadName()+" workload has negative number of job");
						}
					// si notifica il cambio di proprietà
					firePropertyChange(JOBS, null, value);
					}
				catch (NumberFormatException e) 
					{
					// corrisponde al caso in cui il numero di jobs non è un BigInteger
					Finestra.mostraIE("Property setting error: the "+getWorkloadName()+" workload has not integer number of job");
					}
				}
			else if ("Workload.thinkTime".equals(id))
				{
				try {
					Float float1 = new Float(value.toString());
					if (float1 < 0)
						{
						// corrisponde al caso in cui il tempo di pensiero è una quantità negativa
						Finestra.mostraIE("Property setting error: the "+getWorkloadName()+" workload has negative thinking time");
						}
					this.thinkTime = float1;
					// si notifica il cambio di proprietà
					firePropertyChange("Workload.thinkTime", null, value);
					}
				catch (NumberFormatException e) 
					{
					// corrisponde al caso in cui il tempo di pensiero non è un float
					Finestra.mostraIE("Property setting error: the "+getWorkloadName()+" workload has not float thinking time");
					}
				}
			else if (TIMEUNIT.equals(id))
				{
				try {
					this.timeUnits = TimeUnitsModel.valueOf(value.toString());
					// si notifica il cambio di proprietà
					firePropertyChange(TIMEUNIT, null, value);
					}
				catch (IllegalArgumentException e) 
					{
					// corrisponde al caso in cui il valore impostato per l'unità di tempo non appartiene
					// ad uno dei valori dell'enumerazione TimeUnitsModel
					Finestra.mostraIE("Property setting error: the "+getWorkloadName()+" workload has not time units between: "+
							TimeUnitsModel.stampa());
					}
				}
			else if (DELAY_NAME.equals(id))
				{
				if (!this.iqnm.existsNodeFromPMIF(value.toString()))
					{
					// corrisponde al caso in cui il dispositivo di pensamento non corrisponde
					// a nessun nodo
					Finestra.mostraIE("Property setting error for "+getWorkloadName()+
						" workload: the thinking device "+value.toString()+" don't exists");
					}
				this.thinkDevice = value.toString();
				// si imposta il modello per il nodo di delay nel caso in cui tale modello sia null
				if (getDelay() == null)
					{
					INode delay = this.iqnm.getStructureNodeFromName(this.thinkDevice);
					Point point = delay.getLocation();
					WorkloadDelayModel workloadDelayModel = new WorkloadDelayModel(this,point);
					setDelayModel(workloadDelayModel);
					}
				// si notifica il cambio del dispositivo di ritardo
				firePropertyChange(DELAY_NAME, null, value);
				}
			else 
				Finestra.mostraIE("bad property");
			}
		catch (Exception exception)
			{}
		}
	
	@Override
	public String getWorkloadName() 
		{
		return this.workloadName;
		}

	public String getThinkDevice() 
		{
		return this.thinkDevice;
		}
	
	public void setThinkDevice(String string)
		{
		this.thinkDevice = string;
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
	
	// viene utilizzato per generare l'elemento dom su cui è stato costrutito
	// questo oggetto
	public ClosedWorkloadType getXMLmodel()
		throws LoadingException
		{
		ClosedWorkloadType closedWorkloadType = new ClosedWorkloadType();
		if (this.numbersOfJobs != null)
			try {
				closedWorkloadType.addNumberOfJobs(this.numbersOfJobs.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.thinkDevice != null)
			try {
				closedWorkloadType.addThinkDevice(this.thinkDevice);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.thinkTime != null)
			try {
				closedWorkloadType.addThinkTime(this.thinkTime.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.timeUnits != null)
			try {
				closedWorkloadType.addTimeUnits(this.timeUnits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (this.workloadName != null)
			try {
				closedWorkloadType.addWorkloadName(this.workloadName);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		for (TransitType transitType : this.transits)
			{
			closedWorkloadType.addTransit(transitType);
			}
		return closedWorkloadType;
		}

	public void setDelayModel(WorkloadDelayModel workloadDelayModel2) 
		{
		this.workloadDelayModel = workloadDelayModel2;
		}

	public boolean hasThinkDevice() 
		{
		return this.workloadDelayModel != null;
		}

	// viene utilizzato per generare l'elemento dom che corrisponde a questo oggetto
	// e viene utilizzato per la serializzazione
	public ClosedWorkloadType generaDom() throws SavingException
		{
		ClosedWorkloadType closedWorkloadType = new ClosedWorkloadType();
		// si generano prima gli attributi
		if (this.workloadName != null)
			{
			try {
				closedWorkloadType.addWorkloadName(this.workloadName);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			Finestra.mostraSE("Saving error: workload without name");
		if (this.numbersOfJobs != null)
			{
			try {
				closedWorkloadType.addNumberOfJobs(this.numbersOfJobs.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il carico di lavoro non ha un numero di jobs associato
			Finestra.mostraSE("Saving error for "+getWorkloadName()+" workload: " +
					"have not number of jobs");
		if (this.thinkTime != null)
			{
			try {
				closedWorkloadType.addThinkTime(this.thinkTime.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il carico di lavoro non ha un tempo di pensiero associato
			Finestra.mostraSE("Saving error for "+getWorkloadName()+" workload: " +
				"have not thinking time");
		// timeUnits è un attributo facoltativo
		if (this.timeUnits != null)
			{
			try {
				closedWorkloadType.addTimeUnits(this.timeUnits.toString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.thinkDevice != null)
			{
			try {
				closedWorkloadType.addThinkDevice(this.thinkDevice);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// corrisponde al caso in cui non è stato impostato un dispositivo di ritardo
			Finestra.mostraSE("Saving error:"+
				" the thinking device for "+getWorkloadName()+" workload don't setted");
		// si generano successivamente gli elementi associati
		if (this.sourceTransits.size() == 0)
			{
			// siamo nel caso in cui non ci sono transit associati
			Finestra.mostraSW("Saving error for "+getWorkloadName()+" workload: " +
					"have not transits");
			}
		else
			{
			for (ITransit transit : this.sourceTransits)
				{
				TransitType transitType = transit.generaDom();
				closedWorkloadType.addTransit(transitType);
				}
			}
		return closedWorkloadType;
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
		if (!(arg0 instanceof ClosedWorkloadModel) || arg0 == null)
			return false;
		ClosedWorkloadModel closedWorkloadModel = (ClosedWorkloadModel)arg0;
		if (this.workloadName != null && closedWorkloadModel.workloadName != null)
			{
			if (!(this.workloadName.equals(closedWorkloadModel.workloadName)))
				return false;
			}
		else return false;
		return true;
		}
	
	public WorkloadDelayModel getDelay()
		{
		return this.workloadDelayModel;
		}

	public List<TransitType> getTransitTypes() 
		{
		return this.transits;
		}

	public void setNumbersOfJobs(BigInteger numbersOfJobs) 
		{
		this.numbersOfJobs = numbersOfJobs;
		}

	public void setThinkTime(Float thinkTime) 
		{
		this.thinkTime = thinkTime;
		}

	public void setTimeUnits(TimeUnitsModel timeUnits) 
		{
		this.timeUnits = timeUnits;
		}

	public void delayPMIFLoading() 
		{
		this.workloadDelayModel.setClosedWorkloadModel(this);
		this.workloadDelayModel.setPcsDelegate(new PropertyChangeSupport(this.workloadDelayModel));
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
