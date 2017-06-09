/**
 * 
 */
package restrizioniGenerali.secondRelease;

import java.util.ArrayList;
import java.util.List;

import restrizioniGenerali.IGeneraliRules;
import restrizioniGenerali.RestrizioniGenException;
import specificheAEmilia.ArchiType;
import specificheAEmilia.ElemType;
import valutazione.NormalizeException;
import equivalenzaComportamentale.EquivalenzaFactory2;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviFiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviInfiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferIllimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferLimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;

/**
 * @author Mirko
 *
 */
public class GeneraliRules2 
	implements IGeneraliRules 
	{

	private ArchiType archiType;
	private List<IEquivalenza> tipiEquivalenza = new ArrayList<IEquivalenza>();
	
	public GeneraliRules2(ArchiType archiType) 
		{
		super();
		this.archiType = archiType;
		}

	/**
	 * La prima restrizione generale è che ogni AEI di una specifica AEmilia 
	 * deve essere un processo di arrivi,
	 * un buffer, un processo fork, un processo join, un processo di servizio o un processo di routing 
	 * e deve essere correttamente
	 * connesso agli altri AEI per ottenere una QN ben-formata.
	 *
	 * @return
	 * @throws RestrizioniGenException
	 */
	public boolean regola1() throws RestrizioniGenException
		{
		boolean b = true;
		// si verifica la tipologia degli elementi architetturali
		ArchiType at = this.archiType;
		ElemType[] elemTypes = at.getArchiElemTypes().getElementTypes();
		for (int i = 0; i < elemTypes.length; i++)
			{
			b = b && verificaTipoElemento(elemTypes[i]);
			}
		// la connessione non va verificata
		return b;
		}

	@Override
	public boolean isCompliantGeneralRules() 
		throws RestrizioniGenException 
		{
		return regola1();
		}
	
	/**
	 * Verifica se un tipo di elemento architetturale abbia un comportamento equivalente ad
	 * un elemento base di una rete di code.
	 *
	 * @return
	 * @throws NormalizeException
	 */
private boolean verificaTipoElemento(ElemType elemType) 
	throws RestrizioniGenException
		{
		IEquivalenzaBufferLimitato iEquivalenzaBufferLimitato = new EquivalenzaFactory2().getFCB();
		iEquivalenzaBufferLimitato.setEt(elemType);
		IEquivalenzaForkSenzaBuffer iEquivalenzaForkSenzaBuffer = new EquivalenzaFactory2().getFPNB();
		iEquivalenzaForkSenzaBuffer.setEt(elemType);
		IEquivalenzaForkConBuffer iEquivalenzaForkConBuffer = new EquivalenzaFactory2().getFPWB();
		iEquivalenzaForkConBuffer.setEt(elemType);
		IEquivalenzaJoin equivalenzaJoin = new EquivalenzaFactory2().getJP();
		equivalenzaJoin.setEt(elemType);
		IEquivalenzaArriviFiniti iEquivalenzaArriviFiniti = new EquivalenzaFactory2().getSCAP();
		iEquivalenzaArriviFiniti.setEt(elemType);
		IEquivalenzaServizioSenzaBuffer iEquivalenzaServizioSenzaBuffer = new EquivalenzaFactory2().getSPNB();
		iEquivalenzaServizioSenzaBuffer.setEt(elemType);
		IEquivalenzaServizioConBuffer iEquivalenzaServizioConBuffer = new EquivalenzaFactory2().getSPWB();
		iEquivalenzaServizioConBuffer.setEt(elemType);
		IEquivalenzaBufferIllimitato iEquivalenzaBufferIllimitato = new EquivalenzaFactory2().getUB();
		iEquivalenzaBufferIllimitato.setEt(elemType);
		IEquivalenzaArriviInfiniti iEquivalenzaArriviInfiniti = new EquivalenzaFactory2().getUPAP();
		iEquivalenzaArriviInfiniti.setEt(elemType);
		IEquivalenzaRoutingSenzaBuffer equivalenzaRoutingSenzaBuffer = new EquivalenzaFactory2().getRPNB();
		equivalenzaRoutingSenzaBuffer.setEt(elemType);
		IEquivalenzaRoutingConBuffer equivalenzaRoutingConBuffer = new EquivalenzaFactory2().getRPWB();
		equivalenzaRoutingConBuffer.setEt(elemType);
		if (iEquivalenzaBufferLimitato.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaBufferLimitato);
			return true;
			}
		else if (iEquivalenzaForkSenzaBuffer.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaForkSenzaBuffer);
			return true;
			}
		else if (iEquivalenzaForkConBuffer.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaForkConBuffer);
			return true;
			}
		else if (equivalenzaJoin.isEquivalente())
			{
			this.tipiEquivalenza.add(equivalenzaJoin);
			return true;
			}
		else if (iEquivalenzaArriviFiniti.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaArriviFiniti);
			return true;
			}
		else if (iEquivalenzaServizioSenzaBuffer.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaServizioSenzaBuffer);
			return true;
			}
		else if (iEquivalenzaServizioConBuffer.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaServizioConBuffer);
			return true;
			}
		else if (iEquivalenzaBufferIllimitato.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaBufferIllimitato);
			return true;
			}
		else if (iEquivalenzaArriviInfiniti.isEquivalente())
			{
			this.tipiEquivalenza.add(iEquivalenzaArriviInfiniti);
			return true;
			}
		else if (equivalenzaRoutingConBuffer.isEquivalente())
			{
			this.tipiEquivalenza.add(equivalenzaRoutingConBuffer);
			return true;
			}
		else if (equivalenzaRoutingSenzaBuffer.isEquivalente())
			{
			this.tipiEquivalenza.add(equivalenzaRoutingSenzaBuffer);
			return true;
			}
		else return false;
		}
	}
