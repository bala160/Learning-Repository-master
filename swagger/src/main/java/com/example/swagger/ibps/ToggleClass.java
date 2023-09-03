package com.example.swagger.ibps;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ToggleClass {
    public static Element handleToggleControlElement(String controlId, String controlLabel, String
            dataType, String groupId, String identifier, String title) {
        Element controlElement = base.emptyXmlDoc.createElement("Control");
        controlElement.setAttribute("ControlId", controlId);
        controlElement.setAttribute("ControlType", "CustomControl");
        controlElement.setAttribute("ControlLabel", controlLabel);
        controlElement.setAttribute("DataType", dataType);
        controlElement.setAttribute("GroupId", groupId);
        controlElement.setAttribute("Identifier", identifier);
        controlElement.setAttribute("Title", title);

// Create <Event> and <Events> elements
        Element eventElement = base.emptyXmlDoc.createElement("Event");
        Element eventsElement = base.emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);

// Append <Event> element to control
        controlElement.appendChild(eventElement);

// Create <Style> element with static attributes
        Element styleElement = base.emptyXmlDoc.createElement("Style");
        styleElement.setAttribute("ControlName", "Toggle");
        styleElement.setAttribute("CustomControlType", "Toggle");
        styleElement.setAttribute("ControlIcon", "toggle.png");

// Append <Style> element to control
        controlElement.appendChild(styleElement);

// Create <DataClass> element
        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", "");

// Append <DataClass> element to control
        controlElement.appendChild(dataClassElement);

        return controlElement;
    }
}
