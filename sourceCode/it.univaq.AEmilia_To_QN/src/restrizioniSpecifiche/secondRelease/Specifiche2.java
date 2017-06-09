package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.Interaction;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheFCB;
import restrizioniSpecifiche.interfaces.ISpecificheUB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ArchiAttachments;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;
import specificheAEmilia.ElemType;
import specificheAEmilia.RateInf;
import valutazione.NormalizeException;
import valutazione.NormalizeParts;
import valutazione.Scope;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.EquivalenzaFactory2;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArrivi;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviFiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferIllimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferLimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaFork;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaRouting;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizio;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;

public abstract class Specifiche2 
	implements ISpecifiche 
	{
	
	/*
	 * Le espressioni utilizzate per la trasformazione devono essere privi di 
	 * identificatori dichiarati nelle intestazioni delle equazioni comportamentali:
	 * PROCESSI ARRIVI INFINITI
	 * 1) i pesi delle azioni di scelta di fase devono essere Real; (isOnlyReal)
	 * 2) i tassi delle azioni di fase devono essere Real; (isOnlyReal)
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 * PROCESSI ARRIVI FINITI
	 * 1) i pesi delle azioni di scelta di fase devono essere Real; (isOnlyReal)
	 * 2) i tassi delle azioni di fase devono essere Real; (isOnlyReal)
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 * BUFFERS ILLIMITATI
	 * BUFFERS LIMITATI
	 * 4) le capacità di ogni classe deve essere un Integer; (isOnlyInteger)
	 * PROCESSI FORKS CON BUFFER
	 * PROCESSI FORKS SENZA BUFFER
	 * PROCESSI JOINS CON BUFFER
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 * PROCESSI JOINS SENZA BUFFER
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 * PROCESSI DI SERVIZIO CON BUFFER
	 * 1) i pesi delle azioni di scelta di fase devono essere Real; (isOnlyReal)
	 * 2) i tassi delle azioni di fase devono essere Real; (isOnlyReal)
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 * 5) le priorità delle azioni di selezione devono essere Integer
	 * PROCESSI DI SERVIZIO SENZA BUFFER
	 * 1) i pesi delle azioni di scelta di fase devono essere Real; (isOnlyReal)
	 * 2) i tassi delle azioni di fase devono essere Real; (isOnlyReal)
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 * PROCESSI DI ROUTING CON BUFFER
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 * PROCESSI DI ROUTING SENZA BUFFER
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 */
	
	protected ArchiType archiType;
	AEIdecl idecl;
	protected List<IEquivalenza> listEquivalenzeArchiType;
	protected IEquivalenza equivalenza;
	
	/*
	 * collegamenti in cui idecl risulta essere l'istanza di input
	 */
	protected List<AttacDecl> attacsDeclInput;
	List<AttacDecl> attacsDeclOutput;
	/*
	 * istanze collegate a idecl come istanze di output
	 */
	List<AEIdecl> AEIsDeclInput;
	List<AEIdecl> AEIsDeclOutput;
	/*
	 * equivalenze di istanze di output in cui idecl è l'istanza di input
	 */
	protected List<IEquivalenza> equivalenzeInput;
	List<IEquivalenza> equivalenzeOutput;
	
	public Specifiche2() 
		{
		super();
		}

	public Specifiche2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
		throws RestrizioniSpecException
		{
		super();
		this.archiType = archiType;
		this.idecl = idecl;
		this.listEquivalenzeArchiType = listEquivalenzeArchiType;
		// come precondizione ho il tipo architetturale già normalizzato
		inizializzazione2();
		}

	/**
	 * Imposta il tipo di elemento architetturale normalizzato di equivalenza.
	 * 
	 * @throws RestrizioniSpecException
	 */
	protected void normalizeElemTypeForEquiv()
			throws RestrizioniSpecException {
		try {
			Scope scope = new Scope(this.archiType);
			NormalizeParts normalizeParts = new NormalizeParts(scope);
			ElemType elemType = normalizeParts.normalizeElemTypeFromAEI(this.idecl);
			this.equivalenza.setEt(elemType);
			}
		catch (NormalizeException e)
			{
			throw new RestrizioniSpecException(e);
			}
	}

	// il seguente costruttore viene utilizzato per il testing
	public Specifiche2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory) 
		throws RestrizioniSpecException
		{
		super();
		inizializzazione1(idecl, archiType, equivalenzaFactory);
		// si crea la lista di tutte le equivalenzaFactory presenti nel tipo architetturale
		inizializzazioneEquivalenze();
		inizializzazione2();
		}


	/**
	 * Imposta i campi attacsDeclInput, attacsDeclOutput, AEIsDeclInput, AEIsDeclOutput, equivalenzeInput,
	 * equivalenzeOutput utilizzati per la verifica delle restrizioni.
	 *
	 * @throws RestrizioniSpecException
	 */
	protected void inizializzazione2()
			throws RestrizioniSpecException {
			// si preleva una lista che contiene tutti gli AttacDecl
			// che hanno idecl come interazione di input
			ArchiAttachments archiAttachments = this.archiType.getTopologia().getAttachments();
			// list2 contiene i collegamenti in cui l'istanza che si sta considerando
			// risulta essere un'istanza di input
			List<AttacDecl> list2 = getAttacDeclsAEIInput(idecl.getName(), archiAttachments);
			attacsDeclInput = list2;
			// si prelevano i nomi degli AEIs di output
			// list3 contiene i nomi degli aei di output in cui l'aei attuale
			// risulta essere l'aei di input
			List<String> list3 = getAEIsOutputFromAttacDecls(list2);
			// si preleva una lista che contiene tutti gli AttacDecl
			// che hanno idecl come aei di output
			List<AttacDecl> list = getAttacDeclsAEIOutput(idecl.getName(), archiAttachments);
			attacsDeclOutput = list;
			// si prelevano i nomi degli AEIs di input tra i collegamenti in cui l'istanza considerata
			// risulta essere un AEI di output
			List<String> list5 = getAEIsInputFromAttacDecls(list);
			ArchiElemInstances archiElemInstances = this.archiType.getTopologia().getAEIs();
			// si prelevano le dichiarazioni degli AEI di output collegati
			List<AEIdecl> list9 = getAEIsFromNames(list3, archiElemInstances);
			AEIsDeclInput = list9;
			// si prelevano le dichiarazioni degli AEI di input collegati
			List<AEIdecl> list10 = getAEIsFromNames(list5, archiElemInstances);
			AEIsDeclOutput = list10;
			// si restituiscono delle liste contenenti le equivalenze 
			// corrispondeti agli ElemType di list9 e list10
			List<IEquivalenza> list7 = getEquivalenze(this.archiType, AEIsDeclInput);
			this.equivalenzeInput = list7;
			List<IEquivalenza> list8 = getEquivalenze(this.archiType, AEIsDeclOutput);
			this.equivalenzeOutput = list8;
			}

	@Override
	public List<AEIdecl> getAEIsDeclInput() 
		{
		return this.AEIsDeclInput;
		}

	@Override
	public List<AEIdecl> getAEIsDeclOutput() 
		{
		return this.AEIsDeclOutput;
		}

	@Override
	public ArchiType getArchiType() 
		{
		return this.archiType;
		}

	@Override
	public List<AttacDecl> getAttacsDeclInput() 
		{
		return this.attacsDeclInput;
		}

	@Override
	public List<AttacDecl> getAttacsDeclOutput() 
		{
		return this.attacsDeclOutput;
		}

	@Override
	public List<IEquivalenza> getEquivalenzeInput() 
		{
		return this.equivalenzeInput;
		}

	@Override
	public List<IEquivalenza> getEquivalenzeOutput() 
		{
		return this.equivalenzeOutput;
		}

	@Override
	public AEIdecl getIdecl() 
		{
		return this.idecl;
		}

	@Override
	public List<String> getInstancesInput() 
		{
		List<String> list = new ArrayList<String>();
		for (AEIdecl idecl : this.AEIsDeclInput)
			{
			String string = idecl.getName();
			list.add(string);
			}
		return list;
		}

	@Override
	public List<String> getInstancesOutput() 
		{
		List<String> list = new ArrayList<String>();
		for (AEIdecl idecl : this.AEIsDeclOutput)
			{
			String string = idecl.getName();
			list.add(string);
			}
		return list;
		}

	@Override
	public String getInteractionFromEquivalenzaOutput(IEquivalenza equivalenza) 
		{
		int i = this.equivalenzeOutput.indexOf(equivalenza);
		AttacDecl attacDecl = this.attacsDeclOutput.get(i);
		String string = attacDecl.getInputInteraction();
		return string;
		}

	@Override
	public String getNomeIstanza() 
		{
		String string = this.idecl.getName();
		return string;
		}

	@Override
	public String getNomeTipo() 
		{
		String string = this.idecl.getAET();
		return string;
		}

	@Override
	public void setArchiType(ArchiType archiType) 
		{
		this.archiType = archiType;
		}

	public IEquivalenza getEquivalenza() 
		{
		return this.equivalenza;
		}
	
	/**
	 * Restituisce una lista di dichiarazioni di collegamenti architetturali per
	 * cui toAEI risulta essere l'istanza di input.
	 *
	 * @param toAEI deve essere non null
	 * @param archiAttachments deve essere non null
	 * @return
	 */
	public List<AttacDecl> getAttacDeclsAEIInput(String toAEI, ArchiAttachments archiAttachments) 
		{
		List<AttacDecl> list = new ArrayList<AttacDecl>();
		AttacDecl[] attacDecls = archiAttachments.getAttachments();
		for (int i = 0; i < attacDecls.length; i++)
			{
			if (attacDecls[i].getInputAei().equals(toAEI))
				list.add(attacDecls[i]);
			}
		return list;
		}
	
	/**
	 * Restituisce una lista dei nomi degli AEI di output dalla lista delle dichiarazioni
	 * di collegamenti architetturali.
	 *
	 * @param list
	 * @return
	 */
	public List<String> getAEIsOutputFromAttacDecls(List<AttacDecl> list) 
		{
		List<String> list2 = new ArrayList<String>();
		for (AttacDecl attacDecl : list)
			{
			list2.add(attacDecl.getOutputAei());
			}
		return list2;
		}
	
	/**
	 * Restituisce una lista di dichiarazioni di collegamenti architetturali per
	 * cui fromAEI risulta essere l'istanza di output.
	 * Come post condizione la lista restituita è diversa da null.
	 *
	 * @param fromAEI
	 * @param archiAttachments
	 * @return
	 */
	public List<AttacDecl> getAttacDeclsAEIOutput(String fromAEI, ArchiAttachments archiAttachments) 
		{
		List<AttacDecl> list = new ArrayList<AttacDecl>();
		AttacDecl[] attacDecls = archiAttachments.getAttachments();
		for (int i = 0; i < attacDecls.length; i++)
			{
			if (attacDecls[i].getOutputAei().equals(fromAEI))
				list.add(attacDecls[i]);
			}
		return list;
		}
	
	/**
	 * Restituisce una lista dei nomi degli AEI di input dalla lista delle dichiarazioni
	 * di collegamenti architetturali.
	 *
	 * @param list
	 * @return
	 */
	public List<String> getAEIsInputFromAttacDecls(List<AttacDecl> list) 
		{
		List<String> list2 = new ArrayList<String>();
		for (AttacDecl attacDecl : list)
			{
			list2.add(attacDecl.getInputAei());
			}
		return list2;
		}
	
	/**
	 * Restituisce una lista di dichiarazioni di istanze che hanno lo stesso 
	 * nome delle stringhe contenute in collection.
	 *
	 * @param collection
	 * @param archiElemInstances
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public List<AEIdecl> getAEIsFromNames(Collection<String> collection, 
			ArchiElemInstances archiElemInstances)
			throws RestrizioniSpecException 
		{
		List<AEIdecl> list2 = new ArrayList<AEIdecl>();
		AEIdecl[] idecls = archiElemInstances.getAEIdeclSeq();
		for (String string : collection)
			{
			boolean b = false;
			for (int i = 0; i <idecls.length; i++)
				{
				String string2 = idecls[i].getName();
				if (string.equals(string2))
					{
					b = true;
					list2.add(idecls[i]);
					}
				}
			if (!b) throw new RestrizioniSpecException(string+" is not found");
			}
		return list2;
		}
	
	/**
	 * Restituisce una lista di oggetti IEquivalenza corrispondenti agli AEI dichiarati in di list2,
	 * secondo equivalenzaFactory.
	 * Viene sollevata un'eccezione se un ElemType non è equivalente a nessun elemento base di una
	 * rete di code.
	 *
	 * @param list
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public List<IEquivalenza> getEquivalenze(ArchiType archiType, List<AEIdecl> list2)
			throws RestrizioniSpecException {
			List<IEquivalenza> list3 = new ArrayList<IEquivalenza>();
			for (AEIdecl idecl : list2)
				{
				IEquivalenza iEquivalenza =
					SpecificheRules2.getEquivalenzaFromElemType(archiType, idecl);
				list3.add(iEquivalenza);
				}
			return list3;
			}
		
	/**
	 * Imposta i campi idecl, equivalenzaFactory, archiType ed  elemType.
	 *
	 * @param idecl
	 * @param archiType
	 * @param equivalenzaFactory
	 * @throws RestrizioniSpecException
	 */
	protected void inizializzazione1(AEIdecl idecl, ArchiType archiType, 
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
			{
			this.idecl = idecl;
			this.archiType = archiType;
			}
	
	/**
	 * Crea una lista di equivalenzaFactory del tipo architetturale.
	 *
	 * @throws RestrizioniSpecException
	 */
	protected void inizializzazioneEquivalenze()
			throws RestrizioniSpecException {
			AEIdecl[] idecls = this.archiType.getTopologia().getAEIs().getAEIdeclSeq();
			CopyOnWriteArrayList<AEIdecl> copyOnWriteArrayList2 = new CopyOnWriteArrayList<AEIdecl>(idecls);
			List<IEquivalenza> list = getEquivalenze(this.archiType, copyOnWriteArrayList2);
			listEquivalenzeArchiType = list;
			}
	
	/**
	 * Restiutisce true se e solo se specifiche e specifiche2 hanno le stesse interazioni di input.
	 *
	 * @param specifiche
	 * @param specifiche2
	 * @return
	 */
	public boolean sameInputInteractions(ISpecifiche specifiche, ISpecifiche specifiche2) 
		{
		List<Interaction> list = getActionsInputFromAttacs(specifiche.getAttacsDeclOutput());
		List<Interaction> list2 = getActionsInputFromAttacs(specifiche2.getAttacsDeclOutput());
		if (list.size() != list2.size()) return false;
		for (Interaction interaction : list)
			{
			if (!list2.contains(interaction)) return false;
			}
		return true;
		}
	
	/**
	 * Restituisce true se e solo se specifiche e specifiche2 hanno le stesse interazioni di output.
	 *
	 * @param specifiche
	 * @param specifiche2
	 * @return
	 */
	public boolean sameOutputInteractions(ISpecifiche specifiche, ISpecifiche specifiche2) 
		{
		List<Interaction> list = getActionsOutputFromAttacs(specifiche.getAttacsDeclInput());
		List<Interaction> list2 = getActionsOutputFromAttacs(specifiche2.getAttacsDeclInput());
		if (list.size() != list2.size()) return false;
		for (Interaction interaction : list)
			{
			if (!list2.contains(interaction)) return false;
			}
		return true;
		}
	
	// leaf
	public List<Interaction> getActionsInputFromAttacs(List<AttacDecl> list) 
		{
		List<Interaction> list2 = new ArrayList<Interaction>();
		for (AttacDecl attacDecl : list)
			{
			String string = attacDecl.getInputInteraction();
			String string2 = attacDecl.getInputAei();
			Interaction interaction = new Interaction(string2,string);
			list2.add(interaction);
			}
		return list2;
		}

	/**
	 * Restituisce una lista di oggetti interaction con istanze uguale ai nomi
	 * degli AEI di output contenuti in list, e come azioni le interazioni di output
	 * corrispondenti.
	 *
	 * @param list
	 * @return
	 */
	public List<Interaction> getActionsOutputFromAttacs(List<AttacDecl> list) 
		{
		List<Interaction> list2 = new ArrayList<Interaction>();
		for (AttacDecl attacDecl : list)
			{
			String string = attacDecl.getOutputInteraction();
			String string2 = attacDecl.getOutputAei();
			Interaction interaction = new Interaction(string2,string);
			list2.add(interaction);
			}
		return list2;
		}
	
	/**
	 * Le azioni di output connesse alla componente 
	 * devono essere azioni immediate.
	 * 
	 * @return
	 */
	public boolean restrizione14()
		throws RestrizioniSpecException
		{
		for (int i = 0; i < this.equivalenzeInput.size(); i++)
			{
			// si preleva il nome dell'interazione di output connessa a questa componente
			AttacDecl attacDecl = this.attacsDeclInput.get(i);
			String string = attacDecl.getOutputInteraction();
			// si preleva la relativa equivalenza
			IEquivalenza equivalenza = this.equivalenzeInput.get(i);
			// si preleva l'azione corrispondente a string
			Action action = equivalenza.getActionFromName(string);
			if (action == null)
				{
				String string2 = equivalenza.getAEIdecl().getName();
				throw new RestrizioniSpecException("The "+string+" action of instance "+
						string2+" don't found");
				}
			ActionRate actionRate = action.getRate();
			if (!(actionRate instanceof RateInf))
				return false;
			}
		return true;
		}
	
	/**
	 * Ogni azione di arrivo è attaccata ad un processo di arrivi, 
	 * un processo fork, un processo join, 
	 * un processo di servizio o un processo di routing.
	 * 
	 * @return
	 */
	public boolean restrizione10()
		{
		boolean ris = true;
		for (IEquivalenza equivalenza : this.equivalenzeInput)
			{
			if (equivalenza instanceof IEquivalenzaArrivi)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaFork)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaJoin)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaServizio)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaRouting)
				ris = ris && true;
			else 
				ris = ris && false;
			}
		return ris;
		}
	
	/**
	 * Ogni azione di selezione è attaccata ad un buffer.
	 * Tutte le azioni select sono connesse allo stesso buffer.
	 * 
	 * @return
	 */
	public boolean restrizione8()
		{
		boolean ris = true;
		for (IEquivalenza equivalenza : this.equivalenzeInput)
			{
			if (equivalenza instanceof IEquivalenzaBuffer)
				ris = ris && true;
			else
				ris = ris && false;
			}
		ris = ris && restrizione8_1();
		return ris;
		}

	/*
	 * Tutte le azioni select sono connesse allo stesso buffer.
	 */
	private boolean restrizione8_1()
		{
		AEIdecl idecl = this.AEIsDeclInput.get(0);
		String string = idecl.getName();
		for (int i = 1; i < this.AEIsDeclInput.size(); i++)
			{
			AEIdecl idecl2 = this.AEIsDeclInput.get(i);
			String string2 = idecl2.getName();
			if (!string.equals(string2))
				return false;
			}
		return true;
		}

	/**
	 * Ogni azione di consegna è attaccata ad un: 
	 * processo di arrivi per popolazioni finite, 
	 * buffer, processo fork senza buffer, 
	 * processo join, 
	 * processo di servizio senza buffer, 
	 * processo di routing senza buffer.
	 * 
	 * @return
	 */
	public boolean restrizione12()
		{
		boolean ris = true;
		for (IEquivalenza equivalenza : this.equivalenzeOutput)
			{
			if (equivalenza instanceof IEquivalenzaArriviFiniti)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaBuffer)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaForkSenzaBuffer)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaJoin)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaServizioSenzaBuffer)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaRoutingSenzaBuffer)
				ris = ris && true;
			else
				ris = ris && false;
			}
		return ris;
		}

	public boolean isBufferNoRouting(AEIdecl idecl)
			throws RestrizioniSpecException {
			// si trova l'elemento architetturale corrispondente a idecl
			// si preleva il tipo di elemento architetturale di idecl normalizzato
			ElemType elemType = null;
			try {
				Scope scope = new Scope(archiType);
				NormalizeParts normalizeParts = new NormalizeParts(scope);
				elemType = normalizeParts.normalizeElemTypeFromAEI(idecl);
				}
			catch (NormalizeException e)
				{
				throw new RestrizioniSpecException(e);
				}
			IEquivalenzaBufferIllimitato iEquivalenzaBufferIllimitato = new EquivalenzaFactory2().getUB();
			iEquivalenzaBufferIllimitato.setEt(elemType);
			IEquivalenzaBufferLimitato iEquivalenzaBufferLimitato = new EquivalenzaFactory2().getFCB();
			iEquivalenzaBufferLimitato.setEt(elemType);
			if (iEquivalenzaBufferIllimitato.isEquivalente())
				{
				ISpecificheUB specificheUB = 
					new Specifiche2Factory().createSpecificheUB(idecl, archiType);
				// si deve verificare che le equivalenzaFactory 
				// di output attaccate non sono
				// processi di routing
				List<IEquivalenza> list2 = specificheUB.getEquivalenzeOutput();
				if (!noRouting(list2)) return false;
				}
			else if (iEquivalenzaBufferLimitato.isEquivalente())
				{
				ISpecificheFCB specificheFCB = 
					new Specifiche2Factory().createSpecificheFCB(idecl, archiType);
				// si deve verificare che le equivalenzaFactory di output attaccate non sono
				// processi di routing
				List<IEquivalenza> list2 = specificheFCB.getEquivalenzeOutput();
				if (!noRouting(list2)) return false;
				}
			else throw new RestrizioniSpecException(idecl.getName()+" is not a buffer");
			return true;
			}

	public boolean noRouting(List<IEquivalenza> list2) 
		throws RestrizioniSpecException 
		{
		if (list2.isEmpty())
			throw new RestrizioniSpecException("Empty equivalences list");
		for (IEquivalenza iEquivalenza : list2)
			{
			if (iEquivalenza instanceof IEquivalenzaRouting) 
				return false;
			}
		return true;
		}

	/**
	 * Restituisce true se e solo se idecl è 
	 * un buffer non attaccato ad un processo join.
	 *
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public boolean isBufferNoJoin(AEIdecl idecl) 
		throws RestrizioniSpecException 
		{
		// si trova l'elemento architetturale corrispondente a idecl
		// si preleva il tipo di elemento architetturale di idecl normalizzato
		ElemType elemType = null;
		try {
			Scope scope = new Scope(archiType);
			NormalizeParts normalizeParts = new NormalizeParts(scope);
			elemType = normalizeParts.normalizeElemTypeFromAEI(idecl);
			}
		catch (NormalizeException e)
			{
			throw new RestrizioniSpecException(e);
			}
		IEquivalenzaBufferIllimitato iEquivalenzaBufferIllimitato = new EquivalenzaFactory2().getUB();
		iEquivalenzaBufferIllimitato.setEt(elemType);
		IEquivalenzaBufferLimitato iEquivalenzaBufferLimitato = new EquivalenzaFactory2().getFCB();
		iEquivalenzaBufferLimitato.setEt(elemType);
		if (iEquivalenzaBufferIllimitato.isEquivalente())
			{
			ISpecificheUB specificheUB = 
				new Specifiche2Factory().createSpecificheUB(idecl, archiType);
			// si deve verificare che le equivalenzaFactory 
			// di output attaccate non sono
			// processi join
			List<IEquivalenza> list2 = specificheUB.getEquivalenzeOutput();
			if (!noJoin(list2)) return false;
			}
		else if (iEquivalenzaBufferLimitato.isEquivalente())
			{
			ISpecificheFCB specificheFCB = 
				new Specifiche2Factory().createSpecificheFCB(idecl, archiType);
			// si deve verificare che le equivalenzaFactory di output attaccate non sono
			// processi join
			List<IEquivalenza> list2 = specificheFCB.getEquivalenzeOutput();
			if (!noJoin(list2)) return false;
			}
		else throw new RestrizioniSpecException(idecl.getName()+" is not a buffer");
		return true;
		}

	/**
	 * Restituisce true se e solo se list contiene equivalenze che non sono
	 * processi join.
	 *
	 * @param list
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public boolean noJoin(List<IEquivalenza> list) 
		throws RestrizioniSpecException 
		{
		if (list.isEmpty())
			throw new RestrizioniSpecException("Empty equivalences list");
		for (IEquivalenza iEquivalenza : list)
			{
			if (iEquivalenza instanceof IEquivalenzaJoin) return false;
			}
		return true;
		}
	}