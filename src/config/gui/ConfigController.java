package config.gui;

import config.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logging.MyLogger;
import Main.Main;
import message.Notification;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ConfigController implements Initializable {

    @FXML
    private GridPane config_grid;

    @FXML
    private Text sceneTitle;

    @FXML
    private Label languageSelectTitle;

    @FXML
    private ComboBox<Language> languageSelectComboBox;

    @FXML
    private Label filePathTitle;

    @FXML
    private TextField filePathField;

    @FXML
    private Button choose;

    @FXML
    private Button apply;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        config_grid.getStylesheets().add(getClass().getResource("../../main.css").toExternalForm());

        setLanguage();
        populateLanguageSelectComboBox();
        setFilePath();
    }

    /**
     * Set the text for all elements depending on the language {@link Language} settings specified
     * in {@link config.Config}.
     */
    private void setLanguage() {
        sceneTitle.setText(Main.getBundle().getString("Settings"));
        languageSelectTitle.setText(Main.getBundle().getString("Language") + ":");
        filePathTitle.setText(Main.getBundle().getString("Path") + ":");
        choose.setText(Main.getBundle().getString("Choose"));
        apply.setText(Main.getBundle().getString("Apply"));
    }

    private void populateLanguageSelectComboBox() {
        for(Language language: Language.values())
            languageSelectComboBox.getItems().add(language);
        languageSelectComboBox.getSelectionModel().select(Main.getConfig().getLanguage());
    }

    private void setFilePath() {
        filePathField.setText(Main.getConfig().getTimetablePath());
    }

    /**
     * Opens a file chooser window where the user can specify a new path to a json file.
     */
    @FXML
    public void handleChooseButtonAction(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(config_grid.getScene().getWindow());
        if(selectedFile == null) return;
        filePathField.setText(selectedFile.getPath());
    }

    /**
     * Apply changes by updating {@link config.Config}. Some changes only come into effect after
     * restarting the application.
     */
    @FXML
    public void handleApplyButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) config_grid.getScene().getWindow();

        /* ############# update config class ################################# */
        Main.getConfig().setLanguage(languageSelectComboBox.getSelectionModel().getSelectedItem());
        Main.getConfig().setTimetablePath(filePathField.getText());

        try {
            Main.getConfig().store();

            Notification.showConfirm(   Main.getBundle().getString("Success"),
                                        Main.getBundle().getString("SettingsApplied"),
                                        Main.getPrimaryStage());
        } catch (Exception exc) {
            MyLogger.LOGGER.log(Level.SEVERE,
                    exc.getMessage()+"\nIn: src.config.gui.ConfigController"+"\nWhile: Clicking the apply button");
            Notification.showAlert( Main.getBundle().getString("Failure"),
                                    Main.getBundle().getString("UnableToApply"),
                                    Main.getPrimaryStage());
        } finally {
            stage.close();
        }
    }

}
