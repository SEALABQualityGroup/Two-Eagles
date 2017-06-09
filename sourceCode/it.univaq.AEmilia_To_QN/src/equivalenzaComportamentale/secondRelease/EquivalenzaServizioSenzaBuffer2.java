package equivalenzaComportamentale.secondRelease;

import java.util.ArrayList;
import java.util.HashMap;
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
import specificheAEmilia.Stop;
import equivalenzaComportamentale.AETinteractionsParts;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ArriveBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ExitBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PhaseBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaServizioSenzaBuffer2 
	extends	EquivalenzaServizio2
	implements IEquivalenzaServizioSenzaBuffer
	{
	
	
	public EquivalenzaServizioSenzaBuffer2() 
		{
		super();
		}

	public EquivalenzaServizioSenzaBuffer2(ElemType et) 
		{
		super();
		this.elemType = et;
		}

	public boolean isServizioSenzaBuffer() 
		{
		// Il comportamento di un processo di servizio senza buffer è 
		// definito dalla seguente sequenza di comportamenti:
		// 1. l'arrivo di jobs;
		// 2. una distribuzione di tipo fase;
		// 3. routing di jobs.
		// 4. le uniche azioni di input sono le azioni di arrivo;
		// 5. le uniche azioni di output sono le azioni di consegna.
		// 6. il comportamento di routing è opzionale
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		if (!tailRecursion.isTailRecursion())
			return false;
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		ArriveBehavior arriveBehavior = new ArriveBehavior(processTerm,tinteractions);
		if (!arriveBehavior.isArrivalBehavior())
			return false;
		// alloco memoria per la mappa delle azioni di arrivo con quelle di scelta
		HashMap<String, List<String>> arrChoosesmap = new HashMap<String, List<String>>();
		// alloco memoria per la mappa delle azioni di consegna da quelle di arrivo
		HashMap<String, Action[]> arrDelActionMap = new HashMap<String, Action[]>();
		// alloco memoria per la mappa dei nomi delle azioni di consegna da quelli
		// di selezione
		HashMap<String, List<String>> arrDelSelMap = new HashMap<String, List<String>>();
		// alloco memoria per la mappa dei pesi di destinazione
		HashMap<String, Expression[]> arrPesiSelMap = new HashMap<String, Expression[]>();
		// alloco memoria per la mappa delle priorità di destinazione
		HashMap<String, Expression[]> arrPrioSelMap = new HashMap<String, Expression[]>();
		// alloco memoria per la mappa delle probabilità di routing
		HashMap<String, Expression[]> arrProbSelMap = new HashMap<String, Expression[]>();
		// prelevo i nomi delle azioni di arrivo
		List<String> listArrNames = arriveBehavior.getArriveNames();
		// alloco memoria per i tassi di servizio
		HashMap<String, Expression[]> arrTassiMap = new HashMap<String, Expression[]>();
		// assegno le priorità di selezione
		List<Expression> listPrioSel = arriveBehavior.getPrioSelezione();
		// assegno le probabilità di selezione
		List<Expression> listProbSel = arriveBehavior.getProbSelezione();
		// si preleva la differenza tra processTerm e processTerm2
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, arriveBehavior.getMaximalArrivalBehavior());
		// alloco memoria per i nomi delle azioni di consegna
		List<String> listD = new ArrayList<String>();
		for (int i = 0; i < processTerms.size(); i++)
			{
			ProcessTerm processTerm3 = processTerms.get(i);
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm3,tinteractions);
			// si preleva il comportamento di fase di phaseBehavior
			ProcessTerm processTerm4 = phaseBehavior.getMaximalPhaseBehavior();
			if (processTerm4 == null)
				return false;
			// prelevo la differenza tra processTerm3 e processTerm4
			// processTerm4 deve essere un comportamento di fase massimo
			List<ProcessTerm> list = MetodiVari.differenza(processTerm3, processTerm4);
			// se list ha lunghezza maggiore di uno vuol dire che ho incontrato un comportamento
			// di fase di tipo hyperesponenziale. In questo caso bisogna verificare che i comportamenti di
			// list siano uguali
			if (list.size() > 1)
				{
				// list.get(0) può essere o un comportamento di routing o 
				// null o un BehavProcess
				if (list.get(0) != null && !(list.get(0) instanceof BehavProcess))
					{
					ExitBehavior exitBehavior = new ExitBehavior(list.get(0),tinteractions);
					ProcessTerm processTermR3 = exitBehavior.getJobsRoutingBehavior();
					for (int j = 1; j < list.size(); j++)
						{
						ExitBehavior exitBehavior2 = new ExitBehavior(list.get(j),tinteractions);
						ProcessTerm processTermR4 = exitBehavior2.getJobsRoutingBehavior();
						if (!processTermR3.equals(processTermR4))
							return false;
						}
					}
				}
			// verifica che list contenga comportamenti di routing di job
			// processTerm5 può esssere o un comportamento di routing o null
			// o un BehavProcess
			ProcessTerm processTerm5 = list.get(0);

			ExitBehavior exitBehavior = new ExitBehavior(processTerm5,tinteractions);
			// aggiorno la mappa per i nomi delle chooses da quelle di arrivo
			String string = listArrNames.get(i);
			// imposto le mappe con chiave i nomi delle azioni di arrivo e
			// valori dati dai comportamenti di routing
			arrChoosesmap.put(string, null);
			arrDelActionMap.put(string, null);
			arrDelSelMap.put(string, null);
			arrPesiSelMap.put(string, null);
			arrPrioSelMap.put(string, null);
			arrProbSelMap.put(string, null);
			if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
				{
				List<String> listChooseNames = exitBehavior.getChooseActionNames();
				arrChoosesmap.put(string, listChooseNames);
				// aggiorno la mappa per le azioni di consegna da quelle di arrivo
				List<Action> listDelAction = exitBehavior.getDeliverActions();
				// la lista delle azioni di consegna può essere nulla (variante)
				Action[] actions = new Action[listDelAction.size()];
				listDelAction.toArray(actions);
				arrDelActionMap.put(string, actions);
				// aggiorno la mappa delle azioni di consegna
				List<String> list2Del = exitBehavior.getDeliverActionNames();
				arrDelSelMap.put(string, list2Del);
				// aggiorno la mappa per i pesi delle azioni di destinazione
				List<Expression> listPesi = exitBehavior.getPesiConsegna();
				Expression[] espressionesPesi = new Expression[listPesi.size()];
				listPesi.toArray(espressionesPesi);
				arrPesiSelMap.put(string, espressionesPesi);
				// aggiorno la mappa per le priorità delle azioni di destinazione
				List<Expression> listPrio = exitBehavior.getPrioritaConsegna();
				Expression[] espressionesPrio = new Expression[listPrio.size()];
				listPrio.toArray(espressionesPrio);
				arrPrioSelMap.put(string, espressionesPrio);
				// aggiorno la mappa delle probabilità di routing
				List<Expression> listProb = exitBehavior.getProbsRouting();
				Expression[] espressionesProb = new Expression[listProb.size()];
				listProb.toArray(espressionesProb);
				arrProbSelMap.put(string, espressionesProb);
				// aggiorno la struttura dei nomi delle azioni di consegna
				listD.addAll(list2Del);
				}
			else if (!(processTerm5 == null || processTerm5 instanceof BehavProcess))
				// nel caso in cui (processTerm2 instanceof BehavProcess)
				// bisogna impostare i campi della classe ad array o liste vuote
				return false;
			// aggiorno la mappa dei tassi di servizio
			PhaseBehavior phaseBehavior2 = new PhaseBehavior(processTerm4,tinteractions);
			List<Expression> listTassi = phaseBehavior2.getTassi();
			Expression[] espressionesTassi = new Expression[listTassi.size()];
			listTassi.toArray(espressionesTassi);
			arrTassiMap.put(string, espressionesTassi);				
			}
		// Le uniche azioni di input sono le azioni di selezione.
		List<String> list = arriveBehavior.getArriveNames();
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		List<String> list2 = tinteractionsParts.getInputInteractions();
		if (!list.containsAll(list2))
			return false;
		if (!list2.containsAll(list))
			return false;
		// Le uniche azioni di output sono le azioni di consegna.
		List<String> list3 = tinteractionsParts.getOutputInteractions();
		List<String> listD2 = MetodiVari.onlyNotNull(listD);
		if (!list3.containsAll(listD2))
			return false;
		if (!listD2.containsAll(list3))
			return false;
		
		// imposto i campi dell'oggetto
		// imposto la mappa per le associazioni dei nomi delle azioni di arrivo con quelle di scelta
		this.choosesFromSelection = arrChoosesmap;
		// imposto la mappa per le associazioni dei nomi delle azioni di arrivo con le azioni
		// di consegna
		this.deliverActionsFromSelections = arrDelActionMap;
		// imposto l'array che contiene tutti i nomi delle azioni di consegna
		this.delivers = new String[listD.size()];
		listD.toArray(this.delivers);
		// imposto la mappa delle azioni di consegna
		this.deliversFromSelection = arrDelSelMap;
		// imposto la mappa dei pesi di consegna
		this.pesiDestinazioniFromSel = arrPesiSelMap;
		// imposto la mappa delle priorità di consegna
		this.prioDestinazioniFromSel = arrPrioSelMap;
		// imposto la mappa delle probabilità di routing
		this.probRoutingFromSel = arrProbSelMap;
		// imposto l'array dei nomi delle azioni di arrivo
		this.selectionsNames = new String[listArrNames.size()];
		listArrNames.toArray(this.selectionsNames);
		// imposto la mappa dei tassi di servizio
		this.tassiServizioFromSel = arrTassiMap;
		// imposto l'array delle priorità di selezione
		this.prioSelezione = new Expression[listPrioSel.size()];
		listPrioSel.toArray(this.prioSelezione);
		// imposto l'array delle probabilità di selezione
		this.probSelezione = new Expression[listProbSel.size()];
		listProbSel.toArray(this.probSelezione);
		
		return true;
		}

	// restituisce un comportamento con le equazioni standard.
	// Ritorna null se et non è un processo di servizio senza buffer
	public AETbehavior getNormalizedBehavior()
		{
		if (!isServizioSenzaBuffer())
			return null;
		// allochiamo memoria per il comportamento risultato e le sue equazioni
		AETbehavior tbehaviorR = new AETbehavior();
		List<BehavEquation> listR = new ArrayList<BehavEquation>();
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		ArriveBehavior arriveBehavior = new ArriveBehavior(processTerm,tinteractions);
		// si preleva il comportamento per l'arrivo
		ProcessTerm processTerm2 = arriveBehavior.getMaximalArrivalBehavior();
		// si preleva la differenza tra processTerm e processTerm2
		// processTerm2 deve essere un'equazione di arrivo massima
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, processTerm2);
		// alloco memoria per i comportamenti di fase
		List<ProcessTerm> listF = new ArrayList<ProcessTerm>();
		// alloco memoria per le equazioni di routing, una per ogni elemento di list
		List<ProcessTerm> listCR = new ArrayList<ProcessTerm>();
		for (ProcessTerm processTerm3 : processTerms)
			{
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm3,tinteractions);
			// si preleva il comportamento di fase di phaseBehavior
			listF.add(phaseBehavior.getPhaseBehavior());
			List<ProcessTerm> list = MetodiVari.differenza(processTerm3, 
					phaseBehavior.getMaximalPhaseBehavior());
			// aggiungiamo il primo elemento di list a list2, perchè potrebbero esserci più comportamenti
			// di routing a causa della presenza di una distribuzione hyperesponziale
			if (!list.isEmpty())
				{
				// bisogna verificare se ci sono BehavProcess o Stop.
				// In questo caso va aggiunto null
				ProcessTerm processTerm5 = list.get(0);
				if (processTerm5 instanceof Stop || processTerm5 instanceof BehavProcess)
					listCR.add(null);
				else
					{
					// list.get(0) deve essere un comportamento di routing senza azioni null
					ExitBehavior exitBehavior = new ExitBehavior(list.get(0),tinteractions);
					listCR.add(exitBehavior.getJobsRoutingBehavior());
					}
				}
			else
				listCR.add(null);
			}
		// imposto l'equazione di arrivi
		ProcessTerm processTerm3s = arriveBehavior.getArrivalBehavior();
		List<BehavProcess> listS = computeLeaf(processTerm3s);
		ParamDeclaration[] declPars = new ParamDeclaration[2];
		declPars[0] = null;
		declPars[1] = null;
		Header header = new Header("Arrivo",declPars);
		for (int i = 0; i < listS.size(); i++)
			{
			BehavProcess behavProcess = listS.get(i);
			behavProcess.setExprs(new Expression[0]);
			behavProcess.setName("Fase"+(i+1));
			}
		BehavEquation behavEquation2 = new BehavEquation(header,processTerm3s);
		listR.add(behavEquation2);
		// imposto le equazioni di comportamento di fase
		for (int i = 0; i < listF.size(); i++)
			{
			ProcessTerm processTerm3 = listF.get(i);
			ProcessTerm processTerm4 = listCR.get(i);
			List<BehavProcess> list = computeLeaf(processTerm3);
			ParamDeclaration[] declPars2 = new ParamDeclaration[2];
			declPars2[0] = null;
			declPars2[1] = null;
			Header intestazione2 = new Header("Fase"+(i+1),declPars2);
			for (int j = 0; j < list.size(); j++)
				{
				BehavProcess behavProcess = list.get(j);
				behavProcess.setExprs(new Expression[0]);
				// può esserci una sola equazione di routing per ogni equazione di fase
				// l'equazione di routing è opzionale
				if (processTerm4 != null)
					behavProcess.setName("Routing"+(i+1));
				else
					behavProcess.setName("Arrivo");
				}
			BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm3);
			listR.add(behavEquation3);
			}
		// imposto le equazioni di routing
		for (int i = 0; i < listCR.size(); i++)
			{
			ProcessTerm processTerm3 = listCR.get(i);
			if (processTerm3 != null)
				{
				List<BehavProcess> list = computeLeaf(processTerm3);
				ParamDeclaration[] declPars2 = new ParamDeclaration[2];
				declPars2[0] = null;
				declPars2[1] = null;
				Header intestazione2 = new Header("Routing"+(i+1),declPars2);
				for (int j = 0; j < list.size(); j++)
					{
					BehavProcess behavProcess = list.get(j);
					behavProcess.setExprs(new Expression[0]);
					behavProcess.setName("Arrivo");
					}
				BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm3);
				listR.add(behavEquation3);
				}
			}
		// imposto le equazioni per il comportamento risultato
		BehavEquation[] behavEquations2 = new BehavEquation[listR.size()];
		listR.toArray(behavEquations2);
		tbehaviorR.setBehaviors(behavEquations2);
		return tbehaviorR;		
		}

	@Override
	public HashMap<String, List<String>> getChoosesFromSelection() 
		{
		return this.choosesFromSelection;
		}

	@Override
	public HashMap<String, Action[]> getDeliverActionsFromSelections() 
		{
		return this.deliverActionsFromSelections;
		}

	@Override
	public String[] getDelivers() 
		{
		return this.delivers;
		}

	@Override
	public HashMap<String, List<String>> getDeliversFromSelection() 
		{
		return this.deliversFromSelection;
		}

	@Override
	public HashMap<String, Expression[]> getPesiDestinazioniFromSel() 
		{
		return this.pesiDestinazioniFromSel;
		}

	@Override
	public HashMap<String, Expression[]> getPrioDestinazioniFromSel() 
		{
		return this.prioDestinazioniFromSel;
		}

	@Override
	public HashMap<String, Expression[]> getProbRoutingFromSel() 
		{
		return this.probRoutingFromSel;
		}

	@Override
	public String[] getArrivesNames() 
		{
		return this.selectionsNames;
		}

	@Override
	public List<BehavEquation> getServiceEquations() 
		{
		if (!isServizioSenzaBuffer())
			return null;
		List<BehavEquation> listRis = new ArrayList<BehavEquation>();
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		ArriveBehavior arriveBehavior = new ArriveBehavior(processTerm,tinteractions);
		// si preleva il comportamento per l'arrivo
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, 
				arriveBehavior.getArrivalBehavior());
		// alloco memoria per i comportamenti di fase
		List<ProcessTerm> listF = new ArrayList<ProcessTerm>();
		// alloco memoria per i comportamenti di routing
		List<ProcessTerm> listCR = new ArrayList<ProcessTerm>();
		for (ProcessTerm processTerm3 : processTerms)
			{
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm3,tinteractions);
			// si preleva il comportamento di fase di phaseBehavior
			// prelevo la differenza tra processTerm3 e processTerm4
			// aggiungo processTerm4 a listF
			// processTerm4 nella seguente istruzione deve essere privo di azioni null
			listF.add(phaseBehavior.getPhaseBehavior());
			List<ProcessTerm> list = MetodiVari.differenza(processTerm3, 
					phaseBehavior.getMaximalPhaseBehavior());
			// aggiungiamo il primo elemento di list a list2, perchè potrebbero esserci più comportamenti
			// di routing a causa della presenza di una distribuzione hyperesponziale
			// list può essere vuota perchè il comportamento di routing è opzionale
			if (!list.isEmpty())
				{
				// bisogna verificare se ci sono BehavProcess o Stop.
				// In questo caso va aggiunto null
				ProcessTerm processTerm5 = list.get(0);
				if (processTerm5 instanceof Stop || processTerm5 instanceof BehavProcess)
					listCR.add(null);
				else
					{
					ExitBehavior exitBehavior = new ExitBehavior(list.get(0),tinteractions);
					// nella seguente istruzione list.get(0)
					// deve essere privo di azioni null
					listCR.add(exitBehavior.getJobsRoutingBehavior());
					}
				}
			else
				listCR.add(null);
			}
		// imposto le equazioni di comportamento di fase
		for (int i = 0; i < listF.size(); i++)
			{
			ProcessTerm processTerm3 = listF.get(i);
			List<BehavProcess> list = computeLeaf(processTerm3);
			ParamDeclaration[] declPars2 = new ParamDeclaration[2];
			Header intestazione2 = new Header("Fase"+i,declPars2);
			for (int j = 0; j < list.size(); j++)
				{
				BehavProcess behavProcess = list.get(j);
				behavProcess.setExprs(new Expression[0]);
				// deve esserci una sola equazione di routing per ogni equazione di fase
				behavProcess.setName("Routing"+i);
				}
			BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm3);
			listRis.add(behavEquation3);
			}
		return listRis;		
		}

	@Override
	public HashMap<String, String> getServicesNamesFromSelections() 
		{
		if (!isServizioSenzaBuffer())
			return null;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		List<BehavEquation> listRis = new ArrayList<BehavEquation>();
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		ArriveBehavior arriveBehavior = new ArriveBehavior(processTerm,tinteractions);
		// si preleva il comportamento per l'arrivo
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, 
				arriveBehavior.getMaximalArrivalBehavior());
		// alloco memoria per i comportamenti di fase
		List<ProcessTerm> listF = new ArrayList<ProcessTerm>();
		// alloco memoria per i comportamenti di routing
		List<ProcessTerm> listCR = new ArrayList<ProcessTerm>();
		
		for (ProcessTerm processTerm3 : processTerms)
			{
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm3,tinteractions);
			// prelevo la differenza tra processTerm3 e processTerm4
			// aggiungo processTerm4 a listF
			listF.add(phaseBehavior.getPhaseBehavior());
			List<ProcessTerm> list = MetodiVari.differenza(processTerm3, 
					phaseBehavior.getMaximalPhaseBehavior());
			// aggiungiamo il primo elemento di list a list2, perchè potrebbero esserci più comportamenti
			// di routing a causa della presenza di una distribuzione hyperesponziale
			// list può essere vuota perchè il comportamento di routing è opzionale
			if (!list.isEmpty())
				{
				// bisogna verificare se ci sono BehavProcess o Stop.
				// In questo caso va aggiunto null
				ProcessTerm processTerm5 = list.get(0);
				if (processTerm5 instanceof Stop || processTerm5 instanceof BehavProcess)
					listCR.add(null);
				else
					{
					ExitBehavior exitBehavior = new ExitBehavior(list.get(0),tinteractions);
					listCR.add(exitBehavior.getJobsRoutingBehavior());
					}
				}
			else
				listCR.add(null);
			
			}
		// imposto le equazioni di comportamento di fase
		for (int i = 0; i < listF.size(); i++)
			{
			ProcessTerm processTerm3 = listF.get(i);
			List<BehavProcess> list = computeLeaf(processTerm3);
			ParamDeclaration[] declPars2 = new ParamDeclaration[2];
			Header intestazione2 = new Header("Fase"+i,declPars2);
			for (int j = 0; j < list.size(); j++)
				{
				BehavProcess behavProcess = list.get(j);
				behavProcess.setExprs(new Expression[0]);
				// deve esserci una sola equazione di routing per ogni equazione di fase
				behavProcess.setName("Routing"+i);
				}
			BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm3);
			listRis.add(behavEquation3);
			}
		List<String> listArr = arriveBehavior.getArriveNames();
		for (int i = 0; i < listArr.size(); i++)
			{
			String string = listArr.get(i);
			BehavEquation behavEquation2 = listRis.get(i);
			Header header = behavEquation2.getBehavHeader();
			String string2 = header.getName();
			hashMap.put(string, string2);
			}
		return hashMap;		
		}

	@Override
	public HashMap<String, Expression[]> getTassiServizioFromSel() 
		{
		return this.tassiServizioFromSel;
		}

	@Override
	public boolean isEquivalente() 
		{
		return isServizioSenzaBuffer();
		}

	@Override
	public Expression[] getPrioSelezione() 
		{		
		return this.prioSelezione;
		}

	@Override
	public Expression[] getProbSelezione() 
		{
		return this.probSelezione;
		}
	}
