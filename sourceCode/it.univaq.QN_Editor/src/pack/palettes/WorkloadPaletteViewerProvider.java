package pack.palettes;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;

public class WorkloadPaletteViewerProvider 
	extends PaletteViewerProvider 
	{
	public WorkloadPaletteViewerProvider(EditDomain graphicalViewerDomain) 
		{
		super(graphicalViewerDomain);
		}

	@Override
	protected void configurePaletteViewer(PaletteViewer viewer) 
		{
		super.configurePaletteViewer(viewer);
		// create a drag source listener for this palette viewer
		// together with an appropriate transfer drop target listener, this will enable
		// model element creation by dragging a CombinatedTemplateCreationEntries 
		// from the palette into the editor
		// @see ShapesEditor#createTransferDropTargetListener()
		viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
		}	
	}
