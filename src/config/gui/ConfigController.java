package config.gui;

import config.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logging.MyLogger;
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static message.Notification.showAlertWindow;

public class ConfigController implements Initializable {

    @FXML
    private GridPane config_grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        config_grid.getStylesheets().add(getClass().getResource("../../main.css").toExternalForm());

        adjustGridPane(config_grid);
        makeForm(config_grid);

    }

    /**
     * Setup the specified {@link GridPane}. All adjustments to the gridPane should be made within this method to
     * keep everything at one place.
     *
     * @param gridPane The {@link GridPane} to adjust.
     */
    private void adjustGridPane(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "adjustGridPane", gridPane);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setAlignment(Pos.CENTER);

        MyLogger.LOGGER.exiting(getClass().toString(), "adjustGridPane");
    }

    private void makeForm(GridPane gridPane) {

        MyLogger.LOGGER.entering(getClass().toString(), "makeForm", gridPane);

        Text sceneTitle = new Text(Main.getBundle().getString("Settings"));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- LANGUAGE -------------------------------------
         */
        Label languageTitle = new Label(Main.getBundle().getString("Language"));
        gridPane.add(languageTitle, 0, 1);

        ComboBox languageSelect = new ComboBox();
        for(Language language: Language.values())
            languageSelect.getItems().add(language);
        languageSelect.getSelectionModel().select(Main.getConfig().getLanguage());
        gridPane.add(languageSelect, 1, 1);

        /*
        --------------- Timetable PATH ----------------------------------------
         */
        Label timetablePathTitle = new Label(Main.getBundle().getString("Path"));
        gridPane.add(timetablePathTitle, 0, 2);

        TextField pathField = new TextField();
        pathField.setText(Main.getConfig().getTimetablePath());
        gridPane.add(pathField, 1, 2);

        FileChooser fileChooser = new FileChooser();
        Button choose = new Button(Main.getBundle().getString("Choose"));
        choose.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(config_grid.getScene().getWindow());
            pathField.setText(selectedFile.getPath());
        });
        gridPane.add(choose, 2, 2);

        /*
        ---------------- APPLY CHANGES ---------------------------------------------
         */
        Button apply = new Button(Main.getBundle().getString("Apply"));
        apply.setOnAction(e -> {
            try {
                /* ############# update config class ################################# */
                Main.getConfig().setLanguage((Language) languageSelect.getSelectionModel().getSelectedItem());
                Main.getConfig().setTimetablePath(pathField.getText());

                /* ############ apply changes ######################################## */
                Main.getConfig().store();

                /* ############## close window #######################################*/
                Stage stage = (Stage) gridPane.getScene().getWindow();
                stage.close();

                /* ################# show info message ############################### */
                showAlertWindow(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), Main.getBundle().getString("Success"),
                        Main.getBundle().getString("RestartMsg"));
            } catch (IOException exc) {
                MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage() + "\nIn: src.config.gui.ConfigController" +
                        "\nWhile: Clicking the apply button");
                exc.printStackTrace();
            }
        });
        gridPane.add(apply, 0, 3);


        MyLogger.LOGGER.exiting(getClass().toString(), "makeForm");
    }
}
