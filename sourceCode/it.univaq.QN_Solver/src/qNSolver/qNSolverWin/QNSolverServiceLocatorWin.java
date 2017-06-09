/**
 * QNSolverServiceLocatorLin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package qNSolver.qNSolverWin;

@SuppressWarnings("serial")
public class QNSolverServiceLocatorWin extends org.apache.axis.client.Service implements qNSolver.qNSolverWin.QNSolverServiceWin {

/**
 * This ia a web service for solution of queueing networks models
 * described in PMIF 2.0 XML/Modeling Language, using some external tools
 * for queueing networks problems solution.
 */

    public QNSolverServiceLocatorWin() {
    }


    public QNSolverServiceLocatorWin(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public QNSolverServiceLocatorWin(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for QNSolverPortLin
    private java.lang.String QNSolverPort_address = "http://weaselwin.univaq.it/Queueing_Network_Solver_Service.php";

    public void setQNSolverPortAddress(java.lang.String solverPort_address) 
    	{
		QNSolverPort_address = solverPort_address;
    	}

	public java.lang.String getQNSolverPortAddress() {
        return QNSolverPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String QNSolverPortWSDDServiceName = "QNSolverPortLin";

    public java.lang.String getQNSolverPortWSDDServiceName() {
        return QNSolverPortWSDDServiceName;
    }

    public void setQNSolverPortWSDDServiceName(java.lang.String name) {
        QNSolverPortWSDDServiceName = name;
    }

    public qNSolver.qNSolverWin.QNSolverPortWin getQNSolverPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(QNSolverPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getQNSolverPort(endpoint);
    }

    public qNSolver.qNSolverWin.QNSolverPortWin getQNSolverPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            qNSolver.qNSolverWin.QNSolverBindingStubWin _stub = new qNSolver.qNSolverWin.QNSolverBindingStubWin(portAddress, this);
            _stub.setPortName(getQNSolverPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setQNSolverPortEndpointAddress(java.lang.String address) {
        QNSolverPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("unchecked")
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (qNSolver.qNSolverWin.QNSolverPortWin.class.isAssignableFrom(serviceEndpointInterface)) {
                qNSolver.qNSolverWin.QNSolverBindingStubWin _stub = new qNSolver.qNSolverWin.QNSolverBindingStubWin(new java.net.URL(QNSolverPort_address), this);
                _stub.setPortName(getQNSolverPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("unchecked")
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("QNSolverPortLin".equals(inputPortName)) {
            return getQNSolverPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:qNSolver", "QNSolverServiceLin");
    }

    @SuppressWarnings("unchecked")
	private java.util.HashSet ports = null;

    @SuppressWarnings("unchecked")
	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:qNSolver", "QNSolverPortLin"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("QNSolverPortLin".equals(portName)) {
            setQNSolverPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
