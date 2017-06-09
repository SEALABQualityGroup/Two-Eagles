package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.WorkloadSourceModel;

public class WorkloadSourceFactory implements CreationFactory {

	private OpenWorkloadModel openWorkloadModel;
	
	public WorkloadSourceFactory(OpenWorkloadModel openWorkloadModel) 
		{
		super();
		this.openWorkloadModel = openWorkloadModel;
		}

	@Override
	public Object getNewObject() 
		{
		return new WorkloadSourceModel(openWorkloadModel,null);
		}

	@Override
	public Object getObjectType() 
		{
		return WorkloadSourceModel.class;
		}
	}
