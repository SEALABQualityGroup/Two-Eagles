/**
 * QNSolverServiceLin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package qNSolver.qNSolverLin;

public interface QNSolverServiceLin extends javax.xml.rpc.Service {

/**
 * This ia a web service for solution of queueing networks models
 * described in PMIF 2.0 XML/Modeling Language, using some external tools
 * for queueing networks problems solution.
 */
    public java.lang.String getQNSolverPortAddress();

    public qNSolver.qNSolverLin.QNSolverPortLin getQNSolverPort() throws javax.xml.rpc.ServiceException;

    public qNSolver.qNSolverLin.QNSolverPortLin getQNSolverPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
