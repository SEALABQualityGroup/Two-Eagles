/**
 * QNSolverPortLin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package qNSolver.qNSolverWin;

public interface QNSolverPortWin extends java.rmi.Remote {
    public java.lang.String copyright_() throws java.rmi.RemoteException;
    public java.lang.String validateSyntax_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException;
    public java.lang.String validateSemantic_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException;
    public java.lang.String solve_(java.lang.String model, java.lang.String model_type, java.lang.String tool, java.lang.String method, java.lang.String params) throws java.rmi.RemoteException;
    public java.lang.String transform_(java.lang.String model, java.lang.String model_type, java.lang.String tool, java.lang.String method, java.lang.String params) throws java.rmi.RemoteException;
    public java.lang.String getModelType_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException;
    public java.lang.String getModelDescription_(java.lang.String model, java.lang.String model_type) throws java.rmi.RemoteException;
    public java.lang.String getToolsList_() throws java.rmi.RemoteException;
}
