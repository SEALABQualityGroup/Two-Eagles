package restrizioniSpecifiche;

import java.util.List;

import restrizioniSpecifiche.interfaces.ISpecificheArriviFiniti;
import restrizioniSpecifiche.interfaces.ISpecificheFCB;
import restrizioniSpecifiche.interfaces.ISpecificheFPNB;
import restrizioniSpecifiche.interfaces.ISpecificheFPWB;
import restrizioniSpecifiche.interfaces.ISpecificheJPNB;
import restrizioniSpecifiche.interfaces.ISpecificheJPWB;
import restrizioniSpecifiche.interfaces.ISpecificheMultiServer;
import restrizioniSpecifiche.interfaces.ISpecificheRPNB;
import restrizioniSpecifiche.interfaces.ISpecificheRPWB;
import restrizioniSpecifiche.interfaces.ISpecificheSCAP;
import restrizioniSpecifiche.interfaces.ISpecificheSPNB;
import restrizioniSpecifiche.interfaces.ISpecificheSPWB;
import restrizioniSpecifiche.interfaces.ISpecificheUB;
import restrizioniSpecifiche.interfaces.ISpecificheUPAP;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import equivalenzaComportamentale.interfaces.IEquivalenza;

public interface ISpecificheFactory {

	public abstract ISpecificheArriviFiniti createSpecificheArriviFiniti(
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheFCB createSpecificheFCB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheFCB createSpecificheFCB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheFPNB createSpecificheFPNB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheFPNB createSpecificheFPNB(AEIdecl idecl,
			ArchiType archiType, 
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheFPWB createSpecificheFPWB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheFPWB createSpecificheFPWB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheJPNB createSpecificheJPNB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheJPNB createSpecificheJPNB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheJPWB createSpecificheJPWB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheJPWB createSpecificheJPWB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheMultiServer createSpecificheMultiServer(
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheSCAP createSpecificheSCAP(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheSCAP createSpecificheSCAP(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheSPNB createSpecificheSPNB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheSPNB createSpecificheSPNB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheSPWB createSpecificheSPWB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheSPWB createSpecificheSPWB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheUB createSpecificheUB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheUB createSpecificheUB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheUPAP createSpecificheUPAP(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheUPAP createSpecificheUPAP(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	public abstract ISpecificheRPNB createSpecificheRPNB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheRPNB createSpecificheRPNB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException;

	public abstract ISpecificheRPWB createSpecificheRPWB(AEIdecl idecl,
			ArchiType archiType)
			throws RestrizioniSpecException;

	public abstract ISpecificheRPWB createSpecificheRPWB(AEIdecl idecl,
			ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException;

	}