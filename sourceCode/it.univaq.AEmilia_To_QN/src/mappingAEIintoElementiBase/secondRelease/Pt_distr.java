package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.RateExp;
import specificheAEmilia.RateNoExp;
import specificheAEmilia.Stop;
import valutazione.NormalizeException;
import valutazione.Valutazione;

class Pt_distr 
	{
	
	/**
	 * La durata di un processo azione è uguale alla durata dell'azione più la durata del termine
	 * di processo che segue l'azione.
	 *
	 * @param actionProcess
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private double pt_distr(ActionProcess actionProcess)
		throws AEIintoElementiBaseException
		{
		double d;
		Action action = actionProcess.getAzione();
		ActionType actionType = action.getType();
		String string = actionType.getName();
		ActionRate actionRate = action.getRate();
		if (actionRate instanceof RateExp)
			{
			RateExp rateExp = (RateExp)actionRate;
			Expression expression = rateExp.getExpr();
			double d2;
			Valutazione valutazione = new Valutazione();
			Object object = null;
			try {
				object = valutazione.returnDouble(expression);
				} 
			catch (NormalizeException e) 
				{
				new AEIintoElementiBaseException(e);
				}
			// per precondizione object è un Double
			Double double1 = (Double)object;
			d2 = 1 / double1;
			ProcessTerm processTerm = actionProcess.getProcesso();
			d = d2 + pt_distr(processTerm);
			}
		else throw new AEIintoElementiBaseException("The action "+string+" is not exponentially timed");
		return d;
		}
	
	/**
	 * La durata di un'equazione comportamentale è data dalla durata del comportamento che
	 * definisce l'equazioe comportamentale.
	 *
	 * @param behavEquation
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	double pt_distr(BehavEquation behavEquation)
		throws AEIintoElementiBaseException
		{
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		return pt_distr(processTerm);
		}
	
	/**
	 * La durata di una chiamata comportamentale è 0 per precondizione.
	 * 
	 * @param behavProcess
	 * @return
	 */
	private double pt_distr(BehavProcess behavProcess)
		{
		return 0;
		}
	
	/**
	 * La durata di un processo scelta è data dalla media delle durate di ogni alternativa,
	 * a seconda della probabilità di selezione dell'alternativa stessa.
	 *
	 * @param choiceProcess
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private double pt_distr(ChoiceProcess choiceProcess)
		throws AEIintoElementiBaseException
		{
		double d = 0;
		ProcessTerm[] processTerms = choiceProcess.getProcesses();
		// introduciamo una lista di probabilità che venga scelta una particolare fase.
		// tali probabilità corrispondono ai pesi delle azioni di scelta.
		// list contiene le probabilità di scelta
		// list2 contiene la durata del processo di un'alterativa
		List<Double> list = new ArrayList<Double>();
		List<Double> list2 = new ArrayList<Double>();
		for (ProcessTerm processTerm : processTerms)
			{
			if (processTerm instanceof ActionProcess)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action action = actionProcess.getAzione();
				ActionType actionType = action.getType();
				String string = actionType.getName();
				ActionRate actionRate = action.getRate();
				if (actionRate instanceof RateNoExp)
					{
					RateNoExp rateNoExp = (RateNoExp)actionRate;
					Expression expression = rateNoExp.getWeight();
					Valutazione valutazione = new Valutazione();
					Object object;
					try {
						object = valutazione.returnDouble(expression);
						} 
					catch (NormalizeException e1) 
						{
						throw new AEIintoElementiBaseException(e1);
						}
					// per precondizione object è un Double
					Double double1 = (Double)object;
					ProcessTerm processTerm2 = actionProcess.getProcesso();
					list.add(double1);
					list2.add(pt_distr(processTerm2));
					}
				else throw new AEIintoElementiBaseException("The action "+string+" is exponential");
				}
			else throw new AEIintoElementiBaseException("Unexpected process type");
			}
		// si restituisce il tasso calcolato in modo hyperesponenziale
		// si calcola la somma delle probabilità (e)
		double e = 0;
		for (Double double1 : list)
			{
			e = e + double1;
			}
		for (int i = 0; i < list.size(); i++)
			{
			double f = list.get(i);
			double g = list2.get(i);
			f = f / e;
			d = d + (g * f);
			}
		return d;
		}
	
	/**
	 * La durata di un generico termine di processo viene calcolata a seconda del tipo di processTerm.
	 *
	 * @param processTerm
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	private double pt_distr(ProcessTerm processTerm)
		throws AEIintoElementiBaseException
		{
		double d = 0;
		// si procede per casi a seconda del tipo di processTerm
		if (processTerm instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm;
			d = pt_distr(actionProcess);
			}
		else if (processTerm instanceof BehavProcess)
			{
			BehavProcess behavProcess = (BehavProcess)processTerm;
			d = pt_distr(behavProcess);
			}
		else if (processTerm instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm;
			d = pt_distr(choiceProcess);
			}
		else if (processTerm instanceof Stop)
			{
			Stop stop = (Stop)processTerm;
			d = pt_distr(stop);
			}
		return d;
		}
	
	/**
	 * La durata dell'azione stop è zero.
	 *
	 * @param stop
	 * @return
	 */
	private double pt_distr(Stop stop)
		{
		return 0;
		}
	}
