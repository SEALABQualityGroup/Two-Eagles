package it.univaq.wizards.providers;

import it.univaq.wizards.pages.QNSolveServersWizardPage;

import java.rmi.RemoteException;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;

import parserReply.ParserWeaselReply;
import parserReply.Tool;
import parserReply.ToolsList;
import qNSolver.QNSolverPortProxy;
import qNSolver.WeaselProxy;

/**
 * The content provider second page of the extract strings wizard
 */
public class ExtractedStringsContentProvider
	implements IExtractedStringsContentProviderTools
	{
	
	private ToolsList toolsList;

	// dummy data
	private Object[] items;
	
	private boolean responded;
	
	public ExtractedStringsContentProvider(QNSolveServersWizardPage serversWizardPage)
		{
		super();
		QNSolverPortProxy portProxy = WeaselProxy.getProxy(serversWizardPage.getCombo().getText());
		try {
			String string = portProxy.getToolsList_();
			this.responded = portProxy.responded();
			if (this.responded)
				{
				ParserWeaselReply parserWeaselReply =
					new ParserWeaselReply(string);
				ToolsList toolsList = parserWeaselReply.getToolList();
				// inizializzo gli items a seconda del nome e descrizione
				// presente in ogni tool
				List<Tool> list = toolsList.getList();
				items = new Object[list.size()];
				for (int i = 0; i < list.size(); i++)
					{
					Tool tool = list.get(i);
					String string2 = tool.getName();
					String string3 = tool.getDescription();
					ExtractedString extractedString = new ExtractedString(string2,string3);
					items[i] = extractedString;
					}
				this.toolsList = toolsList;
				}
			} 
		catch (RemoteException e) 
			{
			e.printStackTrace();
			}
		}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) 
   		{
		
   		}

   	public Object[] getElements(Object inputElement) 
   		{
	   	return items;
   		}

   	public void dispose() 
   		{
   		}

	public ToolsList getToolsList() 
		{
		return toolsList;
		}

	public boolean isResponded() 
		{
		return responded;
		}
	}