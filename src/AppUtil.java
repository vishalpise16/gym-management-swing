import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

    private static String dateFormat = "dd-MM-yyyy";

    public String dateConversion(Date date) {
        DateFormat outputFormat = new SimpleDateFormat(dateFormat);
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

    public Boolean dateComparison(String date) throws ParseException {
        Date renewalDate=new SimpleDateFormat(dateFormat).parse(date);
        Date currentDate = new Date();
        return currentDate.after(renewalDate);
    }
}
