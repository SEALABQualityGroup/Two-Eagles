package equivalenzaComportamentale.interfaces;

import java.util.List;

import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.UnconditionalGetBehavior;


/**
 * Questa interfaccia definisce i metodi da implementare
 * per determinare se un tipo di elemento architetturale
 * ha un camportamento equivalente ad un buffer con
 * capacità illimitata.
 * Contiene anche metodi che restituiscono le informazioni
 * che caratterizzano un buffer a capacita illimitata.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaBufferIllimitato 
	extends IEquivalenzaBuffer 
	{
	
	public List<UnconditionalGetBehavior> getUnconditionalGetBehaviors(); 
	
	}
