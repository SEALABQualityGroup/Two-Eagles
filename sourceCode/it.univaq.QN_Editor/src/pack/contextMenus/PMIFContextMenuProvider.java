package pack.contextMenus;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

import pack.actions.InsertClosedWorkload;
import pack.actions.InsertNodeModel;
import pack.actions.InsertOpenWorkload;
import pack.actions.InsertServiceRequestModel;
import pack.actions.InsertWorkloadModel;
import pack.actions.SwitchToQN;
import pack.actions.SwitchToWorkload;
import pack.errorManagement.Finestra;

public class PMIFContextMenuProvider 
	extends ContextMenuProvider 
	{
	
	/** The editor's action registry. */
	private ActionRegistry actionRegistry;

	public PMIFContextMenuProvider(EditPartViewer viewer, ActionRegistry registry)
		throws Exception
		{
		super(viewer);
		if (registry == null) 
			{
			Finestra.mostraIE("action registry is null");
			}
		actionRegistry = registry;
		// si registrano le azioni di switch
		// All'inizio tali azioni sono disabilitate
		SwitchToWorkload switchToWorkload = new SwitchToWorkload();
		SwitchToQN switchToQN = new SwitchToQN();
		switchToWorkload.setEnabled(false);
		switchToQN.setEnabled(false);
		actionRegistry.registerAction(switchToWorkload);
		actionRegistry.registerAction(switchToQN);
		// si registrano le azioni di inserimento di un carico di lavoro.
		// All'inizio tali azioni sono disabilitate
		InsertClosedWorkload insertClosedWorkload = new InsertClosedWorkload();
		InsertOpenWorkload insertOpenWorkload = new InsertOpenWorkload();
		insertClosedWorkload.setEnabled(false);
		insertOpenWorkload.setEnabled(false);
		actionRegistry.registerAction(insertClosedWorkload);
		actionRegistry.registerAction(insertOpenWorkload);
		// si registra l'azione di inserimento di un contenitore di nodi
		InsertNodeModel insertNodeModel = new InsertNodeModel();
		insertNodeModel.setEnabled(false);
		actionRegistry.registerAction(insertNodeModel);
		// si registra l'azione di inserimento di un contenitore di carichi di lavori
		InsertWorkloadModel insertWorkloadModel = new InsertWorkloadModel();
		insertWorkloadModel.setEnabled(false);
		actionRegistry.registerAction(insertWorkloadModel);
		// si registra l'azione di inserimento di un contenitore di richieste di servizio
		InsertServiceRequestModel insertServiceRequestModel = new InsertServiceRequestModel();
		insertServiceRequestModel.setEnabled(false);
		actionRegistry.registerAction(insertServiceRequestModel);
		}

	@Override
	public void buildContextMenu(IMenuManager menu) 
		{
		// Add standard action groups to the menu
		GEFActionConstants.addStandardActionGroups(menu);
		
		// Add actions to the menu
		menu.appendToGroup(
				GEFActionConstants.GROUP_UNDO, // target group id
				getAction(ActionFactory.UNDO.getId())); // action to add
		menu.appendToGroup(
				GEFActionConstants.GROUP_UNDO, 
				getAction(ActionFactory.REDO.getId()));
		menu.appendToGroup(
				GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.DELETE.getId()));
		// si aggiungono le azioni di switch
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction("it.univaq.SwitchToWorkload"));
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction("it.univaq.SwitchToQN"));
		// si aggiungono le azioni di inserimento di un carico di lavoro
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction("it.univaq.InsertClosedWorkload"));
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction("it.univaq.InsertOpenWorkload"));
		// si aggiunge l'azione per l'inserimento di un contenitore di nodi
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction("it.univaq.InsertNodeModel"));
		// si aggiunge l'azione per l'inserimento di un contenitore di carichi di lavori
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction("it.univaq.InsertWorkloadModel"));
		// si aggiunge l'azione per l'inserimento di un contenitore di richieste di servizio
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction("it.univaq.InsertServiceRequestModel"));
		}
	
	private IAction getAction(String actionId) 
		{
		return actionRegistry.getAction(actionId);
		}	
	}
