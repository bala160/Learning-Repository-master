package com.example.swagger.formdesign;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

public class IsolateXMLDifferences {

    public static void main(String[] args) {
        String filePath1 = "C:\\Users\\Balakrishnan\\Downloads\\NG_Form (5).xml";
        String filePath2 = "C:\\Users\\Balakrishnan\\Downloads\\NG_Form (6).xml";

        // Parse the XML files
        Document xml1 = parseXMLFile(filePath1);
        Document xml2 = parseXMLFile(filePath2);

        // Isolate differences
        isolateDifferences(xml1.getDocumentElement(), xml2.getDocumentElement());
    }

    public static Document parseXMLFile(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void isolateDifferences(Element element1, Element element2) {
        if (!element1.getNodeName().equals(element2.getNodeName())) {
            System.out.println("Different element names: " + element1.getNodeName() + " and " + element2.getNodeName());
        }

        NamedNodeMap attributes1 = element1.getAttributes();
        NamedNodeMap attributes2 = element2.getAttributes();

        if (attributes1.getLength() != attributes2.getLength()) {
            System.out.println("Different attribute counts for element: " + element1.getNodeName());
        } else {
            for (int i = 0; i < attributes1.getLength(); i++) {
                Node attribute1 = attributes1.item(i);
                Node attribute2 = attributes2.getNamedItem(attribute1.getNodeName());

                if (!attribute1.getNodeValue().equals(attribute2.getNodeValue())) {
                    System.out.println("Different attribute values for attribute: " + attribute1.getNodeName());
                    System.out.println("Value in XML1: " + attribute1.getNodeValue());
                    System.out.println("Value in XML2: " + attribute2.getNodeValue());
                }
            }
        }

        NodeList children1 = element1.getChildNodes();
        NodeList children2 = element2.getChildNodes();

        if (children1.getLength() != children2.getLength()) {
            System.out.println("Different child node counts for element: " + element1.getNodeName());
        } else {
            for (int i = 0; i < children1.getLength(); i++) {
                Node child1 = children1.item(i);
                Node child2 = children2.item(i);

                if (child1 instanceof Element && child2 instanceof Element) {
                    isolateDifferences((Element) child1, (Element) child2);
                }
            }
        }
    }
}
