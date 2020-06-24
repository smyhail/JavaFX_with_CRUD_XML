package com.sub.controller;

import com.sub.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

import java.io.File;

public class BaseController {

    // Odniesienie do głównej aplikacji
    private Main main;

    //setter do głownego okna
    public void setMain(Main main) {
        this.main = main;
    }


    /**
     * okodowanie  menu FILE
     */
    @FXML
    private void handleNew() {
        main.getPersonData().clear();
        main.setPersonFilePath(null);
    }


    //import danych z pliku XML
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        // Ustaw filtr rozszerzeń
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        // Pokaż okno dialogowe zapisywania pliku
        File file = fileChooser.showOpenDialog( main.getPrimaryStage());

        if (file != null) {
            main.loadPersonDataFromFile(file);
        }
    }


    @FXML
    private void handleSave() {
        File personFile = main.getPersonFilePath();
        if (personFile != null) {
            main.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Otwiera FileChooser, aby pozwolić użytkownikowi wybrać plik do zapisania.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Filtruj rozszerzenia tylko XML
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog( main.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            main.savePersonDataToFile(file);
        }
    }

    
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Spis pacjentów");
    	alert.setHeaderText("O aplikacji");
    	alert.setContentText("Zadaniem aplikacji jest gromadzenie danych o statusie przepowadzony testów na obecność Koranowitusa \n \n" +
                "Dane zapisywane sa w postaci XML " );

    	alert.showAndWait();
    }

    //zamykanie aplikacji
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    @FXML
    private void showStatisticsData() {
      main.showStatisticPerDate();
    }

    public void showStatisticsCity(ActionEvent actionEvent) {
        main.showStatisticPerCity();
    }
    // TODO: 03.06.2020 zmienic to 
}