package mappingAEIintoElementiBase;

import restrizioniSpecifiche.interfaces.ISpecifiche;
import elementiBaseQN.ElementoBaseQN;
import mappingAEIintoElementiBase.struttura.IMappingSpecifiche;
import mappingAEIintoElementiBase.struttura.ListaElementiBase;

public interface IFromAEIintoElementiBase {

	/**
	 * Trasforma ogni oggetto mappingSpecifiche corrispondenti alle istanze della specifica
	 * architetturale in elementi base, generando un oggetto ListaElementiBase.
	 *
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	public abstract ListaElementiBase generaElementiBase()
			throws AEIintoElementiBaseException;

	public abstract ListaElementiBase getElementiBase();

	/**
	 * Tale metodo esegue le seguenti operazioni:
	 *
	 * 1) Si normalizzano i processi di arrivi finiti.
	 * 2) Si normalizzano i processi di servizio multiserver.
	 * 3) Si aggiornano le destinazioni e le sorgenti di ogni elemento base a seconda
	 * dei collegamenti architetturali.
	 * 4) Si impostano i buffers degli elementi base.
	 * 5) Si normalizzano le probabilità di routing di ogni elemento base a seconda del numero
	 * delle destinazioni di ogni interazione di output.
	 *
	 * @throws AEIintoElementiBaseException
	 */
	public abstract void connettiElementiBase()
			throws AEIintoElementiBaseException;

	public boolean verificaUPAPJobsExit();
	
	public ElementoBaseQN getElementoBaseFromName(String string);
	
	public IMappingSpecifiche<ISpecifiche> getSpecificheObject(String string)
		throws AEIintoElementiBaseException;
	
	/**
	 * Assegna le classi di clienti alle interazioni per ogni oggetto IMappingSpecifiche.
	 * Restituisce true se e solo se ogni interazione ha al più una classe di clienti
	 * associata.
	 * 
	 */
	public abstract boolean assegnaClassi();

	}