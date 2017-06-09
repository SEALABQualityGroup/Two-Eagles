package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.Rate_;

public class ReturnAction {

	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isReturn;

	public ReturnAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isReturn = true;
		// Un'azione di ritorno è definita da una 
		// uni interazione di input
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts =
			new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getUniInput();
		if (!list.contains(string))
			isReturn = isReturn && false;
		// l'azione return deve essere passiva
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof Rate_))
			isReturn = isReturn && false;
		isReturn = isReturn && true;
		}
	
	public boolean isReturn()
		{
		return isReturn;
		}
}
