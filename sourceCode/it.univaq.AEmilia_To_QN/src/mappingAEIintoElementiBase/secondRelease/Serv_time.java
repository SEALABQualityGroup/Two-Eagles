package mappingAEIintoElementiBase.secondRelease;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import restrizioniSpecifiche.interfaces.ISpecificheSP;
import specificheAEmilia.BehavEquation;

class Serv_time 
	{
	
	/**
	 * Restituisce in output una mappa le cui chiavi sono i nomi delle interazioni di input
	 * e come valori oggetti Double corrispondenti ai tempi di servizio calcolati
	 * dalla funzione pt_distr applicata all'oggetto BehavEquation del tipo
	 * di elemento architetturale wrappato da specificheSP.
	 *
	 * @param specificheSP
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	HashMap<String, Double> serv_time(ISpecificheSP specificheSP)
		throws AEIintoElementiBaseException
		{
		// l'impostazione deve considerare l'azione di selezione associata al tempo di servizio
		HashMap<String, String> hashMap = specificheSP.getServicesNamesFromSelections();
		// si prelevano le equazioni di servizio
		List<BehavEquation> list = specificheSP.getServiceEquations();
		// per ogni equazione di servizio ottenuta in precedenza, si chiama la funzione pt_distr.
		// Alla fine si ottiene una mappa le cui chiavi sono i nomi delle equazioni di servizio
		// e i valori corrispondono al tasso di servizio.
		HashMap<String,Double> hashMap2 = new HashMap<String,Double>();
		for (BehavEquation behavEquation : list)
			{
			String string = behavEquation.getBehavHeader().getName();
			Pt_distr pt_distr = new Pt_distr();
			double d = pt_distr.pt_distr(behavEquation);
			hashMap2.put(string, d);
			}
		// a partire da hashMap e hashMap2 si costruisce la mappa risultato
		HashMap<String, Double> hashMap3 = new HashMap<String, Double>();
		if (hashMap.size() != hashMap2.size())
			throw new AEIintoElementiBaseException("The selection actions and "+
					"the service equations have not same size");
		Set<Entry<String, String>> set = hashMap.entrySet();
		for (Entry<String, String> entry : set)
			{
			String string = entry.getKey();
			// string2 è il nome dell'equazione di servizio 
			// corrispondente a string
			String string2 = entry.getValue();
			// double1 è il tasso di servizio corrispondente a string2
			Double double1 = hashMap2.get(string2);
			hashMap3.put(string, double1);
			}
		// si restituisce la mappa risultato
		return hashMap3;
		}
	
	}
