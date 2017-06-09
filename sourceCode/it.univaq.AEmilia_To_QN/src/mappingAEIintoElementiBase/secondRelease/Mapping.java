package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.struttura.Disciplina;
import mappingAEIintoElementiBase.struttura.IMappingSpecifiche;
import restrizioniSpecifiche.interfaces.ISpecifiche;
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
import specificheAEmilia.Expression;
import elementiBaseQN.BufferIllimitato;
import elementiBaseQN.BufferLimitato;
import elementiBaseQN.ElementoBaseException;
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
import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.AggregatoBufferLimitato;
import elementiBaseQN.Strutture.AggregatoProcessoArriviConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoArriviRitorni;
import elementiBaseQN.Strutture.AggregatoProcessoJoinConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoJoinSorgenti;
import elementiBaseQN.Strutture.AggregatoProcessoRoutingConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoRoutingSorgenti;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataBuffer;
import elementiBaseQN.Strutture.DataBufferLimitato;

class Mapping 
	{
		
	/**
	 * Trasforma mappingSpecifiche in un elemento base.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	ElementoBaseQN mapping(IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		ElementoBaseQN elementoBaseQN = null;
		if (specifiche instanceof ISpecificheUPAP)
			elementoBaseQN = mappingIntoProcessoArriviInfiniti(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheSCAP)
			elementoBaseQN = mappingIntoProcessoArriviFiniti(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheUB)
			elementoBaseQN = mappingIntoBufferIllimitato(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheFCB)
			elementoBaseQN = mappingIntoBufferLimitato(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheFPWB)
			elementoBaseQN = mappingIntoProcessoForkConBuffer(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheFPNB)
			elementoBaseQN = mappingIntoProcessoForkSenzaBuffer(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheJPWB)
			elementoBaseQN = mappingIntoProcessoJoinConBuffer(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheJPNB)
			elementoBaseQN = mappingIntoProcessoJoinSenzaBuffer(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheSPWB)
			elementoBaseQN = mappingIntoProcessoServizioConBuffer(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheSPNB)
			elementoBaseQN = mappingIntoProcessoServizioSenzaBuffer(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheRPWB)
			elementoBaseQN = mappingIntoProcessoRoutingConBuffer(mappingSpecifiche);
		else if (specifiche instanceof ISpecificheRPNB)
			elementoBaseQN = mappingIntoProcessoRoutingSenzaBuffer(mappingSpecifiche);
		else throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+" have not "+
				"a specific type");
		return elementoBaseQN;
		}

	/**
	 * Trasforma mappingSpecifiche in un oggetto BufferIllimitato.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private BufferIllimitato mappingIntoBufferIllimitato(IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheUB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a unbounded buffer");
		ISpecificheUB specificheUB = (ISpecificheUB)specifiche;
		// si chiama name
		Name name = new Name();
		BufferIllimitato bufferIllimitato = name.name(specificheUB);
		// si imposta la disciplina di scheduling
		Queueing_disc queueing_disc = new Queueing_disc();
		Disciplina disciplina = queueing_disc.queueing_disc(specificheUB);
		bufferIllimitato.setDisciplinaScheduling(disciplina);
		// si istanzia l'oggetto AggregatoBuffer
		AggregatoBuffer<DataBuffer> aggregatoBuffer = new AggregatoBuffer<DataBuffer>();
		// si prelevano le azioni di input
		Input input = new Input();
		List<String> list = input.input(specificheUB);
		// si prelevano le azioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheUB);
		// list e list2 devono avere la stessa lunghezza
		if (list.size() != list2.size())
			throw new AEIintoElementiBaseException("The input interaction list and"+
					" the output interaction list have not same size");
		HashMap<String, String> hashMap = mappingSpecifiche.getClassiInput();
		HashMap<String, String> hashMap2 = mappingSpecifiche.getClassiOutput();
		// preleviamo la mappa degli ordini di classi
		HashMap<String, Integer> hashMap3 = getOrdineClassi(hashMap);
		int contatoreNullOrdine = 0;
		for (String string : list)
			{
			Integer integer = hashMap3.get(string);
			if (integer == null)
				{
				integer = contatoreNullOrdine;
				contatoreNullOrdine++;
				}
			// si istanzia un oggetto DataBuffer
			DataBuffer dataBuffer = new DataBuffer(integer);
			// si imposta l'azione di get
			dataBuffer.setGetAction(string);
			// si imposta l'azione di put
			List<String> list3 = specificheUB.getOutputsFromInput(string);
			// per precondizione c'è un'unica azione di output associata a string
			String string2 = list3.get(0);
			dataBuffer.setPutAction(string2);
			// si imposta la classe
			// nel caso in cui non ci sono processi di arrivi le interazioni non hanno nessuna
			// classe associata
			// per default il nome della classe è la stringa vuota
			if (hashMap.containsKey(string))
				{
				String string3 = hashMap.get(string);
				String string4 = hashMap2.get(string2);
				if (!string3.equals(string4))
					throw new AEIintoElementiBaseException("The "+string+" action channel is "+
						string3+
						", but the "+string2+" relative put action channel is "+string4);
				dataBuffer.setClasse(string3);
				}
			// si aggiunge dataBuffer all'aggregato
			aggregatoBuffer.add(dataBuffer);
			}
		// si assegna l'aggregato
		bufferIllimitato.setAggregatoBuffer(aggregatoBuffer);
		// si restituisce il buffer
		return bufferIllimitato;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto BufferLimitato.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private BufferLimitato mappingIntoBufferLimitato(IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheFCB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a finite capacity buffer");
		ISpecificheFCB specificheFCB = (ISpecificheFCB)specifiche;
		// si chiama name
		Name name = new Name();
		BufferLimitato bufferLimitato = name.name(specificheFCB);
		// si imposta la disciplina di scheduling
		Queueing_disc queueing_disc = new Queueing_disc();
		Disciplina disciplina = queueing_disc.queueing_disc(specificheFCB);
		bufferLimitato.setDisciplinaScheduling(disciplina);
		// si istanzia l'oggetto AggregatoBufferLimitato
		AggregatoBufferLimitato aggregatoBufferLimitato = new AggregatoBufferLimitato();
		// si prelevano le azioni di input
		Input input = new Input();
		List<String> list = input.input(specificheFCB);
		// si prelevano le azioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheFCB);
		// list e list2 deveno avere la stessa lunghezza
		if (list.size() != list2.size())
			throw new AEIintoElementiBaseException("The input interaction list and the "+
					"output interaction list have not same size");
		// si prelevano le capacità
		Capacity capacity = new Capacity();
		HashMap<String, Integer> hashMap3 = capacity.capacity(specificheFCB);
		HashMap<String, String> hashMap = mappingSpecifiche.getClassiInput();
		HashMap<String, String> hashMap2 = mappingSpecifiche.getClassiOutput();
		HashMap<String, Integer> hashMap4 = getOrdineClassi(hashMap);
		// si assegnano i vari DataBufferLimitato
		int conatoreNullOrdine = 0;
		for (String string : list)
			{
			Integer integer1 = hashMap4.get(string);
			// se integer1  è null vuol dire che non abbiamo un carico di lavoro per la classe
			// di clienti che stiamo considerando
			if (integer1 == null)
				{
				integer1 = conatoreNullOrdine;
				conatoreNullOrdine++;
				}
			// si istanzia un oggetto DataBuffer
			DataBufferLimitato dataBufferLimitato = new DataBufferLimitato(integer1);
			// si imposta l'azione di get
			dataBufferLimitato.setGetAction(string);
			// si imposta l'azione di put
			List<String> list3 = specificheFCB.getOutputsFromInput(string);
			// per precondizione esiste una sola azione di output associata a string
			String string2 = list3.get(0);
			dataBufferLimitato.setPutAction(string2);
			// si imposta la classe
			// nel caso in cui non ci sono processi di arrivi le interazioni non hanno nessuna
			// classe associata
			if (hashMap.containsKey(string))
				{
				String string3 = hashMap.get(string);
				String string4 = hashMap2.get(string2);
				if (!string3.equals(string4))
					throw new AEIintoElementiBaseException("The "+string+" channel is "+string3+
						", but the "+string2+" relative put channel is "+string4);
				dataBufferLimitato.setClasse(string3);
				}
			// si imposta la capacità
			if (!hashMap3.containsKey(string))
				throw new AEIintoElementiBaseException("The action "+string+" have not a size");
			Integer integer = hashMap3.get(string);
			dataBufferLimitato.setCapacita(integer);
			// si aggiunge dataBuffer all'aggregato
			aggregatoBufferLimitato.add(dataBufferLimitato);
			}
		// si assegna l'aggregato
		bufferLimitato.setAggregatoBuffer(aggregatoBufferLimitato);
		// si restituisce il buffer
		return bufferLimitato;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoArriviFiniti.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoArriviFiniti mappingIntoProcessoArriviFiniti(IMappingSpecifiche<ISpecifiche> 
		mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheSCAP))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a arrival process for finite population");
		ISpecificheSCAP specificheSCAP =
			(ISpecificheSCAP)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoArriviFiniti processoArriviFiniti = name.name(specificheSCAP);
		// si imposta il tempo di pensiero
		Int_arr_time int_arr_time = new Int_arr_time();
		double d = int_arr_time.int_arr_time(specificheSCAP);
		processoArriviFiniti.setTempoPensiero(d);
		// si imposta l'oggetto AggregatoProcessoArriviRitorni
		// si prelevano le azioni di input
		Input input = new Input();
		List<String> list = input.input(specificheSCAP);
		AggregatoProcessoArriviRitorni aggregatoProcessoArriviRitorni =
			new AggregatoProcessoArriviRitorni(list);
		processoArriviFiniti.setAggregatoProcessoArriviRitorni(aggregatoProcessoArriviRitorni);
		// si imposta l'oggetto AggregatoProcessoArriviConsegne
		// si prelevano le azioni di consegna
		Output output = new Output();
		List<String> list2 = output.output(specificheSCAP);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		List<Double> list3 = intra_routing_prob.intra_routing_prob(specificheSCAP);
		AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne;
		try {
			aggregatoProcessoArriviConsegne = new AggregatoProcessoArriviConsegne(list2,list3);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoArriviFiniti.setAggregatoProcessoArriviConsegne(aggregatoProcessoArriviConsegne);
		return processoArriviFiniti;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoArriviInfiniti.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoArriviInfiniti mappingIntoProcessoArriviInfiniti(IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheUPAP))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not arrival process for unbounded population");
		ISpecificheUPAP specificheUPAP =
			(ISpecificheUPAP)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoArriviInfiniti processoArriviInfiniti = name.name(specificheUPAP);
		// si imposta il tempo di interarrivo
		Int_arr_time int_arr_time = new Int_arr_time();
		double d = int_arr_time.int_arr_time(specificheUPAP);
		processoArriviInfiniti.setTempoInterarrivo(d);
		// si imposta l'oggetto AggregatoProcessoArriviConsegne
		// si prelevano le azioni di consegna
		Output output = new Output();
		List<String> list2 = output.output(specificheUPAP);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		List<Double> list3 = intra_routing_prob.intra_routing_prob(specificheUPAP);
		AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne;
		try {
			aggregatoProcessoArriviConsegne = new AggregatoProcessoArriviConsegne(list2,list3);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoArriviInfiniti.setAggregatoProcessoArriviConsegne(aggregatoProcessoArriviConsegne);
		return processoArriviInfiniti;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoForkConBuffer.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoForkConBuffer mappingIntoProcessoForkConBuffer(IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo
		// ad un processo fork con buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheFPWB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a fork process with buffer");
		ISpecificheFPWB specificheFPWB =
			(ISpecificheFPWB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoForkConBuffer processoForkConBuffer = name.name(specificheFPWB);
		// si prelevano le interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheFPWB);
		// si prelevano le interazioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheFPWB);
		// si imposta il canale
		// si preleva il canale relativo alla prima interazione di input
		String string = list.get(0);
		// nel caso in cui non ci sono processi di arrivi le interazioni non hanno nessuna
		// classe associata
		if (mappingSpecifiche.getClassiInput().containsKey(string))
			{
			String string2 = mappingSpecifiche.getClassiInput().get(string);
			processoForkConBuffer.setCanale(string2);
			}
		// si inizializzano le mappe per le sorgenti
		try {
			processoForkConBuffer.inizializzaMappaSorgenti(list);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		// si inizializzano le mappe per le destinazioni
		try {
			processoForkConBuffer.inizializzaMappaDestinazioni(list2);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		return processoForkConBuffer;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoForkSenzaBuffer.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoForkSenzaBuffer mappingIntoProcessoForkSenzaBuffer
		(IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo
		// ad un processo fork senza buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheFPNB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a fork process with no buffer");
		ISpecificheFPNB specificheFPNB =
			(ISpecificheFPNB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoForkSenzaBuffer processoForkSenzaBuffer = name.name(specificheFPNB);
		// si prelevano le interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheFPNB);
		// si prelevano le interazioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheFPNB);
		// si imposta il canale
		// si preleva il canale relativo alla prima interazione di input
		String string = list.get(0);
		// nel caso in cui non ci sono processi di arrivi le interazioni non hanno nessuna
		// classe associata
		if (mappingSpecifiche.getClassiInput().containsKey(string))
			{
			String string2 = mappingSpecifiche.getClassiInput().get(string);
			processoForkSenzaBuffer.setCanale(string2);
			}
		// si inizializzano le mappe per le sorgenti
		try {
			processoForkSenzaBuffer.inizializzaMappaSorgenti(list);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		// si inizializzano le mappe per le destinazioni
		try {
			processoForkSenzaBuffer.inizializzaMappaDestinazioni(list2);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		return processoForkSenzaBuffer;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoJoinConBuffer.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoJoinConBuffer mappingIntoProcessoJoinConBuffer(IMappingSpecifiche<ISpecifiche>
		mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo al processo join
		// con buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheJPWB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a join process with buffer");
		ISpecificheJPWB specificheJPWB = (ISpecificheJPWB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoJoinConBuffer processoJoinConBuffer =
			name.name(specificheJPWB);
		// si prelevano le interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheJPWB);
		// si prelevano le interazioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheJPWB);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		List<Double> list3 = intra_routing_prob.intra_routing_prob(specificheJPWB);
		// si assegna l'oggetto AggregatoProcessoJoinSorgenti
		AggregatoProcessoJoinSorgenti aggregatoProcessoJoinSorgenti;
		try {
			aggregatoProcessoJoinSorgenti = new AggregatoProcessoJoinSorgenti(list);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoJoinConBuffer.setAggregatoProcessoJoinSorgenti(aggregatoProcessoJoinSorgenti);
		// si assegna l'oggetto AggregatoProcessoJoinConsegne
		AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne;
		try {
			// list2 può contenere null
			// list3 non può contenere null
			aggregatoProcessoJoinConsegne = new AggregatoProcessoJoinConsegne(list2,list3);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoJoinConBuffer.setAggregatoProcessoJoinConsegne(aggregatoProcessoJoinConsegne);
		// si assegna il canale
		// si preleva il canale relativo alla prima interazione di input
		String string = list.get(0);
		// nel caso in cui non ci sono processi di arrivi le interazioni non hanno nessuna
		// classe associata
		// per default il canale è la stringa vuota
		if (mappingSpecifiche.getClassiInput().containsKey(string))
			{
			String string2 = mappingSpecifiche.getClassiInput().get(string);
			processoJoinConBuffer.setCanale(string2);
			}
		return processoJoinConBuffer;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoJoinSenzaBuffer.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoJoinSenzaBuffer mappingIntoProcessoJoinSenzaBuffer(
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo ad un processo join
		// senza buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheJPNB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a join process with no buffer");
		ISpecificheJPNB specificheJPNB = (ISpecificheJPNB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoJoinSenzaBuffer processoJoinSenzaBuffer =
			name.name(specificheJPNB);
		// si prelevano le interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheJPNB);
		// si prelevano le interazioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheJPNB);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		List<Double> list3 = intra_routing_prob.intra_routing_prob(specificheJPNB);
		// si assegna l'oggetto AggregatoProcessoJoinSorgenti
		AggregatoProcessoJoinSorgenti aggregatoProcessoJoinSorgenti;
		try {
			aggregatoProcessoJoinSorgenti = new AggregatoProcessoJoinSorgenti(list);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoJoinSenzaBuffer.setAggregatoProcessoJoinSorgenti(aggregatoProcessoJoinSorgenti);
		// si assegna l'oggetto AggregatoProcessoJoinConsegne
		AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne;
		try {
			// list2 può contenere null
			// list3 non contiene null
			aggregatoProcessoJoinConsegne = new AggregatoProcessoJoinConsegne(list2,list3);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoJoinSenzaBuffer.setAggregatoProcessoJoinConsegne(aggregatoProcessoJoinConsegne);
		// si assegna il canale
		// si preleva il canale relativo alla prima interazione di input
		String string = list.get(0);
		// nel caso in cui non ci sono processi di arrivi le interazioni non hanno nessuna
		// classe associata
		// per default il canale è la stringa vuota
		if (mappingSpecifiche.getClassiInput().containsKey(string))
			{
			String string2 = mappingSpecifiche.getClassiInput().get(string);
			processoJoinSenzaBuffer.setCanale(string2);
			}
		return processoJoinSenzaBuffer;
		}
	
	private ElementoBaseQN mappingIntoProcessoRoutingConBuffer(
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo al processo routing
		// con buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheRPWB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a routing process with buffer");
		ISpecificheRPWB specificheRPWB = 
			(ISpecificheRPWB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoRoutingConBuffer processoRoutingConBuffer =
			name.name(specificheRPWB);
		// si prelevano le interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheRPWB);
		// si prelevano le interazioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheRPWB);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		List<Double> list3 = intra_routing_prob.intra_routing_prob(specificheRPWB);
		// si assegna l'oggetto AggregatoProcessoRoutingSorgenti
		AggregatoProcessoRoutingSorgenti aggregatoProcessoRoutingSorgenti;
		try {
			aggregatoProcessoRoutingSorgenti = new AggregatoProcessoRoutingSorgenti(list);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoRoutingConBuffer.setAggregatoProcessoRoutingSorgenti(aggregatoProcessoRoutingSorgenti);
		// si assegna l'oggetto AggregatoProcessoRoutingConsegne
		AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne;
		try {
			// list2 può contenere null
			// list3 non contiene null
			aggregatoProcessoRoutingConsegne = new AggregatoProcessoRoutingConsegne(list2,list3);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoRoutingConBuffer.setAggregatoProcessoRoutingConsegne(aggregatoProcessoRoutingConsegne);
		// si assegna il canale
		// si preleva il canale relativo alla prima interazione di input
		String string = list.get(0);
		// nel caso in cui non ci sono processi di arrivi le interazioni non hanno nessuna
		// classe associata
		if (mappingSpecifiche.getClassiInput().containsKey(string))
			{
			String string2 = mappingSpecifiche.getClassiInput().get(string);
			processoRoutingConBuffer.setCanale(string2);
			}
		return processoRoutingConBuffer;
		}
	
	private ElementoBaseQN mappingIntoProcessoRoutingSenzaBuffer(
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo ad un processo di routing
		// senza buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheRPNB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a routing process with no buffer");
		ISpecificheRPNB specificheRPNB = (ISpecificheRPNB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoRoutingSenzaBuffer processoRoutingSenzaBuffer =
			name.name(specificheRPNB);
		// si prelevano le interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheRPNB);
		// si prelevano le interazioni di output
		Output output = new Output();
		List<String> list2 = output.output(specificheRPNB);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		List<Double> list3 = intra_routing_prob.intra_routing_prob(specificheRPNB);
		// si assegna l'oggetto AggregatoProcessoRoutingSorgenti
		AggregatoProcessoRoutingSorgenti aggregatoProcessoRoutingSorgenti;
		try {
			aggregatoProcessoRoutingSorgenti = new AggregatoProcessoRoutingSorgenti(list);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoRoutingSenzaBuffer.setAggregatoProcessoRoutingSorgenti(aggregatoProcessoRoutingSorgenti);
		// si assegna l'oggetto AggregatoProcessoRoutingSorgenti
		AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne;
		try {
			// list2 può contenere dei null
			// list3 non ha null
			aggregatoProcessoRoutingConsegne = new AggregatoProcessoRoutingConsegne(list2,list3);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoRoutingSenzaBuffer.setAggregatoProcessoRoutingConsegne(aggregatoProcessoRoutingConsegne);
		// si assegna il canale
		// si preleva il canale relativo alla prima interazione di input
		String string = list.get(0);
		// ricordo che la rete di code può essere priva di processi di arrivi e quindi di canali
		// di default il canale è la stringa vuota
		if (mappingSpecifiche.getClassiInput().containsKey(string))
			{
			String string2 = mappingSpecifiche.getClassiInput().get(string);
			processoRoutingSenzaBuffer.setCanale(string2);
			}
		return processoRoutingSenzaBuffer;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoServizioConBuffer.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoServizioConBuffer mappingIntoProcessoServizioConBuffer(
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo ad processo di servizio
		// con buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheSPWB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a service process with buffer");
		ISpecificheSPWB specificheSPWB = (ISpecificheSPWB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoServizioConBuffer processoServizioConBuffer =
			name.name(specificheSPWB);
		// si assegna l'oggetto AggregatoProcessoServizioSorgenti
		// si prelevano i nomi delle interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheSPWB);
		// si prelevano le priorità di selezione
		Expression[] espressiones = specificheSPWB.getPrioSelezione();
		List<Integer> list2 = mappingAEIintoElementiBase.secondRelease.MetodiVari.getIntegerFromEspressioni(espressiones);
		// si prelevano le probabilità di selezione
		Expression[] espressiones2 = specificheSPWB.getProbSelezione();
		List<Double> list3 = mappingAEIintoElementiBase.secondRelease.MetodiVari.getDoubleFromEspressioniProportioned(espressiones2);
		// si prelevano i tempi di servizio
		Serv_time serv_time = new Serv_time();
		HashMap<String, Double> hashMap = serv_time.serv_time(specificheSPWB);
		// si prelevano le classi di input
		HashMap<String, String> hashMap2 = mappingSpecifiche.getClassiInput();
		HashMap<String, Integer> hashMap31 = getOrdineClassi(hashMap2);
		// si istanzia l'aggregato per le sorgenti del processo di servizio
		AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti;
		try {
			aggregatoProcessoServizioSorgenti = 
				new AggregatoProcessoServizioSorgenti(list,list2,list3,hashMap,hashMap2,hashMap31);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoServizioConBuffer.setAggregatoProcessoServizioSorgenti(aggregatoProcessoServizioSorgenti);
		// si assegna l'oggetto AggregatoProcessoServizioDestinazioni di ogni DataProcessoServizioSorgenti
		// si prelevano le interazioni di output
		Output output = new Output();
		HashMap<String, List<String>> hashMap3 = output.output(specificheSPWB);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		HashMap<String, List<Double>> hashMap4 = intra_routing_prob.intra_routing_prob(specificheSPWB);
		// si assegnano gli aggregati di destinazioni alle varie sorgenti
		try {
			// i valori di hashMap3 sono liste che possono contenere dei null
			aggregatoProcessoServizioSorgenti.setDestinazioniFromSelectionMaps(hashMap3,hashMap4);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		return processoServizioConBuffer;
		}
	
	/**
	 * Trasforma mappingSpecifiche in un oggetto ProcessoServizioSenzaBuffer.
	 *
	 * @param mappingSpecifiche
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private ProcessoServizioSenzaBuffer mappingIntoProcessoServizioSenzaBuffer(IMappingSpecifiche<ISpecifiche> mappingSpecifiche)
		throws AEIintoElementiBaseException
		{
		// si verifica se l'oggetto mappingSpecifiche sia relativo ad un processo di servizio
		// senza buffer
		ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
		if (!(specifiche instanceof ISpecificheSPNB))
			throw new AEIintoElementiBaseException(mappingSpecifiche.getNomeIstanza()+
					" is not a service process with no buffer");
		ISpecificheSPNB specificheSPNB = (ISpecificheSPNB)specifiche;
		// si chiama name
		Name name = new Name();
		ProcessoServizioSenzaBuffer processoServizioSenzaBuffer =
			name.name(specificheSPNB);
		// si assegna l'oggetto AggregatoProcessoServizioSorgenti
		// si prelevano i nomi delle interazioni di input
		Input input = new Input();
		List<String> list = input.input(specificheSPNB);
		// si prelevano i tempi di servizio
		Serv_time serv_time = new Serv_time();
		HashMap<String, Double> hashMap = serv_time.serv_time(specificheSPNB);
		// si prelevano le classi di input
		HashMap<String, String> hashMap2 = mappingSpecifiche.getClassiInput();
		// si prelevano le probabilità di selezione
		List<Expression> list2 = specificheSPNB.getProbSelection();
		Expression[] espressiones = new Expression[list2.size()];
		list2.toArray(espressiones);
		List<Double> list3 = 
			mappingAEIintoElementiBase.secondRelease.
			MetodiVari.getDoubleFromEspressioniProportioned(espressiones);
		HashMap<String, Integer> hashMap31 = getOrdineClassi(hashMap2);
		// si istanzia l'aggregato per le sorgenti del processo di servizio
		AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti;
		try {
			aggregatoProcessoServizioSorgenti = new AggregatoProcessoServizioSorgenti(list,list3,
					hashMap,hashMap2,hashMap31);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		processoServizioSenzaBuffer.setAggregatoProcessoServizioSorgenti(aggregatoProcessoServizioSorgenti);
		// si assegna l'oggetto AggregatoProcessoServizioDestinazioni di ogni DataProcessoServizioSorgenti
		// si prelevano le interazioni di output
		Output output = new Output();
		HashMap<String, List<String>> hashMap3 = output.output(specificheSPNB);
		// si prelevano le probabilità di routing
		Intra_routing_prob intra_routing_prob = new Intra_routing_prob();
		HashMap<String, List<Double>> hashMap4 = intra_routing_prob.intra_routing_prob(specificheSPNB);
		// si assegnano gli aggregati di destinazioni alle varie sorgenti
		try {
			aggregatoProcessoServizioSorgenti.setDestinazioniFromSelectionMaps(hashMap3,hashMap4);
			} 
		catch (ElementoBaseException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		return processoServizioSenzaBuffer;
		}
	
	// restituisce una mappa in cui le chiavi sono nomi di interazioni e i valori
	// l'ordine di classe a seconda del relativo valore nella mappa di input
	private HashMap<String, Integer> getOrdineClassi(HashMap<String, String> hashMap)
		{
		// list contiene i nomi di canali incontrati
		List<String> list = new ArrayList<String>();
		HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
		Set<Entry<String, String>> set = hashMap.entrySet();
		for (Entry<String, String> entry : set)
			{
			String string = entry.getKey();
			String string2 = entry.getValue();
			Integer integer = 0;
			for (String string3 : list)
				{
				if (string2.equals(string3))
					integer = integer + 1;
				}
			// aggiorniamo la mappa risultato
			hashMap2.put(string, integer);
			// aggiorniamo la lista temporanea
			list.add(string2);
			}
		return hashMap2;
		}
	}
