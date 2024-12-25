import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class Home extends  JFrame implements ActionListener {
    JButton view, add, update , remove , salary , logout;
Home(){
    setLayout(null);

    ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/delete.png"));
    Image i2 = i1.getImage().getScaledInstance(1120, 630,Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    JLabel  image = new JLabel(i3);
    image.setBounds(0,0,1120,630);
    add(image);

    JLabel heading = new JLabel("Employee Management System");
    heading.setBounds(650,20,400,40);
    heading.setFont(new Font("TAHOMA",Font.BOLD,25));
    image.add(heading); 

add = new JButton("Add Employee");
    add.setBounds(650,140,150,40);
    add.addActionListener(this);
    image.add(add);

view = new JButton("View Employee");
    view.setBounds(820,80,150,40);
    view.addActionListener(this);
    image.add(view);

 update = new JButton("Update Employee");
    update.setBounds(650,80,150,40);
    update.addActionListener(this);
    image.add(update);

 remove = new JButton("Remove Employee");
    remove.setBounds(820,140,150,40);
    remove.addActionListener(this);
    image.add(remove);

 salary = new JButton("Calculate Salary");
    salary.setBounds(735,200,150,40);
    salary.addActionListener(this);
    image.add(salary);

    logout = new JButton("Logout");
    logout.setBounds(820, 260, 150, 40);
    logout.addActionListener(this);
    image.add(logout);

    setSize(1120,630);
    setLocation(250,100);
    setVisible(true);



}

public static void main(String[] args) {
    new Home();

}

@Override
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == add) {
        setVisible(false);
        new AddEmployee();
    } else if (ae.getSource() == view) {
        setVisible(false);
        new ViewEmployee();
    } else if (ae.getSource() == update) {
        setVisible(false);
        new ViewEmployee();
    } else if (ae.getSource() == remove) {
        setVisible(false);
        new RemoveEmployee();
    } else if (ae.getSource() == salary) {
        setVisible(false);
        new Salary();
    }
    else if (ae.getSource() == logout) {
        setVisible(false);
        new Login();
    }
}
}
