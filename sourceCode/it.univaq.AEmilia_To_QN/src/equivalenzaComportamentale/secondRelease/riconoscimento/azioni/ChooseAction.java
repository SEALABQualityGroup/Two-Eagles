package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.RateInf;
import equivalenzaComportamentale.AETinteractionsParts;

public class ChooseAction 
	{
	
	private Action action;
	private AETinteractions tinteractions;
	private boolean isChoose;
	
	public ChooseAction(Action action,
			AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isChoose = true;
		// Un'azione di prosecuzione del percorso è 
		// definita da un'azione interna
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getNamesFromInteractions();
		// se il nome dell'azione è contenuto in list si restituisce false
		// perchè vuol dire che l'azione non è interna
		if (list.contains(string)) isChoose = isChoose && false;
		// L'azione deve essere immediata
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof RateInf))
			isChoose = isChoose && false;
		isChoose = isChoose && true;
		}
	
	
	public boolean isChoose()
		{
		return isChoose;
		}
	}
