package mappingAEIintoElementiBase.struttura;

import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;

public abstract class AListaMappingSpecifiche
	extends ArrayList<IMappingSpecifiche<ISpecifiche>> 
	{

	private static final long serialVersionUID = 1L;

	/**
	 * Restituisce una lista di oggetti MappingSpecifiche connessi 
	 * come istanze di input a mappingSpecifiche,
	 * in cui string è il nome dell'interazione di output e 
	 * l'istanza wrappata da mappingSpecifiche come
	 * istanza di output.
	 * mappingSpecifiche deve essere non null.
	 * 
	 * @param string
	 * @param mappingSpecifiche
	 * @return una lista non null.
	 */
	public abstract List<IMappingSpecifiche<ISpecifiche>> getListaMappingFromOutput(
			String string, IMappingSpecifiche<ISpecifiche> mappingSpecifiche);

	/**
	 * Restituisce l'oggetto MappingSpecifiche che ha string come nome dell'istanza wrappata.
	 * Se string non viene trovato, si restituisce null.
	 * string è non null.
	 * 
	 * @param string
	 * @return
	 */
	public abstract IMappingSpecifiche<ISpecifiche> getMappingSpecificheFromInstanceName(
			String string);

	/*
	 * Restituisce true se e solo se l'oggetto 
	 * mappingSpecifiche2 ha interazioni di input e output associate con classe uguale a string,
	 * oppure non hanno nessuna classe associata. Le interazioni di input da aggiornare
	 * sono conesse a string2, mentre string32 è il nome dell'istanza di output.
	 */
	/**
	 * Si aggiornano le mappe delle classi di mappingSpecifiche2 secondo string.
	 * 
	 * @param mappingSpecifiche2 deve essere non null.
	 * @param string è il nome del canale, che non è null.
	 * @param string2 è il nome dell'interazione di output collegata all'istanza da aggiornare.
	 * @param string32 è il nome dell'istanza di output.
	 * @throws AEIintoElementiBaseException
	 */
	public abstract boolean updateClassInDepth(
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche2, String string,
			String string2, String string32);

	/**
	 * Restituisce l'archiType prelevandolo dal primo oggetto mappinSpecifiche contenuto in this.
	 * Solleva un'eccezione se this è vuoto, cioè non ha nessun oggetto MappingSpecifiche.
	 * 
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	public abstract ArchiType getArchiType()
			throws AEIintoElementiBaseException;

	/**
	 * Restituisce le dichiarazioni di collegamenti dall'istanza 
	 * del tipo architetturale. Restituisce un array vuoto se la specifica non ha
	 * dichiarazioni di collegamenti architetturali.
	 * 
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	public abstract List<AttacDecl> getAttacDecls()
			throws AEIintoElementiBaseException;	
	}