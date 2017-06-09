/**
 * 
 */
package factories;

import it.univaq.wizards.pages.QNSolveServersWizardPage;
import it.univaq.wizards.providers.ExtractedStringsContentProvider;
import it.univaq.wizards.providers.IExtractedStringsContentProviderTools;

/**
 * @author Mirko
 *
 */
public class ContentProviderToolsFatory 
	{
	
	public static IExtractedStringsContentProviderTools getExtractedStringsContentProviderTools(
			QNSolveServersWizardPage serversWizardPage)
		{
		return new ExtractedStringsContentProvider(serversWizardPage);
		}
	
	}
