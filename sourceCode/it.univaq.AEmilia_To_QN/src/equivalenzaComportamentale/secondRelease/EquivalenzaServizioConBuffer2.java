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
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioConBuffer;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ExitBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PhaseBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.SelectBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaServizioConBuffer2
	extends EquivalenzaServizio2
	implements IEquivalenzaServizioConBuffer
	{
	
	public EquivalenzaServizioConBuffer2() 
		{
		super();
		}

	public EquivalenzaServizioConBuffer2(ElemType et) 
		{
		super();
		this.elemType = et;
		}

	public boolean isServizioConBuffer() 
		{
		// Il comportamento di un processo di servizio 
		// con buffer è definito dalla seguente sequenza 
		// di comportamenti:
		// 1. la selezione di jobs;
		// 2. una distribuzione di tipo fase;
		// 3. il routing di jobs.
		// 4. le uniche azioni di input sono le azioni 
		// di selezione;
		// 5. le uniche azioni di output sono le azioni 
		// di consegna.
		// 6. il comportamento di routing è opzionale
		// si rende il comportamento recursiveTail
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
		SelectBehavior selectBehavior = new SelectBehavior(processTerm,tinteractions);
		// si preleva il comportamento per la selezione
		if (!selectBehavior.isSelectionBehavior())
			return false;
		// alloco memoria per le priorità di selezione
		List<Expression> listPrioSel = selectBehavior.getPrioSelezione();
		// alloco memoria per le probabilità di routing
		List<Expression> listProbSel = selectBehavior.getProbSelezione();
		// alloco memoria per la mappa delle azioni di selezione con quelle di scelta
		HashMap<String, List<String>> selChoosesmap = new HashMap<String, List<String>>();
		// alloco memoria per la mappa delle azioni di consegna a partire dai nomi
		// delle azioni di selezione
		HashMap<String, Action[]> selDelActionMap = new HashMap<String, Action[]>();
		// alloco memoria per la mappa di associazione tra i nomi delle azioni di selezione
		// e i nomi delle azioni di consegna associati
		HashMap<String, List<String>> selNameActionsMap = new HashMap<String, List<String>>();
		// alloco memoria per la mappa di associazioni tra i nomi delle azioni di selezione
		// e i pesi delle destinazioni
		HashMap<String, Expression[]> selDelPesiMap = new HashMap<String, Expression[]>();
		// alloco memoria per la mappa di associazioni tra i nomi delle azioni di selezione
		// e i pesi delle destinazioni
		HashMap<String, Expression[]> selDelPrioMap = new HashMap<String, Expression[]>();
		// alloco memoria per la mappa di associazioni tra i nomi delle azioni di selezione
		// e le probabilità di routing
		HashMap<String, Expression[]> selDelProbMap = new HashMap<String, Expression[]>();
		// alloco memoria per la mappa dei tassi di servizio
		HashMap<String, Expression[]> selTassiMap = new HashMap<String, Expression[]>();
		
		// prelevo i nomi delle azioni di selezione
		List<String> listSelNames = selectBehavior.getSelectionNames();
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, 
				selectBehavior.getMaximalSelectionBehavior());
		// alloco memoria per i nomi delle azioni di consegna
		List<String> listD = new ArrayList<String>();
		for (int i = 0; i < processTerms.size(); i++)
			{
			ProcessTerm processTerm3 = processTerms.get(i);
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm3,tinteractions);
			// si preleva il comportamento di fase di phaseBehavior
			ProcessTerm processTerm4 = phaseBehavior.getPhaseBehavior();
			if (processTerm4 == null)
				return false;
			List<ProcessTerm> list = MetodiVari.differenza(processTerm3, 
					phaseBehavior.getMaximalPhaseBehavior());
			// se list ha lunghezza maggiore di uno vuol dire che ho incontrato un comportamento
			// di fase di tipo hyperesponenziale. In questo caso bisogna verificare che i 
			// comportamenti di
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
			// processTerm5 deve essere o un comportamento di routing o 
			// null o un BehavProcess
			ProcessTerm processTerm5 = list.get(0);
			ExitBehavior exitBehavior = 
				new ExitBehavior(processTerm5,tinteractions);
			// imposto la mappa per i nomi delle chooses da quelle di selezione
			String string = listSelNames.get(i);
			// imposto le mappe con chiave i nomi delle azioni di selezione e
			// valori dati dai comportamenti di routing
			selChoosesmap.put(string, null);
			selDelActionMap.put(string, null);
			selNameActionsMap.put(string, null);
			selDelPesiMap.put(string, null);
			selDelPrioMap.put(string, null);
			selDelProbMap.put(string, null);
			if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
				{
				// aggiorno la struttura dei nomi delle azioni di consegna
				listD.addAll(exitBehavior.getDeliverActionNames());
				List<String> listChooseNames = exitBehavior.getChooseActionNames();
				selChoosesmap.put(string, listChooseNames);
				// aggiorno la mappa per le azioni di consegna da quelle di selezione
				List<Action> listDelAction = exitBehavior.getDeliverActions();
				// la lista delle azioni di consegna può essere nulla
				Action[] actions = new Action[listDelAction.size()];
				listDelAction.toArray(actions);
				selDelActionMap.put(string, actions);
				// aggiorno la mappa per i nomi delle azioni di consegna da quelle di selezione
				List<String> listDelNames = exitBehavior.getDeliverActionNames();
				selNameActionsMap.put(string, listDelNames);
				// aggiorno la mappa dei pesi
				List<Expression> listDelPesi = exitBehavior.getPesiConsegna();
				Expression[] espressionesPesi = new Expression[listDelPesi.size()];
				listDelPesi.toArray(espressionesPesi);
				selDelPesiMap.put(string, espressionesPesi);
				// aggiorno la mappa delle priorità
				List<Expression> listDelPrio = exitBehavior.getPrioritaConsegna();
				Expression[] espressionesPrio = new Expression[listDelPrio.size()];
				listDelPrio.toArray(espressionesPrio);
				selDelPrioMap.put(string, espressionesPrio);
				// aggiorno la mappa delle probabilità di routing
				List<Expression> listDelProb = exitBehavior.getProbsRouting();
				Expression[] espressionesProb = new Expression[listDelProb.size()];
				listDelProb.toArray(espressionesProb);
				selDelProbMap.put(string, espressionesProb);
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
			selTassiMap.put(string, espressionesTassi);
			}
		// Le uniche azioni di input sono le azioni di selezione.
		List<String> list = selectBehavior.getSelectionNames();
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
		// imposto le priorità di selezione
		this.prioSelezione = new Expression[listPrioSel.size()];
		listPrioSel.toArray(this.prioSelezione);
		// imposto le probabilità di routing
		this.probSelezione = new Expression[listProbSel.size()];
		listProbSel.toArray(this.probSelezione);
		// imposto la mappa per le associazioni 
		// delle azioni di selezione con quelle di scelta
		this.choosesFromSelection = selChoosesmap;
		// imposto la mappa per le associazioni delle azioni di selezione con le azioni di consegna
		this.deliverActionsFromSelections = selDelActionMap;
		// imposto la mappa per le associazioni delle azioni di consegna con i nomi delle azioni
		// di consegna
		this.deliversFromSelection = selNameActionsMap;
		// imposto i nomi di tutte le azioni di consegna
		this.delivers = new String[listD.size()];
		listD.toArray(this.delivers);
		// imposto la mappa dei pesi
		this.pesiDestinazioniFromSel = selDelPesiMap;
		// imposto la mappa delle priorità
		this.prioDestinazioniFromSel = selDelPrioMap;
		// imposto la mappa delle probabilità di routing
		this.probRoutingFromSel = selDelProbMap;
		// imposto l'array dei nomi delle azioni di selezione
		String[] stringsSel = new String[listSelNames.size()];
		listSelNames.toArray(stringsSel);
		this.selectionsNames = stringsSel;
		// imposto la mappa dei tassi di servizio
		this.tassiServizioFromSel = selTassiMap;

		return true;
				
		}
	
	// restituisce un comportamento con le equazioni standard.
	// Ritorna null se et non è un processo di servizio con buffer
	public AETbehavior getNormalizedBehavior()
		{
		if (!isServizioConBuffer())
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
		SelectBehavior selectBehavior = new SelectBehavior(processTerm,tinteractions);
		// si preleva il comportamento per la selezione
		ProcessTerm processTerm2 = selectBehavior.getSelectionBehavior();
		// si preleva la differenza tra processTerm e processTerm2
		// processTerm2 deve essere il comportamento di selezione massimo
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, 
				selectBehavior.getMaximalSelectionBehavior());
		// alloco memoria per i comportamenti di fase
		List<ProcessTerm> listF = new ArrayList<ProcessTerm>();
		// alloco memoria per le equazioni di routing
		List<ProcessTerm> listCR = new ArrayList<ProcessTerm>();
		for (ProcessTerm processTerm3 : processTerms)
			{
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm3,tinteractions);
			// si preleva il comportamento di fase di phaseBehavior
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
		// imposto l'equazione di selezione
		// computo le foglie di processTerm
		List<BehavProcess> listS = computeLeaf(processTerm2);
		ParamDeclaration[] declPars = new ParamDeclaration[2];
		declPars[0] = null;
		declPars[1] = null;
		Header header = new Header("Select",declPars);
		for (int i = 0; i < listS.size(); i++)
			{
			BehavProcess behavProcess = listS.get(i);
			behavProcess.setExprs(new Expression[0]);
			behavProcess.setName("Fase"+(i+1));
			}
		BehavEquation behavEquation2 = new BehavEquation(header,processTerm2);
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
					behavProcess.setName("Select");
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
					behavProcess.setName("Select");
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
	public Expression[] getPrioSelezione() 
		{
		return this.prioSelezione;
		}

	@Override
	public Expression[] getProbSelezione() 
		{
		return this.probSelezione;
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
	public HashMap<String, List<String>> getDeliversFromSelection() 
		{
		return this.deliversFromSelection;
		}

	@Override
	public String[] getDelivers() 
		{
		return this.delivers;
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
	public String[] getSelectionsNames() 
		{
		return this.selectionsNames;
		}

	@Override
	public List<BehavEquation> getServiceEquations() 
		{
		if (!isServizioConBuffer())
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
		SelectBehavior selectBehavior = new SelectBehavior(processTerm,tinteractions);
		// si preleva il comportamento per la selezione
		// si preleva la differenza tra processTerm e processTerm2
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, 
				selectBehavior.getMaximalSelectionBehavior());
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
			ProcessTerm processTerm4 = listCR.get(i);
			List<BehavProcess> list = computeLeaf(processTerm3);
			ParamDeclaration[] declPars2 = new ParamDeclaration[2];
			Header intestazione2 = new Header("Fase"+i,declPars2);
			for (int j = 0; j < list.size(); j++)
				{
				BehavProcess behavProcess = list.get(j);
				behavProcess.setExprs(new Expression[0]);
				// deve esserci una sola equazione di routing per ogni equazione di fase
				// l'equazione di routing è opzionale
				if (processTerm4 != null)
					behavProcess.setName("Routing"+i);
				else
					behavProcess.setName("Select");
				}
			BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm3);
			listRis.add(behavEquation3);
			}
		return listRis;		
		}

	@Override
	public HashMap<String, String> getServicesNamesFromSelections() 
		{
		if (!isServizioConBuffer())
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
		SelectBehavior selectBehavior = new SelectBehavior(processTerm,tinteractions);
		List<ProcessTerm> processTerms = MetodiVari.differenza(processTerm, 
				selectBehavior.getMaximalSelectionBehavior());
		// alloco memoria per i comportamenti di fase
		List<ProcessTerm> listF = new ArrayList<ProcessTerm>();
		// alloco memoria per i comportamenti di routing
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
			ProcessTerm processTerm4 = listCR.get(i);
			List<BehavProcess> list = computeLeaf(processTerm3);
			ParamDeclaration[] declPars2 = new ParamDeclaration[2];
			Header intestazione2 = new Header("Fase"+i,declPars2);
			for (int j = 0; j < list.size(); j++)
				{
				BehavProcess behavProcess = list.get(j);
				behavProcess.setExprs(new Expression[0]);
				// deve esserci una sola equazione di routing per ogni equazione di fase
				// l'equazione di routing è opzionale
				if (processTerm4 != null)
					behavProcess.setName("Routing"+i);
				else
					behavProcess.setName("Select");
				}
			BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm3);
			listRis.add(behavEquation3);
			}
		List<String> listSel = selectBehavior.getSelectionNames();
		for (int i = 0; i < listSel.size(); i++)
			{
			String string = listSel.get(i);
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
		return isServizioConBuffer();
		}
	}
