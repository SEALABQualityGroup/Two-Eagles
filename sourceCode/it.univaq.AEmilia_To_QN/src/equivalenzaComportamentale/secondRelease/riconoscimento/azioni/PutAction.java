package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.Rate_;

public class PutAction {

	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isPut;

	public PutAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isPut = true;
		// Un'azione di put è definita da 
		// una uni interazione di output
		// L'azione di put è una or-interazione se e soltanto se è attaccata
		// ad un processo di servizio multiserver
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts =
			new AETinteractionsParts(this.tinteractions);
		List<String> list2 = tinteractionsParts.getUniOutput();
		List<String> list3 = tinteractionsParts.getOrOutput();
		if (!(list2.contains(string)) && !(list3.contains(string)))
			isPut = isPut && false;
		// l'azione di put è passiva
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof Rate_))
			isPut = isPut && false;
		isPut = isPut && true;
		}
	
	public boolean isPut()
		{
		return isPut;
		}
}
