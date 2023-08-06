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

public class CompareXMLProperties {

    public static void main(String[] args) {
        String filePath1 = "C:\\Users\\Balakrishnan\\Downloads\\NG_Form (4).xml";
        String filePath2 = "C:\\Users\\Balakrishnan\\Downloads\\NG_Form (5).xml";

        // Parse the XML files
        Document xml1 = parseXMLFile(filePath1);
        Document xml2 = parseXMLFile(filePath2);

        // Get tags and their properties from both XMLs
        Map<String, String> tagsProperties1 = getTagsWithProperties(xml1);
        Map<String, String> tagsProperties2 = getTagsWithProperties(xml2);

        // Find differences in properties between the two XMLs
        Map<String, String> differences = findPropertyDifferences(tagsProperties1, tagsProperties2);

        // Display the tags with different properties
        System.out.println("Tags with different properties:");
        displayTagsWithDifferentProperties(differences, tagsProperties1, tagsProperties2);
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

    public static Map<String, String> getTagsWithProperties(Document document) {
        Map<String, String> tagsProperties = new HashMap<>();
        traverseXML(document.getDocumentElement(), tagsProperties);
        return tagsProperties;
    }

    public static void traverseXML(Element element, Map<String, String> tagsProperties) {
        String tagName = element.getTagName();
        NamedNodeMap attributes = element.getAttributes();

        StringBuilder attributeInfo = new StringBuilder();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            attributeInfo.append(attribute.getNodeName()).append(": ").append(attribute.getNodeValue());
            if (i < attributes.getLength() - 1) {
                attributeInfo.append(", ");
            }
        }
        tagsProperties.put(tagName, attributeInfo.toString());

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                traverseXML((Element) child, tagsProperties);
            }
        }
    }

    public static Map<String, String> findPropertyDifferences(Map<String, String> tagsProperties1,
                                                              Map<String, String> tagsProperties2) {
        Map<String, String> differences = new HashMap<>();
        for (Map.Entry<String, String> entry : tagsProperties1.entrySet()) {
            String tagName = entry.getKey();
            String properties1 = entry.getValue();
            String properties2 = tagsProperties2.get(tagName);
            if (properties2 != null && !properties1.equals(properties2)) {
                differences.put(tagName, properties2);
            }
        }
        return differences;
    }

    public static void displayTagsWithDifferentProperties(Map<String, String> differences,
                                                          Map<String, String> tagsProperties1,
                                                          Map<String, String> tagsProperties2) {
        for (Map.Entry<String, String> entry : differences.entrySet()) {
            System.out.println("Tag: " + entry.getKey());
            System.out.println("Properties in file1.xml: " + tagsProperties1.get(entry.getKey()));
            System.out.println("Properties in file2.xml: " + entry.getValue());
            System.out.println();
        }
    }
}


