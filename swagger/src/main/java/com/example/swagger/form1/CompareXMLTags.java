package com.example.swagger.form1;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CompareXMLTags {

    public static void main(String[] args) {
        String filePath1 = "C:\\Users\\Balakrishnan\\Downloads\\NG_Form (4).xml";
        String filePath2 = "C:\\Users\\Balakrishnan\\Downloads\\NG_Form (5).xml";

        // Parse the XML files
        Document xml1 = parseXMLFile(filePath1);
        Document xml2 = parseXMLFile(filePath2);

        // Get unique tags and their properties from both XMLs
        Map<String, String> uniqueTagsProperties1 = getUniqueTagsWithProperties(xml1);
        Map<String, String> uniqueTagsProperties2 = getUniqueTagsWithProperties(xml2);

        // Display the unique tags and properties from each XML
        System.out.println("Unique tags and properties in " + filePath1 + ":");
        displayTagsProperties(uniqueTagsProperties1);
        System.out.println();

        System.out.println("Unique tags and properties in " + filePath2 + ":");
        displayTagsProperties(uniqueTagsProperties2);
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

    public static Map<String, String> getUniqueTagsWithProperties(Document document) {
        Map<String, String> uniqueTagsProperties = new HashMap<>();
        traverseXML(document.getDocumentElement(), uniqueTagsProperties);
        return uniqueTagsProperties;
    }

    public static void traverseXML(Element element, Map<String, String> uniqueTagsProperties) {
        String tagName = element.getTagName();
        NamedNodeMap attributes = element.getAttributes();

        if (!uniqueTagsProperties.containsKey(tagName)) {
            StringBuilder attributeInfo = new StringBuilder();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                attributeInfo.append(attribute.getNodeName()).append(": ").append(attribute.getNodeValue());
                if (i < attributes.getLength() - 1) {
                    attributeInfo.append(", ");
                }
            }
            uniqueTagsProperties.put(tagName, attributeInfo.toString());
        }

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                traverseXML((Element) child, uniqueTagsProperties);
            }
        }
    }

    public static void displayTagsProperties(Map<String, String> tagsProperties) {
        for (Map.Entry<String, String> entry : tagsProperties.entrySet()) {
            System.out.println("Tag: " + entry.getKey());
            System.out.println("Properties: " + entry.getValue());
            System.out.println();
        }
    }
}
