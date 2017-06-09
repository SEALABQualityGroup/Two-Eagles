package from_aemilia_to_qn_plug_in;


import it.univaq.from_aemilia_to_qn_plug_in.listeners.MainWindowListener;

import java.util.ArrayList;
import java.util.List;


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
	implements IStartup 
	{

	// The plug-in ID
	public static final String PLUGIN_ID = "From_AEmilia_To_QN_Plug_in2";

	// The shared instance
	private static Activator plugin;
	
	private List<MainWindowListener> mainWindowsListeners;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) 
		throws Exception 
		{
		super.start(context);
		plugin = this;
		}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		for (MainWindowListener mainWindowListener : mainWindowsListeners)
			{
			workbench.removeWindowListener(mainWindowListener);
			}
		mainWindowsListeners = null;
		plugin = null;
		super.stop(context);
		}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	public void earlyStartup() 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow[] workbenchWindows = workbench.getWorkbenchWindows();
		mainWindowsListeners = new ArrayList<MainWindowListener>();
		for (IWorkbenchWindow workbenchWindow : workbenchWindows)
			{
			MainWindowListener mainWindowListener = new MainWindowListener(workbenchWindow);
			mainWindowsListeners.add(mainWindowListener);
			workbench.addWindowListener(mainWindowListener);
			}
		}
}
