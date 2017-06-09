/**
 * 
 */
package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.NullAction;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ProcessTerm;

/**
 * Rappresenta un comportamento con una sequenza di zero o più azioni null.
 * 
 * @author Mirko
 *
 */
public class NullBehavior 
	{
	
	private ProcessTerm processTerm;
	private AETinteractions tinteractions;
	
	public NullBehavior(ProcessTerm processTerm, 
			AETinteractions tinteractions) 
		{
		super();
		this.processTerm = processTerm;
		this.tinteractions = tinteractions;
		}

	/*
	 * Restituisce il termine di processo più grande di processTerm costituito da zero
	 * o più azioni null.
	 * restituisce null se processTerm non inizia con un'azione null.
	 */
	public ProcessTerm getMaximalNullBehavior()
		{
		ProcessTerm ris = null;
		if (!(this.processTerm instanceof ActionProcess))
			return ris;
		ActionProcess actionProcess = (ActionProcess)this.processTerm;
		Action action = actionProcess.getAzione();
		// action deve essere un'azione null
		NullAction nullAction = new NullAction(action,tinteractions);
		if (!nullAction.isNull())
			return ris;
		ProcessTerm processTerm = actionProcess.getProcesso();
		NullBehavior nullBehavior = new NullBehavior(
				processTerm == null ? null : processTerm.copy(),tinteractions);
		ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
		ris = new ActionProcess(action.copy(),processTerm2);
		return ris;
		}
	}
