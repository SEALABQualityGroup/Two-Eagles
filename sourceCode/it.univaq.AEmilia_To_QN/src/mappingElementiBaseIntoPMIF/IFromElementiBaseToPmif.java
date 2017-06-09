package mappingElementiBaseIntoPMIF;

import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;
import elementiBaseQN.ElementoBaseQN;

public interface IFromElementiBaseToPmif {

	/**
	 * Effettua il mapping di elementoBaseQN in elementi PMIF. elementoBaseQN può essere un'istanza di:
	 * BufferIllimitato, BufferLimitato, ProcessoArriviFiniti, ProcessoArriviInfiniti, ProcessoForkConBuffer,
	 * ProcessoForkSenzaBuffer, ProcessoJoinConBuffer, ProcessoJoinSenzaBuffer, ProcessoServizioConBuffer o
	 * ProcessoServizioSenzaBuffer.
	 *
	 * @param elementoBaseQN
	 * @throws FilteringElementiBaseException 
	 * @throws MappingElementiBaseException 
	 */
	public abstract void mappingElementoBase(ElementoBaseQN elementoBaseQN)
			throws MappingElementiBaseException, FilteringElementiBaseException;

	/**
	 * Restituisce l'elemento QueueingNetworkModel, radice
	 * del modello a rete di code.
	 *
	 * @return
	 */
	public abstract QNMTypeEq getRadice();

}