package com.example.swagger.ibps;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TabClass {
    public static Element createTabElement(String controlId, String controlLabel, String controlTypeToSearch) {
        Element tabControlElement = base.emptyXmlDoc.createElement("Control");
        tabControlElement.setAttribute("ControlId", controlId);
        tabControlElement.setAttribute("ControlType", controlTypeToSearch);
        tabControlElement.setAttribute("Caption", controlLabel); // You can set the Caption attribute to the control label
        tabControlElement.setAttribute("FontStyle", "");
        tabControlElement.setAttribute("FontWeight", "");
        tabControlElement.setAttribute("FontSize", "");
        tabControlElement.setAttribute("FontColor", "");
        tabControlElement.setAttribute("BackColor", "");
        tabControlElement.setAttribute("FontFamily", "");
        tabControlElement.setAttribute("DataOnDemand", "N");
        tabControlElement.setAttribute("FixTabHeader", "N");
        tabControlElement.setAttribute("CustomId", "");
        tabControlElement.setAttribute("Visible", "true");
        tabControlElement.setAttribute("iFontColor", "");
        tabControlElement.setAttribute("ControlStyle", "");
        tabControlElement.setAttribute("Grouping", "1");
        tabControlElement.setAttribute("MergeSection", "1");
        tabControlElement.setAttribute("Enable", "true");
        tabControlElement.setAttribute("ReadOnly", "false");
        tabControlElement.setAttribute("ReadOnlyStyle", "N");
        tabControlElement.setAttribute("Summary", "");
        tabControlElement.setAttribute("ShowSaveButtons", "N");
        tabControlElement.setAttribute("CombinedFontWeight", "Bold");
        return tabControlElement;
    }
}
