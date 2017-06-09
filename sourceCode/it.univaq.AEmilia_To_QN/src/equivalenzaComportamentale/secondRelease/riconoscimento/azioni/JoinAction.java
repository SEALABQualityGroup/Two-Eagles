package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateInf;

public class JoinAction {

	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isJoin;

	public JoinAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isJoin = true;
		// Un'azione di join è definita da una 
		// and interazione di input
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts =
			new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getAndInput();
		if (!list.contains(string)) isJoin = isJoin && false;
		// join deve essere un'azione immediata
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof RateInf))
			isJoin = isJoin && false;
		isJoin = isJoin && true;
		}
	
	public boolean isJoin()
		{
		return isJoin;
		}
}
