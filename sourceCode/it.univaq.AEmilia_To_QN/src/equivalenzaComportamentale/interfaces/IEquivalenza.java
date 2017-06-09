package equivalenzaComportamentale.interfaces;

import specificheAEmilia.AEIdecl;
import specificheAEmilia.Action;
import specificheAEmilia.ElemType;

/**
 * Interfaccia utilizzata per rappresentare e risolvere il problema
 * dell'iEquivalenza comportamentale di un tipo di elemento
 * architetturale.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 *
 */
public interface IEquivalenza {

	/**
	 * Restituisce il tipo di elemento architetturale per il quale
	 * si vuole verificare l'iEquivalenza.
	 *
	 * @return
	 */
	public abstract ElemType getEt();

	/**
	 * Imposta il tipo di elemento architetturale per il quale
	 * si vuole verificare l'iEquivalenza.
	 *
	 * @return
	 */
	public abstract void setEt(ElemType et);

	/**
	 * Restituisce la dichiarazione di istanza che definisce l'iEquivalenza.
	 * 
	 * @return
	 */
	public abstract AEIdecl getAEIdecl();
	
	/**
	 * Imposta la dichiarazione di istanza che definisce l'iEquivalenza.
	 * 
	 * @param idecl
	 */
	public abstract void setAEIdecl(AEIdecl idecl);
	
	/**
	 * Restituisce true se e solo se l'istanza è
	 * equivalente.
	 * 
	 * @return
	 */
	public abstract boolean isEquivalente();
	
	/**
	 * Restituisce l'azione relativa all'interazione di nome string.
	 * Restituisce null se string non è il nome di un'azione oppure l'istanza
	 * non è equivalente.
	 * 
	 * @param string
	 * @return
	 */
	public abstract Action getActionFromName(String string);
	
	}