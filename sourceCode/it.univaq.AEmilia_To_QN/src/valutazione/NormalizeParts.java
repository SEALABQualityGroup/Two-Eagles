package valutazione;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import specificheAEmilia.AEIdecl;
import specificheAEmilia.AEIdeclInd;
import specificheAEmilia.Action;
import specificheAEmilia.ActionOutput;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.ArchiAttachments;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiInteractions;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;
import specificheAEmilia.AttacDeclInd;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.Const;
import specificheAEmilia.ConstInit;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.Integer;
import specificheAEmilia.InteractionDecl;
import specificheAEmilia.InteractionDeclInd;
import specificheAEmilia.IntegerRangeType;
import specificheAEmilia.Header;
import specificheAEmilia.Local;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.RateExp;
import specificheAEmilia.RateInf;
import specificheAEmilia.Rate_;
import specificheAEmilia.Real;
import specificheAEmilia.DataType;
import specificheAEmilia.VariableDeclaration;
import specificheAEmilia.VarInit;

/**
 * Viene utilizzata per la riduzione in forme normali
 * delle parti di una specifica AEmilia.
 * 
 * @author Mirko
 *
 */
public class NormalizeParts  
	{

	private Scope scope;
	
	public NormalizeParts(Scope scope) 
		{
		super();
		this.scope = scope;
		}

	/**
	 * Normalizza la specifica AEmilia: semplificando
	 * le dichiarazioni indicizzate, valorizzando le espressioni e
	 * assegnando un valore agli identificatori. Normalizza anche
	 * i tipi di elementi architetturali. Utile quando i tipi di elementi
	 * architetturali non hanno parametri.
	 *
	 * @return il tipo architetturale normalizzato.
	 * @throws NormalizeException
	 */
	public ArchiType normalizeAllWithElemType() throws NormalizeException {
	ArchiType clone = this.scope.getAt().copy();
	// si normalizzano i tipi di elementi architetturali
	ElemType[] ets = clone.getArchiElemTypes().getElementTypes();
	for (int i = 0; i < ets.length; i++)
	    {
	    ElemType et = normalizeElemType(ets[i]);
	    ets[i] = et;
	    }
	clone.getArchiElemTypes().setElementTypes(ets);
	// si normalizzano le dichiarazioni di collegamenti di
	// elementi architetturali
	ArchiAttachments aa = normalizeAttacs();
	clone.getTopologia().setAttachments(aa);
	// si normalizzano le dichiarazioni di interazioni architetturali
	ArchiInteractions ai = normalizeInteractions();
	clone.getTopologia().setArchiInteractions(ai);
	// si normalizzano le dichiarazioni di istanze di tipi di elementi architetturali
	ArchiElemInstances aei = normalizeInstances();
	clone.getTopologia().setAEIs(aei);
	return clone;
	}

	/**
	 * Normalizza la specifica AEmilia: semplificando
	 * le dichiarazioni indicizzate, valorizzando le espressioni e
	 * assegnando un valore agli identificatori.
	 *
	 * @return il tipo architetturale normalizzato.
	 * @throws NormalizeException
	 */
	public ArchiType normalizeAll() throws NormalizeException {
	ArchiType clone = this.scope.getAt().copy();
	// si normalizzano le dichiarazioni di collegamenti di
	// elementi architetturali
	ArchiAttachments aa = normalizeAttacs();
	clone.getTopologia().setAttachments(aa);
	// si normalizzano le dichiarazioni di interazioni architetturali
	ArchiInteractions ai = normalizeInteractions();
	clone.getTopologia().setArchiInteractions(ai);
	// si normalizzano le dichiarazioni di istanze di tipi di elementi architetturali
	ArchiElemInstances aei = normalizeInstances();
	clone.getTopologia().setAEIs(aei);
	return clone;
	}

	/**
	 * Normalizza le espressioni contenute nella dichiarazione
	 * di istanza, fornita come parametro.
	 *
	 * @param aeid
	 * @return la dichiarazione normalizzata
	 * @throws NormalizeException
	 */
	public AEIdecl normalizeAEIdecl(AEIdecl aeid) throws NormalizeException {
	AEIdecl clone = aeid.copy();
	// bisogna ispezionare la struttura di clone.
	// Si preleva lo scope della dichiarazione di istanza
	TreeMap<String, ValueIdentExpr> sco = scope.getScopeAEIdecl(clone);
	// si normalizza l'eventuale espressione di selezione
	if (clone.getSelector() != null)
	    {
	    Object o;
	    o = scope.getValutazione().valuta(clone.getSelector(), sco);
	    if (o instanceof Expression)
	        clone.setSelector((Expression)o);
	    else
	        clone.setSelector(Valutazione.obtainExp(o));
	    // si concatena al nome la valutazione dell'espressione di selezione
	    clone.setName(concatInd(clone.getName(), clone.getSelector()));
	    }
	// si normalizzano i valori attuali per i parametri formali costanti dell'AET.
	if (clone.getActualParams() != null)
	    {
	    for (int i = 0; i < clone.getActualParams().length; i++)
	        {
	        Object o;
	        o = scope.getValutazione().valuta(clone.getActualParams()[i], sco);
	        if (o instanceof Expression)
	            clone.getActualParams()[i] = (Expression)o;
	        else
	            clone.getActualParams()[i] = Valutazione.obtainExp(o);
	        }
	    }
	return clone;
	}

	/**
	 * Restituisce un array di dichiarazioni di istanze
	 * di elementi architetturali semplici,
	 * a partire da una dichiarazione indicizzata.
	 *
	 * @param aeidi
	 * @return
	 * @throws NormalizeException
	 */
	public AEIdecl[] normalizeAEIdeclInd(AEIdeclInd aeidi) throws NormalizeException {
	AEIdeclInd clone = aeidi.copy();
	// si preleva lo scope della dichiarazione di istanza
	TreeMap<String, ValueIdentExpr> sco = scope.getScopeAEIdecl(clone);
	int indiceIniziale = 0, indiceFinale = 0;
	// si valuta l'espressione iniziale
	Object o1 = scope.getValutazione().valuta(clone.getBeginningExpr(), sco);
	if (o1 instanceof Expression)
	    clone.setBeginningExpr((Expression)o1);
	else
	    clone.setBeginningExpr(Valutazione.obtainExp(o1));
	// si valuta l'espressione finale
	Object o2 = scope.getValutazione().valuta(clone.getEndingExpr(), sco);
	if (o2 instanceof Expression)
	    clone.setEndingExpr((Expression)o2);
	else
	    clone.setEndingExpr(Valutazione.obtainExp(o2));
	// si controlla la propriet√† di interezza
	Interezza interezza = new Interezza();
	if (!interezza.isRangeIntero(clone))
	    try {
	        throw new InterezzaException("The range is not integer");
	        }
	    catch (InterezzaException e)
	        {
	        throw new NormalizeException(e);
	        }
	// si preleva l'espressione iniziale come un intero
	indiceIniziale = ((Integer)clone.getBeginningExpr()).getValore();
	// si preleva l'espressione finale come un intero
	indiceFinale = ((Integer)clone.getEndingExpr()).getValore();
	// si alloca memoria per l'array risultato
	AEIdecl[] ris = new AEIdecl[indiceFinale - indiceIniziale + 1];
	for (int i = 0; i < indiceFinale - indiceIniziale + 1; i++)
	    {
	    // si aggiorna l'indice
	    sco.put(clone.getIndex(), new ValueIdentExpr(new java.lang.Integer(i + indiceIniziale),true));
	    // si normalizza la dichiarazione secondo il nuovo valore
	    // dell'indice
	    ris[i] = normalizeAEIdecl(clone, sco);
	    }
	// si restituisce l'array
	return ris;
	}

	/**
	 * Restituisce un array di dichiarazioni di collegamenti architetturali semplici, a partire
	 * da una dichiarazione indicizzata.
	 *
	 * @param adi
	 * @return
	 * @throws NormalizeException
	 */
	public AttacDecl[] normalizeAttacDeclInd(AttacDeclInd adi) throws NormalizeException {
	AttacDeclInd clone = adi.copy();
	// si preleva lo scope della dichiarazione
	// di collegamento architetturale
	TreeMap<String, ValueIdentExpr> sco = scope.getScopeAttacDecl(clone);
	int indiceIniziale1 = 0, indiceFinale1 = 0, indiceIniziale2 = 0, indiceFinale2 = 0;
	// si valuta l'espressione iniziale per la prima indicizzazione
	Object o1 = scope.getValutazione().valuta(clone.getBeginningExpr1(), sco);
	if (o1 instanceof Expression)
	    clone.setBeginningExpr1((Expression)o1);
	else
	    clone.setBeginningExpr1(Valutazione.obtainExp(o1));
	// si valuta l'espressione finale per la prima indicizzazione
	Object o2 = scope.getValutazione().valuta(clone.getEndingExpr1(), sco);
	if (o2 instanceof Expression)
	    clone.setEndingExpr1((Expression)o2);
	else
	    clone.setEndingExpr1(Valutazione.obtainExp(o2));
	// si prelevano anche le espressioni iniziali e finali
	// per la seconda indicizzazione come interi
	if (clone.getIndex2() != null)
	    {
	    // si valuta l'espressione iniziale per la seconda indicizzazione
	    Object o3 = scope.getValutazione().valuta(clone.getBeginningExpr2(), sco);
	    if (o3 instanceof Expression)
	        clone.setBeginningExpr2((Expression)o3);
	    else
	        clone.setBeginningExpr2(Valutazione.obtainExp(o3));
	    // si valuta l'espressione finale per la seconda indicizzazione
	    Object o4 = scope.getValutazione().valuta(clone.getEndingExpr2(), sco);
	    if (o4 instanceof Expression)
	        clone.setEndingExpr2((Expression)o4);
	    else
	        clone.setEndingExpr2(Valutazione.obtainExp(o4));
	    }
	// si verifica la propriet√† di interezza di clone
	Interezza interezza = new Interezza();
	if (!interezza.isRangesInteri(clone))
	    try {
	        throw new InterezzaException("The range is not integer");
	        }
	    catch (InterezzaException e)
	        {
	        throw new NormalizeException(e);
	        }
	// si preleva la prima espressione iniziale come un
	// intero
	indiceIniziale1 = ((Integer)clone.getBeginningExpr1()).getValore();
	// si preleva la prima espressione finale come un
	// intero
	indiceFinale1 = ((Integer)clone.getEndingExpr1()).getValore();
	// si alloca memoria per l'array risultato
	if (clone.getIndex2() != null)
	    {
	    // si preleva la seconda espressione iniziale come un
	    // intero
	    indiceIniziale2 = ((Integer)clone.getBeginningExpr2()).getValore();
	    // si preleva la seconda espressione finale come un
	    // intero
	    indiceFinale2 = ((Integer)clone.getEndingExpr2()).getValore();
	    }
	AttacDecl[] ris = null;
	if (indiceFinale2 - indiceIniziale2 > 0)
	    ris = new AttacDecl[(indiceFinale1 - indiceIniziale1 + 1) * (indiceFinale2 - indiceIniziale2 + 1)];
	else
	    ris = new AttacDecl[indiceFinale1 - indiceIniziale1 + 1];
	int c = 0;
	for (int i = 0; i < indiceFinale1 - indiceIniziale1 + 1; i++)
	    {
	    for (int j = 0; j < indiceFinale2 - indiceIniziale2 + 1; j++)
	        {
	        // si aggiornano gli indici
	        sco.put(clone.getIndex1(), new ValueIdentExpr(new java.lang.Integer(i + indiceIniziale1),true));
	        if (indiceFinale2 - indiceIniziale2 > 0)
	        sco.put(clone.getIndex2(), new ValueIdentExpr(new java.lang.Integer(j + indiceIniziale2),true));
	        // si normalizza la dichiarazione secondo i nuovi valori
	        // per gli indici
	        ris[c] = normalizeAttacDecl(clone, sco);
	        c++;
	        }
	    }
	// si restituisce l'array
	return ris;
	}

	/**
	 * Restituisce un array di dichiarazioni di interazioni semplici, a partire
	 * da una dichiarazione indicizzata.
	 *
	 * @param idi
	 * @return
	 * @throws NormalizeException
	 */
	public InteractionDecl[] normalizeInteractionDeclInd(InteractionDeclInd idi)
			throws NormalizeException {
			InteractionDeclInd clone = idi.copy();
			// si preleva lo scope della dichiarazione
			// di interazione architetturale
			TreeMap<String, ValueIdentExpr> sco = scope.getScopeInteractionDecl(clone);
			int indiceIniziale = 0, indiceFinale = 0;
			// si valuta l'espressione iniziale
			Object o1 = scope.getValutazione().valuta(clone.getBeginningExpr(), sco);
			if (o1 instanceof Expression)
			    clone.setExprInizio((Expression)o1);
			else
			    clone.setExprInizio(Valutazione.obtainExp(o1));
			// si valuta l'espressione finale
			Object o2 = scope.getValutazione().valuta(clone.getEndingExpr(), sco);
			if (o2 instanceof Expression)
			    clone.setExprFine((Expression)o2);
			else
			    clone.setExprFine(Valutazione.obtainExp(o2));
			// si verifica la propriet√† di interezza
			Interezza interezza = new Interezza();
			if (!interezza.isRangeIntero(clone))
			    try {
			        throw new InterezzaException("The range is not integer");
			        }
			    catch (InterezzaException e)
			        {
			        throw new NormalizeException(e);
			        }
			// si preleva l'espressione iniziale come un
			// intero
			indiceIniziale = ((Integer)clone.getBeginningExpr()).getValore();
			// si preleva l'espressione finale come un
			// intero
			indiceFinale = ((Integer)clone.getEndingExpr()).getValore();
			// si alloca memoria per l'array risultato
			InteractionDecl[] ris = new InteractionDecl[indiceFinale - indiceIniziale + 1];
			for (int i = 0; i < indiceFinale - indiceIniziale + 1; i++)
			    {
			    // si aggiorna l'indice
			    sco.put(clone.getIndex(), new ValueIdentExpr(new java.lang.Integer(i + indiceIniziale),true));
			    // si normalizza la dichiarazione secondo il nuovo valore
			    // per l'indice
			    ris[i] = normalizeInteractionDecl(clone, sco);
			    }
			// si restituisce l'array
			return ris;
			}

	/**
	   * Restituisce una dichiarazione di interazione semplice
	  * con lo scope fornito come parametro, a partire da una
	  * dichiarazione composta.
	  *
	 * @param ad
	 * @param sco
	 * @return
	 * @throws NormalizeException
	 */
	private InteractionDecl normalizeInteractionDecl(InteractionDecl ad, TreeMap<String, ValueIdentExpr> sco)
			throws NormalizeException {
			InteractionDecl clone = ad.copy();
			// bisogna ispezionare la struttura di clone.
			// Si normalizza l'eventuale espressione di selezione.
			if (clone.getSelector() != null)
			    {
			    Object o = scope.getValutazione().valuta(clone.getSelector(), sco);
			    if (o instanceof Expression)
			        clone.setSelector((Expression)o);
			    else
			        clone.setSelector(Valutazione.obtainExp(o));
			    // si concatena al nome dell'aei l'espressione di selezione
			    clone.setAei(concatInd(clone.getAei(), clone.getSelector()));
			    }
			// si costruisce una dichiarazione semplice, e
			// la si restituisce come risultato
			InteractionDecl ris = new InteractionDecl();
			ris.setAei(clone.getAei());
			ris.setInteraction(clone.getInteraction());
			return ris;
			}

	/**
	   * Restituisce una dichiarazione di collegamento semplice
	  * con lo scope fornito come parametro, a partire da una
	  * dichiarazione composta.
	  *
	 * @param ad
	 * @param sco
	 * @return
	 * @throws NormalizeException
	 */
	private AttacDecl normalizeAttacDecl(AttacDecl ad, TreeMap<String, ValueIdentExpr> sco)
			throws NormalizeException {
			AttacDecl clone = ad.copy();
			// bisogna ispezionare la struttura di clone.
			// Si normalizzano le eventuali espressioni di selezione
			if (clone.getFromSelector() != null)
			    {
			    Object o = scope.getValutazione().valuta(clone.getFromSelector(), sco);
			    if (o instanceof Expression)
			        clone.setFromSelector((Expression)o);
			    else
			        clone.setFromSelector(Valutazione.obtainExp(o));
			    // si concatena al nome dell'aei di partenza la valutazione dell'espressione di selezione
			    clone.setOutputAei(concatInd(clone.getOutputAei(), clone.getFromSelector()));
			    }
			if (clone.getToSelector() != null)
			    {
			    Object o = scope.getValutazione().valuta(clone.getToSelector(), sco);
			    if (o instanceof Expression)
			        clone.setToSelector((Expression)o);
			    else
			        clone.setToSelector(Valutazione.obtainExp(o));
			    // si concatena al nome dell'aei di arrivo la valutazione dell'espressione di selezione
			    clone.setInputAei(concatInd(clone.getInputAei(), clone.getToSelector()));
			    }
			// si costruisce una dichiarazione semplice, e
			// la si restituisce come risultato
			AttacDecl ris = new AttacDecl();
			ris.setOutputAei(clone.getOutputAei());
			ris.setInputInteraction(clone.getInputInteraction());
			ris.setOutputInteraction(clone.getOutputInteraction());
			ris.setInputAei(clone.getInputAei());
			return ris;
			}

	/**
	  * Restituisce una dichiarazione di istanza semplice
	  * con lo scope fornito come parametro, a partire da una
	  * dichiarazione composta.
	 *
	 * @param aeid
	 * @param sco
	 * @return
	 * @throws NormalizeException
	 */
	private AEIdecl normalizeAEIdecl(AEIdecl aeid, TreeMap<String, ValueIdentExpr> sco)
			throws NormalizeException {
			AEIdecl clone = aeid.copy();
			// bisogna ispezionare la struttura di clone.
			// Si normalizza l'eventuale espressione di selezione
			if (clone.getSelector() != null)
			    {
			    Object o = scope.getValutazione().valuta(clone.getSelector(), sco);
			    if (o instanceof Expression)
			        clone.setSelector((Expression)o);
			    else
			        clone.setSelector(Valutazione.obtainExp(o));
			    // si concatena al nome l'espressione di selezione
			    clone.setName(concatInd(clone.getName(), clone.getSelector()));
			    }
			// si normalizzano i valori attuali per i parametri formali costanti dell'AET.
			if (clone.getActualParams() != null)
			    {
			    for (int i = 0; i < clone.getActualParams().length; i++)
			        {
			        Object o = scope.getValutazione().valuta(clone.getActualParams()[i], sco);
			        if (o instanceof Expression)
			            clone.getActualParams()[i] = (Expression)o;
			        else
			            clone.getActualParams()[i] = Valutazione.obtainExp(o);
			        }
			    }
			// si costruisce una dichiarazione semplice e
			// la si restituisce come risultato
			AEIdecl ris = new AEIdecl();
			ris.setAET(clone.getAET());
			ris.setName(clone.getName());
			ris.setActualParams(clone.getActualParams());
			return ris;
			}

	/**
	 * Normalizza le espressioni contenute nella dichiarazione di collegamento architetturale,
	 * fornita come parametro.
	 *
	 * @param ad
	 * @return la dichiarazione di collegamento architetturale normalizzata.
	 * @throws NormalizeException
	 */
	public AttacDecl normalizeAttacDecl(AttacDecl ad) throws NormalizeException {
	AttacDecl clone = ad.copy();
	// si preleva lo scope della dichiarazione di collegamento
	TreeMap<String, ValueIdentExpr> sco = scope.getScopeAttacDecl(clone);
	// si valuta le eventuali espressioni di selezione
	// di istanze
	if (clone.getFromSelector() != null)
	    {
	    Object o = scope.getValutazione().valuta(clone.getFromSelector(), sco);
	    if (o instanceof Expression)
	        clone.setFromSelector((Expression)o);
	    else
	        clone.setFromSelector(Valutazione.obtainExp(o));
	    // si concatena al nome dell'AEI di partenza
	    // del collegamento
	    // la valutazione dell'espressione di selezione
	    clone.setOutputAei(concatInd(clone.getOutputAei(), clone.getFromSelector()));
	    clone.setFromSelector(null);
	    }
	if (clone.getToSelector() != null)
	    {
	    Object o = scope.getValutazione().valuta(clone.getToSelector(), sco);
	    if (o instanceof Expression)
	        clone.setToSelector((Expression)o);
	    else
	        clone.setToSelector(Valutazione.obtainExp(o));
	    // si concatena, al nome dell'AEI di arrivo
	    // del collegamento,
	    // la valutazione dell'espressione di selezione
	    clone.setInputAei(concatInd(clone.getInputAei(), clone.getToSelector()));
	    clone.setToSelector(null);
	    }
	return clone;
	}

	/**
	 * Normalizza le espressioni, contenute nell'intestazione di un'equazione comportamentale, fornita
	 * come parametro. Di solito, il metodo viene utilizzato solo per la prima equazione comportamentale,
	 * presente in un ElemType.
	 *
	 * @param i
	 * @param et
	 * @return l'intestazione del comportamento normalizzata.
	 * @throws NormalizeException
	 */
	public Header normalizeBehavHeader(Header i, ElemType et)
			throws NormalizeException {
			Header clone = i.copy();
			// si preleva lo scope per la valutazione dell'intestazione
			TreeMap<String, ValueIdentExpr> sco = scope.getScopeBehavHeader(clone,et);
			// si prelevano le dichiarazioni dei parametri da normalizzare
			ParamDeclaration[] dps = clone.getParameters();
			if (dps == null) return clone;
			if (dps instanceof ConstInit[])
			    throw new NormalizeException("Architectural type header normalization");
			if (dps instanceof Const[])
			    throw new NormalizeException("Architectural elem type header normalization");
			for (int j = 0; j < dps.length; j++)
			    {
				if (dps[j] != null)
					{
				    if (dps[j] instanceof VarInit)
				        {
				        Object o =scope.getValutazione().valuta(((VarInit)dps[j]).getExpr(), sco);
				        if (o instanceof Expression)
				            ((VarInit)dps[j]).setExpr((Expression)o);
				        else
				            ((VarInit)dps[j]).setExpr(Valutazione.obtainExp(o));
				        }
				    // si normalizza un'eventuale dichiarazione
				    // di un intervallo di interi
				    dps[j] = normalizeRange(dps[j], sco);
					}
			    }
			return clone;
			}

	/**
	 * Normalizza le espressioni, contenute nella dichiarazione di interazione architetturale, fornita
	 * come parametro.
	 *
	 * @param id
	 * @return la dichiarazione di interazione normalizzata.
	 * @throws NormalizeException
	 */
	public InteractionDecl normalizeInteractionDecl(InteractionDecl id)
			throws NormalizeException {
			InteractionDecl clone = id.copy();
			// si preleva lo scope per la valutazione della dichiarazione
			// di interazione architetturale
			TreeMap<String, ValueIdentExpr> sco = scope.getScopeInteractionDecl(clone);
			// si normalizza l'eventuale espressione di selezione
			// di istanza
			if (clone.getSelector() != null)
			    {
			    Object o = scope.getValutazione().valuta(clone.getSelector(), sco);
			    if (o instanceof Expression)
			        clone.setSelector((Expression)o);
			    else
			        clone.setSelector(Valutazione.obtainExp(o));
			    // si concatena al nome dell'AEI, al quale
			    // appartiene l'interazione,
			    // la valutazione dell'espressione di selezione
			    clone.setAei(concatInd(clone.getAei(), clone.getSelector()));
			    clone.setSelector(null);
			    }
			return clone;
			}

	/**
	 * Normalizza le espressioni, contenute nel termine di processo, fornito come parametro.
	 *
	 * @param pt
	 * @return il termine di processo normalizzato.
	 * @throws NormalizeException
	 */
	public ProcessTerm normalizeProcessTerm(BehavEquation be, ElemType et)
			throws NormalizeException {
			ProcessTerm clone = be.getTermineProcesso().copy();
			// si preleva lo scope per la valutazione del termine
			// di processo
			TreeMap<String, ValueIdentExpr> sco = scope.getScopeProcessTerm(be,et);
			// si normalizza l'eventuale condizione del termine
			// di processo
			if (clone.getCondition() != null)
			    {
			    Object o = scope.getValutazione().valuta(clone.getCondition(), sco);
			    if (o instanceof Expression)
			        clone.setCondition((Expression)o);
			    else
			        clone.setCondition(Valutazione.obtainExp(o));
			    }
			if (clone instanceof ActionProcess)
			    {
			    // si normalizza l'azione del processo
			    Action a = ((ActionProcess)clone).getAzione();
			    ActionType at = a.getType();
			    // si normalizzano le eventuali espressioni di output
			    if (at instanceof ActionOutput)
			        {
			        for (int i = 0; i < ((ActionOutput)at).getOutputExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((ActionOutput)at).getOutputExprs()[i], sco);
			            if (o instanceof Expression)
			                ((ActionOutput)at).getOutputExprs()[i] = (Expression)o;
			            else
			                ((ActionOutput)at).getOutputExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    ActionRate ar = a.getRate();
			    if (ar instanceof RateExp)
			        {
			        Object o = scope.getValutazione().valuta(((RateExp)ar).getExpr(), sco);
			        if (o instanceof Expression)
			            ((RateExp)ar).setExpr((Expression)o);
			        else
			            ((RateExp)ar).setExpr(Valutazione.obtainExp(o));
			        }
			    if (ar instanceof Rate_)
			        {
			        Object o1 = scope.getValutazione().valuta(((Rate_)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((Rate_)ar).setWeight((Expression)o1);
			        else
			            ((Rate_)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((Rate_)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((Rate_)ar).setPrio((Expression)o2);
			        else
			            {
			            ((Rate_)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                try {
			                    throw new InterezzaException("The action priority "+at.getName()+
			                        " is not a integer");
			                    }
			                catch (InterezzaException e)
			                    {
			                    throw new NormalizeException(e);
			                    }
			            }
			        }
			    if (ar instanceof RateInf)
			        {
			        Object o1 = scope.getValutazione().valuta(((RateInf)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((RateInf)ar).setWeight((Expression)o1);
			        else
			            ((RateInf)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((RateInf)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((RateInf)ar).setPrio((Expression)o2);
			        else
			            {
			            ((RateInf)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                try {
			                    throw new InterezzaException("The action priority "+at.getName()+
			                        " is not a integer");
			                    }
			                catch (InterezzaException e)
			                    {
			                    throw new NormalizeException(e);
			                    }
			            }
			        }
			    try {
			        ((ActionProcess)clone).setProcesso(normalizePartialProcessTerm(((ActionProcess)clone).getProcesso(), sco));
			        }
			    catch (InterezzaException e)
			        {
			        throw new NormalizeException(e);
			        }
			    }
			if (clone instanceof BehavProcess)
			    {
			    // si normalizzano i parametri attuali della chiamata
			    // di comportamento
			    if (((BehavProcess)clone).getExprs() != null)
			        {
			        for (int i = 0; i < ((BehavProcess)clone).getExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((BehavProcess)clone).getExprs()[i], sco);
			            if (o instanceof Expression)
			                ((BehavProcess)clone).getExprs()[i] = (Expression)o;
			            else
			                ((BehavProcess)clone).getExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    }
			if (clone instanceof ChoiceProcess)
			    {
			    // si normalizza ogni processo alternativo
			    for (int i = 0; i < ((ChoiceProcess)clone).getProcesses().length; i++)
			        {
			        try {
			            ((ChoiceProcess)clone).getProcesses()[i] =
			                normalizePartialProcessTerm(((ChoiceProcess)clone).getProcesses()[i],sco);
			            }
			        catch (InterezzaException e)
			            {
			            throw new NormalizeException(e);
			            }
			        }
			    }
			return clone;
			}

	/**
	 * Normalizza le espressioni, presenti nel tipo di elemento architetturale, fornito come parametro.
	 *
	 * @param et
	 * @return il tipo di elemento architetturale normalizzato.
	 * @throws NormalizeException
	 */
	public ElemType normalizeElemType(ElemType et) throws NormalizeException {
	ElemType clone = et.copy();
	// si normalizzano i comportamenti del tipo di elemento
	// architetturale
	BehavEquation[] bes = clone.getBehavior().getBehaviors();
	for (int i = 0; i < bes.length; i++)
	    {
	    // si normalizza l'intestazione del comportamento
	    bes[i].setBehavHeader(normalizeBehavHeader(bes[i].getBehavHeader(),et));
	    // si normalizza il termine di processo del comportamento
	    bes[i].setTermineProcesso(normalizeProcessTerm(bes[i],clone));
	    }
	return clone;
	}

	/**
	 * Normalizza le dichiarazioni di istanze di elementi,
	 * presenti nel tipo architetturale.
	 *
	 * @return le dichiarazioni di istanze normalizzate.
	 * @throws NormalizeException
	 */
	public ArchiElemInstances normalizeInstances() throws NormalizeException {
	ArrayList<AEIdecl> al = new ArrayList<AEIdecl>(0);
	// si prelevano le dichiarazioni di istanze di
	// elementi architetturali
	ArchiElemInstances aei = this.scope.getAt().getTopologia().getAEIs();
	ArchiElemInstances clone = aei.copy();
	// si preleva l'array delle dichiarazioni
	AEIdecl[] aeids = clone.getAEIdeclSeq();
	// si normalizzano opportunamente tutte le dichiarazioni
	for (int i = 0; i < aeids.length; i++)
	    {
	    if (aeids[i] instanceof AEIdeclInd)
	        {
	        AEIdecl[] risnorm =
	            normalizeAEIdeclInd((AEIdeclInd)aeids[i]);
	        al.addAll(new CopyOnWriteArrayList<AEIdecl>(risnorm));
	        }
	    else
	        {
	        aeids[i] = normalizeAEIdecl(aeids[i]);
	        al.add(aeids[i]);
	        }
	    }
	// si assegnano le nuove dichiarazioni di istanze normalizzate
	// a clone
	al.trimToSize();
	AEIdecl[] ris = new AEIdecl[al.size()];
	al.toArray(ris);
	clone.setAEIdeclSeq(ris);
	return clone;
	}

	/**
	 * Normalizza le dichiarazioni di collegamenti,
	 * presenti nel tipo architetturale.
	 *
	 * @return la dichiarazione di collegamenti normalizzata.
	 * @throws NormalizeException
	 */
	public ArchiAttachments normalizeAttacs() throws NormalizeException {
	ArrayList<AttacDecl> al = null;
	// si prelevano le dichiarazioni di collegamenti
	// architetturali
	// risaa Ë il risultato del metodo
	ArchiAttachments risaa = null;
	ArchiAttachments aa = this.scope.getAt().getTopologia().getAttachments();
	ArchiAttachments clone = aa.copy();
	// si preleva l'array delle dichiarazioni
	AttacDecl[] ads = clone.getAttachments();
	// si normalizzano opportunamente tutte le dichiarazioni
	if (ads != null)
	    {
	    al = new ArrayList<AttacDecl>(0);
	    for (int i = 0; i < ads.length; i++)
	        {
	        if (ads[i] instanceof AttacDeclInd)
	            {
	            AttacDecl[] risnorm =
	                normalizeAttacDeclInd((AttacDeclInd)ads[i]);
	            al.addAll(new CopyOnWriteArrayList<AttacDecl>(risnorm));
	            }
	        else
	            {
	            ads[i] = normalizeAttacDecl(ads[i]);
	            al.add(ads[i]);
	            }
	        }
	    // si assegnano le nuove dichiarazioni di collegamenti normalizzate
	    // alla tipologia architetturale
	    al.trimToSize();
	    AttacDecl[] ris = new AttacDecl[al.size()];
	    al.toArray(ris);
	    risaa = new ArchiAttachments(ris);
	    }
	else
	    {
	    risaa = new ArchiAttachments(ads);
	    }
	return risaa;
	}

	/**
	 * Normalizza le dichiarazioni di interazioni,
	 * presenti nel tipo architetturale.
	 *
	 * @return le dichiarazioni di interazioni normalizzate
	 * @throws NormalizeException
	 */
	public ArchiInteractions normalizeInteractions() throws NormalizeException {
	ArrayList<InteractionDecl> al = null;
	// si prelevano le dichiarazioni di interazioni
	// architetturali
	// risai Ë il risultato del metodo
	ArchiInteractions risai = null;
	ArchiInteractions ai = this.scope.getAt().getTopologia().getArchiInteractions();
	ArchiInteractions clone = ai.copy();
	// si preleva l'array delle dichiarazioni
	InteractionDecl[] ids = clone.getInteractions();
	// si normalizzano opportunamente tutte le dichiarazioni
	if (ids != null)
	    {
	    al = new ArrayList<InteractionDecl>(0);
	    for (int i = 0; i < ids.length; i++)
	        {
	        if (ids[i] instanceof InteractionDeclInd)
	            {
	            InteractionDecl[] risnorm =
	                normalizeInteractionDeclInd((InteractionDeclInd)ids[i]);
	            al.addAll(new CopyOnWriteArrayList<InteractionDecl>(risnorm));
	            }
	        else
	            {
	            ids[i] = normalizeInteractionDecl(ids[i]);
	            al.add(ids[i]);
	            }
	        }
	    // si assegnano le nuove dichiarazioni di interazioni normalizzate
	    // alla tipologia architetturale
	    al.trimToSize();
	    InteractionDecl[] ris = new InteractionDecl[al.size()];
	    al.toArray(ris);
	    risai = new ArchiInteractions(ris);
	    }
	else
	    {
	    risai = new ArchiInteractions(ids);
	    }
	return risai;
	}

	/**
	 * Normalizza le espressioni, contenute nella dichiarazione
	 * di un intervallo di interi.
	 *
	 * @param dp
	 * @param tma
	 * @return la dichiarazione di parametro normalizzata.
	 * @throws NormalizeException
	 */
	private ParamDeclaration normalizeRange(ParamDeclaration dp, TreeMap<String, ValueIdentExpr> tma)
			throws NormalizeException {
			ParamDeclaration clone = dp.copy();
			if (clone instanceof Const)
			    {
			    DataType td = ((Const)clone).getType();
			    if (td instanceof IntegerRangeType)
			        {
			        Object o1 = scope.getValutazione().valuta(((IntegerRangeType)td).getBeginningInt(), tma);
			        if (o1 instanceof Expression)
			            ((IntegerRangeType)td).setBeginningInt((Expression)o1);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setBeginningInt(interezza.returnIntero(Valutazione.obtainExp(o1)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        Object o2 = scope.getValutazione().valuta(((IntegerRangeType)td).getEndingInt(), tma);
			        if (o2 instanceof Expression)
			            ((IntegerRangeType)td).setEndingInt((Expression)o2);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setEndingInt(interezza.returnIntero(Valutazione.obtainExp(o2)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        }
			    }
			if (clone instanceof ConstInit)
			    {
			    DataType td = ((ConstInit)clone).getType();
			    if (td instanceof IntegerRangeType)
			        {
			        Object o1 = scope.getValutazione().valuta(((IntegerRangeType)td).getBeginningInt(), tma);
			        if (o1 instanceof Expression)
			            ((IntegerRangeType)td).setBeginningInt((Expression)o1);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setBeginningInt(interezza.returnIntero(Valutazione.obtainExp(o1)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        Object o2 = scope.getValutazione().valuta(((IntegerRangeType)td).getEndingInt(), tma);
			        if (o2 instanceof Expression)
			            ((IntegerRangeType)td).setEndingInt((Expression)o2);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setEndingInt(interezza.returnIntero(Valutazione.obtainExp(o2)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        }
			    }
			if (clone instanceof Local)
			    {
			    DataType td = ((Local)clone).getType();
			    if (td instanceof IntegerRangeType)
			        {
			        Object o1 = scope.getValutazione().valuta(((IntegerRangeType)td).getBeginningInt(), tma);
			        if (o1 instanceof Expression)
			            ((IntegerRangeType)td).setBeginningInt((Expression)o1);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setBeginningInt(interezza.returnIntero(Valutazione.obtainExp(o1)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        Object o2 = scope.getValutazione().valuta(((IntegerRangeType)td).getEndingInt(), tma);
			        if (o2 instanceof Expression)
			            ((IntegerRangeType)td).setEndingInt((Expression)o2);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setEndingInt(interezza.returnIntero(Valutazione.obtainExp(o2)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        }
			    }
			if (clone instanceof VariableDeclaration)
			    {
			    DataType td = ((VariableDeclaration)clone).getType();
			    if (td instanceof IntegerRangeType)
			        {
			        Object o1 = scope.getValutazione().valuta(((IntegerRangeType)td).getBeginningInt(), tma);
			        if (o1 instanceof Expression)
			            ((IntegerRangeType)td).setBeginningInt((Expression)o1);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setBeginningInt(interezza.returnIntero(Valutazione.obtainExp(o1)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        Object o2 = scope.getValutazione().valuta(((IntegerRangeType)td).getEndingInt(), tma);
			        if (o2 instanceof Expression)
			            ((IntegerRangeType)td).setEndingInt((Expression)o2);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setEndingInt(interezza.returnIntero(Valutazione.obtainExp(o2)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        }
			    }
			if (clone instanceof VarInit)
			    {
			    DataType td = ((VarInit)clone).getType();
			    if (td instanceof IntegerRangeType)
			        {
			        Object o1 = scope.getValutazione().valuta(((IntegerRangeType)td).getBeginningInt(), tma);
			        if (o1 instanceof Expression)
			            ((IntegerRangeType)td).setBeginningInt((Expression)o1);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setBeginningInt(interezza.returnIntero(Valutazione.obtainExp(o1)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        Object o2 = scope.getValutazione().valuta(((IntegerRangeType)td).getEndingInt(), tma);
			        if (o2 instanceof Expression)
			            ((IntegerRangeType)td).setEndingInt((Expression)o2);
			        else
			            {
			            Interezza interezza = new Interezza();
			            try {
			                ((IntegerRangeType)td).setEndingInt(interezza.returnIntero(Valutazione.obtainExp(o2)));
			                }
			            catch (InterezzaException e)
			                {
			                throw new NormalizeException(e);
			                }
			            }
			        }
			    }
			return clone;
			}

	/**
	 * Normalizza una parte del termine di processo di
	 * un'equazione comportamentale, secondo lo scope passato
	 * come parametro.
	 *
	 * @param pt
	 * @param sco
	 * @return il termine di processo normalizzato.
	 * @throws NormalizeException
	 * @throws InterezzaException
	 */
	private ProcessTerm normalizePartialProcessTerm(ProcessTerm pt, TreeMap<String,ValueIdentExpr> sco)
			throws NormalizeException, InterezzaException {
			ProcessTerm clone = pt.copy();
			// si normalizza l'eventuale condizione del termine
			// di processo
			if (clone.getCondition() != null)
			    {
			    Object o = scope.getValutazione().valuta(clone.getCondition(), sco);
			    if (o instanceof Expression)
			        clone.setCondition((Expression)o);
			    else
			        clone.setCondition(Valutazione.obtainExp(o));
			    }
			if (clone instanceof ActionProcess)
			    {
			    // si normalizza l'azione del processo
			    Action a = ((ActionProcess)clone).getAzione();
			    ActionType at = a.getType();
			    // si normalizzano le eventuali espressioni di output
			    if (at instanceof ActionOutput)
			        {
			        for (int i = 0; i < ((ActionOutput)at).getOutputExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((ActionOutput)at).getOutputExprs()[i], sco);
			            if (o instanceof Expression)
			                ((ActionOutput)at).getOutputExprs()[i] = (Expression)o;
			            else
			                ((ActionOutput)at).getOutputExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    ActionRate ar = a.getRate();
			    if (ar instanceof RateExp)
			        {
			        Object o = scope.getValutazione().valuta(((RateExp)ar).getExpr(), sco);
			        if (o instanceof Expression)
			            ((RateExp)ar).setExpr((Expression)o);
			        else
			            ((RateExp)ar).setExpr(Valutazione.obtainExp(o));
			        }
			    if (ar instanceof Rate_)
			        {
			        Object o1 = scope.getValutazione().valuta(((Rate_)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((Rate_)ar).setWeight((Expression)o1);
			        else
			            ((Rate_)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((Rate_)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((Rate_)ar).setPrio((Expression)o2);
			        else
			            {
			            ((Rate_)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                throw new InterezzaException("The action priority "+at.getName()+
			                    " is not a integer");
			            }
			        }
			    if (ar instanceof RateInf)
			        {
			        Object o1 = scope.getValutazione().valuta(((RateInf)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((RateInf)ar).setWeight((Expression)o1);
			        else
			            ((RateInf)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((RateInf)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((RateInf)ar).setPrio((Expression)o2);
			        else
			            {
			            ((RateInf)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                throw new InterezzaException("The action priority "+at.getName()+
			                    " is not a integer");
			            }
			        }
			    ((ActionProcess)clone).setProcesso(normalizePartialProcessTerm(
			    		((ActionProcess)clone).getProcesso(),sco));
			    }
			if (clone instanceof BehavProcess)
			    {
			    // si normalizzano i parametri attuali della chiamata
			    // di comportamento
			    if (((BehavProcess)clone).getExprs() != null)
			        {
			        for (int i = 0; i < ((BehavProcess)clone).getExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((BehavProcess)clone).getExprs()[i], sco);
			            if (o instanceof Expression)
			                ((BehavProcess)clone).getExprs()[i] = (Expression)o;
			            else
			                ((BehavProcess)clone).getExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    }
			if (clone instanceof ChoiceProcess)
			    {
			    // si normalizza ogni processo alternativo
			    for (int i = 0; i < ((ChoiceProcess)clone).getProcesses().length; i++)
			        {
			        ((ChoiceProcess)clone).getProcesses()[i] =
			            normalizePartialProcessTerm(((ChoiceProcess)clone).getProcesses()[i],sco);
			        }
			    }
			return clone;
			}

	/**
	 * Normalizza in profondit‡ un tipo di elemento architetturale, a partire
	 * dalla dichiarazione di una sua istanza. Oltre all'intestazione dei comportamenti, 
	 * viene normalizzato anche il termine di processo.
	 *
	 * @param aeid
	 * @return l'elemento architetturale normalizzato
	 * @throws NormalizeException
	 */
	public ElemType normalizeInDepthElemTypeFromAEI(AEIdecl aeid)
			throws NormalizeException {
			// si preleva il tipo di elemento architetturale a partire da aeid
			ElemType et = Find.getElemTypeFromAei(aeid.getName(), this.scope.getAt());
			if (et == null) throw new NormalizeException(aeid.getName()+" have not relative architectural element type");
			ElemType clone = et.copy();
			// si normalizzano i comportamenti del tipo di elemento
			// architetturale
			BehavEquation[] bes = clone.getBehavior().getBehaviors();
			for (int i = 0; i < bes.length; i++)
			    {
			    BehavEquation beact = bes[i].copy();
			    // si normalizza l'intestazione del comportamento
			    bes[i].setBehavHeader(normalizeBehavHeaderFromAEI(aeid,beact.getBehavHeader()));
			    // si normalizza il termine di processo del comportamento
			    bes[i].setTermineProcesso(normalizeProcessTermFromAEIinDepth(aeid,beact));
			    }
			return clone;
			}

	/**
	 * Normalizza un tipo di elemento architetturale, a partire
	 * dalla dichiarazione di una sua istanza. Viene normalizzato il termine di processo
	 * solo con lo scope relativo all'intestazione del tipo di elemento architetturale,
	 * cioË non si considerano gli identificatori presenti nelle intestazioni
	 * delle equazioni comportamentali.
	 *
	 * @param aeid
	 * @return l'elemento architetturale normalizzato
	 * @throws NormalizeException
	 */
	public ElemType normalizeElemTypeFromAEI(AEIdecl aeid)
			throws NormalizeException {
			// si preleva il tipo di elemento architetturale a partire da aeid
			ElemType et = Find.getElemTypeFromAei(aeid.getName(), this.scope.getAt());
			if (et == null) throw new NormalizeException(aeid.getName()+" have not relative " +
					"architectural element type");
			ElemType clone = et.copy();
			// si normalizzano i comportamenti del tipo di elemento
			// architetturale
			BehavEquation[] bes = clone.getBehavior().getBehaviors();
			for (int i = 0; i < bes.length; i++)
			    {
			    BehavEquation beact = bes[i].copy();
			    // si normalizza l'intestazione del comportamento
			    bes[i].setBehavHeader(normalizeBehavHeaderFromAEI(aeid,beact.getBehavHeader()));
			    // si normalizza il termine di processo del comportamento
			    bes[i].setTermineProcesso(normalizeProcessTermFromAEI(aeid,beact));
			    }
			return clone;
			}

	/**
	 * Normalizza l'intestazione di un comportamento di
	 * un elemento architetturale, a partire da una dichiarazione di una sua istanza,
	 * e dall'intestazione di una sua equazione comportamentale.
	 *
	 * @param aeid
	 * @param i - intestazione da normalizzare
	 * @return l'intestazione normalizzata
	 * @throws NormalizeException
	 */
	public Header normalizeBehavHeaderFromAEI(AEIdecl aeid, Header i)
			throws NormalizeException {
			Header clone = i.copy();
			// si preleva lo scope per la valutazione dell'intestazione
			TreeMap<String, ValueIdentExpr> sco = scope.getScopeBehavHeaderFromAEI(aeid);
			// si prelevano le dichiarazioni dei parametri da normalizzare
			ParamDeclaration[] dps = clone.getParameters();
			if (dps == null) return clone;
			if (dps instanceof ConstInit[])
			    throw new NormalizeException("Architectural type header normalization");
			if (dps instanceof Const[])
			    throw new NormalizeException("Architectural element type header normalization");
			for (int j = 0; j < dps.length; j++)
			    {
				if (dps[j] != null)
					{
				    // si normalizzano le eventuali espressioni di inizializzazione
				    if (dps[j] instanceof VarInit)
				        {
				        Object o =scope.getValutazione().valuta(((VarInit)dps[j]).getExpr(), sco);
				        if (o instanceof Expression)
				            ((VarInit)dps[j]).setExpr((Expression)o);
				        else
				            ((VarInit)dps[j]).setExpr(Valutazione.obtainExp(o));
				        }
				    // si normalizza un'eventuale dichiarazione
				    // di un intervallo di interi
				    dps[j] = normalizeRange(dps[j], sco);
					}
			    }
			return clone;
			}

	/**
	 * Normalizza un termine di processo di un tipo di
	 * elemento architetturale, a partire dalla dichiarazione
	 * di un'istanza dell'elemento e dall'equazione comportamentale, a cui il termine di processo
	 * appartiene. Oltre all'intestazione, viene normalizzato anche il termine di processo.
	 *
	 * @param aeid
	 * @return il termine di processo normalizzato.
	 * @throws NormalizeException
	 */
	public ProcessTerm normalizeProcessTermFromAEIinDepth(AEIdecl aeid, BehavEquation be)
			throws NormalizeException {
			AEIdecl clone = aeid.copy();
			BehavEquation clonebe = be.copy();
			// si preleva lo scope per la valutazione del termine
			// di processo
			TreeMap<String, ValueIdentExpr> sco = scope.getScopeProcessTermFromAEIinDepth(clone, clonebe);
			// si normalizza l'eventuale condizione del termine
			// di processo
			ProcessTerm pt = clonebe.getTermineProcesso();
			if (pt.getCondition() != null)
			    {
			    Object o = scope.getValutazione().valuta(pt.getCondition(), sco);
			    if (o instanceof Expression)
			        clonebe.getTermineProcesso().setCondition((Expression)o);
			    else
			        clonebe.getTermineProcesso().setCondition(Valutazione.obtainExp(o));
			    }
			if (pt instanceof ActionProcess)
			    {
			    // si normalizza l'azione del processo
			    Action a = ((ActionProcess)pt).getAzione();
			    ActionType at = a.getType();
			    // si normalizzano le eventuali espressioni di output
			    if (at instanceof ActionOutput)
			        {
			        for (int i = 0; i < ((ActionOutput)at).getOutputExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((ActionOutput)at).getOutputExprs()[i], sco);
			            if (o instanceof Expression)
			                ((ActionOutput)at).getOutputExprs()[i] = (Expression)o;
			            else
			                ((ActionOutput)at).getOutputExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    ActionRate ar = a.getRate();
			    if (ar instanceof RateExp)
			        {
			        Object o = scope.getValutazione().valuta(((RateExp)ar).getExpr(), sco);
			        if (o instanceof Expression)
			            ((RateExp)ar).setExpr((Expression)o);
			        else
			            ((RateExp)ar).setExpr(Valutazione.obtainExp(o));
			        }
			    if (ar instanceof Rate_)
			        {
			        Object o1 = scope.getValutazione().valuta(((Rate_)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((Rate_)ar).setWeight((Expression)o1);
			        else
			            ((Rate_)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((Rate_)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((Rate_)ar).setPrio((Expression)o2);
			        else
			            {
			            ((Rate_)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                try {
			                    throw new InterezzaException("The action priority "+at.getName()+
			                        " is not a integer");
			                    }
			                catch (InterezzaException e)
			                    {
			                    throw new NormalizeException(e);
			                    }
			            }
			        }
			    if (ar instanceof RateInf)
			        {
			        Object o1 = scope.getValutazione().valuta(((RateInf)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((RateInf)ar).setWeight((Expression)o1);
			        else
			            ((RateInf)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((RateInf)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((RateInf)ar).setPrio((Expression)o2);
			        else
			            {
			            ((RateInf)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                try {
			                    throw new InterezzaException("The action priority "+at.getName()+
			                        " is not a integer");
			                    }
			                catch (InterezzaException e)
			                    {
			                    throw new NormalizeException(e);
			                    }
			            }
			        }
			    try {
			        ((ActionProcess)pt).setProcesso(normalizePartialProcessTerm(((ActionProcess)pt).getProcesso(), sco));
			        }
			    catch (InterezzaException e)
			        {
			        throw new NormalizeException(e);
			        }
			    }
			if (pt instanceof BehavProcess)
			    {
			    // si normalizzano i parametri attuali della chiamata
			    // di comportamento
			    if (((BehavProcess)pt).getExprs() != null)
			        {
			        for (int i = 0; i < ((BehavProcess)pt).getExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((BehavProcess)pt).getExprs()[i], sco);
			            if (o instanceof Expression)
			                ((BehavProcess)pt).getExprs()[i] = (Expression)o;
			            else
			                ((BehavProcess)pt).getExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    }
			if (pt instanceof ChoiceProcess)
			    {
			    // si normalizza ogni processo alternativo
			    for (int i = 0; i < ((ChoiceProcess)pt).getProcesses().length; i++)
			        {
			        try {
			            ((ChoiceProcess)pt).getProcesses()[i] =
			            normalizePartialProcessTerm(((ChoiceProcess)pt).getProcesses()[i],sco);
			            }
			        catch (InterezzaException e)
			            {
			            throw new NormalizeException(e);
			            }
			        }
			    }
			return pt;
			}

	/**
	 * Normalizza un termine di processo di un tipo di
	 * elemento architetturale, a partire dalla dichiarazione
	 * di un'istanza dell'elemento e dall'equazione comportamentale, a cui il termine di processo
	 * appartiene. Oltre all'intestazione, viene normalizzato anche il termine di processo.
	 * Lo scope utilizzato per la valutazione delle espressioni non tiene in considerazione degli
	 * identificatori dichiarati nell'intestazione dell'equazione comportamentale.
	 *
	 * @param aeid
	 * @return il termine di processo normalizzato.
	 * @throws NormalizeException
	 */
	public ProcessTerm normalizeProcessTermFromAEI(AEIdecl aeid, BehavEquation be)
			throws NormalizeException {
			AEIdecl clone = aeid.copy();
			BehavEquation clonebe = be.copy();
			// si preleva lo scope per la valutazione del termine
			// di processo
			TreeMap<String, ValueIdentExpr> sco = scope.getScopeProcessTermFromAEI(clone, clonebe);
			// si normalizza l'eventuale condizione del termine
			// di processo
			ProcessTerm pt = clonebe.getTermineProcesso();
			if (pt.getCondition() != null)
			    {
			    Object o = scope.getValutazione().valuta(pt.getCondition(), sco);
			    if (o instanceof Expression)
			        clonebe.getTermineProcesso().setCondition((Expression)o);
			    else
			        clonebe.getTermineProcesso().setCondition(Valutazione.obtainExp(o));
			    }
			if (pt instanceof ActionProcess)
			    {
			    // si normalizza l'azione del processo
			    Action a = ((ActionProcess)pt).getAzione();
			    ActionType at = a.getType();
			    // si normalizzano le eventuali espressioni di output
			    if (at instanceof ActionOutput)
			        {
			        for (int i = 0; i < ((ActionOutput)at).getOutputExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((ActionOutput)at).getOutputExprs()[i], sco);
			            if (o instanceof Expression)
			                ((ActionOutput)at).getOutputExprs()[i] = (Expression)o;
			            else
			                ((ActionOutput)at).getOutputExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    ActionRate ar = a.getRate();
			    if (ar instanceof RateExp)
			        {
			        Object o = scope.getValutazione().valuta(((RateExp)ar).getExpr(), sco);
			        if (o instanceof Expression)
			            ((RateExp)ar).setExpr((Expression)o);
			        else
			            ((RateExp)ar).setExpr(Valutazione.obtainExp(o));
			        }
			    if (ar instanceof Rate_)
			        {
			        Object o1 = scope.getValutazione().valuta(((Rate_)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((Rate_)ar).setWeight((Expression)o1);
			        else
			            ((Rate_)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((Rate_)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((Rate_)ar).setPrio((Expression)o2);
			        else
			            {
			            ((Rate_)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                try {
			                    throw new InterezzaException("The action priority "+at.getName()+
			                        " is not a integer");
			                    }
			                catch (InterezzaException e)
			                    {
			                    throw new NormalizeException(e);
			                    }
			            }
			        }
			    if (ar instanceof RateInf)
			        {
			        Object o1 = scope.getValutazione().valuta(((RateInf)ar).getWeight(), sco);
			        if (o1 instanceof Expression)
			            ((RateInf)ar).setWeight((Expression)o1);
			        else
			            ((RateInf)ar).setWeight(Valutazione.obtainExp(o1));
			        Object o2 = scope.getValutazione().valuta(((RateInf)ar).getPrio(), sco);
			        if (o2 instanceof Expression)
			            ((RateInf)ar).setPrio((Expression)o2);
			        else
			            {
			            ((RateInf)ar).setPrio(Valutazione.obtainExp(o2));
			            // verifico l'interezza del tasso
			            Interezza interezza = new Interezza();
			            if (!interezza.isPrioritaAzioneIntera(ar))
			                try {
			                    throw new InterezzaException("The action priority "+at.getName()+
			                        " is not a integer");
			                    }
			                catch (InterezzaException e)
			                    {
			                    throw new NormalizeException(e);
			                    }
			            }
			        }
			    try {
			        ((ActionProcess)pt).setProcesso(normalizePartialProcessTerm(((ActionProcess)pt).getProcesso(), sco));
			        }
			    catch (InterezzaException e)
			        {
			        throw new NormalizeException(e);
			        }
			    }
			if (pt instanceof BehavProcess)
			    {
			    // si normalizzano i parametri attuali della chiamata
			    // di comportamento
			    if (((BehavProcess)pt).getExprs() != null)
			        {
			        for (int i = 0; i < ((BehavProcess)pt).getExprs().length; i++)
			            {
			            Object o = scope.getValutazione().valuta(((BehavProcess)pt).getExprs()[i], sco);
			            if (o instanceof Expression)
			                ((BehavProcess)pt).getExprs()[i] = (Expression)o;
			            else
			                ((BehavProcess)pt).getExprs()[i] = Valutazione.obtainExp(o);
			            }
			        }
			    }
			if (pt instanceof ChoiceProcess)
			    {
			    // si normalizza ogni processo alternativo
			    for (int i = 0; i < ((ChoiceProcess)pt).getProcesses().length; i++)
			        {
			        try {
			            ((ChoiceProcess)pt).getProcesses()[i] =
			            normalizePartialProcessTerm(((ChoiceProcess)pt).getProcesses()[i],sco);
			            }
			        catch (InterezzaException e)
			            {
			            throw new NormalizeException(e);
			            }
			        }
			    }
			return pt;
			}

    /**
     * Concatena nome con il valore di e, che puÚ essere un
     * Real o un Integer.
     *
     * @param nome
     * @param e
     * @return
     * @throws NormalizeException
     */
    String concatInd(String nome, Expression e)
        throws NormalizeException
        {
        String ris = null;
        if (e instanceof Integer)
            {
            java.lang.Integer i = new java.lang.Integer(((Integer)e).getValore());
            ris = nome + i.toString();
            }
        else if (e instanceof Real)
            {
            Double d = new Double(((Real)e).getValore());
            java.lang.Integer i = new java.lang.Integer(d.intValue());
            ris = nome + i.toString();
            }
        else throw new NormalizeException("The index expression is not a integer");
        return ris;
        }
	}