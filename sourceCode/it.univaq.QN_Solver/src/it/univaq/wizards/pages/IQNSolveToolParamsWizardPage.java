/**
 * 
 */
package it.univaq.wizards.pages;

import java.util.List;

import org.eclipse.swt.widgets.Text;

import parserReply.Method;
import parserReply.Tool;

/**
 * @author Mirko
 *
 */
public interface IQNSolveToolParamsWizardPage 
	{

	Tool getTool();

	List<Text> getParametri();

	Text getSeparatore();

	Method getMetodo();

	}
