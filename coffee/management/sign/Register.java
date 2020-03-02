package coffee.management.sign;

import coffee.management.connection.DbAdapter;
import coffee.management.frame.Line;
import coffee.management.frame.TwoButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Register
{

    private static void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private Line userName = new Line("User Name: ",15);
    private Line firstName = new Line("First Name: ",15);
    private Line lastName = new Line("Last Name: ",15);
    private Line password = new Line(" Password: ", 15);
    private TwoButton button = new TwoButton("CONFIRM", "QUIT");

    private static DbAdapter dbAdapter;
    JFrame frame;


    public Register(DbAdapter adapter)
    {
        dbAdapter = adapter;
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Coffee Management Information System");
            frame.add(new Register.MenuPane());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setMinimumSize(new Dimension(500, 500));
            frame.setVisible(true);
            frame.setResizable(false);
            setActionListener();
        });
    }

    private static void close()
    {
        try{ dispose(); }
        catch(Exception e){}
    }

    private void setActionListener()
    {
        button.getFirstButton().addActionListener((ActionEvent e) -> {
            String id = userName.getText();
            String sname = firstName.getText();
            String ssex = lastName.getText();
            String sclass = password.getText();
            
            try
            {
                String sql = "select user_id from user where user_id = '" + id + "';";
                PreparedStatement ps = dbAdapter.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {
                    if(rs.getString(1) != null)
                        return;
                }
            }
            catch (Exception error) {error.printStackTrace();}
            
            try {
                Statement statement = dbAdapter.getConnection().createStatement();
                String sqlInsert = "INSERT INTO USER (USER_ID, FIRST_NAME, LAST_NAME, PASSWORD, PRIVILEGE) "+
                        "VALUES ('" + id + "', '" + sname + "', '" + ssex + "', '" + sclass +"', " + "2);";
                System.out.println(sqlInsert);
                statement.executeUpdate(sqlInsert);
                statement.close();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        button.getLastButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    private class MenuPane extends JPanel
    {
        public MenuPane()
        {
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.NORTH;

            this.add(new JLabel("<html><h1><strong><i>REGISTER</i></strong></h1><hr><h2></h2><h3></h3></html>"), gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;


            JPanel buttons = new JPanel(new GridBagLayout());

            buttons.add(userName, gbc);
            buttons.add(Box.createVerticalStrut(50));
            buttons.add(firstName, gbc);
            buttons.add(lastName, gbc);
            buttons.add(Box.createVerticalStrut(50));
            buttons.add(password, gbc);
            buttons.add(Box.createRigidArea(new Dimension(0, 150)));
            buttons.add(button, gbc);

            gbc.weighty = 1;
            add(buttons, gbc);
        }
    }
}
