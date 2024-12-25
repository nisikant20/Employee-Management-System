import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class ViewEmployee extends JFrame implements ActionListener {
    JTable table;
    JButton search, print, update, back;
    Choice cemployeeid;

    public ViewEmployee() {
        getContentPane().setBackground(Color.white);
        setLayout(null);

        JLabel searchlbl = new JLabel("Search by Employee Id");
        searchlbl.setBounds(20, 20, 150, 20);
        add(searchlbl);

        cemployeeid = new Choice();
        cemployeeid.setBounds(180, 20, 150, 20);
        add(cemployeeid);

        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 100, 1180, 650); // Adjusted to fit the window
        add(sp);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from employee");
            while (rs.next()) {
                cemployeeid.add(rs.getString("empId"));
            }
            // Load all employee data into the table
            rs = c.s.executeQuery("select * from employee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        search = new JButton("Search");
        search.setBounds(20, 60, 100, 20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(140, 60, 100, 20);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(260, 60, 100, 20);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(380, 60, 100, 20);
        back.addActionListener(this);
        add(back);

        setSize(1200, 800); // Increased window size
        setLocation(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select * from employee where empId = '" + cemployeeid.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateEmployee(cemployeeid.getSelectedItem());
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}

class SearchResultFrame extends JFrame {
    JTable table;

    public SearchResultFrame(String empId) {
        setTitle("Search Results");
        setSize(1000, 700); // Increased window size
        setLocation(300, 200);
        getContentPane().setBackground(Color.white);
        setLayout(null);
 
        
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 0, 980, 670); // Adjusted to fit the window
        add(sp);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from employee where empId = '" + empId + "'");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}