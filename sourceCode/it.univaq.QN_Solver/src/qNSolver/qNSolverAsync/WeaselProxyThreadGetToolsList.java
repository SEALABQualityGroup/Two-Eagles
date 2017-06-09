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
public class WeaselProxyThreadGetToolsList 
	extends Thread 
	{

	private WeaselProxyRis weaselProxyRis;
	
	private QNSolverPortProxy proxy;

	public WeaselProxyThreadGetToolsList(WeaselProxyRis weaselProxyRis,
			QNSolverPortProxy proxy) 
		{
		super();
		this.setName("Mirko Thread");
		this.weaselProxyRis = weaselProxyRis;
		this.proxy = proxy;
		}

	@Override
	public void run() 
		{
		try {
			String string = proxy.getToolsList_();
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
		System.out.println("WeaselProxyThreadGetToolsList esce");
		}
	}
