package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.List;

import elementiBaseQN.Buffer;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;
import elementiBaseQN.ProcessoServizio;
import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataBuffer;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import specificheAEmilia.Expression;
import specificheAEmilia.Real;
import valutazione.NormalizeException;
import valutazione.Valutazione;

class MetodiVari 
	{
	
	/**
	 * Restituisce una lista di Double corrispondente a espressiones.
	 * La somma di tutti i Double restituiti è 1.
	 * 
	 * @param espressiones non contiene null.
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	static List<Double> getDoubleFromEspressioniProportioned(Expression[] espressiones)
		throws AEIintoElementiBaseException
		{
		List<Double> list = new ArrayList<Double>();
		// si trasformano le espressioni delle probabilità in
		// double e si aggiungono a list
		if (espressiones == null) return list;
		for (Expression expression : espressiones)
			{
			Valutazione valutazione = new Valutazione();
			Object object;
			try {
				object = valutazione.returnDouble(expression);
				} 
			catch (NormalizeException e) 
				{
				throw new AEIintoElementiBaseException(e);
				}
			// per precondizone object deve essere un Double
			Double double1 = (Double)object;
			list.add(double1);
			}
		// si sommano gli elementi di list
		double d = 0;
		for (Double double1 : list)
			{
			d = d + double1;
			}
		// si aggiorna ogni elemento di list dividendolo per la somma degli elementi
		for (int i = 0; i < list.size(); i++)
			{
			Double double1 = list.get(i);
			double1 = double1 / d;
			list.remove(i);
			list.add(i, double1);
			}
		return list;
		}	
	
	/**
	 * Restituisce una lista di interi corrispondenti a espressiones.
	 * 
	 * @param espressiones non contiene null
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	static List<Integer> getIntegerFromEspressioni(Expression[] espressiones)
		throws AEIintoElementiBaseException
		{
		List<Integer> list = new ArrayList<Integer>();
		// si trasformano le espressioni in Integer e si aggiungono a list
		if (espressiones == null) return list;
		for (Expression expression : espressiones)
			{
			if (!(equivalenzaComportamentale.MetodiVari.isOnlyInteger(expression)) && 
					!(equivalenzaComportamentale.MetodiVari.isOnlyReal(expression)))
				// per precondizione espressiones non contiene null
				throw new AEIintoElementiBaseException("Is not a number");
			if (equivalenzaComportamentale.MetodiVari.isOnlyInteger(expression))
				{
				specificheAEmilia.Integer integer = (specificheAEmilia.Integer)expression;
				int i = integer.getValore();
				list.add(i);
				}
			if (equivalenzaComportamentale.MetodiVari.isOnlyReal(expression))
				{
				Real real = (Real)expression;
				double d = real.getValore();
				Double double1 = new Double(d);
				int i = double1.intValue();
				list.add(i);
				}
			}
		return list;
		}
	
	// restituisce false se elementoBaseQN non viene trovato in list, secondo un'agualianza a 'vista' 
	public static boolean isViewIn(List<Destinazione> list, 
			ElementoBaseQN elementoBaseQN)
		{
		// modificare tale metodo, indicando nell'uguaglianza anche l'indice
		// del DataBuffer o DataProcessoServizioSorgenti nel caso in cui
		// l'elemento base sia un buffer o un processo di servizio.
		String string = elementoBaseQN.getNome();
		for (int i = 0; i < list.size(); i++)
			{
			Destinazione destinazione = list.get(i);
			Integer integer = destinazione.getOrdineClasse();
			ElementoBaseQN elementoBaseQN2 = destinazione.getElementoBaseQN();
			// elementoBaseQN2 può essere null
			if (elementoBaseQN2 != null)
				{
				String string2 = elementoBaseQN2.getNome();
				if (elementoBaseQN instanceof Buffer<?>)
					{
					Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
					if (string.equals(string2))
						{
						// verifichiamo l'indice dell'unico DataBuffer contenuto in elementoBaseQN
						AggregatoBuffer<?> aggregatoBuffer = buffer.getAggregatoBuffer();
						Object object = aggregatoBuffer.get(0);
						// per precondizione object deve essere un DataBuffer
						DataBuffer dataBuffer = (DataBuffer)object;
						int j = dataBuffer.getOrdineClasse();
						if (j == integer)
							return true;
						}
					}
				else if (elementoBaseQN instanceof ProcessoServizio)
					{
					ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
					if (string.equals(string2))
						{
						// verifichiamo l'indice dell'unico DataProcessoServizioSorgente
						AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
							processoServizio.getAggregatoProcessoServizioSorgenti();
						DataProcessoServizioSorgente dataProcessoServizioSorgente =
							aggregatoProcessoServizioSorgenti.get(0);
						int j = dataProcessoServizioSorgente.getOrdineClasse();
						if (j == integer)
							return true;
						}
					}
				else
					{
					if (string.equals(string2))
						return true;
					}
				}
			}
		return false;
		}

	// restituisce -1 se elementoBaseQN non viene trovato in list, secondo un'agualianza a 'vista'
	// altrimenti restituisce l'idice in cui elementoBaseQN è presente in list
	public static int getViewIndex(List<Destinazione> list, 
			ElementoBaseQN elementoBaseQN)
		{
		// modificare tale metodo, indicando nell'uguaglianza anche l'indice
		// del DataBuffer o DataProcessoServizioSorgenti nel caso in cui
		// l'elemento base sia un buffer o un processo di servizio.
		String string = elementoBaseQN.getNome();
		for (int i = 0; i < list.size(); i++)
			{
			Destinazione destinazione = list.get(i);
			Integer integer = destinazione.getOrdineClasse();
			ElementoBaseQN elementoBaseQN2 = destinazione.getElementoBaseQN();
			// elementoBaseQN2 può essere null
			if (elementoBaseQN2 != null)
				{
				String string2 = elementoBaseQN2.getNome();
				if (elementoBaseQN instanceof Buffer<?>)
					{
					Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
					if (string.equals(string2))
						{
						// verifichiamo l'indice dell'unico DataBuffer contenuto in elementoBaseQN
						AggregatoBuffer<?> aggregatoBuffer = buffer.getAggregatoBuffer();
						Object object = aggregatoBuffer.get(0);
						// per precondizione object deve essere un DataBuffer
						DataBuffer dataBuffer = (DataBuffer)object;
						int j = dataBuffer.getOrdineClasse();
						if (j == integer)
							return i;
						}
					}
				else if (elementoBaseQN instanceof ProcessoServizio)
					{
					ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
					if (string.equals(string2))
						{
						// verifichiamo l'indice dell'unico DataProcessoServizioSorgente
						AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
							processoServizio.getAggregatoProcessoServizioSorgenti();
						DataProcessoServizioSorgente dataProcessoServizioSorgente =
							aggregatoProcessoServizioSorgenti.get(0);
						int j = dataProcessoServizioSorgente.getOrdineClasse();
						if (j == integer)
							return i;
						}
					}
				else
					{
					if (string.equals(string2))
						return i;
					}
				}
			}
		return -1;
		}
	}
