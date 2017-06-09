package pack;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PMIFPlugin 
	extends AbstractUIPlugin 
	{

	/** Single plugin instance. */
	private static PMIFPlugin singleton;

	/**
	 * Returns the shared plugin instance.
	 */
	public static PMIFPlugin getDefault() 
		{
		return singleton;
		}

	/** 
	 * The constructor. 
	 */
	public PMIFPlugin() 
		{
		if (singleton == null) 
			{
			singleton = this;
			}
		}
	}
