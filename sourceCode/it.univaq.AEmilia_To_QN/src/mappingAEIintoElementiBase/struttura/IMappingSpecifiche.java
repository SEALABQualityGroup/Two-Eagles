package mappingAEIintoElementiBase.struttura;

import java.util.HashMap;
import java.util.List;

import restrizioniSpecifiche.Interaction;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import specificheAEmilia.ArchiType;

public interface IMappingSpecifiche<E extends ISpecifiche> {

	public abstract void setClassiInput(HashMap<String, String> classiInput);

	public abstract HashMap<String, String> getClassiOutput();

	public abstract void setClassiOutput(HashMap<String, String> classiOutput);

	public abstract E getSpecifiche();

	/*
	 * Come precondizione la dichiarazione dell'istanza deve essere non null.
	 */
	public abstract String getNomeIstanza();

	/**
	 * Restituisce una lista di interazioni di input relativa all'istanza wrappata 
	 * dall'oggetto Specifiche.
	 * Restituisce un oggetto diverso da null.
	 * 
	 * @return
	 */
	public abstract List<String> getInputInteractions();

	/**
	 * Restituisce una lista di tutte le interazioni di output relative all'istanza wrappata
	 * dall'oggetto Specifiche.
	 * La lista restituita non è null.
	 *
	 * @return
	 */
	public abstract List<String> getOutputInteractions();

	/*
	 * La dichiarazione dell'istanza associata deve essere non null.
	 */
	public abstract String getTipoIstanza();

	/**
	 * Restituisce una lista di interazioni in cui: string è il nome dell'interazione di output,
	 * appartenenti a collegamenti  
	 * in cui gli elementi restituiti desfiniscono l'interazione e l'istanza di input.
	 * 
	 * @param string
	 * @return una lista non null
	 */
	public abstract List<Interaction> getInteractionsInputFromOutput(
			String string);

	/**
	 * Si prelevano le interazioni di input di this che sono connesse a string come
	 * interazione di output e string32 come istanza di output. 
	 * 
	 * @param string32 deve essere non null.
	 * @return una lista non null.
	 */
	public abstract List<Interaction> getInteractionsInputFromOtherInstanceOutput(
			String string, String string32);

	/**
	 * Restituisce true se e solo se la mappa per le classi di input contiene una
	 * entry (string,string2).
	 * 
	 * @param string è il nome dell'interazione di input, che non deve essere null.
	 * @param string2 è il nome del canale, che non deve essere null.
	 * @return
	 */
	public abstract boolean containsInputClassMapping(String string,
			String string2);

	/**
	 * Restituisce true se e solo se la mappa per le classi di output contiene una
	 * entry (string,string2).
	 * 
	 * @param string è il nome dell'interazione di output.
	 * @param string2 è il nome del canale.
	 * @return
	 */
	public abstract boolean containsOutputClassMapping(String string,
			String string2);

	/**
	 * Restituisce gli output associati nel comportamento
	 * all'interazione di input string3 di this.
	 * Può restituire un array con elementi null.
	 * 
	 * @param string3
	 * @return una lista vuota se non c'è nessuna interazione di output associata a string3.
	 */
	public abstract List<String> getOutputsFromInput(String string3);

	/**
	 * Aggiorno la mappa delle classi di input e di output secondo string.
	 * Per precondizione ho che tali mapping non sono già presenti nella mappa.
	 * 
	 * @param list2 è la lista delle interazioni di input, che non ha elementi null.
	 * @param list3 è la lista delle interazioni di output, che può contenere dei null.
	 * @param string è il nome del canale, che non è null.
	 */
	public abstract void updateClassMaps(List<String> list2,
			List<String> list3, String string);

	public abstract ArchiType getArchiType();

	public HashMap<String, String> getClassiInput();

	/*
	 * Restituisce true se e solo se esiste un'associazione con una classe di clienti
	 * per l'interazione di input string. Per precondizione string non è null.
	 */
	boolean containsInputClassMapping(String string);

	/*
	 * Restituisce true se e solo se esiste un'associazione con una classe di clienti
	 * per l'interazione di output string.
	 */
	boolean containsOutputClassMapping(String string);

}