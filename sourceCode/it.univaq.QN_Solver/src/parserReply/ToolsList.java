package parserReply;

import java.util.List;

public class ToolsList 
	{
	// è formato da 0 o più oggetti Tool
	private List<Tool> list;

	public ToolsList(List<Tool> list2) 
		{
		list = list2;
		}

	public List<Tool> getList() 
		{
		return list;
		}

	public void setList(List<Tool> list) 
		{
		this.list = list;
		}

	public Tool getToolFromName(String nome) 
		{
		Tool tool = null;
		for (Tool tool2 : list)
			{
			String string = tool2.getName();
			if (nome.equals(string))
				tool = tool2;
			}
		return tool;
		}
	}
