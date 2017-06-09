package pack.model.structure;

public enum SchedulingModel 
	{
	FCFS,
	IS,
	PS;
	
	public static String stampa()
		{
		String string = new String();
		for (SchedulingModel schedulingModel : SchedulingModel.values())
			{
			string = string.concat(schedulingModel.toString()+", ");
			}
		String string2 = string.substring(0, string.length() - 2);
		string2.concat(".");
		return string2;
		}
	}
