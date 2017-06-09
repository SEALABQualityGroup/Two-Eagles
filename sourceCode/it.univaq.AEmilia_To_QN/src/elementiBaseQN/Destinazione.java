package elementiBaseQN;

public class Destinazione 
	{
	
	private ElementoBaseQN elementoBaseQN;
	private Integer ordineClasse;
	
	public Destinazione(ElementoBaseQN elementoBaseQN, Integer ordineClasse) 
		{
		super();
		this.elementoBaseQN = elementoBaseQN;
		this.ordineClasse = ordineClasse;
		}

	public Destinazione(ElementoBaseQN elementoBaseQN) 
		{
		super();
		this.elementoBaseQN = elementoBaseQN;
		this.ordineClasse = -1;
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof Destinazione))
			return false;
		Destinazione destinazione = (Destinazione)arg0;
		if (!(this.ordineClasse.equals(destinazione.ordineClasse)))
			return false;
		if (!this.elementoBaseQN.isContainedName(destinazione.elementoBaseQN))
			return false;
		return true;
		}

	public ElementoBaseQN getElementoBaseQN() 
		{
		return elementoBaseQN;
		}

	public Integer getOrdineClasse()
		{
		return ordineClasse;
		}
	}
