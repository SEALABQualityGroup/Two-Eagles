package mappingAEIintoElementiBase.secondRelease;

import java.util.List;

import restrizioniSpecifiche.interfaces.ISpecifiche;

class Input 
	{
	
	/**
	 * Restituisce una lista dei nomi delle interazioni di input di specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	List<String> input(ISpecifiche specifiche)
		{
		return specifiche.getInputInteractionsNames();
		}
	
	}
