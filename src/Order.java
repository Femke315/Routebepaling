
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
    private int orderID;
    private int routeID;
    private int klantID;
    private String status;
    public String postcode;
    private double longitude;
    private double latitude;


    public Order(int orderID){
        DatabaseConnectie.verbindingMaken();
        this.orderID= orderID;

        //create statement/query
        String query = "SELECT OrderID, Status, KlantID, RouteID FROM orders WHERE OrderID =?";

        try (PreparedStatement stmt = DatabaseConnectie.getConnection().prepareStatement(query))
        {
            stmt.setInt(1, orderID);//parameter toevoegen in query

            //ontvangen data
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    System.out.println("OrderID: " + rs.getInt("OrderID"));
                    this.klantID=rs.getInt("KlantID");
                    this.status=rs.getString("Status");
                    this.routeID=rs.getInt("RouteID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //----------------------------------------------------------------------------
        //create statement/query
        String stmtGetLocatie = "SELECT po.PostCodePK, Latitude, Longitude FROM postcode po INNER JOIN people pe ON po.PostCodePK=pe.postcode WHERE pe.PersonID=?";

        try (PreparedStatement stmt = DatabaseConnectie.getConnection().prepareStatement(stmtGetLocatie))
        {
            stmt.setInt(1, klantID);//parameter toevoegen in query

            //ontvangen data
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    this.postcode=rs.getString("postcode");
                    this.longitude=rs.getInt("Longitude");
                    this.latitude=rs.getInt("Latitude");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectie.verbindingSluiten();
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getStatus() {
        return status;
    }
}
//=======
//package src;
//import java.util.ArrayList;
//
//public class Order {
//
//        public double x;
//        public double y;
//
//        public int orderID;
//        public int routeID;
//
//
//    }
//
//
//
//
//
//
//
//
//>>>>>>> origin/Distributiemedewerker_&_Route
