/**
 * 
 */
package qNSolver.qNSolverAsync;

/**
 * @author Mirko
 *
 */
public class WeaselProxyRis 
	{

	private boolean responded;
	
	private String response;

	public synchronized boolean isResponded() 
		{
		return responded;
		}

	public synchronized void setResponded(boolean responded) 
		{
		this.responded = responded;
		}

	public synchronized String getResponse() 
		{
		return response;
		}

	public synchronized void setResponse(String response) 
		{
		this.response = response;
		}
	}
