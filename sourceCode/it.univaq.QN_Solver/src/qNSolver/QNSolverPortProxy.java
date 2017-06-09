package qNSolver;

import java.rmi.RemoteException;

public interface QNSolverPortProxy 
	{

	public String getToolsList_() throws RemoteException;

	public String validateSyntax_(String pmif, String tipoModello)
		throws RemoteException;

	public String validateSemantic_(String pmif, String tipoModello)
		throws RemoteException;

	public String solve_(String pmif, String tipoModello, String name,
			String string, String string2) throws RemoteException;

	public boolean responded();

	}
