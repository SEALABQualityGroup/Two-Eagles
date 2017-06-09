package mappingElementiBaseIntoPMIF.secondRelease;

import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.Disciplina;
import mappingElementiBaseIntoPMIF.FilteringElementiBaseException;
import mappingElementiBaseIntoPMIF.IFromElementiBaseToPmif;
import mappingElementiBaseIntoPMIF.MappingElementiBaseException;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.ArcTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.ClosedWorkloadTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.NodeTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.OpenWorkloadTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.ServerTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.ServiceRequestTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.SinkNodeTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.SourceNodeTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.TimeServTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.TransitTypeEq;
import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.WorkloadTypeEq;

import com.schema.nonNegativeFloat;

import elementiBaseQN.Buffer;
import elementiBaseQN.BufferIllimitato;
import elementiBaseQN.BufferLimitato;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;
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
import elementiBaseQN.Strutture.AggregatoProcessoRoutingConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoServizioDestinazioni;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataProcessoRoutingConsegna;
import elementiBaseQN.Strutture.DataProcessoServizioDestinazione;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;

/**
 * Classe utilizzata per il mapping di elementi di base di una rete
 * di code in elementi PMIF. Genera i tempi di servizio invece delle domande
 *
 * @author Mirko
 *
 */
public class FromElementiBaseToPMIF implements IFromElementiBaseToPmif {

	private QNMTypeEq type;

	/**
	 * Utilizzato per istanziare un oggetto FromElementiBaseToPMIF
	 * a partire da un modello di rete di code PMIF vuoto.
	 *
	 */
	public FromElementiBaseToPMIF()
		{
		super();
		QNMTypeEq type = new QNMTypeEq();
		this.type = type;
		}

	/**
	 * Utilizzato per istanziare un oggetto FromElementiBaseToPMIF
	 * a partire da un modello di rete di code PMIF fornito al costruttore.
	 *
	 * @param type
	 */
	public FromElementiBaseToPMIF(QNMTypeEq type)
		{
		super();
		this.type = type;
		}

	/**
	 * Effettua il mapping di arriviInfiniti in elementi PMIF.
	 * @param arriviInfiniti
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoArriviInfiniti arriviInfiniti)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		try {
			// se non è presente nel modello, si crea un elemento Node radice
			NodeTypeEq nodeType = null;
			if (this.type.hasNode())
				nodeType = this.type.getNode();
			else
				{
				nodeType = new NodeTypeEq();
				this.type.addNode(nodeType);
				}
			// se non è già stato creato, si crea il nodo source
			SourceNodeTypeEq sourceType = null;
			if (!hasSourceNode())
				{
				// si crea un elemento SourceNodeType
				sourceType = new SourceNodeTypeEq();
				// si imposta l'attributo Name uguale a 'SourceNode'
				sourceType.addName("SourceNode");
				// si aggiunge l'elemento SourceNode all'elemento Node
				nodeType.addSourceNode(sourceType);
				}
			else
				{
				sourceType = getSourceNode();
				}			
			// se non è già stato creato, si crea il nodo sink
			SinkNodeTypeEq sinkType = null;
			if (!hasSinkNode())
				{
				// si crea un elemento SinkNodeType
				sinkType = new SinkNodeTypeEq();
				// si imposta l'attributo Name uguale a 'SinkNode'
				sinkType.addName("SinkNode");
				// si aggiunge l'elemento SinkNode all'elemento Node
				nodeType.addSinkNode(sinkType);
				}
			else
				{
				sinkType = getSinkNode();
				}			
			// Si crea un elemento OpenWorkload
			OpenWorkloadTypeEq openWorkloadType = new OpenWorkloadTypeEq();
			// All'attributo WorkloadName si assegna il nome del processo di arrivi infiniti
			openWorkloadType.addWorkloadName(arriviInfiniti.getNome());
			// All'attributo ArrivalRate si assegna l'inverso del tempo di interarrivo dei clienti
			openWorkloadType.addArrivalRate(new nonNegativeFloat(
					new Double(1 / arriviInfiniti.getTempoInterarrivo()).toString()));
			// All'attributo ArrivesAt si assegna il valore dell'attributo Name del SourceNode
			// creato in precedenza
			openWorkloadType.addArrivesAt(sourceType.getName().asString());
			// All' attributo DepartsAt si assegna il valore dell'attributo Name del SinkNode
			// creato in precedenza
			openWorkloadType.addDepartsAt(sinkType.getName().asString());
			// Per ogni possibile destinazione associate ad un'azione di consegna
			// si crea un elemento Transit nel seguente modo:
			for (int i = 0; i < arriviInfiniti.getNumeroConsegne(); i++)
				{
				for (Destinazione destinazione : arriviInfiniti.getDestinazioni(i))
					{
					ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
					TransitTypeEq transitType = new TransitTypeEq();
					// L'attributo To corrisponde al nome della destinazione;
					transitType.addTo(elementoBaseQN.getNome());
					// L'attributo Probability corrisponde alla probabilità  di routing di un cliente
					// rispetto alla destinazione che si sta considerando;
					transitType.addProbability(new Double(arriviInfiniti.getProbRouting(i)).toString());
					openWorkloadType.addTransit(transitType);
					}
				}
			// Si aggiunge il carico di lavoro aperto al modello di rete di code
			WorkloadTypeEq workloadType = null;
			if (this.type.hasWorkload())
				workloadType = this.type.getWorkload();
			else
				{
				workloadType = new WorkloadTypeEq();
				this.type.addWorkload(workloadType);
				}
			workloadType.addOpenWorkload(openWorkloadType);
			// Per ogni possibile destinazione associata
			// ad un'azione di consegna, si crea un elemento Arc nel seguente modo:
			for (int i = 0; i < arriviInfiniti.getNumeroConsegne(); i++)
				{
				for (Destinazione destinazione : arriviInfiniti.getDestinazioni(i))
					{
					ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
					ArcTypeEq arcType = new ArcTypeEq();
					// L'attributo FromNode corrisponde all'attributo ArrivesAt dell' OpenWorkload creato
					arcType.addFromNode(openWorkloadType.getArrivesAt().asString());
					// L'attributo ToNode corrisponde al nome della destinazione che si sta considerando;
					arcType.addToNode(elementoBaseQN.getNome());
					// Si aggiunge l'elemento Arc creato nel modello a rete di code.
					if (!presentArc(arcType)) this.type.addArc(arcType);
					}
				}
			}
		catch (Exception e)
			{
			throw new MappingElementiBaseException(
					"Arrival process for unbounded population mapping error",e);
			}
		// si mantiene il sequenziamento degli elementi di QNM secondo lo schema PMIF
		mantainSequenceQNMTypeEq();
		}

	/**
	 * Restituisce il nodo pozzo del modello
	 * a rete di code, o null se non è presente nessun nodo pozzo.
	 * 
	 * @return
	 * @throws Exception - se c'è più di un nodo pozzo.
	 */
	private SinkNodeTypeEq getSinkNode()
		throws Exception
		{
		for (int i = 0; i < this.type.getNodeCount(); i++)
			{
			NodeTypeEq nodeTypeEq = this.type.getNodeAt(i);
			if (nodeTypeEq.getSinkNodeCount() > 1)
				throw new Exception("There are more than one sink node");
				SinkNodeTypeEq sinkTypeEq = nodeTypeEq.getSinkNodeAt(0);
				return sinkTypeEq;
			}
		return null;
		}

	/**
	 * Restituisce true se e solo se il modello a rete di code
	 * ha un nodo sink (pozzo).
	 * 
	 * @return
	 */
	private boolean hasSinkNode() 
		{
		if (!this.type.hasNode()) return false;
		for (int i = 0; i < this.type.getNodeCount(); i++)
			{
			NodeTypeEq nodeTypeEq;
			try {
				nodeTypeEq = this.type.getNodeAt(i);
				}
			catch (Exception e)
				{
				return false;
				}
			if (!nodeTypeEq.hasSinkNode()) return false;
			for (int j = 0; j < nodeTypeEq.getSinkNodeCount(); j++)
				{
				SinkNodeTypeEq sinkTypeEq;
				try {
					sinkTypeEq = nodeTypeEq.getSinkNodeAt(j);
					}
				catch (Exception e)
					{
					return false;
					}
				try {
					if (sinkTypeEq.getName().asString().equals("SinkNode"))
						return true;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		return false;
		}

	/**
	 * Restituisce il nodo sorgente del modello
	 * a rete di code, o null se non è presente nessun nodo sorgente.
	 * 
	 * @return
	 * @throws Exception - se c'è più di un nodo sorgente.
	 */
	private SourceNodeTypeEq getSourceNode()
		throws Exception
			{
			for (int i = 0; i < this.type.getNodeCount(); i++)
				{
				NodeTypeEq nodeTypeEq = this.type.getNodeAt(i);
				if (nodeTypeEq.getSourceNodeCount() > 1)
					throw new Exception("There are more than one source node");
				SourceNodeTypeEq sourceTypeEq = nodeTypeEq.getSourceNodeAt(0);
				return sourceTypeEq;
				}
			return null;
			}

	/**
	 * Restituisce true se e solo se il modello a rete di code
	 * ha un nodo source (sorgente).
	 * 
	 * @return
	 */
	private boolean hasSourceNode() 
		{
		if (!this.type.hasNode()) return false;
		for (int i = 0; i < this.type.getNodeCount(); i++)
			{
			NodeTypeEq nodeTypeEq;
			try {
				nodeTypeEq = this.type.getNodeAt(i);
				}
			catch (Exception e)
				{
				return false;
				}
			if (!nodeTypeEq.hasSourceNode()) return false;
			for (int j = 0; j < nodeTypeEq.getSourceNodeCount(); j++)
				{
				SourceNodeTypeEq sourceTypeEq;
				try {
					sourceTypeEq = nodeTypeEq.getSourceNodeAt(j);
					}
				catch (Exception e)
					{
					return false;
					}
				try {
					if (sourceTypeEq.getName().asString().equals("SourceNode"))
						return true;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		return false;
		}

	/**
	 * Effettua il mapping di arriviFiniti in elementi PMIF.
	 * @param arriviFiniti
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoArriviFiniti arriviFiniti)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		try	{
			// se non è presente nel modello, si crea un elemento Node radice
			NodeTypeEq nodeType = null;
			if (this.type.hasNode())
				nodeType = this.type.getNode();
			else
				{
				nodeType = new NodeTypeEq();
				this.type.addNode(nodeType);
				}
			// Se non è stato già creato, si crea il dispositivo di pensamento
			ServerTypeEq serverType = null;
			if (!hasThinkDevice())
				{
				// si crea un elemento Server
				serverType = new ServerTypeEq();
				// si imposta l'attributo Name uguale a 'ThinkDevice'
				serverType.addName("ThinkDevice");
				// si assegna all'attributo Quantity il valore 1
				serverType.addQuantity(new Integer(1).toString());
				// si assegna a SchedulingPolicy il valore 'IS', che indica un server di delay
				serverType.addSchedulingPolicy("IS");
				// si aggiunge l'elemento Server all'elemento Node
				nodeType.addServer(serverType);
				}
			else
				{
				serverType = getThinkDevice();
				}
			// Si crea un elemento ClosedWorkload
			ClosedWorkloadTypeEq closedWorkloadType = new ClosedWorkloadTypeEq();
			// Si assegna all'attributo WorkloadName il nome del processo di arrivi per una popolazione finita
			closedWorkloadType.addWorkloadName(arriviFiniti.getNome());
			// Si assegna all'attributo NumberOfJobs il numero di clienti della popolazione finita
			closedWorkloadType.addNumberOfJobs(new Integer(arriviFiniti.getNumeroClienti()).toString());
			// L'attributo ThinkTime corrisponde al tempo di pensiero del cliente
			closedWorkloadType.addThinkTime(new Double(arriviFiniti.getTempoPensiero()).toString());
			// L'attributo ThinkDevice corrisponde all'attributo Name del Server definito in precedenza
			closedWorkloadType.addThinkDevice(serverType.getName().asString());
			// Per ogni possibile destinazione di ogni azione di consegna di un cliente,
			// si crea un elemento Transit nel seguente modo
			for (int i = 0; i < arriviFiniti.getNumeroConsegne(); i++)
				{
				for (Destinazione destinazione : arriviFiniti.getDestinazioni(i))
					{
					ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
					TransitTypeEq transitType = new TransitTypeEq();
					// L'attributo To corrisponde al nome della destinazione
					transitType.addTo(elementoBaseQN.getNome());
					// L'attributo Probability corrisponde alla probabilità  di routing di un cliente
					// rispetto alla destinazione che si sta considerando
					transitType.addProbability(new Double(arriviFiniti.getProbRouting(i)).toString());
					// Si aggiunge l'elemento Transit al carico di lavoro chiuso
					closedWorkloadType.addTransit(transitType);
					}
				}
			// Si aggiunge il carico di lavoro chiuso all'elemento Workload
			WorkloadTypeEq workloadType = null;
			if (this.type.hasWorkload())
				workloadType = this.type.getWorkload();
			else
				{
				workloadType = new WorkloadTypeEq();
				this.type.addWorkload(workloadType);
				}
			workloadType.addClosedWorkload(closedWorkloadType);
			// Per ogni possibile destinazione
			// associata ad un'azione di consegna si crea un elemento Arc nel seguente modo
			for (int i = 0; i < arriviFiniti.getNumeroConsegne(); i++)
				{
				for (Destinazione destinazione : arriviFiniti.getDestinazioni(i))
					{
					ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
					ArcTypeEq arcType = new ArcTypeEq();
					// L'attributo FromNode corrisponde all'attributo Name del Server creato
					arcType.addFromNode(serverType.getName().asString());
					// L'attributo ToNode corrisponde al nome della destinazione che si sta considerando
					arcType.addToNode(elementoBaseQN.getNome());
					// Si aggiunge l'arco al modello di rete di code
					if (!presentArc(arcType)) this.type.addArc(arcType);
					}
				}
			// Per ogni possibile ritorno del cliente al dispositivo di pensamento, si crea un elemento Arc
			// nel seguente modo
			for (int i = 0; i < arriviFiniti.getNumeroRitorni(); i++)
				{
				ArcTypeEq arcType = new ArcTypeEq();
				// L'attributo FromNode corrisponde al nome dell'elemento base da cui il cliente ritorna
				arcType.addFromNode(arriviFiniti.getRitorno(i).getNome());
				// L'attributo ToNode corrisponde al nome del dispositivo di pensamento
				arcType.addToNode(serverType.getName().asString());
				// Si aggiunge l'arco al modello di rete di code
				if (!presentArc(arcType)) this.type.addArc(arcType);
				}
			}
		catch (Exception e)
			{
			throw new MappingElementiBaseException(
					"Arrival process for finite population mapping error",e);
			}
		// si mantiene il sequenziamento secondo lo schema PMIF
		mantainSequenceQNMTypeEq();
		}

	/**
	 * Effettua il mapping di bufferIllimitato in elementi PMIF.
	 * @param bufferIllimitato
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(BufferIllimitato bufferIllimitato)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		// il mapping consiste nel trasferimento dei references delle sorgenti del buffer nei references
		// delle sorgenti del processo di servizio con buffer, a cui il buffer stesso è collegato.
		// Tale associazione avviene nel mapping del processo di servizio.
		}

	/**
	 * Effettua il mapping di bufferLimitato in elementi PMIF.
	 * @param bufferLimitato
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(BufferLimitato bufferLimitato)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		// non c'è nessuna associazione per un buffer a capacità  limitata
		throw new FilteringElementiBaseException("Impossible PMIF transformation for finite capacity buffer");
		}

	/**
	 * Effettua il mapping di processoForkConBuffer in elementi PMIF.
	 * @param processoForkConBuffer
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoForkConBuffer processoForkConBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		// non c'è nessuna corrispondenza per un processo fork con buffer
		throw new FilteringElementiBaseException("Impossible PMIF transformation for fork process with buffer");
		}

	/**
	 * Effettua il mapping di processoForkSenzaBuffer in elementi PMIF.
	 * @param processoForkSenzaBuffer
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoForkSenzaBuffer processoForkSenzaBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		// non c'è nessuna corrispondenza per un processo fork senza buffer
		throw new FilteringElementiBaseException("Impossible PMIF transformation for fork process with no buffer");
		}

	/**
	 * Effettua il mapping di processoJoinConBuffer in elementi PMIF.
	 * @param processoJoinConBuffer
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoJoinConBuffer processoJoinConBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		// non c'è nessuna corrispondenza per un processo join senza buffer
		throw new FilteringElementiBaseException("Impossible PMIF transformation for join process with buffer");
		}

	/**
	 * Effettua il mapping di processoJoinSenzaBuffer in elementi PMIF.
	 * @param processoJoinSenzaBuffer
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoJoinSenzaBuffer processoJoinSenzaBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		// non c'è nessuna corrispondenza per un processo join senza buffer
		throw new FilteringElementiBaseException("Impossible PMIF transformation for join process with no buffer");
		}

	/**
	 * Effettua il mapping di processoServizioConBuffer in elementi PMIF.
	 * 
	 * @param processoServizioConBuffer
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoServizioConBuffer processoServizioConBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		try {
			// Se il buffer è a capacità  limitata si restituisce un'eccezione
			Buffer<?> buffer = processoServizioConBuffer.getBuffer();
			if (buffer instanceof BufferLimitato)
				throw new MappingElementiBaseException("The finite capacity buffers have not " +
						"relative PMIF elements");
			// si crea un elemento server
			ServerTypeEq serverType = new ServerTypeEq();
			// Si imposta l'attributo Name uguale al nome del processo di servizio
			serverType.addName(processoServizioConBuffer.getNome());
			// Si imposta l'attributo Quantity uguale al numero dei server che costituiscono il processo
			// di servizio
			serverType.addQuantity(
				new Integer(processoServizioConBuffer.getNumeroServers()).toString());
			// se la disciplina di scheduling non è FCFC si restituisce un'eccezione
			if (buffer.getDisciplinaScheduling() == Disciplina.FCFS)
				serverType.addSchedulingPolicy(Disciplina.FCFS.name());
			else
				throw new MappingElementiBaseException("The service process"+
						processoServizioConBuffer.getNome()+
				" have not FCFC queueing discipline");
			NodeTypeEq nodeType = null;
			if (this.type.hasNode())
				nodeType = this.type.getNode();
			else
				{
				nodeType = new NodeTypeEq();
				this.type.addNode(nodeType);
				}
			nodeType.addServer(serverType);
			// Per ogni classe di clienti si procede nel seguente modo
			AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
				processoServizioConBuffer.getAggregatoProcessoServizioSorgenti();
			for (int i = 0; i < processoServizioConBuffer.getNumeroSorgenti(); i++)
				{
				DataProcessoServizioSorgente dataProcessoServizioSorgente =
					aggregatoProcessoServizioSorgenti.get(i);
				List<Destinazione> list1 = dataProcessoServizioSorgente.getDestinazioni();
				// se non ci sono destinazioni per l'i-esima classe di clienti si crea un arco
				// con destinazione uguale al nodo sink del canale di clienti che si sta
				// considerando
				if (list1.size() == 0)
					{
					ArcTypeEq arcTypeEq = new ArcTypeEq();
					arcTypeEq.addFromNode(serverType.getName().asString());
					SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
					if (sinkNodeTypeEq == null)
						throw new MappingElementiBaseException(
								"Sink node not found for jobs class "+
								dataProcessoServizioSorgente.getClasse());
					arcTypeEq.addToNode(sinkNodeTypeEq.getName().asString());
					// Si aggiunge l'arco all'elemento QueueingNetworkModel.
					if (!presentArc(arcTypeEq)) this.type.addArc(arcTypeEq);
					}
				// Per ogni oggetto DataProcessoSevizioDestinazione con il nome dell'azione di consegna
				// diversa da null, si crea un elemento Arc per ogni elemento base destinazione.
				AggregatoProcessoServizioDestinazioni aggregatoProcessoServizioDestinazioni =
					dataProcessoServizioSorgente.getAggregatoProcessoServizioDestinazioni();
				for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : aggregatoProcessoServizioDestinazioni)
					{
					String string = dataProcessoServizioDestinazione.getNomeAzioneConsegna();
					if (string != null)
						{
						List<Destinazione> list = dataProcessoServizioDestinazione.getDestinazioni();
						for (Destinazione destinazione : list)
							{
							ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
							ArcTypeEq arcType = new ArcTypeEq();
							// Si imposta l'attributo FromNode uguale al Name del Server creato
							arcType.addFromNode(serverType.getName().asString());
							// Si imposta l'attributo ToNode uguale al Name dell'elemento base destinazione.
							// Se l'elemento destinazione è un processo di arrivi finiti, Name deve essere uguale
							// a 'ThinkDevice'
							if (elementoBaseQN instanceof ProcessoArriviFiniti)
								arcType.addToNode("ThinkDevice");
							else
								arcType.addToNode(elementoBaseQN.getNome());
							// Si aggiunge l'arco all'elemento QueueingNetworkModel.
							if (!presentArc(arcType)) this.type.addArc(arcType);
							}
						}
					else
						{
						ArcTypeEq arcType = new ArcTypeEq();
						// Si imposta l'attributo FromNode uguale al Name del Server creato
						arcType.addFromNode(serverType.getName().asString());
						// Si imposta l'attributo ToNode uguale al Name del sink node del canale
						// della classe di clienti che si sta considerando.
						SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
						if (sinkNodeTypeEq == null)
							throw new MappingElementiBaseException(
									"Sink node not found for jobs class"+
									dataProcessoServizioSorgente.getClasse());
						arcType.addToNode(sinkNodeTypeEq.getName().asString());
						// Si aggiunge l'arco all'elemento QueueingNetworkModel.
						if (!presentArc(arcType)) this.type.addArc(arcType);
						}
					}
				// Si crea un elemento TimeServTypeEq come segue
				TimeServTypeEq timeServTypeEq = new TimeServTypeEq();
				// Si imposta WorkloadName uguale al nome del canale della classe di clienti
				// che si sta considerando
				timeServTypeEq.addWorkloadName(dataProcessoServizioSorgente.getClasse());
				// Si imposta ServerID uguale al nome del Server definito
				timeServTypeEq.addServerID(serverType.getName().asString());
				// imposta il TimeService uguale al tempo di servizio della classe di clienti che si
				// sta considerando
				Double double1 = dataProcessoServizioSorgente.getTempoServizio();
				timeServTypeEq.addServiceTime(double1.toString());
				// il numero di visite non può essere impostato perchè
				// può essere non intero
				// se non ci sono destinazioni si crea un elemento Transit:
				// con attributo To uguale al nome del sink node della classe di clienti che si
				// sta considerando;
				// con attributo Probability uguale a 1
				if (dataProcessoServizioSorgente.getNumeroDestinazioni() == 0)
					{
					TransitTypeEq transitType = new TransitTypeEq();
					SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
					transitType.addTo(sinkNodeTypeEq.getName().asString());
					transitType.addProbability(new Double(1).toString());
					timeServTypeEq.addTransit(transitType);
					}
				// Per ogni possibile destinazione si crea un elemento Transit:
				// con attributo To uguale al nome della destinazione;
				// con attributo Probability uguale alla probabilità di routing per la destinazione
				// che si sta considerando. Se la destinazione è un processo di arrivi finiti To è uguale a
				// 'ThinkDevice'
				for (int j = 0; j < dataProcessoServizioSorgente.getNumeroDestinazioni(); j++)
					{
					DataProcessoServizioDestinazione dataProcessoServizioDestinazione =
						aggregatoProcessoServizioDestinazioni.get(j);
					// Se il nome dell'azione di consegna sia null.
					// In questo caso il job esce dalla rete.
					String string = dataProcessoServizioDestinazione.getNomeAzioneConsegna();
					TransitTypeEq transitType = new TransitTypeEq();
					if (string == null)
						{
						// Si crea un elemento Transit con To uguale all'attributo Name del 
						// SinkNode del canale
						// della classe di clienti che si sta considerando.
						SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
						transitType.addTo(sinkNodeTypeEq.getName().asString());
						}
					else
						{
						List<Destinazione> list = dataProcessoServizioDestinazione.getDestinazioni();
						for (Destinazione destinazione : list)
							{
							ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
							// Se la destinazione è un processo di arrivi finiti To è uguale a 
							// 'ThinkDevice'
							if (elementoBaseQN instanceof ProcessoArriviFiniti)
								transitType.addTo("ThinkDevice");
							// altrimenti To è il nome dell'elemento base destinazione
							else
								transitType.addTo(elementoBaseQN.getNome());
							}
						}
					transitType.addProbability(new Double(dataProcessoServizioSorgente.
							getProbsRouting().get(j)).toString());
					timeServTypeEq.addTransit(transitType);
					}
				ServiceRequestTypeEq serviceRequestType = null;
				if (this.type.hasServiceRequest())
					serviceRequestType = this.type.getServiceRequest();
				else
					{
					serviceRequestType = new ServiceRequestTypeEq();
					this.type.addServiceRequest(serviceRequestType);
					}
				// Si aggiunge l'elemento TimeServiceRequest all'elemento ServiceRequest
				serviceRequestType.addTimeServiceRequest(timeServTypeEq);
				}
			}
		catch (Exception e)
			{
			throw new MappingElementiBaseException(
					"Service process with buffer mapping error",e);
			}
		mantainSequenceQNMTypeEq();
		}

	/**
	 * Effettua il mapping di processoServizioSenzaBuffer in elementi PMIF.
	 * @param processoServizioSenzaBuffer
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoServizioSenzaBuffer processoServizioSenzaBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		throw new MappingElementiBaseException(
				"Impossible PMIF saving for the service process with no buffer");
		}

	/* (non-Javadoc)
	 * @see mappingElementiBaseIntoPMIF.secondRelease.IFromElementiBaseToPmif#mappingElementoBase(elementiBaseQN.ElementoBaseQN)
	 */
	public void mappingElementoBase(ElementoBaseQN elementoBaseQN) 
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		if (elementoBaseQN instanceof BufferIllimitato)
			mappingElementoBase((BufferIllimitato)elementoBaseQN);
		else if (elementoBaseQN instanceof BufferLimitato)
			mappingElementoBase((BufferLimitato)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoArriviFiniti)
			mappingElementoBase((ProcessoArriviFiniti)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoArriviInfiniti)
			mappingElementoBase((ProcessoArriviInfiniti)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoForkConBuffer)
			mappingElementoBase((ProcessoForkConBuffer)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoForkSenzaBuffer)
			mappingElementoBase((ProcessoForkSenzaBuffer)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoJoinConBuffer)
			mappingElementoBase((ProcessoJoinConBuffer)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoJoinSenzaBuffer)
			mappingElementoBase((ProcessoJoinSenzaBuffer)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoServizioConBuffer)
			mappingElementoBase((ProcessoServizioConBuffer)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoServizioSenzaBuffer)
			mappingElementoBase((ProcessoServizioSenzaBuffer)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoRoutingConBuffer)
			mappingElementoBase((ProcessoRoutingConBuffer)elementoBaseQN);
		else if (elementoBaseQN instanceof ProcessoRoutingSenzaBuffer)
			mappingElementoBase((ProcessoRoutingSenzaBuffer)elementoBaseQN);
		}
	
	/**
	 * Effettua il mapping di processoRoutingSenzaBuffer in elementi PMIF.
	 * 
	 * @param processoServizioSenzaBuffer
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoRoutingSenzaBuffer processoRoutingSenzaBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		throw new MappingElementiBaseException(
				"Impossible PMIF saving for the routing process with no buffer");
		}

	/**
	 * Effettua il mapping di processoRoutinConBuffer in elementi PMIF.
	 * @param processoRoutingConBuffer
	 * 
	 * @throws MappingElementiBaseException
	 */
	private void mappingElementoBase(ProcessoRoutingConBuffer processoRoutingConBuffer)
		throws MappingElementiBaseException, FilteringElementiBaseException
		{
		try {
			// Se il buffer è a capacità  limitata si restituisce un'eccezione
			Buffer<?> buffer = processoRoutingConBuffer.getBuffer();
			if (buffer instanceof BufferLimitato)
				throw new MappingElementiBaseException("The finite capacity buffers have not relative PMIF elements");
			// si crea un elemento server
			ServerTypeEq serverType = new ServerTypeEq();
			// Si imposta l'attributo Name uguale al nome del processo di servizio
			serverType.addName(processoRoutingConBuffer.getNome());
			// Si imposta l'attributo Quantity uguale a 1
			serverType.addQuantity(new Integer(1).toString());
			// se la disciplina di scheduling non è FCFC si restituisce un'eccezione
			if (buffer.getDisciplinaScheduling() == Disciplina.FCFS)
				serverType.addSchedulingPolicy(Disciplina.FCFS.name());
			else
				throw new MappingElementiBaseException("The service process"+
						processoRoutingConBuffer.getNome()+
				" have not FCFC queueing discipline");
			NodeTypeEq nodeType = null;
			if (this.type.hasNode())
				nodeType = this.type.getNode();
			else
				{
				nodeType = new NodeTypeEq();
				this.type.addNode(nodeType);
				}
			nodeType.addServer(serverType);
			// se non ci sono destinazioni per la classe di clienti si crea un arco
			// con destinazione uguale al nodo sink del canale di clienti che si sta
			// considerando
			if (processoRoutingConBuffer.getDestinazioni().size() == 0)
				{
				ArcTypeEq arcTypeEq = new ArcTypeEq();
				arcTypeEq.addFromNode(serverType.getName().asString());
				SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
				if (sinkNodeTypeEq == null)
					throw new MappingElementiBaseException(
							"Sink node not found");
				arcTypeEq.addToNode(sinkNodeTypeEq.getName().asString());
				// Si aggiunge l'arco all'elemento QueueingNetworkModel.
				if (!presentArc(arcTypeEq)) this.type.addArc(arcTypeEq);
				}
			// Per ogni oggetto DataProcessoRoutingConsegna con il nome dell'azione di consegna
			// diversa da null si crea un elemento Arc per ogni elemento base destinazione.
			AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne =
				processoRoutingConBuffer.getAggregatoProcessoRoutingConsegne();
			for (DataProcessoRoutingConsegna dataProcessoRoutingConsegna : aggregatoProcessoRoutingConsegne)
				{
				String string = dataProcessoRoutingConsegna.getNomeAzioneConsegna();
				if (string != null)
					{
					List<Destinazione> list = dataProcessoRoutingConsegna.getDestinazioni();
					for (Destinazione destinazione : list)
						{
						ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
						ArcTypeEq arcType = new ArcTypeEq();
						// Si imposta l'attributo FromNode uguale al Name del Server creato
						arcType.addFromNode(serverType.getName().asString());
						// Si imposta l'attributo ToNode uguale al Name dell'elemento base destinazione.
						// Se l'elemento destinazione è un processo di arrivi finiti, 
						// Name deve essere uguale
						// a 'ThinkDevice'
						if (elementoBaseQN instanceof ProcessoArriviFiniti)
							arcType.addToNode("ThinkDevice");
						else
							arcType.addToNode(elementoBaseQN.getNome());
						// Si aggiunge l'arco all'elemento QueueingNetworkModel.
						if (!presentArc(arcType)) this.type.addArc(arcType);
						}
					}
				else
					{
					ArcTypeEq arcType = new ArcTypeEq();
					// Si imposta l'attributo FromNode uguale al Name del Server creato
					arcType.addFromNode(serverType.getName().asString());
					// Si imposta l'attributo ToNode uguale al Name del sink node del canale
					// della classe di clienti che si sta considerando.
					SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
					if (sinkNodeTypeEq == null)
						throw new MappingElementiBaseException(
								"Sink node not found");
					arcType.addToNode(sinkNodeTypeEq.getName().asString());
					// Si aggiunge l'arco all'elemento QueueingNetworkModel.
					if (!presentArc(arcType)) this.type.addArc(arcType);
					}
				}
			// Si crea un elemento TimeServTime come segue
			TimeServTypeEq timeServTypeEq = new TimeServTypeEq();
			// Si imposta WorkloadName uguale al nome del canale della classe di clienti
			// che si sta considerando
			timeServTypeEq.addWorkloadName(processoRoutingConBuffer.getCanale());
			// Si imposta ServerID uguale al nome del Server definito
			timeServTypeEq.addServerID(serverType.getName().asString());
			// Si imposta ServiceTime uguale a zero
			timeServTypeEq.addServiceTime(new Double(0).toString());
			// il numero di visite non può essere impostato
			// perchè può essere non intero

			// se non ci sono destinazioni si crea un elemento Transit:
			// con attributo To uguale al nome del sink node della classe di clienti che si
			// sta considerando;
			// con attributo Probability uguale a 1
			if (processoRoutingConBuffer.getDestinazioni().size() == 0)
				{
				TransitTypeEq transitType = new TransitTypeEq();
				SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
				transitType.addTo(sinkNodeTypeEq.getName().asString());
				transitType.addProbability(new Double(1).toString());
				timeServTypeEq.addTransit(transitType);
				}
			// Per ogni possibile destinazione si crea un elemento Transit:
			// con attributo To uguale al nome della destinazione;
			// con attributo Probability uguale alla probabilità di routing per la destinazione
			// che si sta considerando. Se la destinazione è un processo di arrivi finiti To è uguale a
			// 'ThinkDevice'
			for (int j = 0; j < processoRoutingConBuffer.getDestinazioni().size(); j++)
				{
				DataProcessoRoutingConsegna dataProcessoRoutingConsegna =
					aggregatoProcessoRoutingConsegne.get(j);
				// Se il nome dell'azione di consegna sia null.
				// In questo caso il job esce dalla rete.
				String string = dataProcessoRoutingConsegna.getNomeAzioneConsegna();
				TransitTypeEq transitType = new TransitTypeEq();
				if (string == null)
					{
					// Si crea un elemento Transit con To uguale all'attributo Name del SinkNode del canale
					// della classe di clienti che si sta considerando.
					SinkNodeTypeEq sinkNodeTypeEq = getSinkNode();
					transitType.addTo(sinkNodeTypeEq.getName().asString());
					}
				else
					{
					List<Destinazione> list = dataProcessoRoutingConsegna.getDestinazioni();
					for (Destinazione destinazione : list)
						{
						ElementoBaseQN elementoBaseQN = destinazione.getElementoBaseQN();
						// Se la destinazione è un processo di arrivi finiti To è uguale a 'ThinkDevice'
						if (elementoBaseQN instanceof ProcessoArriviFiniti)
							transitType.addTo("ThinkDevice");
						// altrimenti To è il nome dell'elemento base destinazione
						else
							transitType.addTo(elementoBaseQN.getNome());
						}
					}
				transitType.addProbability(new Double(dataProcessoRoutingConsegna.getProbRouting().getProbabilita()).toString());
				timeServTypeEq.addTransit(transitType);
				}
			ServiceRequestTypeEq serviceRequestType = null;
			if (this.type.hasServiceRequest())
				serviceRequestType = this.type.getServiceRequest();
			else
				{
				serviceRequestType = new ServiceRequestTypeEq();
				this.type.addServiceRequest(serviceRequestType);
				}
			// Si aggiunge l'elemento DemandServType all'elemento ServiceRequest
			serviceRequestType.addTimeServiceRequest(timeServTypeEq);
			}
		catch (Exception e)
			{
			throw new MappingElementiBaseException(
					"Service process with buffer mapping error",e);
			}
		mantainSequenceQNMTypeEq();
		}

	/* (non-Javadoc)
	 * @see mappingElementiBaseIntoPMIF.secondRelease.IFromElementiBaseToPmif#getRadice()
	 */
	public QNMTypeEq getRadice() {
		return type;
	}

	/**
	 * Restituisce true se e solo se il modello a rete di code
	 * ha un dispositivo di pensamento.
	 * @return
	 */
	private boolean hasThinkDevice()
		{
		if (!this.type.hasNode()) return false;
		for (int i = 0; i < this.type.getNodeCount(); i++)
			{
			NodeTypeEq nodeTypeEq;
			try {
				nodeTypeEq = this.type.getNodeAt(i);
				}
			catch (Exception e)
				{
				return false;
				}
			if (!nodeTypeEq.hasServer()) return false;
			for (int j = 0; j < nodeTypeEq.getServerCount(); j++)
				{
				ServerTypeEq serverTypeEq;
				try {
					serverTypeEq = nodeTypeEq.getServerAt(j);
					}
				catch (Exception e)
					{
					return false;
					}
				try {
					if (serverTypeEq.getName().asString().equals("ThinkDevice"))
						return true;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		return false;
		}

	/**
	 * Mantiene il sequenziamento definito secondo lo schema PMIF
	 * dell'elemento QueueingNetworkModel.
	 */
	private void mantainSequenceQNMTypeEq()
		{
		ArrayList<NodeTypeEq> arrayList = new ArrayList<NodeTypeEq>();
		for (int i = 0; i < this.type.getNodeCount(); i++)
			{
			try {
				arrayList.add(this.type.getNodeAt(i));
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			}
		while (this.type.hasNode())
			{
			this.type.removeNode();
			}
		ArrayList<ArcTypeEq> arrayList2 = new ArrayList<ArcTypeEq>();
		for (int i = 0; i < this.type.getArcCount(); i++)
			{
			try {
				arrayList2.add(this.type.getArcAt(i));
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			}
		while (this.type.hasArc())
			{
			this.type.removeArc();
			}
		ArrayList<WorkloadTypeEq> arrayList3 = new ArrayList<WorkloadTypeEq>();
		for (int i = 0; i < this.type.getWorkloadCount(); i++)
			{
			try {
				arrayList3.add(this.type.getWorkloadAt(i));
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			}
		while (this.type.hasWorkload())
			{
			this.type.removeWorkload();
			}
		ArrayList<ServiceRequestTypeEq> arrayList4 = new ArrayList<ServiceRequestTypeEq>();
		for (int i = 0; i < this.type.getServiceRequestCount(); i++)
			{
			try {
				arrayList4.add(this.type.getServiceRequestAt(i));
				}
			catch (Exception e)
				{
				e.printStackTrace();
				}
			}
		while (this.type.hasServiceRequest())
			{
			this.type.removeServiceRequest();
			}
		for (NodeTypeEq nodeTypeEq : arrayList)
			{
			this.type.addNode(nodeTypeEq);
			}
		for (ArcTypeEq arcTypeEq : arrayList2)
			{
			this.type.addArc(arcTypeEq);
			}
		for (WorkloadTypeEq workloadTypeEq : arrayList3)
			{
			this.type.addWorkload(workloadTypeEq);
			}
		for (ServiceRequestTypeEq serviceRequestTypeEq : arrayList4)
			{
			this.type.addServiceRequest(serviceRequestTypeEq);
			}
		}

	/**
	 * Restituisce true se e solo se arcTypeEq è presente nel
	 * modello a rete di code.
	 * @param arcTypeEq
	 * @return
	 */
	private boolean presentArc(ArcTypeEq arcTypeEq)
		{
		for (int i = 0; i < this.type.getArcCount(); i++)
			{
			try {
				String fromNode1 = this.type.getArcAt(i).getFromNode().asString();
				String fromNode2 = arcTypeEq.getFromNode().asString();
				String toNode1 = this.type.getArcAt(i).getToNode().asString();
				String toNode2 = arcTypeEq.getToNode().asString();
				if (fromNode1.equals(fromNode2) && toNode1.equals(toNode2))
					return true;
				}
			catch (Exception e)
				{
				return true;
				}
			}
		return false;
		}

	/**
	 * Restituisce il dispositivo di pensamento del modello
	 * a rete di code, o null se non è presente nessun dispositivo
	 * di pensamento
	 * @return
	 * @throws Exception
	 */
	private ServerTypeEq getThinkDevice()
		throws Exception
		{
		for (int i = 0; i < this.type.getNodeCount(); i++)
			{
			NodeTypeEq nodeTypeEq = this.type.getNodeAt(i);
			for (int j = 0; j < nodeTypeEq.getServerCount(); j++)
				{
				ServerTypeEq serverTypeEq = nodeTypeEq.getServerAt(j);
				if (serverTypeEq.getName().asString().equals("ThinkDevice"))
					return serverTypeEq;
				}
			}
		return null;
		}
}