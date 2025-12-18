package org.noteam.nextclient.utils;

import com.google.gson.*;
import org.json.JSONArray;
import org.noteam.nextclient.dto.OrderTable;
import org.noteam.nextclient.dto.ShipmentRequest;
import org.noteam.nextclient.models.*;
import org.noteam.nextclient.models.Driver;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SqlUtil {
    //get
    public static List<ShipmentRequest>  getShipmentsByComplete(boolean isComplete){
        List<ShipmentRequest> shipments = new ArrayList<ShipmentRequest>();
        List<Integer> ordersIds = new ArrayList<Integer>();
        String cityName=null;
        String countryName=null;
        HttpURLConnection connection = null;
        try {
            connection =ApiUtil.fetchApi(
                    "/api/shipments/complete/"+ isComplete,ApiUtil.RequestMethod.GET,null
            );
            if (connection.getResponseCode()!=200){
                System.out.println("Error getting shipments by complete"+ connection.getResponseCode());
                return Collections.emptyList();
            }
            String result =ApiUtil.readResponse(connection);

            JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
            for (int i=0;i<jsonArray.size();i++) {

                JsonObject shipmentObject = jsonArray.get(i).getAsJsonObject();
                int shipmentId = shipmentObject.get("shipment_id").getAsInt();
                //Driver driver = null;
                //Vehicle vehicle = null;
                //City city = null;
               // if(shipmentObject.has("driver") && !shipmentObject.get("driver").isJsonNull()){
                 //   JsonObject driverObject = shipmentObject.get("driver").getAsJsonObject();
                   // int driverId = driverObject.get("driver_id").getAsInt();
                    //String driverName = driverObject.get("name").getAsString();
                    /*
                    String image=driverObject.get("image").getAsString();
                    int age=driverObject.get("age").getAsInt();
                    String socialSecurityNumber=driverObject.get("social_security_number").getAsString();
                    String email=driverObject.get("email").getAsString();
                    String password=driverObject.get("password").getAsString();
                    boolean isBusy = driverObject.get("is_busy").getAsBoolean();
                    LocalDate createdAt = LocalDate.parse(driverObject.get("created_at").getAsString());
                    LocalDate updatedAt = LocalDate.parse(driverObject.get("updated_at").getAsString());


                    driver =new Driver(
                            driverId , driverName , image , age
                            ,socialSecurityNumber , email , password
                , isBusy
                    );}

                if (shipmentObject.has("vehicle") && !shipmentObject.get("vehicle").isJsonNull()){
                    JsonObject vehicleObject = shipmentObject.get("vehicle").getAsJsonObject();
                    int vehicleId = vehicleObject.get("vehicle_id").getAsInt();
                    int wight = vehicleObject.get("wight").getAsInt();
                    //String licensePlate = vehicleObject.get("license_plate").getAsString();
                    boolean isAvailable = vehicleObject.get("is_available").getAsBoolean();
                    boolean isUsed = vehicleObject.get("is_used").getAsBoolean();/*
                    vehicle =new Vehicle(
                           vehicleId , wight ,licensePlate,isAvailable,isUsed
                    );
                }*/
                if (shipmentObject.has("city") && !shipmentObject.get("city").isJsonNull()){
                    JsonObject cityObject = shipmentObject.get("city").getAsJsonObject();
                    //int cityId = cityObject.get("city_id").getAsInt();
                    cityName = cityObject.get("name").getAsString();
                   JsonObject CountryObject = cityObject.get("country").getAsJsonObject();
                   //int countryId = CountryObject.get("country_id").getAsInt();
                   countryName = CountryObject.get("name").getAsString();
                   /*Country country = new Country(
                           countryId,countryName
                   );
                   city =new City(
                           cityId , cityName , country
                   );*/
                }

                if (shipmentObject.has("orderList") && !shipmentObject.get("orderList").isJsonNull()){
                    JsonArray orderListArray = shipmentObject.get("orderList").getAsJsonArray();

                    for ( int x=0 ;x<orderListArray.size();x++){
                        JsonObject orderObject = orderListArray.get(i).getAsJsonObject();
                        int orderId = orderObject.get("order_id").getAsInt();
                        ordersIds.add(orderId);
                    }
                }
                //JsonObject adminObject = shipmentObject.get("admin").getAsJsonObject();
                //int adminId = adminObject.get("admin_id").getAsInt();
               // String admnImage = adminObject.get("image").getAsString();
               // String adminName = adminObject.get("name").getAsString();
                //String adminPassword = adminObject.get("password").getAsString();
               // String adminEmail = adminObject.get("email").getAsString();
               // String adminSocialSecurityNumber = adminObject.get("social_security_number").getAsString();
               // int adminAge = adminObject.get("age").getAsInt();
                //Admin admin = new Admin(
                    //    adminId,adminPassword,adminEmail,adminAge,adminSocialSecurityNumber,adminName, admnImage
                //);
                int totalWight = shipmentObject.get("total_wight").getAsInt();
                String shippingDate =shipmentObject.get("shipping_date").getAsString();
               // boolean isComplete = shipmentObject.get("is_complete").getAsBoolean();
                ShipmentRequest shipment = new ShipmentRequest(
                      shipmentId,totalWight,shippingDate,cityName,countryName
                );
                shipments.add(shipment);
                }
               return  shipments;
            }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection!=null){
                connection.disconnect();
            }
        }
        return Collections.emptyList();
    }
    public static List<OrderTable> getAllOrders() {
        List<OrderTable>orders= new ArrayList<>() ;
        HttpURLConnection connection = null;
        try {
            connection = ApiUtil.fetchApi(
                    "/api/v1/order/all", ApiUtil.RequestMethod.GET, null
            );
            if (connection.getResponseCode() != 200) {
                System.out.println("Error getting all orders"  + connection.getResponseCode());
                return Collections.emptyList();
            }
            String result =ApiUtil.readResponse(connection);
            JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
            for (int i=0;i<jsonArray.size();i++){
               JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
               int orderId = jsonObject.get("order_id").getAsInt();
               int orderWight = jsonObject.get("order_wight").getAsInt();
               int orderPrice = jsonObject.get("order_price").getAsInt();
                OrderTable orderTable = new OrderTable(orderId,orderPrice,orderWight);
                orders.add(orderTable);
            }
            return orders;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (connection!=null){
                connection.disconnect();
            }
        }
        return Collections.emptyList();
    }
    public static List<OrderTable> getShipmentOrders( int shipmentId) {
        List<OrderTable>orders= new ArrayList<>() ;
        HttpURLConnection connection = null;
        try {
            connection = ApiUtil.fetchApi(
                    "/api/shipments/"+shipmentId+"/orders", ApiUtil.RequestMethod.GET, null
            );
            if (connection.getResponseCode() != 200) {
                System.out.println("Error getting orders in shipment by id" + connection.getResponseCode());
                return Collections.emptyList();
            }
            String result =ApiUtil.readResponse(connection);
            JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
            for (int i=0;i<jsonArray.size();i++){
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                int orderId = jsonObject.get("order_id").getAsInt();
                int orderWight = jsonObject.get("order_wight").getAsInt();
                int orderPrice = jsonObject.get("order_price").getAsInt();
                OrderTable orderTable = new OrderTable(orderId,orderPrice,orderWight);
                orders.add(orderTable);
            }
            return orders;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (connection!=null){
                connection.disconnect();
            }
        }
        return Collections.emptyList();
    }
    //POST
    public static boolean createShipment(ShipmentRequest shipmentRequest) {
        HttpURLConnection connection = null;
        try {
            Gson gson = new Gson();
            JsonObject shipmentJson = gson.toJsonTree(shipmentRequest).getAsJsonObject();


            connection = ApiUtil.fetchApi(
                    "/api/shipments", ApiUtil.RequestMethod.POST, shipmentJson
            );
            if (connection.getResponseCode() != 200) {
                System.out.println("Error creating a shipment" + connection.getResponseCode());
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    //PUT
public static boolean updateShipment(ShipmentRequest shipmentRequest) {
    HttpURLConnection connection = null;
    try {
        Gson gson = new Gson();
        JsonObject shipmentJson = gson.toJsonTree(shipmentRequest).getAsJsonObject();


        connection = ApiUtil.fetchApi(
                "/api/shipments/update", ApiUtil.RequestMethod.PUT, shipmentJson
        );
        if (connection.getResponseCode() != 200) {
            System.out.println("Error updating shipment" + connection.getResponseCode());
            return false;
        }
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    } finally {
        if (connection != null) {
            connection.disconnect();
        }
    }
}


    public static boolean setShipmentAsCompleted(int shipmentId){
    HttpURLConnection connection = null;
    try {
        connection =ApiUtil.fetchApi(
                "/api/shipments/"+shipmentId,ApiUtil.RequestMethod.PATCH,null
        );
        if (connection.getResponseCode()!=200){
            System.out.println("Error manually set shipment as completed by id"+ connection.getResponseCode());
            return false;
        }
        return true;
    }
    catch (Exception e){
        e.printStackTrace();
        return false;
    }
    finally {
        if (connection!=null){
            connection.disconnect();
        }
    }

}
    // delete
public static boolean deleteShipment(int shipmentId){
    HttpURLConnection connection = null;
    try {
        connection =ApiUtil.fetchApi(
                "/api/shipments/"+shipmentId,ApiUtil.RequestMethod.DELETE,null
        );
        if (connection.getResponseCode()!=200){
            System.out.println("Error deleting shipment by id"+ connection.getResponseCode());
           return false;
        }
        return true;
    }
    catch (Exception e){
    e.printStackTrace();
    return false;
    }
    finally {
        if (connection!=null){
            connection.disconnect();
        }
    }

}

}
