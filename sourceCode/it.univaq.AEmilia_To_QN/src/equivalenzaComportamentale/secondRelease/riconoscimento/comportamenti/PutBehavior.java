package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import java.util.ArrayList;
import java.util.List;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionType;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.Expression;
import specificheAEmilia.IdentExpr;
import specificheAEmilia.Integer;
import specificheAEmilia.Maggiore;
import specificheAEmilia.Minore;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.Real;
import specificheAEmilia.Sottrazione;
import specificheAEmilia.VarInit;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.PutAction;

public class PutBehavior 
	{

	private ParamDeclaration[] paramDeclarations;
	private ProcessTerm processTerm;
	private AETinteractions tinteractions;
	private BehavProcess behavProcess;
	
	public PutBehavior( 
			ParamDeclaration[] dps, 
			ProcessTerm processTerm, 
			AETinteractions interactions) 
		{
		super();
		this.paramDeclarations = dps;
		this.processTerm = processTerm;
		this.tinteractions = interactions;
		}

	public boolean isPutBehavior()
		{
		// processTerm deve essere un ActionProcess
		if (!(processTerm instanceof ActionProcess))
			return false;
		// si preleva il comportamento null massimo
		NullBehavior nullBehavior = new NullBehavior(
				this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
				this.processTerm == null ? null : this.processTerm.copy(), 
						processTerm == null ? null : processTerm.copy());
		// list deve avere taglia uguale a 1
		if (list.size() != 1)
			return false;
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		if (!(processTerm2 instanceof ActionProcess))
			return false;
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		// si restituisce l'azione dell'ActionProcess
		Action a1 = actionProcess.getAzione();
		// a1 deve essere un'azione put
		PutAction putAction = new PutAction(a1,this.tinteractions);
		if (!putAction.isPut())
			return false;
		// si preleva la coda dallo j-esimo ActionProcess
		ProcessTerm processTerm3 = actionProcess.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(
				processTerm3 == null ? null : processTerm3.copy(),this.tinteractions);
		ProcessTerm processTerm4 = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(
				processTerm3 == null ? null : processTerm3.copy(), 
						processTerm4 == null ? null : processTerm4.copy());
		// list deve avere taglia uguale a 1
		if (list2.size() != 1)
			return false;
		ProcessTerm processTerm5 = list2.get(0);
		// processTerm5 deve essere un BehavProcess
		if (!(processTerm5 instanceof BehavProcess))
			return false;
		BehavProcess behavProcess = (BehavProcess)processTerm5;
		this.behavProcess = behavProcess;
		return true;
		}
	
	// restituisce l'azione
	// put
	// restituisce null se processTerm non è 
	// un processo put
	public String getPutName()
		{
		if (!isPutBehavior())
			return null;
		// processTerm deve essere un ActionProcess
		// si preleva il comportamento null massimo
		NullBehavior nullBehavior = new NullBehavior(
				this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
				this.processTerm == null ? null : this.processTerm.copy(), 
						processTerm == null ? null : processTerm.copy());
		// list deve avere taglia uguale a 1
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		// si restituisce l'azione dell'ActionProcess
		Action a1 = actionProcess.getAzione();
		// a1 deve essere un'azione put
		ActionType actionType = a1.getType();
		String string = actionType.getName();
		return string;
		}
	
	/**
	 * Verifica se la condizione di esecuzione del termine di processo è una condizione
	 * indicante che il numero di clienti attuale di una certa classe è maggiore di zero.
	 *  
	 * @return
	 */
	public boolean checkEmpty() 
		{
		Expression c1 = processTerm.getCondition();
		// l'ActionProcess può essere eseguito se e
		// solo se il numero di clienti di una certa
		// classe è maggiore di zero
		boolean b = false;
		for (int k = 0; k < this.paramDeclarations.length; k++)
			{
			if (c1.equals(new Maggiore(
				new IdentExpr(this.paramDeclarations[k].getName()),
				new Integer(0)))
				||
				c1.equals(new Minore(
					new Integer(0),
					new IdentExpr(this.paramDeclarations[k].getName())))
					||
					c1.equals(new Maggiore(
							new IdentExpr(this.paramDeclarations[k].getName()),
							new Real(0)))
							||
							c1.equals(new Minore(
									new Real(0),
									new IdentExpr(this.paramDeclarations[k].getName())))			
					)
				b = true;
			}
		return b;
		}
	
	/**
	 * Restituisce il numero della classe di clienti relativa al processo put.
	 * Se non c' è una corrispondenza tra dichiarazioni di parametri ed espressioni si restituisce - 1.
	 * 
	 * @param es1
	 * @return
	 */
	protected int getPutClassNumber(Expression[] es1) 
		{
		// si costruisce una lista che contiene gli identificatori
		// espressione dei parametri - 1
		List<Expression> list1 = new ArrayList<Expression>();
		// si costruisce un'array per i reali
		List<Expression> list21 = new ArrayList<Expression>();
		for (int l = 0; l < this.paramDeclarations.length; l++)
			{
			list1.add(new Sottrazione(
					new IdentExpr(this.paramDeclarations[l].getName()),
					new Integer(1)));
			list21.add(new Sottrazione(
					new IdentExpr(this.paramDeclarations[l].getName()),
					new Real(1)));
			}
		// si costruisce una lista che contiene le espressioni
		// di inizializzazione dei parametri
		List<Expression> list2 = new ArrayList<Expression>();
		for (int l = 0; l < this.paramDeclarations.length; l++)
			{
			list2.add(((VarInit)this.paramDeclarations[l]).getExpr());
			}
		int z = -1;
		// ci deve essere una delle espressioni che è uguale 
		// al nome dello z-esimo parametro dell'intestazione - 1
		for (int k = 0; k < es1.length; k++)
			{
			// si verifica che es1[k] sia contenuto in list 
			if (list1.contains(es1[k]))
				z = list1.indexOf(es1[k]);
			if (list21.contains(es1[k]))
				z = list21.indexOf(es1[k]);
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
	protected boolean parametersRelation(ParamDeclaration[] dps, Expression[] es1, int z) 
		{
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
	 * Restituisce il numero del processo put riconosciuto, 
	 * a seconda della posizione del relativo
	 * parametro in dps.
	 * Come precondizione processTerm deve essere un processo put.
	 * 
	 */
	public int putIdentificationNumber() 
		{
		if (!isPutBehavior())
			return -1;
		BehavProcess bp1 = MetodiVari.returnTail(
				this.processTerm == null ? null : this.processTerm.copy());
		// per precondizione bp1 non è null
		// si prelevano i parametri attuali per la
		// chiamata del comportamento
		Expression[] es1 = bp1.getExprs();
		return getPutClassNumber(es1);
		}

	/**
	 * Restituisce il nome dell'azione di put. Come precondizione processTerm è un comportamento put.
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
	
	public ProcessTerm getMaximalPutBehavior()
		{
		if (!isPutBehavior())
			return null;
		ProcessTerm ris = null;
		// processTerm deve essere un ActionProcess
		// si preleva il comportamento null massimo
		NullBehavior nullBehavior = new NullBehavior(
				this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
				this.processTerm == null ? null : this.processTerm.copy(), 
						processTerm == null ? null : processTerm.copy());
		// list deve avere taglia uguale a 1
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		// si restituisce l'azione dell'ActionProcess
		Action a1 = actionProcess.getAzione();
		// a1 deve essere un'azione put
		// si preleva la coda dallo j-esimo ActionProcess
		ProcessTerm processTerm3 = actionProcess.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(
				processTerm3 == null ? null : processTerm3.copy(),this.tinteractions);
		ProcessTerm processTerm4 = nullBehavior2.getMaximalNullBehavior();
		// imposto il processo risultato uguale a processTerm + a1 + processTerm4
		ris = MetodiVari.somma(a1, processTerm4);
		ris = MetodiVari.somma(processTerm, ris);
		// imposto la condizione del risultato
		Expression expression = this.processTerm.getCondition();
		ris.setCondition(expression.copy());
		return ris;
		}

	public ProcessTerm getPutBehavior()
		{
		if (!isPutBehavior())
			return null;
		ProcessTerm ris = null;
		// processTerm deve essere un ActionProcess
		// si preleva il comportamento null massimo
		NullBehavior nullBehavior = new NullBehavior(
			this.processTerm == null ? null : this.processTerm.copy(),this.tinteractions);
		ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(
			this.processTerm == null ? null : this.processTerm.copy(), 
					processTerm == null ? null : processTerm.copy());
		// list deve avere taglia uguale a 1
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		// si restituisce l'azione dell'ActionProcess
		Action a1 = actionProcess.getAzione();
		// a1 deve essere un'azione put
		// imposto il processo risultato uguale ad un ActionProcess con azione a1
		ris = new ActionProcess();
		((ActionProcess)ris).setAzione(a1);
		// imposto la condizione del risultato
		Expression expression = this.processTerm.getCondition();
		ris.setCondition(expression.copy());
		return ris;
		}

	// si verificano le espressioni sulla chiamata comportamentale
	public boolean checkBehavProcess()
		{
		Expression[] es1 = this.behavProcess.getExprs();
		int z = getPutClassNumber(es1);
		// se non è stato trovato il parametro attuale corrispondente alla capacità per la classe
		// di clienti si restituisce false
		if (z == -1) return false;
		// ci devono essere es1.length - 1 espressioni uguali ad uno dei parametri dell'intestazione
		// diverso dallo z-esimo parametro
		if (!parametersRelation(this.paramDeclarations, es1, z)) 
			return false;
		return true;
		}
	}
