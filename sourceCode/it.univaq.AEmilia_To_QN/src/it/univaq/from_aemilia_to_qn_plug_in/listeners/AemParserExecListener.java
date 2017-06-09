/**
 * 
 */
package it.univaq.from_aemilia_to_qn_plug_in.listeners;

import it.univaq.from_aemilia_to_qn_plug_in.listeners.data.AemParsingToken;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;

/**
 * @author Mirko
 *
 */
public class AemParserExecListener 
	implements IExecutionListener 
	{

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IExecutionListener#notHandled(java.lang.String, org.eclipse.core.commands.NotHandledException)
	 */
	@Override
	public void notHandled(String commandId, NotHandledException exception) 
		{
		}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IExecutionListener#postExecuteFailure(java.lang.String, org.eclipse.core.commands.ExecutionException)
	 */
	@Override
	public void postExecuteFailure(String commandId,
			ExecutionException exception) 
		{
		if ("it.univaq.TTEP.parser".equals(commandId))
			{
			AemParsingToken.isParsing = false;			
			}
		}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IExecutionListener#postExecuteSuccess(java.lang.String, java.lang.Object)
	 */
	@Override
	public void postExecuteSuccess(String commandId, Object returnValue) 
		{
		if ("it.univaq.TTEP.parser".equals(commandId))
			{
			AemParsingToken.isParsing = false;			
			}
		}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IExecutionListener#preExecute(java.lang.String, org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public void preExecute(String commandId, ExecutionEvent event) 
		{
		if ("it.univaq.TTEP.parser".equals(commandId))
			{
			AemParsingToken.isParsing = true;			
			}
		}
	}
