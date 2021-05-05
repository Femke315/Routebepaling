import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Accountpagina extends JPanel{
    private JLabel titel;
    private JLabel naam;
    private JTextField email; // om te veranderen?
    private JButton veranderemail;
    private JTextField telefoonnummer;
    private JButton veranderTel;
    private JLabel werknemersnummer;

    public Accountpagina(){
        setBackground(Color.white);
        this.setPreferredSize(new Dimension(400,380));

//        titel= new JLabel("Mijn account");
//        naam= new JLabel("");
//        email=new JTextField("Test123@nerdy.com");
//        veranderemail= new JButton("✏️");
//        telefoonnummer= new JTextField("0000000000");
//        veranderTel= new JButton("✏️");
//        werknemersnummer= new JLabel("1234567");
//
//        this.add(titel);
//        this.add(naam);
//        this.add(email);
//        this.add(veranderemail);
//        this.add(telefoonnummer);
//        this.add(veranderTel);
//        this.add(werknemersnummer);

        setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);// OOK HEEL BELANGRIJK
    }
}
