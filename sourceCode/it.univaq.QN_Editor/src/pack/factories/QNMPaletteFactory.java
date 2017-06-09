package pack.factories;

import org.eclipse.draw2d.Graphics;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import pack.model.structure.ServerModel;
import pack.model.structure.SinkNodeModel;
import pack.model.structure.SourceNodeModel;
import pack.model.structure.WorkUnitServerModel;


public class QNMPaletteFactory 
	{
	
	/** Create the "PMIF" drawer. */
	private PaletteContainer createPMIFDrawer() 
		{
		PaletteDrawer componentsDrawer = new PaletteDrawer("QNM palette");

		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"Server", "Create a server node", ServerModel.class,
				new ServerFactory(), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Red.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Red.bmp"));
		componentsDrawer.add(component);

		component = new CombinedTemplateCreationEntry(
				"Work Unit Server",	"Create a work unit server node", WorkUnitServerModel.class,
				new WorkUnitServerFactory(), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Blu.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Blu.bmp"));
		componentsDrawer.add(component);

		component = new CombinedTemplateCreationEntry(
				"Source Node", "Create a source node", SourceNodeModel.class,
				new SourceNodeFactory(), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Source.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Source.bmp"));
		componentsDrawer.add(component);

		component = new CombinedTemplateCreationEntry(
				"Sink Node", "Create a sink node", SinkNodeModel.class,
				new SinkNodeFactory(), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Sink.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Sink.bmp"));
		componentsDrawer.add(component);

		return componentsDrawer;
		}
		
	/**
	 * Creates the PaletteRoot and adds all palette elements.
	 * Use this factory method to create a new palette for your graphical editor.
	 * @return a new PaletteRoot
	 */
	public PaletteRoot createPalette() 
		{
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
		palette.add(createPMIFDrawer());
		return palette;
		}

	/** Create the "Tools" group. */
	private PaletteContainer createToolsGroup(PaletteRoot palette) 
		{
		PaletteToolbar toolbar = new PaletteToolbar("Tools group");

		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		toolbar.add(tool);
		palette.setDefaultEntry(tool);
		
		// Add a marquee tool to the group
		toolbar.add(new MarqueeToolEntry());

		// Add (solid-line) arc tool 
		tool = new ConnectionCreationToolEntry(
				"arc",
				"Create a arc",
				new CreationFactory() 
					{
					public Object getNewObject() 
						{ 
						return null; 
						}
					// see ShapeEditPart#createEditPolicies() 
					// this is abused to transmit the desired line style 
					public Object getObjectType() 
						{ 
						return new Integer(Graphics.LINE_SOLID); 
						}
					},
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/connection_s16.gif"),
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/connection_s24.gif")
				);
		toolbar.add(tool);
		
		return toolbar;
		}
	}
