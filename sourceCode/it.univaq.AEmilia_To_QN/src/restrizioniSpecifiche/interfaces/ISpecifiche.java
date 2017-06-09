package restrizioniSpecifiche.interfaces;

import java.util.List;

import restrizioniSpecifiche.Interaction;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;
import equivalenzaComportamentale.interfaces.IEquivalenza;

public interface ISpecifiche 
	extends ICompliantSpecificRules
	{

	/*
	 * La lista restituita non è null.
	 */
	public List<AttacDecl> getAttacsDeclInput();

	/*
	 * Restituisce una lista non null.
	 */
	public List<AttacDecl> getAttacsDeclOutput();

	public List<IEquivalenza> getEquivalenzeInput();

	public List<IEquivalenza> getEquivalenzeOutput();

	public List<AEIdecl> getAEIsDeclInput();

	public List<AEIdecl> getAEIsDeclOutput();

	public ArchiType getArchiType();

	public void setArchiType(ArchiType archiType);

	public AEIdecl getIdecl();

	/**
	 * Restituisce i nomi delle istanze di output collegate ad idecl.
	 *
	 * @return
	 */
	public List<String> getInstancesInput();

	/**
	 * Restituisce i nomi delle istanze di input collegate ad idecl.
	 *
	 * @return
	 */
	public List<String> getInstancesOutput();

	/**
	 * Restituisce il nome dell'interazione di input relativa all'iEquivalenza passata come argomento.
	 *
	 * @param iEquivalenza
	 * @return
	 */
	public String getInteractionFromEquivalenzaOutput(IEquivalenza iEquivalenza);

	public String getNomeIstanza();

	public String getNomeTipo();
	
	/**
	 * Restituisce una lista di oggetti interaction con istanze uguale ai nomi
	 * degli AEI di input contenuti in list, e come azioni le interazioni di input
	 * corrispondenti.
	 * list deve essere non null.
	 *
	 * @param list
	 * @return una lista non null.
	 */
	public List<Interaction> getActionsInputFromAttacs(List<AttacDecl> list);
	
	/*
	 * Restituisce un oggetto diverso da null.
	 */
	public List<String> getInputInteractionsNames();
	
	/*
	 * Restituisce una lista vuota se l'elemento base non ha interazioni di output.
	 */
	public List<String> getOutputInteractionsNames();
	
	// restituisce una lista vuota se string non è tra le interazioni di input
	// string non è null.
	public List<String> getOutputsFromInput(String string);
	
	public List<Interaction> getActionsOutputFromAttacs(List<AttacDecl> list);

	}