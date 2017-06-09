/**
 * 
 */
package qNSolver.qNSolverAsync;

import java.rmi.RemoteException;

import qNSolver.QNSolverPortProxy;
import qNSolver.qNSolverWin.QNSolverPortProxyWin;
import utility.Properties;

/**
 * @author Mirko
 *
 */
public class QNSolverPortProxyWinAsync 
	implements QNSolverPortProxy 
	{

	private boolean responded;
	
	/* (non-Javadoc)
	 * @see qNSolver.QNSolverPortProxy#getToolsList_()
	 */
	@Override
	public String getToolsList_() 
		throws RemoteException
		{
		// istanzio un oggetto WeaselProxyRis
		WeaselProxyRis weaselProxyRis = new WeaselProxyRis();
		QNSolverPortProxyWin solverPortProxyWin = new QNSolverPortProxyWin();
		// istanzio un oggetto WeaselProxyThreadGetToolsList come thread
		WeaselProxyThreadGetToolsList weaselProxyThreadGetToolsList = 
			new WeaselProxyThreadGetToolsList(weaselProxyRis,solverPortProxyWin);
		try {
			synchronized (weaselProxyRis) 
				{
				// avvio il thread
				weaselProxyThreadGetToolsList.start();
				// mi pongo in attesa per 30000 millisecondi sull'istanza WeaselProxyRis				
				weaselProxyRis.wait(Properties.timeout);
				}
			} 
		catch (InterruptedException e) 
			{
			e.printStackTrace();
			}
		this.responded = weaselProxyRis.isResponded();
		return weaselProxyRis.getResponse();
		}

	/* (non-Javadoc)
	 * @see qNSolver.QNSolverPortProxy#solve_(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String solve_(String pmif, String tipoModello, String name,
			String string, String string2) throws RemoteException 
		{
		// istanzio un oggetto WeaselProxyRis
		WeaselProxyRis weaselProxyRis = new WeaselProxyRis();
		QNSolverPortProxyWin solverPortProxyWin = new QNSolverPortProxyWin();
		// istanzio un oggetto WeaselProxyThreadSolve come thread
		WeaselProxyThreadSolve weaselProxyThreadSolve = 
			new WeaselProxyThreadSolve(weaselProxyRis,solverPortProxyWin,pmif,tipoModello,name,string,string2);
		try {
			synchronized (weaselProxyRis) 
				{
				// avvio il thread
				weaselProxyThreadSolve.start();
				// mi pongo in attesa per 30000 millisecondi sull'istanza WeaselProxyRis				
				weaselProxyRis.wait(Properties.timeout);
				}
			} 
		catch (InterruptedException e) 
			{
			e.printStackTrace();
			}
		this.responded = weaselProxyRis.isResponded();
		return weaselProxyRis.getResponse();
		}

	/* (non-Javadoc)
	 * @see qNSolver.QNSolverPortProxy#validateSemantic_(java.lang.String, java.lang.String)
	 */
	@Override
	public String validateSemantic_(String pmif, String tipoModello)
			throws RemoteException 
		{
		// istanzio un oggetto WeaselProxyRis
		WeaselProxyRis weaselProxyRis = new WeaselProxyRis();
		QNSolverPortProxyWin solverPortProxyWin = new QNSolverPortProxyWin();
		// istanzio un oggetto WeaselProxyThreadSolve come thread
		WeaselProxyThreadValidateSemantic weaselProxyThreadValidateSemantic = 
			new WeaselProxyThreadValidateSemantic(weaselProxyRis,solverPortProxyWin,pmif,tipoModello);
		try {
			synchronized (weaselProxyRis) 
				{
				// avvio il thread
				weaselProxyThreadValidateSemantic.start();
				// mi pongo in attesa per 30000 millisecondi sull'istanza WeaselProxyRis				
				weaselProxyRis.wait(Properties.timeout);
				}
			} 
		catch (InterruptedException e) 
			{
			e.printStackTrace();
			}
		this.responded = weaselProxyRis.isResponded();
		return weaselProxyRis.getResponse();
		}

	/* (non-Javadoc)
	 * @see qNSolver.QNSolverPortProxy#validateSyntax_(java.lang.String, java.lang.String)
	 */
	@Override
	public String validateSyntax_(String pmif, String tipoModello)
			throws RemoteException 
		{
		// istanzio un oggetto WeaselProxyRis
		WeaselProxyRis weaselProxyRis = new WeaselProxyRis();
		QNSolverPortProxyWin solverPortProxyWin = new QNSolverPortProxyWin();
		// istanzio un oggetto WeaselProxyThreadValidateSyntax come thread
		WeaselProxyThreadValidateSyntax weaselProxyThreadValidateSyntax = 
			new WeaselProxyThreadValidateSyntax(weaselProxyRis,solverPortProxyWin,pmif,tipoModello);
		try {
			synchronized (weaselProxyRis) 
				{
				// avvio il thread
				weaselProxyThreadValidateSyntax.start();
				// mi pongo in attesa per 30000 millisecondi sull'istanza WeaselProxyRis				
				weaselProxyRis.wait(Properties.timeout);
				}
			} 
		catch (InterruptedException e) 
			{
			e.printStackTrace();
			}
		this.responded = weaselProxyRis.isResponded();
		return weaselProxyRis.getResponse();
		}

	@Override
	public boolean responded() 
		{
		return this.responded;
		}

	}
