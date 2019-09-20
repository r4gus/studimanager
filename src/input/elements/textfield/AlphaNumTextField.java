package input.elements.textfield;

import javafx.scene.control.TextFormatter;

public class AlphaNumTextField extends TemplateTextField {
    public AlphaNumTextField(int len) {
        super();

        String regex = "(^[a-zA-Z0-9äöüß_. ]{0," + len + "}$)";

        String finalRegex = regex;
        this.setTextFormatter(new TextFormatter<Object>(change ->
                (change.getControlNewText().matches(finalRegex)) ? change : null));
    }
}
