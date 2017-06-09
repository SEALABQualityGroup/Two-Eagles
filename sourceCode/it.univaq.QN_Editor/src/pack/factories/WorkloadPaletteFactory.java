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

import pack.model.IWorkload;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.DemandServModel;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.TimeServModel;
import pack.model.structure.WorkUnitServModel;
import pack.model.structure.WorkloadDelayModel;
import pack.model.structure.WorkloadSinkModel;
import pack.model.structure.WorkloadSourceModel;

public class WorkloadPaletteFactory 
	{
	
	private IWorkload workload;
	
	public WorkloadPaletteFactory(IWorkload workload) 
		{
		super();
		this.workload = workload;
		}

	/** Create the "PMIF" drawer. */
	private PaletteContainer createPMIFDrawer() 
		{
		PaletteDrawer componentsDrawer = new PaletteDrawer("Workload palette");

		if (workload instanceof ClosedWorkloadModel)
			{
			CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"Delay Node", "Create a delay device", WorkloadDelayModel.class,
				new WorkloadDelayFactory((ClosedWorkloadModel)workload), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Red.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Red.bmp"));
			componentsDrawer.add(component);
			}
		else
			{
			CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
					"Source Node", "Create a source node", WorkloadSourceModel.class,
					new WorkloadSourceFactory((OpenWorkloadModel)workload), 
					ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Source.bmp"), 
					ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Source.bmp"));
			componentsDrawer.add(component);

			component = new CombinedTemplateCreationEntry(
					"Sink Node", "Create a sink node", WorkloadSinkModel.class,
					new WorkloadSinkFactory((OpenWorkloadModel)workload), 
					ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Sink.bmp"), 
					ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/Queueing Network Sink.bmp"));
			componentsDrawer.add(component);
			}
		
		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"Demand Service Request", "Create a demand service request", DemandServModel.class,
				new DemandServFactory(this.workload), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/DemandServiceRequest_16X16.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/DemandServiceRequest_32X32.bmp"));
		componentsDrawer.add(component);

		component = new CombinedTemplateCreationEntry(
				"Time Service Request", "Create a time service request", TimeServModel.class,
				new TimeServFactory(this.workload), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/TimeServiceRequest_16X16.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/TimeServiceRequest_32X32.bmp"));
		componentsDrawer.add(component);

		component = new CombinedTemplateCreationEntry(
				"Work Unit Service Request", "Create a work unit service request", WorkUnitServModel.class,
				new WorkUnitServFactory(this.workload), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/WorkUnitServiceRequest_16X16.bmp"), 
				ImageDescriptor.createFromFile(pack.PMIFPlugin.class, "icons/WorkUnitServiceRequest_32X32.bmp"));
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
				"transit",
				"Create a transit",
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
