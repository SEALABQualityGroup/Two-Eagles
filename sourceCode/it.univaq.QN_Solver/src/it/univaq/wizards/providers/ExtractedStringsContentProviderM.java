package it.univaq.wizards.providers;


import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import parserReply.Method;
import parserReply.SolveM;
import parserReply.Tool;

public class ExtractedStringsContentProviderM implements
		IStructuredContentProvider {

	private Tool tool;
	
	private Object[] items;
		
	public ExtractedStringsContentProviderM(Tool tool) 
		{
		super();
		this.tool = tool;
		// inizializzo gli items a seconda del nome e descrizione presente in ogni metodo
		// di tool
		SolveM solveM = (SolveM)this.tool.getSolve();
		List<Method> list = solveM.getList();
		items = new Object[list.size()];
		for (int i = 0; i < list.size(); i++)
			{
			Method method = list.get(i);
			String string2 = method.getName();
			String string3 = method.getDescription();
			ExtractedString extractedString = new ExtractedString(string2,string3);
			items[i] = extractedString;
			}
		}

	@Override
	public Object[] getElements(Object inputElement) 
		{
	   	return items;
		}

	@Override
	public void dispose() 
		{
		}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) 
		{
		}

	public List<Method> getMethodsList() 
		{
		// siamo nel caso in cui tool abbia metodi 
		SolveM solveM = (SolveM)tool.getSolve();
		return solveM.getList();
		}
	}	
