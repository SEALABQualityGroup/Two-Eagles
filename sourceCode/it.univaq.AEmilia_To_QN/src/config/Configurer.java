package config;

import mappingAEIintoElementiBase.IFromAEIintoElementiBaseFactory;
import mappingAEIintoElementiBase.secondRelease.FromAEIintoElementiBaseFactory2;
import mappingElementiBaseIntoPMIF.ISalvaElementiBaseFactory;
import mappingElementiBaseIntoPMIF.secondRelease.SalvaElementiBaseFactory2;
import restrizioniGenerali.IGeneraliRulesFactory;
import restrizioniGenerali.secondRelease.GeneraliRulesFactory2;
import restrizioniSpecifiche.ISpecificheFactory;
import restrizioniSpecifiche.ISpecificheRulesFactory;
import restrizioniSpecifiche.secondRelease.Specifiche2Factory;
import restrizioniSpecifiche.secondRelease.SpecificheRulesFactory2;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.EquivalenzaFactory2;

public final class Configurer {

	public static EquivalenzaFactory equivalenzaFactory;
	public static ISpecificheFactory specificheFactory;
	public static IGeneraliRulesFactory generaliRulesFactory;
	public static ISpecificheRulesFactory specificheRulesFactory;
	public static IFromAEIintoElementiBaseFactory fromAEIintoElementiBaseFactory;
	public static ISalvaElementiBaseFactory salvaElementiBaseFactory;

	static
		{
		equivalenzaFactory = new EquivalenzaFactory2();
		specificheFactory = new Specifiche2Factory();
		generaliRulesFactory = new GeneraliRulesFactory2();
		specificheRulesFactory = new SpecificheRulesFactory2();
		fromAEIintoElementiBaseFactory = new FromAEIintoElementiBaseFactory2();
		salvaElementiBaseFactory = new SalvaElementiBaseFactory2();
		}
}
