package elementiBaseQN;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazione;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;

public class ElementoBaseQN implements 
	StrutturaInterazione, Serializable
	{

	private static final long serialVersionUID = 1L;

	private String nome;

	public ElementoBaseQN()
		{
		super();
		}

	public ElementoBaseQN(String nome) 
		{
		super();
		this.nome = nome;
		}

	public String getNome() 
		{
		return nome;
		}

	public void setNome(String nome) 
		{
		this.nome = nome;
		}

	/**
	 * Restituisce true se e solo se obj è di tipo ElementoBaseQN ed ha lo stesso nome
	 * di questo oggetto.
	 */
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ElementoBaseQN)) return false;
		ElementoBaseQN elementoBaseQN = (ElementoBaseQN)obj;
		return nome.equals(elementoBaseQN.getNome());
		}

	@Override
	public StrutturaInterazioneInput getStrutturaInput(String string) 
		{
		return null;
		}

	@Override
	public StrutturaInterazioneOutput getStrutturaOutput(String string) 
		{
		return null;
		}

	@Override
	public void setBuffers()
		throws ElementoBaseException
		{
		}
	
	/**
	 * Restituisce true se e solo se list contiene un elemento base con lo stesso nome
	 * di quello relativo a this.
	 *  
	 * @param list
	 * @return
	 */
	public boolean isContainedName(Collection<ElementoBaseQN> list)
		{
		String string = getNome();
		for (ElementoBaseQN elementoBaseQN2 : list)
			{
			String string2 = elementoBaseQN2.getNome();
			if (string.equals(string2))
				return true;
			}
		return false;
		}
	
	/**
	 * Restituisce true se e solo se elementoBaseQN ha lo stesso nome di quello relativo
	 * a this.
	 * 
	 * @param elementoBaseQN
	 * @return
	 */
	public boolean isContainedName(ElementoBaseQN elementoBaseQN)
		{
		String string = getNome();
		String string2 = elementoBaseQN.getNome();
		if (string.equals(string2))
			return true;
		else
			return false;
		}

	@Override
	public void proporzionaProbabilita() 
		throws ElementoBaseException 
		{
		}
	
	/**
	 * Sostituisce elementoBaseQN con ogni elemento base contenuto in list.
	 * 
	 */
	public void replaceSource(ElementoBaseQN elementoBaseQN, List<ElementoBaseQN> list)
		{
		}
	
	public void replaceDestination(Destinazione destinazione, List<Destinazione> list)
		{
		
		}
}
