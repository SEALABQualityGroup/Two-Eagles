package elementiBaseQN;

import java.util.HashMap;

import elementiBaseQN.Strutture.ListaStrutturaInputFork;
import elementiBaseQN.Strutture.ListaStrutturaOutputFork;

public class ProcessoForkSenzaBuffer extends ProcessoFork {
	
	private static final long serialVersionUID = 1L;

	public ProcessoForkSenzaBuffer()
		{
		super();
		}

	public ProcessoForkSenzaBuffer(String nome, HashMap<String,ElementoBaseQN> sorgenti,
		HashMap<String,Destinazione> destinazioni, String canale)
		throws ElementoBaseException 
		{
		super(nome, sorgenti, destinazioni, canale);
		}

	public ProcessoForkSenzaBuffer(String string) 
		{
		super(string);
		}

	public ProcessoForkSenzaBuffer(String nome,
			ListaStrutturaInputFork sorgenti,
			ListaStrutturaOutputFork destinazioni, String canale) 
		{
		super(nome, sorgenti, destinazioni, canale);
		}	
}
