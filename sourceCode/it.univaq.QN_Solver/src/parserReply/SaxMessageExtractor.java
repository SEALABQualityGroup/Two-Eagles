package parserReply;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxMessageExtractor extends DefaultHandler 
	{

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		String string = String.copyValueOf(ch);
		System.out.println(string);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		super.endPrefixMapping(prefix);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		super.error(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		super.fatalError(e);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		super.ignorableWhitespace(ch, start, length);
	}

	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		super.notationDecl(name, publicId, systemId);
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		super.processingInstruction(target, data);
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws IOException, SAXException {
		return super.resolveEntity(publicId, systemId);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		super.setDocumentLocator(locator);
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		super.skippedEntity(name);
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		super.startPrefixMapping(prefix, uri);
	}

	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		super.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		super.warning(e);
	}
	}
