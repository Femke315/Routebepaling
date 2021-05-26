import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
    private int orderID;
    private int routeID;
    private int klantID;
    private String status;
    private String postcode;
    private double longitude;
    private double latitude;
    private String plaats;
    private String adres;


    public Order(int orderID){
        this.orderID= orderID;

        //create statement/query
        String query = "SELECT OrderID, Status, KlantID, RouteID FROM orders WHERE OrderID =?";

        try (PreparedStatement stmt = DatabaseConnectie.getConnection().prepareStatement(query))
        {
            stmt.setInt(1, orderID);//parameter toevoegen in query

            //ontvangen data
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
//                    System.out.println("OrderID: " + rs.getInt("OrderID"));
                    this.klantID=rs.getInt("KlantID");
                    this.status=rs.getString("Status");
                    this.routeID=rs.getInt("RouteID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //---------------------------------------------------------------------------------------
        //create statement/query
        String stmtGetLocatie = "SELECT pe.postcode, pe.plaats, pe.adres, Latitude, Longitude FROM postcode po INNER JOIN people pe ON po.PostCode=pe.postcode WHERE pe.PersonID=?";

        try (PreparedStatement stmt = DatabaseConnectie.getConnection().prepareStatement(stmtGetLocatie))
        {
            stmt.setInt(1, klantID);//parameter toevoegen in query

            //ontvangen data
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    this.postcode=rs.getString("postcode");
                    this.longitude=rs.getDouble("Longitude");
                    this.latitude=rs.getDouble("Latitude");
                    this.plaats=rs.getString("plaats");
                    this.adres=rs.getString("adres");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ophalen van order gegevens is fout gegeaan: "+e.toString());
            e.printStackTrace();
        }

    }

    public Order(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
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

    public String getPostcode() {
        return postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public String getAdres() {
        return adres;
    }

    public int getKlantID() {
        return klantID;
    }
}