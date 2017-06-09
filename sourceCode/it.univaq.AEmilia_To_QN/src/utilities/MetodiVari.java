package utilities;

import java.io.File;

public class MetodiVari 
	{
	
	static public String namespaceCompute(File file)
		{
		File file2 = file.getAbsoluteFile();
		String string = file2.getName();
		String string2 = file2.getParent();
		String string3 = 
			string2+File.separatorChar+"GaussJordan"+File.separatorChar+string;
		// si crea la directory relativa a
		// string2+File.separatorChar+"GaussJordan"
		File file3 = new File(string2+File.separatorChar+"GaussJordan");
		file3.mkdir();
		return string3;
		}
	}
