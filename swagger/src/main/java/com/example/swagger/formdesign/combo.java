package com.example.swagger.formdesign;

public class combo {
    public static String getDefaultAttributeValueForCombo(String attributeName) {
        return switch (attributeName) {
            case "AddGradient", "AllowAutoScroll", "EnableSliderArrows", "FitToContentHeight", "FormHorizontal" -> "false";
            case "BackColor", "BackColorBoundary", "FormBackgroundColor", "ScreenBackgroundColor" -> "ffffff";
            case "BoderSize", "FormBorderWidth", "GradientPercent1", "LeftPercent", "RightPercent", "TopPercent" -> "0";
            case "CombinedFontWeight" -> "Bold";
            case "FixHeader", "FontStyle", "FormWidthType" -> "1";
            case "FontColor" -> "000000";
            case "FontFamily", "FontWeight" -> "2";
            case "FontSize" -> "8";
            case "FormBorderColor" -> "d7d7d7";
            case "FormVertical", "HeaderAllignment" -> "center";
            case "FormWidth", "GradientPercent2" -> "100";
            case "GradientColor1", "GradientColor2" -> "transparent";
            case "ImageAllignment" -> "Center";
            case "ScreenBackgroundRadio" -> "color";
            case "ShowHeader" -> "true";
            default -> "";
        };
    }
}
