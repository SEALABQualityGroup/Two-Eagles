package mappingAEIintoElementiBase.gauss;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;

public class InGaussJordan 
	{
	/*
	 * La prima riga del file generato deve essere composta dal numero di righe e colonne
	 * della matrice che è indicata nelle righe successive.
	 */
	private String nomeFile;
	
	public InGaussJordan(String nomeFile) 
		{
		super();
		this.nomeFile = nomeFile;
		}
		
	public void writeChannel(List<List<Double>> list)
		throws AEIintoElementiBaseException
		{
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(this.nomeFile);
			} 
		catch (FileNotFoundException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		// scrivo la dimensione della matrice
		writeDimension(printWriter, list);
		// scrivo la matrice
		writeMatrix(printWriter, list);
		printWriter.close();
		}

	private void writeMatrix(PrintWriter printWriter, List<List<Double>> list) 
		{
		for (List<Double> list2 : list)
			{
			for (Double double1 : list2)
				{
				printWriter.print(double1 + "  ");
				}
			printWriter.println();
			}
		}

	private void writeDimension(PrintWriter printWriter, List<List<Double>> list) 
		{
		int m = list.size();
		int n = list.get(0).size();
		printWriter.print(m + "  ");
		printWriter.print(n + "  ");
		printWriter.println();
		}
	}
