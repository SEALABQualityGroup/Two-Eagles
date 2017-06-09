package scanSpecAEmilia;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import personalScanner.MyScanner;

import specificheAEmilia.InteractionDecl;
import specificheAEmilia.InteractionDeclInd;

/**
 * Classe utilizzata per scannerizzare ogni parte di una
 * dichiarazione di interazione architetturale, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

/*
 * Una dichiarazione di interazione architetturale ha la seguente
 * forma:
 *
 * <architectural_interaction_decl> ::=
 * <identifier> ["[" <expr> "]"] "." <identifier>
 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 * <identifier> "[" <expr> "]" "." <identifier>
 *
 * Nella sua forma piเน semplice, una dichiarazione di interazione
 * architetturale contiene l'identificatore di un AEI a cui
 * l'interazione appartiene, una possibile espressione intera
 * racchiusa tra parentesi quadre, che rappresenta un selettore e
 * deve essere privo di identificatori non dichiarati e invocazioni
 * a generatori di numeri pseudo-casuali, e l'identificatore
 * dell'interazione. I soli identificatori che si possono
 * presentare nella possibile espressione di selezione sono
 * quelli dei parametri formali costanti dichiarati
 * nell'intestazione del tipo architetturale.
 * La seconda forma ่ utile per dichiarare in modo conciso diverse
 * interazioni architetturali attraverso un meccanismo di
 * indicizzazione. Questo richiede la specifica
 * dell'identificatore indice, che si pu๒ successivamente
 * presentare nell'espressione di selezione, insieme con il suo
 * intervallo, che ่ dato da due espressioni intere.
 * Queste due espressioni devono essere prive da identificatori
 * non dichiarati e invocazioni a generatori di numeri
 * pseudo-casuali, con il valore della prima espressione
 * non piเน grande del valore della seconda espressione
 */

public class ScanInteractionDecl {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di interazione architetturale
	 * secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isInteractionDecl(String specifiche)
		{
		/*
		 * Una dichiarazione di interazione architetturale ha la
		 * seguente forma:
		 *
		 * <architectural_interaction_decl> ::=
		 * <identifier> ["[" <expr> "]"] "."
		 * <identifier> |
		 * "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" "." <identifier>
		 */
		return isInteractionDeclP(specifiche) ||
		isInteractionDeclInd(specifiche);
		}

	/**
	 * Crea un oggetto InteractionDecl, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto InteractionDecl.
	 * @throws ScanException
	 */

	public static InteractionDecl scanInteractionDecl(String specifiche)
		throws ScanException
		{
		/*
		 * Una dichiarazione di interazione architetturale ha la
		 * seguente forma:
		 *
		 * <architectural_interaction_decl> ::=
		 * <identifier> ["[" <expr> "]"] "."
		 * <identifier> |
		 * "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" "." <identifier>
		 */
		InteractionDecl id = new InteractionDecl();
		if (isInteractionDeclP(specifiche))
			id = scanInteractionDeclP(specifiche);
		else if (isInteractionDeclInd(specifiche))
			id = scanInteractionDeclInd(specifiche);
		else throw new ScanException(specifiche+" is not "+
			"architectural interaction declaration");
		return id;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di interazione architetturale semplice
	 * secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isInteractionDeclP(String specifiche)
		{
		/*
		 * Una dichiarazione di interazione architetturale ha la
		 * seguente forma:
		 *
		 * <architectural_interaction_decl> ::=
		 * <identifier> ["[" <expr> "]"] "."
		 * <identifier>
		 */
		boolean ris = false;
		String Aei = new String();
		String S = new String();
		String Interaction = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			// Aei contiene l'identificatore di un Aei con un
			// eventuale selettore
			Aei = s.next();
			if (Aei.contains("["))
				{
				MyScanner s1 = new MyScanner(Aei);
				s1.useDelimiter("\\s*\\[\\s*");
				Aei = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				// S rappresenta il sellettore di un Aei
				S = s1.next();
				s1.skip("\\s*\\]\\s*");
				}
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\.\\s*");
			// Interaction ่ il nome dell'interazione
			// architetturale
			Interaction = s.next();
			ris = ScanIdent.isIdent(Aei) &&
			ScanIdent.isIdent(Interaction);
			if (!S.equals("")) ris = ris &&
			ScanExp.isEspressione(S);
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto InteractionDecl, includendo informazioni
	 * ottenute attraverso la scannerizzazione di una
	 * dichiarazione di interazione architetturale semplice.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto InteractionDecl.
	 * @throws ScanException
	 */

	private static InteractionDecl scanInteractionDeclP(String specifiche)
		throws ScanException
		{
		/*
		 * Una dichiarazione di interazione architetturale ha la
		 * seguente forma:
		 *
		 * <architectural_interaction_decl> ::=
		 * <identifier> ["[" <expr> "]"] "."
		 * <identifier>
		 */
		InteractionDecl id = new InteractionDecl();
		String Aei = new String();
		String S = new String();
		String Interaction = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			// Aei contiene l'identificatore di un Aei con un
			// eventuale selettore
			Aei = s.next();
			if (Aei.contains("["))
				{
				MyScanner s1 = new MyScanner(Aei);
				s1.useDelimiter("\\s*\\[\\s*");
				Aei = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				// S rappresenta il sellettore di un Aei
				S = s1.next();
				s1.skip("\\s*\\]\\s*");
				}
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\.\\s*");
			// Interaction ่ il nome dell'interazione
			// architetturale
			Interaction = s.next();
			id.setAei(ScanIdent.scanIdent(Aei));
			id.setInteraction(ScanIdent.scanIdent(Interaction));
			if (!S.equals("")) id.setSelector(ScanExp.scanEspressione(S));
			return id;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not InteractionDecl object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * a dichiarazioni di interazioni architetturali ottenute
	 * in modo indicizzato.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isInteractionDeclInd(String specifiche)
		{
		/*
		 * Una dichiarazione di interazione architetturale ha la
		 * seguente forma:
		 *
		 * <architectural_interaction_decl> ::=
		 * "FOR_ALL" <identifier>
		 * "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" "." <identifier>
		 */
		String indice = new String();
		String ExprInizio = new String();
		String ExprFine = new String();
		String r = new String();
		boolean ris = false;
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*FOR_ALL\\s*");
			s.useDelimiter("\\s*IN\\s*");
			// identificatore indice
			indice = s.next();
			s.useDelimiter("\\s*\\.\\.\\s*");
			s.skip("\\s*IN\\s*");
			// espressione iniziale
			ExprInizio = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\.\\.\\s*");
			// rim ่ l'espressione finale + la dichiarazione
			// semplice di un'interazione architetturale
			String rim = s.next();
			s = new MyScanner(rim);
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			// del contiene l'espressione finale +
			// il nome dell'Aei con l'eventuale selettore
			String del = s.next();
			MyScanner s1 = new MyScanner(del);
			s1.useDelimiter("\\s*\\[\\s*");
			// adesso del contiene l'espressione finale +
			// il nome dell'Aei senza selettore
			del = s1.next();
			s1 = new MyScanner(del);
			s1.useDelimiter("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			// ExprFine contiene l'espressione finale
			ExprFine = s1.next();
			s = new MyScanner (rim);
			s.useDelimiter("\\s*\\z");
			s.skip(Pattern.quote(ExprFine));
			// r ่ la dichiarazione semplice di
			// un'interazione architetturale
			r = s.next();
			ris = ScanIdent.isIdent(indice) && ScanExp.isEspressione(ExprInizio)
			&& ScanExp.isEspressione(ExprFine) && isInteractionDecl(r);
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto InteractionDeclInd, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto InteractionDeclInd.
	 * @throws ScanException
	 */

	private static InteractionDeclInd scanInteractionDeclInd(String specifiche)
		throws ScanException
		{
		/*
		 * Una dichiarazione di interazione architetturale ha la
		 * seguente forma:
		 *
		 * <architectural_interaction_decl> ::=
		 * "FOR_ALL" <identifier>
		 * "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" "." <identifier>
		 */
		InteractionDeclInd idi = new InteractionDeclInd();
		String indice = new String();
		String ExprInizio = new String();
		String ExprFine = new String();
		String r = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*FOR_ALL\\s*");
			s.useDelimiter("\\s*IN\\s*");
			// identificatore indice
			indice = s.next();
			s.useDelimiter("\\s*\\.\\.\\s*");
			s.skip("\\s*IN\\s*");
			// espressione iniziale
			ExprInizio = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\.\\.\\s*");
			// rim ่ l'espressione finale + la dichiarazione
			// semplice di un'interazione architetturale
			String rim = s.next();
			s = new MyScanner(rim);
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			// del contiene l'espressione finale +
			// il nome dell'Aei con l'eventuale selettore
			String del = s.next();
			MyScanner s1 = new MyScanner(del);
			s1.useDelimiter("\\s*\\[\\s*");
			// adesso del contiene l'espressione finale +
			// il nome dell'Aei senza selettore
			del = s1.next();
			s1 = new MyScanner(del);
			s1.useDelimiter("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			// ExprFine contiene l'espressione finale
			ExprFine = s1.next();
			s = new MyScanner (rim);
			s.useDelimiter("\\s*\\z");
			s.skip(Pattern.quote(ExprFine));
			// r ่ la dichiarazione semplice di
			// un'interazione architetturale
			r = s.next();
			idi.setIndex(ScanIdent.scanIdent(indice));
			idi.setExprInizio(ScanExp.scanEspressione(ExprInizio));
			idi.setExprFine(ScanExp.scanEspressione(ExprFine));
			InteractionDecl id = scanInteractionDeclP(r);
			idi.setAei(id.getAei());
			idi.setInteraction(id.getInteraction());
			idi.setSelector(id.getSelector());
			return idi;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not InteractionDeclInd object",e);
			}
		}
}