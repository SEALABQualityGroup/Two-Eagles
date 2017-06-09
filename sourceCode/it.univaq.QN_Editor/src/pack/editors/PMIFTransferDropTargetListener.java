/**
 * 
 */
package pack.editors;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.requests.SimpleFactory;

/**
 * @author Mirko
 *
 */
public class PMIFTransferDropTargetListener extends
		TemplateTransferDropTargetListener 
	{

	public PMIFTransferDropTargetListener(EditPartViewer viewer) 
		{
		super(viewer);
		}

	protected CreationFactory getFactory(Object template) 
		{
		return new SimpleFactory((Class<?>) template);
		}
	}