package com.sub.controller;

import com.sub.Main;
import com.sub.model.Person;
import com.sub.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PatientController {


    @FXML
    public TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;

    @FXML
    private Label cityLabel;
    @FXML
    public Label statusLabel;
    @FXML
    public Label dateLabel;
    // Odniesienie do głównej aplikacji.
    private Main main;

    public static int sizze;
    public static int getSizze() {
        return sizze;
    }
    public void setSizze(int sizze) {
        this.sizze = sizze;
    }
    /**
     * Konstruktor jest wywoływany przed metodą initialize().
     */
    public PatientController() {
    }
    /**
     * Inicjuje klasę kontrolera. Ta metoda jest wywoływana automatycznie
     * po załadowaniu pliku fxml.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        // Clear person details.
        showPersonDetails(null);
        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        System.out.println(sizze);
    }
    /**
     * Jest wywoływany przez główną aplikację w celu uzyskania odniesienia do siebie.
     *
     * @param main
     */
    public void setMain(Main main) {
        this.main = main;
        // Add observable list data to the table
        personTable.setItems( main.getPersonData());
       setSizze( personTable.getItems().size() );
    }
    
    /**
     * Wypełnia wszystkie pola tekstowe, aby wyświetlić szczegółowe informacje o osobie.
     * Jeśli określona osoba ma wartość null, wszystkie pola tekstowe są usuwane.
     * 
     * @param person osoba lub zero
     */
    private void showPersonDetails(Person person) {

        sizze = 0;
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
         //   streetLabel.setText(person.getStreet());
          //  postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            statusLabel.setText( String.valueOf( person.getTestStatus() ) );
            cityLabel.setText(person.getCity());
            dateLabel.setText( DateUtil.format(person.getTestDate()));
            sizze++;
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
          statusLabel.setText( "" );
            cityLabel.setText("");
           dateLabel.setText("");
        }


    }
    
    /**
     * Wywoływany, gdy użytkownik kliknie przycisk usuwania.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            alarmNWO( "Nie zaznaczono", "Nie zaznaczono pacjenta", "Prosze zaznacz pacjenta w tabeli ." );
        }
    }


    /**
     * Wywoływany, gdy użytkownik kliknie nowy przycisk. Otwiera okno dialogowe do edycji
     * szczegóły dotyczące nowej osoby.
     */
    @FXML
    private void handleNewPerson() {

        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if (okClicked) {
            main.getPersonData().add(tempPerson);
        }
    }

    /**
     * Wywoływany, gdy użytkownik kliknie przycisk edycji. Otwiera okno dialogowe do edycji
     * szczegóły dotyczące wybranej osoby.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            alarmNWO( "Błąd", "NIe wybrano osoby", "Prosze wybuerz pacjenta z tabeli." );
        }
    }

    private void alarmNWO(String s, String s2, String s3) {
        // Nothing selected.
        Alert alert = new Alert( AlertType.ERROR );
        alert.initOwner( main.getPrimaryStage() );
        alert.setTitle( s );
        alert.setHeaderText( s2 );
        alert.setContentText( s3 );

        alert.showAndWait();
    }

}