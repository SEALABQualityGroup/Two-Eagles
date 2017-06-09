package parserReply;

import java.util.List;

public class SolveP extends Solve
	{
	
	// un oggetto SolveP è formato da 0 o più
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
