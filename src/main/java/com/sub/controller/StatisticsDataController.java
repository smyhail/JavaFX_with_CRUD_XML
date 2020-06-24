package com.sub.controller;

import com.sub.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class StatisticsDataController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(new Locale("pl")).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));
        xAxis.setCategories(monthNames);


    }

    public void setPatientData(List<Person> persons) {
        // Count the number of people having their birthday in a specific month.
        int[] statPositive = new int[12];
        int[] statNegative = new int[12];
        for (Person p : persons) {
            if(p.getTestStatus()== true) {
                int month = p.getTestDate().getMonthValue() - 1;
                statPositive[month]++;
            }
            if(p.getTestStatus()== false) {
                int month = p.getTestDate().getMonthValue() - 1;
                statNegative[month]++;
            }
        }

        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
        series1.setName( "pozytywny" );
        series2.setName( "negatytywny" );



        for (int i = 0; i < statPositive.length; i++) {
            series1.getData().add(new XYChart.Data<>(monthNames.get(i), statPositive[i]));
            series2.getData().add(new XYChart.Data<>(monthNames.get(i), statNegative[i]));

        }

        barChart.getData().add(series1);
        barChart.getData().add(series2);
    }
}