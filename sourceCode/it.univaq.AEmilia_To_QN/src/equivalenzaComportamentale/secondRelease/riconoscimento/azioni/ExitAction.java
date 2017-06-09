package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateNoExp;

public class ExitAction 
	{
	
	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isDeliver; 

	public ExitAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isDeliver = true;
		// Un'azione di consegna è una uni o or interazione di output
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getUniOutput();
		List<String> list2 = tinteractionsParts.getOrOutput();
		if (!(list.contains(string) || list2.contains(string)))
			isDeliver = isDeliver && false;
		// non temporizzata esponenzialmente
		ActionRate actionRate = this.action.getRate();
		// il tasso dell'azione deve essere RateNoExp
		if (!(actionRate instanceof RateNoExp))
			isDeliver = isDeliver && false;
		isDeliver = isDeliver && true;
		}
	
	public boolean isDeliver()
		{
		return isDeliver;
		}
	}
