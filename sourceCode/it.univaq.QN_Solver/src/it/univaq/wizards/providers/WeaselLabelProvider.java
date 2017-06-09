/**
 * 
 */
package it.univaq.wizards.providers;

import it.univaq.wizards.WeaselTreeNode;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

/**
 * @author Mirko
 *
 */
public class WeaselLabelProvider 
	extends ColumnLabelProvider 
	{

	/**
	 * 
	 */
	public WeaselLabelProvider() 
		{
		super();
		}

	@Override
	public Font getFont(Object element) 
		{
		if (element instanceof WeaselTreeNode)
			{
			WeaselTreeNode weaselTreeNode = (WeaselTreeNode)element;
			if (weaselTreeNode.isHeader())
				{
				Font font2 = new Font(null,"Tahoma", 9, SWT.BOLD);
				return font2;	
				}
			else 
				return super.getFont(element);
			}
		else
			return super.getFont(element);
		}

	@Override
	public String getText(Object element) 
		{
		if (element instanceof WeaselTreeNode)
			{
			WeaselTreeNode weaselTreeNode = (WeaselTreeNode)element;
			return weaselTreeNode.getText();
			}
		else
			return super.getText(element);
		}
	}
