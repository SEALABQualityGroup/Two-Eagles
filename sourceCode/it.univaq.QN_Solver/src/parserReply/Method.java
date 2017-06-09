package parserReply;

import java.util.List;

public class Method 
	{
	// un oggetto Method è formato da un nome
	private String name;
	// un'eventuale descrizione
	private String description;
	// zero o più elementi params
	private List<Params> paramsList;
	
	public String getName() 
		{
		return name;
		}
	
	public void setName(String name) 
		{
		this.name = name;
		}
	
	public String getDescription() 
		{
		return description;
		}
	
	public void setDescription(String description) 
		{
		this.description = description;
		}

	public List<Params> getParamsList() 
		{
		return paramsList;
		}

	public void setParamsList(List<Params> list) 
		{
		this.paramsList = list;
		}

	// considero due metodi uguali se hanno lo stesso nome
	@Override
	public boolean equals(Object arg0) 
		{
		if (arg0 instanceof Method)
			{
			Method method = (Method) arg0;
			String string = method.getName();
			String string2 = this.getName();
			return string.equals(string2);
			}
		else
			return false;
		}
	}
