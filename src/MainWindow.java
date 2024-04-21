import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdesktop.swingx.JXDatePicker;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainWindow {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTable table1;
    private JButton saveButton;
    private JPanel mainPanelParent;
    private JTextField textField3;
    private JXDatePicker JXDatePicker1;

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

    public java.sql.Date dateConversion(Date date){
        DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utilDate = null;
        try {
            Date convertedDate = inputFormat.parse(date.toString());
            String formattedDate = outputFormat.format(convertedDate);
            System.out.println("Formatted date: " + formattedDate);
            utilDate = outputFormat.parse(formattedDate);

        } catch (ParseException es) {
            es.printStackTrace();
        }
        return new java.sql.Date(Objects.requireNonNull(utilDate).getTime());
    }

    public MainWindow() {
        connect();
        JXDatePicker1.setDate(Calendar.getInstance().getTime());
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String firstName, lastName;
                java.sql.Date joiningDate;
                double months;
                firstName = textField1.getText();
                lastName = textField2.getText();
                joiningDate = dateConversion(JXDatePicker1.getDate());
                months = Double.parseDouble(textField3.getText());
                try {
                    pst = con.prepareStatement("insert into member_info(first_name,last_name,date_of_joining)values(?,?,?)");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setDate(3, joiningDate);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    //table_load();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField1.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
    }
}
