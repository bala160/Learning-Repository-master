package com.example.swagger.ibps;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EmptyCellRowClass {
    public static Element handleEmptyCellControl(String controlId, String controlLabel, String dataType, String
            groupId, String identifier, String title) {
        Element controlElement = base.findControlElementByType(base.allFieldXmlDoc, "EmptyCell");
        if (controlElement != null) {
            Element newControl = (Element) base.emptyXmlDoc.importNode(controlElement, true);
            newControl.setAttribute("ControlId", controlId);
            newControl.setAttribute("ControlLabel", controlLabel);
            newControl.setAttribute("DataType", dataType);
            newControl.setAttribute("GroupId", groupId);
            newControl.setAttribute("Identifier", identifier);
            newControl.setAttribute("Title", title);

           /* NodeList styleNodes = controlElement.getElementsByTagName("Style");
            styleNodes.getLength();// newControl.appendChild(importedStyleNode);*/
            NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
            if (dataClassNodes.getLength() > 0) {
                Element dataClassElement = (Element) dataClassNodes.item(0);
                newControl.removeChild(dataClassElement);
            }

            return newControl;
        }
        return null;
    }
}
