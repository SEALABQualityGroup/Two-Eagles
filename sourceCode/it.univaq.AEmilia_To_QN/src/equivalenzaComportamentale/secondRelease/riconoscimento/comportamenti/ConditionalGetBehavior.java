package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;


import specificheAEmilia.AETinteractions;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.Expression;
import specificheAEmilia.IdentExpr;
import specificheAEmilia.IntegerRangeType;
import specificheAEmilia.Maggiore;
import specificheAEmilia.Minore;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.VarInit;

public class ConditionalGetBehavior 
	extends GetBehavior 
	{
	
	public ConditionalGetBehavior(
			VarInit[] varInits,
			ProcessTerm processTerm, 
			AETinteractions tinteractions
			) 
		{
		super(varInits,processTerm,tinteractions);
		}
	
	public boolean isConditionalGetBehavior()
		{
		if (!this.isGetBehavior())
			return false;
		return true;
		}
	
	// restituisce null se ConditionalGetBehavior non è un ConditionalGetBehavior
	public ProcessTerm getMaximalConditionalGetBehavior()
		{
		if (!this.isGetBehavior())
			return null;
		ProcessTerm processTerm = getMaximalGetBehavior();
		Expression expression = this.processTerm.getCondition();
		processTerm.setCondition(expression.copy());
		return processTerm;
		}

	// restituisce null se ConditionalGetBehavior non è un ConditionalGetBehavior
	public ProcessTerm getConditionalGetBehavior()
		{
		if (!this.isGetBehavior())
			return null;
		ProcessTerm processTerm = getGetBehavior();
		Expression expression = this.processTerm.getCondition();
		processTerm.setCondition(expression.copy());
		return processTerm;
		}
	
	// restituisce il nome dell'azione get 
	// restituisce null se processTerm non è un comportamento get
	public String getGetName()
		{
		if (!isConditionalGetBehavior())
			return null;
		return this.nameAction();
		}
	
	/*
	 * Restituisce true se e solo se processTerm ha una condizione di esecuzione uguale 
	 * alla verifica di non pienezza del buffer dell'i-esimo parametro di declPars.
	 */
	public boolean getConditionParameter(int i) 
		{
		// si preleva l'i-esimo dichiarazione di parametro da declPars
		ParamDeclaration paramDeclaration = this.varInits[i];
		// come precondizione ho che declPar è di tipo VarInit
		VarInit varInit = (VarInit)paramDeclaration;
		// si preleva il nome del parametro
		String string = varInit.getName();
		// come precondizione ho che varInit è un intervallo di interi
		IntegerRangeType integerRangeType = (IntegerRangeType)varInit.getType();
		// si preleva l'estremo superiore dell'intervallo
		Expression expression = integerRangeType.getEndingInt();
		// si crea un'identificatore espressione da string
		IdentExpr identExpr = new IdentExpr(string);
		// si crea un espressione string < espressione
		Minore minore = new Minore(identExpr,expression);
		// si crea un espressione espressione > string
		Maggiore maggiore = new Maggiore(expression,identExpr);
		// si preleva la condizione di processTerm
		Expression espressione2 = processTerm.getCondition();
		// se espressione2 non è uguale a minore o maggiore si restituisce false
		if (minore.equals(espressione2))
			return true;
		else if (maggiore.equals(espressione2))
			return true;
		else
			return false;
		}
	}
