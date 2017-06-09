package restrizioniSpecifiche;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.interfaces.ISpecifiche;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiType;
import specificheAEmilia.ElemType;
import specificheAEmilia.ORinteractions;
import specificheAEmilia.OutputInteractions;
import equivalenzaComportamentale.interfaces.IEquivalenza;

public abstract class ASpecificheRules 	
	implements ISpecificheRules
	{

	protected ArchiType archiType;
	protected List<ISpecifiche> listaSpecifiche;

	/**
	 * Restituisce true se e solo se output è una or-interazione di output di elemType.
	 *
	 * @param elemType
	 * @param output
	 * @return
	 */
	public static boolean isOrOutput(ElemType elemType, String output) {
	AETinteractions tinteractions = elemType.getInteractions();
	OutputInteractions outputInteractions = tinteractions.getOuIn();
	if (outputInteractions == null) return false;
	ORinteractions rinteractions = outputInteractions.getOr();
	if (rinteractions == null) return false;
	String[] strings = rinteractions.getActions();
	CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>(strings);
	if (copyOnWriteArrayList.contains(output)) return true;
	else return false;
	}

	public List<ISpecifiche> getListaSpecifiche() {
	return listaSpecifiche;
	}

	/**
	 * Restituisce il nome dell'AET relativo a string e presente in list.
	 *
	 * @param list
	 * @param string
	 * @return
	 */
	public static String getAETFromAEI(List<AEIdecl> list, String string) {
	// prelevo l'aet di string
	String string2 = null;
	for (AEIdecl idecl : list)
		{
		String string3 = idecl.getName();
		if (string.equals(string3))
			string2 = idecl.getAET();
		}
	return string2;
	}

	/**
	 * Restituisce una lista di equivalenzaFactory a partire da list, tale che ogni iEquivalenza contenuta
	 * ha il tipo di elemento architetturale con nome contenuto in list2.
	 *
	 * @param list
	 * @param list2
	 * @return
	 */
	public static List<IEquivalenza> getEquivalenzeFromAETs(List<IEquivalenza> list, List<String> list2) {
	List<IEquivalenza> list3 = new ArrayList<IEquivalenza>();
	for (String string : list2)
		{
		for (IEquivalenza iEquivalenza : list)
			{
			ElemType elemType = iEquivalenza.getEt();
			String string2 = elemType.getHeader().getName();
			if (string2.equals(string))
				list3.add(iEquivalenza);
			}
		}
	return list3;
	}

	/**
	 * Restituisce l'iEquivalenza presente in list, che ha come nome di tipo di elemento
	 * architetturale uguale a sstring.
	 *
	 * @param list
	 * @param string
	 * @return
	 */
	public static IEquivalenza getEquivalenzaFromElemTypeName(List<IEquivalenza> list,
			String string) {
			// si preleva l'iEquivalenza relativa a string
			IEquivalenza iEquivalenza = null;
			for (IEquivalenza equivalenza2 : list)
				{
				ElemType elemType2 = equivalenza2.getEt();
				String string2 = elemType2.getHeader().getName();
				if (string.equals(string2)) iEquivalenza = equivalenza2;
				}
			return iEquivalenza;
			}

	/**
	 * Restituisce i nome degli AET contenuti in list.
	 *
	 * @param list
	 * @return
	 */
	public static List<String> getAETsFromAEIDecls(List<AEIdecl> list) {
	List<String> list2 = new ArrayList<String>();
	for (AEIdecl idecl : list)
		{
		list2.add(idecl.getAET());
		}
	return list2;
	}

	/**
	 * Restituisce una lista di dichiarazioni di istanze che hanno lo stesso nome delle stringhe contenute in list.
	 *
	 * @param collection
	 * @param archiElemInstances
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public static List<AEIdecl> getAEIsFromNames(Collection<String> collection, ArchiElemInstances archiElemInstances)
			throws RestrizioniSpecException {
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
	 * Restituisce la lista di dichiarazioni di istanze del tipo di elemento architetturale di nome string.
	 *
	 * @param string
	 * @param archiElemInstances
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public static List<AEIdecl> getAEIsFromAET(String string, ArchiElemInstances archiElemInstances)
			throws RestrizioniSpecException {
			List<AEIdecl> list2 = new ArrayList<AEIdecl>();
			AEIdecl[] idecls = archiElemInstances.getAEIdeclSeq();
			boolean b = false;
			for (int i = 0; i <idecls.length; i++)
				{
				String string2 = idecls[i].getAET();
				if (string.equals(string2))
					{
					b = true;
					list2.add(idecls[i]);
					}
				}
			if (!b) throw new RestrizioniSpecException(string+" is not found");
			return list2;
			}

	/**
	 * Restituisce true se e solo se specifiche e specifiche2 hanno le stesse interazioni di output.
	 *
	 * @param specifiche
	 * @param specifiche2
	 * @return
	 */
	public static boolean sameOutputInteractions(ISpecifiche specifiche, ISpecifiche specifiche2) {
	List<Interaction> list = specifiche.getActionsOutputFromAttacs(specifiche.getAttacsDeclInput());
	List<Interaction> list2 = specifiche2.getActionsOutputFromAttacs(specifiche2.getAttacsDeclInput());
	if (list.size() != list2.size()) return false;
	for (Interaction interaction : list)
		{
		if (!list2.contains(interaction)) return false;
		}
	return true;
	}

	/**
	 * Restiutisce true se e solo se specifiche e specifiche2 hanno le stesse interazioni di input.
	 *
	 * @param specifiche
	 * @param specifiche2
	 * @return
	 */
	public static boolean sameInputInteractions(ISpecifiche specifiche, ISpecifiche specifiche2) {
	List<Interaction> list = specifiche.getActionsInputFromAttacs(specifiche.getAttacsDeclOutput());
	List<Interaction> list2 = specifiche2.getActionsInputFromAttacs(specifiche2.getAttacsDeclOutput());
	if (list.size() != list2.size()) return false;
	for (Interaction interaction : list)
		{
		if (!list2.contains(interaction)) return false;
		}
	return true;
	}

	/**
	 * Restituisce una lista di istanze presenti nelle interazioni di list.
	 *
	 * @param list
	 * @return
	 */
	public static List<String> getInstances(List<Interaction> list) {
	List<String> list2 = new ArrayList<String>();
	for (Interaction interaction : list)
		{
		String string = interaction.getInstance();
		list2.add(string);
		}
	return list2;
	}

	public ASpecificheRules() {
		super();
	}

}