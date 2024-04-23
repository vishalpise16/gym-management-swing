import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.JXDatePicker;

import java.util.Calendar;
import java.util.Locale;

public class MainWindow {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JButton saveButton;
    private JPanel mainPanelParent;
    private JTextField textField3;
    private JXDatePicker JXDatePicker1;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Kothrud Fitness");
        frame.setContentPane(new MainWindow().mainPanelParent);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(500, 200, 600, 300);
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/management_system", "root", "admin");
            System.out.println("Successs");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();

        }
    }

    public String dateConversion(Date date) {
        DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utilDate = null;
        String formattedDate = null;
        try {
            Date convertedDate = inputFormat.parse(date.toString());
            formattedDate = outputFormat.format(convertedDate);
            System.out.println("Formatted date: " + formattedDate);
            //utilDate = outputFormat.parse(formattedDate);

        } catch (ParseException es) {
            es.printStackTrace();
        }
        return formattedDate;
    }

    void tableLoad() {
        try {
            pst = con.prepareStatement("select * from member_info");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String addMonths(Date date, int days) {
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Date ---" + date + " Days-----" + days);
        Date utilDate = null;
        String formattedDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days); // Add 3 months
        utilDate = calendar.getTime();
        formattedDate = outputFormat.format(utilDate);
        System.out.println("New Date after adding 3 months: " + formattedDate);
        return formattedDate;
    }

    public MainWindow() {
        connect();
        tableLoad();
        JXDatePicker1.setDate(Calendar.getInstance().getTime());
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstName, lastName, joiningDate, renewalDate;
                int days;
                firstName = textField1.getText();
                lastName = textField2.getText();
                try {
                    joiningDate = dateConversion(JXDatePicker1.getDate());
                    days = (int) (Double.parseDouble(textField3.getText()) * 30);
                    renewalDate = addMonths(JXDatePicker1.getDate(), days);
                    pst = con.prepareStatement("insert into member_info(first_name,last_name,date_of_joining,renewal_date)values(?,?,?,?)");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, joiningDate);
                    pst.setString(4, renewalDate);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    //table_load();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    JXDatePicker1.setDate(new Date());
                    textField1.requestFocus();
                    tableLoad();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Check Entered Values!!!!");
                }
            }
        });

    }
}
