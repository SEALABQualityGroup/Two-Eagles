package pack.outlinePages;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;

import pack.contextMenus.PMIFContextMenuProvider;
import pack.factories.WorkloadTreeEditPartFactory;
import pack.model.IWorkload;

public class WorkloadOutlinePage extends ContentOutlinePage {

	private ActionRegistry actionRegistry;
	
	private EditDomain editDomain;
	
	private IWorkload workload;
	
	private SelectionSynchronizer selectionSynchronizer;
	
	public WorkloadOutlinePage(EditPartViewer viewer, 
			ActionRegistry actionRegistry,
			EditDomain editDomain,
			IWorkload workload,
			SelectionSynchronizer selectionSynchronizer) 
		{
		super(viewer);
		this.actionRegistry = actionRegistry;
		this.editDomain = editDomain;
		this.workload = workload;
		this.selectionSynchronizer = selectionSynchronizer;
		}

	@Override
	public void init(IPageSite pageSite) 
		{
		super.init(pageSite);
		ActionRegistry registry = actionRegistry;
		IActionBars bars = pageSite.getActionBars();
		String id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		}
	
	@Override
	public void createControl(Composite parent) 
		{
		// create outline viewer page
		getViewer().createControl(parent);
		// configure outline viewer
		getViewer().setEditDomain(editDomain);
		getViewer().setEditPartFactory(new WorkloadTreeEditPartFactory(workload.getQNM()));
		// configure & add context menu to viewer
		try {
			ContextMenuProvider cmProvider = new PMIFContextMenuProvider(
				getViewer(), actionRegistry); 
			getViewer().setContextMenu(cmProvider);
			getSite().registerContextMenu(
				"it.univaq.PMIFGraphicalEditor.contextmenu",
				cmProvider, getSite().getSelectionProvider());		
			// hook outline viewer
			selectionSynchronizer.addViewer(getViewer());
			// initialize outline viewer with model
			getViewer().setContents(workload);
			// show outline viewer
			}
		catch (Exception exception)
			{}
		}
	
	@Override
	public Control getControl() 
		{
		return getViewer().getControl();
		}
	
	@Override
	public void dispose() 
		{
		// unhook outline viewer
		selectionSynchronizer.removeViewer(getViewer());
		// dispose
		super.dispose();
		}
	}
