package com.example.swagger.ibps;

import org.w3c.dom.Element;

import java.io.IOException;
import java.util.List;

public class SectionClass {
    public static Element section(String controlId, String controlLabel, String
            dataType, String groupId, String identifier, String title, String
                                          saveValueType, List<base.frameControlNames> controlNames, String dataClassName, String caption, String ColumnLayout, String dbQuery, String caching) throws IOException {

        Element Frame = base.emptyXmlDoc.createElement("Frame");
        Frame.setAttribute("FrameId", "columnFrameLayout");
        Frame.setAttribute("SectionTheme", "Select");
        Frame.setAttribute("ControlType", "frame");
        Frame.setAttribute("Caption", caption);
        Frame.setAttribute("FontStyle", "");
        Frame.setAttribute("FontWeight", "");
        Frame.setAttribute("FontSize", "");
        Frame.setAttribute("FontColor", "");
        Frame.setAttribute("BackColor", "");
        Frame.setAttribute("SectionBackColor", "");
        Frame.setAttribute("FontFamily", "");
        Frame.setAttribute("Enable", "true");
        Frame.setAttribute("DataOnDemand", "N");
        Frame.setAttribute("ColumnLayout", ColumnLayout);
        Frame.setAttribute("BorderColor", "");
        Frame.setAttribute("BorderWidth", "1");
        Frame.setAttribute("Grouping", "1");
        Frame.setAttribute("MergeSection", "1");
        Frame.setAttribute("ReadOnlyStyle", "N");
        Frame.setAttribute("Summary", "");
        Frame.setAttribute("CustomId", "");
        Frame.setAttribute("CombinedFontWeight", "Regular");
        Frame.setAttribute("FrameState", "false");
        Frame.setAttribute("FrameVisible", "false");
        Frame.setAttribute("GridLayout", "false");
        Frame.setAttribute("GridLayoutInputLabel", "FFFFFF");
        Frame.setAttribute("GridLayoutBorderColor", "FFFFFF");


        for (int i = 0; i < controlNames.size(); i++) {
            base.frameControlNames control = controlNames.get(i);
            String names = control.controlName;
            System.out.println(controlNames.size() + " Names-------------");
            if (names.equalsIgnoreCase("textarea")) {
                Frame.appendChild(TextAreaClass.textarea(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("combo")) {
                Frame.appendChild(ComboBoxClass.handleComboControl(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching, dataClassName));
            } else if (names.equalsIgnoreCase("listbox")) {
                Frame.appendChild(ListBoxClass.handleListBoxControl(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching, dataClassName));
            }  else if (names.equalsIgnoreCase("textbox")) {
                Frame.appendChild(TextBoxClass.textbox(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("checkbox")) {
                Frame.appendChild(CheckBoxClass.checkbox(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("datepick")) {
                Frame.appendChild(DatepickClass.datepick(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("label")) {
                Frame.appendChild(LableClass.label(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("radio")) {
                Frame.appendChild(RadioClass.radio(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("EmptyCell") || names.equalsIgnoreCase("EmptyRow")) {
                Frame.appendChild(EmptyCellRowClass.handleEmptyCellControl(controlId, names, dataType, groupId, identifier, title));
            } else if (controlId.startsWith("Slider")) {
                Frame.appendChild(SliderClass.handleSliderControlElement(controlId, names, dataType, groupId, identifier, title));
            } else if (controlId.startsWith("Doclist")) {
                Frame.appendChild(DoclistClass.handleDoclistControlElement(controlId, names, dataType, groupId, identifier, title));
            } else if (controlId.startsWith("Toggle")) {
                Frame.appendChild(ToggleClass.handleToggleControlElement(controlId, names, dataType, groupId, identifier, title));
            } else if (controlId.startsWith("Tile")) {
                Frame.appendChild(TileClass.handleTileControlElement(controlId, names, dataType, groupId, identifier, title));
            } else if (names.equalsIgnoreCase("frameend")) {
                break;
            }
        }


        Element eventElement1 = base.emptyXmlDoc.createElement("Event");
        Element eventsElement1 = base.emptyXmlDoc.createElement("Events");
        eventElement1.appendChild(eventsElement1);
        Frame.appendChild(eventElement1);

        return Frame;
    }
}
