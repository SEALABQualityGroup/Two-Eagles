package mappingAEIintoElementiBase.secondRelease.struttura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mappingAEIintoElementiBase.struttura.IMappingSpecifiche;
import restrizioniSpecifiche.Interaction;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;

public class MappingSpecifiche2<E extends ISpecifiche> 
	implements IMappingSpecifiche<E> 
	{

	private E specifiche;
	// contiene coppie (string1,string2) dove string1 è il nome dell'azione di input e
	// string2 è il nome del canale di clienti associato.
	// Se string1 non ha un canale associato, allora non è
	// presente in tale mappa.
	HashMap<String, String> classiInput = new HashMap<String, String>();
	// contiene coppie (string1,string2) dove string1 è il nome dell'azione di output e
	// string2 è il nome del canale di clienti associato.
	// Se string1 non ha un canale associato, allora non è
	// presente in tale mappa.
	private HashMap<String, String> classiOutput = new HashMap<String, String>();
	

	public MappingSpecifiche2(E specifiche, HashMap<String, String> 
			classiInput,
			HashMap<String, String> classiOutput)
		{
		super();
		this.specifiche = specifiche;
		this.classiInput = classiInput;
		this.classiOutput = classiOutput;
		}

	public MappingSpecifiche2(E specifiche)
		{
		super();
		this.specifiche = specifiche;
		this.classiInput = new HashMap<String, String>();
		this.classiOutput = new HashMap<String, String>();
		}
	
	@Override
	public boolean containsInputClassMapping(String string)
		{
		if (this.classiInput == null)
			return false;
		else
			{
			if (this.classiInput.containsKey(string))
				return true;
			else
				return false;
			}
		}

	@Override
	public boolean containsOutputClassMapping(String string)
		{
		if (this.classiOutput == null)
			return false;
		else
			{
			if (this.classiOutput.containsKey(string))
				return true;
			else
				return false;
			}
		}
	
	@Override
	public boolean containsInputClassMapping(String string, String string2)
		{
		boolean b = false;
		if (this.classiInput == null)
			b = false;
		else
			{
			if (this.classiInput.containsKey(string))
				{
				String string3 = this.classiInput.get(string);
				if (string3.equals(string2))
					b = true;
				else
					b = false;
				}
			else b = false;
			}
		return b;
		}

	@Override
	public boolean containsOutputClassMapping(String string, String string2)
		{
		boolean b = false;
		if (this.classiOutput == null)
			b = false;
		else
			{
			if (this.classiOutput.containsKey(string))
				{
				String string3 = this.classiOutput.get(string);
				if (string3.equals(string2))
					b = true;
				else 
					b = false;
				}
			else b = false;
			}
		return b;
		}

	@Override
	public ArchiType getArchiType() 
		{
		return specifiche.getArchiType();
		}

	@Override
	public HashMap<String, String> getClassiInput() 
		{
		return classiInput;
		}

	@Override
	public HashMap<String, String> getClassiOutput() 
		{
		return classiOutput;
		}

	@Override
	public List<String> getInputInteractions()
		{
		List<String> list = this.specifiche.getInputInteractionsNames();
		return list;
		}

	@Override
	public List<Interaction> getInteractionsInputFromOtherInstanceOutput(
			String string, String string32) 
		{
		// list2 è l'insieme dei collegamenti in cui specifiche
		// risulta essere l'istanza di input.
		List<AttacDecl> list2 = this.specifiche.getAttacsDeclInput();
		// tra i collegamenti di list2, preleviamo quei collegamenti che hanno string
		// come interazione di output e string32 come istanza di output.
		// per precondizione list2 non è null.
		List<AttacDecl> list4 = getAttacsFromOutputInteractions(list2,string,string32);
		// per precondizione list4 non è null
		// list3 è l'insieme delle interazioni di input di list4
		List<Interaction> list3 = this.specifiche.getActionsInputFromAttacs(list4);
		// per precondizione list3 non è null
		return list3;
		}

	@Override
	public List<Interaction> getInteractionsInputFromOutput(String string) 
		{
		// list2 è l'insieme dei collegamenti in cui specifiche
		// risulta essere l'istanza di output
		List<AttacDecl> list2 = this.specifiche.getAttacsDeclOutput();
		// tra i collegamenti di list2, preleviamo quei collegamenti che hanno string
		// come interazione di output.
		// per precondizione list2 non è null
		List<AttacDecl> list4 = getAttacsFromOutputInteractions(list2,string,this.getNomeIstanza());
		// list3 è l'insieme delle interazioni di input di list4
		List<Interaction> list3 = this.specifiche.getActionsInputFromAttacs(list4);
		// per precondizione list3 non è null
		return list3;
		}

	@Override
	public String getNomeIstanza() 
		{
		AEIdecl idecl = this.specifiche.getIdecl();
		return idecl.getName();
		}

	@Override
	public List<String> getOutputInteractions()
		{
		List<String> list = this.specifiche.getOutputInteractionsNames();
		// per precondizione list non è null.
		return list;
		}

	@Override
	public List<String> getOutputsFromInput(String string3)
		{
		List<String> list = this.specifiche.getOutputsFromInput(string3);
		return list;
		}

	@Override
	public E getSpecifiche() 
		{
		return specifiche;
		}

	@Override
	public String getTipoIstanza() 
		{
		return getIdecl().getAET();
		}

	@Override
	public void setClassiInput(HashMap<String, String> classiInput) 
		{
		this.classiInput = classiInput;
		}

	@Override
	public void setClassiOutput(HashMap<String, String> classiOutput) 
		{
		this.classiOutput = classiOutput;
		}

	@Override
	public void updateClassMaps(List<String> list2, List<String> list3,
			String string) 
		{
		// si aggiorna la mappa delle classi di input
		for (String string2 : list2)
			{
			this.classiInput.put(string2, string);
			}
		// si aggiorna la mappa delle classi di output
		for (String string2 : list3)
			{
			// per precondizione le HashMap permettono come chiave null
			this.classiOutput.put(string2, string);
			}
		}
	
	/**
	 * A partire da list2, restituisce una lista di collegamenti in cui
	 * string è il nome dell'interazione di output e 
	 * string32 è il nome dell'istanza di output. 
	 * list2 deve essere non null.
	 * 
	 * @param list2
	 * @param string
	 * @param string32
	 * @return una lista non null.
	 */
	private List<AttacDecl> getAttacsFromOutputInteractions(
			List<AttacDecl> list2,String string, String string32)
		{
		List<AttacDecl> list = new ArrayList<AttacDecl>();
		// per precondizione list2 non è null.
		for (AttacDecl attacDecl : list2)
			{
			String string2 = attacDecl.getOutputInteraction();
			String string3 = attacDecl.getOutputAei();
			if (string2.equals(string) && string3.equals(string32))
				list.add(attacDecl);
			}
		return list;
		}
	
	private AEIdecl getIdecl()
		{
		return specifiche.getIdecl();
		}
	}
