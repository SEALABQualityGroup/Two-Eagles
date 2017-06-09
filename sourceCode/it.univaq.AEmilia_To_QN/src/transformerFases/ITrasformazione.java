package transformerFases;

import javax.swing.JTextArea;

public interface ITrasformazione 
	{

	public RisultatoFase trasforma(JTextArea textArea, RisultatoFase risultatoFase, String string);

	}