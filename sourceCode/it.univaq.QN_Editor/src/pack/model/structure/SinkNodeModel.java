package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.LoadingException;
import pack.errorManagement.SavingException;
import pack.figures.Etichetta;
import pack.model.IArc;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.schema.SinkNodeType;

public class SinkNodeModel implements INode {

	private static final long serialVersionUID = 1L;
	
	private Point point;
	
	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        { 
								new TextPropertyDescriptor(XPOS_PROP, "X"), // id and description pair
								new TextPropertyDescriptor(YPOS_PROP, "Y"),
								new TextPropertyDescriptor(NAME, "Name")
		                        };
	
	private transient List<IArc> sourceArcs = new ArrayList<IArc>();
	
	private transient List<IArc> targetArcs = new ArrayList<IArc>();
	
	private transient Etichetta etichetta;
	
	private transient IQNM iqnm;
	
	// i seguenti attributi si riferiscono all'xml
	private String name = "";
	
	
	// il seguente costruttore viene utilizzato per l'inserimento di un nuovo nodo
	public SinkNodeModel() 
		{
		super();
		}

	public SinkNodeModel(SinkNodeType sinkNodeType2, Point point, IQNM iqnm)
		throws LoadingException
		{
		this.iqnm = iqnm;
		if (sinkNodeType2.hasName())
			{
			String string = null;
			try {
				string = sinkNodeType2.getName().asString(); 
				this.name = string;
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (this.iqnm.duplicateNodeFromXML(string))
				{
				// siamo nel caso in cui abbiamo un duplicato nel nome del nodo
				Finestra.mostraLE("Loading error: nodes with duplicate names");
				}
			}
		else
			{
			// siamo nel caso in cui il nodo sink non ha un nome
			Finestra.mostraLE("Loading error: sink node without name");
			}
		this.point = point;
		}

	@Override
	public void addSourceArc(IArc arc) 
		{
		sourceArcs.add(arc);
		// si notifica il cambio
		firePropertyChange(SOURCE_ARCS_PROP, null, arc);
		}

	@Override
	public Point getLocation() 
		{
		return point.getCopy();
		}

	@Override
	public String getServerName() 
		{
		String string;
		string = this.name;
		return string;
		}

	@Override
	public List<IArc> getSourceArcs() 
		{
		return sourceArcs;
		}

	@Override
	public List<IArc> getTargetArcs() 
		{
		return targetArcs;
		}

	@Override
	public void removeSourceArc(IArc arc) 
		{
		sourceArcs.remove(arc);
		// si notifica il cambio di proprietà
		firePropertyChange(SOURCE_ARCS_PROP, null, arc);
		}

	@Override
	public void setLocation(Point location) throws Exception 
		{
		if (location == null) 
			{
			Finestra.mostraIE("location is null");
			}
		if (point == null )
			point = location.getCopy();
		else
			point.setLocation(location.getCopy());
		firePropertyChange(LOCATION_PROP, null, location);
		}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) 
		{
		try {
			if (l == null) 
				{
				Finestra.mostraIE("listener is null");
				}
			getPcsDelegate().addPropertyChangeListener(l);
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void firePropertyChange(String property, Object oldValue,
			Object newValue) 
		{
		if (getPcsDelegate().hasListeners(property)) 
			{
			getPcsDelegate().firePropertyChange(property, oldValue, newValue);
			}
		}

	@Override
	public Object getEditableValue() 
		{
		return this;
		}

	@Override
	public PropertyChangeSupport getPcsDelegate() 
		{
		return this.pcsDelegate;
		}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() 
		{
		return descriptors;
		}

	@Override
	public Object getPropertyValue(Object id) 
		{
		if (XPOS_PROP.equals(id)) 
			{
			return Integer.toString(point.x);
			}
		if (YPOS_PROP.equals(id)) 
			{
			return Integer.toString(point.y);
			}
		if (NAME.equals(id))
			{
			if (this.name != null)
				return this.name;
			else
				return "";
			}
		return null;
		}

	@Override
	public boolean isPropertySet(Object id) 
		{
		return false;
		}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) 
		{
		if (l != null) 
			{
			pcsDelegate.removePropertyChangeListener(l);
			}
		}

	@Override
	public void resetPropertyValue(Object id) 
		{}

	@Override
	public void setPcsDelegate(PropertyChangeSupport pcsDelegate) 
		{
		this.pcsDelegate = pcsDelegate;
		}

	@Override
	public void setPropertyValue(Object id, Object value) 
		{
		try {
			if (XPOS_PROP.equals(id)) 
				{
				int x = Integer.parseInt((String) value);
				setLocation(new Point(x, point.y));
				} 
			else if (YPOS_PROP.equals(id)) 
				{
				int y = Integer.parseInt((String) value);
				setLocation(new Point(point.x, y));
				}
			else if (NAME.equals(id))
				{
				String oldName = this.name;
				this.name = value.toString();
				if (this.iqnm.duplicateNodeFromPMIF(this.name))
					{
					// siamo nel caso in cui ci sono due nodi con lo stesso nome
					Finestra.mostraIE("Setting error: node name "+this.name+" already exists");
					}
				// si aggiornano le richieste relative e gli archi in cui risulta
				// essere un'estremo
				updateNameElements(oldName, this.name);
				// si notifica il cambio di nome
				firePropertyChange(NAME, null, value);
				}
			else Finestra.mostraIE("bad property");
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void setSourceArcs(List<IArc> list) 
		{
		this.sourceArcs = list;
		}

	@Override
	public void setTargetArcs(List<IArc> list) 
		{
		this.targetArcs = list;
		}

	public SinkNodeType generaDom() throws SavingException
		{
		SinkNodeType sinkNodeType = new SinkNodeType();
		// si generano gli attributi
		if (this.name != null)
			{
			try {
				sinkNodeType.addName(this.name);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		else
			// siamo nel caso in cui il nodo sink non ha un nome
			Finestra.mostraSE("Saving error: sink node without name");
		return sinkNodeType;
		}

	@Override
	public void addTargetArc(IArc arcModel) 
		{
		this.targetArcs.add(arcModel);
		// si notifica il cambio
		firePropertyChange(INode.TARGET_ARCS_PROP, null, arcModel);
		}

	@Override
	public void removeTargetArc(IArc arcModel) 
		{
		this.targetArcs.remove(arcModel);
		// si notifica il cambio
		firePropertyChange(INode.TARGET_ARCS_PROP, null, arcModel);
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof SinkNodeModel) || arg0 == null)
			return false;
		SinkNodeModel sinkNodeModel = (SinkNodeModel)arg0;
		if (this.name != null && sinkNodeModel.name != null)
			{
			if (!(this.getServerName().equals(sinkNodeModel.getServerName())))
				return false;
			}
		else return false;
		return true;
		}

	@Override
	public Etichetta getEtichetta() 
		{
		return this.etichetta;
		}

	@Override
	public void setEtichetta(Etichetta etichetta) 
		{
		this.etichetta = etichetta;
		}

	@Override
	public IQNM getQNM() 
		{
		return this.iqnm;
		}

	@Override
	public void setQNM(IQNM iqnm) 
		{
		this.iqnm = iqnm;
		}

	@Override
	public void updateNameElements(String string, String string2) 
		{
		IQNM iqnm = this.iqnm;
		List<IRequest> list = iqnm.getRequestsFromNodeName(string);
		List<IArc> list2 = iqnm.getSourceArcsFromID(string);
		List<IArc> list3 = iqnm.getTargetArcsFromID(string);
		List<ITransit> list4 = iqnm.getTransitsFromTo(string);
		for (IRequest request : list)
			{
			request.setServerID(string2);
			}
		for (IArc arc : list2)
			{
			arc.setFromNode(string2);
			}
		for (IArc arc : list3)
			{
			arc.setToNode(string2);
			}
		for (ITransit transit : list4)
			{
			transit.setTo(string2);
			}
		}
	}
