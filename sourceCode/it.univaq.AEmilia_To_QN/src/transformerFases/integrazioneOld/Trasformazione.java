package transformerFases.integrazioneOld;

import javax.swing.JTextArea;

import transformerFases.ITrasformazione;
import transformerFases.RisultatoFase;


public class Trasformazione implements ITrasformazione {

	public RisultatoFase trasforma(JTextArea textArea, RisultatoFase risultatoFase, String string)
		{
		// si chiama Fase1
		Fase1 fase1 = new Fase1();
		RisultatoFase risultatoFase2 =
			fase1.trasforma(textArea, risultatoFase);
		// si chiama Fase2
		Fase2 fase2 = new Fase2();
		RisultatoFase risultatoFase5 =
			fase2.trasforma(textArea, risultatoFase2);
		// si chiama Fase3
		Fase3 fase3 = new Fase3();
		RisultatoFase risultatoFase6 =
			fase3.trasforma(textArea, risultatoFase5);
		// si chiama Fase4
		Fase4 fase4 = new Fase4();
		RisultatoFase risultatoFase7 =
			fase4.trasforma(textArea, risultatoFase6);
		// si chiama Fase5
		Fase5 fase5 = new Fase5();
		RisultatoFase risultatoFase8 =
			fase5.trasforma(textArea, risultatoFase7);
		// si chiama Fase6
		Fase6 fase6 = new Fase6();
		RisultatoFase risultatoFase9 =
			fase6.trasforma(textArea, risultatoFase8);
		return risultatoFase9;
		}	
}
