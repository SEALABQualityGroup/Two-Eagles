package pack.factories;


import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import pack.editParts.ArcTypeEditPart;
import pack.editParts.QNMTypeEditPart;
import pack.editParts.ServerTypeEditPart;
import pack.editParts.SinkNodeTypeEditPart;
import pack.editParts.SourceNodeTypeEditPart;
import pack.editParts.WorkUnitServerTypeEditPart;
import pack.errorManagement.Finestra;
import pack.model.IArc;
import pack.model.IQNM;
import pack.model.structure.ServerModel;
import pack.model.structure.SinkNodeModel;
import pack.model.structure.SourceNodeModel;
import pack.model.structure.WorkUnitServerModel;


/**
 * Factory that maps pack.model elements to edit parts.
 * @author Mirko
 */

public class QNMEditPartFactory 
	implements EditPartFactory
	{
	
	private IQNM type;
	
	public QNMEditPartFactory(IQNM type) 
		{
		super();
		this.type = type;
		}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object modelElement) 
		{
		// get EditPart for pack.model element
		EditPart part = getPartForElement(modelElement);
		// store pack.model element in EditPart
		if (part != null) part.setModel(modelElement);
		return part;
		}

	/**
	 * Maps an object to an EditPart. 
	 * @throws RuntimeException if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object modelElement) 
		{
		if (modelElement instanceof IQNM) 
			{
			return new QNMTypeEditPart();
			}
		if (modelElement instanceof IArc) 
			{
			return new ArcTypeEditPart();
			}
		if (modelElement instanceof ServerModel)
			{
			return new ServerTypeEditPart(type);
			}
		if (modelElement instanceof WorkUnitServerModel)
			{
			return new WorkUnitServerTypeEditPart(type);
			}
		if (modelElement instanceof SourceNodeModel)
			{
			return new SourceNodeTypeEditPart(type);
			}
		if (modelElement instanceof SinkNodeModel)
			{
			return new SinkNodeTypeEditPart(type);
			}
		else
			{
			try {
				Finestra.mostraIE("unexpected model");
				} 
			catch (Exception e) 
				{}
			return null;
			}
		}
	}