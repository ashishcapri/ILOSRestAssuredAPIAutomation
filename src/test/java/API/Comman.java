package API;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Comman {

  public static String CurrentDate =null;
    public static String twomonthDate =null;
    public static String random16DigitNumber = null; // Global variable

    public static void Setdate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate twoMonthsBefore = currentDate.minusMonths(2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        CurrentDate=currentDate.format(formatter);
        twomonthDate=twoMonthsBefore.format(formatter);


        System.out.println("Current Date: " + CurrentDate);
        System.out.println("Date Two Months Before: " + twomonthDate);
    }
    public static void generateRandom16DigitNumber() {
        Random random = new Random();
        long number = 1000000000000000L + (long) (random.nextDouble() * 9000000000000000L);
        random16DigitNumber = String.valueOf(number);
    }


}
