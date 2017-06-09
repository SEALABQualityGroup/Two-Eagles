package equivalenzaComportamentale;

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
 * Fornisce dei metodi per ottenere i wrapper per
 * la verifica delle equivalenze comportamentali con elementi
 * di una rete di code da parte di componenti della specifica.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public interface EquivalenzaFactory {

	/**
	 * Restituisce il wrapper per verificare se una componente 
	 * è equivalente ad un buffer limitato.
	 *
	 * @return
	 */
	public IEquivalenzaBufferLimitato getFCB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo fork senza buffer.
	 *
	 * @return
	 */
	public IEquivalenzaForkSenzaBuffer getFPNB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo fork con buffer.
	 *
	 * @return
	 */
	public IEquivalenzaForkConBuffer getFPWB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo join senza buffer.
	 *
	 * @return
	 */
	public IEquivalenzaJoinSenzaBuffer getJPNB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo join con buffer.
	 *
	 * @return
	 */
	public IEquivalenzaJoinConBuffer getJPWB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo di arrivi finiti.
	 *
	 * @return
	 */
	public IEquivalenzaArriviFiniti getSCAP();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo di servizio senza buffer.
	 *
	 * @return
	 */
	public IEquivalenzaServizioSenzaBuffer getSPNB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo di servizio con buffer.
	 *
	 * @return
	 */
	public IEquivalenzaServizioConBuffer getSPWB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un buffer illimitato.
	 *
	 * @return
	 */
	public IEquivalenzaBufferIllimitato getUB();

	/**
	 * Restituisce il wrapper per verificare se una componente
	 * è equivalente ad un processo di arrivi infiniti.
	 *
	 * @return
	 */
	public IEquivalenzaArriviInfiniti getUPAP();
	
	/**
	 * Restituisce il wrapper per verificare se una componente 
	 * è equivalente ad un processo
	 * di routing con buffer.
	 * 
	 * @return
	 */
	public IEquivalenzaRoutingConBuffer getRPWB();
	
	/**
	 * Restituisce il wrapper per verificare se una componente 
	 * è equivalente ad un processo
	 * di routing senza buffer.
	 * 
	 * @return
	 */
	public IEquivalenzaRoutingSenzaBuffer getRPNB();
	
	/**
	 * Restituisce il wrapper per verificare se una componente 
	 * è equivalente ad un processo join.
	 * 
	 * @return
	 */
	public IEquivalenzaJoin getJP();
	
	}
