package input.elements.textfield;

import javafx.scene.control.TextFormatter;

public class IntTextField extends TemplateTextField {
    public IntTextField(int len, boolean zero) {
        super();

        String regex = "(^$|[";
        regex += (zero ? "0" : "1");
        regex += "-9]";
        if(len > 1) {
            regex += "[0-9]{0," + (len-1) + "})";
        }

        String finalRegex = regex;
        this.setTextFormatter(new TextFormatter<Object>(change ->
                (change.getControlNewText().matches(finalRegex)) ? change : null));
    }
}
