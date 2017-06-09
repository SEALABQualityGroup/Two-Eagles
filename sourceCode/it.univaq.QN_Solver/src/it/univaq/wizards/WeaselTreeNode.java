/**
 * 
 */
package it.univaq.wizards;

import org.eclipse.jface.viewers.TreeNode;

/**
 * @author Mirko
 *
 */
public abstract class WeaselTreeNode 
	extends TreeNode 
	{

	public WeaselTreeNode(Object value) 
		{
		super(value);
		}

	public abstract boolean isHeader();

	@Override
	public String toString() 
		{
		return value.toString();
		}
	
	public void setText(String string)
		{
		this.value = string;
		}
	
	public String getText()
		{
		return this.value.toString();
		}
	}
