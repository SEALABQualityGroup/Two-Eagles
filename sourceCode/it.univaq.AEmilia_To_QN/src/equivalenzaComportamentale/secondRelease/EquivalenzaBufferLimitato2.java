package equivalenzaComportamentale.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import specificheAEmilia.AETbehavior;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.IntegerRangeType;
import specificheAEmilia.Header;
import specificheAEmilia.Maggiore;
import specificheAEmilia.Minore;
import specificheAEmilia.NormalType;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.VarInit;
import equivalenzaComportamentale.AETinteractionsParts;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferLimitato;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ConditionalGetBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.NullBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PutBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaBufferLimitato2
	extends EquivalenzaBuffer2
	implements IEquivalenzaBufferLimitato
	{
	
	private Expression[] limitiClassi = null;
	List<ConditionalGetBehavior> conditionalGetBehaviors;
		
	public EquivalenzaBufferLimitato2() 
		{
		super();
		}

	public EquivalenzaBufferLimitato2(ElemType elemType) 
		{
		super();
		this.elemType = elemType;
		}

	public boolean isBufferLimitato() 
		{
		// 1) Il comportamento di un buffer a capacità finita è definito nel seguente modo:
		// Nell'equazione devono essere dichiarati dei parametri p1, p2, … , pn 
		// che rappresentano il numero di clienti di ogni classe presenti nel buffer;
		// 2) I parametri presenti nell’intestazione del comportamento devono essere tutti dichiarati 
		// come intervalli di interi;
		// 3) Devono essere presenti 2n processi alternativi: n processi get e n processi put.
		// 4) Le uniche azioni di input corrispondono alle azioni get;
		// 5) Le uniche azioni di output corrispondono alle azioni put;
		// 6) le azioni get devono avere nomi distinti;
		// 7) le azioni put devono avere nomi distinti.
		// si rende tail recursion il comportamento
		AETbehavior tbehavior = this.getEt().getBehavior();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		if (!tailRecursion.isTailRecursion())
			return false;
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		
		// alloco memoria per i limiti di capacità di ogni classe
		List<Expression> listLimitiClassi = new ArrayList<Expression>();
		// alloco memoria per le capacità iniziali
		List<Expression> listCapacitaIniziali = new ArrayList<Expression>();
		// alloco memoria per il numero delle classi clienti
		int numClassi = 0;
		
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = tbehavior2.getBehaviors()[0];
		Header header = behavEquation.getBehavHeader();
		VarInit[] varInits = this.capDecl(header);
		// Il comportamento di un buffer a capacità finita è definito nel seguente modo:
		// Nell'equazione devono essere dichiarati dei parametri p1, p2, … , pn 
		// che rappresentano il numero di clienti di ogni classe presenti nel buffer;
		for (VarInit varInit : varInits)
			{
			Expression expression = varInit.getExpr();
			listCapacitaIniziali.add(expression);
			// incremento il numero delle classi
			numClassi++;
			}
		// 2) Devono essere tutti dichiarati 
		// come intervalli di interi;
		for (VarInit varInit : varInits)
			{
			NormalType normalType = varInit.getType();
			if (!(normalType instanceof IntegerRangeType))
				return false;
			IntegerRangeType integerRangeType = (IntegerRangeType)normalType;
			Expression espressione2 = integerRangeType.getEndingInt();
			// memorizzo il limite
			listLimitiClassi.add(espressione2);
			}
		
		// devono essere presenti 2n processi alternativi
		int i = varInits.length;
		AETinteractions tinteractions = this.getEt().getInteractions();
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior = new NullBehavior(processTerm,tinteractions);
		ProcessTerm processTerm21 = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(processTerm, processTerm21);
		// list deve avere taglia 1
		if (list1.size() != 1)
			return false;
		ProcessTerm processTerm3 = list1.get(0);
		// processTerm3 deve essere un processo choice
		if (!(processTerm3 instanceof ChoiceProcess))
			return false;
		ChoiceProcess choiceProcess = (ChoiceProcess)processTerm3;
		ProcessTerm[] processTerms = choiceProcess.getProcesses();
		int j = processTerms.length;
		if ((2 * i) != j)
			return false;
		// k è il contatore dei processi get incondizionati
		int k = 0;
		// l è il contatore dei processi put
		int l = 0;
		// n processi get condizionati e n processi put;
		this.conditionalGetBehaviors = new ArrayList<ConditionalGetBehavior>();
		this.putBehaviors = new ArrayList<PutBehavior>();
		for (ProcessTerm processTerm2 : processTerms)
			{
			ConditionalGetBehavior conditionalGetBehavior =
				new ConditionalGetBehavior(varInits, processTerm2, tinteractions);
			if (conditionalGetBehavior.isConditionalGetBehavior())
				{
				this.conditionalGetBehaviors.add(conditionalGetBehavior);
				k++;
				}
			PutBehavior putBehavior = new PutBehavior(varInits, processTerm2, tinteractions);
			if (putBehavior.isPutBehavior())
				{
				this.putBehaviors.add(putBehavior);
				l++;
				}
			}
		if ((2 * k) != j) return false;
		if ((2 * l) != j) return false;
		// le uniche azioni di input corrispondono alle azioni get;
		String[] strings = this.getGetsP(tinteractions);
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		List<String> list2 = tinteractionsParts.getInputInteractions();
		if (!list.containsAll(list2))
			return false;
		if (!list2.containsAll(list))
			return false;
		// le uniche azioni di output corrispondono alle azioni put;
		String[] strings2 = this.getPutsP(tinteractions);
		List<String> list3 = new CopyOnWriteArrayList<String>(strings2);
		List<String> list4 = tinteractionsParts.getOutputInteractions();
		if (!list3.containsAll(list4))
			return false;
		if (!list4.containsAll(list3))
			return false;
		// le azioni get devono avere nomi distinti;
		if (!MetodiVari.distinct(list))
			return false;
		// le azioni put devono avere nomi distinti.
		if (!MetodiVari.distinct(list3))
			return false;
		
		// imposto i campi dell'oggetto
		// imposto i limiti per le classi
		this.limitiClassi = new Expression[listLimitiClassi.size()];
		listLimitiClassi.toArray(this.limitiClassi);
		// imposto le capacità iniziali
		this.capacitaIniziali = new Expression[listCapacitaIniziali.size()];
		listCapacitaIniziali.toArray(this.capacitaIniziali);
		// imposto i nomi delle azioni get
		this.gets = strings;
		// imposto il numero delle classi cliente
		this.numeroClassiCliente = numClassi;
		// imposto i nomi delle azioni put
		this.puts = strings2;
		
		return true;
		}
	
	// Restituisce
	// un comportamento con le equazioni standard
	public AETbehavior getNormalizedBehavior()
		{
		if (!isBufferLimitato())
			return null;
		AETbehavior tbehavior = this.getEt().getBehavior();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = tbehavior2.getBehaviors()[0];		
		Header header = behavEquation.getBehavHeader();
		VarInit[] varInits = this.capDecl(header);		
		AETinteractions tinteractions = this.getEt().getInteractions();
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior = new NullBehavior(processTerm,tinteractions);
		ProcessTerm processTerm21 = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(processTerm, processTerm21);
		// list deve avere taglia 1
		ProcessTerm processTerm3 = list1.get(0);
		ChoiceProcess choiceProcess = (ChoiceProcess)processTerm3;
		ProcessTerm[] processTerms = choiceProcess.getProcesses();
		ChoiceProcess ris = new ChoiceProcess();
		ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
		ris.setProcesses(processTerms2);
		// processTerms è composto da processi get e put
		for (int i = 0; i < processTerms.length; i++)
			{
			ProcessTerm processTerm2 = processTerms[i];
			BehavProcess behavProcess = MetodiVari.returnTail(processTerm2);
			ConditionalGetBehavior conditionalGetBehavior =
				new ConditionalGetBehavior(varInits,processTerm2,tinteractions);
			PutBehavior putBehavior = new PutBehavior(varInits,processTerm2,tinteractions);
			if (conditionalGetBehavior.isConditionalGetBehavior())
				processTerms2[i] = conditionalGetBehavior.getConditionalGetBehavior();
			else if (putBehavior.isPutBehavior())
				processTerms2[i] = putBehavior.getPutBehavior();
			// inserisco la coda all'i-esimo termine di processo risultato
			processTerms2[i] = MetodiVari.somma(processTerms2[i], behavProcess);
			}
		Header intestazione2 = header.copy();
		BehavEquation behavEquation2 = new BehavEquation(intestazione2,ris);
		BehavEquation[] behavEquations = new BehavEquation[1];
		behavEquations[0] = behavEquation2;
		AETbehavior tbehavior3 = new AETbehavior(behavEquations);
		return tbehavior3;
		}

	@Override
	public Expression getLimiteClasse(String string) 
		{
		if (!this.isEquivalente())
			return null;
		return this.getLimiteClasseP(string);
		}

	@Override
	public Expression[] getLimitiClassi() 
		{
		return this.limitiClassi;
		}

	@Override
	public Expression[] getCapacitaIniziali() 
		{
		return this.capacitaIniziali;
		}

	@Override
	public String[] getGets() 
		{
		return this.gets;
		}

	@Override
	public int getNumeroClassiCliente() 
		{
		return this.numeroClassiCliente;
		}

	@Override
	public String getPutFromGet(String string) 
		{
		if (!this.isEquivalente())
			return null;
		AETinteractions tinteractions = 
			this.elemType.getInteractions();
		return this.getPutFromGetP(string, tinteractions);
		}

	@Override
	public String[] getPuts() 
		{
		return this.puts;
		}

	@Override
	public boolean isEquivalente() 
		{
		return isBufferLimitato();
		}

	private Expression getLimiteClasseP(String string) 
		{
		ProcessTerm processTerm = 
			this.elemType.getBehavior().getBehaviors()[0].getTermineProcesso();
		AETinteractions tinteractions = this.elemType.getInteractions();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior = new NullBehavior(processTerm,tinteractions);
		ProcessTerm processTerm21 = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(processTerm, processTerm21);
		// list deve avere taglia 1
		ProcessTerm processTerm3 = list1.get(0);
		// come precondizione ho che processTerm3 è uno choice
		ChoiceProcess choiceProcess = (ChoiceProcess)processTerm3;
		// si trova il comportamento che ha string come nome di azione
		ProcessTerm processTerm2 = findGet(choiceProcess,string);
		Expression expression = processTerm2.getCondition();
		Expression espressione2 = null;
		// come precondizioe ho che espressione è un maggiore o un minore
		if (expression instanceof Maggiore)
			{
			Maggiore maggiore = (Maggiore)expression;
			// prelevo il primo operando
			espressione2 = maggiore.getExpr1();
			}
		if (expression instanceof Minore)
			{
			Minore minore = (Minore)expression;
			// prelevo il secondo operando
			espressione2 = minore.getExpr2();
			}
		return espressione2;
		}
	
	// restituisce null se choiceProcess non contiene un processo
	// get con nome dell'azione uguale a string
	private ProcessTerm findGet(ChoiceProcess choiceProcess, String string) 
		{
		ProcessTerm[] processTerms = choiceProcess.getProcesses();
		for (ProcessTerm processTerm2 : processTerms)
			{
			// come precondizione ho che processTerm2 è 
			// un ActionProcess
			ActionProcess actionProcess = (ActionProcess)processTerm2;
			Action action = actionProcess.getAzione();
			ActionType actionType = action.getType();
			String string2 = actionType.getName();
			if (string.equals(string2))
				return actionProcess;
			}
		return null;
		}

	public List<ConditionalGetBehavior> getConditionalGetBehaviors() 
		{
		return conditionalGetBehaviors;
		}
	}
