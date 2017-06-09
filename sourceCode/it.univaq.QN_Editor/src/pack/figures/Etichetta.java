package pack.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public class Etichetta extends Label 
	{

	public Etichetta(Image i) 
		{
		super(i);
		Font font = JFaceResources.getDefaultFont();
		this.setFont(font);
		setTextPlacement(PositionConstants.SOUTH);
		}

	public void impostaEtichetta(String string)
		{
		setText(string);
		Dimension dimTesto = calculateTextSize();
		Dimension dimLabel = calculateLabelSize(dimTesto);
		this.setSize(dimLabel);
		}
	
	}
