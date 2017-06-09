package it.univaq.ttep.editors.aemilia;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class AemiliaDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					new AemiliaPartitionScanner(),
					new String[] {
						AemiliaPartitionScanner.Aemilia_COMMENT,
						AemiliaPartitionScanner.Aemilia_archi_Elem_Types,
						AemiliaPartitionScanner.Aemilia_archi_Topology,
						AemiliaPartitionScanner.Aemilia_behav_Variations});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}