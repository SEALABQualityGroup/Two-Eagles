package pack.editPolicies;

import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;

import pack.actions.InsertNodeModel;
import pack.actions.InsertServiceRequestModel;
import pack.actions.InsertWorkloadModel;
import pack.editParts.QNMTypeTreeEditPart;
import pack.errorManagement.Finestra;
import pack.model.structure.QNMModel;

public class QNMModelSelectionEditPolicy extends SelectionEditPolicy 
	{

	@Override
	protected void hideSelection() 
		{
		if (getHost() instanceof QNMTypeTreeEditPart)
			{
			QNMTypeTreeEditPart typeTreeEditPart =
				(QNMTypeTreeEditPart)getHost();
			ActionRegistry actionRegistry = typeTreeEditPart.getActionRegistry();
			IAction action = actionRegistry.getAction("it.univaq.InsertNodeModel");
			IAction action2 = actionRegistry.getAction("it.univaq.InsertWorkloadModel");
			IAction action3 = actionRegistry.getAction("it.univaq.InsertServiceRequestModel");
			action.setEnabled(false);
			action2.setEnabled(false);
			action3.setEnabled(false);
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
		// viene impostato lo stato di attivazione dell'azione di inserimento di  
		// contenitori
		if (getHost() instanceof QNMTypeTreeEditPart)
			{
			QNMTypeTreeEditPart typeTreeEditPart =
				(QNMTypeTreeEditPart)getHost();
			ActionRegistry actionRegistry = typeTreeEditPart.getActionRegistry();
			InsertNodeModel action = (InsertNodeModel)actionRegistry.getAction("it.univaq.InsertNodeModel");
			InsertWorkloadModel action2 = (InsertWorkloadModel)actionRegistry.getAction("it.univaq.InsertWorkloadModel");
			InsertServiceRequestModel action3 = (InsertServiceRequestModel)actionRegistry.getAction("it.univaq.InsertServiceRequestModel");
			action.setEnabled(true);
			action2.setEnabled(true);
			action3.setEnabled(true);
			// si imposta la radice QNMModel per le azioni
			QNMModel model = (QNMModel)typeTreeEditPart.getModel();
			action.setQNMModel(model);
			action2.setQNMModel(model);
			action3.setQNMModel(model);
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
