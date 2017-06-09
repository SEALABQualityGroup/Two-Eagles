package it.univaq.wizards.providers;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import parserReply.ToolsList;

public interface IExtractedStringsContentProviderTools
	extends IStructuredContentProvider
	{

	public ToolsList getToolsList();
	
	public boolean isResponded();
	
	}
