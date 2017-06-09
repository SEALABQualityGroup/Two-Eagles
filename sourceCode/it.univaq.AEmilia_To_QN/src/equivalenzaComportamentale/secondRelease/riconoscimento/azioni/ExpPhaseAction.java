package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateExp;

public class ExpPhaseAction 
	{
	
	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isPhase;

	public ExpPhaseAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isPhase = true;
		// Un'azione di fase è un'azione interna
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts =
			new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getNamesFromInteractions();
		if (list.contains(string))
			isPhase = isPhase && false;
		// l'azione di fase è esponenziale
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof RateExp))
			isPhase = isPhase && false;
		isPhase = isPhase && true;
		}
	
	public boolean isPhase()
		{
		return isPhase;
		}
	}
