package mappingAEIintoElementiBase.secondRelease;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import restrizioniSpecifiche.interfaces.ISpecificheAP;
import specificheAEmilia.BehavEquation;

class Int_arr_time 
	{
	
	/**
	 * Restituisce in output un double corrispondente al tempo di pensiero o tempo di interarrivo
	 * calcolato dalla funzione pt_distr applicata all'oggetto BehavEquation del tipo
	 * di elemento architetturale wrappato dallo ISpecificheAP,
	 * che rappresenta l'equazione di generazione di un job, sostituendo ogni chiamata comportamentale
	 * con uno stop.
	 *
	 * @param ISpecificheAP
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	double int_arr_time(ISpecificheAP specificheAP)
		throws AEIintoElementiBaseException
		{
		BehavEquation behavEquation = specificheAP.getPhaseBehavior();
		Pt_distr pt_distr = new Pt_distr();
		double d = pt_distr.pt_distr(behavEquation);
		return d;
		}
	
	}
