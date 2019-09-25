package Main.about;

import Main.Main;
import config.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAbout implements Initializable {

    @FXML
    private WebView aboutWebView;

    private static String[] URL = {
            "https://github.com/r4gus/studimanager_about/blob/master/ABOUT_EN.md",
            "https://github.com/r4gus/studimanager_about/blob/master/ABOUT_DE.md"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String Url;

        if(Main.getConfig().getLanguage().equals(Language.DE)) {
            Url = URL[1];
        } else {
            Url = URL[0];
        }

        aboutWebView.getEngine().load(Url);
    }

}
