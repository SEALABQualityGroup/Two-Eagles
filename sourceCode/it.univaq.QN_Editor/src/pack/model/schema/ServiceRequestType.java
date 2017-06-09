/**
 * ServiceRequestType.java
 *
 * This file was generated by XMLSPY 5 Enterprise Edition.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the XMLSPY Documentation for further details.
 * http://www.altova.com/xmlspy
 */


package pack.model.schema;



/**
 * Una ServiceRequest � una richiesta di servizio diretta
 * ad un Server. Una richiesta di servizio pu� essere fatta
 * specificando o il tempo medio di servizio (e in tal caso
 * si parla di TimeServiceRequest) o la domanda media di
 * servizio fornita ad ogni Workload che visita il Server
 * (e in tal caso si parla di DemandServiceRequest) o,
 * infine, il numero medio di visite fatte ad un
 * WorkUnitServer (e in tal caso si parla di
 * WorkUnitServiceRequest).
 * <br/>
 * - DemandServiceRequest: Una DemandServiceRequest � una
 * richiesta di servizio diretta ad un Server, fatta
 * specificando la domanda media di servizio (tempo di
 * servizio moltiplicato per il numero delle visite) fornito
 * ad ogni Workload che visita il Server specificato.
 * <br/>
 * - TimeServiceRequest: Una TimeServiceRequest � una
 * richiesta di servizio diretta ad un Server, fatta
 * specificando il tempo medio di servizio ed il numero di
 * visite fornite per ogni Workload che visita il Server
 * specificato.
 * <br/>
 * - WorkUnitServiceRequest: Una WorkUnitServiceRequest �
 * una richiesta di servizio diretta ad un WorkUnitServer,
 * fatta specificando il numero medio di visite che i
 * Workloads effettueranno al WorkUnitServer.
 * <pre>
 * <code>
 * &lt;xsd:complexType name="ServiceRequestType"&gt;
 * 	&lt;xsd:choice minOccurs="0" maxOccurs="unbounded"&gt;
 * 		&lt;xsd:element name="TimeServiceRequest" type="TimeServType" minOccurs="0" maxOccurs="unbounded"/&gt;
 * 		&lt;xsd:element name="DemandServiceRequest" type="DemandServType" minOccurs="0" maxOccurs="unbounded"/&gt;
 * 		&lt;xsd:element name="WorkUnitServiceRequest" type="WorkUnitServType" minOccurs="0" maxOccurs="unbounded"/&gt;
 * 	&lt;/xsd:choice&gt;
 * &lt;/xsd:complexType&gt;
 * </code>
 * </pre>
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class ServiceRequestType 
	extends pack.model.altova.xml.Node 
	{

	private static final long serialVersionUID = 1L;
	
	public ServiceRequestType() {
		super();
	}

	public ServiceRequestType(ServiceRequestType node) {
		super(node);
	}

	public ServiceRequestType(org.w3c.dom.Node node) {
		super(node);
	}

	public ServiceRequestType(org.w3c.dom.Document doc) {
		super(doc);
	}

	public int getTimeServiceRequestMinCount() {
		return 0;
	}

	public int getTimeServiceRequestMaxCount() {
		return Integer.MAX_VALUE;
	}

	public int getTimeServiceRequestCount() {
		return getDomChildCount(Element, null, "TimeServiceRequest");
	}

	public boolean hasTimeServiceRequest() {
		return hasDomChild(Element, null, "TimeServiceRequest");
	}

	public TimeServType getTimeServiceRequestAt(int index) throws Exception {
		return new TimeServType(getDomChildAt(Element, null, "TimeServiceRequest", index));
	}

	public TimeServType getTimeServiceRequest() throws Exception {
		return getTimeServiceRequestAt(0);
	}

	public void removeTimeServiceRequestAt(int index) {
		removeDomChildAt(Element, null, "TimeServiceRequest", index);
	}

	public void removeTimeServiceRequest() {
		while (hasTimeServiceRequest())
			removeTimeServiceRequestAt(0);
	}

	public void addTimeServiceRequest(TimeServType value) {
		appendDomElement(null, "TimeServiceRequest", value);
	}

	public void insertTimeServiceRequestAt(TimeServType value, int index) {
		insertDomElementAt(null, "TimeServiceRequest", index, value);
	}

	public void replaceTimeServiceRequestAt(TimeServType value, int index) {
		replaceDomElementAt(null, "TimeServiceRequest", index, value);
	}

	public int getDemandServiceRequestMinCount() {
		return 0;
	}

	public int getDemandServiceRequestMaxCount() {
		return Integer.MAX_VALUE;
	}

	public int getDemandServiceRequestCount() {
		return getDomChildCount(Element, null, "DemandServiceRequest");
	}

	public boolean hasDemandServiceRequest() {
		return hasDomChild(Element, null, "DemandServiceRequest");
	}

	public DemandServType getDemandServiceRequestAt(int index) throws Exception {
		return new DemandServType(getDomChildAt(Element, null, "DemandServiceRequest", index));
	}

	public DemandServType getDemandServiceRequest() throws Exception {
		return getDemandServiceRequestAt(0);
	}

	public void removeDemandServiceRequestAt(int index) {
		removeDomChildAt(Element, null, "DemandServiceRequest", index);
	}

	public void removeDemandServiceRequest() {
		while (hasDemandServiceRequest())
			removeDemandServiceRequestAt(0);
	}

	public void addDemandServiceRequest(DemandServType value) {
		appendDomElement(null, "DemandServiceRequest", value);
	}

	public void insertDemandServiceRequestAt(DemandServType value, int index) {
		insertDomElementAt(null, "DemandServiceRequest", index, value);
	}

	public void replaceDemandServiceRequestAt(DemandServType value, int index) {
		replaceDomElementAt(null, "DemandServiceRequest", index, value);
	}

	public int getWorkUnitServiceRequestMinCount() {
		return 0;
	}

	public int getWorkUnitServiceRequestMaxCount() {
		return Integer.MAX_VALUE;
	}

	public int getWorkUnitServiceRequestCount() {
		return getDomChildCount(Element, null, "WorkUnitServiceRequest");
	}

	public boolean hasWorkUnitServiceRequest() {
		return hasDomChild(Element, null, "WorkUnitServiceRequest");
	}

	public WorkUnitServType getWorkUnitServiceRequestAt(int index) throws Exception {
		return new WorkUnitServType(getDomChildAt(Element, null, "WorkUnitServiceRequest", index));
	}

	public WorkUnitServType getWorkUnitServiceRequest() throws Exception {
		return getWorkUnitServiceRequestAt(0);
	}

	public void removeWorkUnitServiceRequestAt(int index) {
		removeDomChildAt(Element, null, "WorkUnitServiceRequest", index);
	}

	public void removeWorkUnitServiceRequest() {
		while (hasWorkUnitServiceRequest())
			removeWorkUnitServiceRequestAt(0);
	}

	public void addWorkUnitServiceRequest(WorkUnitServType value) {
		appendDomElement(null, "WorkUnitServiceRequest", value);
	}

	public void insertWorkUnitServiceRequestAt(WorkUnitServType value, int index) {
		insertDomElementAt(null, "WorkUnitServiceRequest", index, value);
	}

	public void replaceWorkUnitServiceRequestAt(WorkUnitServType value, int index) {
		replaceDomElementAt(null, "WorkUnitServiceRequest", index, value);
	}
	
	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof ServiceRequestType))
			return false;
		ServiceRequestType serviceRequestType = (ServiceRequestType)obj;
		if (!this.hasTimeServiceRequest() && serviceRequestType.hasTimeServiceRequest())
			return false;
		if (this.hasTimeServiceRequest() && !serviceRequestType.hasTimeServiceRequest())
			return false;
		if (this.hasTimeServiceRequest() && serviceRequestType.hasTimeServiceRequest())
			{
			if (this.getTimeServiceRequestCount() != serviceRequestType.getTimeServiceRequestCount())
				return false;
			for (int i = 0; i < this.getTimeServiceRequestCount(); i++)
				{
				try {
					if (!this.getTimeServiceRequestAt(i).equals(serviceRequestType.getTimeServiceRequestAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasDemandServiceRequest() && serviceRequestType.hasDemandServiceRequest())
			return false;
		if (this.hasDemandServiceRequest() && !serviceRequestType.hasDemandServiceRequest())
			return false;
		if (this.hasDemandServiceRequest() && serviceRequestType.hasDemandServiceRequest())
			{
			for (int i = 0; i < this.getDemandServiceRequestCount(); i++)
				{
				try {
					if (!this.getDemandServiceRequestAt(i).equals(serviceRequestType.getDemandServiceRequestAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkUnitServiceRequest() && serviceRequestType.hasWorkUnitServiceRequest())
			return false;
		if (this.hasWorkUnitServiceRequest() && !serviceRequestType.hasWorkUnitServiceRequest())
			return false;
		if (this.hasWorkUnitServiceRequest() && serviceRequestType.hasWorkUnitServiceRequest())
			{
			if (this.getWorkUnitServiceRequestCount() != serviceRequestType.getWorkUnitServiceRequestCount())
				return false;
			for (int i = 0; i < this.getWorkUnitServiceRequestCount(); i++)
				{
				try {
					if (!this.getWorkUnitServiceRequestAt(i).equals(serviceRequestType.getWorkUnitServiceRequestAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		return true;
		}
	}