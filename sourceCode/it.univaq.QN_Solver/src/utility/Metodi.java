package utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class Metodi 
	{

	public static void stampaSuConsole(String string)
		{
		ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
		IConsoleManager consoleManager = consolePlugin.getConsoleManager();			
		MessageConsole messageConsole = new MessageConsole("Console TwoTower",null);
		MessageConsoleStream messageConsoleStream = messageConsole.newMessageStream();
		messageConsoleStream.println(string);
		// prima di aggiungere la nuova console, rimuoviamo quelle vecchie
		IConsole[] consoles = consoleManager.getConsoles();
		consoleManager.removeConsoles(consoles);
		// si mostra la console nuova con il messaggio
		consoleManager.addConsoles(new MessageConsole[]{messageConsole});
		consoleManager.showConsoleView(messageConsole);
		}
	
	public static void stampaEstoricizza(StringWriter stringWriter,IFile file)
		{
		String string = stringWriter.toString();
		Metodi.stampaSuConsole(string);
		// si salva il log della risoluzione di weasel per una rete di code in formato 
		// PMIF su workspace, nella stessa directory
		// del file PMIF, con lo stesso nome del file PMIf seguito dal suffisso 'Result', 
		// con estensione txt
		// si preleva il path assoluto dello PMIF risolto
		IPath path = file.getLocation();
		IPath path2 = path.removeFileExtension();
		String string2 = path2.toString()+"Result.txt";
		// si crea un writer su cui scrivere string2
		try {
			FileWriter fileWriter = new FileWriter(string2);
			// si scrive string sul writer
			fileWriter.write(string);
			fileWriter.close();
			} 
		catch (IOException e) 
			{
			e.printStackTrace();
			}
		// si effettua il refresh del file su workspace
		// secondo le seguenti istruzioni
		IContainer container = file.getParent();
		try {
			container.refreshLocal(IFile.DEPTH_INFINITE, null);
			} 
		catch (CoreException e) 
			{
			e.printStackTrace();
			}
		}
	}
