package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Route {
    private int routeID;
    private int personID;
    private int aantalPakketten;
    private double reistijd;
    private int afstand;
    private String provincie;
    private String status;
    private String opmerkingen;

    //we moeten nog bepalen waar een route wordt gemaakt in de database en in object
    public Route(int routeID, String provincie, String status, int aantalPakketten, double reisTijd, int afstand, String opmerkingen) {
        this.routeID=routeID;
        this.provincie=provincie;
        this.aantalPakketten=aantalPakketten;
        this.status=status;
        this.reistijd=reisTijd;
        this.afstand=afstand;
        this.opmerkingen=opmerkingen;
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

    public double getReistijd() {
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
