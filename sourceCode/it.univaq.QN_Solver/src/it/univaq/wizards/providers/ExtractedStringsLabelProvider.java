package it.univaq.wizards.providers;


import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;

/**
 * A label provider for the second page of the queueing network solver wizard
 */
public class ExtractedStringsLabelProvider extends LabelProvider
      implements ITableLabelProvider
      {
	
   public Image getColumnImage(Object element, int columnIndex) 
   		{
	   	return null;
   		}

   public String getColumnText(Object element, int columnIndex) 
   		{
	   	if (element instanceof ExtractedString) 
	   		{
	   		ExtractedString extractedString = (ExtractedString) element;
	   		switch (columnIndex) 
	   			{
	   			case 0:
	   				return extractedString.getName();
	   			case 1:
	   				return extractedString.getDescription();
	   			default:
	   				return "";
	   			}
	   		}
	   	if (element == null)
	   		return "<null>";
	   	try 
	   		{
	   		return element.toString();
	   		}
	   	catch (Exception e) 
	   		{
	   		return e.toString();
	   		}	
   		}
      }