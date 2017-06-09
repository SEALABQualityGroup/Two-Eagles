package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.SavingException;
import pack.model.IModelElement;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.schema.DemandServType;
import pack.model.schema.ServiceRequestType;
import pack.model.schema.TimeServType;
import pack.model.schema.WorkUnitServType;

public class ServiceRequestModel implements IModelElement {

	private static final long serialVersionUID = 1L;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);

	private static IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[0];
	
	private List<IRequest> requests = new ArrayList<IRequest>();
	
	// i seguenti attributi si riferiscono all'xml

	public ServiceRequestModel()
		{
		requests = new ArrayList<IRequest>();
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
		{}
	
	public List<IRequest> getChildrenRequest() 
		{
		return requests;
		}
	
	public boolean removeChild(IRequest request) 
		{
		if (requests.contains(request))
			{
			requests.remove(request);
			// si notifica il cambio di proprietà
			firePropertyChange(IQNM.REQUEST_REMOVED_PROP, null, request);
			return true;
			}
		return false;
		}
	
	public boolean addChild(IRequest request) 
		{
		requests.add(request);
		// si notifica il cambio di proprietà
		firePropertyChange(IQNM.REQUEST_ADDED_PROP, null, request);
		return true;
		}
	
	public List<DemandServModel> getDemandServs()
		{
		List<DemandServModel> list = new ArrayList<DemandServModel>();
		for (IRequest request : this.requests)
			{
			if (request instanceof DemandServModel)
				list.add((DemandServModel)request);
			}
		return list;
		}
	
	public List<TimeServModel> getTimeServs()
		{
		List<TimeServModel> list = new ArrayList<TimeServModel>();
		for (IRequest request : this.requests)
			{
			if (request instanceof TimeServModel)
				list.add((TimeServModel)request);
			}
		return list;
		}
	
	public List<WorkUnitServModel> getWorkUnitServs()
		{
		List<WorkUnitServModel> list = new ArrayList<WorkUnitServModel>();
		for (IRequest request : this.requests)
			{
			if (request instanceof WorkUnitServModel)
				list.add((WorkUnitServModel)request);
			}
		return list;
		}

	public ServiceRequestType generaDom() throws SavingException
		{
		ServiceRequestType serviceRequestType = new ServiceRequestType();
		for (DemandServModel demandServModel : this.getDemandServs())
			{
			DemandServType demandServType = demandServModel.generaDom();
			serviceRequestType.addDemandServiceRequest(demandServType);
			}
		for (TimeServModel timeServModel : this.getTimeServs())
			{
			TimeServType timeServType = timeServModel.generaDom();
			serviceRequestType.addTimeServiceRequest(timeServType);
			}
		for (WorkUnitServModel workUnitServModel : this.getWorkUnitServs())
			{
			WorkUnitServType workUnitServType = workUnitServModel.generaDom();
			serviceRequestType.addWorkUnitServiceRequest(workUnitServType);
			}
		return serviceRequestType;
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof ServiceRequestModel) || arg0 == null)
			return false;
		ServiceRequestModel serviceRequestModel = (ServiceRequestModel)arg0;
		if (!(this.getChildrenRequest().equals(serviceRequestModel.getChildrenRequest())))
			return false;
		return true;
		}

	public Collection<? extends IRequest> getChildrenRequestFromWorkload(
			String string) 
		{
		List<IRequest> list = new ArrayList<IRequest>();
		for (IRequest request : requests)
			{
			String string2 = request.getWorkloadName();
			if (string2.equals(string))
				list.add(request);
			}
		return list;
		}
	}
