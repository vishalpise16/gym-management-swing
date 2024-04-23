import javax.swing.*;
import java.sql.*;
import java.util.Date;

import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.JXDatePicker;

import java.util.Calendar;

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

    Connection con;
    PreparedStatement pst;
    AppUtil appUtil;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Kothrud Fitness");
        frame.setContentPane(new MainWindow().mainPanelParent);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(300, 100, 800, 500);
        frame.setVisible(true);
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/management_system", "root", "admin");
            System.out.println("Successs");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();

        }
    }

    public void loadClasses() {
        appUtil = new AppUtil();
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


    public MainWindow() {
        loadClasses();
        connect();
        tableLoad();
        JXDatePicker1.setDate(Calendar.getInstance().getTime());
        saveButton.addActionListener(e -> {
            String firstName, lastName, joiningDate, renewalDate;
            int days;
            firstName = textField1.getText();
            lastName = textField2.getText();
            try {
                joiningDate = appUtil.dateConversion(JXDatePicker1.getDate());
                days = (int) (Double.parseDouble(textField3.getText()) * 30);
                renewalDate = appUtil.addMonths(JXDatePicker1.getDate(), days);
                pst = con.prepareStatement("insert into member_info(first_name,last_name,date_of_joining,renewal_date)values(?,?,?,?)");
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, joiningDate);
                pst.setString(4, renewalDate);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                JXDatePicker1.setDate(new Date());
                textField1.requestFocus();
                tableLoad();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Check Entered Values!!!!");
            }
        });

    }
}
