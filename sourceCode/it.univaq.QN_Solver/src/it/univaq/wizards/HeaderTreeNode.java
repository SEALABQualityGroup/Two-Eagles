/**
 * 
 */
package it.univaq.wizards;



/**
 * @author Mirko
 *
 */
public class HeaderTreeNode 
	extends WeaselTreeNode 
	{

	public HeaderTreeNode(Object value) 
		{
		super(value);
		}

	/* (non-Javadoc)
	 * @see it.univaq.wizards.WeaselTreeNode#isHeader()
	 */
	@Override
	public boolean isHeader() 
		{
		return true;
		}

	}
