package pack;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.swt.graphics.Image;


public class Utility 
	{

	public static Image createImage(String fileName) 
		{
		InputStream stream = PMIFPlugin.class.getResourceAsStream(fileName);
		// come si può notare il dispositivo può essere null
		Image image = new Image(null, stream);
		try {
			stream.close();
			} 
		catch (IOException ioe) 
			{
			ioe.printStackTrace();
			}
		return image;
		}
	}
