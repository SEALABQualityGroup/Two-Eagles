package parserReply;

import java.util.List;

public class SolveM extends Solve
	{
	// un oggetto SolveM � formato da 0 o pi�
	// oggetti Method
	private List<Method> list;

	public SolveM(List<Method> list2) 
		{
		list = list2;
		}

	public List<Method> getList() 
		{
		return list;
		}

	public void setList(List<Method> list) 
		{
		this.list = list;
		}
	}
