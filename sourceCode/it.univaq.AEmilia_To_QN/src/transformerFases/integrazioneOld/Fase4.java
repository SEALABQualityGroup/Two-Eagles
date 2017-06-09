package transformerFases.integrazioneOld;

import java.util.List;

import javax.swing.JTextArea;

import config.Configurer;


import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.IFromAEIintoElementiBase;
import mappingAEIintoElementiBase.struttura.ListaElementiBase;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import transformerFases.RisultatoFase;

public class Fase4 {

	@SuppressWarnings("unchecked")
	public RisultatoFase trasforma(JTextArea textArea, RisultatoFase risultatoFase)
		{
		// risultatoFase2 contiene il risultato di
		// questa fase
		RisultatoFase risultatoFase2 =
			new RisultatoFase();
		if (risultatoFase.isEsito())
			{
			// Si mostra a schermo la chiamata
			// della verifica delle restrizioni sintattiche specifiche
			textArea.append(" AEI translating ... ");
			textArea.setVisible(true);
			// si preleva la lista di oggetti Specifiche
			// generati nella fase precedente
			List<ISpecifiche> list =
				(List<ISpecifiche>) risultatoFase.getRisultato();
			// si generano gli elementi base.
			try {
				// si utilizza FromAEIintoElementiBase
				IFromAEIintoElementiBase fromAEIintoElementiBase =
					Configurer.fromAEIintoElementiBaseFactory.createFromAEIintoElementiBase(list);
				if (!fromAEIintoElementiBase.assegnaClassi())
					{
					risultatoFase2.setEsito(false);
					risultatoFase2.setRisultato(null);
					textArea.append(" interactions with more than one class of jobs \n");
					textArea.setVisible(true);
					}
				else
					{
					// se non sono state generate eccezioni
					// la fase è andata a buon fine
					fromAEIintoElementiBase.generaElementiBase();
					fromAEIintoElementiBase.connettiElementiBase();
					ListaElementiBase listaElementiBase =
						fromAEIintoElementiBase.getElementiBase();
					// se verificaUPAPJobsExit restituisce false viene segnalato all'utente
					boolean b = fromAEIintoElementiBase.verificaUPAPJobsExit();
					if (!b)
						{
						risultatoFase2.setEsito(false);
						risultatoFase2.setRisultato(null);
						textArea.append(" exit jobs propery is not true\n");
						textArea.setVisible(true);
						}
					else
						{
						risultatoFase2.setEsito(true);
						risultatoFase2.setRisultato(listaElementiBase);
						textArea.append(" OK\n");
						textArea.setVisible(true);
						}
					}
				}
			catch (AEIintoElementiBaseException e)
				{
				// caso in cui si è verificata un'eccezione
				// prevista
				risultatoFase2.setEsito(false);
				risultatoFase2.setRisultato(null);
				textArea.append("Occurred basic elements translation exception \n"+
					e.getMessage());
				textArea.setVisible(true);
				}
			catch (Exception exception)
				{
				// caso in cui è stata generata un'eccezione
				// non prevista
				risultatoFase2.setEsito(false);
				risultatoFase2.setRisultato(null);
				textArea.append(" Unexpected exception\n"+
					exception.getMessage());
				textArea.setVisible(true);
				}
			}
		return risultatoFase2;
		}
}
