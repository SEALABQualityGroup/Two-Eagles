package elementiBaseQN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Strutture.ListaStrutturaInputFork;
import elementiBaseQN.Strutture.ListaStrutturaOutputFork;

public class ProcessoFork extends ElementoBaseQN 
	{

	private static final long serialVersionUID = 1L;

	// sorgenti ha come chiavi i nomi delle interazioni 
	// di input dell'elemento.
	// Le restrizioni di sintassi specifiche ci dicono che
	// può esserci più di una sorgente
	protected ListaStrutturaInputFork sorgenti = new ListaStrutturaInputFork();
	
	// destinazioni ha come chiavi i nomi delle interazioni 
	// di output dell'elemento.
	// Le restrizioni di sintassi specifiche ci dicono che
	// può esserci più di una destinazione
	private ListaStrutturaOutputFork destinazioni = new ListaStrutturaOutputFork();
	
	private String canale = "";
	
	private Double numberOfVisits;

	public ProcessoFork()
		{
		super();
		}

	public ProcessoFork(String nome, HashMap<String, ElementoBaseQN> sorgenti,
		HashMap<String, Destinazione> destinazioni, String canale)
		throws ElementoBaseException
		{
		super(nome);
		this.canale = canale;
		this.destinazioni = new ListaStrutturaOutputFork();
		Set<Entry<String, Destinazione>> set2 = destinazioni.entrySet();
		for (Entry<String, Destinazione> entry : set2)
			{
			String string = entry.getKey();
			Destinazione destinazione = entry.getValue();
			List<Destinazione> list = new ArrayList<Destinazione>();
			list.add(destinazione);
			this.destinazioni.put(string, list);
			}
		this.sorgenti = new ListaStrutturaInputFork();
		Set<Entry<String, ElementoBaseQN>> set = sorgenti.entrySet();
		for (Entry<String, ElementoBaseQN> entry : set)
			{
			String string = entry.getKey();
			ElementoBaseQN elementoBaseQN = entry.getValue();
			List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>();
			list.add(elementoBaseQN);
			this.sorgenti.put(string, list);
			}
		}

	
	
	public ProcessoFork(HashMap<String, List<ElementoBaseQN>> sorgenti,
			HashMap<String, List<Destinazione>> destinazioni, String canale) 
		{
		super();
		this.sorgenti = new ListaStrutturaInputFork(sorgenti);
		this.destinazioni = new ListaStrutturaOutputFork(destinazioni);
		this.canale = canale;
		}

	public ProcessoFork(String string) 
		{
		super(string);
		}

	/**
	 * Restituisce il numero dell'azioni fork.
	 * 
	 * @return
	 */
	public int getNumeroForks()
		{
		return destinazioni.size();
		}

	public String getCanale()
		{
		return canale;
		}

	public void setCanale(String canale) 
		{
		this.canale = canale;
		}

	/**
	 * Imposta la struttura per gli output del fork ad una struttura in cui c'è un unico
	 * elemento base attaccato ad ogni azione fork.
	 * 
	 * @param sorgenti
	 */
	public void setSorgentiForOne(HashMap<String, ElementoBaseQN> sorgenti) 
		{
		this.sorgenti = new ListaStrutturaInputFork();
		this.sorgenti.putForSingle(sorgenti);
		}

	/**
	 * Restituisce una mappa in cui le chiavi sono i nomi delle azioni di destinazione e i valori
	 * corrispondono alla prima destinazione connessa all'azione di fork.
	 * 
	 * @return
	 */
	public HashMap<String, Destinazione> getDestinazioniForOne() 
		{
		HashMap<String, Destinazione> hashMap = new HashMap<String, Destinazione>();
		Set<Entry<String, List<Destinazione>>> set = this.destinazioni.entrySet();
		for (Entry<String, List<Destinazione>> entry : set)
			{
			String string = entry.getKey();
			List<Destinazione> list = entry.getValue();
			hashMap.put(string, list.get(0));
			}
		return hashMap;
		}

	/**
	 * Imposta una mappa che associa ad ogni azione fork un'unica destinazione connessa all'azione. 
	 * 
	 * @param destinazioni
	 */
	public void setDestinazioniForOne(HashMap<String, Destinazione> destinazioni) 
		{
		this.destinazioni = new ListaStrutturaOutputFork();
		this.destinazioni.putForSingle(destinazioni);
		}

	public HashMap<String, List<ElementoBaseQN>> getSorgenti() 
		{
		return sorgenti.getHashMap();
		}

	public void setSorgenti(HashMap<String, List<ElementoBaseQN>> sorgenti) 
		{
		this.sorgenti = new ListaStrutturaInputFork(sorgenti);
		}

	public HashMap<String, List<Destinazione>> getDestinazioni() 
		{
		return destinazioni.getHashMap();
		}

	public void setDestinazioni(HashMap<String, List<Destinazione>> destinazioni) 
		{
		this.destinazioni = new ListaStrutturaOutputFork(destinazioni);
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoFork))
			return false;
		ProcessoFork processoFork = (ProcessoFork)obj;
		Set<Entry<String, List<Destinazione>>> set =
			this.getDestinazioni().entrySet();
		Set<Entry<String, List<Destinazione>>> set2 =
			processoFork.getDestinazioni().entrySet();
		if (set.size() != set2.size())
			return false;
		// si controlla se set e set2 contengono gli stessi elementi.
		for (Entry<String, List<Destinazione>> entry : set)
			{
			if (!containEntryForName(set2, entry))
				return false;
			}
		for (Entry<String,List<Destinazione>> entry : set2)
			{
			if (!containEntryForName(set, entry))
				return false;
			}
		Set<Entry<String, List<ElementoBaseQN>>> set3 =
			this.getSorgenti().entrySet();
		Set<Entry<String, List<ElementoBaseQN>>> set4 =
			this.getSorgenti().entrySet();
		if (!set3.containsAll(set4))
			return false;
		if (!set4.containsAll(set3))
			return false;
		if (!this.getCanale().equals(processoFork.getCanale()))
			return false;
		return super.equals(obj);
		}
	
	/**
	 * Inizializza la mappa per l'associazione di interazioni di input con i relativi elementi base ad una
	 * mappa in cui i nomi delle interazioni di input di this corrispondono agli elementi di list e i
	 * valori sono liste vuote di elementi base.
	 * 
	 * @param list
	 * @throws AEIintoElementiBaseException
	 */
	public void inizializzaMappaSorgenti(List<String> list)
		throws ElementoBaseException
		{
		if (list == null) 
			throw new ElementoBaseException("The input list is null");
		this.sorgenti = new ListaStrutturaInputFork();
		for (String string : list)
			{
			List<ElementoBaseQN> list2 = new ArrayList<ElementoBaseQN>();
			this.sorgenti.put(string, list2);
			}
		}
	
	/**
	 * Inizializza la mappa per l'associazione di interazioni di output con i relativi elementi base ad una
	 * mappa in cui i nomi delle interazioni di output di this corrispondono agli elementi di list e i
	 * valori sono liste vuote di destinazioni.
	 * 
	 * @param list
	 * @throws AEIintoElementiBaseException
	 */
	public void inizializzaMappaDestinazioni(List<String> list)
		throws ElementoBaseException
		{
		if (list == null) 
			throw new ElementoBaseException("The input list is null");
		this.destinazioni = new ListaStrutturaOutputFork();
		for (String string : list)
			{
			List<Destinazione> list2 = new ArrayList<Destinazione>();
			this.destinazioni.put(string, list2);
			}
		}

	@Override
	public StrutturaInterazioneInput getStrutturaInput(String string) 
		{
		return this.sorgenti.getStrutturaFromInput(string);
		}

	@Override
	public StrutturaInterazioneOutput getStrutturaOutput(String string) 
		{
		return this.destinazioni.getStrutturaFromOutput(string);
		}
	
	public ListaStrutturaInputFork getStrutturaSorgenti()
		{
		return this.sorgenti;
		}
	
	public ListaStrutturaOutputFork getStrutturaDestinazioni()
		{
		return this.destinazioni;
		}

	public ProcessoFork(String nome, ListaStrutturaInputFork sorgenti,
			ListaStrutturaOutputFork destinazioni, String canale) 
		{
		super(nome);
		this.sorgenti = sorgenti;
		this.destinazioni = destinazioni;
		this.canale = canale;
		}

	public void setSorgenti(ListaStrutturaInputFork sorgenti) 
		{
		this.sorgenti = sorgenti;
		}

	public void setDestinazioni(ListaStrutturaOutputFork destinazioni) 
		{
		this.destinazioni = destinazioni;
		}
	
	/**
	 * Restituisce true se e solo se entry è contenuta in set. 
	 * 
	 * @param set
	 * @param entry
	 * @return
	 */
	private boolean containEntryForName(Set<Entry<String, List<Destinazione>>> set, 
			Entry<String, List<Destinazione>> entry)
		{
		// è possibile evitare le seguenti azioni se si utilizzano i metodi di Set
		boolean b = false;
		String string = entry.getKey();
		List<Destinazione> list = entry.getValue();
		for (Entry<String, List<Destinazione>> entry2 : set)
			{
			String string2 = entry2.getKey();
			List<Destinazione> list2 = entry2.getValue();
			if (string.equals(string2))
				{
				for (Destinazione destinazione : list2)
					{
					// il nome di ogni elemento di list2 deve essere il nome
					// di un elemento di list
					if (!list.contains(destinazione))
						return false;
					}
				// in questo caso abbiamo trovato la corrispondenza
				b = true;
				}
			}
		return b;
		}
	
	@Override
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		this.destinazioni.replaceDestination(destinazione, list);
		}

	@Override
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list) 
		{
		this.sorgenti.replaceSource(elementoBaseQN, list);
		}

	public Double getNumberOfVisits() 
		{
		return numberOfVisits;
		}

	public void setNumberOfVisits(Double numberOfVisits) 
		{
		this.numberOfVisits = numberOfVisits;
		}
	}