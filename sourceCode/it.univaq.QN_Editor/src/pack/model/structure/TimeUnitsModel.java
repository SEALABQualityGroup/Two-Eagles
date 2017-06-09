package pack.model.structure;

import pack.errorManagement.Finestra;

public enum TimeUnitsModel 
	{
	day, 
	Day, 
	hr, 
	Hr, 
	min, 
	Min, 
	sec, 
	Sec, 
	ms, 
	Ms, 
	ns, 
	Ns;
	
	public static String stampa()
		{
		String string = new String();
		for (TimeUnitsModel timeUnitsModel : TimeUnitsModel.values())
			{
			string = string.concat(timeUnitsModel.toString()+", ");
			}
		String string2 = string.substring(0, string.length() - 2);
		string2.concat(".");
		return string2;
		}
	
	public static TimeUnitsModel getEnumerato(String string)
		{
		if (string.equals(day.toString()))
			return day;
		else if (string.equals(Day.toString()))
			return Day;
		else if (string.equals(hr.toString()))
			return hr;
		else if (string.equals(Hr.toString()))
			return Hr;
		else if (string.equals(min.toString()))
			return min;
		else if (string.equals(Min.toString()))
			return Min;
		else if (string.equals(sec.toString()))
			return sec;
		else if (string.equals(Sec.toString()))
			return Sec;
		else if (string.equals(ms.toString()))
			return ms;
		else if (string.equals(Ms.toString()))
			return Ms;
		else if (string.equals(ns.toString()))
			return ns;
		else if (string.equals(Ns.toString()))
			return Ns;
		else
			{
			try {
				Finestra.mostraIE("Time units is not: "+
						TimeUnitsModel.stampa());
				} 
			catch (Exception e) 
				{}
			return null;
			}
		}
	}
