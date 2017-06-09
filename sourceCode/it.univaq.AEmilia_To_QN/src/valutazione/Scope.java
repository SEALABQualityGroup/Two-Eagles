package valutazione;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import specificheAEmilia.AEIdecl;
import specificheAEmilia.AEIdeclInd;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;
import specificheAEmilia.AttacDeclInd;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.ConstInit;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.IntegerType;
import specificheAEmilia.InteractionDecl;
import specificheAEmilia.InteractionDeclInd;
import specificheAEmilia.IntegerRangeType;
import specificheAEmilia.Header;
import specificheAEmilia.Local;
import specificheAEmilia.PrioType;
import specificheAEmilia.RateType;
import specificheAEmilia.RealType;
import specificheAEmilia.DataType;
import specificheAEmilia.VariableDeclaration;
import specificheAEmilia.VarInit;
import specificheAEmilia.WeightType;

/**
 * Gli Scope possono essere i seguenti:<br/><br/>
 *
 * Scope ArchiType - Le espressioni presenti nell'intestazione del tipo
 * architetturale devono essere libere da identificatori non dichiarati
 * cioè, i soli identificatori che si possono presentare nelle espressioni
 * di assegnamento sono quelli dei parametri formali costanti giÃ 
 * dichiarati.<br/><br/>
 *
 * Scope Header di un BehavEquation - Le espressioni presenti
 * nell'intestazione della prima equazione comportamentale devono essere
 * libere da identificatori non dichiarati. I soli identificatori che si
 * possono presentare nelle espressioni di assegnamento sono quelli dei
 * parametri formali costanti dichiarati nell'intestazione dell'AET
 * (oggetto Header associato ad un ElemType).<br/><br/>
 *
 * Scope ProcessTerm di un BehavEquation - Ogni espressione presente
 * all'interno della definizione di un comportamento può contenere solo
 * identificatori dichiarati nell'intestazione dell'AET relativo o
 * dichiarati nel comportamento (Header associata ad un BehavEquation) in cui
 * è presente l'espressione.<br/><br/>
 *
 * Scope AEIdecl, InteractionDecl o AttacDecl - I soli identificatori che si possono presentare
 * nell'espressione selettore e nei parametri attuali di un AEIdecl o nel selettore di un InteractionDecl o AttacDecl
 * sono quelli dei parametri formali costanti dichiarati nell'intestazione del
 * tipo architetturale (oggetto Header dell'ArchiType).<br/><br/>
 *
 * Scope AEIdeclInd - L'identificatore indice presente in un AEIdeclInd può essere presente
 * nell'espressione di selezione e nei parametri attuali. Il range
 * dell'indice è dettato da due espressioni e i soli identificatori che si possono presentare in queste due
 * espressioni sono quelli dei parametri formali costanti dichiarati
 * nell'intestazione del tipo architetturale.<br/><br/>
 *
 * Scope InteractionDeclInd - L'identificatore indice presente in un
 * InteractionDeclInd può essere presente nell'espressione di selezione.
 * Il range dell'indice è dettato da due espressioni e isoli identificatori che si possono presentare in queste
 * due espressioni sono quelli dei parametri formali costanti dichiarati
 * nell'intestazione del tipo architetturale.<br/><br/>
 *
 * Scope AttacDeclInd - Gli identificatori indice presenti in un
 * AttacDeclInd possono essere presenti nell'espressione di
 * selezione. Il range degli indici è dettato da due espressioni e i soli identificatori che si possono
 * presentare in queste due espressioni sono quelli dei parametri formali
 * costanti dichiarati nell'intestazione del tipo architetturale.<br/><br/>
 *
 * Sono presenti anche metodi per valutare espressioni presenti nelle varie parti
 * di un tipo architetturale. Ovviamente, la loro valutazione dipende dallo scope dell'espressione.
 * Inoltre, ci sono metodi che normalizzano parti del tipo architetturale, dove, per normalizzazione
 * si intende la valutazione delle espressioni contenute nella parte della descrizione architetturale.
 * Un'espressione è normalizzata, se al suo interno non sono presenti identificatori.
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class Scope {

	private ArchiTypeParts atp;
	    
    // si imposta la mappa per la valutazione  
    private Valutazione valutazione = new Valutazione();

    /**
     * Crea un nuovo oggetto Scope, definendo una TreeMap, che ha come chiavi,
     * i nomi dei parametri costanti inizializzati presenti
     * nell'intestazione dell'ArchiType, e come valori, le valutazioni
     * delle rispettive espressioni di inizializzazione.
     *
     * @param at
     * @throws NormalizeException
     */
    public Scope(ArchiType at) throws NormalizeException
        {
        // si assegna al campo l'argomento del costruttore
        this.atp = new ArchiTypeParts(at);
        // i contiene l'intestazione del tipo architetturale
        Header i = at.getArchiTypeHeader();
        // ci contiene i parametri costanti dell'intestazione del
        // tipo architetturale
        ParamDeclaration[] ci = i.getParameters();
        for (int j = 0; j < ci.length; j++)
            {
        	if (ci[j] != null)
	        	{
	            // key è il nome della costante
	            String key = ((ConstInit)ci[j]).getName();
	            // td è il tipo della costante
	            DataType td = ((ConstInit)ci[j]).getType();
	            // e è l'espressione associata alla costante
	            Expression e = ((ConstInit)ci[j]).getExpr();
	            // se td è un Boolean si inserisce nella mappa una voce con
	            // key come chiave e returnBoolean(e) come valore
	            if (td instanceof specificheAEmilia.BooleanType)
	                {
	                Object o = valutazione.returnBoolean(e);
	                if (o instanceof Boolean)
	                    {
	                    this.valutazione.put(key, new ValueIdentExpr(o,true));
	                    ((ConstInit)ci[j]).setExpr(Valutazione.obtainExp(o));
	                    }
	                else throw new NormalizeException("Evaluation constant error "+key);
	                }
	            // se td è un Integer si inserisce nella mappa una voce con
	            // key come chiave e returnInteger(e) come valore
	            else if (td instanceof IntegerType)
	                {
	                Object o = this.valutazione.returnInteger(e);
	                if (o instanceof java.lang.Integer)
	                    {
	                	this.valutazione.put(key, new ValueIdentExpr(o,true));
	                    ((ConstInit)ci[j]).setExpr(Valutazione.obtainExp(o));
	                    }
	                else throw new NormalizeException("Evaluation constant error "+key);				
	                }
	            else if (td instanceof IntegerRangeType)
	            	{
	            	Object o = this.valutazione.returnInteger(e);
	            	if (o instanceof java.lang.Integer)
	                	{
	            		this.valutazione.put(key, new ValueIdentExpr(o,true));
	            		((ConstInit)ci[j]).setExpr(Valutazione.obtainExp(o));
	                	}
	            	else throw new NormalizeException("Evaluation constant error "+key);				
	            	}
	            else if (td instanceof RealType)
	                {
	                Object o = valutazione.returnDouble(e);
	                if (o instanceof Double)
	                    {
	                	this.valutazione.put(key, new ValueIdentExpr(o,true));
	                    ((ConstInit)ci[j]).setExpr(Valutazione.obtainExp(o));
	                    }
	                else throw new NormalizeException("Evaluation constant error "+key);				}
	            else if (td instanceof PrioType)
	                {
	                Object o = this.valutazione.returnInteger(e);
	                if (o instanceof java.lang.Integer)
	                    {
	                	this.valutazione.put(key, new ValueIdentExpr(o,true));
	                    ((ConstInit)ci[j]).setExpr(Valutazione.obtainExp(o));
	                    }
	                else throw new NormalizeException("Evaluation constant error "+key);				}
	            else if (td instanceof RateType)
	                {
	                Object o = valutazione.returnDouble(e);
	                if (o instanceof Double)
	                    {
	                	this.valutazione.put(key, new ValueIdentExpr(o,true));
	                    ((ConstInit)ci[j]).setExpr(Valutazione.obtainExp(o));
	                    }
	                else throw new NormalizeException("Evaluation constant error "+key);				}
	            else if (td instanceof WeightType)
	                {
	                Object o = valutazione.returnDouble(e);
	                if (o instanceof Double)
	                    {
	                	this.valutazione.put(key, new ValueIdentExpr(o,true));
	                    ((ConstInit)ci[j]).setExpr(Valutazione.obtainExp(o));
	                    }
	                else throw new NormalizeException("Evaluation constant error "+key);				}
	            else throw new NormalizeException(key+" not will be " +
	                    "evaluated");
	            }
            }
        }

    /**
     * Restituisce lo scope derivante dalla dichiarazione dei parametri
     * presenti nell'intestazione del tipo architetturale.
     * @return
     */
    public TreeMap<String, ValueIdentExpr> getScopeArchiType()
        {
        // restituisce lo scope derivante dai parametri presenti
        // nel tipo architetturale
        return this.valutazione.getTm();
        }

    /**
     * Restituisce lo scope che serve per la valutazione delle espressioni
     * presenti nell'intestazione della prima equazione comportamentale
     * di un ElemType.
     *
     * @param bh
     * @param et
     * @return
     */
    public TreeMap<String, ValueIdentExpr> getScopeBehavHeader(Header bh, ElemType et)
        throws NormalizeException
        {
        TreeMap<String, ValueIdentExpr> tma = new TreeMap<String, ValueIdentExpr>();
        if (!Find.isIntestazioneInElemType(bh, et)) throw new NormalizeException(bh.getName()+" is not header of " +
                "a architectural element type behavior" +
                et.getHeader().getName());
        // si aggiungono allo scope i parametri dell'intestazione di et
        Header intElemType = et.getHeader();
        // l'intestazione di un ElemType contiene solo costanti
        // non inizializzate
        ParamDeclaration[] spc = intElemType.getParameters();
        for (int j = 0; j < spc.length; j++)
            {
        	if (spc[j] != null)
	        	{
	            String key = spc[j].getName();
	            // nella mappa si inizializza a null il valore della costante
	            tma.put(key, new ValueIdentExpr(null,false));
	        	}
            }
        return tma;
        }

    /**
     * Restituisce lo scope che serve per la valutazione di espressioni 
     * all'interno di un'intestazione
     * di comportamento, secondo la dichiarazione di istanza fornita come parametro.
     * Il TreeMap restituito contiene gli identificatori con i rispettivi valori, ottenuti
     * dal mapping tra parametri attuali e parametri costanti del tipo di elemento
     * architetturale, specificato nella dichiarazione di istanza.
     *
     * @param aeid
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String, ValueIdentExpr> getScopeBehavHeaderFromAEI(AEIdecl aeid)
        throws NormalizeException
        {
        // in getScopeElemTypeFromAEI avviene il mapping tra parametri attuali e
        // i parametri definiti nel tipo di elemento architetturale
        TreeMap<String, ValueIdentExpr> ris = getScopeElemTypeFromAEI(aeid);
        return ris;
        }

    /**
     * Restituisce lo scope che serve per la valutazione di espressioni all'interno 
     * di un termine di processo
     * , secondo la dichiarazione di istanza e l'equazione comportamentale forniti come parametri.
     * L'equazione comportamentale deve appartenere all'ElemType relativo alla dichiarazione di istanza,
     * altrimenti viene lanciata una NormalizeException.
     * Il TreeMap restituito contiene il mapping tra parametri attuali e parametri costanti
     * del tipo di elemento architetturale presente nella dichiarazione di istanza, e gli identificatori
     * dell'intestazione di comportamento, al quale il termine di processo appartiene.
     *
     * @param aeid
     * @param be
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String, ValueIdentExpr> getScopeProcessTermFromAEIinDepth(AEIdecl aeid, BehavEquation be)
        throws NormalizeException
        {
        // si controlla se aeid è un'istanza del tipo di elemento architetturale
        // che ha be come equazione comportamentale
        ElemType et1 = Find.getElemTypeFromAei(aeid.getName(), this.atp.getAt());
        if (et1 == null) throw new NormalizeException(aeid.getName()+" have not relative architectural element type"
               	);
        if (!Find.isIntestazioneInElemType(be.getBehavHeader(), et1))
            throw new NormalizeException(be.getBehavHeader().getName()+" is not behavior header of "+
                et1.getHeader().getName());
        // si aggiunge alla scope di be lo normalizeParts dell'ElemType di aeid
        TreeMap<String, ValueIdentExpr> scoaeid = getScopeBehavHeaderFromAEI(aeid);
        TreeMap<String, ValueIdentExpr> scopt = getScopeProcessTerminDepth(be, scoaeid);
        // tramite la seguente istruzione, si valorizzano i parametri dell'ElemeType,
        // che hanno valore null, con i parametri
        // attuali presenti in aeid
        scoaeid.putAll(scopt);
        return scoaeid;
        }

    /**
     * Restituisce lo scope che serve per la valutazione di espressioni all'interno 
     * di un termine di processo
     * , secondo la dichiarazione di istanza e l'equazione comportamentale forniti come parametri.
     * L'equazione comportamentale deve appartenere all'ElemType relativo alla dichiarazione di istanza,
     * altrimenti viene lanciata una NormalizeException.
     * Il TreeMap restituito contiene il mapping tra parametri attuali e parametri costanti
     * del tipo di elemento architetturale presente nella dichiarazione di istanza, e gli identificatori
     * dell'intestazione di comportamento, al quale il termine di processo appartiene.
     * Gli identificatore dell'intestazione dell'equazione però non possono essere valutati.
     *
     * @param aeid
     * @param be
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String, ValueIdentExpr> getScopeProcessTermFromAEI(AEIdecl aeid, BehavEquation be)
        throws NormalizeException
        {
        // si controlla se aeid è un'istanza del tipo di elemento architetturale
        // che ha be come equazione comportamentale
        ElemType et1 = Find.getElemTypeFromAei(aeid.getName(), this.atp.getAt());
        if (et1 == null) throw new NormalizeException(aeid.getName()+
        		" have not relative architectural element type"
               	);
        if (!Find.isIntestazioneInElemType(be.getBehavHeader(), et1))
            throw new NormalizeException(be.getBehavHeader().getName()+
            		" is not behavior header of "+
                et1.getHeader().getName());
        // si aggiunge alla scope di be lo scope dell'ElemType di aeid
        TreeMap<String, ValueIdentExpr> scoaeid = getScopeBehavHeaderFromAEI(aeid);
        TreeMap<String, ValueIdentExpr> scopt = getScopeProcessTerm(be, scoaeid);
        // tramite la seguente istruzione, si valorizzano i parametri dell'ElemeType,
        // che hanno valore null, con i parametri
        // attuali presenti in aeid
        scoaeid.putAll(scopt);
        return scoaeid;
        }

    /**
     * Restituisce lo scope, che serve per la valutazione delle espressioni
     * presenti in un termine di processo, che definisce un comportamento di
     * un ElemType. Solleva una NormalizeException, se ci sono espressioni
     * di inizializzazione di parametri dichiarati in be, che non possono essere valutate.
     *
     * @param be
     * @param et
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String, ValueIdentExpr> getScopeProcessTerm(BehavEquation be, ElemType et)
        throws NormalizeException
        {
        // si preleva l'intestazione di et.
        TreeMap<String, ValueIdentExpr> tma = new TreeMap<String, ValueIdentExpr>();
        if (!Find.isIntestazioneInElemType(be.getBehavHeader(), et))
            throw new NormalizeException(et.getHeader().getName()+" do not belong to architectural element type "+
                et.getHeader().getName());
        // si aggiungono gli identificatori dell'intestazione di et
        // al contesto corrente (parametri formali per l'istanziazione
        // dell'AET)
        Header intElemType = et.getHeader();
        ParamDeclaration[] spc = intElemType.getParameters();
        for (int j = 0; j < spc.length; j++)
            {
        	if (spc[j] != null)
	        	{
	            String key = spc[j].getName();
	            // la costante viene inizializzata a null
	            tma.put(key, new ValueIdentExpr(null,false));
	        	}
            }
        // si preleva l'intestazione del BehavEquation precedente e si
        // aggiungono gli identificatori allo scope tma
        Header i = be.getBehavHeader();
        // per far si che il parametro abbia un'espressione di
        // inizializzazione deve essere VarInit
        ParamDeclaration[] dps = i.getParameters();
        int j = 0;
        while (j < dps.length && ((dps[j] instanceof VarInit) ||
                (dps[j] instanceof VariableDeclaration)))
            {
            if (dps[j] instanceof VarInit)
                {
                // key rappresenta il nome della costante inizializzata
                String key = ((VarInit)dps[j]).getName();
                // td rappresenta il tipo dato della costante
                DataType td = ((VarInit)dps[j]).getType();
                // e è l'espressione associata alla costante
                Expression e = ((VarInit)dps[j]).getExpr();
                // se td è un Boolean si inserisce nella mappa una voce con
                // key come chiave e returnBoolean(e) come valore
                if (td instanceof specificheAEmilia.BooleanType)
                    {
                    Object o = valutazione.returnBoolean(e);
                    if (o instanceof Boolean)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                // se td è un Integer si inserisce nella mappa una voce con
                // key come chiave e returnInteger(e) come valore.
                else if (td instanceof IntegerType)
                    {
                    Object o = this.valutazione.returnInteger(e);
                    if (o instanceof java.lang.Integer)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof IntegerRangeType)
                	{
                	Object o = this.valutazione.returnInteger(e);
                	if (o instanceof java.lang.Integer)
                		tma.put(key, new ValueIdentExpr(o,true));
                	else
                		tma.put(key, new ValueIdentExpr(o,false));
                	}
                else if (td instanceof RealType)
                    {
                    Object o = valutazione.returnDouble(e);
                    if (o instanceof Double)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof PrioType)
                    {
                    Object o = this.valutazione.returnInteger(e);
                    if (o instanceof java.lang.Integer)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof RateType)
                    {
                    Object o = valutazione.returnDouble(e);
                    if (o instanceof Double)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof WeightType)
                    {
                    Object o = valutazione.returnDouble(e);
                    if (o instanceof Double)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else throw new NormalizeException(key+" not will be evaluated");
                }
            if (dps[j] instanceof VariableDeclaration)
                {
                String key = dps[j].getName();
                tma.put(key, new ValueIdentExpr(null,false));
                }
            j++;
            }
        // nel caso in cui non ci sono variabili si incrementa j
        j++;
        // si aggiungono le variabili locali allo scope
        for (int k = j; k < dps.length && (dps[k] instanceof Local); k++)
            {
            String key = dps[k].getName();
            tma.put(key, new ValueIdentExpr(null,false));
            }
        return tma;
        }

    /**
     * Restituisce lo scope che serve per la valutazione delle espressioni
     * presenti in un termine di processo, che definisce un comportamento di
     * un ElemType a partire da uno scope fornito come parametro, che contiene i valori per
     * le costanti dell'ElemType.
     * Solleva una NormalizeException se ci sono espressioni
     * di inizializzazione di parametri che non possono essere valutate.
     *
     * @param be
     * @param tma
     * @return
     * @throws NormalizeException
     */
    private TreeMap<String, ValueIdentExpr> getScopeProcessTerminDepth(BehavEquation be, TreeMap<String, 
    		ValueIdentExpr> tma)
        throws NormalizeException
        {
        // si preleva l'intestazione del BehavEquation, e si
        // aggiungono gli identificatori allo scope tm
        Header i = be.getBehavHeader();
        // per far si che il parametro abbia un'espressione di
        // inizializzazione, deve essere VarInit
        ParamDeclaration[] dps = i.getParameters();
        int j = 0;
        while (j < dps.length && ((dps[j] instanceof VarInit) ||
                (dps[j] instanceof VariableDeclaration)))
            {
            if (dps[j] instanceof VarInit)
                {
                // key rappresenta il nome della costante inizializzata
                String key = ((VarInit)dps[j]).getName();
                // td rappresenta il tipo dato della costante
                DataType td = ((VarInit)dps[j]).getType();
                // e è l'espressione associata alla costante
                Expression e = ((VarInit)dps[j]).getExpr();
                // se td è un Boolean si inserisce nella mappa una voce con
                // key come chiave e returnBoolean(e) come valore
                if (td instanceof specificheAEmilia.BooleanType)
                    {
                    Object o = valutazione.returnBoolean(e,tma);
                    if (o instanceof Boolean)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                // se td è un Integer si inserisce nella mappa una voce con
                // key come chiave e returnInteger(e) come valore
                else if (td instanceof IntegerType)
                    {
                    Object o = this.valutazione.returnInteger(e,tma);
                    if (o instanceof java.lang.Integer)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof IntegerRangeType)
                	{
                	Object o = this.valutazione.returnInteger(e,tma);
                	if (o instanceof java.lang.Integer)
                		tma.put(key, new ValueIdentExpr(o,true));
                	else
                		tma.put(key, new ValueIdentExpr(o,false));
                	}
                else if (td instanceof RealType)
                    {
                    Object o = this.valutazione.returnDouble(e,tma);
                    if (o instanceof Double)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof PrioType)
                    {
                    Object o = this.valutazione.returnInteger(e,tma);
                    if (o instanceof java.lang.Integer)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof RateType)
                    {
                    Object o = this.valutazione.returnDouble(e,tma);
                    if (o instanceof Double)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof WeightType)
                    {
                    Object o = this.valutazione.returnDouble(e,tma);
                    if (o instanceof Double)
                        tma.put(key, new ValueIdentExpr(o,true));
                    else
                        tma.put(key, new ValueIdentExpr(o,false));
                    }
                else throw new NormalizeException(key+" not will be evaluated");
                }
            if (dps[j] instanceof VariableDeclaration)
                {
                String key = dps[j].getName();
                tma.put(key, new ValueIdentExpr(null,false));
                }
            j++;
            }
        // nel caso in cui non ci sono variabili si incrementa j
        j++;
        // si aggiungono le variabili locali allo scope
        for (int k = j; k < dps.length && (dps[k] instanceof Local); k++)
            {
            String key = dps[k].getName();
            tma.put(key, new ValueIdentExpr(null,false));
            }
        return tma;
        }

    /**
     * Restituisce lo scope che serve per la valutazione delle espressioni
     * presenti in un termine di processo, che definisce un comportamento di
     * un ElemType a partire da uno scope fornito come parametro, che contiene i valori per
     * le costanti dell'ElemType.
     * Solleva una NormalizeException se ci sono espressioni
     * di inizializzazione di parametri che non possono essere valutate.
     * I parametri presenti nell'intestazione non potranno essere valutate.
     *
     * @param be
     * @param tma
     * @return
     * @throws NormalizeException
     */
    private TreeMap<String, ValueIdentExpr> getScopeProcessTerm(BehavEquation be, TreeMap<String, 
    		ValueIdentExpr> tma)
        throws NormalizeException
        {
        // si preleva l'intestazione del BehavEquation, e si
        // aggiungono gli identificatori allo scope tm
        Header i = be.getBehavHeader();
        // per far si che il parametro abbia un'espressione di
        // inizializzazione, deve essere VarInit
        ParamDeclaration[] dps = i.getParameters();
        int j = 0;
        while (j < dps.length && ((dps[j] instanceof VarInit) ||
                (dps[j] instanceof VariableDeclaration)))
            {
            if (dps[j] instanceof VarInit)
                {
                // key rappresenta il nome della costante inizializzata
                String key = ((VarInit)dps[j]).getName();
                // td rappresenta il tipo dato della costante
                DataType td = ((VarInit)dps[j]).getType();
                // e è l'espressione associata alla costante
                Expression e = ((VarInit)dps[j]).getExpr();
                // se td è un Boolean si inserisce nella mappa una voce con
                // key come chiave e returnBoolean(e) come valore
                if (td instanceof specificheAEmilia.BooleanType)
                    {
                    Object o = valutazione.returnBoolean(e,tma);
                    tma.put(key, new ValueIdentExpr(o,false));
                    }
                // se td è un Integer si inserisce nella mappa una voce con
                // key come chiave e returnInteger(e) come valore
                else if (td instanceof IntegerType)
                    {
                    Object o = this.valutazione.returnInteger(e,tma);
                    tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof IntegerRangeType)
            		{
                	Object o = this.valutazione.returnInteger(e,tma);
                	tma.put(key, new ValueIdentExpr(o,false));
            		}
                else if (td instanceof RealType)
                    {
                    Object o = this.valutazione.returnDouble(e,tma);
                    tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof PrioType)
                    {
                    Object o = this.valutazione.returnInteger(e,tma);
                    tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof RateType)
                    {
                    Object o = this.valutazione.returnDouble(e,tma);
                    tma.put(key, new ValueIdentExpr(o,false));
                    }
                else if (td instanceof WeightType)
                    {
                    Object o = this.valutazione.returnDouble(e,tma);
                    tma.put(key, new ValueIdentExpr(o,false));
                    }
                else throw new NormalizeException(key+" not will be evaluated");
                }
            if (dps[j] instanceof VariableDeclaration)
                {
                String key = dps[j].getName();
                tma.put(key, new ValueIdentExpr(null,false));
                }
            j++;
            }
        // nel caso in cui non ci sono variabili si incrementa j
        j++;
        // si aggiungono le variabili locali allo scope
        for (int k = j; k < dps.length && (dps[k] instanceof Local); k++)
            {
            String key = dps[k].getName();
            tma.put(key, new ValueIdentExpr(null,false));
            }
        return tma;
        }

    /**
     * Restituisce lo scope, che serve per la valutazione delle espressioni,
     * presenti in una dichiarazione di istanze di elementi architetturali
     * . Allo scope ArchiType, si aggiunge un'eventuale voce
     * per l'indice, con valore uguale alla sua espressione di
     * inizializzazione.
     * Solleva una NormalizeException, se ci sono espressioni
     * di inizializzazione di parametri, che non possono essere valutate.
     *
     * @param aeid
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String, ValueIdentExpr> getScopeAEIdecl(AEIdecl aeid)
        throws NormalizeException
        {
        // 1) se aeid è un oggetto AEIdeclInd, si aggiunge l'indice
        // a ris, con il valore uguale all'espressione di inizializzazione.
        if (aeid instanceof AEIdeclInd)
            {
            AEIdeclInd clone = ((AEIdeclInd)aeid).copy();
            TreeMap<String, ValueIdentExpr> ris =
                new TreeMap<String, ValueIdentExpr>();
            // in ris si inseriscono tutte le voci della mappa attuale
            ris.putAll(this.valutazione.getTm());
            String indice = clone.getIndex();
            // ei è l'espressione di inizializzazione per l'indice
            Expression ei = clone.getBeginningExpr();
            // ef è l'espressione di fine per l'indice
            Expression ef = clone.getEndingExpr();
            // il tipo dinamico di o deve essere un Integer o Real.
            // Si assegna alle espressioni iniziali e finali i loro
            // rispettivi valori.
            // La valutazione delle espressioni iniziale e finale
            // deve essere indipendente.
            Object o = valutazione.valuta(ei);
            if (o instanceof Expression)
                ris.put(indice, new ValueIdentExpr(o,false));
            else
                ris.put(indice, new ValueIdentExpr(o,true));
            valutazione.valuta(ef);
            // si restituisce il nuovo scope
            return ris;
            }
        else return this.valutazione.getTm();
        }

    /**
     * Restituisce lo scope, che serve per la valutazione delle espressioni,
     * presenti in una dichiarazione indicizzata di interazioni.
     * Solleva una NormalizeException, se ci sono espressioni
     * di inizializzazione di parametri, che non possono essere valutate.
     *
     * @param id
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String, ValueIdentExpr> getScopeInteractionDecl(InteractionDecl id)
        throws NormalizeException
        {
        // se id è un oggetto InteractionDeclInd, si aggiunge l'indice a
        // tm, con valore uguale all'espressione di inizializzazione.
        if (id instanceof InteractionDeclInd)
            {
            InteractionDeclInd clone = ((InteractionDeclInd)id).copy();
            TreeMap<String, ValueIdentExpr> ris = new TreeMap<String, ValueIdentExpr>();
            ris.putAll(this.valutazione.getTm());
            String indice = clone.getIndex();
            Expression ei = clone.getBeginningExpr();
            Expression ef = clone.getEndingExpr();
            Object o = valutazione.valuta(ei);
            if (o instanceof Expression)
                ris.put(indice, new ValueIdentExpr(o,false));
            else
                ris.put(indice, new ValueIdentExpr(o,true));
            valutazione.valuta(ef);
            // si restituisce il nuovo scope.
            return ris;
            }
        else return this.valutazione.getTm();
        }

    /**
     * Restituisce lo scope, che serve per la valutazione delle espressioni,
     * presenti in una dichiarazione indicizzata di legami architetturali.
     * Solleva una NormalizeException, se ci sono espressioni
     * di inizializzazione di parametri, che non possono essere valutate.
     *
     * @param ad
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String, ValueIdentExpr> getScopeAttacDecl(AttacDecl ad)
        throws NormalizeException
        {
        // se id è un oggetto AttacDeclInd, si aggiungono gli indici
        // a tm, con valore uguale all'espressione di inizializzazione.
        if (ad instanceof AttacDeclInd)
            {
            AttacDeclInd clone = ((AttacDeclInd)ad).copy();
            TreeMap<String, ValueIdentExpr> ris = new TreeMap<String, ValueIdentExpr>();
            ris.putAll(this.valutazione.getTm());
            String indice1 = clone.getIndex1();
            Expression ei1 = clone.getBeginningExpr1();
            Expression ef1 = clone.getEndingExpr1();
            Object o1 = valutazione.valuta(ei1);
            if (o1 instanceof Expression)
                ris.put(indice1, new ValueIdentExpr(o1,false));
            else
                ris.put(indice1, new ValueIdentExpr(o1,true));
            valutazione.valuta(ef1);
            if (clone.getIndex2() != null)
                {
                String indice2 = clone.getIndex2();
                Expression ei2 = clone.getBeginningExpr2();
                Expression ef2 = clone.getEndingExpr2();
                Object o2 = valutazione.valuta(ei2);
                if (o2 instanceof Expression)
                    ris.put(indice2, new ValueIdentExpr(o2,false));
                else
                    ris.put(indice2, new ValueIdentExpr(o2,true));
                valutazione.valuta(ef2);
                }
            // si restituisce il nuovo scope.
            return ris;
            }
        else return this.valutazione.getTm();
        }

    /**
     * Stampa sullo standard output le coppie identificatore - valore
     * contenute nella mappa.
     *
     * @param t
     */
    public void stampaScope(TreeMap<String, ValueIdentExpr> t)
        {
        Set<String> keys = t.keySet();
        String[] as = new String[keys.size()];
        keys.toArray(as);
        Collection<ValueIdentExpr> valori = t.values();
        Object[] os = valori.toArray();
        for (int i = 0; i < as.length; i++)
            {
            System.out.print("The identifier "+as[i]);
            if (os[i] != null)
            System.out.println(" have value "+((ValueIdentExpr)os[i]).getValore());
            else
            System.out.println(" have value "+os[i]);
            }
        System.out.println();
        }

    /**
     * Assegna un tipo architetturale ad un oggetto Scope.
     *
     * @param at
     */
    public void setAt(ArchiType at) {
        this.atp.setAt(at);
    }

    /**
     * Restituisce lo scope, che serve per la valutazione di espressioni, all'interno di una dichiarazione
     * di tipo di elemento architetturale, secondo la dichiarazione di istanza fornita come parametro.
     * Il TreeMap restituito contiene gli identificatori con i rispettivi valori, prelevati dall'
     * intestazione del tipo architetturale e dal mapping tra parametri attuali e parametri costanti
     * del tipo di elemento architetturale, presente nella dichiarazione di istanza.
     *
     * @param aeid
     * @return
     * @throws NormalizeException
     */
    public TreeMap<String,ValueIdentExpr> getScopeElemTypeFromAEI(AEIdecl aeid) throws NormalizeException
        {
        AEIdecl clone = aeid.copy();
        // si normalizza la dichiarazione di istanza
        NormalizeParts normalizeParts = new NormalizeParts(this);
        AEIdecl temp = normalizeParts.normalizeAEIdecl(clone);
        // si preleva il tipo di elemento architetturale
        ElemType et = Find.getElemTypeFromAei(clone.getName(), this.atp.getAt());
        if (et == null) throw new NormalizeException(clone.getName()+" have not relative architectural element type");
        // a partire dallo scope per il tipo architetturale, si aggiungono i parametri allo scope, con
        // valore uguale a quello che corrispondente al parametro attuale
        ParamDeclaration[] dps = et.getHeader().getParameters();
        Expression[] es = temp.getActualParams();
        if (dps.length != es.length && !(dps.length == 1 && es.length == 0 && dps[0] == null))
            throw new NormalizeException("The size of actual parameters and formals parameters is not equal");
        TreeMap<String, ValueIdentExpr> tma = new TreeMap<String, ValueIdentExpr>();
        if (es != null)
            {
            for (int i = 0; i < es.length; i++)
                {
                Object o = valutazione.valuta(es[i]);
                if (o instanceof Expression)
                    tma.put(dps[i].getName(), new ValueIdentExpr(o,false));
                else
                    tma.put(dps[i].getName(), new ValueIdentExpr(o,true));
                }
            }
        return tma;
        }

    /**
     * Assegna uno scope ArchiType.
     *
     * @param tm
     */
    public void setScopeArchiType(TreeMap<String, ValueIdentExpr> tm)
        {
        this.valutazione.setTm(tm);
        }

	/**
	 * Restituisce una mappa di tipi di elementi architetturali normalizzati
	 * a seconda dei parametri contenuti nelle loro istanze, che rappresentano le chiavi della mappa.
	 *
	 * @return
	 * @throws NormalizeException
	 */
	public HashMap<String,ElemType> getElemTypesNormalized() throws NormalizeException {
	HashMap<String,ElemType> list = new HashMap<String, ElemType>();
	NormalizeParts normalizeParts = new NormalizeParts(this);
	ArchiTypeParts archiTypeParts = new ArchiTypeParts(this.atp.getAt());
	AEIdecl[] idecls = archiTypeParts.getAEIdecls();
	for (int i = 0; i < idecls.length; i++)
		{
		ElemType elemType = normalizeParts.normalizeElemTypeFromAEI(idecls[i]);
		list.put(idecls[i].getName(), elemType);
		}
	return list;
	}
	
	/**
	 * Restituisce il tipo architetturale.
	 *
	 * @return
	 */
	public ArchiType getAt() {
	    return atp.getAt();
	}

	public Valutazione getValutazione() 
		{
		return valutazione;
		}
	}