package equivalenzaComportamentale.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import specificheAEmilia.AETbehavior;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.Header;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.VarInit;
import equivalenzaComportamentale.AETinteractionsParts;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferIllimitato;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.NullBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PutBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.UnconditionalGetBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaBufferIllimitato2 
	extends EquivalenzaBuffer2 
	implements IEquivalenzaBufferIllimitato 
	{
	
	List<UnconditionalGetBehavior> unconditionalGetBehaviors;
	
	public EquivalenzaBufferIllimitato2() 
		{
		super();
		}

	public EquivalenzaBufferIllimitato2(ElemType elemType) 
		{
		super();
		this.elemType = elemType;
		}
	
	public boolean isBufferIllimitato() 
		{
		// Il comportamento di un buffer a capacità infinita è definito nel seguente modo:
		// 1) Nell'equazione devono essere dichiarati dei parametri p1, p2, … , pn 
		// che rappresentano il numero di clienti di ogni classe presenti nel buffer;
		// 3) Devono essere presenti 2n processi alternativi: n processi get 
		// incondizionati e n processi put;
		// 4) le uniche azioni di input corrispondono alle azioni get;
		// 5) le uniche azioni di output corrispondono alle azioni put;
		// 6) le azioni get devono avere nomi distinti;
		// 7) le azioni put devono avere nomi distinti.
		// si rende tail recursion il comportamento
		AETbehavior tbehavior = this.getEt().getBehavior();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		if (!tailRecursion.isTailRecursion())
			return false;
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = tbehavior2.getBehaviors()[0];
		Header header = behavEquation.getBehavHeader();
		VarInit[] varInits = this.capDecl(header);
		// per precondizioni le espressioni di inizializzazione di varInits sono
		// già state valutate e devono corrispondere a interi non negativi
		// alloco memoria per le capacita iniziali
		List<Expression> listCapacitaIniziali = new ArrayList<Expression>();
		// conto il numero di classi servite
		int numeroClassi = 0;
		for (VarInit varInit : varInits)
			{
			Expression espression = varInit.getExpr();
			listCapacitaIniziali.add(espression);
			numeroClassi++;
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
		// k è il contatore dei processi get
		int k = 0;
		// l è il contatore dei processi put
		int l = 0;
		// n processi get 
		// incondizionati e n processi put;
		this.putBehaviors = new ArrayList<PutBehavior>();
		this.unconditionalGetBehaviors = new ArrayList<UnconditionalGetBehavior>();
		for (ProcessTerm processTerm2 : processTerms)
			{
			UnconditionalGetBehavior unconditionalGetBehavior = 
				new UnconditionalGetBehavior(varInits, processTerm2, tinteractions);
			if (unconditionalGetBehavior.isUnconditionalGetBehavior())
				{
				this.unconditionalGetBehaviors.add(unconditionalGetBehavior);
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
		// imposto le capacità iniziali
		this.capacitaIniziali = new Expression[listCapacitaIniziali.size()];
		listCapacitaIniziali.toArray(this.capacitaIniziali);
		// imposto i nomi delle azioni get
		this.gets = strings;
		// imposto il numero di classi clienti servite
		this.numeroClassiCliente = numeroClassi;
		// imposto l'array delle azioni put
		this.puts = strings2;
		
		return true;
		}
	
	// Restituisce
	// un comportamento con le equazioni standard,
	// in cui ogni processo get e put non hanno
	// comportamenti null
	public AETbehavior getNormalizedBehavior()
		{
		if (!isBufferIllimitato())
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
			UnconditionalGetBehavior unconditionalGetBehavior =
				new UnconditionalGetBehavior(varInits,processTerm2,tinteractions);
			PutBehavior putBehavior = new PutBehavior(varInits,processTerm2,tinteractions);
			if (unconditionalGetBehavior.isUnconditionalGetBehavior())
				processTerms2[i] = unconditionalGetBehavior.getUnconditionalGetBehavior();
			else if (putBehavior.isPutBehavior())
				processTerms2[i] = putBehavior.getPutBehavior();
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
		// string deve essere non null.
		return getPutFromGetP(string, tinteractions);
		}

	@Override
	public String[] getPuts() 
		{
		return this.puts;
		}

	@Override
	public boolean isEquivalente() 
		{
		return this.isBufferIllimitato();
		}

	public List<UnconditionalGetBehavior> getUnconditionalGetBehaviors() 
		{
		return unconditionalGetBehaviors;
		}
	}
