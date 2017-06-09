package transformerFases.integrazioneOld;

import javax.swing.JTextArea;


import scanSpecAEmilia.ScanArchiType;
import scanSpecAEmilia.ScanException;
import specificheAEmilia.ArchiType;
import transformerFases.RisultatoFase;

/*
 * Classe utilizzata per la scansione di una specifica aem, ottenendo una rappresentazione
 * ArchiType.
 * 
 */
public class Fase1
	{
	public RisultatoFase trasforma(JTextArea textArea, RisultatoFase risultatoFase)
		{
		// Si mostra a schermo la chiamata
		// della scannerizzazione
		textArea.append(" Aem scanning ... ");
		textArea.setVisible(true);
		// si scannerizza object che deve essere
		// una stringa
		ArchiType archiType = null;
		// risultato2 contiene il risultato di questa fase
		RisultatoFase risultatoFase2 =
			new RisultatoFase();
		try {
			String string = (String)risultatoFase.getRisultato();
			archiType = ScanArchiType.scanArchiType(string);
			// scansione superata
			risultatoFase2.setEsito(true);
			risultatoFase2.setRisultato(archiType);
			textArea.append(" OK\n");
			textArea.setVisible(true);
			}
		catch (ScanException e)
			{
			// scansione con costrutti non supportati
			risultatoFase2.setEsito(false);
			risultatoFase2.setRisultato(null);
			textArea.append(" Unsupported AEmilia constructs \n" +
					e.getMessage());
			textArea.setVisible(true);
			}
		catch (Exception exception)
			{
			// scansione con eccezione non prevista
			risultatoFase2.setEsito(false);
			risultatoFase2.setRisultato(null);
			textArea.append(" Unexpected exception \n" +
					exception.getMessage());
			textArea.setVisible(true);
			}
		return risultatoFase2;
		}
	}
