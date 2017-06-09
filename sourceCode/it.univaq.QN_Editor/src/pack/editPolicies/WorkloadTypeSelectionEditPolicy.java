package pack.editPolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;

import pack.actions.InsertClosedWorkload;
import pack.actions.InsertOpenWorkload;
import pack.editParts.WorkloadTypeTreeEditPart;
import pack.errorManagement.Finestra;
import pack.model.structure.WorkloadModel;

public class WorkloadTypeSelectionEditPolicy 
	extends SelectionEditPolicy 
	{

	@Override
	protected void hideSelection() 
		{
		if (getHost() instanceof WorkloadTypeTreeEditPart)
			{
			WorkloadTypeTreeEditPart workloadTypeTreeEditPart =
				(WorkloadTypeTreeEditPart)getHost();
			ActionRegistry actionRegistry = workloadTypeTreeEditPart.getActionRegistry();
			IAction action = actionRegistry.getAction("it.univaq.InsertOpenWorkload");
			action.setEnabled(false);
			IAction action2 = actionRegistry.getAction("it.univaq.InsertClosedWorkload");
			action2.setEnabled(false);
			}
		else
			{
			try {
				Finestra.mostraIE("Illegal Edit Part");
				} 
			catch (Exception e) 
				{}
			}
		}

	@Override
	protected void showSelection() 
		{
		// viene impostato lo stato di attivazione delle azioni di inserimento di un carico di lavoro
		// solo se la parte di edit ha la selezione principale
		int i = getHost().getSelected();
		if (i == EditPart.SELECTED_PRIMARY)
			{
			if (getHost() instanceof WorkloadTypeTreeEditPart)
				{
				WorkloadTypeTreeEditPart workloadTypeTreeEditPart =
					(WorkloadTypeTreeEditPart)getHost();
				ActionRegistry actionRegistry = workloadTypeTreeEditPart.getActionRegistry();
				InsertClosedWorkload action = (InsertClosedWorkload)actionRegistry.getAction("it.univaq.InsertClosedWorkload");
				action.setEnabled(true);
				InsertOpenWorkload action2 = (InsertOpenWorkload)actionRegistry.getAction("it.univaq.InsertOpenWorkload");
				action2.setEnabled(true);
				// si imposta il contenitore dei carichi di lavoro per le azioni
				WorkloadModel workloadModel = (WorkloadModel)workloadTypeTreeEditPart.getModel();
				action.setWorkloadModel(workloadModel);
				action2.setWorkloadModel(workloadModel);
				}
			else
				{
				try {
					Finestra.mostraIE("Illegal Edit Part");
					} 
				catch (Exception e) 
					{}
				}
			}
		}
	}
