package restrizioniSpecifiche;

/**
 * Un'oggetto Interaction rappresenta un'interazione, data dal nome dell'istanza di un tipo di elemento 
 * architetturale e dal nome dell'interazione.
 * 
 * @author Mirko
 *
 */
public class Interaction 
	{

	private String Instance;
	private String Action;
	
	public Interaction(String instance, String action) 
		{
		super();
		Instance = instance;
		Action = action;
		}

	public String getInstance() 
		{
		return Instance;
		}
	
	public void setInstance(String instance) 
		{
		Instance = instance;
		}
	
	public String getAction() 
		{
		return Action;
		}
	
	public void setAction(String action) 
		{
		Action = action;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof Interaction)) return false;
		if (obj == null) return false;
		Interaction interaction = (Interaction)obj;
		String string = interaction.getAction();
		String string2 = interaction.getInstance();
		if (!this.getAction().equals(string)) return false;
		if (!this.getInstance().equals(string2)) return false;
		return true;
		}
	}