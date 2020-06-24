package com.sub.controller;

import com.sub.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;

import java.util.*;

import static com.sub.controller.PatientController.getSizze;
import static com.sub.controller.PatientController.sizze;


public class StatisticsCityController {

   @FXML
    private BarChart<String, Integer> barChart2;

    @FXML
    private CategoryAxis xAxis2;

    public ObservableList<String> cityNames = FXCollections.observableArrayList();

    public int citiesSize ;


    @FXML
    private void initialize() {
      //  String[] months =  DateFormatSymbols.getInstance(new Locale("pl")).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
       // cityNames.addAll(Arrays.asList(months));
        System.out.println(citiesSize);
    }

    public void setAxix(List<Person> persons) {
        String[] city = new String[getSizze()];
        int i = 0;
        for (Person p : persons) {
            city[i] = p.getCity();
            i++;
        }
        int end = city.length;
        Set<String> set = new HashSet<String>();

        city = new HashSet<String>(Arrays.asList(city)).toArray(new String[0]);

        cityNames.addAll(Arrays.asList(city));
        System.out.println( "dsdf " + cityNames.get( 1 ) );
        xAxis2.setCategories( cityNames );

        citiesSize = cityNames.size();

        test();
    }
private  void   test(){
    System.out.println("test  " + citiesSize);
    System.out.println("test2  " + sizze);
}

 public void setPatientCity(List<Person> persons) {

        int q = 0;
     int[] stat =new int[citiesSize];
     String[] pacjecc = new String[sizze];
     for (Person p : persons) {
         pacjecc[q]= p.getCity();
         q++;

     }
    int l = 0;

     for (int i = 0; i < citiesSize ; i++){
         for (int j = 0; j < sizze; j++){
         System.out.println( i + "x"+ j +" "+ cityNames.get( i ) + " vs " + pacjecc[j]);;
         System.out.println( i + "x"+ j +" "+ cityNames.get( i ).length()+ " vs " + pacjecc[j].length());;

            if(cityNames.get( i ) == pacjecc[j]) {
                stat[i] += 1  ;
                System.out.println(i + " - " +  stat[i]);

            }
             }


         System.out.println();
         }






/**
        for (int i = 0; i < citiesSize ; i++){
            //stat[i] = 5;
            for (Person p : persons) {
            if(cityNames.get( i ) == p.getCity()){
                System.out.println(cityNames.get( i ) + " vs " + p.getCity() );
                stat[i]++;
                System.out.println("va' " +stat[i]);

            }

            l++;
                System.out.println(l);
            }
        }
*/
    // XYChart.Series<String, Integer> series = new XYChart.Series<>();

   //  for (int i = 0; i < citiesSize; i++) {
   //      series.getData().add(new XYChart.Data<>(cityNames.get(i), stat[i]));
  //   }

  //   barChart2.getData().add(series);
    //    statCity[p] = p.getCity();
     //    if(p.getTestStatus()== true) {
     //        int month = p.getTestDate().getMonthValue() - 1;
     //     statPositive[month]++;
     //   }

     //    }

     //   XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
     //  XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
     //   series1.setName( "pozytywny" );
     //   series2.setName( "negatytywny" );


     // for (int i = 0; i < statPositive.length; i++) {
     //     series1.getData().add(new XYChart.Data<>(monthNames.get(i), statPositive[i]));
     //     series2.getData().add(new XYChart.Data<>(monthNames.get(i), statNegative[i]));

     // }

     //  barChart.getData().add(series1);
     // barChart.getData().add(series2);
 }

}