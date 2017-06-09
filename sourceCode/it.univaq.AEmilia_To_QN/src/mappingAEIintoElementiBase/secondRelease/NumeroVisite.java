package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.gauss.GaussJordan;
import mappingAEIintoElementiBase.gauss.InGaussJordan;
import mappingAEIintoElementiBase.gauss.OutGaussJordan;
import mappingAEIintoElementiBase.struttura.ListaElementiBase;
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

/*
 * Supponiamo che non ci siano classi con nome uguale alla stringa vuota.
 * Altrimenti, bisognerebbe calcolarsi il numero di visite per frammenti di reti di code.
 * Dunque, supponiamo la presenza di almeno un processo di arrivi. 
 */
public class NumeroVisite 
	{
	
	private ListaElementiBase elementiBase;
	
	private List<ArrivalProcessView> arrivalProcessViews = new ArrayList<ArrivalProcessView>();
	
	// il seguente campo serve esclusivamente per il testing
	private List<List<List<Double>>> inputLists = new ArrayList<List<List<Double>>>();
	
	// rappresenta il prefisso per l'identificazione dei file di input ed output utilizzati 
	// dall'eliminazione di GaussJordan
	private String namespace;

	public NumeroVisite(ListaElementiBase elementiBase, String namespace) 
		{
		super();
		this.elementiBase = elementiBase;
		this.namespace = namespace;
		}
	
	/*
	 * Per ogni processo di arrivi:
	 * 1) si genera la vista relativa al nome del canale
	 * di clienti rappresentato dal processo di arrivi;
	 * 2) si memorizza su file la matrice di conservazione 
	 * del flusso per la vista generata;
	 * 3) si risolve il sistema utilizzando l'eliminazione
	 * di GaussJordan;
	 * 4) si preleva da file il risultato dell'eliminazione
	 * di GaussJordan;
	 * 5) si aggiornano il numero di visite di ogni
	 * elemento base presente nella vista.
	 */
	public void computeNumberOfVisits() 
		throws AEIintoElementiBaseException
		{
		// preleviamo i processi di arrivi
		List<ProcessoArrivi> list = this.elementiBase.getArrivalProcesses();
		for (ProcessoArrivi processoArrivi : list)
			{
			computeNumberOfVisitsStep(processoArrivi);
			}
		}
	
	private List<Double> makeNumberOfVisits(Double[][] doubles) 
		{
		List<Double> list = new ArrayList<Double>(doubles.length);
		// riempio inputLists con elementi uguali a zero
		for (int i = 0; i < doubles.length; i++)
			{
			list.add(0d);
			}
		for (int i = 0; i < doubles.length; i++)
			{
			Double[] doubles2 = doubles[i];
			for (int j = 0; j < doubles2.length - 1; j++)
				{
				if (doubles2[j] == 1)
					list.set(j, doubles2[doubles2.length - 1]);
				}
			}
		return list;
		}

	private boolean unfeasibleSystem(Double[][] doubles) 
		{
		for (Double[] doubles2 : doubles)
			{
			// 1 verifico se i primi doubles2.leght - 1 elementi sono uguali a zero
			if (primiMenoUnoZero(doubles2))
				// 1.1 verifico se l'ultimo elemento di doubles2 è diverso da zero. 
				// In questo caso restituisco true
				if (doubles2[doubles2.length - 1] != 0)
					return true;
			}
		return false;
		}

	/*
	 * Restituisce true se e solo i primi doubles2.leght - 1 elementi sono zero.
	 */
	private boolean primiMenoUnoZero(Double[] doubles2) 
		{
		for (int i = 0; i < doubles2.length - 1; i++)
			{
			Double double1 = doubles2[i];
			if (double1 != 0)
				return false;
			}
		return true;
		}

	private boolean infititeSolutions(Double[][] doubles) 
		{
		for (Double[] doubles2 : doubles)
			{
			// 1 verifico se i primi doubles2.leght - 1 elementi sono uguali a zero
			if (primiMenoUnoZero(doubles2))
				// 1.1 verifico se l'ultimo elementi di doubles2 è uguale a zero.
				// In questo caso restituisco true
				if (doubles2[doubles2.length - 1] == 0)
					return true;
			}
		return false;
		}

	public boolean isFeasible()
		{
		// affinchè sia possibile calcolare il numero di visite c'è bisogno che in ogni elemento
		// non ci siano classi corrispondenti alla stringa vuota.
		for (ElementoBaseQN elementoBaseQN : this.elementiBase)
			{
			if (elementoBaseQN instanceof ProcessoServizio)
				{
				ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
				if (!isFeasible(processoServizio))
					return false;
				}
			if (elementoBaseQN instanceof ProcessoRouting)
				{
				ProcessoRouting processoRouting = (ProcessoRouting)elementoBaseQN;
				if (!isFeasible(processoRouting))
					return false;
				}
			if (elementoBaseQN instanceof ProcessoFork)
				{
				ProcessoFork processoFork = (ProcessoFork)elementoBaseQN;
				if (!isFeasible(processoFork))
					return false;
				}
			if (elementoBaseQN instanceof ProcessoJoin)
				{
				ProcessoJoin processoJoin = (ProcessoJoin)elementoBaseQN;
				if (!isFeasible(processoJoin))
					return false;
				}
			if (elementoBaseQN instanceof Buffer<?>)
				{
				Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
				if (!isFeasible(buffer))
					return false;
				}
			}
		return true;
		}

	private boolean isFeasible(Buffer<?> buffer) 
		{
		AggregatoBuffer<?> aggregatoBuffer = buffer.getAggregatoBuffer();
		for (Object object : aggregatoBuffer)
			{
			// object deve essere un DataBuffer
			DataBuffer dataBuffer = (DataBuffer)object;
			String string = dataBuffer.getClasse();
			if ("".equals(string))
				return false;
			}
		return true;
		}

	private boolean isFeasible(ProcessoJoin processoJoin) 
		{
		String string = processoJoin.getCanale();
		if ("".equals(string))
			return false;
		return true;
		}

	private boolean isFeasible(ProcessoFork processoFork) 
		{
		String string = processoFork.getCanale();
		if ("".equals(string))
			return false;
		return true;
		}

	private boolean isFeasible(ProcessoRouting processoRouting) 
		{
		String string = processoRouting.getCanale();
		if ("".equals(string))
			return false;
		return true;
		}

	private boolean isFeasible(ProcessoServizio processoServizio) 
		{
		AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti =
			processoServizio.getAggregatoProcessoServizioSorgenti();
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : aggregatoProcessoServizioSorgenti)
			{
			String string = dataProcessoServizioSorgente.getClasse();
			if ("".equals(string))
				return false;
			}
		return true;
		}

	public List<ArrivalProcessView> getArrivalProcessViews() 
		{
		return arrivalProcessViews;
		}
	
	private void computeNumberOfVisitsStep(ProcessoArrivi processoArrivi) 
		throws AEIintoElementiBaseException
		{
		// creiamo un oggetto ArrivalProcessView
		ArrivalProcessView arrivalProcessView = new ArrivalProcessView();
		arrivalProcessView.setArrivalProcess(processoArrivi);
		String string = processoArrivi.getNome();
		ViewGeneration viewGeneration = new ViewGeneration(string);
		List<ElementoBaseQN> list2 = viewGeneration.getView(this.elementiBase);
		PRMatrix matrix = new PRMatrix(list2);
		// prelevo l'array corrispondente alla vista, in cui
		// l'ultimo elemento è il processo di arrivi
		List<ElementoBaseQN> list = matrix.getList();
		arrivalProcessView.setView(list);
		List<List<Double>> list3 = matrix.getMatrixFromEbView();
		this.inputLists.add(list3);
		InGaussJordan inGaussJordan = new InGaussJordan(
				this.namespace+"."+string+".in");
		inGaussJordan.writeChannel(list3);
		GaussJordan gaussJordan = new GaussJordan();
		gaussJordan.gaussJordan(
				this.namespace+"."+string+".in", 
				this.namespace+"."+string+".out");
		OutGaussJordan outGaussJordan = 
			new OutGaussJordan(this.namespace+"."+string+".out");
		outGaussJordan.readResult();
		List<Double[][]> list4 = outGaussJordan.getList();
		int i = outGaussJordan.getRango();
		Double[][] doubles = list4.get(list4.size() - 1);
		int j = doubles.length;
		if (i > j)
			{
			arrivalProcessView.setResult(false);
			// si controlla se ci sono righe con elementi tutti uguali a zero tranne
			// l'ultimo elemento. In questo caso il sistema è inammissibile.
			if (unfeasibleSystem(doubles))
				arrivalProcessView.setLogResult("unfeasible system");
			// si controlla se ci sono righe con elementi tutti uguali a zero.
			// In questo caso il sistema ammette infinite soluzioni.
			else if (infititeSolutions(doubles))
				arrivalProcessView.setLogResult("infinite solutions system");
			}
		else
			{
			// costruisco l'array per il numero di visite
			List<Double> list5 = makeNumberOfVisits(doubles);
			arrivalProcessView.setNumberOfVisits(list5);
			// aggiorno la vista di arrivalProcessView con il numero di visite
			arrivalProcessView.fillView();
			// imposto il risultato a true
			arrivalProcessView.setResult(true);
			}
		// aggiungo arrivalProcessView
		this.arrivalProcessViews.add(arrivalProcessView);
		}

	public List<List<List<Double>>> getInputLists() 
		{
		return inputLists;
		}
	}
