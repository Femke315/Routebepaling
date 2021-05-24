//package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HoofdschermGUI extends JFrame implements ActionListener {

    // Attributen voor basisscherm.
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
    private JButton menuRouteoverzicht;
    private JButton menuRouteAanmaken;
    private JButton menuAccountBekijken;
    private JButton menuUitloggen;

    // Attributen voor accountpagina
    private JButton accountMenu;

    // Attributen voor route-aanmaakpagina
    private JSpinner aanmaakAantalBezorguren;
    private JSpinner aanmaakAantalRoutes;
    private JButton aanmaakBevestig;
    private JButton aanmaakMenu;

    // Attributen voor routeoverzichtpagina
    private JButton overzichtInfo1;
    private JButton overzichtInfo2;
    private JButton overzichtInfo3;
    private JButton overzichtInfo4;
    private int overzichtRouteID1;
    private int overzichtRouteID2;
    private int overzichtRouteID3;
    private int overzichtRouteID4;
    private JButton overzichtMenu;
    JButton overzichtVolgendepagina;
    JButton overzichtVorigepagina;
    int overzichtHuidigePagina;


    public HoofdschermGUI() {

        aanmakenBasisScherm();

        aanmakenLoginPagina();

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
        titelTekst.setBounds(20, 3, 300, 35);
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
        menuRouteoverzicht = new JButton("Routeoverzicht");
        menuRouteAanmaken = new JButton("Nieuwe routes aanmaken");
        menuAccountBekijken = new JButton("Account bekijken");
        menuUitloggen = new JButton("Uitloggen");
        menuRouteAanmaken.addActionListener(this);
        menuUitloggen.addActionListener(this);
        menuRouteoverzicht.addActionListener(this);
        menuAccountBekijken.addActionListener(this);

        // Opmaak van content
        menuTekst.setFont(menuTekst.getFont().deriveFont(30.0f));

        menuRouteoverzicht.setBorder(BorderFactory.createEmptyBorder());
        menuRouteAanmaken.setBorder(BorderFactory.createEmptyBorder());
        menuAccountBekijken.setBorder(BorderFactory.createEmptyBorder());
        menuUitloggen.setBorder(BorderFactory.createEmptyBorder());

        menuTekst.setForeground(mainColor);
        menuRouteoverzicht.setBackground(mainColor);
        menuRouteoverzicht.setForeground(textColor);
        menuRouteAanmaken.setBackground(mainColor);
        menuRouteAanmaken.setForeground(textColor);
        menuAccountBekijken.setBackground(mainColor);
        menuAccountBekijken.setForeground(textColor);
        menuUitloggen.setBackground(mainColor);
        menuUitloggen.setForeground(textColor);


        // Positionering van content
        if (Distributiemedewerker.getFunctie().equals("Magazijn manager")) {
            menuTekst.setBounds(160, 95, 200, 35);
            menuRouteoverzicht.setBounds(100, 150, 200, 35);
            menuRouteAanmaken.setBounds(100, 200, 200, 35);
            menuAccountBekijken.setBounds(100, 250, 200, 35);
            menuUitloggen.setBounds(100, 300, 200, 35);
        } else {
            menuTekst.setBounds(160, 110, 200, 35);
            menuRouteoverzicht.setBounds(100, 165, 200, 35);
            menuAccountBekijken.setBounds(100, 215, 200, 35);
            menuUitloggen.setBounds(100, 265, 200, 35);
        }

        // Toevoegen van content
        add(menuTekst);
        add(menuRouteoverzicht);
        add(menuRouteAanmaken);
        add(menuAccountBekijken);
        add(menuUitloggen);
    }

    public void aanmakenAccountPagina(){
        // Aanmaken content
        titelTekst.setText("NerdyGadgets - Accountpagina");
        JLabel accountTekst = new JLabel("Accountpagina");
        JLabel accountNaam = new JLabel("Naam: " + Distributiemedewerker.getNaam());
        JLabel accountMail = new JLabel("Mail: " + Distributiemedewerker.getMail());
        JLabel accountTelefoonnummer = new JLabel("Telefoonnummer: " + Distributiemedewerker.getTelefoonnummer());
        JLabel accountWerknemersnummer = new JLabel("Werknemersnummer: " + Distributiemedewerker.getPersonID());
        accountMenu = new JButton("Menu");
        accountMenu.addActionListener(this);


        // Opmaak van content
        accountTekst.setFont(accountTekst.getFont().deriveFont(28.0f));
        accountMenu.setBorder(BorderFactory.createEmptyBorder());

        accountTekst.setForeground(mainColor);
        accountMenu.setBackground(mainColor);
        accountMenu.setForeground(textColor);
        accountNaam.setBackground(mainColor);
        accountNaam.setForeground(textColor);
        accountMail.setBackground(mainColor);
        accountMail.setForeground(textColor);
        accountTelefoonnummer.setBackground(mainColor);
        accountTelefoonnummer.setForeground(textColor);
        accountWerknemersnummer.setBackground(mainColor);
        accountWerknemersnummer.setForeground(textColor);

        // Positionering van content
        accountTekst.setBounds(100, 100, 350, 35);
        accountMenu.setBounds(100, 315, 200, 35);

        accountNaam.setBounds(100, 140, 300, 50);
        accountMail.setBounds(100, 175, 300, 50);
        accountTelefoonnummer.setBounds(100, 215, 300, 50);
        accountWerknemersnummer.setBounds(100, 255, 300, 50);

        // Toevoegen van content
        add(accountTekst);
        add(accountMenu);
        add(accountNaam);
        add(accountMail);
        add(accountTelefoonnummer);
        add(accountWerknemersnummer);
    }

    public void aanmakenRouteAanmaakPagina(){
        // Aanmaken content
        titelTekst.setText("NerdyGadgets - Nieuwe routes aanmaken");
        JLabel aanmaakTekst = new JLabel("Nieuwe routes aanmaken");
        JLabel aanmaakAantalBezorgurenTekst = new JLabel("Aantal bezorguren per route:");
        JLabel aanmaakAantalRoutesTekst = new JLabel("Aantal te maken routes:");
        aanmaakAantalBezorguren = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        aanmaakAantalRoutes = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        aanmaakBevestig = new JButton("Nieuwe routes aanmaken");
        aanmaakMenu = new JButton("Menu");
        aanmaakMenu.addActionListener(this);

        // Opmaak van content
        aanmaakTekst.setFont(aanmaakTekst.getFont().deriveFont(25.0f));
        aanmaakBevestig.setBorder(BorderFactory.createEmptyBorder());
        aanmaakMenu.setBorder(BorderFactory.createEmptyBorder());

        aanmaakTekst.setForeground(mainColor);
        aanmaakAantalBezorgurenTekst.setForeground(mainColor);
        aanmaakAantalRoutesTekst.setForeground(mainColor);
        aanmaakAantalBezorguren.setBackground(mainColor);
        aanmaakAantalBezorguren.setForeground(textColor);
        aanmaakAantalRoutes.setBackground(mainColor);
        aanmaakAantalRoutes.setForeground(textColor);
        aanmaakBevestig.setBackground(mainColor);
        aanmaakBevestig.setForeground(textColor);
        aanmaakMenu.setBackground(mainColor);
        aanmaakMenu.setForeground(textColor);

        // Positionering van content
        aanmaakTekst.setBounds(45, 100, 350, 35);
        aanmaakAantalBezorgurenTekst.setBounds(80, 165, 200, 35);
        aanmaakAantalBezorguren.setBounds(280, 165, 40, 35);
        aanmaakAantalRoutesTekst.setBounds(80, 215, 200, 35);
        aanmaakAantalRoutes.setBounds(280, 215, 40, 35);
        aanmaakBevestig.setBounds(100, 270, 200, 35);
        aanmaakMenu.setBounds(100, 320, 200, 35);

        // Toevoegen van content
        add(aanmaakTekst);
        add(aanmaakAantalBezorgurenTekst);
        add(aanmaakAantalBezorguren);
        add(aanmaakAantalRoutesTekst);
        add(aanmaakAantalRoutes);
        add(aanmaakBevestig);
        add(aanmaakMenu);
    }

    public void aanmakenRouteoverzichtPagina(){
        // Ophalen routes
        ArrayList<Route> routelijst = new ArrayList<Route>();

        if (Distributiemedewerker.getFunctie().equals("Magazijn manager") || Distributiemedewerker.getFunctie().equals("Magazijn sorteerder")) {
            routelijst.addAll(SQLqueries.getRoutes("Klaar voor sorteren"));
        }
        if (Distributiemedewerker.getFunctie().equals("Magazijn manager") || Distributiemedewerker.getFunctie().equals("Bezorger")) {
            routelijst.addAll(SQLqueries.getRoutes("Klaar voor bezorging"));
        }
        if (Distributiemedewerker.getFunctie().equals("Magazijn manager")) {
            routelijst.addAll(SQLqueries.getRoutes("Onderweg"));
        }

        // Aantal paginas berekenen
        int aantalPaginas = (int) Math.ceil(routelijst.size()/4.00);
        if (aantalPaginas<1){
            aantalPaginas = 1;
        }
        if (overzichtHuidigePagina==0) {
            overzichtHuidigePagina =1;
        }

        // Routetitels en gegevens invullen
        ArrayList<String> routeTitels = new ArrayList<String>();
        ArrayList<String> routeGegevens = new ArrayList<String>();

        for (int i=0; i<(aantalPaginas*4); i++) {
            if (i<routelijst.size()) {
                routeTitels.add("RouteID " + routelijst.get(i).getRouteID() + ": " + routelijst.get(i).getStatus());
                if (Distributiemedewerker.getFunctie().equals("Magazijn manager") || Distributiemedewerker.getFunctie().equals("Bezorger")) {
                    routeGegevens.add(routelijst.get(i).getProvincie() + " | " + routelijst.get(i).getReistijd() + " uur | "
                            + routelijst.get(i).getAantalPakketten() + " pakketjes | " + routelijst.get(i).getAfstand() + "km");
                } else {
                    routeGegevens.add(routelijst.get(i).getAantalPakketten() + " pakketjes");
                }
            } else {
                routeTitels.add("");
                routeGegevens.add("");
            }
        }

        if (overzichtHuidigePagina*4-4<routelijst.size()) {
            overzichtRouteID1 = routelijst.get(overzichtHuidigePagina*4-4).getRouteID();
        }
        if (overzichtHuidigePagina*4-3<routelijst.size()) {
            overzichtRouteID2 = routelijst.get(overzichtHuidigePagina*4-3).getRouteID();
        }
        if (overzichtHuidigePagina*4-2<routelijst.size()) {
            overzichtRouteID3 = routelijst.get(overzichtHuidigePagina*4-2).getRouteID();
        }
        if (overzichtHuidigePagina*4-1<routelijst.size()) {
            overzichtRouteID4 = routelijst.get(overzichtHuidigePagina*4-1).getRouteID();
        }


        // Aanmaken content
        titelTekst.setText("NerdyGadgets - Routeoverzicht (pagina "+ overzichtHuidigePagina + "/" + aantalPaginas + ")");
        JLabel overzichtTekst = new JLabel("Routeoverzicht "+ overzichtHuidigePagina + "/" + aantalPaginas);
        overzichtMenu = new JButton("Menu");
        overzichtMenu.addActionListener(this);

        JLabel routetitel1 = new JLabel(routeTitels.get(overzichtHuidigePagina*4-4));
        JLabel routegegevens1 = new JLabel(routeGegevens.get(overzichtHuidigePagina*4-4));
        overzichtInfo1 = new JButton("i");
        overzichtInfo1.addActionListener(this);

        JLabel routetitel2 = new JLabel(routeTitels.get(overzichtHuidigePagina*4-3));
        JLabel routegegevens2 = new JLabel(routeGegevens.get(overzichtHuidigePagina*4-3));
        overzichtInfo2 = new JButton("i");
        overzichtInfo2.addActionListener(this);

        JLabel routetitel3 = new JLabel(routeTitels.get(overzichtHuidigePagina*4-2));
        JLabel routegegevens3 = new JLabel(routeGegevens.get(overzichtHuidigePagina*4-2));
        overzichtInfo3 = new JButton("i");
        overzichtInfo3.addActionListener(this);

        JLabel routetitel4 = new JLabel(routeTitels.get(overzichtHuidigePagina*4-1));
        JLabel routegegevens4 = new JLabel(routeGegevens.get(overzichtHuidigePagina*4-1));
        overzichtInfo4 = new JButton("i");
        overzichtInfo4.addActionListener(this);


        // Opmaak van content
        overzichtTekst.setFont(overzichtTekst.getFont().deriveFont(30.0f));

        overzichtInfo1.setFont(overzichtTekst.getFont().deriveFont(18.0f));
        overzichtInfo2.setFont(overzichtTekst.getFont().deriveFont(18.0f));
        overzichtInfo3.setFont(overzichtTekst.getFont().deriveFont(18.0f));
        overzichtInfo4.setFont(overzichtTekst.getFont().deriveFont(18.0f));
        overzichtMenu.setBorder(BorderFactory.createEmptyBorder());

        overzichtTekst.setForeground(mainColor);
        overzichtMenu.setBackground(mainColor);
        overzichtMenu.setForeground(textColor);

        routetitel1.setBackground(mainColor);
        routetitel1.setForeground(textColor);
        routegegevens1.setBackground(mainColor);
        routegegevens1.setForeground(textColor);

        routetitel2.setBackground(mainColor);
        routetitel2.setForeground(textColor);
        routegegevens2.setBackground(mainColor);
        routegegevens2.setForeground(textColor);

        routetitel3.setBackground(mainColor);
        routetitel3.setForeground(textColor);
        routegegevens3.setBackground(mainColor);
        routegegevens3.setForeground(textColor);

        routetitel4.setBackground(mainColor);
        routetitel4.setForeground(textColor);
        routegegevens4.setBackground(mainColor);
        routegegevens4.setForeground(textColor);

        overzichtInfo1.setBackground(mainColor);
        overzichtInfo1.setForeground(textColor);
        overzichtInfo1.setBorder(BorderFactory.createEmptyBorder());

        overzichtInfo2.setBackground(mainColor);
        overzichtInfo2.setForeground(textColor);
        overzichtInfo2.setBorder(BorderFactory.createEmptyBorder());

        overzichtInfo3.setBackground(mainColor);
        overzichtInfo3.setForeground(textColor);
        overzichtInfo3.setBorder(BorderFactory.createEmptyBorder());overzichtInfo1.setBackground(mainColor);

        overzichtInfo4.setBackground(mainColor);
        overzichtInfo4.setForeground(textColor);
        overzichtInfo4.setBorder(BorderFactory.createEmptyBorder());

        // Positionering van content
        overzichtTekst.setBounds(40, 70, 400, 35);
        overzichtMenu.setBounds(40, 340, 200, 30);

        routetitel1.setBounds(40, 110, 300, 50);
        routegegevens1.setBounds(40, 130, 300, 50);

        routetitel2.setBounds(40, 160, 300, 50);
        routegegevens2.setBounds(40, 180, 300, 50);

        routetitel3.setBounds(40, 210, 300, 50);
        routegegevens3.setBounds(40, 230, 300, 50);

        routetitel4.setBounds(40, 260, 300, 50);
        routegegevens4.setBounds(40, 280, 300, 50);


        if (!routeTitels.get(overzichtHuidigePagina*4-4).equals("")) {
            overzichtInfo1.setBounds(330, 132, 30, 30);
        }
        if (!routeTitels.get(overzichtHuidigePagina*4-3).equals("")) {
            overzichtInfo2.setBounds(330, 182, 30, 30);
        }
        if (!routeTitels.get(overzichtHuidigePagina*4-2).equals("")) {
            overzichtInfo3.setBounds(330, 232, 30, 30);
        }
        if (!routeTitels.get(overzichtHuidigePagina*4-1).equals("")) {
            overzichtInfo4.setBounds(330, 282, 30, 30);
        }

        // Toevoegen van content
        add(overzichtTekst);
        add(overzichtMenu);

        add(routetitel1);
        add(routegegevens1);
        add(routetitel2);
        add(routegegevens2);
        add(routetitel3);
        add(routegegevens3);
        add(routetitel4);
        add(routegegevens4);


        add(overzichtInfo1);
        add(overzichtInfo2);
        add(overzichtInfo3);
        add(overzichtInfo4);

        // Pagina navigatie buttons
        overzichtVolgendepagina = new JButton(">");
        overzichtVorigepagina = new JButton("<");

        overzichtVolgendepagina.addActionListener(this);
        overzichtVorigepagina.addActionListener(this);

        overzichtVolgendepagina.setBackground(mainColor);
        overzichtVolgendepagina.setForeground(textColor);
        overzichtVolgendepagina.setBorder(BorderFactory.createEmptyBorder());

        overzichtVorigepagina.setBackground(mainColor);
        overzichtVorigepagina.setForeground(textColor);
        overzichtVorigepagina.setBorder(BorderFactory.createEmptyBorder());

        overzichtVolgendepagina.setBounds(330, 340, 30, 30);
        overzichtVorigepagina.setBounds(290, 340, 30, 30);

        if (overzichtHuidigePagina<aantalPaginas) {
            add(overzichtVolgendepagina);
        }
        if (overzichtHuidigePagina>1) {
            add(overzichtVorigepagina);
        }

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
        titelTekst.setBounds(20, 3, 300, 35);
        add(titelTekst);

        // Titelbalk
        JLabel titelbalk = new JLabel();
        titelbalk.setOpaque(true);
        titelbalk.setBackground(mainColor);
        titelbalk.setBounds(0,0,400,40);
        add(titelbalk);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBevestig) {
            String loginResultaat = DatabaseConnectie.inloggen(loginMail.getText(), String.valueOf(loginWachtwoord.getPassword()));

            if (loginResultaat.equals("Account is ingelogd")) {
                afrondenPagina();
                aanmakenMenuPagina();
                Distributiemedewerker.print();
            } else {
                loginMelding.setText(loginResultaat);
            }
        } else if (e.getSource() == menuRouteAanmaken) {
            afrondenPagina();
            aanmakenRouteAanmaakPagina();
        } else if (e.getSource() == menuUitloggen) {
            Distributiemedewerker.Uitloggen();
            overzichtHuidigePagina = 1;
            afrondenPagina();
            aanmakenLoginPagina();
        } else if (e.getSource() == aanmaakMenu || e.getSource() == overzichtMenu) {
            afrondenPagina();
            aanmakenMenuPagina();
        } else if (e.getSource() == menuRouteoverzicht) {
            afrondenPagina();
            aanmakenRouteoverzichtPagina();
        } else if (e.getSource() == overzichtVolgendepagina) {
            overzichtHuidigePagina = overzichtHuidigePagina + 1;
            afrondenPagina();
            aanmakenRouteoverzichtPagina();
        } else if (e.getSource() == overzichtVorigepagina) {
            overzichtHuidigePagina = overzichtHuidigePagina-1;
            afrondenPagina();
            aanmakenRouteoverzichtPagina();
        } else if (e.getSource() == menuAccountBekijken) {
            afrondenPagina();
            aanmakenAccountPagina();
        } else if (e.getSource() == accountMenu) {
            afrondenPagina();
            aanmakenMenuPagina();
        } else if (e.getSource() == overzichtInfo1 || e.getSource() == overzichtInfo2 || e.getSource() == overzichtInfo3 || e.getSource() == overzichtInfo4) {
            if (e.getSource() == overzichtInfo1) {
                System.out.println(overzichtRouteID1);
            } else if (e.getSource() == overzichtInfo2) {
                System.out.println(overzichtRouteID2);
            } else if (e.getSource() == overzichtInfo3) {
                System.out.println(overzichtRouteID3);
            } else {
                System.out.println(overzichtRouteID4);
            }
            //methode bezorgroutescherm(overzichtRouteID..)
        }
    }
}



