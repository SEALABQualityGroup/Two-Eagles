package it.univaq.ttep.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class Utility {

	/**
	 * Lancia il programma desiderato (TTKernel) passando
	 * l'opzione opportuna e i path dei file in input e in output.
	 * 
	 * @param shellParent Shell parent
	 * @param description Breve descrizione per la finestra di dialogo
	 * @param program Path del programma da eseguire
	 * @param option Opzioni da passare al programma da eseguire
	 * @param source Path del file di input
	 * @param dest Path dei file di output
	 */
	public static void execute(Shell shellParent, final String description, final String program, final String option, final String source, final String dest){
		try {
			IRunnableWithProgress r = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InterruptedException{
					monitor.beginTask(description, IProgressMonitor.UNKNOWN);
					ProcessBuilder processBuider = new ProcessBuilder(new String[]{program, option, source, dest});
					try{
						processBuider.start();
					}catch (java.io.IOException exception){
						exception.printStackTrace();
						monitor.done();
						return ;
					}
					monitor.done();
				}	
			};

			ProgressMonitorDialog p = new ProgressMonitorDialog(shellParent);

			p.run(true, true, r);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return ;
		}

		// Aggiornamento del navigator per visualizzare gli eventuali nuovi file creati
		refreshNavigator();
	}


	/**
	 * Lancia il programma desiderato (TTKernel) passando
	 * l'opzione opportuna e i path dei file in input e in output.
	 * 
	 * @param shellParent Shell parent
	 * @param description Breve descrizione per la finestra di dialogo
	 * @param program Path del programma da eseguire
	 * @param option Opzioni da passare al programma da eseguire
	 * @param source Path del file di input
	 * @param source_2OrValue Path del file del secondo file di input oppure valore da passare a al programma
	 * @param dest Path dei file di output
	 */
	public static void execute(Shell shellParent, final String description, final String program, final String option, final String source, final String source_2OrValue, final String dest){

		IRunnableWithProgress r = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InterruptedException{
				monitor.beginTask(description, IProgressMonitor.UNKNOWN);
				ProcessBuilder processBuider = new ProcessBuilder(new String[]{program, option, source, source_2OrValue, dest});
				try{
					processBuider.start();
				}catch (java.io.IOException exception){
					exception.printStackTrace();
					return ;
				}
				catch(Exception e){
					e.printStackTrace();
					return ;
				}
				monitor.done();
			}	
		};
		
		ProgressMonitorDialog p = new ProgressMonitorDialog(shellParent);
		try {
			p.run(true, true, r);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return ;
		}
		catch(Exception e){
			e.printStackTrace();
			return ;
		}
		
		// Aggiornamento del navigator per visualizzare gli eventuali nuovi file creati
		refreshNavigator();
	}


	/**
	 * Lancia il programma desiderato (TTKernel) passando
	 * l'opzione opportuna e i path dei file in input e in output.
	 * 
	 * @param shellParent Shell parent
	 * @param description Breve descrizione per la finestra di dialogo
	 * @param program Path del programma da eseguire
	 * @param option Opzioni da passare al programma da eseguire
	 * @param source Path del file di input
	 * @param source_2 Path del secondo file di input
	 * @param dest Path dei file di output
	 */
	public static void execute(Shell shellParent, final String description, final String program, final String option, final String source, final String value, final String source_2, final String dest){
		try {
			IRunnableWithProgress r = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InterruptedException{
					monitor.beginTask(description, IProgressMonitor.UNKNOWN);
					ProcessBuilder processBuider = new ProcessBuilder(new String[]{program, option, source, value, source_2, dest});
					try{
						processBuider.start();
					}catch (java.io.IOException exception){
						exception.printStackTrace();
						monitor.done();
						return ;
					}
					monitor.done();
				}	
			};

			ProgressMonitorDialog p = new ProgressMonitorDialog(shellParent);

			p.run(true, true, r);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return ;
		}

		// Aggiornamento del navigator per visualizzare gli eventuali nuovi file creati
		refreshNavigator();
	}

	/**
	 * Lancia il programma desiderato (TTKernel) passando
	 * l'opzione opportuna e i path dei file in input e in output.
	 * 
	 * @param shellParent Shell parent
	 * @param description Breve descrizione per la finestra di dialogo
	 * @param program Path del programma
	 * @param option Opzioni da passare al programma da eseguire
	 * @param source Path del file di input
	 */
	public static void execute(Shell shellParent, final String description, final String program, final String option, final String source){
		try{
			IRunnableWithProgress r = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InterruptedException{
					monitor.beginTask(description, IProgressMonitor.UNKNOWN);
					ProcessBuilder processBuider = new ProcessBuilder(new String[]{program, option, source});
					try{
						processBuider.start();
					}catch (java.io.IOException exception){
						exception.printStackTrace();
						monitor.done();
						return ;
					}

					monitor.done();
				}	
			};

			ProgressMonitorDialog p = new ProgressMonitorDialog(shellParent);
			p.run(true, true, r);
			
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return ;
		}

		// Aggiornamento del navigator per visualizzare gli eventuali nuovi file creati
		refreshNavigator();
	}


	/**
	 * Restituisce il file scelto dall'utente.
	 * 
	 * @param shellParent Shell parent
	 * @param extensions Array di stringhe che contiene tutte le estensioni che denotano
	 * i tipi di file che possono essere selezionati
	 * @param currentPath Path di lavoro
	 * 
	 * */
	public static File queryFile(Shell shellParent, String[] extensions, String currentPath) {
		FileDialog dialog= new FileDialog(shellParent, SWT.OPEN);
		dialog.setFilterExtensions(extensions);
		dialog.setFilterPath(currentPath);
		dialog.setText("Select .aem file");
		String path= dialog.open();
		if (path != null && path.length() > 0)
			return new File(path);
		return null;
	}


	/**
	 * Stampa sulla console il contentuto del file il cui nome
	 * è passato come parametro
	 * 
	 * @param dest Nome del file, comprensivo di path, il cui
	 * contentuto sarà stampato sulla console
	 * 
	 */
	public static void printOutput(String dest) {
		/* Verrà restituito l'output nella console */
		MessageConsole console = Utility.findConsole("Console TwoTowers");
		MessageConsoleStream out = console.newMessageStream();

		InputStream in;
		try {
			File f = new File(dest);
			in = new FileInputStream(f);

			byte[] buf = new byte[(int)f.length()];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.write('\n');
			in.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return ;
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return ;
		}

		IConsole myConsole = console;
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		String id_console = IConsoleConstants.ID_CONSOLE_VIEW;
		IConsoleView view;
		try {
			view = (IConsoleView) page.showView(id_console);
			view.display(myConsole);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
	}


	/**
	 * Controlla se è stato settato nel pannello delle preferenze il path
	 * con relativo nome del programma da lanciare (TTKernel)
	 * 
	 * @param window Finestra di lavoro per lanciare eventuali notifiche all'utente
	 * @param program Stringa da controllare
	 * @return true se la stringa è settata correttamente, false altrimenti
	 */
	public static boolean checkProgramPreferences(IWorkbenchWindow window, String program){
		if(program.equals("")){
			MessageDialog.openInformation(window.getShell(),
					"TTEP Plug-in",
					"Modify TwoTowers preferences before continue: set program path."
			);
			return false;
		}
		return true;
	}

	/**
	 * Restituisce una console esistente, se esiste, oppure ne crea una nuova.
	 *  
	 * @param Nome della console
	 * */
	private static MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		//no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[]{myConsole});
		return myConsole;
	}

	/**
	 * Aggiorna il workspace per visualizzare eventuali modifiche nella vista Navigator.
	 * 
	 * */
	private static void refreshNavigator() {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot();
		try {
			resource.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
