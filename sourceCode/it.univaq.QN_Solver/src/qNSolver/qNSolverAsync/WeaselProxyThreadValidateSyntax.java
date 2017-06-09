/**
 * 
 */
package qNSolver.qNSolverAsync;

import java.rmi.RemoteException;

import qNSolver.QNSolverPortProxy;

/**
 * @author Mirko
 *
 */
public class WeaselProxyThreadValidateSyntax 
	extends Thread 
	{

	private WeaselProxyRis weaselProxyRis;
	
	private QNSolverPortProxy proxy;
	
	private String pmif;
	
	private String tipoModello;

	public WeaselProxyThreadValidateSyntax(
			WeaselProxyRis weaselProxyRis,
			QNSolverPortProxy proxy,
			String pmif,
			String tipoModello) 
		{
		super();
		this.setName("Mirko Thread");
		this.weaselProxyRis = weaselProxyRis;
		this.proxy = proxy;
		this.pmif = pmif;
		this.tipoModello = tipoModello;
		}

	@Override
	public void run() 
		{
		try {
			String string = proxy.validateSyntax_(this.pmif,this.tipoModello);
			synchronized (weaselProxyRis) 
				{
				weaselProxyRis.setResponse(string);
				weaselProxyRis.setResponded(true);
				weaselProxyRis.notifyAll();
				}
			} 
		catch (RemoteException e) 
			{
			e.printStackTrace();
			}
		catch (Exception e) 
			{
			e.printStackTrace();
			}
		}
	
	}
