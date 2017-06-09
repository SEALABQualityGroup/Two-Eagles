package pack.errorManagement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class Finestra 
	{
	
	public static void mostraLE(String string)
		throws LoadingException
		{
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setMessage(string);
		messageBox.open();
		throw new LoadingException(string);
		}
	
	public static void mostraSE(String string)
		throws SavingException
		{
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setMessage(string);
		messageBox.open();
		throw new SavingException(string);
		}
	
	public static void mostraIE(String string)
		throws Exception
		{
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setMessage(string);
		messageBox.open();
		throw new Exception(string);
		}	

	public static void mostraSW(String string)
		{
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		messageBox.setMessage(string);
		messageBox.open();
		}		
	}
