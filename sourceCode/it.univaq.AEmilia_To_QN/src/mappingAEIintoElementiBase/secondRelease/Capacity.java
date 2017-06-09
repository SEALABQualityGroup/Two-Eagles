package mappingAEIintoElementiBase.secondRelease;

import java.util.HashMap;
import java.util.List;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import restrizioniSpecifiche.interfaces.ISpecificheB;
import restrizioniSpecifiche.interfaces.ISpecificheFCB;
import restrizioniSpecifiche.interfaces.ISpecificheUB;
import specificheAEmilia.Expression;
import specificheAEmilia.Real;
import equivalenzaComportamentale.MetodiVari;

class Capacity 
	{
	
	/**
	 * Restituisce una mappa di capacità per ogni classe di clienti
	 * corrispondenti all'oggetto ISpecificheB di input.
	 * Tale mappa ha come chiavi i nomi delle azioni get e come valori la capacità corrispondente.
	 * Se l'oggetto in input è di tipo IEquivalenzaBufferIllimitato,
	 * si restituisce null.
	 *
	 * @param iEquivalenzaBuffer
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	HashMap<String,Integer> capacity(ISpecificheB specifiche)
		throws AEIintoElementiBaseException
		{
		HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
		if (specifiche instanceof ISpecificheUB)
			return null;
		else
			{
			ISpecificheFCB specificheFCB = 
				(ISpecificheFCB)specifiche;
			List<String> list = specificheFCB.getInputInteractionsNames();
			for (String string : list)
				{
				// si estrae la capacità relativa a string
				Expression expression = specificheFCB.getLimiteClasse(string);
				if (!(MetodiVari.isOnlyInteger(expression)) && 
						!(MetodiVari.isOnlyReal(expression)))
					throw new AEIintoElementiBaseException("The buffer size for relative class "+
							" of input interaction "+string+" is not a integer");
				if (MetodiVari.isOnlyInteger(expression))
					{
					specificheAEmilia.Integer integer = (specificheAEmilia.Integer)expression;
					int i = integer.getValore();
					hashMap.put(string, i);
					}
				if (MetodiVari.isOnlyReal(expression))
					{
					Real real = (Real)expression;
					double d = real.getValore();
					Double double1 = new Double(d);
					int i = double1.intValue();
					hashMap.put(string, i);
					}
				}
			}
		return hashMap;
		}	
	}
