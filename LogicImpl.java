package com.searchReplace.assignment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LogicImpl {
	
	public static void main(String args[]) {
		try {
			System.out.println("I am in main");
			//searchAndReplace("trace", "error", "xml");
			searchAndReplace(args[0], args[1], args[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void searchAndReplace(String searchText,String replaceText, String file) throws SAXException, IOException, ParserConfigurationException, TransformerException, XPathExpressionException {
		System.out.println("I am in search"+file);
		if (file.equalsIgnoreCase("txt")) {			
			Path path = Paths.get("C:\\Users\\Devanshu_mishra\\Downloads\\manifesto.txt");
			Charset charset = StandardCharsets.UTF_8;

			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll(searchText, replaceText);
			Files.write(Paths.get("C:\\Users\\Devanshu_mishra\\Downloads\\result.txt"), content.getBytes(charset));
		}
		else if (file.equalsIgnoreCase("xml")) {
              System.out.println("I Am Here");
			Document doc = DocumentBuilderFactory.newInstance()
			            .newDocumentBuilder().parse(new InputSource("C:\\Users\\Devanshu_mishra\\Downloads\\configuration.xml"));
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList)xpath.evaluate("//*[contains(@level,'"+searchText+"')]",doc, XPathConstants.NODESET);
			NodeList nodesName = (NodeList)xpath.evaluate("//*[contains(@name,'"+searchText+"')]",doc, XPathConstants.NODESET);
			for (int idx = 0; idx < nodes.getLength(); idx++) {
			    Node value = nodes.item(idx).getAttributes().getNamedItem("level");
			    String val = value.getNodeValue();
			    value.setNodeValue(val.replaceAll(searchText, replaceText));
			    Node valueName = nodesName.item(idx).getAttributes().getNamedItem("name");
			    String valName = valueName.getNodeValue();
			    valueName.setNodeValue(valName.replaceAll(searchText, replaceText));
			}
			
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(new DOMSource(doc), new StreamResult(new File("C:\\Users\\Devanshu_mishra\\Downloads\\result.xml")));  
	        
		}
		
        
	}
}
