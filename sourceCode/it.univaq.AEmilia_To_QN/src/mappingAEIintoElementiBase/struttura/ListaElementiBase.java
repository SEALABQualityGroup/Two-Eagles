package mappingAEIintoElementiBase.struttura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;

import elementiBaseQN.ElementoBaseException;
import elementiBaseQN.ElementoBaseQN;
import elementiBaseQN.ProcessoArrivi;

/**
 * Struttura utilizzata per la gestione degli elementi base durante la trasformazione da aei
 * in elementi base. Contiene ogni associazione tra nome di istanza e tipo di elemento
 * architetturale.
 * 
 * @author Mirko
 *
 */
public class ListaElementiBase 
	extends ArrayList<ElementoBaseQN> 
	implements Serializable
	{
	/*
	 * Rappresenta una mappa in cui le chiavi corrispondono ad una istanza e il valore 
	 * rappresenta il nome del tipo di elemento architetturale a cui appartiene.
	 * Non esiste nessun mapping per le istanze che hanno come elemento base associato
	 * un elemento base con lo stesso nome.
	 */
	private HashMap<String, String> mappaNomeTipo = new HashMap<String, String>();
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Restituisce l'elemento base dal suo nome.
	 * 
	 * @param string
	 * @return
	 */
	public ElementoBaseQN getElementoBaseFromName(String string)
		{
		ElementoBaseQN elementoBaseQN = null;
		for (ElementoBaseQN elementoBaseQN2 : this)
			{
			String string2 = elementoBaseQN2.getNome();
			if (string.equals(string2))
				elementoBaseQN = elementoBaseQN2;
			}
		return elementoBaseQN;
		}
	
	public void addNomeTipo(String string, String string2)
		{
		this.mappaNomeTipo.put(string, string2);
		}

	public boolean containsKey(Object key) 
		{
		return mappaNomeTipo.containsKey(key);
		}

	public String get(Object key) 
		{
		return mappaNomeTipo.get(key);
		}
	
	/**
	 * Restituisce l'elemento base relativo all'istanza di nome string.
	 * 
	 * @param string
	 * @return
	 */
	public ElementoBaseQN getElementoBaseFromAEI(String string)
		{
		ElementoBaseQN elementoBaseQN = null;
		String string2 = null;
		if (mappaNomeTipo.containsKey(string))
			string2 = mappaNomeTipo.get(string);
		else
			string2 = string;
		for (ElementoBaseQN elementoBaseQN2 : this)
			{
			String string3 = elementoBaseQN2.getNome();
			if (string2.equals(string3))
				elementoBaseQN = elementoBaseQN2;
			}
		return elementoBaseQN;
		}

	/**
	 * Imposta il buffer di ogni elemento base a seconda del primo elemento base
	 * connesso come sorgente.
	 * 
	 * @throws AEIintoElementiBaseException
	 */
	public void impostaBuffers()
		throws AEIintoElementiBaseException
		{
		for (StrutturaInterazione strutturaInterazione : this)
			{
			try {
				strutturaInterazione.setBuffers();
				} 
			catch (ElementoBaseException e) 
				{
				throw new AEIintoElementiBaseException(e);
				}
			}
		}

	/**
	 * Normalizza le probabilità di routing a seconda del numero degli elementi base 
	 * connessi ad ogni azione di output.
	 * 
	 * @throws AEIintoElementiBaseException
	 */
	public void normalizzaProbabilita()
		throws AEIintoElementiBaseException
		{
		for (StrutturaInterazione strutturaInterazione : this)
			{
			try {
				strutturaInterazione.proporzionaProbabilita();
				} 
			catch (ElementoBaseException e) 
				{
				throw new AEIintoElementiBaseException(e);
				}
			}
		}
	
	public List<ProcessoArrivi> getArrivalProcesses()
		{
		List<ProcessoArrivi> list = new ArrayList<ProcessoArrivi>();
		for (ElementoBaseQN elementoBaseQN : this)
			{
			if (elementoBaseQN instanceof ProcessoArrivi)
				{
				ProcessoArrivi processoArrivi = (ProcessoArrivi)elementoBaseQN;
				list.add(processoArrivi);
				}
			}
		return list;
		}
	}
