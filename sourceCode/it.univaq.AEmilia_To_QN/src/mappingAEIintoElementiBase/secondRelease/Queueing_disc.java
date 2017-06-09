package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.struttura.Disciplina;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheB;
import restrizioniSpecifiche.interfaces.ISpecificheSPWB;
import specificheAEmilia.Expression;

class Queueing_disc 
	{
	
	/**
	 * Restituisce un valore dell'enumerazione Disciplina nel seguente modo:
	 * se tutte le interazioni di input dei processi di servizio al quale un buffer è attaccato,
	 * hanno la stessa priorità, allora si restituisce FCFS,
	 * altrimenti si restituisce NP.
	 * (non-preemptive, senza prelazione - il centro di servizio estrae dalla coda il cliente con priorità
	 * maggiore. I clienti con uguale priorità vengono serviti con una politica FIFO).
	 * Restituisce null se il buffer non è relativo a processi di servizio.
	 *
	 * @param specificheB
	 * @return
	 */
	Disciplina queueing_disc(ISpecificheB specificheB)
		throws AEIintoElementiBaseException
		{
		// 1) si verifica che le equivalenze di output sono tutte relative ad oggetti di tipo
		// ISpecificheSPWB. Altrimenti si restituisce null.
		// 2) per ognuno di tali processi di servizio con buffer, si prelevano le priorità di selezione.
		// se le priorità di selezione sono tutte uguali si restituisce FCFS, altrimenti si restituisce NP.
		List<ISpecifiche> list;
		try {
			list = specificheB.getSpecificheOutput();
			} 
		catch (RestrizioniSpecException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		for (ISpecifiche specifiche : list)
			{
			if (!(specifiche instanceof ISpecificheSPWB))
				return null;
			}
		// si memorizzano in una stessa struttura tutte le priorità di selezione
		List<Expression> list2 = new ArrayList<Expression>();
		for (ISpecifiche specifiche : list)
			{
			ISpecificheSPWB specificheSPWB = (ISpecificheSPWB)specifiche;
			Expression[] espressiones = specificheSPWB.getPrioSelezione();
			List<Expression> list3 = new CopyOnWriteArrayList<Expression>(espressiones);
			list2.addAll(list3);
			}
		// si verifica se le espressioni contenute in list2 siano uguali
		// si preleva la prima espressione di list2
		Expression expression = list2.get(0);
		for (int i = 1; i < list2.size(); i++)
			{
			if (!expression.equals(list2.get(i)))
				return Disciplina.NP;
			}
		return Disciplina.FCFS;
		}	
	}
