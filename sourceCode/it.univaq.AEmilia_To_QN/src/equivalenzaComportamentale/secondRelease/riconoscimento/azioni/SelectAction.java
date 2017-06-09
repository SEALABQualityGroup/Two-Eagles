package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateInf;

public class SelectAction {

	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isSelect;

	public SelectAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isSelect = true;
		// Un'azione di selezione 
		// è definita da una uni interazione di input
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts =
			new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getUniInput();
		if (!list.contains(string))
			isSelect = isSelect && false;
		// select è un'azione immediata
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof RateInf))
			isSelect = isSelect && false;
		isSelect = isSelect && true;
		}
	
	public boolean isSelect()
		{
		return isSelect;
		}
}
