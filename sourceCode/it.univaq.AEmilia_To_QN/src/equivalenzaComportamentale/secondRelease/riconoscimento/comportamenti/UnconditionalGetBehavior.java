package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Boolean;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.VarInit;

public class UnconditionalGetBehavior 
	extends GetBehavior
	{
	
	public UnconditionalGetBehavior(
			VarInit[] dps, 
			ProcessTerm processTerm, 
			AETinteractions interactions) 
		{
		super(dps,processTerm,interactions);
		}

	public boolean isUnconditionalGetBehavior()
		{
		if (!getConditionTrue(processTerm)) return false;
		if (!isGetBehavior()) 
			return false;
		return true;
		}
	
	// restituisce il nome dell'azione get 
	// restituisce null se processTerm non è un comportamento get
	public String getGetName()
		{
		if (!isUnconditionalGetBehavior())
			return null;
		return this.nameAction();
		}
	
	/**
	 * Restituisce true se e solo se la condizione di esecuzione
	 * di processTerm è true
	 * 
	 * @param processTerm
	 */
	private boolean getConditionTrue(ProcessTerm processTerm) 
		{
		// si preleva la condizione di esecuzione
		// del processo
		Expression c1 = processTerm.getCondition();
		// la condizione deve essere uguale a true
		if (!c1.equals(new Boolean(true))) return false;
		else return true;
		}
	
	// restituisce null se UnconditionalGetBehavior non è un UnconditionalGetBehavior
	public ProcessTerm getMaximalUnconditionalGetBehavior()
		{
		if (!getConditionTrue(processTerm)) return null;
		if (!isGetBehavior()) 
			return null;
		return getMaximalGetBehavior();
		}

	// restituisce null se ConditionalGetBehavior non è un ConditionalGetBehavior
	public ProcessTerm getUnconditionalGetBehavior()
		{
		if (!getConditionTrue(processTerm)) return null;
		if (!isGetBehavior()) 
			return null;
		return getGetBehavior();
		}
	}
