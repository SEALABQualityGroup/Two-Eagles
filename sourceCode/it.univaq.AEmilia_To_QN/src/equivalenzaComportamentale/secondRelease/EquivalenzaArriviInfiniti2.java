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
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviInfiniti;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ExitBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PhaseBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaArriviInfiniti2
	extends EquivalenzaArrivi2
	implements IEquivalenzaArriviInfiniti	
	{
	
	public EquivalenzaArriviInfiniti2() 
		{
		super();
		}

	public EquivalenzaArriviInfiniti2(ElemType elemType) 
		{
		super();
		this.elemType = elemType;
		}

	public boolean isProcessoArriviInfiniti() 
		{
		// Il comportamento di un processo di arrivi infiniti 
		// è definito dalla seguente sequenza di comportamenti:
		// 1.una distribuzione di tipo fase;
		// 2.il routing di jobs.

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
		// se processTerm2 è null, alora non abbiamo trovato un comportamento di tipo fase
		if (processTerm2 == null)
			return false;
		// preleviamo i tassi delle azioni di fase
		PhaseBehavior phaseBehavior2 = new PhaseBehavior(processTerm2,tinteractions);
		List<Expression> listFASI = phaseBehavior2.getTassi();
		// 2.il routing di jobs;
		// preleviamo la differenza tra processTerm e processTerm2
		List<ProcessTerm> list = MetodiVari.differenza(processTerm, phaseBehavior.getMaximalPhaseBehavior());
		// allochiamo memoria per i nomi delle azioni di consegna
		List<String> listDel = new ArrayList<String>();
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
		
		// per il primo elemento di list dobbiamo cercare un comportamento di routing di jobs
		ProcessTerm processTerm3 = list.get(0);

		ExitBehavior exitBehavior = new ExitBehavior(processTerm3,tinteractions);
		ProcessTerm processTerm4 = exitBehavior.getMaximalJobsRoutingBehavior();
		if (processTerm4 == null)
			return false;
		
		// se list ha lunghezza maggiore di uno vuol dire che ho incontrato un comportamento
		// di fase di tipo hyperesponenziale. In questo caso bisogna verificare che i comportamenti di
		// list siano uguali semanticamente
		if (list.size() > 1)
			{
			ExitBehavior exitBehavior2 = new ExitBehavior(list.get(0),tinteractions);
			ProcessTerm processTermR3 = exitBehavior2.getJobsRoutingBehavior();
			for (int i = 1; i < list.size(); i++)
				{
				ExitBehavior exitBehavior3 = new ExitBehavior(list.get(i),tinteractions);
				ProcessTerm processTermR4 = exitBehavior3.getJobsRoutingBehavior();
				if (!processTermR3.equals(processTermR4))
					return false;
				}
			}
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
		
		// le uniche azioni di output devono essere le azioni di consegna
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		List<String> list2 = tinteractionsParts.getOutputInteractions();
		if (!list2.containsAll(listDel))
			return false;
		if (!listDel.containsAll(list2))
			return false;
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
	
	// Restituisce un comportamento con le equazioni standard.
	// restituisce null se et non è un processo di arrivi finiti
	public AETbehavior getNormalizedBehavior()
		{
		if (!isProcessoArriviInfiniti())
			return null;
		AETbehavior tbehaviorR = new AETbehavior();
		// il comportamento risultato ha due equazioni
		BehavEquation[] behavEquationsR = new BehavEquation[2];
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
		// costruiamo un'equazione per il comportamento di fase
		ParamDeclaration[] declPars = new ParamDeclaration[2];
		declPars[0] = null;
		declPars[1] = null;
		Header header = new Header("Fase",declPars);
		ProcessTerm processTerm2 = phaseBehavior.getPhaseBehavior();
		// impostiamo la coda per processTerm2
		BehavProcess behavProcess = new BehavProcess("Routing", new Expression[0]);
		processTerm2 = MetodiVari.somma(processTerm2, behavProcess);
		BehavEquation behavEquation2 = new BehavEquation(header,
				processTerm2);
		behavEquationsR[0] = behavEquation2;
		// 2.il routing di jobs;
		List<ProcessTerm> list = MetodiVari.differenza(processTerm, 
				phaseBehavior.getMaximalPhaseBehavior());
		// per ogni elemento di list dobbiamo cercare un comportamento di routing di jobs
		ProcessTerm processTerm3 = list.get(0);
		ExitBehavior exitBehavior = new ExitBehavior(processTerm3,tinteractions);
		ProcessTerm processTerm4 = exitBehavior.getJobsRoutingBehavior();
		// impostiamo la coda per processTerm4
		BehavProcess behavProcess2 = new BehavProcess("Fase",new Expression[0]);
		processTerm4 = MetodiVari.somma(processTerm4, behavProcess2);
		// crea un'equazione per il routing
		ParamDeclaration[] declPars2 = new ParamDeclaration[2];
		declPars2[0] = null;
		declPars2[1] = null;
		Header intestazione2 = new Header("Routing",declPars2);
		BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm4);
		behavEquationsR[1] = behavEquation3;
		// si impostano correttamente le chiamate alle equazioni
		List<BehavProcess> list3 = computeLeaf(processTerm2);
		List<BehavProcess> list4 = computeLeaf(processTerm4);
		for (BehavProcess processTerm5 : list3)
			{
			processTerm5.setName("Routing");
			processTerm5.setExprs(new Expression[0]);
			}
		for (BehavProcess behavProcess4 : list4)
			{
			behavProcess4.setName("Fase");
			behavProcess4.setExprs(new Expression[0]);
			}
		return tbehaviorR;
		}

	@Override
	public BehavEquation getPhaseBehavior() 
		{
		return this.phaseBehavior;
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
		return this.isProcessoArriviInfiniti();
		}
	}
