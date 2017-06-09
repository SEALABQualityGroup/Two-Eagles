package transformerFases.integrazioneOld;

import javax.swing.JTextArea;

import config.Configurer;

import restrizioniGenerali.IGeneraliRules;
import restrizioniGenerali.RestrizioniGenException;
import specificheAEmilia.ArchiType;
import transformerFases.RisultatoFase;

public class Fase2
	{
	public RisultatoFase trasforma(JTextArea textArea, RisultatoFase risultatoFase)
		{
		// risultatoFase2 contiene il risultato di
		// questa fase
		RisultatoFase risultatoFase2 =
			new RisultatoFase();
		if (risultatoFase.isEsito())
			{
			// Si mostra a schermo la chiamata
			// della verifica delle restrizioni sintattiche generali
			textArea.append(" General syntax restrictions checking ... ");
			textArea.setVisible(true);
			// si preleva il tipo architetturale
			Object object = risultatoFase.getRisultato();
			// per precondizione object deve essere un ArchiType
			ArchiType archiType = (ArchiType)object;
			IGeneraliRules generaliRules = Configurer.generaliRulesFactory.createGeneraliRules(archiType);
			// si controlla se le regole di restrizione
			// generali siano soddisfatte
			try {
				boolean b = generaliRules.isCompliantGeneralRules();
				// se b == true le regole sono verificate
				if (b)
					{
					risultatoFase2.setEsito(true);
					risultatoFase2.setRisultato(archiType);
					textArea.append(" OK\n");
					textArea.setVisible(true);
					}
				else
					{
					// se le regole non sono state
					// verificate si visualizza a schermo
					risultatoFase2.setEsito(false);
					risultatoFase2.setRisultato(null);
					textArea.append(" Rules don't met \n");
					textArea.setVisible(true);
					}
				}
			catch (RestrizioniGenException e)
				{
				// caso in cui si è verificata un'eccezione
				// prevista
				risultatoFase2.setEsito(false);
				risultatoFase2.setRisultato(null);
				textArea.append(" Occurred general restrictions exception \n"+
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
