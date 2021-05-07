import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Accountpagina extends JPanel implements ActionListener{
    private static HoofdschermGUI frame;
    private String emailadres;
    private JLabel titel;
    private JLabel naam;
    private JTextField email; // om te veranderen?
    private JButton veranderemail;
    private JTextField telefoonnummer;
    private JButton veranderTel;
    private JLabel werknemersnummer;
    private JButton menu;
    private String personid;

    public Accountpagina(HoofdschermGUI frame, String ingelogdeEmail){
        this.frame=frame;
        emailadres=ingelogdeEmail;
        setBackground(Color.white);
        this.setPreferredSize(new Dimension(400,360));
        setLayout(new GridLayout(0,2));

        gegevensZoeken();

        titel= new JLabel("Mijn account");
//        naam= new JLabel("");
        email=new JTextField("Test123@nerdy.com");
        veranderemail= new JButton("✏️");
//        telefoonnummer= new JTextField("0000000000");
        veranderTel= new JButton("✏️");
        werknemersnummer= new JLabel("1234567");
        menu= new JButton("Menu");
        menu.addActionListener(this);

        add(titel);
        add(naam);
        add(email);
        add(veranderemail);
        add(telefoonnummer);
        add(veranderTel);
        add(werknemersnummer);
        add(menu);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menu){
            frame.setUndecorated();
            setVisible(false);
        }
    }

    private void gegevensZoeken(){
        try {
            //verbinding maken met de database
            Connection con =DatabaseConnectie.connectieDoorgeven();

            //Select statement voorbereiden
            PreparedStatement ophalenGegevensQuery = con.prepareStatement("SELECT PersonID, FullName, PhoneNumber FROM people WHERE EmailAddress=?");
            ophalenGegevensQuery.setString(1, emailadres);

            //gegevens uit de database halen
            ResultSet rs = ophalenGegevensQuery.executeQuery();

            //zet opgehaalde gegevens in Jlabels en variabele
            while(rs.next()) {
                personid = rs.getString("PersonID");
                naam= new JLabel(rs.getString("FullName"));
                telefoonnummer= new JTextField(rs.getString("PhoneNumber"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (Exception e){
            System.out.println(e.getCause());
        }

        DatabaseConnectie.verbindingSluiten();
    }

//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);// OOK HEEL BELANGRIJK
//    }
}
