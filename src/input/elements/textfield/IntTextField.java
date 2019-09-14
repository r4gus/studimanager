package input.elements.textfield;

import javafx.beans.NamedArg;
import javafx.scene.control.TextFormatter;

public class IntTextField extends TemplateTextField {

    public IntTextField(@NamedArg("length") int len) {
        super();

        boolean zero = true;
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


    public IntTextField(@NamedArg("length") int len,@NamedArg("IntZero") boolean zero) {
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
