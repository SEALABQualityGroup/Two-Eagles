package mappingAEIintoElementiBase.secondRelease.struttura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.struttura.AListaMappingSpecifiche;
import mappingAEIintoElementiBase.struttura.IMappingSpecifiche;
import restrizioniSpecifiche.Interaction;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheArriviFiniti;
import restrizioniSpecifiche.secondRelease.SpecificheRules2;
import specificheAEmilia.ArchiAttachments;
import specificheAEmilia.ArchiTopology;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;

public class ListaMappingSpecifiche2 
	extends AListaMappingSpecifiche 
	{

	private static final long serialVersionUID = 1L;

	public static <E extends ISpecifiche> IMappingSpecifiche<E> createMappingSpecifiche(
			E specifiche, HashMap<String, String> classiInput,
			HashMap<String, String> classiOutput) 
		{
		return new MappingSpecifiche2<E>(specifiche, classiInput, classiOutput);
		}

	public static <E extends ISpecifiche> IMappingSpecifiche<E> createMappingSpecifiche(
			E specifiche) 
		{
		return new MappingSpecifiche2<E>(specifiche);
		}

	/*
	 * (non-Javadoc)
	 * @see mappingAEIintoElementiBase.struttura.AListaMappingSpecifiche#getArchiType()
	 */
	@Override
	public ArchiType getArchiType() 
		throws AEIintoElementiBaseException 
		{
		if (this.isEmpty())
			throw new AEIintoElementiBaseException("The list is empty");
		IMappingSpecifiche<ISpecifiche> mappingSpecifiche =
			this.get(0);
		ArchiType archiType = mappingSpecifiche.getArchiType();
		return archiType;
		}

	@Override
	public List<AttacDecl> getAttacDecls() 
		throws AEIintoElementiBaseException 
		{
		ArchiType archiType = getArchiType();
		ArchiTopology archiTopology = 
			archiType.getTopologia();
		ArchiAttachments archiAttachments =
			archiTopology.getAttachments();
		AttacDecl[] attacDecls = archiAttachments.getAttachments();
		// attacDecls può essere null. In questo caso si restituisce un'array vuoto.
		List<AttacDecl> list = null;
		if (attacDecls == null)
			list = new CopyOnWriteArrayList<AttacDecl>();
		else
			list = new CopyOnWriteArrayList<AttacDecl>(attacDecls);
		return list;
		}

	@Override
	public List<IMappingSpecifiche<ISpecifiche>> getListaMappingFromOutput(
			String string, IMappingSpecifiche<ISpecifiche> mappingSpecifiche) 
		{
		List<IMappingSpecifiche<ISpecifiche>> list = new ArrayList<IMappingSpecifiche<ISpecifiche>>();
		// si prelevano le istanze collegate a string con le relative interazioni di input,
		// che le appartengono
		List<Interaction> list2 = mappingSpecifiche.getInteractionsInputFromOutput(string);
		// si prelevano i nomi delle istanze presenti negli elementi di list2
		List<String> list3 = SpecificheRules2.getInstances(list2);
		// si cercano gli oggetti MappingSpecifiche che wrappano istanze con nome contenuto
		// in list3
		for (IMappingSpecifiche<ISpecifiche> mappingSpecifiche2 : this)
			{
			String string2 = mappingSpecifiche2.getNomeIstanza();
			if (list3.contains(string2))
				{
				list.add(mappingSpecifiche2);
				}
			}
		return list;
		}

	@Override
	public IMappingSpecifiche<ISpecifiche> getMappingSpecificheFromInstanceName(
			String string) 
		{
		for (IMappingSpecifiche<ISpecifiche> mappingSpecifiche : this)
			{
			String string2 = mappingSpecifiche.getNomeIstanza();
			if (string2.equals(string))
				return mappingSpecifiche;
			}
		return null;
		}

	@Override
	public boolean updateClassInDepth(
			IMappingSpecifiche<ISpecifiche> mappingSpecifiche2, String string,
			String string2, String string32)
		{
		boolean ris = true;
		// si prelevano le interazioni di input di mappingSpecifiche2 che sono connesse a string2 come
		// interazione di output e string32 come istanza di output. 
		List<Interaction> list = mappingSpecifiche2.getInteractionsInputFromOtherInstanceOutput(
				string2,string32);
		// per precondizione list non è null
		List<String> list2 = getActions(list);
		// per preconzione list2 non è null
		// si prelevano le interazioni di output associate alle interazioni di input precedentemente
		// trovate e contenute in list2
		List<String> list3 = new ArrayList<String>();
		for (String string3 : list2)
			{
			List<String> list4 = mappingSpecifiche2.getOutputsFromInput(string3);
			// come precondizione list4 non è null
			list3.addAll(list4);
			}
		// si prosegue solo se in list2 e list3 c'è almeno un elemento che non ha un canale
		// associato. Si 'screma' list2 e list3 in modo da considerare solo gli elementi che non hanno
		// un canale associato
		String[] strings = new String[list2.size()];
		list2.toArray(strings);
		// come precondizione strings non è null
		CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>(strings);
		for (String string3 : copyOnWriteArrayList)
			{
			if (mappingSpecifiche2.containsInputClassMapping(string3) && 
					// per precondizione string non è null
					!mappingSpecifiche2.containsInputClassMapping(string3, string))
				ris = ris && false;
			if (mappingSpecifiche2.containsInputClassMapping(string3, string))
				list2.remove(string3);
			}
		String[] strings2 = new String[list3.size()];
		list3.toArray(strings2);
		CopyOnWriteArrayList<String> copyOnWriteArrayList2 = new CopyOnWriteArrayList<String>(strings2);
		for (String string3 : copyOnWriteArrayList2)
			{
			// string3 può essere null
			if (string3 != null)
				{
				if (mappingSpecifiche2.containsOutputClassMapping(string3) && 
					!mappingSpecifiche2.containsOutputClassMapping(string3, string))
					ris = ris && false;
				if (mappingSpecifiche2.containsOutputClassMapping(string3, string))
					list3.remove(string3);
				}
			}
		// si aggiornano le mappe delle classi di mappingSpecifiche2
		mappingSpecifiche2.updateClassMaps(list2,list3,string);
		// si prelevano gli oggetti MappingSpecifiche connessi alle interazioni di output
		// presenti in list3
		String string4 = mappingSpecifiche2.getNomeIstanza();
		for (String string3 : list3)
			{
			// string3 può essere null
			if (string3 != null)
				{
				List<IMappingSpecifiche<ISpecifiche>> list5 = getListaMappingFromOutput(string3, 
					mappingSpecifiche2);
				// per precondizione list5 è non null.
				// string3 è non null.
				// string4 è non null.
				// si richiama ricorsivamente la funzione updateClassInDepth
				// su ogni elemento di list5,
				// string3 come interazione di output,
				// string4 come istanza di output,
				// string come canale.
				for (IMappingSpecifiche<ISpecifiche> mappingSpecifiche : list5)
					{
					// se mappingSpecifiche wrappa un'iEquivalenza di arrivi finiti
					// non c'è la chiamata ricorsiva
					ISpecifiche specifiche = mappingSpecifiche.getSpecifiche();
					if (!(specifiche instanceof ISpecificheArriviFiniti))
						ris = ris && updateClassInDepth(mappingSpecifiche, string, string3, string4);
					else
						{
						// si verifica che mappingSpecifiche ha nome string per garantire
						// che delle interazioni di output sono connesse alle azioni return dello stesso
						// canale
						String string5 = mappingSpecifiche.getNomeIstanza();
						if (string5.equals(string))
							ris = ris && true;
						else
							ris = ris && false;
						}
					}
				}
			}
		return ris;
		}

	/**
	 * Restituisce una lista di azioni presenti nelle interazioni di list.
	 *
	 * @param list è non null.
	 * @return una lista non null.
	 */
	private List<String> getActions(List<Interaction> list)
		{
		List<String> list2 = new ArrayList<String>();
		for (Interaction interaction : list)
			{
			String string = interaction.getAction();
			list2.add(string);
			}
		return list2;
		}

	}
