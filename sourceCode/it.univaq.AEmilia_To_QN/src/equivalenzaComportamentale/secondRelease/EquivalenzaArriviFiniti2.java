package equivalenzaComportamentale.secondRelease;

import java.util.ArrayList;
import java.util.List;

import specificheAEmilia.AETbehavior;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.Header;
import specificheAEmilia.ProcessTerm;
import equivalenzaComportamentale.AETinteractionsParts;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviFiniti;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ExitBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PhaseBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ReturnBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaArriviFiniti2 
	extends EquivalenzaArrivi2 
	implements IEquivalenzaArriviFiniti 
	{
	
	private int numReturns = 0;
	private String[] returns = null;
	
	
	public EquivalenzaArriviFiniti2() 
		{
		super();
		}

	public EquivalenzaArriviFiniti2(ElemType et1) 
		{
		super();
		this.setEt(et1);
		}

	public boolean isProcessoArriviFiniti() 
		{
		// Il comportamento di un processo di arrivi finiti 
		// è definito dalla seguente sequenza di 
		// comportamenti:
		// 1.una distribuzione di tipo fase;
		ElemType elemType = this.getEt();
		// trasformiamo il comportamento
		// elemType in uno tail recursion
		AETbehavior tbehavior = elemType.getBehavior();
		AETinteractions tinteractions = elemType.getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		if (!tailRecursion.isTailRecursion())
			return false;
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// verifichiamo la presenza della distribuzione di tipo fase
		PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm,tinteractions);
		ProcessTerm processTerm2 = phaseBehavior.getPhaseBehavior();
		// se processTerm2 è null, allora non abbiamo trovato un comportamento di tipo fase
		if (processTerm2 == null)
			return false;
		// preleviamo i tassi delle azioni di fase
		PhaseBehavior phaseBehavior2 = new PhaseBehavior(processTerm2,tinteractions);
		List<Expression> listFASI = phaseBehavior2.getTassi();
		// 2.il routing di jobs;
		// preleviamo la differenza tra processTerm e processTerm2
		List<ProcessTerm> list = MetodiVari.differenza(processTerm, phaseBehavior.getMaximalPhaseBehavior());
		// se list ha cardinalità maggiore di 1 vuol dire che il comportamento è di tipo 
		// Hyperesponenziale. In questo caso bisogna verificare che i comportamenti di list sono
		// uguali semanticamente
		if (list.size() > 1)
			{
			ExitBehavior exitBehavior = new ExitBehavior(list.get(0),tinteractions);
			ProcessTerm processTermR1 = exitBehavior.getJobsRoutingBehavior();
			for (int i = 1; i < list.size(); i++)
				{
				ExitBehavior exitBehavior2 = new ExitBehavior(list.get(i),tinteractions);
				ProcessTerm processTermR2 = exitBehavior2.getJobsRoutingBehavior();
				if (!processTermR1.equals(processTermR2))
					return false;
				}
			}
		// per il primo elemento di list dobbiamo cercare un comportamento di routing di jobs
		// allochiamo memoria per i nomi delle azioni di consegna
		List<String> listDel = new ArrayList<String>();
		// allochiamo memoria per i nomi delle azioni di ritorno
		List<String> listRit = new ArrayList<String>();
		// allochiamo memoria per i nomi delle azioni di choose
		List<String> listCho = new ArrayList<String>();
		// allochiamo memoria per le azioni di consegna
		List<Action> listAD = new ArrayList<Action>();
		// allochiamo memoria per i pesi delle azioni di consegna
		List<Expression> listEPD = new ArrayList<Expression>();
		// allochiamo memoria per le priorità delle azioni di consegna
		List<Expression> listEPrD = new ArrayList<Expression>();
		// allochiamo memoria per le probabilità di routing
		List<Expression> listRP = new ArrayList<Expression>();
	
		ProcessTerm processTerm3 = list.get(0);
		ExitBehavior exitBehavior = new ExitBehavior(processTerm3,tinteractions);
		ProcessTerm processTerm4 = exitBehavior.getMaximalJobsRoutingBehavior();
		if (processTerm4 == null)
			return false;
		// memorizziamo in listDel i nomi delle azioni di consegna
		ExitBehavior jobsRoutingBehavior2 = new ExitBehavior(processTerm4,tinteractions);
		// ogni comportamento di routing ha almeno un'azione di consegna
		listDel.addAll(jobsRoutingBehavior2.getDeliverActionNames());
		// salviamo i nomi delle azioni di choose
		listCho.addAll(jobsRoutingBehavior2.getChooseActionNames());
		// salviamo le azioni di consegna
		listAD.addAll(jobsRoutingBehavior2.getDeliverActions());
		// salviamo i pesi delle azioni di consegna
		listEPD.addAll(jobsRoutingBehavior2.getPesiConsegna());
		// salviamo le priorità delle azioni di consegna
		listEPrD.addAll(jobsRoutingBehavior2.getPrioritaConsegna());
		// salviamo le probabilità di routing 
		listRP.addAll(jobsRoutingBehavior2.getProbsRouting());
		
		// 3.il ritorno di jobs.
		// preleviamo la differenza tra processTerm3 e processTerm4
		List<ProcessTerm> list21 = MetodiVari.differenza(processTerm3, processTerm4);
		// per ogni elemento di list dobbiamo cercare un comportamento di ritorno di jobs
		for (ProcessTerm processTerm5 : list21)
			{
			ReturnBehavior returnBehavior = new ReturnBehavior(processTerm5,tinteractions);
			if (!returnBehavior.isReturnBehavior())
				return false;
			}
		// se list2 ha taglia maggiore di uno vuol dire che c'è più di una destinazione.
		// In questo caso, bisogna controllare che tutti i termini di processo di list21
		// siano uguali semanticamente
		if (list21.size() > 1)
			{
			ReturnBehavior returnBehavior = new ReturnBehavior(list21.get(0),tinteractions);
			ProcessTerm processTermR3 = returnBehavior.getReturnBehavior();
			for (int i = 1; i < list21.size(); i++)
				{
				ReturnBehavior returnBehavior2 = new ReturnBehavior(list21.get(0),tinteractions);
				ProcessTerm processTermR4 = returnBehavior2.getReturnBehavior();
				if (!processTermR3.equals(processTermR4))
					return false;
				}
			}
		// ogni comportamento di ritorno deve avere almeno un'azione di ritorno
		// e dei comportamenti di ritorno ce ne devono essere solo uno
		ReturnBehavior returnBehavior = new ReturnBehavior(list21.get(0),tinteractions);
		List<String> listR = returnBehavior.getReturnActionNames();
		listRit.addAll(listR);
		// le uniche azioni di output devono essere le azioni di consegna
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		List<String> list2 = tinteractionsParts.getOutputInteractions();
		if (!list2.containsAll(listDel))
			return false;
		if (!listDel.containsAll(list2))
			return false;
		// le uniche azioni di input devono essere le azioni di ritorno
		List<String> list3 = tinteractionsParts.getInputInteractions();
		if (!list3.containsAll(listRit))
			return false;
		if (!listRit.containsAll(list3))
			return false;
		// impostiamo il numero delle azioni di ritorno
		this.numReturns = listRit.size();
		// impostiamo i nomi delle azioni di ritorno
		this.returns = new String[listRit.size()];
		listRit.toArray(this.returns);
		// impostiamo i nomi delle azioni di choose
		this.chooses = new String[listCho.size()];
		listCho.toArray(this.chooses);
		// impostiamo le azioni di consegna
		this.deliverActions = new Action[listAD.size()];
		listAD.toArray(this.deliverActions);
		// impostiamo i nomi delle azioni di consegna
		this.delivers = new String[listDel.size()];
		listDel.toArray(this.delivers);
		// impostaimo i pesi delle azioni di consegna
		this.pesiConsegna = new Expression[listEPD.size()];
		listEPD.toArray(this.pesiConsegna);
		// impostiamo le priorità delle azioni di consegna
		this.prioConsegna = new Expression[listEPrD.size()];
		listEPrD.toArray(this.prioConsegna);
		// impostiamo le probabilità di routing
		this.probRouting = new Expression[listRP.size()];
		listRP.toArray(this.probRouting);
		// imposto i tassi del comportamento di fase
		this.tassiProcesso = new Expression[listFASI.size()];
		listFASI.toArray(this.tassiProcesso);
		// imposto il comportamento per il routing
		ParamDeclaration[] declPars = new ParamDeclaration[2];
		Header header = new Header("Fase",declPars);
		BehavEquation behavEquation2 = new BehavEquation(header,processTerm2);
		this.phaseBehavior = behavEquation2;
		return true;
		}
	
	// restituisce un comportamento con le equazioni standard.
	// restituisce null se et non è un processo di arrivi finiti
	public AETbehavior getNormalizedBehavior()
		{
		if (!isProcessoArriviFiniti())
			return null;
		AETbehavior tbehaviorR = new AETbehavior();
		// il comportamento risultato ha tre equazioni
		BehavEquation[] behavEquationsR = new BehavEquation[3];
		tbehaviorR.setBehaviors(behavEquationsR);
		// preleviamo il comportamento di fase
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// verifichiamo la presenza della distribuzione di tipo fase
		PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm,tinteractions);
		ProcessTerm processTerm2 = phaseBehavior.getPhaseBehavior();
		// costruiamo un'equazione per il comportamento di fase
		ParamDeclaration[] declPars = new ParamDeclaration[2];
		declPars[0] = null;
		declPars[1] = null;
		Header header = new Header("Fase",declPars);
		BehavEquation behavEquation2 = new BehavEquation(header,processTerm2);
		behavEquationsR[0] = behavEquation2;
		// 2.il routing di jobs;
		// preleviamo la differenza tra processTerm e processTerm2
		List<ProcessTerm> list = MetodiVari.differenza(processTerm, phaseBehavior.getMaximalPhaseBehavior());
		// per ogni elemento di list dobbiamo cercare un comportamento di routing di jobs
		ProcessTerm processTerm3 = list.get(0);
		ExitBehavior exitBehavior = new ExitBehavior(processTerm3,tinteractions);
		// crea un'equazione per il routing
		ParamDeclaration[] declPars2 = new ParamDeclaration[2];
		declPars2[0] = null;
		declPars2[1] = null;
		Header intestazione2 = new Header("Routing",declPars2);
		ProcessTerm processTerm4 = exitBehavior.getJobsRoutingBehavior();
		BehavEquation behavEquation3 = new BehavEquation(intestazione2,
				processTerm4);
		behavEquationsR[1] = behavEquation3;
		// 3.il ritorno di jobs.
		List<ProcessTerm> list2 = MetodiVari.differenza(processTerm3, 
				exitBehavior.getMaximalJobsRoutingBehavior());
		// per ogni elemento di list2 dobbiamo cercare un comportamento di ritorno di jobs
		// si crea l'equazione per il ritorno
		ParamDeclaration[] declPars3 = new ParamDeclaration[2];
		declPars3[0] = null;
		declPars3[1] = null;
		Header intestazione3 = new Header("Return",declPars3);
		ReturnBehavior returnBehavior = new ReturnBehavior(list2.get(0),tinteractions);
		ProcessTerm processTerm5 = returnBehavior.getReturnBehavior();
		BehavEquation behavEquation4 = new BehavEquation(intestazione3,processTerm5);
		behavEquationsR[2] = behavEquation4;
		// si impostano correttamente le chiamate alle equazioni
		List<BehavProcess> list3 = computeLeaf(processTerm2);
		List<BehavProcess> list4 = computeLeaf(processTerm4);
		List<BehavProcess> list5 = computeLeaf(processTerm5);
		for (BehavProcess behavProcess : list3)
			{
			behavProcess.setName("Routing");
			behavProcess.setExprs(new Expression[0]);
			}
		for (BehavProcess behavProcess4 : list4)
			{
			behavProcess4.setName("Return");
			behavProcess4.setExprs(new Expression[0]);
			}
		for (BehavProcess behavProcess4 : list5)
			{
			behavProcess4.setName("Fase");
			behavProcess4.setExprs(new Expression[0]);
			}
		return tbehaviorR;
		}
	
	@Override
	public int getNumReturns() 
		{
		return this.numReturns;
		}

	@Override
	public String[] getReturns() 
		{
		return this.returns;
		}

	@Override
	public String[] getChooses() 
		{
		return this.chooses;
		}

	@Override
	public Action[] getDeliverActions() 
		{
		return this.deliverActions;
		}

	@Override
	public String[] getDelivers() 
		{
		return this.delivers;
		}

	@Override
	public Expression[] getPesiConsegna() 
		{
		return this.pesiConsegna;
		}

	@Override
	public Expression[] getPrioConsegna() 
		{
		return this.prioConsegna;
		}

	@Override
	public Expression[] getProbRouting() 
		{
		return this.probRouting;
		}

	@Override
	public Expression[] getTassiProcesso() 
		{
		return this.tassiProcesso;
		}

	@Override
	public boolean isEquivalente() 
		{
		return isProcessoArriviFiniti();
		}

	@Override
	public BehavEquation getPhaseBehavior() 
		{
		return this.phaseBehavior;
		}
	}
