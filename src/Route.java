package src;

public class Route {
    private int routeID;
    private int personID;
    private int aantalPakketten;
    private int reistijd;
    private int afstand;
    private String provincie;
    private String status;
    private String opmerkingen;


    // Constructor met alle gegevens
    public Route(int routeID, int personID, int aantalPakketten, int reistijd, int afstand, String provincie, String status, String opmerkingen){
        this.routeID = routeID;
        this.personID = personID;
        this.aantalPakketten = aantalPakketten;
        this.reistijd = reistijd;
        this.afstand = afstand;
        this.provincie = provincie;
        this.status = status;
        this.opmerkingen = opmerkingen;
    }

    public int getRouteID() {
        return routeID;
    }

    public int getPersonID() {
        return personID;
    }

    public int getAantalPakketten() {
        return aantalPakketten;
    }

    public int getReistijd() {
        return reistijd;
    }

    public int getAfstand() {
        return afstand;
    }

    public String getProvincie() {
        return provincie;
    }

    public String getStatus() {
        return status;
    }

    public String getOpmerkingen() {
        return opmerkingen;
    }
}
