import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

    public String dateConversion(Date date) {
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = outputFormat.format(date);
        System.out.println("Formatted date: " + formattedDate);
        return formattedDate;
    }

    public String addMonths(Date date, int days) {
        System.out.println("Date ---" + date + " Days-----" + days);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days); // Add 3 months
        Date utilDate = calendar.getTime();
        String formattedDate = dateConversion(utilDate);
        System.out.println("New Date after adding 3 months: " + formattedDate);
        return formattedDate;
    }
}
