package API;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Comman {

  public static String CurrentDate =null;
    public static String twomonthDate =null;

    public static void Setdate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate twoMonthsBefore = currentDate.minusMonths(2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        CurrentDate=currentDate.format(formatter);
        twomonthDate=twoMonthsBefore.format(formatter);


        System.out.println("Current Date: " + CurrentDate);
        System.out.println("Date Two Months Before: " + twomonthDate);
    }



}
