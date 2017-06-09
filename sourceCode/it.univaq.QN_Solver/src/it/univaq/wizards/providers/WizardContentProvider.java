/**
 * 
 */
package it.univaq.wizards.providers;

import it.univaq.wizards.WeaselTreeNode;

import org.eclipse.jface.viewers.TreeNodeContentProvider;

/**
 * @author Mirko
 *
 */
public class WizardContentProvider 
	extends TreeNodeContentProvider 
	{

	public WizardContentProvider() 
		{
		super();
		}

	@Override
	public Object[] getElements(Object inputElement) 
		{
		if (inputElement instanceof WeaselTreeNode)
			{
			WeaselTreeNode weaselTreeNode = (WeaselTreeNode)inputElement;
			return weaselTreeNode.getChildren();
			}
		else
			return super.getElements(inputElement);
		}
	}
