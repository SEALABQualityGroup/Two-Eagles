package parserReply;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import personalScanner.MyScanner;

// classe utilizzata per ottenere le informazioni da una 
// risposta di weasel

public class ParserWeaselReply 
	{
	
	private String string;
	
	public ParserWeaselReply(String string) 
		{
		super();
		this.string = string;
		}

	public boolean getEsito()
		{
		// si preleva l'indice dell'attributo value
		int i = string.indexOf("value");
		String string2 = string.substring(i);
		String[] strings = string2.split("\"");
		String string3 = strings[1];
		if ("False".equalsIgnoreCase(string3))
			return false;
		else
			return true;
		}
	
	public String getMessage()
		{
		MyScanner myScanner = new MyScanner(string);
		myScanner.useDelimiter("\\s*<\\s*Message\\s*>\\s*");
		// è possibile ottenere come risposta una stringa vuota e quindi si genera una
		// NoSuchElementException. Questo corrisponde al caso in cui si è cercato di 
		// risolvere uno pmif senza impostare il metodo di risoluzione previsto per il
		// tool selezionato.
		String string0 = new String();
		try {
			string0  = myScanner.next();
			String string = myScanner.next();
			myScanner = new MyScanner(string);
			}
		catch (NoSuchElementException e)
			{
			// questo è il caso in cui la risposta di weasel non è nel formato di risposta di weasel,
			// cioè racchiusa tra tag xml
			return string0;
			}
		myScanner.useDelimiter("\\s*<\\s*/\\s*Message\\s*>\\s*");
		String string2 = myScanner.next();
		return string2;
		}
	
	public ToolsList getToolList()
		{
		StringReader reader = new StringReader(string);
		InputSource inputSource = new InputSource(reader);
		DocumentBuilderFactory builderFactory =
			DocumentBuilderFactory.newInstance();
		Document document = null;
		ToolsList toolList = null;
		try {
			DocumentBuilder documentBuilder =
				builderFactory.newDocumentBuilder();
			document = documentBuilder.parse(inputSource);
			toolList = getToolListFromDoc(document);
			} 
		catch (ParserConfigurationException e) 
			{
			e.printStackTrace();
			} 
		catch (SAXException e) 
			{
			e.printStackTrace();
			} 
		catch (IOException e) 
			{
			e.printStackTrace();
			}
		return toolList;
		}

	public static ToolsList getToolListFromDoc(Document document) 
		{
		NodeList nodeList = document.getElementsByTagName("TOOLSLIST");
		// si restituisce il primo e l'unico nodo
		// TOOLSLIST
		Element element = (Element)nodeList.item(0);
		ToolsList toolsList = null;
		NodeList nodeList2 = element.getElementsByTagName("TOOL");
		List<Tool> list = getToolsFromNodeList(nodeList2);
		toolsList = new ToolsList(list);
		return toolsList;
		}

	private static  List<Tool> getToolsFromNodeList(NodeList nodeList2) 
		{
		List<Tool> list = new ArrayList<Tool>();
		for (int i = 0; i < nodeList2.getLength(); i++)
			{
			Element element = (Element)nodeList2.item(i);
			Tool tool = getToolFromElement(element);
			list.add(tool);
			}
		return list;
		}

	private static Tool getToolFromElement(Element element) 
		{
		Tool tool = new Tool();
		// si preleva il nome
		String nome = element.getAttribute("NAME");
		tool.setName(nome);
		// si preleva la versione
		// getAttribute restituisce una stringa vuota se 
		// VERSION non è presente
		String versione = element.getAttribute("VERSION");
		if (!"".equals(versione))
			tool.setVersion(versione);
		// si preleva la descrizione
		String descrizione = element.getAttribute("DESCRIPTION");
		if (!"".equals(descrizione))
			tool.setDescription(descrizione);
		// si preleva l'autore
		String autore = element.getAttribute("AUTHOR");
		if (!"".equals(autore))
			tool.setAuthor(autore);
		// si preleva il solve
		NodeList nodeList = element.getElementsByTagName("SOLVE");
		// bisogna ricordarsi che possono non esserci elementi solve
		if (nodeList.getLength() > 0)
			{
			Element element2 = (Element)nodeList.item(0);
			Solve solve = getSolveFromElement(element2);
			tool.setSolve(solve);
			}
		return tool;
		}

	private static Solve getSolveFromElement(Element element2) 
		{
		Solve solve = null;
		if (element2 != null)
			{
			NodeList nodeList = element2.getElementsByTagName("METHOD");
			// bisogna ricordarsi che possono non essere presenti elementi method
			List<Method> list = new ArrayList<Method>();
			if (nodeList.getLength() > 0)
				{
				for (int i = 0; i < nodeList.getLength(); i++)
					{
					Element element = (Element)nodeList.item(i);
					Method method = getMethodFromElement(element);
					list.add(method);
					}
				solve = new SolveM(list);				
				}
			else
				{
				NodeList nodeList2 = element2.getElementsByTagName("PARAMS");
				// bisogna ricordarsi che possono non essere presenti elementi params
				List<Params> list2 = new ArrayList<Params>();			
				if (nodeList2.getLength() > 0)
					{
					for (int i = 0; i < nodeList2.getLength(); i++)
						{
						Element element = (Element)nodeList2.item(i);
						Params params = getParamsFromElement(element);
						list2.add(params);
						}
					solve = new SolveP(list2);				
					}
				}
			}
		return solve;
		}

	private static Params getParamsFromElement(Element element) 
		{
		Params params = new Params();
		String nome = element.getAttribute("NAME");
		params.setName(nome);
		String tipo = element.getAttribute("TYPE");
		params.setType(tipo);
		return params;
		}

	private static Method getMethodFromElement(Element element) 
		{
		Method method = new Method();
		String nome = element.getAttribute("NAME");
		method.setName(nome);
		String descrizione = element.getAttribute("DESCRIPTION");
		method.setDescription(descrizione);
		// si impostano gli eventuali parametri
		NodeList nodeList2 = element.getElementsByTagName("PARAMS");
		// bisogna ricordarsi che possono non essere presenti elementi params
		List<Params> list = new ArrayList<Params>();
		if (nodeList2.getLength() > 0)
			{
			for (int i = 0; i < nodeList2.getLength(); i++)
				{
				Element element2 = (Element)nodeList2.item(i);
				Params params = getParamsFromElement(element2);
				list.add(params);
				}
			}
		method.setParamsList(list);
		return method;
		}
	}
