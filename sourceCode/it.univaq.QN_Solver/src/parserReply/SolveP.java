package parserReply;

import java.util.List;

public class SolveP extends Solve
	{
	
	// un oggetto SolveP � formato da 0 o pi�
	// oggetti Params
	private List<Params> list;

	public SolveP(List<Params> list) 
		{
		super();
		this.list = list;
		}

	public List<Params> getList() 
		{
		return list;
		}

	public void setList(List<Params> list) 
		{
		this.list = list;
		}
	}
