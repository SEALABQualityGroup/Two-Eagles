package transformerFases.integrazioneNew;

import java.util.List;

import javax.swing.JTextArea;

import config.Configurer;

import restrizioniSpecifiche.ISpecificheRules;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import specificheAEmilia.ArchiType;
import transformerFases.RisultatoFase;

public class Fase3
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
			// della verifica delle restrizioni sintattiche specifiche
			textArea.append(" Specific syntax restrictions checking ... ");
			textArea.setVisible(true);
			// si controlla se le regole di restrizione
			// specifiche siano soddisfatte
			try {
				// si genera un'istanza di SpecificheRules1
				Object object = risultatoFase.getRisultato();
				// per precondizione object deve essere un ArchiType
				ArchiType archiType = (ArchiType)object;
				ISpecificheRules specificheRules =
					Configurer.specificheRulesFactory.createSpecificheRules(archiType);
				boolean b = specificheRules.isCompliantSpecificRules();
				// se b == true le regole sono verificate
				if (b)
					{
					List<ISpecifiche> list2 = specificheRules.getListaSpecifiche();
					risultatoFase2.setEsito(true);
					risultatoFase2.setRisultato(list2);
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
			catch (RestrizioniSpecException e)
				{
				// caso in cui si è verificata un'eccezione
				// prevista
				risultatoFase2.setEsito(false);
				risultatoFase2.setRisultato(null);
				textArea.append(" Occurred specific restrictions exception \n"+
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
