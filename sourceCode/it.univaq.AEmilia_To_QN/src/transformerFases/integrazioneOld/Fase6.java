package transformerFases.integrazioneOld;

import javax.swing.JTextArea;

import transformerFases.RisultatoFase;


import mappingElementiBaseIntoPMIF.ISalvaElementiBase;
import mappingElementiBaseIntoPMIF.MappingElementiBaseException;

public class Fase6 {

	public RisultatoFase trasforma(JTextArea textArea, RisultatoFase risultatoFase)
		{
		// risultatoFase2 contiene il risultato di
		// questa fase
		RisultatoFase risultatoFase2 =
			new RisultatoFase();
		if (risultatoFase.isEsito())
			{
			// Si mostra a schermo la chiamata
			// della trasformazione degli elementi base
			// in PMIF
			textArea.append(" Basic elements translating into PMIF ... ");
			textArea.setVisible(true);
			// si preleva l'oggetto
			// generato nella fase precedente
			// se è un'oggetto MappingElementiBaseException
			// questa fase dee dare risultato negativo
			// se è un'oggetto SalvaElementiBase
			// si visualizza che la trasformazione
			// è riuscita
			if (risultatoFase.getRisultato() instanceof MappingElementiBaseException)
				{
				MappingElementiBaseException exception =
					(MappingElementiBaseException)risultatoFase.getRisultato();
				risultatoFase2.setEsito(false);
				risultatoFase2.setRisultato(null);
				textArea.append(" Failed translation \n"+
						exception.getMessage());
				textArea.setVisible(true);
				}
			else
				{
				ISalvaElementiBase salvaElementiBase =
					(ISalvaElementiBase)risultatoFase.getRisultato();
				risultatoFase2.setEsito(true);
				risultatoFase2.setRisultato(salvaElementiBase);
				textArea.append(" OK\n\n");
				textArea.setVisible(true);
				textArea.append(" SUCCESSFUL TRANSFORMATION");
				textArea.setVisible(true);
				}
			}
		return risultatoFase2;
		}
}