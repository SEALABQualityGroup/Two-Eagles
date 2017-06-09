package mappingElementiBaseIntoPMIF;


public interface ISalvaElementiBase 
	{
	
	public void salvaInXML(String nomeFile)
		throws MappingElementiBaseException;
	
	public void salvaInXML(String nomeFile, String nomeSchema)
		throws MappingElementiBaseException;

	/**
	 * Restituisce true se e solo se radice è una rete di code multiclasse.
	 * La verifica viene fatta dopo la trasformazione.
	 * Una condizione sufficiente affinchè la rete di code sia multiclasse è
	 * che ci sono due richieste di servizio con server e workload uguali.
	 * 
	 */
	public boolean isMulticlasse()
		throws MappingElementiBaseException;
	
	/*
	 * Restituisce true se e solo se la specifica è trasformabile in una rete PMIF.
	 */
	public boolean isTransformable()
		throws MappingElementiBaseException;
	
	/*
	 * Trasforma la specifica in una rete PMIF.
	 */
	public void mapping()
		throws MappingElementiBaseException;

	/**
	 * Restituisce true se e solo se la rete di code è multiclasse.
	 * La verifica viene effettuata prima della trasformazione.
	 * 
	 * @return
	 */
	public boolean isMulticlasseEB();

	}