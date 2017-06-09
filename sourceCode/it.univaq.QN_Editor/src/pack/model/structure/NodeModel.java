package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.SavingException;
import pack.model.IModelElement;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.schema.NodeType;
import pack.model.schema.ServerType;
import pack.model.schema.SinkNodeType;
import pack.model.schema.SourceNodeType;
import pack.model.schema.WorkUnitServerType;

public class NodeModel implements IModelElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate= new PropertyChangeSupport(this);

	private static IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[0];
	
	private List<INode> nodes = new ArrayList<INode>();
	
	// i seguenti attributi derivano dall'xml
		
	// il seguente costruttore viene utilizzato nell'azione di inserimento di un contenitore di nodi
	public NodeModel() 
		{
		super();
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
		return null;
		}

	@Override
	public PropertyChangeSupport getPcsDelegate() 
		{
		return this.pcsDelegate;
		}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() 
		{
		return propertyDescriptors;
		}

	@Override
	public Object getPropertyValue(Object id) 
		{
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
		{}
	
	public boolean removeChild(INode node) 
		{
		if (nodes.contains(node))
			{
			nodes.remove(node);
			// si notifica il cambio
			firePropertyChange(IQNM.NODE_REMOVED_PROP, null, node);
			return true;
			}
		return false;
		}
	
	public boolean addChild(INode node) 
		{
		nodes.add(node);
		// si notifica il cambio
		firePropertyChange(IQNM.NODE_ADDED_PROP, null, node);
		return true;
		}
	
	public List<INode> getNodes() 
		{
		return nodes;
		}
	
	private List<ServerModel> getServers()
		{
		List<ServerModel> list = new ArrayList<ServerModel>();
		for (INode node : this.nodes)
			{
			if (node instanceof ServerModel)
				list.add((ServerModel)node);
			}
		return list;
		}
	
	private List<SinkNodeModel> getSinks()
		{
		List<SinkNodeModel> list = new ArrayList<SinkNodeModel>();
		for (INode node : this.nodes)
			{
			if (node instanceof SinkNodeModel)
				list.add((SinkNodeModel)node);
			}
		return list;
		}
	
	private List<SourceNodeModel> getSources()
		{
		List<SourceNodeModel> list = new ArrayList<SourceNodeModel>();
		for (INode node : this.nodes)
			{
			if (node instanceof SourceNodeModel)
				list.add((SourceNodeModel)node);
			}
		return list;
		}
	
	private List<WorkUnitServerModel> getWorkUnits()
		{
		List<WorkUnitServerModel> list = new ArrayList<WorkUnitServerModel>();
		for (INode node : this.nodes)
			{
			if (node instanceof WorkUnitServerModel)
				list.add((WorkUnitServerModel)node);
			}
		return list;
		}

	public NodeType generaDom() throws SavingException
		{
		NodeType nodeType = new NodeType();
		for (ServerModel serverModel : this.getServers())
			{
			ServerType serverType = serverModel.generaDom();
			nodeType.addServer(serverType);
			}
		for (SinkNodeModel sinkNodeModel : this.getSinks())
			{
			SinkNodeType sinkNodeType = sinkNodeModel.generaDom();
			nodeType.addSinkNode(sinkNodeType);
			}
		for (SourceNodeModel sourceNodeModel : this.getSources())
			{
			SourceNodeType sourceNodeType = sourceNodeModel.generaDom();
			nodeType.addSourceNode(sourceNodeType);
			}
		for (WorkUnitServerModel workUnitServerModel : this.getWorkUnits())
			{
			WorkUnitServerType workUnitServerType = workUnitServerModel.generaDom();
			nodeType.addWorkUnitServer(workUnitServerType);
			}
		return nodeType;
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof NodeModel) || arg0 == null)
			return false;
		NodeModel nodeModel = (NodeModel)arg0;
		if (!(this.nodes.equals(nodeModel.nodes)))
			return false;
		return true;
		}
	}
