package mappingAEIintoElementiBase.secondRelease;

import restrizioniSpecifiche.interfaces.ISpecificheFCB;
import restrizioniSpecifiche.interfaces.ISpecificheFPNB;
import restrizioniSpecifiche.interfaces.ISpecificheFPWB;
import restrizioniSpecifiche.interfaces.ISpecificheJPNB;
import restrizioniSpecifiche.interfaces.ISpecificheJPWB;
import restrizioniSpecifiche.interfaces.ISpecificheRPNB;
import restrizioniSpecifiche.interfaces.ISpecificheRPWB;
import restrizioniSpecifiche.interfaces.ISpecificheSCAP;
import restrizioniSpecifiche.interfaces.ISpecificheSPNB;
import restrizioniSpecifiche.interfaces.ISpecificheSPWB;
import restrizioniSpecifiche.interfaces.ISpecificheUB;
import restrizioniSpecifiche.interfaces.ISpecificheUPAP;
import elementiBaseQN.BufferIllimitato;
import elementiBaseQN.BufferLimitato;
import elementiBaseQN.ProcessoArriviFiniti;
import elementiBaseQN.ProcessoArriviInfiniti;
import elementiBaseQN.ProcessoForkConBuffer;
import elementiBaseQN.ProcessoForkSenzaBuffer;
import elementiBaseQN.ProcessoJoinConBuffer;
import elementiBaseQN.ProcessoJoinSenzaBuffer;
import elementiBaseQN.ProcessoRoutingConBuffer;
import elementiBaseQN.ProcessoRoutingSenzaBuffer;
import elementiBaseQN.ProcessoServizioConBuffer;
import elementiBaseQN.ProcessoServizioSenzaBuffer;

class Name 
	{
	
	/**
	 * Restituisce un oggetto BufferLimitato con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	BufferLimitato name(ISpecificheFCB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		BufferLimitato bufferLimitato = new BufferLimitato(string);
		return bufferLimitato;
		}

	/**
	 * Restituisce un oggetto ProcessoForkSenzaBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoForkSenzaBuffer name(ISpecificheFPNB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoForkSenzaBuffer processoForkSenzaBuffer = new ProcessoForkSenzaBuffer(string);
		return processoForkSenzaBuffer;
		}

	/**
	 * Restituisce un oggetto ProcessoForkConBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoForkConBuffer name(ISpecificheFPWB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoForkConBuffer processoForkConBuffer = new ProcessoForkConBuffer(string);
		return processoForkConBuffer;
		}

	/**
	 * Restituisce un oggetto ProcessoJoinSenzaBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoJoinSenzaBuffer name(ISpecificheJPNB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoJoinSenzaBuffer processoJoinSenzaBuffer = new ProcessoJoinSenzaBuffer(string);
		return processoJoinSenzaBuffer;
		}

	/**
	 * Restituisce un oggetto ProcessoJoinConBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoJoinConBuffer name(ISpecificheJPWB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoJoinConBuffer processoJoinConBuffer = new ProcessoJoinConBuffer(string);
		return processoJoinConBuffer;
		}

	/**
	 * Restituisce un oggetto ProcessoRoutingSenzaBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoRoutingSenzaBuffer name(ISpecificheRPNB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoRoutingSenzaBuffer processoRoutingSenzaBuffer = new ProcessoRoutingSenzaBuffer(string);
		return processoRoutingSenzaBuffer;
		}

	/**
	 * Restituisce un oggetto ProcessoRoutingConBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoRoutingConBuffer name(ISpecificheRPWB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoRoutingConBuffer processoRoutingConBuffer = new ProcessoRoutingConBuffer(string);
		return processoRoutingConBuffer;
		}

	/**
	 * Restituisce un oggetto ProcessoArriviFiniti con nome uguale al nome dell'istanza wrappata
	 * da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoArriviFiniti name(ISpecificheSCAP specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoArriviFiniti processoArriviFiniti = new ProcessoArriviFiniti(string);
		return processoArriviFiniti;
		}

	/**
	 * Restituisce un oggetto ProcessoServizioSenzaBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoServizioSenzaBuffer name(ISpecificheSPNB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoServizioSenzaBuffer processoServizioSenzaBuffer = new ProcessoServizioSenzaBuffer(string);
		return processoServizioSenzaBuffer;
		}

	/**
	 * Restituisce un oggetto ProcessoServizioConBuffer con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoServizioConBuffer name(ISpecificheSPWB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoServizioConBuffer processoServizioConBuffer = new ProcessoServizioConBuffer(string);
		return processoServizioConBuffer;
		}

	/**
	 * Restituisce un oggetto BufferIllimitato con nome uguale al nome dell'istanza wrappata da
	 * specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	BufferIllimitato name(ISpecificheUB specifiche)
		{
		String string = specifiche.getNomeIstanza();
		BufferIllimitato bufferIllimitato = new BufferIllimitato(string);
		return bufferIllimitato;
		}

	/**
	 * Restituisce un oggetto ProcessoArriviInfiniti con nome uguale al nome dell'istanza
	 * wrappata da specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	ProcessoArriviInfiniti name(ISpecificheUPAP specifiche)
		{
		String string = specifiche.getNomeIstanza();
		ProcessoArriviInfiniti processoArriviInfiniti = new ProcessoArriviInfiniti(string);
		return processoArriviInfiniti;
		}
	
	}
