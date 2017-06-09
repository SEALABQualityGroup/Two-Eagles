package qNSolver.qNSolverLin;

import qNSolver.QNSolverPortProxy;

public class QNSolverPortProxyLin 
	implements qNSolver.qNSolverLin.QNSolverPortLin, QNSolverPortProxy 
	{
  private String _endpoint = null;
  private qNSolver.qNSolverLin.QNSolverPortLin qNSolverPortLin = null;
  
  public QNSolverPortProxyLin() {
    _initQNSolverPortProxy();
  }
  
  public QNSolverPortProxyLin(String endpoint) {
    _endpoint = endpoint;
    _initQNSolverPortProxy();
  }
  
  private void _initQNSolverPortProxy() {
    try {
      qNSolverPortLin = (new qNSolver.qNSolverLin.QNSolverServiceLocatorLin()).getQNSolverPort();
      if (qNSolverPortLin != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)qNSolverPortLin)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)qNSolverPortLin)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (qNSolverPortLin != null)
      ((javax.xml.rpc.Stub)qNSolverPortLin)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public qNSolver.qNSolverLin.QNSolverPortLin getQNSolverPort() {
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin;
  }
  
  public java.lang.String copyright_() throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.copyright_();
  }
  
  public java.lang.String validateSyntax_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.validateSyntax_(model, model_type);
  }
  
  public java.lang.String validateSemantic_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.validateSemantic_(model, model_type);
  }
  
  public java.lang.String solve_(java.lang.String model, java.lang.String model_type, java.lang.String tool, java.lang.String method, java.lang.String params) throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.solve_(model, model_type, tool, method, params);
  }
  
  public java.lang.String transform_(java.lang.String model, java.lang.String model_type, java.lang.String tool, java.lang.String method, java.lang.String params) throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.transform_(model, model_type, tool, method, params);
  }
  
  public java.lang.String getModelType_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.getModelType_(model, model_type);
  }
  
  public java.lang.String getModelDescription_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.getModelDescription_(model, model_type);
  }
  
  public java.lang.String getToolsList_() throws java.rmi.RemoteException{
    if (qNSolverPortLin == null)
      _initQNSolverPortProxy();
    return qNSolverPortLin.getToolsList_();
  }
  
	@Override
	public boolean responded() 
		{
		return true;
		}
	}