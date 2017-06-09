package parserReply;

public class Tool 
	{
	// Un oggetto tool è formato da un nome
	private String name;
	// un'eventuale versione
	private String version;
	// un'eventuale descrizione
	private String description;
	// un'eventuale author
	private String author;
	// un eventuale oggetto SolveM o SolveP.
	// è null se il tool non ha un elemento solve
	private Solve solve;
	
	public String getName() 
		{
		return name;
		}
	
	public void setName(String name) 
		{
		this.name = name;
		}
	
	public String getVersion() 
		{
		return version;
		}
	
	public void setVersion(String version) 
		{
		this.version = version;
		}
	
	public String getDescription() 
		{
		return description;
		}
	
	public void setDescription(String description) 
		{
		this.description = description;
		}
	
	public String getAuthor() 
		{
		return author;
		}
	
	public void setAuthor(String author) 
		{
		this.author = author;
		}
	
	public Solve getSolve() 
		{
		return solve;
		}
	
	public void setSolve(Solve solve) 
		{
		this.solve = solve;
		}

	public int getMethodsNummber() 
		{
		if (solve == null || solve instanceof SolveP)
			return 0;
		else
			{
			SolveM solveM = (SolveM)solve;
			return solveM.getList().size();
			}
		}

	// considero due tool uguali se hanno lo stesso nome
	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof Tool))
			return false;
		else
			{
			Tool tool = (Tool)arg0;
			return this.name.equals(tool.name);
			}
		}
	
	// restituisce true se e solo se il tool ha parametri e non metodi
	public boolean parameters()
		{
		if (solve instanceof SolveP)
			return !(((SolveP)solve).getList().size() == 0);
		else
			return false;
		}
	}
