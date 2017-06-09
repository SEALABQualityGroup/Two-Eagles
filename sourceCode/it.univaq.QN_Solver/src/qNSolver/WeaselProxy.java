package qNSolver;

import qNSolver.qNSolverAsync.QNSolverPortProxyLinAsync;
import qNSolver.qNSolverAsync.QNSolverPortProxyWinAsync;

public class WeaselProxy 
	{

	private static QNSolverPortProxy portProxy;

	public static String[] servers = {"Windows","Linux"};
	
	public static String tipoModello = "PMIF 2.0";
	
	public static QNSolverPortProxy getProxy(String string)
		{
		if (string.equals(servers[0]))
			portProxy = new QNSolverPortProxyWinAsync();
		else if (string.equals(servers[1]))
			portProxy = new QNSolverPortProxyLinAsync();
		return portProxy;
		}
	}
