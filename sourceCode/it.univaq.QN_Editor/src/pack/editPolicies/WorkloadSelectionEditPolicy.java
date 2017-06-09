package pack.editPolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;

import pack.actions.SwitchToWorkload;
import pack.editParts.ClosedWorkloadTypeItemEditPart;
import pack.editParts.OpenWorkloadTypeItemEditPart;
import pack.errorManagement.Finestra;
import pack.model.IWorkload;

public class WorkloadSelectionEditPolicy 
	extends SelectionEditPolicy 
	{

	@Override
	protected void hideSelection() 
		{
		if (getHost() instanceof ClosedWorkloadTypeItemEditPart)
			{
			ClosedWorkloadTypeItemEditPart closedWorkloadTypeItemEditPart =
				(ClosedWorkloadTypeItemEditPart)getHost();
			ActionRegistry actionRegistry = closedWorkloadTypeItemEditPart.getActionRegistry();
			IAction action = actionRegistry.getAction("it.univaq.SwitchToWorkload");
			action.setEnabled(false);
			}
		else if (getHost() instanceof OpenWorkloadTypeItemEditPart)
			{
			OpenWorkloadTypeItemEditPart workloadTypeItemEditPart =
				(OpenWorkloadTypeItemEditPart)getHost();
			ActionRegistry actionRegistry = workloadTypeItemEditPart.getActionRegistry();
			IAction action = actionRegistry.getAction("it.univaq.SwitchToWorkload");
			action.setEnabled(false);
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
		// viene impostato lo stato di attivazione dell'azione di switch solo se
		// la parte di edit ha la selezione principale
		int i = getHost().getSelected();
		if (i == EditPart.SELECTED_PRIMARY)
			{
			if (getHost() instanceof ClosedWorkloadTypeItemEditPart)
				{
				ClosedWorkloadTypeItemEditPart closedWorkloadTypeItemEditPart =
					(ClosedWorkloadTypeItemEditPart)getHost();
				ActionRegistry actionRegistry = closedWorkloadTypeItemEditPart.getActionRegistry();
				SwitchToWorkload action = (SwitchToWorkload)actionRegistry.getAction("it.univaq.SwitchToWorkload");
				action.setEnabled(true);
				// si imposta il workload dell'azione
				action.setWorkload((IWorkload)closedWorkloadTypeItemEditPart.getModel());
				}
			else if (getHost() instanceof OpenWorkloadTypeItemEditPart)
				{
				OpenWorkloadTypeItemEditPart workloadTypeItemEditPart =
					(OpenWorkloadTypeItemEditPart)getHost();
				ActionRegistry actionRegistry = workloadTypeItemEditPart.getActionRegistry();
				SwitchToWorkload action = (SwitchToWorkload)actionRegistry.getAction("it.univaq.SwitchToWorkload");
				action.setEnabled(true);
				// si imposta il workload dell'azione
				action.setWorkload((IWorkload)workloadTypeItemEditPart.getModel());
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
