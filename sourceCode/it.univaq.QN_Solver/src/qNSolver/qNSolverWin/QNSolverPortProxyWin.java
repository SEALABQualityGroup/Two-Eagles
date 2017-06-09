package qNSolver.qNSolverWin;

import qNSolver.QNSolverPortProxy;

public class QNSolverPortProxyWin 
	implements qNSolver.qNSolverWin.QNSolverPortWin, QNSolverPortProxy 
	{
  private String _endpoint = null;
  private qNSolver.qNSolverWin.QNSolverPortWin qNSolverPortWin = null;
  
  public QNSolverPortProxyWin() {
    _initQNSolverPortProxy();
  }
  
  public QNSolverPortProxyWin(String endpoint) {
    _endpoint = endpoint;
    _initQNSolverPortProxy();
  }
  
  private void _initQNSolverPortProxy() {
    try {
      qNSolverPortWin = (new qNSolver.qNSolverWin.QNSolverServiceLocatorWin()).getQNSolverPort();
      if (qNSolverPortWin != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)qNSolverPortWin)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)qNSolverPortWin)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (qNSolverPortWin != null)
      ((javax.xml.rpc.Stub)qNSolverPortWin)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public qNSolver.qNSolverWin.QNSolverPortWin getQNSolverPort() {
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin;
  }
  
  public java.lang.String copyright_() throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.copyright_();
  }
  
  public java.lang.String validateSyntax_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.validateSyntax_(model, model_type);
  }
  
  public java.lang.String validateSemantic_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.validateSemantic_(model, model_type);
  }
  
  public java.lang.String solve_(java.lang.String model, java.lang.String model_type, java.lang.String tool, java.lang.String method, java.lang.String params) throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.solve_(model, model_type, tool, method, params);
  }
  
  public java.lang.String transform_(java.lang.String model, java.lang.String model_type, java.lang.String tool, java.lang.String method, java.lang.String params) throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.transform_(model, model_type, tool, method, params);
  }
  
  public java.lang.String getModelType_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.getModelType_(model, model_type);
  }
  
  public java.lang.String getModelDescription_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.getModelDescription_(model, model_type);
  }
  
  public java.lang.String getToolsList_() throws java.rmi.RemoteException{
    if (qNSolverPortWin == null)
      _initQNSolverPortProxy();
    return qNSolverPortWin.getToolsList_();
  }
  
	@Override
	public boolean responded() 
		{
		return true;
		}

}