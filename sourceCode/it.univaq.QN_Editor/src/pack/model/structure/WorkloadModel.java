package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.SavingException;
import pack.model.IModelElement;
import pack.model.IQNM;
import pack.model.IWorkload;
import pack.model.schema.ClosedWorkloadType;
import pack.model.schema.OpenWorkloadType;
import pack.model.schema.WorkloadType;

public class WorkloadModel implements IModelElement {

	private static final long serialVersionUID = 1L;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);

	private static IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[0];
	
	private List<IWorkload> workloads = new ArrayList<IWorkload>();
	
	private transient IQNM iqnm;
	
	// i seguenti attributi si riferiscono all'xml
	
	public WorkloadModel(List<IWorkload> list, IQNM iqnm)
		{
		this.workloads = list;
		this.iqnm = iqnm;
		}
	
	// il seguente costruttore viene utilizzato nell'azione di inserimento di
	// un contenitore di carichi di lavoro
	public WorkloadModel(IQNM iqnm) 
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
	
	public List<IWorkload> getChildrenWorkload() 
		{
		return workloads;
		}
	
	public boolean removeChild(IWorkload child) 
		{
		workloads.remove(child);
		// si notifica il cambio
		firePropertyChange("WorkloadRemoved", null, child);
		return true;
		}
	
	public boolean addChild(IWorkload child) 
		{
		workloads.add(child);
		// si notifica il cambio
		firePropertyChange("WorkloadAdded", null, child);
		return true;
		}

	public List<ClosedWorkloadModel> getClosedWorkloads() 
		{
		List<ClosedWorkloadModel> list = new ArrayList<ClosedWorkloadModel>();
		for (IWorkload workload : this.workloads)
			{
			if (workload instanceof ClosedWorkloadModel)
				list.add((ClosedWorkloadModel)workload);
			}
		return list;
		}

	public List<OpenWorkloadModel> getOpenWorkloads() 
		{
		List<OpenWorkloadModel> list = new ArrayList<OpenWorkloadModel>();
		for (IWorkload workload : this.workloads)
			{
			if (workload instanceof OpenWorkloadModel)
				list.add((OpenWorkloadModel)workload);
			}
		return list;
		}
	
	public IQNM getQNM()
		{
		return this.iqnm;
		}

	public WorkloadType generaDom() throws SavingException
		{
		WorkloadType workloadType = new WorkloadType();
		for (ClosedWorkloadModel closedWorkloadModel : this.getClosedWorkloads())
			{
			ClosedWorkloadType closedWorkloadType = closedWorkloadModel.generaDom();
			workloadType.addClosedWorkload(closedWorkloadType);
			}
		for (OpenWorkloadModel openWorkloadModel : this.getOpenWorkloads())
			{
			OpenWorkloadType openWorkloadType = openWorkloadModel.generaDom();
			workloadType.addOpenWorkload(openWorkloadType);
			}
		return workloadType;
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof WorkloadModel) || arg0 == null)
			return false;
		WorkloadModel workloadModel = (WorkloadModel)arg0;
		if (!this.workloads.equals(workloadModel.workloads))
			return false;
		return true;
		}

	public void setQNM(IQNM iqnm) 
		{
		this.iqnm = iqnm;
		}
	}
