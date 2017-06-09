package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.IFromAEIintoElementiBase;
import mappingAEIintoElementiBase.secondRelease.struttura.ListaMappingSpecifiche2;
import mappingAEIintoElementiBase.struttura.AListaMappingSpecifiche;
import mappingAEIintoElementiBase.struttura.IMappingSpecifiche;
import mappingAEIintoElementiBase.struttura.ListaElementiBase;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheAP;
import restrizioniSpecifiche.interfaces.ISpecificheArriviFiniti;
import restrizioniSpecifiche.interfaces.ISpecificheMultiServer;
import restrizioniSpecifiche.interfaces.ISpecificheSCAP;
import restrizioniSpecifiche.interfaces.ISpecificheSP;
import restrizioniSpecifiche.interfaces.ISpecificheUPAP;
import restrizioniSpecifiche.secondRelease.Specifiche2Factory;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;
import elementiBaseQN.ProcessoArriviFiniti;
import elementiBaseQN.ProcessoArriviInfiniti;
import elementiBaseQN.ProcessoJoin;
import elementiBaseQN.ProcessoRouting;
import elementiBaseQN.ProcessoServizio;
import elementiBaseQN.ProcessoServizioConBuffer;
import elementiBaseQN.ProcessoServizioSenzaBuffer;
import elementiBaseQN.Strutture.AggregatoProcessoArriviConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoArriviRitorni;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;

public class FromAEIintoElementiBase2 
	implements IFromAEIintoElementiBase 
	{
	
	// lista di oggetti MappingSpecifiche2 che vengono
	// creati per wrappare oggetti
	// Specifiche e associare un canale di clienti ad
	// ogni interazione di input
	private AListaMappingSpecifiche specificheObjects;
	
	// lista di oggetti ElementoBaseQN utilizzata per contenere
	// il risultato del mapping delle istanze.
	private ListaElementiBase elementiBase;
	
	// costruttore in cui si crea la lista specifcheObjects a partire
	// dalla lista list
	public FromAEIintoElementiBase2(List<ISpecifiche> list)
		throws AEIintoElementiBaseException
		{
		super();
		// si costruisce la lista specificheObjects senza l'associazione
		// delle classi
		specificheObjects = new ListaMappingSpecifiche2();
		// si costruisce nel costruttore la lista specificheObjects
		for (ISpecifiche specifiche : list)
			{
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche =
				ListaMappingSpecifiche2.createMappingSpecifiche(specifiche);
			// per precondizione mappingSpecifiche non è null
			specificheObjects.add(mappingSpecifiche);
			}			
		}

	@Override
	public boolean assegnaClassi()
		{
		boolean ris = true;
		for (IMappingSpecifiche<ISpecifiche> mappingSpecifiche : specificheObjects)
			{
			// si preleva l'oggetto Specifiche
			ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
			if (specifiche instanceof ISpecificheAP)
				{
				// si prelevano le interazioni di input del processo di arrivi
				List<String> list = mappingSpecifiche.getInputInteractions();
				// si prelevano le interazioni di output del processo di arrivi
				List<String> list2 = mappingSpecifiche.getOutputInteractions();
				// si preleva il nome del canale
				String string = null;
				// se il processo di arrivi è di tipo finito si preleva il nome del
				// tipo di elemento architetturale
				if (specifiche instanceof ISpecificheSCAP)
					{
					string = mappingSpecifiche.getTipoIstanza();
					}
				// se il processo di arrivi è di tipo infinito si preleva il nome dell'istanza
				else if (specifiche instanceof ISpecificheUPAP)
					{
					string = mappingSpecifiche.getNomeIstanza();
					}
				// si assegna a mappingSpecifiche una classe per ogni interazione 
				// per precondizione list, list2 e string non sono null
				assegnaCanaliSpecificheAP(mappingSpecifiche,list,list2,string);
				// per precondizione string2 può essere null
				for (String string2 : list2)
					{
					// si prelevano gli oggetti MappingSpecifiche connessi a string2
					List<IMappingSpecifiche<ISpecifiche>> list3 =
						this.specificheObjects.getListaMappingFromOutput(string2, mappingSpecifiche);
					// per ogni oggetto contenuto in list3 si chiama la funzione che controllano
					// le mappe delle classi secondo string e string2
					for (IMappingSpecifiche<ISpecifiche> mappingSpecifiche2 : list3)
						{
						// string è il nome del canale e string2 è il nome
						// dell'interazione di output collegata alle istanze da controllare
						// string3 è il nome dell'istanza di output, che in questo caso
						// corrispode al nome del processo di arrivi.
						String string3 = mappingSpecifiche.getNomeIstanza();
						ris = ris && this.specificheObjects.updateClassInDepth(mappingSpecifiche2,string,
								string2,string3);
						}
					}
				}
			}
		return ris;
		}

	/**
	 * Tale metodo assegna alle interazioni di un processo di arrivi
	 * la classe di clienti corrispondenti. Per precondizione mappingSpecifiche è
	 * relativo ad un processo di arrivi.
	 * 
	 * @param mappingSpecifiche
	 * @param list è la lista delle interazioni di input.
	 * @param list2 è la lista delle interazioni di output.
	 * @param string è il nome della classe di clienti
	 */
	private void assegnaCanaliSpecificheAP(
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche, 
			List<String> list,
			List<String> list2, 
			String string)
		{
		// hashMap è la mappa delle interazioni di input
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (String string2 : list)
			{
			hashMap.put(string2, string);
			}
		// hashMap2 è la mappa delle interazioni di output
		HashMap<String, String> hashMap2 = new HashMap<String, String>();
		for (String string2 : list2)
			{
			hashMap2.put(string2, string);
			}
		// si assegna hashMap e hashMap2 a mappingSpecifiche
		mappingSpecifiche.setClassiInput(hashMap);
		mappingSpecifiche.setClassiOutput(hashMap2);
		}
	
	@Override
	public ListaElementiBase generaElementiBase()
			throws AEIintoElementiBaseException 
		{
		ListaElementiBase list = new ListaElementiBase();
		for (IMappingSpecifiche<ISpecifiche> mappingSpecifiche : this.specificheObjects)
			{
			Mapping mapping = new Mapping();
			ElementoBaseQN elementoBaseQN = mapping.mapping(mappingSpecifiche);
			list.add(elementoBaseQN);
			}
		this.elementiBase = list;
		return list;
		}

	@Override
	public void connettiElementiBase() 
		throws AEIintoElementiBaseException 
		{
		normalizzazioneArriviFiniti();
		normalizzazioneMultiServer();
		// si prelevano i collegamenti architetturali
		List<AttacDecl> list =
			this.specificheObjects.getAttacDecls();
		for (AttacDecl attacDecl : list)
			{
			// si prelevano i dati dell'interazione di input
			String string = attacDecl.getInputInteraction();
			String string2 = attacDecl.getInputAei();
			// si prelevano i dati dell'interazione di output
			String string3 = attacDecl.getOutputInteraction();
			String string4 = attacDecl.getOutputAei();
			// si preleva il processo associato a string2
			ElementoBaseQN elementoBaseQN = this.elementiBase.getElementoBaseFromAEI(string2);
			// si preleva il processo associato a string4
			ElementoBaseQN elementoBaseQN2 = this.elementiBase.getElementoBaseFromAEI(string4);
			// si preleva la struttura di interazione di input di elementoBaseQN relativa a string
			StrutturaInterazioneInput strutturaInterazioneInput = 
				elementoBaseQN.getStrutturaInput(string);
			// si preleva la struttura di interazione di output di elementoBaseQN2 relativa a string3
			StrutturaInterazioneOutput strutturaInterazioneOutput = 
				elementoBaseQN2.getStrutturaOutput(string3);
			// si aggiunge la sorgente elementoBaseQN2 a strutturaInterazioneInput
			strutturaInterazioneInput.addSorgente(elementoBaseQN2);
			// si aggiunge la destinazione relativa a elementoBaseQN a strutturaInterazioneOutput
			Integer integer = strutturaInterazioneInput.getOrdineClasse();
			Destinazione destinazione = new Destinazione(elementoBaseQN,integer);
			strutturaInterazioneOutput.addDestinazione(destinazione);
			}
		this.elementiBase.impostaBuffers();
		// La probabilità di routing va proporzionata a seconda del numero delle destinazioni.
		this.elementiBase.normalizzaProbabilita();
		}

	@Override
	public ListaElementiBase getElementiBase() 
		{
		return elementiBase;
		}
	
	/**
	 * Si normalizzano i processi di arrivi finiti contenuti nella specifica architetturale.
	 * Tale normalizzazione consiste nel sostituire ogni elemento base, che rappresenta un singolo
	 * cliente e che si riferisce ad uno stesso processo di arrivi finiti in un unico elemento base
	 * che ha come nome il nome del tipo di elemento architetturale equivalente ad 
	 * un processo di arrivi finiti
	 * e con numero di clienti uguale al numero delle istanze del tipo di elemento
	 * architetturale.
	 *
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ListaElementiBase normalizzazioneArriviFiniti()
		throws AEIintoElementiBaseException
		{
		// si preleva la mappa degli arrivi finiti
		ArchiType archiType = this.specificheObjects.getArchiType();
		ISpecificheArriviFiniti specificheArriviFiniti = null;
		try {
			specificheArriviFiniti =
				new Specifiche2Factory().createSpecificheArriviFiniti(archiType);
			}
		catch (RestrizioniSpecException restrizioniSpecException)
			{
			throw new AEIintoElementiBaseException("Building error of finite arrivals specification",
					restrizioniSpecException);
			}
		HashMap<String, List<ISpecificheSCAP>> hashMap =
			specificheArriviFiniti.getHashMapSCAP();
		// per ogni entrata della mappa:
		// 1) si prelevano gli oggetti ProcessoArriviFiniti
		// che hanno lo stesso nome di tipo
		// 2) si verifica che la lista creata al punto
		// uno abbia gli stessi elementi, nome a parte
		// 3) si aggiorna la mappa utilizzata per la connessione
		// 4) si istanzia un ProcessoArriviFiniti con nome
		// uguale al tipo dei processi di arrivi finiti
		// corrispondenti alla entry di hashMap e numeroClienti
		// uguale alla lunghezza della lista presente
		// nella entry.
		// 5) si cancellano i processi arrivi finiti
		// corrispondenti agli elementi della lista
		// della entry
		// 6) si aggiunge il ProcessoArriviFiniti creato in precedenza
		Set<Entry<String, List<ISpecificheSCAP>>> set =
			hashMap.entrySet();
		for (Entry<String, List<ISpecificheSCAP>> entry : set)
			{
			String string = entry.getKey();
			List<ISpecificheSCAP> list = entry.getValue();
			List<ProcessoArriviFiniti> list2 = getProcessiArriviFinitiFromSpecificheSCAP(list);
			// si verifica che gli oggetti di list siano dello stesso tipo
			if (list2 == null || list2.isEmpty()) throw new AEIintoElementiBaseException(string+
					" have not instances");
			if (list.size() != list2.size())
				throw new AEIintoElementiBaseException("The finite arrivals process list and "+
						"the basic elements list of "+string+" have different size");
			ProcessoArriviFiniti processoArriviFiniti = list2.get(0);
			for (int i = 1; i < list2.size(); i++)
				{
				ProcessoArriviFiniti processoArriviFiniti2 = list2.get(i);
				if (!processoArriviFiniti.equalsType(processoArriviFiniti2))
					throw new AEIintoElementiBaseException(processoArriviFiniti.getNome()+
						" and "+processoArriviFiniti2.getNome()+" have different type");
				}
			// si aggiorna la mappa utilizzata per la connessione
			for (ISpecificheSCAP specificheSCAP : list)
				{
				String string2 = specificheSCAP.getNomeIstanza();
				this.elementiBase.addNomeTipo(string2, string);
				}
			// si crea un oggetto ProcessoArriviFiniti con numero di clienti impostato
			// si prelevano i dati comuni dalla prima istanza
			AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne =
				processoArriviFiniti.getAggregatoProcessoArriviConsegne();
			double tempoPensiero = processoArriviFiniti.getTempoPensiero();
			AggregatoProcessoArriviRitorni aggregatoProcessoArriviRitorni =
				processoArriviFiniti.getAggregatoProcessoArriviRitorni();
			int numeroClienti = list2.size();
			ProcessoArriviFiniti processoArriviFinitiTipo = new ProcessoArriviFiniti();
			processoArriviFinitiTipo.setNome(string);
			processoArriviFinitiTipo.setAggregatoProcessoArriviConsegne(aggregatoProcessoArriviConsegne);
			processoArriviFinitiTipo.setTempoPensiero(tempoPensiero);
			processoArriviFinitiTipo.setNumeroClienti(numeroClienti);
			processoArriviFinitiTipo.setAggregatoProcessoArriviRitorni(aggregatoProcessoArriviRitorni);
			// si cancellano da elementiBase gli oggetti contenuti in list2
			this.elementiBase.removeAll(list2);
			// si aggiunge ad elementiBase l'oggetto processoArriviFinitiTipo
			this.elementiBase.add(processoArriviFinitiTipo);
			}
		return this.elementiBase;
		}

	/**
	 * Si normalizzano i processi di servizio multiserver presenti nella specifica architetturale.
	 * Tale normalizzazione consiste nel sostiutire ogni processo di servizio che si riferisce ad uno
	 * stesso processo di servizio multiserver con un unico processo di servizio con nome uguale
	 * al nome del tipo di elemento architetturale equivalente al processo di servizio e con
	 * numero di server uguale al numero delle istanze del tipo di elemento architetturale che
	 * si sta considerando.
	 *
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ListaElementiBase normalizzazioneMultiServer()
		throws AEIintoElementiBaseException
		{
		// si preleva la mappa dei processi di servizio multiserver
		ArchiType archiType = this.specificheObjects.getArchiType();
		ISpecificheMultiServer specificheMultiServer = null;
		try {
			specificheMultiServer =
				new Specifiche2Factory().createSpecificheMultiServer(archiType);
			}
		catch (RestrizioniSpecException restrizioniSpecException)
			{
			throw new AEIintoElementiBaseException("Multiserver specification building error"
					,restrizioniSpecException);
			}
		HashMap<String, List<ISpecificheSP>> hashMap =
			specificheMultiServer.getMultiServerMap();
		// per ogni entrata della mappa:
		// 1) si prelevano gli oggetti ProcessoServizio
		// che hanno lo stesso nome di istanza
		// 2) si verifica che la lista creata al punto
		// uno abbia gli stessi elementi, nome a parte
		// 3) si aggiorna la mappa utilizzata per la connessione
		// 4) si istanzia un ProcessoServizioConBuffer o ProcessoServizioSenzaBuffer con nome
		// uguale al tipo dei processi di servizio multiserver
		// corrispondenti alla entry di hashMap e numeroServer
		// uguale alla lunghezza della lista presente
		// nella entry.
		// 5) si cancellano i processi di servizio
		// corrispondenti agli elementi della lista
		// della entry
		// 6) si aggiunge il ProcessoServizio creato in precedenza
		Set<Entry<String, List<ISpecificheSP>>> set =
			hashMap.entrySet();
		for (Entry<String, List<ISpecificheSP>> entry : set)
			{
			String string = entry.getKey();
			List<ISpecificheSP> list = entry.getValue();
			List<ProcessoServizio> list2 = getProcessiServizioFromSpecificheSP(list);
			// si verifica che gli oggetti di list siano dello stesso tipo
			if (list2 == null || list2.isEmpty()) throw new AEIintoElementiBaseException(string+" have not instances");
			if (list.size() != list2.size())
				throw new AEIintoElementiBaseException("The specification service process list and "+
						"the basic elements list of "+string+" have not same size");
			ProcessoServizio processoServizio = list2.get(0);
			for (int i = 1; i < list2.size(); i++)
				{
				ProcessoServizio processoServizio2 = list2.get(i);
				// dobbiamo differenziare il caso in cui il processo di servizio multiserver
				// funge da nodo sink

				if (!processoServizio.equalsType(processoServizio2))
						throw new AEIintoElementiBaseException(processoServizio.getNome()+
								" and "+processoServizio2.getNome()+" have not same topology");
				}
			// si aggiorna la mappa utilizzata per la connessione
			for (ISpecificheSP specificheSP : list)
				{
				String string2 = specificheSP.getNomeIstanza();
				this.elementiBase.addNomeTipo(string2, string);
				}
			// si crea un oggetto ProcessoServizioConBuffer o ProcessoServizioSenzaBuffer
			// con numero di server impostato
			// si prelevano i dati comuni dalla prima istanza
			AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
				processoServizio.getAggregatoProcessoServizioSorgenti();
			int numeroServers = list2.size();
			ProcessoServizio processoServizioTipo = null;
			if (processoServizio instanceof ProcessoServizioConBuffer)
				processoServizioTipo = new ProcessoServizioConBuffer();
			else if (processoServizio instanceof ProcessoServizioSenzaBuffer)
				processoServizioTipo = new ProcessoServizioSenzaBuffer();
			else throw new AEIintoElementiBaseException(processoServizio.getNome()+" have not specific type");
			processoServizioTipo.setAggregatoProcessoServizioSorgenti(aggregatoProcessoServizioSorgenti);
			processoServizioTipo.setNome(string);
			processoServizioTipo.setNumeroServers(numeroServers);
			// si impostano i numeri di visite medi, che si suppone sia uguali
			// per ogni server del centro di servizio multiserver

			// si cancellano da elementiBase gli oggetti contenuti in list2
			this.elementiBase.removeAll(list2);
			// si aggiunge ad elementiBase l'oggetto processoServizioTipo
			this.elementiBase.add(processoServizioTipo);
			}
		return this.elementiBase;
		}
	
	/**
	 * Restituisce una lista di elementi base corrispondenti a list.
	 * 
	 * @param list
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private List<ProcessoArriviFiniti> getProcessiArriviFinitiFromSpecificheSCAP(
			List<ISpecificheSCAP> list)
		throws AEIintoElementiBaseException
		{
		if (list == null) throw new AEIintoElementiBaseException("The finite arrivals process specification list "+
				"is null");
		List<ProcessoArriviFiniti> list2 = new ArrayList<ProcessoArriviFiniti>(list.size());
		for (ISpecificheSCAP specificheSCAP : list)
			{
			String string = specificheSCAP.getNomeIstanza();
			ElementoBaseQN elementoBaseQN = this.elementiBase.getElementoBaseFromName(string);
			if (elementoBaseQN == null)
				throw new AEIintoElementiBaseException(string+" have not a relative finite arrivals process");
			if (!(elementoBaseQN instanceof ProcessoArriviFiniti))
				throw new AEIintoElementiBaseException(string+" have not a relative finite arrivals process");
			ProcessoArriviFiniti processoArriviFiniti = (ProcessoArriviFiniti)elementoBaseQN;
			list2.add(processoArriviFiniti);
			}
		return list2;
		}
	
	/**
	 * Restituisce una lista di elementi base corrispondenti a list.
	 * 
	 * @param list
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private List<ProcessoServizio> getProcessiServizioFromSpecificheSP(
			List<ISpecificheSP> list)
			throws AEIintoElementiBaseException
		{
		if (list == null) 
			throw new AEIintoElementiBaseException("The specification service process list is null");
		List<ProcessoServizio> list2 = new ArrayList<ProcessoServizio>(list.size());
		for (ISpecificheSP specificheSP : list)
			{
			String string = specificheSP.getNomeIstanza();
			ElementoBaseQN elementoBaseQN = this.elementiBase.getElementoBaseFromName(string);
			if (elementoBaseQN == null)
				throw new AEIintoElementiBaseException(string+" have not a relative service process");
			if (!(elementoBaseQN instanceof ProcessoServizio))
				throw new AEIintoElementiBaseException(string+" have not a relative service process");
			ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
			list2.add(processoServizio);
			}
		return list2;
		}
	
	public boolean verificaUPAPJobsExit()
		{
		boolean ris = true;
		for (ElementoBaseQN elementoBaseQN : this.elementiBase)
			{
			if (elementoBaseQN instanceof ProcessoJoin)
				{
				ProcessoJoin processoJoin = (ProcessoJoin)elementoBaseQN;
				// 1) prelevo il nome del canale del processo join
				String string = processoJoin.getCanale();
				if (string != null)
					{
					// 2) cerco in elementiBase l'elemento base corrispondente al nome del canale
					ElementoBaseQN elementoBaseQN2 = this.elementiBase.getElementoBaseFromName(string);
					// (per precondizione deve essere stato eseguito il metodo normalizzazioneArriviFiniti)
					// 3) verifico se il processo job ha un exit, corrispondente al fatto che:
					// non ci sono destinazioni oppure c'è il nome di un'azione di 
					// destinazione uguale a null
					ris = processoJoin.verificaNoJobExit();
					// 4) se ris è false allora l'elemento base corrispondente al nome del canale
					// deve essere un processo di arrivi infiniti
					if (!ris)
						{
						if (!(elementoBaseQN2 instanceof ProcessoArriviInfiniti))
							return false;
						}
					}
				}
			if (elementoBaseQN instanceof ProcessoRouting)
				{
				ProcessoRouting processoRouting = (ProcessoRouting)elementoBaseQN;
				// 1) prelevo il nome del canale del processo join
				String string = processoRouting.getCanale();
				// 2) cerco in elementiBase l'elemento base corrispondente al nome del canale
				ElementoBaseQN elementoBaseQN2 = this.elementiBase.getElementoBaseFromName(string);
				// 3) verifico se il processo job ha un exit, corrispondente al fatto che:
				// non ci sono destinazioni oppure c'è il nome di un'azione di 
				// destinazione uguale a null
				ris = processoRouting.verificaNoJobExit();
				// 4) se ris è false allora l'elemento base corrispondente al nome del canale
				// deve essere un processo di arrivi infiniti
				if (!ris)
					{
					if (!(elementoBaseQN2 instanceof ProcessoArriviInfiniti))
						return false;
					}
				}
			if (elementoBaseQN instanceof ProcessoServizio)
				{
				ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
				// 1) per ogni DataProcessoServizioSorgente
				AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
					processoServizio.getAggregatoProcessoServizioSorgenti();
				for (DataProcessoServizioSorgente dataProcessoServizioSorgente : aggregatoProcessoServizioSorgenti)
					{
					// 1.1) prelevo il nome della classe
					String string = dataProcessoServizioSorgente.getClasse();
					// 1.2) cerco in elementiBase l'elemento base corrispondente al nome della classe
					ElementoBaseQN elementoBaseQN2 = this.elementiBase.getElementoBaseFromName(string);
					// 1.3) verifico se il processo job ha un exit, corrispondente al fatto che:
					// non ci sono destinazioni oppure c'è il nome di un'azione di 
					// destinazione uguale a null
					ris = dataProcessoServizioSorgente.verificaNoJobExit();
					// 1.4) se ris è false allora l'elemento base corrispondente al nome del canale
					// deve essere un processo di arrivi infiniti
					if (!ris)
						{
						if (!(elementoBaseQN2 instanceof ProcessoArriviInfiniti))
							return false;
						}
					}
				}
			}
		return true;
		}

	@Override
	public ElementoBaseQN getElementoBaseFromName(String string) 
		{
		return elementiBase.getElementoBaseFromName(string);
		}

	@Override
	public IMappingSpecifiche<ISpecifiche> getSpecificheObject(String string)
			throws AEIintoElementiBaseException 
		{
		IMappingSpecifiche<ISpecifiche> mappingSpecifiche =
			this.specificheObjects.getMappingSpecificheFromInstanceName(string);
		if (mappingSpecifiche == null)
			throw new AEIintoElementiBaseException("The instance "+
					string+" is not found");
		return mappingSpecifiche;
		}	
	}
