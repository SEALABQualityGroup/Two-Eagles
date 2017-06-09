package equivalenzaComportamentale;

import equivalenzaComportamentale.interfaces.IEquivalenzaArriviFiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviInfiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferIllimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferLimitato;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoinConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoinSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;
import equivalenzaComportamentale.secondRelease.EquivalenzaArriviFiniti2;
import equivalenzaComportamentale.secondRelease.EquivalenzaArriviInfiniti2;
import equivalenzaComportamentale.secondRelease.EquivalenzaBufferIllimitato2;
import equivalenzaComportamentale.secondRelease.EquivalenzaBufferLimitato2;
import equivalenzaComportamentale.secondRelease.EquivalenzaForkConBuffer2;
import equivalenzaComportamentale.secondRelease.EquivalenzaForkSenzaBuffer2;
import equivalenzaComportamentale.secondRelease.EquivalenzaJoin2;
import equivalenzaComportamentale.secondRelease.EquivalenzaRoutingConBuffer2;
import equivalenzaComportamentale.secondRelease.EquivalenzaRoutingSenzaBuffer2;
import equivalenzaComportamentale.secondRelease.EquivalenzaServizioConBuffer2;
import equivalenzaComportamentale.secondRelease.EquivalenzaServizioSenzaBuffer2;

public class EquivalenzaFactory2 
	implements EquivalenzaFactory 
	{

	@Override
	public IEquivalenzaBufferLimitato getFCB() 
		{
		return new EquivalenzaBufferLimitato2();
		}

	@Override
	public IEquivalenzaForkSenzaBuffer getFPNB() 
		{		
		return new EquivalenzaForkSenzaBuffer2();
		}

	@Override
	public IEquivalenzaForkConBuffer getFPWB() 
		{
		return new EquivalenzaForkConBuffer2();
		}

	@Override
	public IEquivalenzaJoinSenzaBuffer getJPNB() 
		{
		return null;
		}

	@Override
	public IEquivalenzaJoinConBuffer getJPWB() 
		{
		return null;
		}

	@Override
	public IEquivalenzaRoutingSenzaBuffer getRPNB() 
		{
		return new EquivalenzaRoutingSenzaBuffer2();
		}

	@Override
	public IEquivalenzaRoutingConBuffer getRPWB() 
		{
		return new EquivalenzaRoutingConBuffer2();
		}

	@Override
	public IEquivalenzaArriviFiniti getSCAP() 
		{
		return new EquivalenzaArriviFiniti2();
		}

	@Override
	public IEquivalenzaServizioSenzaBuffer getSPNB() 
		{
		return new EquivalenzaServizioSenzaBuffer2();
		}

	@Override
	public IEquivalenzaServizioConBuffer getSPWB() 
		{
		return new EquivalenzaServizioConBuffer2();
		}

	@Override
	public IEquivalenzaBufferIllimitato getUB() 
		{
		return new EquivalenzaBufferIllimitato2();
		}

	@Override
	public IEquivalenzaArriviInfiniti getUPAP() 
		{
		return new EquivalenzaArriviInfiniti2();
		}

	@Override
	public IEquivalenzaJoin getJP() 
		{
		return new EquivalenzaJoin2();
		}
	}
