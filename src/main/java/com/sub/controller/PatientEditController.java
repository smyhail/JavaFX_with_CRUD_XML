package com.sub.controller;

import com.sub.model.Person;
import com.sub.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 Okno dialogowe edycji danych osoby.
 */
public class PatientEditController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField cityField;
    @FXML
    public TextField statusField;
    @FXML
    public TextField dataField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Inicjuje klasę kontrolera. Ta metoda jest wywoływana automatycznie
     * po załadowaniu pliku fxml.
     */
    @FXML
    private void initialize() {
    }

    /**
     Ustawia etap tego okna dialogowego.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        
        // Set the dialog icon.
        this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }

    /**
     * Ustawia osobę do edycji w oknie dialogowym.

     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        cityField.setText(person.getCity());
        statusField.setText( String.valueOf( person.getTestStatus() ) );
        dataField.setText( String.valueOf( person.getTestDate() ) );
    }

    /**
     * Zwraca true, jeśli użytkownik kliknął OK, w przeciwnym razie false.

     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Pobieranie danych do formatki
     * */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setCity(cityField.getText());
            person.setTestStatus( Boolean.parseBoolean( statusField.getText() ) );
            person.setTestDate( LocalDate.parse( dataField.getText() ) );

            okClicked = true;
            dialogStage.close();
        }
    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Weryfikacja formularza
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nie podano imienia!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Nie podano nazwiska!\n";
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "NIe podano miasta!\n";
        }
        if (statusField.getText() == null || statusField.getText().length() == 0) {
            errorMessage += "NIe wprowadzono statusu!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Wystąpił błąd!!!");
            alert.setHeaderText("Popraw nieprawidłowe pola");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}