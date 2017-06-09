/**
 * 
 */
package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateInf;

/**
 * @author Mirko
 *
 */
public class NullAction 
	{
	
	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isNull;

	public NullAction(Action action, 
			AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isNull = true;
		/*
		 * Un'azione null è definita da un'azione 
		 * immediata e interna.
		 */
		// l'azione null deve essere immediata
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof RateInf))
			isNull = isNull && false;
		// l'azione null deve essere un'azione interna
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getNamesFromInteractions();
		if (list.contains(string))
			isNull = isNull && false;
		isNull = isNull && true;
		}

	public boolean isNull()
		{
		return isNull;
		}
	}
