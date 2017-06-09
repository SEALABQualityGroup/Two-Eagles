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
public class WeaselProxyThreadSolve 
	extends Thread 
	{

	private WeaselProxyRis weaselProxyRis;
	
	private QNSolverPortProxy proxy;
	
	private String model; 
	private String model_type;
	private String tool; 
	private String method; 
	private String params;
	
	public WeaselProxyThreadSolve(WeaselProxyRis weaselProxyRis,
			QNSolverPortProxy proxy, String model, String model_type,
			String tool, String method, String params) 
		{
		super();
		this.setName("Mirko Thread");
		this.weaselProxyRis = weaselProxyRis;
		this.proxy = proxy;
		this.model = model;
		this.model_type = model_type;
		this.tool = tool;
		this.method = method;
		this.params = params;
		}

	@Override
	public void run() 
		{
		try {
			String string = proxy.solve_(this.model, this.model_type, this.tool, this.method, this.params);
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
		}
	}
