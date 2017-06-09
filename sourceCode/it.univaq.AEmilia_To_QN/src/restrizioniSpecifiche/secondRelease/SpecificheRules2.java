/**
 * 
 */
package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;

import restrizioniSpecifiche.ASpecificheRules;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheArriviFiniti;
import restrizioniSpecifiche.interfaces.ISpecificheJPNB;
import restrizioniSpecifiche.interfaces.ISpecificheJPWB;
import restrizioniSpecifiche.interfaces.ISpecificheMultiServer;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiTopology;
import specificheAEmilia.ArchiType;
import specificheAEmilia.ElemType;
import valutazione.NormalizeException;
import valutazione.NormalizeParts;
import valutazione.Scope;
import equivalenzaComportamentale.EquivalenzaFactory2;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviFiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviInfiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferIllimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferLimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoinConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoinSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;

/**
 * @author Mirko
 *
 */
public class SpecificheRules2 
	extends ASpecificheRules 
	{

	public SpecificheRules2(ArchiType archiType)
		throws RestrizioniSpecException
		{
		super();
		// si normalizza il tipo architetturale
		ArchiType archiType2 = normalizzaTipo(archiType);
		this.archiType = archiType2;
		this.listaSpecifiche = new ArrayList<ISpecifiche>();
		// per istanziare gli oggetti Specifiche si costruisce la lista di tutti gli oggetti
		// IEquivalenza, wrappando ogni istanza di un tipo di elemento architetturale dichiarato
		List<IEquivalenza> list = new ArrayList<IEquivalenza>();
		ArchiTopology archiTopology = this.archiType.getTopologia();
		ArchiElemInstances archiElemInstances = archiTopology.getAEIs();
		AEIdecl[] idecls = archiElemInstances.getAEIdeclSeq();
		Scope scope;
		try {
			scope = new Scope(this.archiType);
			} 
		catch (NormalizeException e) 
			{
			throw new RestrizioniSpecException(e);
			}
		NormalizeParts normalizeParts = new NormalizeParts(scope);
		for (AEIdecl idecl : idecls)
			{
			ElemType elemType;
			try {
				elemType = normalizeParts.normalizeElemTypeFromAEI(idecl);
				} 
			catch (NormalizeException e) 
				{
				throw new RestrizioniSpecException(e);
				}
			IEquivalenzaBufferLimitato iEquivalenzaBufferLimitato = new EquivalenzaFactory2().getFCB();
			iEquivalenzaBufferLimitato.setEt(elemType);
			iEquivalenzaBufferLimitato.setAEIdecl(idecl);
			IEquivalenzaForkSenzaBuffer iEquivalenzaForkSenzaBuffer = new EquivalenzaFactory2().getFPNB();
			iEquivalenzaForkSenzaBuffer.setEt(elemType);
			iEquivalenzaForkSenzaBuffer.setAEIdecl(idecl);
			IEquivalenzaForkConBuffer iEquivalenzaForkConBuffer = new EquivalenzaFactory2().getFPWB();
			iEquivalenzaForkConBuffer.setEt(elemType);
			iEquivalenzaForkConBuffer.setAEIdecl(idecl);
			IEquivalenzaJoin equivalenzaJoin = new EquivalenzaFactory2().getJP();
			equivalenzaJoin.setEt(elemType);
			equivalenzaJoin.setAEIdecl(idecl);
			IEquivalenzaArriviFiniti iEquivalenzaArriviFiniti = new EquivalenzaFactory2().getSCAP();
			iEquivalenzaArriviFiniti.setEt(elemType);
			iEquivalenzaArriviFiniti.setAEIdecl(idecl);
			IEquivalenzaServizioSenzaBuffer iEquivalenzaServizioSenzaBuffer = new EquivalenzaFactory2().getSPNB();
			iEquivalenzaServizioSenzaBuffer.setEt(elemType);
			iEquivalenzaServizioSenzaBuffer.setAEIdecl(idecl);
			IEquivalenzaServizioConBuffer iEquivalenzaServizioConBuffer = new EquivalenzaFactory2().getSPWB();
			iEquivalenzaServizioConBuffer.setEt(elemType);
			iEquivalenzaServizioConBuffer.setAEIdecl(idecl);
			IEquivalenzaBufferIllimitato iEquivalenzaBufferIllimitato = new EquivalenzaFactory2().getUB();
			iEquivalenzaBufferIllimitato.setEt(elemType);
			iEquivalenzaBufferIllimitato.setAEIdecl(idecl);
			IEquivalenzaArriviInfiniti iEquivalenzaArriviInfiniti = new EquivalenzaFactory2().getUPAP();
			iEquivalenzaArriviInfiniti.setEt(elemType);
			iEquivalenzaArriviInfiniti.setAEIdecl(idecl);
			IEquivalenzaRoutingConBuffer equivalenzaRoutingConBuffer = new EquivalenzaFactory2().getRPWB();
			equivalenzaRoutingConBuffer.setEt(elemType);
			equivalenzaRoutingConBuffer.setAEIdecl(idecl);
			IEquivalenzaRoutingSenzaBuffer equivalenzaRoutingSenzaBuffer = new EquivalenzaFactory2().getRPNB();
			equivalenzaRoutingSenzaBuffer.setEt(elemType);
			equivalenzaRoutingSenzaBuffer.setAEIdecl(idecl);
			if (iEquivalenzaBufferLimitato.isEquivalente())
				list.add(iEquivalenzaBufferLimitato);
			else if (iEquivalenzaForkSenzaBuffer.isEquivalente())
				list.add(iEquivalenzaForkSenzaBuffer);
			else if (iEquivalenzaForkConBuffer.isEquivalente())
				list.add(iEquivalenzaForkConBuffer);
			else if (equivalenzaJoin.isEquivalente())
				list.add(equivalenzaJoin);
			else if (iEquivalenzaArriviFiniti.isEquivalente())
				list.add(iEquivalenzaArriviFiniti);
			else if (iEquivalenzaServizioSenzaBuffer.isEquivalente())
				list.add(iEquivalenzaServizioSenzaBuffer);
			else if (iEquivalenzaServizioConBuffer.isEquivalente())
				list.add(iEquivalenzaServizioConBuffer);
			else if (iEquivalenzaBufferIllimitato.isEquivalente())
				list.add(iEquivalenzaBufferIllimitato);
			else if (iEquivalenzaArriviInfiniti.isEquivalente())
				list.add(iEquivalenzaArriviInfiniti);
			else if (equivalenzaRoutingConBuffer.isEquivalente())
				list.add(equivalenzaRoutingConBuffer);
			else if (equivalenzaRoutingSenzaBuffer.isEquivalente())
				list.add(equivalenzaRoutingSenzaBuffer);
			}
		// per ogni oggetto IEquivalenza di list
		// si istanzia il corrispondente oggetto Specifiche e lo si aggiunge a
		// listaSpecifiche
		for (IEquivalenza equivalenza : list)
			{
			// a seconda del tipo di equivalenza si istanzia il corrispondente
			// oggetto Specifiche e lo si aggiunge a listaSpecifiche
			ISpecifiche specifiche = SpecificheRules2.getSpecificheFromEquivalenza(this.archiType, list, equivalenza);
			this.listaSpecifiche.add(specifiche);
			}
		}
	
	/**
	 * Viene utilizzato per la normalizzazione del tipo architetturale.
	 * 
	 * @param archiType2
	 * @return
	 * @throws RestrizioniSpecException
	 */
    private ArchiType normalizzaTipo(ArchiType archiType2)
		throws RestrizioniSpecException
		{
    	try {
    		Scope scope = new Scope(archiType2);
    		NormalizeParts normalizeParts = new NormalizeParts(scope);
    		ArchiType archiType = normalizeParts.normalizeAll();
    		return archiType;
			}
    	catch (NormalizeException e)
			{
    		throw new RestrizioniSpecException(e);
			}
		}
    
	public boolean isCompliantSpecificRules() throws RestrizioniSpecException {
		boolean b = true;
		// si verificano le regole di restrizione per ogni elemento presente in listaSpecifiche
		for (ISpecifiche specifiche : this.listaSpecifiche)
			{
			b = b && specifiche.isCompliantSpecificRules();
			}
		// si verificano le regole di restrizione specifica relative a tutti i processi
		// di arrivi per una popolazione finita
		ISpecificheArriviFiniti specificheArriviFiniti = 
			new Specifiche2Factory().createSpecificheArriviFiniti(this.archiType);
		b = b && specificheArriviFiniti.isCompliantSpecificRules();
		// si verificano le regole di restrizione specifica relative ai 
		// centri di servizio multiserver
		ISpecificheMultiServer specificheMultiServer =
			new Specifiche2Factory().createSpecificheMultiServer(this.archiType);
		b = b && specificheMultiServer.isCompliantSpecificRules();
		return b;
		}
	
	/**
	 * Restituisce l'iEquivalenza relativa a idecl.
	 * Se l'ElemType relativo a idecl non è equivalente
	 * a nessun elemento base di una rete di code si solleva un'eccezione.
	 *
	 * @param idecl
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public static IEquivalenza getEquivalenzaFromElemType(ArchiType archiType, AEIdecl idecl)
			throws RestrizioniSpecException {
			// si preleva il tipo di elemento architetturale di idecl normalizzato
			ElemType elemType = null;
			try {
				Scope scope2 = new Scope(archiType);
				NormalizeParts normalizeParts = new NormalizeParts(scope2);
				elemType = normalizeParts.normalizeElemTypeFromAEI(idecl);
				}
			catch (NormalizeException e)
				{
				throw new RestrizioniSpecException(e);
				}
			IEquivalenzaBufferIllimitato iEquivalenzaBufferIllimitato = new EquivalenzaFactory2().getUB();
			IEquivalenzaBufferLimitato iEquivalenzaBufferLimitato = new EquivalenzaFactory2().getFCB();
			IEquivalenzaArriviInfiniti iEquivalenzaArriviInfiniti = new EquivalenzaFactory2().getUPAP();
			IEquivalenzaArriviFiniti iEquivalenzaArriviFiniti = new EquivalenzaFactory2().getSCAP();
			IEquivalenzaForkConBuffer iEquivalenzaForkConBuffer = new EquivalenzaFactory2().getFPWB();
			IEquivalenzaForkSenzaBuffer iEquivalenzaForkSenzaBuffer = new EquivalenzaFactory2().getFPNB();
			IEquivalenzaJoinConBuffer iEquivalenzaJoinConBuffer = new EquivalenzaFactory2().getJPWB();
			IEquivalenzaJoinSenzaBuffer iEquivalenzaJoinSenzaBuffer = new EquivalenzaFactory2().getJPNB();
			IEquivalenzaJoin equivalenzaJoin = new EquivalenzaFactory2().getJP();
			IEquivalenzaServizioConBuffer iEquivalenzaServizioConBuffer = new EquivalenzaFactory2().getSPWB();
			IEquivalenzaServizioSenzaBuffer iEquivalenzaServizioSenzaBuffer = new EquivalenzaFactory2().getSPNB();
			IEquivalenzaRoutingConBuffer equivalenzaRoutingConBuffer = new EquivalenzaFactory2().getRPWB();
			IEquivalenzaRoutingSenzaBuffer equivalenzaRoutingSenzaBuffer = new EquivalenzaFactory2().getRPNB();
			iEquivalenzaArriviFiniti.setEt(elemType);
			iEquivalenzaArriviFiniti.setAEIdecl(idecl);
			iEquivalenzaArriviInfiniti.setEt(elemType);
			iEquivalenzaArriviInfiniti.setAEIdecl(idecl);
			iEquivalenzaBufferIllimitato.setEt(elemType);
			iEquivalenzaBufferIllimitato.setAEIdecl(idecl);
			iEquivalenzaBufferLimitato.setEt(elemType);
			iEquivalenzaBufferLimitato.setAEIdecl(idecl);
			iEquivalenzaForkConBuffer.setEt(elemType);
			iEquivalenzaForkConBuffer.setAEIdecl(idecl);
			iEquivalenzaForkSenzaBuffer.setEt(elemType);
			iEquivalenzaForkSenzaBuffer.setAEIdecl(idecl);
			equivalenzaRoutingConBuffer.setEt(elemType);
			equivalenzaRoutingConBuffer.setAEIdecl(idecl);
			equivalenzaRoutingSenzaBuffer.setEt(elemType);
			equivalenzaRoutingSenzaBuffer.setAEIdecl(idecl);
			if (iEquivalenzaJoinConBuffer != null)
				{
				iEquivalenzaJoinConBuffer.setEt(elemType);
				iEquivalenzaJoinConBuffer.setAEIdecl(idecl);
				}
			if (iEquivalenzaJoinSenzaBuffer != null)
				{
				iEquivalenzaJoinSenzaBuffer.setEt(elemType);
				iEquivalenzaJoinSenzaBuffer.setAEIdecl(idecl);
				}
			if (equivalenzaJoin != null)
				{
				equivalenzaJoin.setEt(elemType);
				equivalenzaJoin.setAEIdecl(idecl);
				}
			iEquivalenzaServizioConBuffer.setEt(elemType);
			iEquivalenzaServizioConBuffer.setAEIdecl(idecl);
			iEquivalenzaServizioSenzaBuffer.setEt(elemType);
			iEquivalenzaServizioSenzaBuffer.setAEIdecl(idecl);
			if (iEquivalenzaArriviFiniti.isEquivalente())
				return iEquivalenzaArriviFiniti;
			else if (iEquivalenzaArriviInfiniti.isEquivalente())
				return iEquivalenzaArriviInfiniti;
			else if (iEquivalenzaBufferIllimitato.isEquivalente())
				return iEquivalenzaBufferIllimitato;
			else if (iEquivalenzaBufferLimitato.isEquivalente())
				return iEquivalenzaBufferLimitato;
			else if (iEquivalenzaForkConBuffer.isEquivalente())
				return iEquivalenzaForkConBuffer;
			else if (iEquivalenzaForkSenzaBuffer.isEquivalente())
				return iEquivalenzaForkSenzaBuffer;
			else if (iEquivalenzaJoinConBuffer != null && iEquivalenzaJoinConBuffer.isEquivalente())
				return iEquivalenzaJoinConBuffer;
			else if (iEquivalenzaJoinSenzaBuffer != null && iEquivalenzaJoinSenzaBuffer.isEquivalente())
				return iEquivalenzaJoinSenzaBuffer;
			else if (equivalenzaJoin != null && equivalenzaJoin.isEquivalente())
				return equivalenzaJoin;
			else if (iEquivalenzaServizioConBuffer.isEquivalente())
				return iEquivalenzaServizioConBuffer;
			else if (iEquivalenzaServizioSenzaBuffer.isEquivalente())
				return iEquivalenzaServizioSenzaBuffer;
			else if (equivalenzaRoutingConBuffer.isEquivalente())
				return equivalenzaRoutingConBuffer;
			else if (equivalenzaRoutingSenzaBuffer.isEquivalente())
				return equivalenzaRoutingSenzaBuffer;
			else throw new RestrizioniSpecException(idecl.getName()+" is not basic element equivalent");
			}
	
	/**
	 * Restituisce una lista di oggetti IEquivalenza corrispondenti agli AEI dichiarati in di list2,
	 * secondo equivalenzaFactory.
	 * Viene sollevata un'eccezione se un ElemType non è equivalente a nessun elemento base di una
	 * rete di code.
	 *
	 * @param list
	 * @param distribuzioniFase
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public static List<IEquivalenza> getEquivalenze(ArchiType archiType, List<AEIdecl> list2)
			throws RestrizioniSpecException {
			List<IEquivalenza> list3 = new ArrayList<IEquivalenza>();
			for (AEIdecl idecl : list2)
				{
				IEquivalenza iEquivalenza =
					getEquivalenzaFromElemType(archiType, idecl);
				list3.add(iEquivalenza);
				}
			return list3;
			}
	
	/**
	 * Restituisce l'oggetto Specifiche relative a iEquivalenza.
	 * 
	 * @param archiType
	 * @param equivalenzaFactory
	 * @param list
	 * @param iEquivalenza
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public static ISpecifiche getSpecificheFromEquivalenza(ArchiType archiType,
			List<IEquivalenza> list, IEquivalenza iEquivalenza) throws RestrizioniSpecException {
			ISpecifiche specifiche = null;
			if (iEquivalenza instanceof IEquivalenzaArriviFiniti)
				{
				IEquivalenzaArriviFiniti iEquivalenzaArriviFiniti = (IEquivalenzaArriviFiniti)iEquivalenza;
				AEIdecl idecl = iEquivalenzaArriviFiniti.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheSCAP(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaArriviInfiniti)
				{
				IEquivalenzaArriviInfiniti iEquivalenzaArriviInfiniti = (IEquivalenzaArriviInfiniti)iEquivalenza;
				AEIdecl idecl = iEquivalenzaArriviInfiniti.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheUPAP(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaBufferIllimitato)
				{
				IEquivalenzaBufferIllimitato iEquivalenzaBufferIllimitato = (IEquivalenzaBufferIllimitato)iEquivalenza;
				AEIdecl idecl = iEquivalenzaBufferIllimitato.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheUB(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaBufferLimitato)
				{
				IEquivalenzaBufferLimitato iEquivalenzaBufferLimitato = (IEquivalenzaBufferLimitato)iEquivalenza;
				AEIdecl idecl = iEquivalenzaBufferLimitato.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheFCB(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaForkConBuffer)
				{
				IEquivalenzaForkConBuffer iEquivalenzaForkConBuffer = (IEquivalenzaForkConBuffer)iEquivalenza;
				AEIdecl idecl = iEquivalenzaForkConBuffer.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheFPWB(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaForkSenzaBuffer)
				{
				IEquivalenzaForkSenzaBuffer iEquivalenzaForkSenzaBuffer = (IEquivalenzaForkSenzaBuffer)iEquivalenza;
				AEIdecl idecl = iEquivalenzaForkSenzaBuffer.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheFPNB(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaJoin)
				{
				IEquivalenzaJoin iEquivalenzaJoin = (IEquivalenzaJoin)iEquivalenza;
				AEIdecl idecl = iEquivalenzaJoin.getAEIdecl();
				ISpecificheJPWB specifiche1 = new Specifiche2Factory().createSpecificheJPWB(idecl, archiType,
						list);
				ISpecificheJPNB specifiche2 = new Specifiche2Factory().createSpecificheJPNB(idecl, archiType, 
						list);
				if (specifiche1.isCompliantSpecificRules())
					specifiche = specifiche1;
				if (specifiche2.isCompliantSpecificRules())
					specifiche = specifiche2;
				}
			else if (iEquivalenza instanceof IEquivalenzaServizioConBuffer)
				{
				IEquivalenzaServizioConBuffer iEquivalenzaServizioConBuffer = (IEquivalenzaServizioConBuffer)iEquivalenza;
				AEIdecl idecl = iEquivalenzaServizioConBuffer.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheSPWB(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaServizioSenzaBuffer)
				{
				IEquivalenzaServizioSenzaBuffer iEquivalenzaServizioSenzaBuffer = (IEquivalenzaServizioSenzaBuffer)iEquivalenza;
				AEIdecl idecl = iEquivalenzaServizioSenzaBuffer.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheSPNB(idecl, archiType,
						list);
				}
			else if (iEquivalenza instanceof IEquivalenzaRoutingConBuffer)
				{
				IEquivalenzaRoutingConBuffer equivalenzaRoutingConBuffer =
					(IEquivalenzaRoutingConBuffer)iEquivalenza;
				AEIdecl idecl = equivalenzaRoutingConBuffer.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheRPWB(idecl, archiType, list);
				}
			else if (iEquivalenza instanceof IEquivalenzaRoutingSenzaBuffer)
				{
				IEquivalenzaRoutingSenzaBuffer equivalenzaRoutingSenzaBuffer =
					(IEquivalenzaRoutingSenzaBuffer)iEquivalenza;
				AEIdecl idecl = equivalenzaRoutingSenzaBuffer.getAEIdecl();
				specifiche = new Specifiche2Factory().createSpecificheRPNB(idecl, archiType, list);
				}
			return specifiche;
			}
	}