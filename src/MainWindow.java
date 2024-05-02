import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;

import com.mysql.cj.util.StringUtils;
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
    private JXDatePicker JXDatePicker1;
    private JScrollPane table_1;
    private JButton searchButton;
    private JTextField textField4;
    private JXDatePicker JXDatePicker2;
    private JButton attendButton;
    private JTextField textField3;
    private JButton resetButton;

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
            pst = con.prepareStatement("select id as ID,first_name as 'First Name',last_name as 'Last Name',date_of_joining as DOJ,renewal_date as DOE from member_info order by renewal_date");
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
            firstName = textField1.getText();
            lastName = textField2.getText();
            try {
                if (StringUtils.isNullOrEmpty(firstName)) {
                    throw new RuntimeException("First Name shouldn't be NULL");
                }
                joiningDate = appUtil.dateConversion(JXDatePicker1.getDate());
                renewalDate = appUtil.dateConversion(JXDatePicker2.getDate());
                pst = con.prepareStatement("insert into member_info(first_name,last_name,date_of_joining,renewal_date)values(?,?,?,?)");
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, joiningDate);
                pst.setString(4, renewalDate);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                textField1.setText("");
                textField2.setText("");
                JXDatePicker1.setDate(new Date());
                JXDatePicker2.setDate(new Date());
                textField1.requestFocus();
                tableLoad();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Check Entered Values!!!!\n" + e1.getMessage());
            }
        });

        searchButton.addActionListener(e -> {
            try {
                String empName = textField4.getText();
                if (StringUtils.isNullOrEmpty(empName)) {
                    throw new RuntimeException("Please enter Name!!!");
                }
                pst = con.prepareStatement("select id as ID,first_name as 'First Name',last_name as 'Last Name',date_of_joining as DOJ,renewal_date as DOE from member_info where upper(first_name) like upper(?)");
                pst.setString(1, "%" + empName + "%");
                ResultSet rs = pst.executeQuery();
                table1.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            finally {
                textField4.setText("");
            }
        });
        attendButton.addActionListener(e -> {
            try {
                String renewalDate = null;
                String empIdStr = textField3.getText();
                if (StringUtils.isNullOrEmpty(empIdStr)) {
                    throw new RuntimeException("ID shouldn't be NULL");
                }
                int empId = Integer.parseInt(empIdStr);
                pst = con.prepareStatement("select renewal_date from member_info where id=?");
                pst.setInt(1, empId);
                ResultSet rs = pst.executeQuery();
                if(rs.next())
                    renewalDate = rs.getString(1);
                if (Boolean.TRUE.equals(appUtil.dateComparison(renewalDate))) {
                    throw new MemberShipExpiredException("Membership Expired on: " + renewalDate);
                }
                JOptionPane.showMessageDialog(null, "Membership Active!!!!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            finally {
                textField3.setText("");
            }
        });
        resetButton.addActionListener(e -> {
            tableLoad();
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            JXDatePicker1.setDate(new Date());
        });
    }
}
