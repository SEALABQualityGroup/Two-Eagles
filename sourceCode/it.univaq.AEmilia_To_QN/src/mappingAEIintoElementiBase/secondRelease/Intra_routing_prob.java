package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import restrizioniSpecifiche.interfaces.ISpecificheAP;
import restrizioniSpecifiche.interfaces.ISpecificheJP;
import restrizioniSpecifiche.interfaces.ISpecificheRP;
import restrizioniSpecifiche.interfaces.ISpecificheSP;
import specificheAEmilia.Expression;

class Intra_routing_prob 
	{
	
	/**
	 * Restituisce in output una lista i cui valori sono una lista di probabilità di routing corrispondenti.
	 * Tali probabilità non tengono in considerazione la cardinalità delle istanze collegate di ogni
	 * interazione di input.
	 *
	 * @param ISpecificheAP
	 * @return
	 */
	List<Double> intra_routing_prob(ISpecificheAP specificheAP)
		throws AEIintoElementiBaseException
		{
		List<Double> list = new ArrayList<Double>();
		List<Expression> list2 = specificheAP.getProbRouting();
		Expression[] espressiones = new Expression[list2.size()];
		list2.toArray(espressiones);
		// si trasformano le espressioni delle probabilità di routing in
		// double e si aggiungono a list
		// per precondizione espressiones non ha null
		list = MetodiVari.getDoubleFromEspressioniProportioned(espressiones);
		return list;
		}

	/**
	 * Restituisce in output una lista i cui valori sono una lista di probabilità di routing corrispondenti.
	 * Tali probabilità non tengono in considerazione la cardinalità delle istanze collegate di ogni
	 * interazione di input.
	 *
	 * @param specificheJP
	 * @return
	 */
	List<Double> intra_routing_prob(ISpecificheJP specificheJP)
		throws AEIintoElementiBaseException
		{
		List<Double> list = new ArrayList<Double>();
		List<Expression> list2 = specificheJP.getProbRouting();
		Expression[] espressiones = new Expression[list2.size()];
		list2.toArray(espressiones);
		// si trasformano le espressioni delle probabilità di routing in
		// double e si aggiungono a list
		// per precondizione espressiones non contiene null
		list = MetodiVari.getDoubleFromEspressioniProportioned(espressiones);
		return list;
		}

	/**
	 * Restituisce in output una lista i cui valori sono una lista di probabilità 
	 * di routing corrispondenti.
	 * Tali probabilità non tengono in considerazione la cardinalità delle istanze collegate di ogni
	 * interazione di input.
	 *
	 * @param specifiche
	 * @return
	 */
	List<Double> intra_routing_prob(ISpecificheRP specifiche)
		throws AEIintoElementiBaseException
		{
		List<Double> list = new ArrayList<Double>();
		List<Expression> list2 = specifiche.getProbRouting();
		Expression[] espressiones = new Expression[list2.size()];
		list2.toArray(espressiones);
		// si trasformano le espressioni delle probabilità di routing in
		// double e si aggiungono a list
		// per precondizione espressiones non contiene null 
		list = MetodiVari.getDoubleFromEspressioniProportioned(espressiones);
		return list;
		}

	/**
	 * Restituisce una mappa che ha come chiavi i nomi delle iterazioni di input del
	 * processo di servizio e come valori una lista di probabilità di routing corrispondenti.
	 * Se non ci sono probabilità di routing associate allora il valore è null.
	 * Tali probabilità non tengono in considerazione la cardinalità delle istanze collegate di ogni
	 * interazione di input.
	 *
	 * @param iEquivalenzaServizio
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	HashMap<String, List<Double>> intra_routing_prob(ISpecificheSP specificheSP)
		throws AEIintoElementiBaseException
		{
		// hashMap ha come chiavi i nomi delle interazioni di input.
		HashMap<String, List<Expression>> hashMap = specificheSP.getProbRouting();
		// hashMap4 è la mappa risultato
		HashMap<String, List<Double>> hashMap4 = new HashMap<String, List<Double>>();
		Set<Entry<String, List<Expression>>> set = hashMap.entrySet();
		for (Entry<String, List<Expression>> entry : set)
			{
			// si preleva il nome dell'azione di selezione
			String string = entry.getKey();
			List<Expression> expressions = hashMap.get(string);
			if (expressions != null)
				{
				Expression[] espressiones2 = new Expression[expressions.size()];
				expressions.toArray(espressiones2);
				// per precondizione espressiones2 non contiene null
				List<Double> list = MetodiVari.getDoubleFromEspressioniProportioned(espressiones2);
				hashMap4.put(string, list);
				}
			else
				{
				hashMap4.put(string, null);
				}
			}
		return hashMap4;
		}	
	}
