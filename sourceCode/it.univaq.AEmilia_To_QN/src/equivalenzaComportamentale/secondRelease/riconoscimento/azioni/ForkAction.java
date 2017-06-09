package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateInf;

public class ForkAction {

	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isFork;

	public ForkAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isFork = true;
		// Un'azione di fork è definita da una 
		// and interazione di output
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getAndOutput();
		if(!list.contains(string)) isFork = isFork && false;
		// un'azione fork è immediata
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof RateInf)) isFork = isFork && false;
		isFork = isFork && true;
		}
	
	public boolean isFork()
		{
		return isFork;
		}
}
