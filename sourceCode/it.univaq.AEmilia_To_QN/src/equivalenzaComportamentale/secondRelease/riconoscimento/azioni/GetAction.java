package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.Rate_;

public class GetAction 
	{

	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isGet;

	public GetAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isGet = true;
		// Un'azione get è definita 
		// da una uni o or interazione di input
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getOrInput();
		List<String> list2 = tinteractionsParts.getUniInput();
		if (!(list.contains(string) || list2.contains(string)))
			isGet = isGet && false;
		// get deve essere un'azione passiva
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof Rate_))
			isGet = isGet && false;
		isGet = isGet && true;
		}
	
	public boolean isGet()
		{
		return isGet;
		}
	}
