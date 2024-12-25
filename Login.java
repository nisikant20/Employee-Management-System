import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Random; // Add this import for generating OTP

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JDialog; // Add this import for creating a new window

public class Login extends JFrame implements ActionListener {

    JTextField tfusername;
    JPasswordField tfpassword;
    JCheckBox showPassword;
    JButton forgotPassword;
    String generatedOtp; // Add this variable to store the generated OTP
    JLabel contactInfo; // Add this variable to display contact information

    Login(){

        getContentPane().setBackground(Color.white);
        setLayout(null);
    
        //>>Username
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40,20,100,30);
        add(lblusername);
        tfusername = new JTextField();
        tfusername.setBounds(150,20,150,30);
        add(tfusername);

        //>>Password
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40,70,100,30);
        add(lblpassword);
        tfpassword = new JPasswordField();
        tfpassword.setBounds(150,70,150,30);
        add(tfpassword);

        //>>Show Password
        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(150,110,150,30);
        showPassword.setBackground(Color.white);
        showPassword.addActionListener(this);
        add(showPassword);

        //>>Login
        JButton login = new JButton("LOGIN");
        login.setBounds(150,140,150,30);
        login.addActionListener(this);
        add(login);

        //>>Forgot Password
        forgotPassword = new JButton("Forgot Password?");
        forgotPassword.setBounds(150,180,150,30);
        forgotPassword.addActionListener(this);
        add(forgotPassword);

        //>>Contact Information
        contactInfo = new JLabel("For any query, contact: +91-7008529510");
        contactInfo.setBounds(150, 220, 300, 30);
        add(contactInfo);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel  image = new JLabel(i3);
        image.setBounds(350,0,150,150);
        add(image);
        setSize(600,300);
        setLocation(450,200);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                tfpassword.setEchoChar((char) 0);
            } else {
                tfpassword.setEchoChar('*');
            }
        } else if (e.getSource() == forgotPassword) {
            JOptionPane.showMessageDialog(null, "Please contact admin to reset your password.");
        } else {
            try {
                String username = tfusername.getText(); // Get the username from the text field
                String password = new String(tfpassword.getPassword()); // Get the password from the password field
                Conn c = new Conn();
                
                // Corrected SQL query with single quotes around username and password
                String query = "SELECT * FROM login WHERE username = '" + username + "' AND password = '" + password + "'";
                
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    generatedOtp = generateOtp();
                    showOtpWindow(generatedOtp);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                    setVisible(false);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void showOtpWindow(String otp) {
        JDialog otpDialog = new JDialog(this, "Enter OTP", true);
        otpDialog.setLayout(null);
        otpDialog.setSize(300, 200);
        otpDialog.setLocationRelativeTo(this);

        JLabel otpLabel = new JLabel("OTP sent: " + otp);
        otpLabel.setBounds(50, 30, 200, 30);
        otpDialog.add(otpLabel);

        JTextField otpField = new JTextField();
        otpField.setBounds(50, 70, 200, 30);
        otpDialog.add(otpField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 110, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredOtp = otpField.getText();
                if (enteredOtp.equals(generatedOtp)) {
                    otpDialog.dispose();
                    setVisible(false);
                    new Home();
                } else {
                    JOptionPane.showMessageDialog(otpDialog, "Invalid OTP");
                }
            }
        });
        otpDialog.add(submitButton);

        otpDialog.setVisible(true);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); // Generate a 4-digit OTP
        return String.valueOf(otp);
    }

    public static void main(String[] args) {
        new Login();
    }
}
