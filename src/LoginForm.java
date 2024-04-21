import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginForm {
    private JPanel loginPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton submitButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginForm().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(500,200,400,300);
        frame.setVisible(true);
    }

    public LoginForm() {
        connect();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    Connection con;
    PreparedStatement pst;

    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/management_system", "root","admin");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();

        }
    }
}
