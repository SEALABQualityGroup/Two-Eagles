package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateInf;
import equivalenzaComportamentale.AETinteractionsParts;

public class PrePhaseAction 
	{
	
	private Action action;
	private AETinteractions tinteractions;
	private boolean isPhaseChoice;
	

	public PrePhaseAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isPhaseChoice = true;
		// Un'azione di scelta fase è un'azione interna
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts =
			new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getNamesFromInteractions();
		if (list.contains(string))
			isPhaseChoice = isPhaseChoice && false;
		// un'azione di scelta fase è immediata
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof RateInf))
			isPhaseChoice = isPhaseChoice && false;
		isPhaseChoice = isPhaseChoice && true;
		}
	
	public boolean isPhaseChoice()
		{
		return isPhaseChoice;
		}
	}
