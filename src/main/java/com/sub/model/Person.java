package com.sub.model;

import com.sub.util.LocalDateAdapter;
import javafx.beans.property.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;



public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty city;
    private  final BooleanProperty testStatus;
    private final ObjectProperty<LocalDate> testDate;

    /**
     * Default constructor.
     */
    public Person() {
        this(null, null,null, true);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName, String city, boolean testStatus) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.city = new SimpleStringProperty(city);
        this.testStatus = new SimpleBooleanProperty(testStatus);
        this.testDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2020, 6, 1));

    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public boolean getTestStatus() {
        return testStatus.get();
    }

    public BooleanProperty testStatusProperty() {
        return testStatus;
    }

    public void setTestStatus(boolean testStatus) {
        this.testStatus.set( testStatus );
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getTestDate() {
        return testDate.get();
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate.set( testDate );
    }

    public ObjectProperty<LocalDate> testDateProperty() {
        return testDate;
    }


}