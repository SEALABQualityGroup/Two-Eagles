package transformerFases.integrazioneNew;

import javax.swing.JTextArea;

import transformerFases.RisultatoFase;

import config.Configurer;

import mappingAEIintoElementiBase.struttura.ListaElementiBase;
import mappingElementiBaseIntoPMIF.ISalvaElementiBase;
import mappingElementiBaseIntoPMIF.MappingElementiBaseException;

public class Fase5
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
			// del filtraggio degli elementi base
			textArea.append(" Filtering into simple basic elements ... ");
			textArea.setVisible(true);
			// si preleva la lista di oggetti ElementoBaseQN
			// generati nella fase precedente
			ListaElementiBase list =
				(ListaElementiBase)risultatoFase.getRisultato();
			// si filtrano gli elementi base, utilizzando
			// la classe SalvaElementiBase
			try {
				ISalvaElementiBase salvaElementiBase =
					Configurer.salvaElementiBaseFactory.createSalvaElementiBase(list);
				if (!salvaElementiBase.isTransformable())
					{
					// caso in cui ci sono stati problemi di filtering
					risultatoFase2.setEsito(false);
					risultatoFase2.setRisultato(null);
					textArea.append("Impossible PMIF transformation \n");
					textArea.setVisible(true);
					}
				else if (salvaElementiBase.isMulticlasseEB())
					{
					// caso in cui ci sono stati problemi di filtering
					risultatoFase2.setEsito(false);
					risultatoFase2.setRisultato(null);
					textArea.append("Multiclass QN \n");
					textArea.setVisible(true);
					}
				else
					{
					salvaElementiBase.mapping();
					// se non sono state generate eccezioni
					// la fase è andata a buon fine
					risultatoFase2.setEsito(true);
					risultatoFase2.setRisultato(salvaElementiBase);
					textArea.append(" OK\n");
					textArea.setVisible(true);
					}
				}
			catch (MappingElementiBaseException exception)
				{
				// caso in cui ci sono stati problemi di mapping
				risultatoFase2.setEsito(true);
				risultatoFase2.setRisultato(exception);
				textArea.append(" OK \n");
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