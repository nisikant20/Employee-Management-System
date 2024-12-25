import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Salary extends JFrame implements ActionListener {
    JTextField tfName, tfBasic, tfHRA, tfDA, tfBonus, tfTotal, tfTax;
    JButton calculate, back, fetch;
    JComboBox<String> cbEmpId;
    JLabel incomeStatus; // Add this variable to display income status

    Salary() {
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icons/Tax-Saving-Tips.jpg"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 800, 600);
        add(backgroundLabel);

        JLabel label = new JLabel("Salary Calculation Module");
        label.setBounds(250, 20, 300, 30);
        label.setFont(new Font("Tahoma", Font.BOLD, 18));
        backgroundLabel.add(label);

        JLabel labelEmpId = new JLabel("Employee ID:");
        labelEmpId.setBounds(50, 70, 100, 30);
        backgroundLabel.add(labelEmpId);

        cbEmpId = new JComboBox<>();
        cbEmpId.setBounds(200, 70, 150, 30);
        backgroundLabel.add(cbEmpId);

        JLabel labelName = new JLabel("Name:");
        labelName.setBounds(50, 110, 100, 30);
        backgroundLabel.add(labelName);

        tfName = new JTextField();
        tfName.setBounds(200, 110, 150, 30);
        tfName.setEditable(false);
        backgroundLabel.add(tfName);

        JLabel labelBasic = new JLabel("Basic Salary:");
        labelBasic.setBounds(50, 150, 100, 30);
        backgroundLabel.add(labelBasic);

        tfBasic = new JTextField();
        tfBasic.setBounds(200, 150, 150, 30);
        backgroundLabel.add(tfBasic);

        JLabel labelHRA = new JLabel("HRA:");
        labelHRA.setBounds(50, 190, 100, 30);
        backgroundLabel.add(labelHRA);

        tfHRA = new JTextField();
        tfHRA.setBounds(200, 190, 150, 30);
        backgroundLabel.add(tfHRA);

        JLabel labelDA = new JLabel("DA:");
        labelDA.setBounds(50, 230, 100, 30);
        backgroundLabel.add(labelDA);

        tfDA = new JTextField();
        tfDA.setBounds(200, 230, 150, 30);
        backgroundLabel.add(tfDA);

        JLabel labelBonus = new JLabel("Bonus:");
        labelBonus.setBounds(50, 270, 100, 30);
        backgroundLabel.add(labelBonus);

        tfBonus = new JTextField();
        tfBonus.setBounds(200, 270, 150, 30);
        backgroundLabel.add(tfBonus);

        JLabel labelTax = new JLabel("Tax:");
        labelTax.setBounds(50, 310, 100, 30);
        backgroundLabel.add(labelTax);

        tfTax = new JTextField();
        tfTax.setBounds(200, 310, 150, 30);
        tfTax.setEditable(false);
        backgroundLabel.add(tfTax);

        JLabel labelTotal = new JLabel("Total Salary:");
        labelTotal.setBounds(50, 350, 100, 30);
        backgroundLabel.add(labelTotal);

        tfTotal = new JTextField();
        tfTotal.setBounds(200, 350, 150, 30);
        tfTotal.setEditable(false);
        backgroundLabel.add(tfTotal);

        incomeStatus = new JLabel();
        incomeStatus.setBounds(50, 390, 300, 30);
        backgroundLabel.add(incomeStatus);

        fetch = new JButton("Fetch");
        fetch.setBounds(400, 70, 100, 30);
        fetch.addActionListener(this);
        backgroundLabel.add(fetch);

        calculate = new JButton("Calculate");
        calculate.setBounds(400, 310, 100, 30);
        calculate.addActionListener(this);
        backgroundLabel.add(calculate);

        back = new JButton("Back");
        back.setBounds(400, 350, 100, 30);
        back.addActionListener(this);
        backgroundLabel.add(back);

        setSize(800, 600);
        setLocation(300, 100);
        setVisible(true);

        loadEmployeeIds();
    }

    private void loadEmployeeIds() {
        try {
            Conn c = new Conn();
            String query = "select empid from employee";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                cbEmpId.addItem(rs.getString("empid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetch) {
            String empId = (String) cbEmpId.getSelectedItem();
            try {
                Conn c = new Conn();
                String query = "select name, salary from employee where empid = '" + empId + "'";
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    tfName.setText(rs.getString("name"));
                    BigDecimal totalSalary = rs.getBigDecimal("salary");
                    tfTotal.setText(totalSalary.toString());

                    // Divide the total salary into basic, HRA, DA, and bonus
                    BigDecimal basic = totalSalary.multiply(new BigDecimal("0.50"));
                    BigDecimal hra = totalSalary.multiply(new BigDecimal("0.20"));
                    BigDecimal da = totalSalary.multiply(new BigDecimal("0.20"));
                    BigDecimal bonus = totalSalary.multiply(new BigDecimal("0.10"));

                    tfBasic.setText(basic.toString());
                    tfHRA.setText(hra.toString());
                    tfDA.setText(da.toString());
                    tfBonus.setText(bonus.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Employee not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == calculate) {
            BigDecimal basic = new BigDecimal(tfBasic.getText());
            BigDecimal hra = new BigDecimal(tfHRA.getText());
            BigDecimal da = new BigDecimal(tfDA.getText());
            BigDecimal bonus = new BigDecimal(tfBonus.getText());
            BigDecimal total = basic.add(hra).add(da).add(bonus);

            // Calculate tax (for example, 10% of total salary)
            BigDecimal tax = total.multiply(new BigDecimal("0.10"));
            tfTax.setText(tax.toString());

            BigDecimal netSalary = total.subtract(tax);
            tfTotal.setText(netSalary.toString());

            // Set income status color based on salary amount
            if (netSalary.compareTo(new BigDecimal("50000")) > 0) {
                incomeStatus.setText("High Income");
                incomeStatus.setForeground(Color.GREEN);
                incomeStatus.setOpaque(false); // Make the label non-opaque to remove background color
            } else if (netSalary.compareTo(new BigDecimal("30000")) < 0) {
                incomeStatus.setText("Low Income");
                incomeStatus.setForeground(Color.RED);
                incomeStatus.setOpaque(false); // Make the label non-opaque to remove background color
            } else {
                incomeStatus.setText("Average Income");
                incomeStatus.setForeground(Color.BLUE); // Change color to blue
                incomeStatus.setOpaque(false); // Make the label non-opaque to remove background color
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Salary();
    }
}