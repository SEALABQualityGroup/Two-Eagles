package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import java.util.ArrayList;
import java.util.List;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.Expression;
import specificheAEmilia.IdentExpr;
import specificheAEmilia.Integer;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.Real;
import specificheAEmilia.Somma;
import specificheAEmilia.VarInit;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.GetAction;

public class GetBehavior 
	{
	
	protected VarInit[] varInits;
	protected ProcessTerm processTerm;
	protected AETinteractions tinteractions;
	private BehavProcess behavProcess;

	public GetBehavior() 
		{
		super();
		}
	
	public GetBehavior(VarInit[] varInits,
			ProcessTerm processTerm, AETinteractions tinteractions) 
		{
		super();
		this.varInits = varInits;
		this.processTerm = processTerm;
		this.tinteractions = tinteractions;
		}

	/**
	 * Restituisce true se e solo se processTerm rappresenta un processo get incondizionato.
	 * 
	 */
	public boolean isGetBehavior() 
		{
		if (!(processTerm instanceof ActionProcess))
			return false;
		// si preleva il comportamento null massimo di processTerm
		NullBehavior nullBehavior = new NullBehavior(
				this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
				this.processTerm == null ? null : this.processTerm.copy(), 
						processTerm == null ? null : processTerm.copy());
		// list deve essere una lista di taglia 1
		if (list.size() != 1)
			return false;
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		if (!(processTerm2 instanceof ActionProcess))
			return false;
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		// si preleva l'azione dall'ActionProcess
		Action a1 = actionProcess.getAzione();
		// a1 deve essere un'azione get
		GetAction getAction = new GetAction(a1,this.tinteractions);
		if (!getAction.isGet())
			return false;
		// riconosciamo un eventuale comportamento null
		ProcessTerm processTerm3 = actionProcess.getProcesso();
		NullBehavior nullBehavior2 = new NullBehavior(
				processTerm3 == null ? null : processTerm3.copy(),this.tinteractions);
		ProcessTerm processTerm4 = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(
				processTerm3 == null ? null : processTerm3.copy(), 
						processTerm4 == null ? null : processTerm4.copy());
		// restituiamo false se list2 ha taglia diversa da 1
		if (list2.size() != 1)
			return false;
		ProcessTerm processTerm5 = list2.get(0);
		// processTerm5 deve essere un BehavProcess
		if (!(processTerm5 instanceof BehavProcess))
			return false;
		this.behavProcess = (BehavProcess)processTerm5;
		return true;
		}

	// restituisce null se processTerm non è un GetBehavior
	public ProcessTerm getMaximalGetBehavior()
		{
		if (!isGetBehavior())
			return null;
		ProcessTerm ris = null;
		// si preleva il comportamento null massimo di processTerm
		NullBehavior nullBehavior = new NullBehavior(
				this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
				this.processTerm == null ? null : this.processTerm.copy(), 
						processTerm == null ? null : processTerm.copy());
		// list deve essere una lista di taglia 1
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		ris = new ActionProcess();
		// si preleva l'azione dall'ActionProcess
		Action a1 = actionProcess.getAzione();
		// a1 deve essere un'azione get
		((ActionProcess)ris).setAzione(a1);
		ris = MetodiVari.somma(processTerm, ris);
		Expression expression = this.processTerm.getCondition();
		ris.setCondition(expression.copy());
		// riconosciamo un eventuale comportamento null
		ProcessTerm processTerm3 = actionProcess.getProcesso();
		NullBehavior nullBehavior2 = new NullBehavior(
				processTerm3 == null ? null : processTerm3.copy(),this.tinteractions);
		ProcessTerm processTerm4 = nullBehavior2.getMaximalNullBehavior();
		ris = MetodiVari.somma(ris,processTerm4);
		return ris;
		}

	// restituisce null se processTerm non è un GetBehavior
	public ProcessTerm getGetBehavior()
		{
		if (!isGetBehavior())
			return null;
		ProcessTerm ris = null;
		// si preleva il comportamento null massimo di processTerm
		NullBehavior nullBehavior = new NullBehavior(
				this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
				this.processTerm == null ? null : this.processTerm.copy(), 
						processTerm == null ? null : processTerm.copy());
		// list deve essere una lista di taglia 1
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		ris = new ActionProcess();
		// si preleva l'azione dall'ActionProcess
		Action a1 = actionProcess.getAzione();
		// a1 deve essere un'azione get
		((ActionProcess)ris).setAzione(a1);
		Expression expression = this.processTerm.getCondition();
		ris.setCondition(expression.copy());
		return ris;
		}
	
	/**
	 * Restituisce il numero del processo get riconosciuto, a seconda della posizione del relativo
	 * parametro in dps e nella chiamata di comportamento di processTerm.
	 * Come precondizione processTerm deve essere un processo get.
	 * Restituisce -1 se processTerm non è un comportamento get.
	 */
	public int getIdentificationNumber() 
		{
		if (!isGetBehavior())
			return -1;
		BehavProcess bp1 = MetodiVari.returnTail(
				this.processTerm == null ? null : this.processTerm.copy());
		// per precondizione bp1 non è null
		// si prelevano i parametri attuali per la
		// chiamata del comportamento
		Expression[] es1 = bp1.getExprs();
		return getGetClassNumber(es1);
		}

	protected int getGetClassNumber(Expression[] es1) 
		{
		// si costruiscono array contenenti gli identificatori
		// espressione dei parametri + 1
		List<Expression> list = new ArrayList<Expression>();
		// si costruisce anche una lista per la commutatività della somma
		List<Expression> list2 = new ArrayList<Expression>();
		// si costruiscono gli array per i reali
		List<Expression> list31 = new ArrayList<Expression>();
		// si costruisce anche una lista per la commutatività della somma
		List<Expression> list4 = new ArrayList<Expression>();
		for (int l = 0; l < this.varInits.length; l++)
			{
			list.add(new Somma(
					new IdentExpr(this.varInits[l].getName()),
					new Integer(1)));
			list2.add(new Somma(
					new Integer(1),
					new IdentExpr(this.varInits[l].getName())));
			list31.add(new Somma(
					new IdentExpr(this.varInits[l].getName()),
					new Real(1)));
			list4.add(new Somma(
					new Real(1),
					new IdentExpr(this.varInits[l].getName())));
			}
		// si costruisce una lista che contiene le espressioni
		// di inizializzazione dei parametri
		List<Expression> list3 = new ArrayList<Expression>();
		for (int l = 0; l < this.varInits.length; l++)
			{
			list3.add(((VarInit)this.varInits[l]).getExpr());
			}
		int z = -1;
		// ci deve essere una delle espressioni che è uguale 
		// al nome dello z-esimo parametro dell'intestazione + 1
		for (int k = 0; k < es1.length; k++)
			{
			if (list.contains(es1[k])) z = list.indexOf(es1[k]);
			if (list2.contains(es1[k])) z = list2.indexOf(es1[k]);
			if (list31.contains(es1[k])) z = list31.indexOf(es1[k]);
			if (list4.contains(es1[k])) z = list4.indexOf(es1[k]);
			}
		return z;
		}

	/**
	 * Restituisce true se e solo se es1 corrisponde alle dichiarazioni di dps, tranne che per
	 * lo z-esimo elemento. 
	 * 
	 * @param dps
	 * @param es1
	 * @param z
	 */
	protected boolean parametersRelation(ParamDeclaration[] dps, Expression[] es1, int z) {
	for (int k = 0; k < es1.length; k++)
		{
		if (k != z)
			{
			// si costruisce un array che contiene gli identificatori
			// espressione dei parametri
			List<IdentExpr> list = new ArrayList<IdentExpr>();
			for (int l = 0; l < dps.length; l++)
				{
				list.add(new IdentExpr(dps[l].getName()));
				}
			// si verifica che es1[k] sia contenuto in list
			if (!list.contains(es1[k]))
				return false;
			}
		}
	return true;
	}
	
	/**
	 * Restituisce il nome dell'azione di get. Come precondizione processTerm è un comportamento get.
	 * 
	 * @return
	 */
	public String nameAction() 
		{
		// si preleva il comportamento null massimo di processTerm
		NullBehavior nullBehavior = new NullBehavior(
				this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
				this.processTerm == null ? null : this.processTerm.copy(), 
						processTerm == null ? null : processTerm.copy());
		// list deve essere una lista di taglia 1
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		// si preleva l'azione dall'ActionProcess
		Action a1 = actionProcess.getAzione();
		// si restituisce il nome dell'azione
		return a1.getType().getName();
		}

	public BehavProcess getBehavProcess() 
		{
		return behavProcess;
		}
	
	// si verificano le espressioni sulla chiamata comportamentale
	public boolean checkBehavProcess()
		{
		// si prelevano i parametri attuali per la
		// chiamata del comportamento
		Expression[] es1 = this.behavProcess.getExprs();
		int z = getGetClassNumber(es1);
		// si restituisce false se non è stato trovato un parametro attuale
		// corrispondente alla capacità della classe di clienti
		if (z == -1) return false;
		// ci devono essere es1.length - 1 espressioni uguali ad un parametro dell'intestazione
		// diverso dallo z-esimo parametro
		if (!parametersRelation(this.varInits, es1, z)) return false;
		return true;
		}
	}