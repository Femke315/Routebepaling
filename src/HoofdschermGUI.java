//package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HoofdschermGUI extends JFrame implements ActionListener {

    // Attributen voor basisscherm
    private Color mainColor = new Color(35,35,47);
    private Color secondColor = new Color(115,115,191);
    private Color textColor = Color.white;
    private JLabel titelTekst = new JLabel("");

    // Attributen voor loginpagina
    private JTextField loginMail;
    private JPasswordField loginWachtwoord;
    private JButton loginBevestig;
    private JLabel loginMelding;

    // Attributen voor menupagina
    private JButton menuNieuweRoute;
    private JButton menuAccountBekijken;
    private JButton menuUitloggen;



    public HoofdschermGUI() {

        aanmakenBasisScherm();

//        aanmakenLoginPagina();

        accountPagina();
        setVisible(true);
    }


    public void aanmakenBasisScherm(){
        // Standaard waarden meegeven
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(secondColor);


        // Titeltekst
        titelTekst.setForeground(textColor);
        titelTekst.setFont(titelTekst.getFont().deriveFont(12.0f));
        titelTekst.setBounds(20, 3, 200, 35);
        add(titelTekst);

        // Titelbalk
        JLabel titelbalk = new JLabel();
        titelbalk.setOpaque(true);
        titelbalk.setBackground(mainColor);
        titelbalk.setBounds(0,0,400,40);
        add(titelbalk);



        // Afsluit knop
        Action exitAction = new AbstractAction("X") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        JButton closeButton = new JButton(exitAction);
        closeButton.setBackground(mainColor);
        closeButton.setForeground(textColor);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setBounds(350, 10, 50, 20);
        add(closeButton);


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
    }

    public void aanmakenLoginPagina() {
        setTitle("NerdyGadgets - inlogpagina");

        // Aanmaken content
        this.loginMail = new JTextField();
        this.loginWachtwoord = new JPasswordField();
        this.loginBevestig = new JButton("Bevestig");
        JLabel loginTekst = new JLabel("Login");
        titelTekst.setText("NerdyGadgets - Inlogpagina");
        loginMelding = new JLabel("");
        loginBevestig.addActionListener(this);


        // Opmaak van content
        loginTekst.setFont(loginTekst.getFont().deriveFont(30.0f));
        loginMelding.setFont(loginMelding.getFont().deriveFont(12.0f));

        loginMail.setBorder(BorderFactory.createEmptyBorder());
        loginWachtwoord.setBorder(BorderFactory.createEmptyBorder());
        loginBevestig.setBorder(BorderFactory.createEmptyBorder());

        loginBevestig.setBackground(mainColor);
        loginTekst.setForeground(mainColor);
        loginMelding.setForeground(mainColor);
        loginBevestig.setForeground(textColor);


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
        loginTekst.setBounds(157, 100, 200, 35);
        loginMail.setBounds(100, 165, 200, 35);
        loginWachtwoord.setBounds(100, 215, 200, 35);
        loginBevestig.setBounds(100, 265, 200, 35);
        loginMelding.setBounds(100, 300, 300, 35);


        // Toevoegen van content
        add(loginTekst);
        add(loginMail);
        add(loginWachtwoord);
        add(loginBevestig);
        add(loginMelding);
    }

    public void aanmakenMenuPagina(){
        // Aanmaken content
        titelTekst.setText("NerdyGadgets - Menupagina");
        JLabel menuTekst = new JLabel("Menu");
        menuNieuweRoute = new JButton("Nieuwe route aanmaken");
        menuAccountBekijken = new JButton("Account bekijken");
        menuUitloggen = new JButton("Uitloggen");

        // Opmaak van content
        menuTekst.setFont(menuTekst.getFont().deriveFont(30.0f));

        menuNieuweRoute.setBorder(BorderFactory.createEmptyBorder());
        menuAccountBekijken.setBorder(BorderFactory.createEmptyBorder());
        menuUitloggen.setBorder(BorderFactory.createEmptyBorder());

        menuTekst.setForeground(mainColor);
        menuNieuweRoute.setBackground(mainColor);
        menuNieuweRoute.setForeground(textColor);
        menuAccountBekijken.setBackground(mainColor);
        menuAccountBekijken.setForeground(textColor);
        menuUitloggen.setBackground(mainColor);
        menuUitloggen.setForeground(textColor);


        // Positionering van content
        menuTekst.setBounds(160, 100, 200, 35);
        menuNieuweRoute.setBounds(100, 165, 200, 35);
        menuAccountBekijken.setBounds(100, 215, 200, 35);
        menuUitloggen.setBounds(100, 265, 200, 35);

        // Toevoegen van content
        add(menuTekst);
        add(menuNieuweRoute);
        add(menuAccountBekijken);
        add(menuUitloggen);
    }

    public void afrondenPagina() {
        getContentPane().removeAll();
        repaint();

        // Afsluit knop
        Action exitAction = new AbstractAction("X") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        JButton closeButton = new JButton(exitAction);
        closeButton.setBackground(mainColor);
        closeButton.setForeground(textColor);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setBounds(350, 10, 50, 20);
        add(closeButton);

        // Titeltekst
        titelTekst.setForeground(textColor);
        titelTekst.setFont(titelTekst.getFont().deriveFont(12.0f));
        titelTekst.setBounds(20, 3, 200, 35);
        add(titelTekst);

        // Titelbalk
        JLabel titelbalk = new JLabel();
        titelbalk.setOpaque(true);
        titelbalk.setBackground(mainColor);
        titelbalk.setBounds(0,0,400,40);
        add(titelbalk);
    }

    public void accountPagina(){
        setTitle("NerdyGadgets - accountgegevens");
        JPanel accountPagina= new Accountpagina();
        add(accountPagina);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBevestig) {
            String loginResultaat = DatabaseConnectie.inloggen(loginMail.getText(), String.valueOf(loginWachtwoord.getPassword()));

            if (loginResultaat.equals("Account is ingelogd")) {
                afrondenPagina();
                aanmakenMenuPagina();
            } else {
                loginMelding.setText(loginResultaat);
            }
        }
    }
}



