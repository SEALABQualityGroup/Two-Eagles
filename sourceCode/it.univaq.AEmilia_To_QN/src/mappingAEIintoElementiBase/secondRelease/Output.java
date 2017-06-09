package mappingAEIintoElementiBase.secondRelease;

import java.util.HashMap;
import java.util.List;

import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheSP;

class Output 
	{
	
	/**
	 * Restituisce una lista dei nomi delle interazioni di output di specifiche.
	 *
	 * @param specifiche
	 * @return
	 */
	List<String> output(ISpecifiche specifiche)
		{
		return specifiche.getOutputInteractionsNames();
		}

	HashMap<String, List<String>> output(ISpecificheSP specificheSP)
		{
		return specificheSP.getDeliversFromSelection();
		}

	}
