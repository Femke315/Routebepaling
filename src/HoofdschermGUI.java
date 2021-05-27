//package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
    private JButton menuHuidigeRoute;

    // Attributen voor accountpagina
    private JButton accountMenu;

    // Attributen voor route-aanmaakpagina
    private JComboBox provincieList;
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
    private JButton overzichtVolgendepagina;
    private JButton overzichtVorigepagina;
    private int overzichtHuidigePagina;

    // Attributen voor bezorgrouteoverzichtpagina
    private JButton bezorgrouteoverzichtMenu;
    private JButton bezorgrouteoverzichtVerwijderroute;
    private JButton bezorgrouteoverzichtKlaarzetten;
    private JButton bezorgrouteoverzichtToeeigenen;
    private JButton bezorgrouteoverzichtAfrondeRoute;
    private JButton bezorgrouteoverzichtVolgendepagina;
    private JButton bezorgrouteoverzichtVorigepagina;
    private int bezorgrouteoverzichtHuidigePagina;
    private int bezorgrouteoverzichtRouteID;



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
        menuHuidigeRoute = new JButton("Huidige route");
        menuRouteAanmaken = new JButton("Nieuwe routes aanmaken");
        menuAccountBekijken = new JButton("Account bekijken");
        menuUitloggen = new JButton("Uitloggen");
        menuRouteAanmaken.addActionListener(this);
        menuUitloggen.addActionListener(this);
        menuRouteoverzicht.addActionListener(this);
        menuHuidigeRoute.addActionListener(this);
        menuAccountBekijken.addActionListener(this);

        // Opmaak van content
        menuTekst.setFont(menuTekst.getFont().deriveFont(30.0f));

        menuRouteoverzicht.setBorder(BorderFactory.createEmptyBorder());
        menuHuidigeRoute.setBorder(BorderFactory.createEmptyBorder());
        menuRouteAanmaken.setBorder(BorderFactory.createEmptyBorder());
        menuAccountBekijken.setBorder(BorderFactory.createEmptyBorder());
        menuUitloggen.setBorder(BorderFactory.createEmptyBorder());

        menuTekst.setForeground(mainColor);
        menuRouteoverzicht.setBackground(mainColor);
        menuRouteoverzicht.setForeground(textColor);
        menuHuidigeRoute.setBackground(mainColor);
        menuHuidigeRoute.setForeground(textColor);
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
        } else if (Distributiemedewerker.getRoute()!=null) {
            menuTekst.setBounds(160, 110, 200, 35);
            menuHuidigeRoute.setBounds(100, 165, 200, 35);
            menuAccountBekijken.setBounds(100, 215, 200, 35);
            menuUitloggen.setBounds(100, 265, 200, 35);
        } else {
            menuTekst.setBounds(160, 110, 200, 35);
            menuRouteoverzicht.setBounds(100, 165, 200, 35);
            menuAccountBekijken.setBounds(100, 215, 200, 35);
            menuUitloggen.setBounds(100, 265, 200, 35);
        }

        // Toevoegen van content
        if (Distributiemedewerker.getFunctie().equals("Magazijn manager")) {
            add(menuTekst);
            add(menuRouteoverzicht);
            add(menuRouteAanmaken);
            add(menuAccountBekijken);
            add(menuUitloggen);
        } else if (Distributiemedewerker.getRoute()!=null) {
            add(menuTekst);
            add(menuHuidigeRoute);
            add(menuAccountBekijken);
            add(menuUitloggen);
        } else {
            add(menuTekst);
            add(menuRouteoverzicht);
            add(menuAccountBekijken);
            add(menuUitloggen);
        }
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
        String[] provincieString = new String[12];
        provincieString[0] = "Groningen";
        provincieString[1] = "Friesland";
        provincieString[2] = "Drenthe";
        provincieString[3] = "Overijssel";
        provincieString[4] = "Flevoland";
        provincieString[5] = "Gelderland";
        provincieString[6] = "Utrecht";
        provincieString[7] = "Noord-Holland";
        provincieString[8] = "Zuid-Holland";
        provincieString[9] = "Zeeland";
        provincieString[10] = "Noord-Brabant";
        provincieString[11] = "Limburg";

        // Aanmaken content
        titelTekst.setText("NerdyGadgets - Nieuwe routes aanmaken");
        JLabel aanmaakTekst = new JLabel("Nieuwe routes aanmaken");
        JLabel provincieTekst = new JLabel("Provincie:");
        provincieList = new JComboBox(provincieString);
        provincieList.setSelectedIndex(11);

        aanmaakBevestig = new JButton("Nieuwe route aanmaken");
        aanmaakBevestig.addActionListener(this);
        aanmaakMenu = new JButton("Menu");
        aanmaakMenu.addActionListener(this);

        // Opmaak van content
        aanmaakTekst.setFont(aanmaakTekst.getFont().deriveFont(25.0f));
        aanmaakBevestig.setBorder(BorderFactory.createEmptyBorder());
        aanmaakMenu.setBorder(BorderFactory.createEmptyBorder());

        aanmaakTekst.setForeground(mainColor);
        provincieList.setForeground(textColor);
        provincieList.setBackground(mainColor);
        provincieTekst.setForeground(mainColor);
        provincieTekst.setBackground(textColor);
        aanmaakBevestig.setBackground(mainColor);
        aanmaakBevestig.setForeground(textColor);
        aanmaakMenu.setBackground(mainColor);
        aanmaakMenu.setForeground(textColor);

        // Positionering van content
        aanmaakTekst.setBounds(45, 100, 350, 35);
        provincieTekst.setBounds(100, 175, 200, 35);
        provincieList.setBounds(175, 175, 125, 35);
        aanmaakBevestig.setBounds(100, 270, 200, 35);
        aanmaakMenu.setBounds(100, 320, 200, 35);

        // Toevoegen van content
        add(aanmaakTekst);
        add(provincieTekst);
        add(provincieList);
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

    public void aanmakenBezorgrouteoverzichtPagina(int routeid){
        // Ophalen orders
        ArrayList<Order> orderlijst = SQLqueries.showRoute(routeid);
        bezorgrouteoverzichtRouteID = routeid;

        // Aantal paginas berekenen
        int aantalPaginas = (int) Math.ceil(orderlijst.size()/4.00);
        if (aantalPaginas<1){
            aantalPaginas = 1;
        }
        if (bezorgrouteoverzichtHuidigePagina==0) {
            bezorgrouteoverzichtHuidigePagina =1;
        }

        // Routetitels en gegevens invullen
        ArrayList<String> orderTitels = new ArrayList<String>();
        ArrayList<String> orderGegevens = new ArrayList<String>();

        for (int i=0; i<(aantalPaginas*4); i++) {
            if (i<orderlijst.size()) {
                if (Distributiemedewerker.getFunctie().equals("Magazijn manager") || Distributiemedewerker.getFunctie().equals("Bezorger")) {
                    orderTitels.add("OrderID " + orderlijst.get(i).getOrderID() + ": " + orderlijst.get(i).getPlaats());
                    orderGegevens.add(orderlijst.get(i).getAdres() + " " + orderlijst.get(i).getPostcode());
                } else {
                    orderTitels.add("OrderID " + orderlijst.get(i).getOrderID());
                    orderGegevens.add(orderlijst.get(i).getAdres() + " " + orderlijst.get(i).getPostcode());
                }
            } else {
                orderTitels.add("");
                orderGegevens.add("");
            }
        }

        // Aanmaken content
        titelTekst.setText("NerdyGadgets - Bezorgrouteoverzicht: route " + routeid);
        JLabel bezorgrouteoverzichtTekst = new JLabel("Bezorgroute "+ bezorgrouteoverzichtHuidigePagina + "/" + aantalPaginas);
        bezorgrouteoverzichtMenu = new JButton("Menu");
        bezorgrouteoverzichtVerwijderroute = new JButton("Verwijder route");
        bezorgrouteoverzichtKlaarzetten= new JButton("Route is gesorteerd");
        bezorgrouteoverzichtToeeigenen = new JButton("Route toe eigenen");
        bezorgrouteoverzichtAfrondeRoute = new JButton("Route afronden");
        bezorgrouteoverzichtMenu.addActionListener(this);
        bezorgrouteoverzichtVerwijderroute.addActionListener(this);
        bezorgrouteoverzichtKlaarzetten.addActionListener(this);
        bezorgrouteoverzichtToeeigenen.addActionListener(this);
        bezorgrouteoverzichtAfrondeRoute.addActionListener(this);

        JLabel bezorgroutetitel1 = new JLabel(orderTitels.get(bezorgrouteoverzichtHuidigePagina*4-4));
        JLabel bezorgroutegegevens1 = new JLabel(orderGegevens.get(bezorgrouteoverzichtHuidigePagina*4-4));

        JLabel bezorgroutetitel2 = new JLabel(orderTitels.get(bezorgrouteoverzichtHuidigePagina*4-3));
        JLabel bezorgroutegegevens2 = new JLabel(orderGegevens.get(bezorgrouteoverzichtHuidigePagina*4-3));

        JLabel bezorgroutetitel3 = new JLabel(orderTitels.get(bezorgrouteoverzichtHuidigePagina*4-2));
        JLabel bezorgroutegegevens3 = new JLabel(orderGegevens.get(bezorgrouteoverzichtHuidigePagina*4-2));

        JLabel bezorgroutetitel4 = new JLabel(orderTitels.get(bezorgrouteoverzichtHuidigePagina*4-1));
        JLabel bezorgroutegegevens4 = new JLabel(orderGegevens.get(bezorgrouteoverzichtHuidigePagina*4-1));

        // Opmaak van content
        bezorgrouteoverzichtTekst.setFont(bezorgrouteoverzichtTekst.getFont().deriveFont(30.0f));
        bezorgrouteoverzichtMenu.setBorder(BorderFactory.createEmptyBorder());
        bezorgrouteoverzichtVerwijderroute.setBorder(BorderFactory.createEmptyBorder());
        bezorgrouteoverzichtKlaarzetten.setBorder(BorderFactory.createEmptyBorder());
        bezorgrouteoverzichtToeeigenen.setBorder(BorderFactory.createEmptyBorder());
        bezorgrouteoverzichtAfrondeRoute.setBorder(BorderFactory.createEmptyBorder());

        bezorgrouteoverzichtTekst.setForeground(mainColor);
        bezorgrouteoverzichtMenu.setBackground(mainColor);
        bezorgrouteoverzichtVerwijderroute.setBackground(mainColor);
        bezorgrouteoverzichtKlaarzetten.setBackground(mainColor);
        bezorgrouteoverzichtToeeigenen.setBackground(mainColor);
        bezorgrouteoverzichtAfrondeRoute.setBackground(mainColor);
        bezorgrouteoverzichtMenu.setForeground(textColor);
        bezorgrouteoverzichtVerwijderroute.setForeground(textColor);
        bezorgrouteoverzichtKlaarzetten.setForeground(textColor);
        bezorgrouteoverzichtToeeigenen.setForeground(textColor);
        bezorgrouteoverzichtAfrondeRoute.setForeground(textColor);


        bezorgroutetitel1.setBackground(mainColor);
        bezorgroutetitel1.setForeground(textColor);
        bezorgroutegegevens1.setBackground(mainColor);
        bezorgroutegegevens1.setForeground(textColor);

        bezorgroutetitel2.setBackground(mainColor);
        bezorgroutetitel2.setForeground(textColor);
        bezorgroutegegevens2.setBackground(mainColor);
        bezorgroutegegevens2.setForeground(textColor);

        bezorgroutetitel3.setBackground(mainColor);
        bezorgroutetitel3.setForeground(textColor);
        bezorgroutegegevens3.setBackground(mainColor);
        bezorgroutegegevens3.setForeground(textColor);

        bezorgroutetitel4.setBackground(mainColor);
        bezorgroutetitel4.setForeground(textColor);
        bezorgroutegegevens4.setBackground(mainColor);
        bezorgroutegegevens4.setForeground(textColor);

        // Positionering van content
        bezorgrouteoverzichtTekst.setBounds(40, 70, 400, 35);
        bezorgrouteoverzichtMenu.setBounds(40, 340, 200, 30);
        bezorgrouteoverzichtVerwijderroute.setBounds(40, 340, 200, 30);
        bezorgrouteoverzichtKlaarzetten.setBounds(40, 340, 200, 30);
        bezorgrouteoverzichtToeeigenen.setBounds(40, 340, 200, 30);
        bezorgrouteoverzichtAfrondeRoute.setBounds(40, 340, 200, 30);

        bezorgroutetitel1.setBounds(40, 110, 300, 50);
        bezorgroutegegevens1.setBounds(40, 130, 300, 50);

        bezorgroutetitel2.setBounds(40, 160, 300, 50);
        bezorgroutegegevens2.setBounds(40, 180, 300, 50);

        bezorgroutetitel3.setBounds(40, 210, 300, 50);
        bezorgroutegegevens3.setBounds(40, 230, 300, 50);

        bezorgroutetitel4.setBounds(40, 260, 300, 50);
        bezorgroutegegevens4.setBounds(40, 280, 300, 50);

        // Toevoegen van content
        add(bezorgrouteoverzichtTekst);
        add(bezorgrouteoverzichtMenu);

        add(bezorgroutetitel1);
        add(bezorgroutegegevens1);
        add(bezorgroutetitel2);
        add(bezorgroutegegevens2);
        add(bezorgroutetitel3);
        add(bezorgroutegegevens3);
        add(bezorgroutetitel4);
        add(bezorgroutegegevens4);

        // Pagina navigatie buttons
        bezorgrouteoverzichtVolgendepagina = new JButton(">");
        bezorgrouteoverzichtVorigepagina = new JButton("<");

        bezorgrouteoverzichtVolgendepagina.addActionListener(this);
        bezorgrouteoverzichtVorigepagina.addActionListener(this);

        bezorgrouteoverzichtVolgendepagina.setBackground(mainColor);
        bezorgrouteoverzichtVolgendepagina.setForeground(textColor);
        bezorgrouteoverzichtVolgendepagina.setBorder(BorderFactory.createEmptyBorder());

        bezorgrouteoverzichtVorigepagina.setBackground(mainColor);
        bezorgrouteoverzichtVorigepagina.setForeground(textColor);
        bezorgrouteoverzichtVorigepagina.setBorder(BorderFactory.createEmptyBorder());

        bezorgrouteoverzichtVolgendepagina.setBounds(330, 340, 30, 30);
        bezorgrouteoverzichtVorigepagina.setBounds(290, 340, 30, 30);

        if (bezorgrouteoverzichtHuidigePagina<aantalPaginas) {
            add(bezorgrouteoverzichtVolgendepagina);
        }
        if (bezorgrouteoverzichtHuidigePagina>1) {
            add(bezorgrouteoverzichtVorigepagina);
        }

        // Route verwijderen, klaarzetten of toeeigenen buttons
        if (bezorgrouteoverzichtHuidigePagina==aantalPaginas) {
            remove(bezorgrouteoverzichtMenu);
            if (Distributiemedewerker.getFunctie().equals("Magazijn manager")) {
                add(bezorgrouteoverzichtVerwijderroute);
            } else if (Distributiemedewerker.getFunctie().equals("Magazijn sorteerder")) {
                add(bezorgrouteoverzichtKlaarzetten);
            } else if (Distributiemedewerker.getFunctie().equals("Bezorger")) {
                if (Distributiemedewerker.getRoute()!=null) {
                    add(bezorgrouteoverzichtAfrondeRoute);
                } else {
                    add(bezorgrouteoverzichtToeeigenen);
                }
            }
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
        } else if (e.getSource() == aanmaakBevestig){
            Route route = new Route(provincieList.getSelectedItem().toString());

            if (route.getAantalPakketten() == 0) {
                JFrame f = new JFrame();

                JOptionPane.showMessageDialog(f,
                        "ERROR: alle orders in de provincie " + provincieList.getSelectedItem().toString() + " zijn verwerkt!");
            } else {

                SQLqueries.toevoegenRoute(route);

                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,
                        "Route in provincie " + provincieList.getSelectedItem().toString() +  " is toegevoegd!"
                        + "\n" + "Aantal pakketjes: " + route.getAantalPakketten()
                        + "\n" + "Totale afstand (in km) = " + route.getAfstand());
            }

        }   else if (e.getSource() == menuUitloggen) {
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
        } else if (e.getSource() == menuHuidigeRoute){
            afrondenPagina();
            aanmakenBezorgrouteoverzichtPagina(Distributiemedewerker.getRoute().getRouteID());
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
                bezorgrouteoverzichtRouteID = overzichtRouteID1;
            } else if (e.getSource() == overzichtInfo2) {
                bezorgrouteoverzichtRouteID = overzichtRouteID2;
            } else if (e.getSource() == overzichtInfo3) {
                bezorgrouteoverzichtRouteID = overzichtRouteID3;
            } else {
                bezorgrouteoverzichtRouteID = overzichtRouteID4;
            }
            afrondenPagina();
            aanmakenBezorgrouteoverzichtPagina(bezorgrouteoverzichtRouteID);
        } else if (e.getSource() == bezorgrouteoverzichtMenu) {
            afrondenPagina();
            if (Distributiemedewerker.getRoute()!=null) {
                aanmakenMenuPagina();
            } else {
                aanmakenRouteoverzichtPagina();
            }
        } else if (e.getSource() == bezorgrouteoverzichtVolgendepagina) {
            bezorgrouteoverzichtHuidigePagina = bezorgrouteoverzichtHuidigePagina + 1;
            afrondenPagina();
            aanmakenBezorgrouteoverzichtPagina(bezorgrouteoverzichtRouteID);
        } else if (e.getSource() == bezorgrouteoverzichtVorigepagina) {
            bezorgrouteoverzichtHuidigePagina = bezorgrouteoverzichtHuidigePagina - 1;
            afrondenPagina();
            aanmakenBezorgrouteoverzichtPagina(bezorgrouteoverzichtRouteID);
        } else if (e.getSource() == bezorgrouteoverzichtVerwijderroute) {
            // verwijder methode
        } else if (e.getSource() == bezorgrouteoverzichtKlaarzetten) {
            System.out.println(SQLqueries.statusSorterenNaarBezorging(bezorgrouteoverzichtRouteID));
            afrondenPagina();
            aanmakenMenuPagina();
        } else if (e.getSource() == bezorgrouteoverzichtToeeigenen) {
            System.out.println(SQLqueries.statusBezorgingNaarOnderweg(bezorgrouteoverzichtRouteID, Distributiemedewerker.getPersonID()));
            Distributiemedewerker.setRoute(SQLqueries.gekozenRouteOphalen(Distributiemedewerker.getPersonID()));
            afrondenPagina();
            aanmakenMenuPagina();
        } else if (e.getSource() == bezorgrouteoverzichtAfrondeRoute) {
            System.out.println(SQLqueries.routeAfronden(Distributiemedewerker.getRoute()));
            Distributiemedewerker.setRoute(SQLqueries.gekozenRouteOphalen(Distributiemedewerker.getPersonID()));
            afrondenPagina();
            aanmakenMenuPagina();
        }
    }
}



