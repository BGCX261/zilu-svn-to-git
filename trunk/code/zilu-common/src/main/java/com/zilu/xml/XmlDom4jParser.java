package com.zilu.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("unchecked")
public class XmlDom4jParser implements XmlParser {

	public XmlMap parse(String xml) throws XmlException {
		Document doc = read(xml);
		XmlMap xmlObject = createXmlObject();
		Element root = doc.getRootElement();
		xmlObject.put(root.getName(), doParse(root));
		return xmlObject;
	}
	
	
	private Document read(String xml) throws XmlException {
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		try {
			SAXReader reader = new SAXReader();
			reader.setEncoding("GBK");
			Document doc;
			try {
				doc = reader.read(is);
			} catch (DocumentException e) {
				throw new XmlException(e);
			}
			return doc;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}
	

	private Object doParse(Element element) {
		if (element.isTextOnly()) {
			return element.getText();
		}
		XmlMap obj = createXmlObject();
		if (element.attributeCount() > 0) {
//			List atts = element.attributes();
		}
		List elements = element.elements();
		for (int i=0; i<elements.size(); i++) {
			Element subElement = (Element) elements.get(i);
			obj.put(subElement.getName(), doParse(subElement));
		}
		return obj;
	}
	
	
	private XmlMap createXmlObject() {
		return new XmlMap();
	}
}
