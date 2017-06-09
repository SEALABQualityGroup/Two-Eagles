package mappingAEIintoElementiBase.gauss;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;

public class OutGaussJordan 
	{
	/*
	 * Il file da scandire deve essere composto dalle
	 * matrici che risultano dall'esecuzione di un passo
	 * dell'eliminazione di Gauss, separate da due righe vuote.
	 * Nell'ultima riga abbiamo la scritta rank = 3, che
	 * indica la terminazione della scansione e il rango ottenuto
	 * 
	 */
	private String nomeFile;
	private List<Double[][]> list = new ArrayList<Double[][]>();
	private int rango;

	public OutGaussJordan(String nomeFile) 
		{
		super();
		this.nomeFile = nomeFile;
		}

	public void readResult()
		throws AEIintoElementiBaseException
		{
		FileReader fileReader;
		try {
			fileReader = new FileReader(this.nomeFile);
			} 
		catch (FileNotFoundException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String string;
		try {
			string = bufferedReader.readLine();
			} 
		catch (IOException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		String[] strings = string.split("\\s+");
		while (!"rank".equals(strings[0]))
			{
			// leggiamo un passo dell'eliminazione
			readStep(strings,bufferedReader);
			try {
				string = bufferedReader.readLine();
				} 
			catch (IOException e) 
				{
				throw new AEIintoElementiBaseException(e);
				}
			strings = string.split("\\s+");
			}
		// leggiamo il rango della matrice risultato
		readRank(strings[2]);
		}

	private void readRank(String string) 
		{
		this.rango = Integer.parseInt(string);
		}

	private void readStep(String[] strings, BufferedReader bufferedReader) 
		throws AEIintoElementiBaseException 
		{
		Double[][] doubles = null;
		List<List<Double>> listR = new ArrayList<List<Double>>();
		// se string è una linea vuota strings è un array contenente una stringa vuota
		while (!"".equals(strings[0]))
			{
			List<Double> list = new ArrayList<Double>();
			for (String string2 : strings)
				{
				Double double1 = Double.parseDouble(string2);
				list.add(double1);
				}
			listR.add(list);
			String string;
			try {
				string = bufferedReader.readLine();
				} 
			catch (IOException e) 
				{
				throw new AEIintoElementiBaseException(e);
				}
			strings = string.split("\\s+");
			}
		// aggiorniamo list
		doubles = new Double[listR.size()][listR.get(0).size()];
		for (int i = 0; i < listR.size(); i++)
			{
			List<Double> list = listR.get(i);
			for (int j = 0; j < list.size(); j++)
				{
				Double double1 = list.get(j);
				doubles[i][j] = double1;
				}
			}
		this.list.add(doubles);
		// leggo la seconda linea vuota
		try {
			bufferedReader.readLine();
			} 
		catch (IOException e) 
			{
			throw new AEIintoElementiBaseException(e);
			}
		}

	public List<Double[][]> getList() 
		{
		return list;
		}

	public int getRango() 
		{
		return rango;
		}
	}
