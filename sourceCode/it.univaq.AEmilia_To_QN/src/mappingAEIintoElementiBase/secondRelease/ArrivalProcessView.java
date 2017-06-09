package mappingAEIintoElementiBase.secondRelease;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import elementiBaseQN.Buffer;
import elementiBaseQN.ElementoBaseQN;
import elementiBaseQN.ProcessoArrivi;
import elementiBaseQN.ProcessoFork;
import elementiBaseQN.ProcessoJoin;
import elementiBaseQN.ProcessoRouting;
import elementiBaseQN.ProcessoServizio;
import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataBuffer;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;


public class ArrivalProcessView 
	{
	private boolean result;
	private ProcessoArrivi arrivalProcess;
	private String logResult;
	// l'ultimo elemento di view corrisponde al processo di arrivi 
	// che caratterizza la vista
	private List<ElementoBaseQN> view;
	private List<Double> numberOfVisits;
	private Map<String, Double> map = new Hashtable<String, Double>();
	
	
	public void fillView()
		{
		for (int i = 0; i < view.size() - 1; i++)
			{
			ElementoBaseQN elementoBaseQN = view.get(i);
			if (elementoBaseQN instanceof ProcessoJoin)
				{
				ProcessoJoin processoJoin = (ProcessoJoin)elementoBaseQN;
				fill(processoJoin,numberOfVisits.get(i));
				}
			if (elementoBaseQN instanceof ProcessoFork)
				{
				ProcessoFork processoFork = (ProcessoFork)elementoBaseQN;
				fill(processoFork,numberOfVisits.get(i));
				}
			if (elementoBaseQN instanceof ProcessoRouting)
				{
				ProcessoRouting processoRouting = (ProcessoRouting)elementoBaseQN;
				fill(processoRouting,numberOfVisits.get(i));
				}
			if (elementoBaseQN instanceof ProcessoServizio)
				{
				ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
				fill(processoServizio,numberOfVisits.get(i));
				}
			if (elementoBaseQN instanceof Buffer<?>)
				{
				Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
				fill(buffer,numberOfVisits.get(i));
				}
			String string = elementoBaseQN.getNome();
			this.map.put(string, numberOfVisits.get(i));
			}
		}

	private void fill(Buffer<?> buffer, Double double1) 
		{
		AggregatoBuffer<?> aggregatoBuffer = buffer.getAggregatoBuffer();
		// per precondizione aggregatoBuffer ha un solo elemento
		DataBuffer dataBuffer = aggregatoBuffer.get(0);
		dataBuffer.setNumberOfVisits(double1);
		}

	private void fill(ProcessoServizio processoServizio, Double double1) 
		{
		AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
			processoServizio.getAggregatoProcessoServizioSorgenti();
		// per precondizione aggregatoProcessoServizioSorgenti ha un solo
		// elemento DataProcessoServizioSorgente
		DataProcessoServizioSorgente dataProcessoServizioSorgente = 
			aggregatoProcessoServizioSorgenti.get(0);
		dataProcessoServizioSorgente.setNumberOfVisits(double1);
		}

	private void fill(ProcessoRouting processoRouting, Double double1) 
		{
		processoRouting.setNumberOfVisits(double1);
		}

	private void fill(ProcessoFork processoFork, Double double1) 
		{
		processoFork.setNumberOfVisits(double1);
		}

	private void fill(ProcessoJoin processoJoin, Double double1) 
		{
		processoJoin.setNumberOfVisits(double1);
		}

	public boolean isResult() 
		{
		return result;
		}

	public void setResult(boolean result) 
		{
		this.result = result;
		}

	public ProcessoArrivi getArrivalProcess() 
		{
		return arrivalProcess;
		}

	public void setArrivalProcess(ProcessoArrivi arrivalProcess) 
		{
		this.arrivalProcess = arrivalProcess;
		}

	public String getLogResult() 
		{
		return logResult;
		}

	public void setLogResult(String logResult) 
		{
		this.logResult = logResult;
		}

	public List<ElementoBaseQN> getView() 
		{
		return view;
		}

	public void setView(List<ElementoBaseQN> view) 
		{
		this.view = view;
		}

	public List<Double> getNumberOfVisits() 
		{
		return numberOfVisits;
		}
	
	public void setNumberOfVisits(List<Double> numberOfVisits) 
		{
		this.numberOfVisits = numberOfVisits;
		}
	
	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof ArrivalProcessView))
			return false;
		ArrivalProcessView arrivalProcessView = (ArrivalProcessView)arg0;
		String string = this.arrivalProcess.getNome();
		String string2 = arrivalProcessView.getArrivalProcess().getNome();
		if (!string.equals(string2))
			return false;
		Set<Entry<String, Double>> set = this.map.entrySet();
		Set<Entry<String, Double>> set2 = arrivalProcessView.getMap().entrySet();
		return set.equals(set2);
		}

	@Override
	public String toString() 
		{
		Set<Entry<String, Double>> set = this.map.entrySet();
		String string = new String();
		string = string + "(arrivalProcess:"+this.arrivalProcess.getNome()+",";
		for (Entry<String, Double> entry :set)
			{
			String string2 = entry.getKey();
			Double double1 = entry.getValue();
			string += "("+string2+","+double1.toString()+")";
			}
		string = string + ")";
		return string;
		}

	public Map<String, Double> getMap()
		{
		return this.map;
		}

	public void setMap(Map<String, Double> map) 
		{
		this.map = map;
		}
	}
