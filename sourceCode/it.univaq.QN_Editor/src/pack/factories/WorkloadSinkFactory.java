package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.WorkloadSinkModel;

public class WorkloadSinkFactory implements CreationFactory {

	private OpenWorkloadModel openWorkloadModel;
	
	public WorkloadSinkFactory(OpenWorkloadModel openWorkloadModel) 
		{
		super();
		this.openWorkloadModel = openWorkloadModel;
		}

	@Override
	public Object getNewObject() 
		{
		return new WorkloadSinkModel(openWorkloadModel,null);
		}

	@Override
	public Object getObjectType() 
		{
		return WorkloadSinkModel.class;
		}
	}
