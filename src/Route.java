
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class Route {
    private int routeID;
    private int personID;
    private double reistijd;
    private String provincie;
    private String status;
    private String opmerkingen;

    //we moeten nog bepalen waar een route wordt gemaakt in de database en in object
    public Route(int routeID, String provincie, String status, double reisTijd, String opmerkingen) {
        this.routeID=routeID;
        this.provincie=provincie;
        this.status=status;
        this.reistijd=reisTijd;
        this.opmerkingen=opmerkingen;
    }

    public int getRouteID() {
        return routeID;
    }

    public int getPersonID() {
        return personID;
    }

    public double getReistijd() {
        return reistijd;
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




