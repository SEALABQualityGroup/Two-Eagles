package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.ListaElementiBase;
import elementiBaseQN.Buffer;
import elementiBaseQN.ElementoBaseQN;
import elementiBaseQN.ProcessoArrivi;
import elementiBaseQN.ProcessoFork;
import elementiBaseQN.ProcessoJoin;
import elementiBaseQN.ProcessoRouting;
import elementiBaseQN.ProcessoServizio;
import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataBuffer;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;

/*
 * Con questa classe è possibile costruirsi una vista
 * della rete di code a seconda del nome del carico
 * di lavoro che viene passato al costruttore.
 * Possiamo anche generare la vista di un carico di lavoro
 * per un dato elemento base fornito come argomento.
 */
public class ViewGeneration 
	{
	
	private String chainName;

	public ViewGeneration(String chainName) 
		{
		super();
		this.chainName = chainName;
		}
	
	// la lista restituita può contenere dei null
	public List<ElementoBaseQN> getView(ListaElementiBase listaElementiBase)
		{
		List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>();
		for (ElementoBaseQN elementoBaseQN : listaElementiBase)
			{
			List<ElementoBaseQN> list2 = getView(elementoBaseQN);
			list.addAll(list2);
			}
		// svuoto list dagli elementi null
		List<ElementoBaseQN> list2 = new ArrayList<ElementoBaseQN>();
		for (ElementoBaseQN elementoBaseQN : list)
			{
			if (elementoBaseQN != null)
				list2.add(elementoBaseQN);
			}
		return list2;
		}
	
	// la lista restituita può contenere dei null
	public List<ElementoBaseQN> getView(List<ElementoBaseQN> list)
		{
		List<ElementoBaseQN> list2 = new ArrayList<ElementoBaseQN>();
		for (ElementoBaseQN elementoBaseQN : list2)
			{
			List<ElementoBaseQN> list3 = getView(elementoBaseQN);
			list2.addAll(list3);
			}
		return list2;
		}
	
	/*
	 * Restituisce una lista vuota se buffer non cura richieste per il carico chainName.
	 */
	public List<Buffer<?>> getView(Buffer<?> buffer)
		{
		List<Buffer<?>> listR = new ArrayList<Buffer<?>>();
		AggregatoBuffer<?> aggregatoBuffer = buffer.getAggregatoBuffer();
		List<DataBuffer> list = new ArrayList<DataBuffer>();
		for (Object object : aggregatoBuffer)
			{
			// per precondizione object deve essere di tipo DataBuffer oppure una sua sottoclasse
			DataBuffer dataBuffer = (DataBuffer)object;
			String string = dataBuffer.getClasse();
			if (string.equals(this.chainName))
				list.add(dataBuffer);
			}
		// se la taglia di list è maggiore di zero dobbiamo restituire la vista di buffer con 
		// un DataBuffer
		String string = buffer.getNome();
		for (int i = 0; i < list.size(); i++)
			{
			DataBuffer dataBuffer = list.get(i);
			AggregatoBuffer<DataBuffer> aggregatoBuffer2 = new AggregatoBuffer<DataBuffer>();
			aggregatoBuffer2.add(dataBuffer);
			Buffer<DataBuffer> buffer2 = new Buffer<DataBuffer>(string,aggregatoBuffer2);
			listR.add(buffer2);
			}
		return listR;
		}

	/*
	 * Restituisce null se processoArrivi non cura richieste per il carico chainName.
	 */
	public ProcessoArrivi getView(ProcessoArrivi processoArrivi)
		{
		String string = processoArrivi.getNome();
		if (string.equals(this.chainName))
			return processoArrivi;
		else
			return null;
		}

	/*
	 * Restituisce null se processoFork non cura richieste per il carico chainName.
	 */
	public ProcessoFork getView(ProcessoFork processoFork)
		{
		String string = processoFork.getCanale();
		if (string.equals(this.chainName))
			return processoFork;
		else
			return null;
		}

	/*
	 * Restituisce null se processoJoin non cura richieste per il carico chainName.
	 */
	public ProcessoJoin getView(ProcessoJoin processoJoin)
		{
		String string = processoJoin.getCanale();
		if (string.equals(this.chainName))
			return processoJoin;
		else
			return null;
		}

	/*
	 * Restituisce null se processoRouting non cura richieste per il carico chainName.
	 */
	public ProcessoRouting getView(ProcessoRouting processoRouting)
		{
		String string = processoRouting.getCanale();
		if (string.equals(this.chainName))
			return processoRouting;
		else
			return null;
		}

	/*
	 * Restituisce una lista vuota se processoServizio non cura richieste per il carico chainName.
	 * Ogni processo di servizio restituito ha lo stesso nome di processoServizio.
	 */
	public List<ProcessoServizio> getView(ProcessoServizio processoServizio)
		{
		List<ProcessoServizio> list = new ArrayList<ProcessoServizio>();
		AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti = 
			processoServizio.getAggregatoProcessoServizioSorgenti();
		List<DataProcessoServizioSorgente> list2 = new ArrayList<DataProcessoServizioSorgente>();
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : aggregatoProcessoServizioSorgenti)
			{
			String string = dataProcessoServizioSorgente.getClasse();
			if (string.equals(this.chainName))
				list2.add(dataProcessoServizioSorgente);
			}
		// se la taglia di list è maggiore di zero, dobbiamo restituire la vista di buffer con 
		// un unico DataProcessoServizioSorgenti. Altrimenti restituiamo una lista vuota.
		String string = processoServizio.getNome();
		for (int i = 0; i < list2.size(); i++)
			{
			DataProcessoServizioSorgente dataProcessoServizioSorgente = list2.get(i);
			AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti2 =
				new AggregatoProcessoServizioSorgenti();
			aggregatoProcessoServizioSorgenti2.add(dataProcessoServizioSorgente);
			ProcessoServizio processoServizio2 = new ProcessoServizio(string);
			processoServizio2.setAggregatoProcessoServizioSorgenti(aggregatoProcessoServizioSorgenti2);
			list.add(processoServizio2);
			}
		return list;
		}
	
	/*
	 * Restituisce una lista vuota se elementoBaseQN non cura richiesta di classe
	 * chainName. Restituisce una lista di elementi non null.
	 */
	public List<ElementoBaseQN> getView(ElementoBaseQN elementoBaseQN)
		{
		List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>();
		if (elementoBaseQN instanceof Buffer<?>)
			{
			Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
			List<Buffer<?>> list2 = getView(buffer);
			list.addAll(list2);
			}
		if (elementoBaseQN instanceof ProcessoArrivi)
			{
			ProcessoArrivi processoArrivi = (ProcessoArrivi)elementoBaseQN;
			ProcessoArrivi processoArrivi2 = getView(processoArrivi);
			if (processoArrivi != null)
				list.add(processoArrivi2);
			}
		if (elementoBaseQN instanceof ProcessoFork)
			{
			ProcessoFork processoFork = (ProcessoFork)elementoBaseQN;
			ProcessoFork processoFork2 = getView(processoFork);
			if (processoFork2 != null)
				list.add(processoFork2);
			}
		if (elementoBaseQN instanceof ProcessoJoin)
			{
			ProcessoJoin processoJoin = (ProcessoJoin)elementoBaseQN;
			ProcessoJoin processoJoin2 = getView(processoJoin);
			if (processoJoin2 != null)
				list.add(processoJoin2);
			}
		if (elementoBaseQN instanceof ProcessoRouting)
			{
			ProcessoRouting processoRouting = (ProcessoRouting)elementoBaseQN;
			ProcessoRouting processoRouting2 = getView(processoRouting);
			if (processoRouting2 != null)
				list.add(processoRouting2);
			}
		if (elementoBaseQN instanceof ProcessoServizio)
			{
			ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
			List<ProcessoServizio> list2 = getView(processoServizio);
			list.addAll(list2);
			}
		return list;
		}
	}
