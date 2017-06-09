package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import elementiBaseQN.ElementoBaseQN;

public class AggregatoProcessoArriviRitorni extends
		ArrayList<DataProcessoArriviRitorno> 
	{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Restituisce l'elemento base connesso all'ordineRitorno-esima azione di ritorno.
	 * Restituisce null se ordineRitorno è null oppure non viene trovato.
	 */
	public ElementoBaseQN getRitornoFromOrdine(Integer ordineRitorno)
		{
		if (ordineRitorno == null) return null;
		ElementoBaseQN elementoBaseQN = null;
		for (DataProcessoArriviRitorno dataProcessoArriviRitorno : this)
			{
			Integer integer = dataProcessoArriviRitorno.getOrdineRitorno();
			if (ordineRitorno.equals(integer))
				elementoBaseQN = dataProcessoArriviRitorno.getSorgente();
			}
		return elementoBaseQN;
		}
	
	/**
	 * Restituisce il nome dell'ordineRitorno-esima azione di ritorno.
	 * Restituisce null se ordineRitorno è null oppure non viene trovato. 
	 */
	public String getNomeAzioneRitornoFromOrdine(Integer ordineRitorno)
		{
		if (ordineRitorno == null) return null;
		String string = null;
		for (DataProcessoArriviRitorno dataProcessoArriviRitorno : this)
			{
			Integer integer = dataProcessoArriviRitorno.getOrdineRitorno();
			if (ordineRitorno.equals(integer))
				string = dataProcessoArriviRitorno.getNomeAzioneRitorno();
			}
		return string;
		}
	
	/**
	 * Restituisce l'elemento base connesso all'azione di ritorno di nome nomeAzioneRitorno.
	 * Restituisce null se nomeAzioneRitorno è null oppure non viene trovato. 
	 */
	public ElementoBaseQN getRitornoFromNomeAzioneRitorno(String nomeAzioneRitorno)
		{
		if (nomeAzioneRitorno == null) return null;
		ElementoBaseQN elementoBaseQN = null;
		for (DataProcessoArriviRitorno dataProcessoArriviRitorno : this)
			{
			String string = dataProcessoArriviRitorno.getNomeAzioneRitorno();
			if (nomeAzioneRitorno.equals(string))
				elementoBaseQN = dataProcessoArriviRitorno.getSorgente();
			}
		return elementoBaseQN;
		}
	
	/**
	 * Restituisce l'ordine dell'azione di ritorno di nome nomeAzioneRitorno.
	 * Restituisce null se nomeAzioneRitorno è null oppure non viene trovato. 
	 */
	public Integer getOrdineRitornoFromNome(String nomeAzioneRitorno)
		{
		if (nomeAzioneRitorno == null) return null;
		Integer integer = null;
		for (DataProcessoArriviRitorno dataProcessoArriviRitorno : this)
			{
			String string = dataProcessoArriviRitorno.getNomeAzioneRitorno();
			if (nomeAzioneRitorno.equals(string))
				integer = dataProcessoArriviRitorno.getOrdineRitorno();
			}
		return integer;
		}

	public AggregatoProcessoArriviRitorni(List<String> list) 
		{
		super();
		for (int i = 0; i < list.size(); i++)
			{
			DataProcessoArriviRitorno dataProcessoArriviRitorno =
				new DataProcessoArriviRitorno();
			dataProcessoArriviRitorno.setOrdineRitorno(i);
			dataProcessoArriviRitorno.setNomeAzioneRitorno(list.get(i));
			this.add(dataProcessoArriviRitorno);
			}
		}

	public AggregatoProcessoArriviRitorni() 
		{
		super();
		}
	
	/**
	 * Restituisce la struttura per l'interazione di input relativa all'azione di nome string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneInput getStrutturaInterazioneInputFromRitorno(String string)
		{
		StrutturaInterazioneInput strutturaInterazioneInput = null;
		for (DataProcessoArriviRitorno dataProcessoArriviRitorno : this)
			{
			String string2 = dataProcessoArriviRitorno.getNomeAzioneRitorno();
			if (string.equals(string2))
				strutturaInterazioneInput = dataProcessoArriviRitorno;
			}
		return strutturaInterazioneInput;
		}
	
	/**
	 * Si sostituisce l'elemento base elementoBaseQN con. Per precondizione la taglia di list
	 * deve essere 1. 
	 * 
	 * @param elementoBaseQN
	 * @param list
	 */
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list)
		{
		// si cerca il ritorno elementoBaseQN
		for (DataProcessoArriviRitorno dataProcessoArriviRitorno : this)
			{
			ElementoBaseQN elementoBaseQN2 = dataProcessoArriviRitorno.getSorgente();
			if (elementoBaseQN.equals(elementoBaseQN2))
				{
				// si sostituisce la vecchia sorgente con il primo elemento di list
				dataProcessoArriviRitorno.setSorgente(list.get(0));
				}
			}
		}
	}