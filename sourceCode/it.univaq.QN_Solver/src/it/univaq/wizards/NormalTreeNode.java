/**
 * 
 */
package it.univaq.wizards;



/**
 * @author Mirko
 *
 */
public class NormalTreeNode 
	extends WeaselTreeNode 
	{

	/**
	 * @param value
	 */
	public NormalTreeNode(Object value) 
		{
		super(value);
		}

	/* (non-Javadoc)
	 * @see it.univaq.wizards.WeaselTreeNode#isHeader()
	 */
	@Override
	public boolean isHeader() 
		{
		return false;
		}

	}
