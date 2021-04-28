package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HoofdschermGUI extends JFrame implements ActionListener {

    private JTextField loginMail;
    private JPasswordField loginWachtwoord;
    private JButton loginBevestig;
    private JLabel loginMelding;
    private Color mainColor = new Color(83, 70, 212);
    private Color secondColor = Color.WHITE;

    public HoofdschermGUI() {
        // Standaard waarden meegeven
        setTitle("NerdyGadgets - inlogpagina");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Aanmaken content
        this.loginMail = new JTextField();
        this.loginWachtwoord = new JPasswordField();
        this.loginBevestig = new JButton("Bevestig");
        JLabel loginTekst = new JLabel("Login");
        JLabel loginBeschrijving = new JLabel("NerdyGadgets - Inlogpagina");
        loginMelding = new JLabel("");


        // Afsluit knop
        Action exitAction = new AbstractAction("X") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        JButton closeButton = new JButton(exitAction);

        loginBevestig.addActionListener(this);


        // Opmaak van content
        loginTekst.setFont(loginTekst.getFont().deriveFont(30.0f));
        loginBeschrijving.setFont(loginBeschrijving.getFont().deriveFont(12.0f));
        loginMelding.setFont(loginMelding.getFont().deriveFont(12.0f));

        loginBevestig.setBackground(mainColor);
        closeButton.setBackground(mainColor);
        loginTekst.setForeground(mainColor);
        loginBeschrijving.setForeground(mainColor);
        loginMelding.setForeground(mainColor);
        getContentPane().setBackground(secondColor);
        closeButton.setForeground(secondColor);
        loginBevestig.setForeground(secondColor);



        // Doorzichtbare tekst in mailadres tekstveld
        loginMail.setForeground(Color.GRAY);
        loginMail.setText("Mailadres");
        loginMail.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (loginMail.getText().equals("Mailadres")) {
                    loginMail.setText("");
                    loginMail.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (loginMail.getText().isEmpty()) {
                    loginMail.setForeground(Color.GRAY);
                    loginMail.setText("Mailadres");
                }
            }
        });



        // Doorzichtbare tekst in wachtwoord tekstveld
        Checkbox passCheckBox = new Checkbox();
        passCheckBox.setState(true);

        if (passCheckBox.getState()) {
            loginWachtwoord.setText("Wachtwoord");
            loginWachtwoord.setEchoChar((char) 0);
            loginWachtwoord.setForeground(new Color(153, 153, 153));
        } else {
            loginWachtwoord.setEchoChar('*');
        }

        loginWachtwoord.addFocusListener(new FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passCheckBox.setState(false);
                String password = String.valueOf(loginWachtwoord.getPassword());

                if (password.equalsIgnoreCase("Wachtwoord")) {
                    loginWachtwoord.setText("");
                    loginWachtwoord.setForeground(Color.black);
                }
                loginWachtwoord.setEchoChar('*');

            }

            public void focusLost(java.awt.event.FocusEvent evt) {

                String password = String.valueOf(loginWachtwoord.getPassword());


                if (password.equalsIgnoreCase("Wachtwoord") || password.toLowerCase().equals("")) {
                    loginWachtwoord.setText("Wachtwoord");
                    loginWachtwoord.setEchoChar((char) 0);
                    loginWachtwoord.setForeground(new Color(153, 153, 153));
                }
            }
        });



        // Positionering van content
        loginBeschrijving.setBounds(20, 5, 200, 35);
        closeButton.setBounds(340, 10, 50, 20);
        loginTekst.setBounds(157, 100, 200, 35);
        loginMail.setBounds(100, 165, 200, 35);
        loginWachtwoord.setBounds(100, 215, 200, 35);
        loginBevestig.setBounds(100, 265, 200, 35);
        loginMelding.setBounds(100, 300, 300, 35);



        // Toevoegen van content
        add(loginBeschrijving);
        add(closeButton);
        add(loginTekst);
        add(loginMail);
        add(loginWachtwoord);
        add(loginBevestig);
        add(loginMelding);



        // Positionering van applicatie op scherm
        setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        final int[] posX = {0};
        final int[] posY = { 0 };

        this.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                posX[0] =e.getX();
                posY[0] =e.getY();
            }
        });

        this.addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent evt)
            {
                //sets frame position when mouse dragged
                setLocation (evt.getXOnScreen()- posX[0],evt.getYOnScreen()- posY[0]);

            }
        });


        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBevestig) {
            loginMelding.setText(DatabaseConnectie.inloggen(loginMail.getText(), String.valueOf(loginWachtwoord.getPassword())));
        }
    }
}



