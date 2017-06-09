package valutazione;

import java.util.TreeMap;

import specificheAEmilia.And;
import specificheAEmilia.Different;
import specificheAEmilia.Divisione;
import specificheAEmilia.Expression;
import specificheAEmilia.IdentExpr;
import specificheAEmilia.Integer;
import specificheAEmilia.IntegerRangeType;
import specificheAEmilia.Maggiore;
import specificheAEmilia.MaggioreUguale;
import specificheAEmilia.Minore;
import specificheAEmilia.MinoreUguale;
import specificheAEmilia.Moltiplicazione;
import specificheAEmilia.Negazione;
import specificheAEmilia.Or;
import specificheAEmilia.Prio;
import specificheAEmilia.Rate;
import specificheAEmilia.Real;
import specificheAEmilia.Somma;
import specificheAEmilia.Sottrazione;
import specificheAEmilia.Equal;
import specificheAEmilia.Weight;

/**
 * Classe utilizzata per la valutazione di un'espressione.
 * tm è una mappa in cui la chiave è un identificatore di tipo
 * String e il valore è un ValueIdentExpr, e serve per la valutazione di espressioni che contengono
 * identificatori.
 * 
 * @author Mirko
 *
 */
public class Valutazione 
	{
	
    private TreeMap<String, ValueIdentExpr> tm;
    	
    public Valutazione() 
    	{
		super();
		this.tm = new TreeMap<String, ValueIdentExpr>();
    	}

	/**
     * Valuta un'espressione.
     * Solleva un'eccezione NormalizeException, se l'espressione non può
     * essere valutata. Se l'espressione contiene parametri
     * non inizializzati, si restituisce l'espressione il più possibile normalizzata
     * di e. Altrimenti, si restituisce un oggetto
     * Integer, Double o Boolean, che wrappa la valutazione di e.
     *
     * @param e
     * @return
     * @throws NormalizeException
     */
    public Object valuta(Expression e)
        throws NormalizeException
        {
        Object ris = new Object();
        // a seconda del tipo dinamico di e, si ha una differente
        // valutazione
        try {
            if (e instanceof IdentExpr)
                ris = valuta((IdentExpr)e);
            else if (e instanceof And) ris = valuta((And)e);
            else if (e instanceof Different) ris = valuta((Different)e);
            else if (e instanceof Maggiore) ris = valuta((Maggiore)e);
            else if (e instanceof MaggioreUguale) ris = valuta((MaggioreUguale)e);
            else if (e instanceof Minore) ris = valuta((Minore)e);
            else if (e instanceof MinoreUguale) ris = valuta((MinoreUguale)e);
            else if (e instanceof Negazione) ris = valuta((Negazione)e);
            else if (e instanceof Or) ris = valuta((Or)e);
            else if (e instanceof Equal) ris = valuta((Equal)e);
            else if (e instanceof specificheAEmilia.Boolean) ris = valuta((specificheAEmilia.Boolean)e);
            else if (e instanceof Integer) ris = valuta((Integer)e);
            else if (e instanceof Divisione) ris = valuta((Divisione)e);
            else if (e instanceof Moltiplicazione) ris = valuta((Moltiplicazione)e);
            else if (e instanceof Somma) ris = valuta((Somma)e);
            else if (e instanceof Sottrazione) ris = valuta((Sottrazione)e);
            else if (e instanceof Real) ris = valuta((Real)e);
            else if (e instanceof Prio) ris = valuta((Prio)e);
            else if (e instanceof Rate) ris = valuta((Rate)e);
            else if (e instanceof Weight) ris = valuta((Weight)e);
            else throw new NormalizeException("The expression not will be evaluated");
            }
        catch (ValutazioneException ve)
            {
            return e;
            }
        return ris;
        }

    /**
     * Valuta un'espressione.
     * Solleva un'eccezione NormalizeException, se l'espressione non può
     * essere valutata. Se l'espressione contiene parametri
     * non inizializzati, si restituisce l'espressione il più possibile normalizzata
     * di e. Altrimenti, si restituisce un oggetto
     * Integer, Double o Boolean che rappresenta la valutazione di e.
     *
     * @param e
     * @param tma
     * @return
     * @throws NormalizeException
     */
    public Object valuta(Expression e, TreeMap<String, ValueIdentExpr> tma)
        throws NormalizeException
        {
        // si assegna ad una TreeMap il reference di tm
        TreeMap<String, ValueIdentExpr> temp = this.tm;
        this.tm = tma;
        Object ris = new Object();
        // a seconda del tipo dinamico di e si ha una differente
        // valutazione
        try {
            if (e instanceof IdentExpr)
                ris = valuta((IdentExpr)e);
            else if (e instanceof And) ris = valuta((And)e);
            else if (e instanceof Different) ris = valuta((Different)e);
            else if (e instanceof Maggiore) ris = valuta((Maggiore)e);
            else if (e instanceof MaggioreUguale) ris = valuta((MaggioreUguale)e);
            else if (e instanceof Minore) ris = valuta((Minore)e);
            else if (e instanceof MinoreUguale) ris = valuta((MinoreUguale)e);
            else if (e instanceof Negazione) ris = valuta((Negazione)e);
            else if (e instanceof Or) ris = valuta((Or)e);
            else if (e instanceof Equal) ris = valuta((Equal)e);
            else if (e instanceof specificheAEmilia.Boolean) ris = valuta((specificheAEmilia.Boolean)e);
            else if (e instanceof Integer) ris = valuta((Integer)e);
            else if (e instanceof Divisione) ris = valuta((Divisione)e);
            else if (e instanceof Moltiplicazione) ris = valuta((Moltiplicazione)e);
            else if (e instanceof Somma) ris = valuta((Somma)e);
            else if (e instanceof Sottrazione) ris = valuta((Sottrazione)e);
            else if (e instanceof Real) ris = valuta((Real)e);
            else if (e instanceof Prio) ris = valuta((Prio)e);
            else if (e instanceof Rate) ris = valuta((Rate)e);
            else if (e instanceof Weight) ris = valuta((Weight)e);
            else throw new NormalizeException("The expression not will be evaluated");
            }
        catch (ValutazioneException e1)
            {
            this.tm = temp;
            return e;
            }
        this.tm = temp;
        return ris;
        }

    /**
     * Valuta un oggetto Weight, restituendo un oggetto Double.
     *
     * @param w
     * @return
     */
    private Double valuta(Weight w)
        {
        return new Double(w.getValore());
        }

    /**
     * Valuta un oggetto Rate, restituendo un oggetto Double.
     *
     * @param r
     * @return
     */
    private Double valuta(Rate r)
        {
        return new Double(r.getValore());
        }

    /**
     * Valuta un oggetto Prio, restituendo un oggetto Integer.
     *
     * @param p
     * @return
     */
    private java.lang.Integer valuta(Prio p)
        {
        return new java.lang.Integer(p.getValore());
        }

    /**
     * Valuta un oggetto Sottrazione, restituendo un oggetto Double.
     *
     * @param s
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Sottrazione s)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = s.getExpr1();
        Expression op2 = s.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            // se gli operandi sono interi si restituisce un intero
            if (Math.rint((Double)f1) == (Double)f1 && Math.rint((Double)f2) == (Double)f2)
                {
                double risdou = ((Double)f1).doubleValue() - ((Double)f2).doubleValue();
                ris = new java.lang.Integer((new Double(risdou)).intValue());
                }
            // altrimenti si restituisce un double
            else
                {
                ris = new Double(((Double)f1).doubleValue() - ((Double)f2).doubleValue());
                }
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            s.setExpr1((Expression)f1);
            s.setExpr2((Expression)f2);
            ris = s;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Somma.
     *
     * @param s
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Somma s)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = s.getExpr1();
        Expression op2 = s.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            // se gli operandi sono interi si restituisce un intero
            if (Math.rint((Double)f1) == (Double)f1 && Math.rint((Double)f2) == (Double)f2)
                {
                ris = new java.lang.Integer(((Double)f1).intValue() + ((Double)f2).intValue());
                }
            // altrimenti si restituisce un double
            else
                {
                ris = new Double((Double)f1 + (Double)f2);
                }
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            s.setExpr1((Expression)f1);
            s.setExpr2((Expression)f2);
            ris = s;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Moltiplicazione.
     *
     * @param m
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Moltiplicazione m)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = m.getExpr1();
        Expression op2 = m.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            // se gli operandi sono interi si restituisce un intero
            if (Math.rint((Double)f1) == (Double)f1 && Math.rint((Double)f2) == (Double)f2)
                {
                ris = new java.lang.Integer(((Double)f1).intValue() * ((Double)f2).intValue());
                }
            // altrimenti si restituisce un double
            else {
                ris = new Double(((Double)f1).doubleValue() * ((Double)f2).doubleValue());
                }
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            m.setExpr1((Expression)f1);
            m.setExpr2((Expression)f2);
            ris = m;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Divisione.
     *
     * @param d
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Divisione d)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = d.getExpr1();
        Expression op2 = d.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            if ((Double)f2 == 0) throw new NormalizeException("Is division for zero");
            ris = new Double(((Double)f1).doubleValue() / ((Double)f2).doubleValue());
            // se il risultato della divisione è intero si restituisce un intero
            if (Math.rint((Double)ris) == (Double)ris)
                {
                ris = new java.lang.Integer(((Double)ris).intValue());
                }
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            d.setExpr1((Expression)f1);
            d.setExpr2((Expression)f2);
            ris = d;
            }
        return ris;
        }

    /**
     * Valuta un oggetto IntegerRangeType.
     *
     * @param ii
     * @return
     * @throws NormalizeException
     */
    @SuppressWarnings("unused")
	private void valuta(IntegerRangeType ii)
        throws NormalizeException
        {
        Expression e1 = ii.getBeginningInt();
        Interezza interezza = new Interezza();
        Integer integer;
        try {
            integer = interezza.returnIntero(e1);
            }
        catch (InterezzaException e)
            {
            throw new NormalizeException(e);
            }
        ii.setBeginningInt(integer);
        Expression e2 = ii.getEndingInt();
        Integer integer2;
        try {
            integer2 = interezza.returnIntero(e2);
            }
        catch (InterezzaException e)
            {
            throw new NormalizeException(e);
            }
        ii.setEndingInt(integer2);
        }

    /**
     * Valuta un oggetto Integer.
     *
     * @param i
     * @return
     */
    public java.lang.Integer valuta(Integer i)
        {
        return new java.lang.Integer(i.getValore());
        }

    /**
     * Valuta un oggetto Equal.
     *
     * @param u
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Equal u)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = u.getExpr1();
        Expression op2 = u.getExpr2();
        Object b1 = returnDouble(op1);
        Object b2 = returnDouble(op2);
        if (b1 instanceof Double && b2 instanceof Double)
            {
            ris = new Boolean(((Double)b1).doubleValue() == ((Double)b2).doubleValue());
            }
        else
            {
            if (b1 instanceof Double) b1 = obtainExp(b1);
            if (b2 instanceof Double) b2 = obtainExp(b2);
            u.setExpr1((Expression)b1);
            u.setExpr2((Expression)b2);
            ris = u;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Or.
     *
     * @param o
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Or o)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = o.getExpr1();
        Expression op2 = o.getExpr2();
        Object b1 = returnBoolean(op1);
        Object b2 = returnBoolean(op2);
        if (b1 instanceof Boolean && b2 instanceof Boolean)
            {
            ris = new Boolean(((Boolean)b1).booleanValue() || ((Boolean)b2).booleanValue());
            }
        else
            {
            if (b1 instanceof Boolean) b1 = obtainExp(b1);
            if (b2 instanceof Boolean) b2 = obtainExp(b2);
            o.setExpr1((Expression)b1);
            o.setExpr2((Expression)b2);
            ris = o;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Negazione.
     *
     * @param n
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Negazione n)
        throws NormalizeException
        {
        Object ris = null;
        Expression op = n.getExpr1();
        Object b = returnBoolean(op);
        if (b instanceof Boolean)
            {
            ris = new Boolean(!(((Boolean)b).booleanValue()));
            }
        else
            {
            b = obtainExp(b);
            n.setExpr1((Expression)b);
            ris = b;
            }
        return ris;
        }

    /**
     * Valuta un oggetto MinoreUguale.
     *
     * @param mu
     * @return
     * @throws NormalizeException
     */
    private Object valuta(MinoreUguale mu)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = mu.getExpr1();
        Expression op2 = mu.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            ris = new Boolean(((Double)f1).doubleValue() <= ((Double)f2).doubleValue());
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            mu.setExpr1((Expression)f1);
            mu.setExpr2((Expression)f2);
            ris = mu;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Minore.
     *
     * @param m
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Minore m)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = m.getExpr1();
        Expression op2 = m.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            ris = new Boolean(((Double)f1).doubleValue() < ((Double)f2).doubleValue());
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            m.setExpr1((Expression)f1);
            m.setExpr2((Expression)f2);
            ris = m;
            }
        return ris;
        }

    /**
     * Valuta un oggetto MaggioreUguale.
     *
     * @param mu
     * @return
     * @throws NormalizeException
     */
    private Object valuta(MaggioreUguale mu)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = mu.getExpr1();
        Expression op2 = mu.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            ris = new Boolean(((Double)f1).doubleValue() >= ((Double)f2).doubleValue());
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            mu.setExpr1((Expression)f1);
            mu.setExpr2((Expression)f2);
            ris = mu;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Maggiore.
     *
     * @param m
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Maggiore m)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = m.getExpr1();
        Expression op2 = m.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            ris = new Boolean(((Double)f1).doubleValue() > ((Double)f2).doubleValue());
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            m.setExpr1((Expression)f1);
            m.setExpr2((Expression)f2);
            ris = m;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Different.
     *
     * @param d
     * @return
     * @throws NormalizeException
     */
    private Object valuta(Different d)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = d.getExpr1();
        Expression op2 = d.getExpr2();
        Object f1 = returnDouble(op1);
        Object f2 = returnDouble(op2);
        if (f1 instanceof Double && f2 instanceof Double)
            {
            ris = new Boolean(((Double)f1).doubleValue() != ((Double)f2).doubleValue());
            }
        else
            {
            if (f1 instanceof Double) f1 = obtainExp(f1);
            if (f2 instanceof Double) f2 = obtainExp(f2);
            d.setExpr1((Expression)f1);
            d.setExpr2((Expression)f2);
            ris = d;
            }
        return ris;
        }

    /**
     * Valuta un oggetto And.
     *
     * @param a
     * @return
     * @throws NormalizeException
     */
    private Object valuta(And a)
        throws NormalizeException
        {
        Object ris = null;
        Expression op1 = a.getExpr1();
        Expression op2 = a.getExpr2();
        Object b1 = returnBoolean(op1);
        Object b2 = returnBoolean(op2);
        if (b1 instanceof Boolean && b2 instanceof Boolean)
            {
            ris = new Boolean(((Boolean)b1).booleanValue() && ((Boolean)b2).booleanValue());
            }
        else
            {
            if (b1 instanceof Boolean) b1 = obtainExp(b1);
            if (b2 instanceof Boolean) b2 = obtainExp(b2);
            a.setExpr1((Expression)b1);
            a.setExpr2((Expression)b2);
            ris = a;
            }
        return ris;
        }

    /**
     * Valuta un oggetto Real.
     *
     * @param r
     * @return
     */
    public Double valuta(Real r)
        {
        return new Double(r.getValore());
        }

    /**
     * Valuta un oggetto Boolean.
     *
     * @param b
     * @return
     */
    private Boolean valuta(specificheAEmilia.Boolean b)
        {
        return new Boolean(b.getValore());
        }

    /**
     * Valuta un oggetto IdentExpr.
     *
     * @param ie
     * @return
     */
    public Object valuta(IdentExpr ie)
        throws NormalizeException, ValutazioneException
        {
        ValueIdentExpr td = tm.get(ie.getNome());
        if (td == null)
            throw new NormalizeException("The identifier "+ie.getNome()+" have null value or do not exist in the normalizeParts.");
        if (!td.isValutazione())
            throw new ValutazioneException("The identifier "+ie.getNome()+" is is either a local variable, or has not been initialized"+
            		", or unevaluable expression assignment");
        return td.getValore();
        }

    /**
     * Restituisce un Double dalla valutazione di un'espressione.
     * Solleva un'eccezione NormalizeException, se o non può essere valutato
     * ad un Double. Se o contiene parametri non inizializzati,
     * si restituisce la sua espressione normalizzata.
     *
     * @param o
     * @return
     * @throws NormalizeException
     */
    public Object returnDouble(Expression o)
        throws NormalizeException
        {
        // per ottenere la giusta valutazione degli operandi
        // bisogna procedere per casi, perchè i parametri dei
        // metodi sono valutati staticamente
        Object ris = null;
        try {
            if (o instanceof IdentExpr)
                {
                Object ob = valuta((IdentExpr)o);
                if (ob instanceof java.lang.Integer)
                    ris = new Double(((java.lang.Integer)ob).doubleValue());
                else if (ob instanceof Double)
                    ris = (Double)ob;
                else throw new NormalizeException("The expression identifier "+
                        ((IdentExpr)o).getNome()+" is not a Double");
                }
            else if (o instanceof Divisione)
                ris = valuta((Divisione)o);
            else if (o instanceof Moltiplicazione)
                ris = valuta((Moltiplicazione)o);
            else if (o instanceof Somma)
                ris = valuta((Somma)o);
            else if (o instanceof Sottrazione)
                ris = valuta((Sottrazione)o);
            else if (o instanceof Integer)
                ris = new Double(valuta((Integer)o).doubleValue());
            else if (o instanceof Real)
                ris = valuta((Real)o);
            else throw new NormalizeException("The expression is not a Double");
            if (ris instanceof java.lang.Integer)
                ris = new Double(((java.lang.Integer)ris).doubleValue());
            }
        catch (ValutazioneException ve)
            {
            return o;
            }
        return ris;
        }
	
    /**
     * Converte un oggetto di tipo Integer, Double o Boolean in un corrispondente
     * oggetto AEmilia. Restituisce null, se o non è di tipo Integer, Double o Boolean.
     *
     * @param o
     * @return
     */
    public static Expression obtainExp(Object o)
        {
        Expression ris = null;
        if (o instanceof java.lang.Integer)
            ris = new Integer(((java.lang.Integer)o).intValue());
        else if (o instanceof Double)
            ris = new Real(((Double)o).doubleValue());
        else if (o instanceof Boolean)
            ris = new specificheAEmilia.Boolean(((Boolean)o).booleanValue());
        return ris;
        }
    
    /**
     * Restituisce un Boolean dalla valutazione di un'espressione.
     * Se o non può essere valutato come un Boolean, si solleva
     * un'eccezione NormalizeException. Se o contiene parametri
     * non inizializzati, si restituisce la sua espressione normalizzata.
     *
     * @param o
     * @return
     * @throws NormalizeException
     */
    public Object returnBoolean(Expression o)
        throws NormalizeException
        {
        Object ris = null;
        try {
            if (o instanceof IdentExpr)
                {
                Object ob = valuta((IdentExpr)o);
                if (ob instanceof Boolean)
                    ris = (Boolean)ob;
                else throw new NormalizeException("The expression identifier"
                		+((IdentExpr)o).getNome()+" is not a Boolean");
                }
            else if (o instanceof And)
                ris = valuta((And)o);
            else if (o instanceof Different)
                ris = valuta((Different)o);
            else if (o instanceof Maggiore)
                ris = valuta((Maggiore)o);
            else if (o instanceof MaggioreUguale)
                ris = valuta((MaggioreUguale)o);
            else if (o instanceof Minore)
                ris = valuta((Minore)o);
            else if (o instanceof MinoreUguale)
                ris = valuta((MinoreUguale)o);
            else if (o instanceof Negazione)
                ris = valuta((Negazione)o);
            else if (o instanceof Or)
                ris = valuta((Or)o);
            else if (o instanceof Equal)
                ris = valuta((Equal)o);
            else if (o instanceof specificheAEmilia.Boolean)
                ris = valuta((specificheAEmilia.Boolean)o);
            else throw new NormalizeException("The parameter is not a Boolean");
            }
        catch (ValutazioneException ve)
            {
            return o;
            }
        return ris;
        }

    /**
     * Restituisce un Boolean dalla valutazione di un'espressione.
     * Se o non può essere valutato come un Boolean, si solleva
     * un'eccezione NormalizeException. Se o contiene parametri
     * non inizializzati, si restituisce la sua espressione normalizzata.
     *
     * @param o
     * @return
     * @throws NormalizeException
     */
    public Object returnBoolean(Expression o, TreeMap<String, ValueIdentExpr> tma)
        throws NormalizeException
        {
        TreeMap<String, ValueIdentExpr> temp = this.tm;
        this.tm = tma;
        Object ris = null;
        try {
            if (o instanceof IdentExpr)
                {
                Object ob;
                ob = valuta((IdentExpr)o);
                if (ob instanceof Boolean)
                    ris = (Boolean)ob;
                else throw new NormalizeException("The expression identifier "
                		+((IdentExpr)o).getNome()+" is not a Boolean");
                }
            else if (o instanceof And)
                ris = valuta((And)o);
            else if (o instanceof Different)
                ris = valuta((Different)o);
            else if (o instanceof Maggiore)
                ris = valuta((Maggiore)o);
            else if (o instanceof MaggioreUguale)
                ris = valuta((MaggioreUguale)o);
            else if (o instanceof Minore)
                ris = valuta((Minore)o);
            else if (o instanceof MinoreUguale)
                ris = valuta((MinoreUguale)o);
            else if (o instanceof Negazione)
                ris = valuta((Negazione)o);
            else if (o instanceof Or)
                ris = valuta((Or)o);
            else if (o instanceof Equal)
                ris = valuta((Equal)o);
            else if (o instanceof specificheAEmilia.Boolean)
                ris = valuta((specificheAEmilia.Boolean)o);
            else throw new NormalizeException("The parameter is not a Boolean");
            this.tm = temp;
            }
        catch (ValutazioneException ve)
            {
            this.tm = temp;
            return o;
            }
        return ris;
        }
    
    /**
     * Restituisce un Double dalla valutazione di un'espressione.
     * Solleva un'eccezione NormalizeException, se o non può essere valutato
     * ad un Double. Se o contiene parametri non inizializzati,
     * si restituisce la sua espressione normalizzata.
     *
     * @param o
     * @return
     * @throws NormalizeException
     */
    public Object returnDouble(Expression o, TreeMap<String, ValueIdentExpr> tma)
        throws NormalizeException
        {
        TreeMap<String, ValueIdentExpr> temp = this.tm;
        this.tm = tma;
        // per ottenere la giusta valutazione degli operandi,
        // bisogna procedere per casi, perchè i parametri dei
        // metodi sono valutati staticamente
        Object ris = null;
        try {
        if (o instanceof IdentExpr)
           {
           Object ob = valuta((IdentExpr)o);
           if (ob instanceof java.lang.Integer)
               ris = new Double(((java.lang.Integer)ob).doubleValue());
           else if (ob instanceof Double)
               ris = (Double)ob;
           else throw new NormalizeException("The expression identifier "
      		+((IdentExpr)o).getNome()+" is not a Double");
           }
        else if (o instanceof Divisione)
            ris = valuta((Divisione)o);
        else if (o instanceof Moltiplicazione)
            ris = valuta((Moltiplicazione)o);
        else if (o instanceof Somma)
            ris = valuta((Somma)o);
        else if (o instanceof Sottrazione)
            ris = valuta((Sottrazione)o);
        else if (o instanceof Integer)
        	ris = new Double(valuta((Integer)o).doubleValue());
        else if (o instanceof Real)
        	ris = valuta((Real)o);
        else throw new NormalizeException("The parameter is not a Double");
        if (ris instanceof java.lang.Integer)
        	ris = new Double(((java.lang.Integer)ris).doubleValue());
        this.tm = temp;
        }
        catch (ValutazioneException ve)
        	{
        	this.tm = temp;
        	return o;
        	}
        return ris;
        }

    /**
     * Restituisce un Integer dalla valutazione di un'espressione.
     * Se o non può essere valutata
     * come un intero, si solleva un'eccezione NormalizeException. Se o contiene parametri non inizializzati,
     * si restituisce la sua espressione normalizzata.
     *
     * @param o
     * @return
     * @throws NormalizeException
     */
    public Object returnInteger(Expression o)
        throws NormalizeException
        {
        Object ris =null;
        try {
            if (o instanceof IdentExpr)
                {
                Object ob = valuta((IdentExpr)o);
                if (ob instanceof java.lang.Integer)
                    ris = (java.lang.Integer)ob;
                else if (ob instanceof Double)
                    ris = new java.lang.Integer(((Double)ob).intValue());
                else throw new NormalizeException("The identifier expression "
                		+((IdentExpr)o).getNome()+" is not a Integer");
                }
            else if (o instanceof Divisione)
                {
                Object v = valuta((Divisione)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Moltiplicazione)
                {
                Object v = valuta((Moltiplicazione)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Somma)
                {
                Object v = valuta((Somma)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Sottrazione)
                {
                Object v = valuta((Sottrazione)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Integer)
                ris = valuta((Integer)o);
            else if (o instanceof Real)
                ris = new java.lang.Integer(valuta((Real)o).intValue());
            else throw new NormalizeException("The parameter is not a Integer");
            }
        catch (ValutazioneException ve)
            {
            return o;
            }
        return ris;
        }

    /**
     * Restituisce un Integer dalla valutazione di un'espressione.
     * Lo normalizeParts utilizzato è tma. Se o non può essere valutata
     * come un intero, si solleva un'eccezione NormalizeException. Se o contiene parametri non inizializzati,
     * si restituisce la sua espressione normalizzata.
     *
     * @param o
     * @return
     * @throws NormalizeException
     */
    public Object returnInteger(Expression o, TreeMap<String, ValueIdentExpr> tma)
        throws NormalizeException
        {
        TreeMap<String, ValueIdentExpr> temp = this.tm;
        this.tm = tma;
        Object ris = null;
        try {
            if (o instanceof IdentExpr)
                {
                Object ob;
                ob = valuta((IdentExpr)o);
                if (ob instanceof java.lang.Integer)
                    ris = (java.lang.Integer)ob;
                else if (ob instanceof Double)
                    ris = new java.lang.Integer(((Double)ob).intValue());
                else throw new NormalizeException("The expression identifier "
                		+((IdentExpr)o).getNome()+" is not a Integer");
                }
            else if (o instanceof Divisione)
                {
                Object v = valuta((Divisione)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Moltiplicazione)
                {
                Object v = valuta((Moltiplicazione)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Somma)
                {
                Object v = valuta((Somma)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Sottrazione)
                {
                Object v = valuta((Sottrazione)o);
                if (v instanceof Double)
                    ris = new java.lang.Integer(((Double)v).intValue());
                else
                    ris = v;
                }
            else if (o instanceof Integer)
                ris = valuta((Integer)o);
            else if (o instanceof Real)
                ris = new java.lang.Integer(valuta((Real)o).intValue());
            else throw new NormalizeException("The parameter is not a Integer");
            }
        catch (ValutazioneException ve)
            {
            this.tm = temp;
            return o;
            }
        this.tm = temp;
        return ris;
        }

	public ValueIdentExpr put(String key, ValueIdentExpr value) 
		{
		return tm.put(key, value);
		}

	public TreeMap<String, ValueIdentExpr> getTm() 
		{
		return tm;
		}

	public void setTm(TreeMap<String, ValueIdentExpr> tm) 
		{
		this.tm = tm;
		}
	}
