/**
 * 
 */
package restrizioniSpecifiche.secondRelease;

import java.util.List;

import restrizioniSpecifiche.ISpecificheFactory;
import restrizioniSpecifiche.RestrizioniSpecException;
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
import equivalenzaComportamentale.EquivalenzaFactory2;
import equivalenzaComportamentale.interfaces.IEquivalenza;

/**
 * @author Mirko
 *
 */
public class Specifiche2Factory 
	implements ISpecificheFactory 
	{

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheArriviFiniti(specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheArriviFiniti createSpecificheArriviFiniti(
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheArriviFiniti2(archiType);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheFCB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheFCB createSpecificheFCB(AEIdecl idecl, ArchiType archiType)
			throws RestrizioniSpecException 
		{
		return new SpecificheFCB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheFCB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheFCB createSpecificheFCB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheFCB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheFPNB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheFPNB createSpecificheFPNB(AEIdecl idecl,
			ArchiType archiType) 
		throws RestrizioniSpecException 
		{
		return new SpecificheFPNB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheFPNB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheFPNB createSpecificheFPNB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheFPNB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheFPWB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheFPWB createSpecificheFPWB(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheFPWB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheFPWB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheFPWB createSpecificheFPWB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheFPWB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheJPNB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheJPNB createSpecificheJPNB(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheJPNB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheJPNB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheJPNB createSpecificheJPNB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheJPNB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheJPWB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheJPWB createSpecificheJPWB(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheJPWB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheJPWB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheJPWB createSpecificheJPWB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheJPWB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheMultiServer(specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheMultiServer createSpecificheMultiServer(
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheMultiServer2(archiType);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheSCAP(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheSCAP createSpecificheSCAP(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheSCAP2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheSCAP(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheSCAP createSpecificheSCAP(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheSCAP2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheSPNB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheSPNB createSpecificheSPNB(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheSPNB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheSPNB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheSPNB createSpecificheSPNB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheSPNB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheSPWB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheSPWB createSpecificheSPWB(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheSPWB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheSPWB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheSPWB createSpecificheSPWB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheSPWB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheUB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheUB createSpecificheUB(AEIdecl idecl, ArchiType archiType)
			throws RestrizioniSpecException 
		{
		return new SpecificheUB2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheUB(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheUB createSpecificheUB(AEIdecl idecl, ArchiType archiType,
			List<IEquivalenza> list) throws RestrizioniSpecException 
		{
		return new SpecificheUB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheUPAP(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType)
	 */
	@Override
	public ISpecificheUPAP createSpecificheUPAP(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheUPAP2(archiType, idecl, new EquivalenzaFactory2());
		}

	/* (non-Javadoc)
	 * @see restrizioniSpecifiche.ISpecificheFactory#createSpecificheUPAP(specificheAEmilia.AEIdecl, specificheAEmilia.ArchiType, java.util.List)
	 */
	@Override
	public ISpecificheUPAP createSpecificheUPAP(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheUPAP2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	@Override
	public ISpecificheRPNB createSpecificheRPNB(AEIdecl idecl,
			ArchiType archiType) throws RestrizioniSpecException 
		{
		return new SpecificheRPNB2(archiType, idecl, new EquivalenzaFactory2());
		}

	@Override
	public ISpecificheRPWB createSpecificheRPWB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheRPWB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	@Override
	public ISpecificheRPNB createSpecificheRPNB(AEIdecl idecl,
			ArchiType archiType, List<IEquivalenza> list)
			throws RestrizioniSpecException 
		{
		return new SpecificheRPNB2(archiType, idecl, new EquivalenzaFactory2(), list);
		}

	@Override
	public ISpecificheRPWB createSpecificheRPWB(AEIdecl idecl,
			ArchiType archiType) 
		throws RestrizioniSpecException 
		{
		return new SpecificheRPWB2(archiType, idecl, new EquivalenzaFactory2());
		}
	}
