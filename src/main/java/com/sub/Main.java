package com.sub;


import com.sub.controller.*;
import com.sub.model.Person;
import com.sub.model.PersonListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;


public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    public static String app;


    //przykładowe dane do startu aplikacji
    public Main() {

        personData.add(new Person("Tymoteusz ","Majchrzak","Gliwice",true));
        personData.add(new Person("Jakub ","Bielecki","Wrocław",false));
        personData.add(new Person("Liliana ","Sobczak","Czestachowa",true));
        personData.add(new Person("Franciszek ","Kacprzak","Łodz",false));
        personData.add(new Person("Julia ","Piotrowska","Warszawa",true));
        personData.add(new Person("Szymon ","Jankowski","Poznań",false));
        personData.add(new Person("Martyna ","Przybylska","Sieradz",true));
        personData.add(new Person("Urszula ","Smolińska","Gdynia",false));

    }


    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        app = "Lista pacjentów";
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(app);
        
        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("/images/address_book_32.png"));

        initRootLayout();

        showPersonOverview();
    }

            ///funkcja uruchamiaj okna plikacji
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Main.class
                    .getResource( "/Main.fxml" ));
            rootLayout = (BorderPane) loader.load();

            // SPokaż scenę zawierającą układ główny.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Daj kontrolerowi dostęp do głównej aplikacji.
            BaseController controller = loader.getController();
            controller.setMain(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // załadowanie ostatniego używanego pliku
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Wyświetla przegląd osób w układzie głównym.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Main.class.getResource( "/Patient.fxml" ));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PatientController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Otwiera okno dialogowe do edycji szczegółów dla określonej osoby. Jeśli użytkownik
     * kliknie OK, zmiany zostaną zapisane w podanym obiekcie osoby i prawdziwe
     * jest zwracany.

     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Main.class.getResource( "/PatientEdit.fxml" ));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edycja danych pacjenta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PatientEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            
            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("/images/edit.png"));

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Otwiera okno dialogowe, aby wyświetlić statystyki z podziałem na daty
     */
    public void showStatisticPerDate() {
        ssas();
    }

    private void ssas() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Main.class.getResource( "/Statistyc_Data.fxml" ));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Statystyki z podziałem na daty wykonania testu");
            dialogStage.initModality( Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            StatisticsDataController controller = loader.getController();
            controller.setPatientData(personData);

            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("/images/calendar.png"));

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Otwiera okno dialogowe, aby wyświetlić statystyki z podziałem na miasta
     */
    public void showStatisticPerCity() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Main.class.getResource( "/Statistyc_City.fxml" ));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Statystyki z podziałem na miasta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // Set the dialog icon.
            dialogStage.getIcons().add(new Image("/images/calendar.png"));

            // Set the persons into the controller.
            StatisticsCityController controller = loader.getController();
            controller.setAxix(personData);
            controller.setPatientCity(personData);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Zwraca preferencję pliku osoby, tj. Plik, który był ostatnio otwierany.
     * Preferencje są odczytywane z rejestru określonego systemu operacyjnego. Jeśli nie,
     * można znaleźć preferencję, zwracana jest wartość null.
     * 
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage( Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

     /**
     * Ustawia ścieżkę do aktualnie załadowanego pliku. Ścieżka jest utrwalona
     * rejestr specyficzny dla systemu operacyjnego.
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage( Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle( app + " - " + file.getName());
        } else {
            prefs.remove("filePath");
            // Update the stage title.
            primaryStage.setTitle( app );
        }
    }

    /**
     * Ładuje dane osoby z określonego pliku. Obecne dane osoby będą
     * być zastąpionym.
     * 
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance( PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            personData.clear();
            personData.addAll(wrapper.getPersons());
            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not load data");
        	alert.setContentText("Could not load data from file:\n" + file.getPath());
        	alert.showAndWait();
        }
    }

    /**
     * Zapisuje dane bieżącej osoby w określonym pliku.
     * 
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance( PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not save data");
        	alert.setContentText("Could not save data to file:\n" + file.getPath());
        	
        	alert.showAndWait();
        }
    }

    /**
     * Zwraca główny etap.
    * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}