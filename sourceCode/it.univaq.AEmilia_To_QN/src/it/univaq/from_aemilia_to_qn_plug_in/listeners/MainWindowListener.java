package it.univaq.from_aemilia_to_qn_plug_in.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class MainWindowListener implements IWindowListener {

	private List<TwoTowerPerspectiveListener> twoTowerPerspectiveListeners;
	
	public MainWindowListener(IWorkbenchWindow workbenchWindow) 
		{
		// si considera anche il caso in cui ci siano più pagine e 
		// quindi abbiamo un listener di prospettiva per ogni pagina
		if (twoTowerPerspectiveListeners == null)
			{
			twoTowerPerspectiveListeners = new ArrayList<TwoTowerPerspectiveListener>();
			IWorkbenchPage[] workbenchPages = workbenchWindow.getPages();
			for (IWorkbenchPage workbenchPage : workbenchPages)
				{
				IPerspectiveDescriptor perspectiveDescriptor = workbenchPage.getPerspective();
				TwoTowerPerspectiveListener twoTowerPerspectiveListener =
					new TwoTowerPerspectiveListener(workbenchPage,perspectiveDescriptor);
				twoTowerPerspectiveListeners.add(twoTowerPerspectiveListener);
				workbenchWindow.addPerspectiveListener(twoTowerPerspectiveListener);
				}
			}
		}

	@Override
	public void windowActivated(IWorkbenchWindow window) 
		{
		if (twoTowerPerspectiveListeners == null)
			{
			twoTowerPerspectiveListeners = new ArrayList<TwoTowerPerspectiveListener>();
			IWorkbenchPage[] workbenchPages = window.getPages();
			for (int i = 0; i < workbenchPages.length; i++)
				{
				TwoTowerPerspectiveListener twoTowerPerspectiveListener =
					new TwoTowerPerspectiveListener();
				twoTowerPerspectiveListeners.add(twoTowerPerspectiveListener);
				window.addPerspectiveListener(twoTowerPerspectiveListener);
				}
			}
		}

	@Override
	public void windowClosed(IWorkbenchWindow window) 
		{
		if (twoTowerPerspectiveListeners != null)
			{
			for (TwoTowerPerspectiveListener twoTowerPerspectiveListener : twoTowerPerspectiveListeners)
				{
				window.removePerspectiveListener(twoTowerPerspectiveListener);
				}
			twoTowerPerspectiveListeners = null;
			}		
		}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) 
		{
		if (twoTowerPerspectiveListeners != null)
			{
			for (TwoTowerPerspectiveListener twoTowerPerspectiveListener : twoTowerPerspectiveListeners)
				{
				window.removePerspectiveListener(twoTowerPerspectiveListener);
				}
			twoTowerPerspectiveListeners = null;
			}		
		}

	@Override
	public void windowOpened(IWorkbenchWindow window) 
		{
		if (twoTowerPerspectiveListeners == null)
			{
			twoTowerPerspectiveListeners = new ArrayList<TwoTowerPerspectiveListener>();
			IWorkbenchPage[] workbenchPages = window.getPages();
			for (int i = 0; i < workbenchPages.length; i++)
				{
				TwoTowerPerspectiveListener twoTowerPerspectiveListener =
					new TwoTowerPerspectiveListener();
				twoTowerPerspectiveListeners.add(twoTowerPerspectiveListener);
				window.addPerspectiveListener(twoTowerPerspectiveListener);
				}
			}
		}
	}
