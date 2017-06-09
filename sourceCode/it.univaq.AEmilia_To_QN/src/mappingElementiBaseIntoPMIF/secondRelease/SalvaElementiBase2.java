package mappingElementiBaseIntoPMIF.secondRelease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mappingElementiBaseIntoPMIF.FilteringElementiBaseException;
import mappingElementiBaseIntoPMIF.IFromElementiBaseToPmif;
import mappingElementiBaseIntoPMIF.ISalvaElementiBase;
import mappingElementiBaseIntoPMIF.MappingElementiBaseException;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;

import com.schema.DemandServType;
import com.schema.ServiceRequestType;
import com.schema.TimeServType;
import com.schema.WorkUnitServType;
import com.schema.pmifschemaDoc;

import config.ServiceTimeFactory;
import elementiBaseQN.Buffer;
import elementiBaseQN.BufferLimitato;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;
import elementiBaseQN.ProcessoArrivi;
import elementiBaseQN.ProcessoFork;
import elementiBaseQN.ProcessoJoin;
import elementiBaseQN.ProcessoRoutingSenzaBuffer;
import elementiBaseQN.ProcessoServizio;
import elementiBaseQN.ProcessoServizioSenzaBuffer;
import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataBuffer;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;

/**
 * Classe utilizzata per salvare in un file XML gli elementi base di una rete di code.
 *
 * @author Mirko
 *
 */
public class SalvaElementiBase2 implements ISalvaElementiBase {

	private pmifschemaDoc doc;
	private Collection<ElementoBaseQN> elementiBase;
	private IFromElementiBaseToPmif fromElementiBaseToPmif;
	
	/**
	 * Istanzia un SalvaElementiBase costruendo un documento PMIF secondo gli elementi
	 * della rete di code presenti in elementiBase.
	 *
	 * @param elementiBase
	 * @throws MappingElementiBaseException
	 */
	public SalvaElementiBase2(Collection<ElementoBaseQN> elementiBase)
		throws MappingElementiBaseException
		{
		super();
		doc = new pmifschemaDoc();
		// si connettono gli elementi senza considerare i buffer presenti
		connectWithoutBuffers(elementiBase);
		this.elementiBase = elementiBase;
		this.fromElementiBaseToPmif = 
			ServiceTimeFactory.getFromElementiBaseToPmifFactory().createFromElementiBaseToPMIF();
		doc.setRootElementName("", "QueueingNetworkModel");
		}
	
	/**
	 * Istanzia un SalvaElementiBase costruendo un documento PMIF secondo gli elementi
	 * della rete di code presenti in elementiBase.
	 *
	 * @param elementiBase
	 * @throws MappingElementiBaseException
	 */
	public SalvaElementiBase2(Collection<ElementoBaseQN> elementiBase, 
			IFromElementiBaseToPmif fromElementiBaseToPmif)
		throws MappingElementiBaseException
		{
		super();
		doc = new pmifschemaDoc();
		// si connettono gli elementi senza considerare i buffer presenti
		connectWithoutBuffers(elementiBase);
		this.elementiBase = elementiBase;
		this.fromElementiBaseToPmif = fromElementiBaseToPmif;
		doc.setRootElementName("", "QueueingNetworkModel");
		}
	
	/**
	 * Restituisce true se e solo se radice è una rete di code multiclasse.
	 * Una condizione sufficiente affinchè la rete di code sia multiclasse è
	 * che ci sono due richieste di servizio con server e workload uguali.
	 * 
	 */
	public boolean isMulticlasse()
		throws MappingElementiBaseException
		{
		// si memorizzano in una lista omogenea le richieste di servizio
		QNMTypeEq radice = fromElementiBaseToPmif.getRadice();
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < radice.getServiceRequestCount(); i++)
			{
			ServiceRequestType serviceRequestType;
			try {
				serviceRequestType = radice.getServiceRequestAt(i);
				} 
			catch (Exception e) 
				{
				throw new MappingElementiBaseException(e);
				}
			for (int j = 0; j < serviceRequestType.getDemandServiceRequestCount(); j++)
				{
				DemandServType demandServType;
				try {
					demandServType = serviceRequestType.getDemandServiceRequestAt(j);
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				list.add(demandServType);
				}
			for (int j = 0; j < serviceRequestType.getTimeServiceRequestCount(); j++)
				{
				TimeServType timeServType;
				try {
					timeServType = serviceRequestType.getTimeServiceRequestAt(j);
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				list.add(timeServType);
				}
			for (int j = 0; j < serviceRequestType.getWorkUnitServiceRequestCount(); j++)
				{
				WorkUnitServType workUnitServType;
				try {
					workUnitServType = serviceRequestType.getWorkUnitServiceRequestAt(j);
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				list.add(workUnitServType);
				}
			}
		// se in list sono presenti due richieste di servizio con server e workload uguali
		// si restituisce true
		for (int i = 0; i < list.size(); i++)
			{
			Object object = list.get(i);
			String server = null;
			String workload = null;
			if (object instanceof DemandServType)
				{
				DemandServType demandServType = (DemandServType)object;
				try {
					server = demandServType.getServerID().asString();
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				try {
					workload = demandServType.getWorkloadName().asString();
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				}
			if (object instanceof TimeServType)
				{
				TimeServType timeServType = (TimeServType)object;
				try {
					server = timeServType.getServerID().asString();
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				try {
					workload = timeServType.getWorkloadName().asString();
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				}
			if (object instanceof WorkUnitServType)
				{
				WorkUnitServType workUnitServType = (WorkUnitServType)object;
				try {
					server = workUnitServType.getServerID().asString();
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				try {
					workload = workUnitServType.getWorkloadName().asString();
					} 
				catch (Exception e) 
					{
					throw new MappingElementiBaseException(e);
					}
				}
			// si verifica se negli elementi seguenti ci sia un'altra richiesta di servizio con
			// server e workload uguali
			for (int j = i+1; j < list.size(); j++)
				{
				Object object2 = list.get(j);
				if (object2 instanceof DemandServType)
					{
					DemandServType demandServType = (DemandServType)object2;
					String server2;
					try {
						server2 = demandServType.getServerID().asString();
						} 
					catch (Exception e) 
						{
						throw new MappingElementiBaseException(e);
						}
					String workload2;
					try {
						workload2 = demandServType.getWorkloadName().asString();
						} 
					catch (Exception e) 
						{
						throw new MappingElementiBaseException(e);
						}
					if (server.equals(server2) && workload.equals(workload2))
						return true;
					}
				if (object2 instanceof TimeServType)
					{
					TimeServType timeServType = (TimeServType)object2;
					String server2;
					try {
						server2 = timeServType.getServerID().asString();
						} 
					catch (Exception e) 
						{
						throw new MappingElementiBaseException(e);
						}
					String workload2;
					try {
						workload2 = timeServType.getWorkloadName().asString();
						} 
					catch (Exception e) 
						{
						throw new MappingElementiBaseException(e);
						}
					if (server.equals(server2) && workload.equals(workload2))
						return true;
					}
				if (object2 instanceof WorkUnitServType)
					{
					WorkUnitServType workUnitServType = (WorkUnitServType)object2;
					String server2;
					try {
						server2 = workUnitServType.getServerID().asString();
						} 
					catch (Exception e) 
						{
						throw new MappingElementiBaseException(e);
						}
					String workload2;
					try {
						workload2 = workUnitServType.getWorkloadName().asString();
						} 
					catch (Exception e) 
						{
						throw new MappingElementiBaseException(e);
						}
					if (server.equals(server2) && workload.equals(workload2))
						return true;
					}
				}
			}
		return false;
		}

	/**
	 * Elimina i buffers da elementiBase. 
	 * 
	 * @param elementiBase
	 */
	private void connectWithoutBuffers(Collection<ElementoBaseQN> elementiBase)
		{
		for (ElementoBaseQN elementoBaseQN : elementiBase)
			{
			if (elementoBaseQN instanceof Buffer<?>)
				{
				Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
				AggregatoBuffer<?> aggregatoBuffer =
					buffer.getAggregatoBuffer();
				for (DataBuffer dataBuffer : aggregatoBuffer)
					{
					// per ogni sorgente del buffer, si sostituiscono le destinazioni della
					// sorgente relative al buffer con le destinazioni del buffer associate
					List<ElementoBaseQN> list = dataBuffer.getSorgenti();
					List<Destinazione> list2 = dataBuffer.getDestinazioni();
					Integer integer = dataBuffer.getOrdineClasse();
					for (ElementoBaseQN elementoBaseQN2 : list)
						{
						Destinazione destinazione = new Destinazione(buffer,integer);
						elementoBaseQN2.replaceDestination(destinazione, list2);
						}
					// per ogni destinazione del buffer, si sostituiscono le sorgenti delle
					// destinazione relative al buffer con le sorgenti del buffer associate
					for (Destinazione destinazione : list2)
						{
						ElementoBaseQN elementoBaseQN2 = destinazione.getElementoBaseQN();
						elementoBaseQN2.replaceSource(buffer, list);
						}
					}
				}
			}
		}

	/**
	 * Memorizza nel file nomeFile il documento PMIF associato a questo oggetto.
	 *
	 * @param nomeFile
	 */
	public void salvaInXML(String nomeFile)
		throws  MappingElementiBaseException
		{
		doc.save(nomeFile, fromElementiBaseToPmif.getRadice());
		}

	/**
	 * Memorizza nel file nomeFile il documento PMIF associato a questo oggetto, validato
	 * secondo lo schema nomeSchema.
	 * @param nomeFile
	 * @param nomeSchema
	 */
	public void salvaInXML(String nomeFile, String nomeSchema)
		throws  MappingElementiBaseException
		{
		doc.setSchemaLocation(nomeSchema);
		doc.save(nomeFile, fromElementiBaseToPmif.getRadice());
		}

	@Override
	public boolean isTransformable() 
		throws MappingElementiBaseException 
		{
		for (ElementoBaseQN elementoBaseQN : this.elementiBase)
			{
			if (elementoBaseQN instanceof BufferLimitato)
				return false;
			if (elementoBaseQN instanceof ProcessoFork)
				return false;
			if (elementoBaseQN instanceof ProcessoJoin)
				return false;
			if (elementoBaseQN instanceof ProcessoRoutingSenzaBuffer)
				return false;
			if (elementoBaseQN instanceof ProcessoServizioSenzaBuffer)
				return false;
			}
		return true;
		}

	@Override
	public void mapping() 
		throws MappingElementiBaseException 
		{
		// si mappano prima i processi di arrivi
		for (ElementoBaseQN elementoBaseQN : elementiBase)
			{
			if (elementoBaseQN instanceof ProcessoArrivi)
				try {
					fromElementiBaseToPmif.mappingElementoBase(elementoBaseQN);
					} 
				catch (FilteringElementiBaseException e) 
					{
					throw new MappingElementiBaseException(e);
					}
			}
		// successivamente si mappano gli altri processi
		for (ElementoBaseQN elementoBaseQN : elementiBase)
			{
			if (!(elementoBaseQN instanceof ProcessoArrivi))
				try {
					fromElementiBaseToPmif.mappingElementoBase(elementoBaseQN);
					} 
				catch (FilteringElementiBaseException e) 
					{
					throw new MappingElementiBaseException(e);
					}
			}
		}
	
	/*
	 * restituisce true se e solo se la rete di code è multiclasse 
	 */
	public boolean isMulticlasseEB()
		{
		for (ElementoBaseQN elementoBaseQN : this.elementiBase)
			{
			// la proprietà di multiclassismo si applica solo ai server
			if (elementoBaseQN instanceof ProcessoServizio)
				{
				ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
				AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
					processoServizio.getAggregatoProcessoServizioSorgenti();
				for (int i = 0; i < aggregatoProcessoServizioSorgenti.size(); i++)
					{
					DataProcessoServizioSorgente dataProcessoServizioSorgente = 
						aggregatoProcessoServizioSorgenti.get(i);
					String string = dataProcessoServizioSorgente.getClasse();
					if (!string.equals(""))
						{
						for (int j = i + 1; j < aggregatoProcessoServizioSorgenti.size(); j++)
							{
							DataProcessoServizioSorgente dataProcessoServizioSorgente2 =
								aggregatoProcessoServizioSorgenti.get(j);
							String string2 = dataProcessoServizioSorgente2.getClasse();
							if (string2.equals(string))
								return true;
							}
						}
					}
				}
			}
		return false;
		}
	}