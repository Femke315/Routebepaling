//package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Orderline {
    private int orderlineID;
    private int orderID;
    private int stockitemID;
    private int quantity;

    public int getOrderID() {
        return orderID;
    }

    public int getStockitemID() {
        return stockitemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public Orderline(int orderID) {
        DatabaseConnectie.verbindingMaken();
        Connection connection = DatabaseConnectie.getConnection();

        this.orderID = orderID;

        //create query
        String query = "SELECT OrderLineID, StockItemID, Quantity FROM orderlines WHERE OrderID=?";

        //maak er een prepared statment + connectie
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderID);//parameter toevoegen in query

            //ontvangen data
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    this.orderlineID = rs.getInt("OrderLineID");
                    this.stockitemID = rs.getInt("StockItemID");
                    this.quantity = rs.getInt("Quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnectie.verbindingSluiten();
    }

    public int getOrderlineID() {
        return orderlineID;
    }

    public String printOrderline(Orderline orderline) {
        return "StockitemID: " + orderline.getStockitemID() + "   Quantity: " + orderline.getQuantity();
    }
}

