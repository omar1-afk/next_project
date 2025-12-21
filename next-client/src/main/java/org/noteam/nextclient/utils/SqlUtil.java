package org.noteam.nextclient.utils;

import com.google.gson.*;
import org.noteam.nextclient.dto.OrderTable;
import org.noteam.nextclient.dto.shipment.ShipmentCreateDTO;
import org.noteam.nextclient.dto.shipment.ShipmentDisplayDTO;
import org.noteam.nextclient.dto.shipment.ShipmentUpdateDTO;
import org.noteam.nextclient.models.*;
import org.noteam.nextclient.models.Driver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlUtil {


    public class JsonFieldConstants {
        public static final String SHIPMENT_ID = "shipment_id";
        public static final String TOTAL_WEIGHT = "total_weight";
        public static final String SHIPPING_DATE = "shipping_date";
        public static final String IS_COMPLETE = "isComplete";
        public static final String ORDERS_LIST = "orderList";
        public static final String ORDER_ID = "id";
        public static final String ORDER_WEIGHT = "weight";
        public static final String ORDER_PRICE = "price";
        public static final String CITY  = "city";
        public static final String CITY_ID = "id";
        public static final String CITY_NAME = "name";
        public static final String COUNTRY  = "country";
        public static final String COUNTRY_ID = "id";
        public static final String COUNTRY_NAME = "name";
        public static final String VEHICLE= "vehicle";
        public static final String VEHICLE_ID = "vehicleId";
        public static final String DRIVER_ID = "driver_id";
        public static final String DRIVER_NAME = "name";
        public static final String DRIVER= "driver";
        public static final String ADMIN_ID = "admin_id";
        public static final String ADMIN= "admin";

    }
    //get
    public static List<DriverObj> getAvailableAndNotBusyDrivers(){
        List<DriverObj> drivers= new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            connection =ApiUtil.fetchApi(
                    "/api/v1/driver/all",ApiUtil.RequestMethod.GET,null
            );
            if (connection.getResponseCode()!=200){
                System.out.println("Error getting drivers"+ connection.getResponseCode());
                return Collections.emptyList();
            }
            String result =ApiUtil.readResponse(connection);

            JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
            for (int i=0;i<jsonArray.size();i++) {
                JsonObject driverObject = jsonArray.get(i).getAsJsonObject();
                int driverId = driverObject.get(JsonFieldConstants.DRIVER_ID).getAsInt();
                String driverName = driverObject.get(JsonFieldConstants.DRIVER_NAME).getAsString();
                DriverObj driver=new DriverObj(driverId,driverName);
                drivers.add(driver);
            }
                return drivers;
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
    public static List< Integer> getAvailableVehicles(){
        List<Integer> vehicles=new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            connection =ApiUtil.fetchApi(
                    "/api/v1/vehicles",ApiUtil.RequestMethod.GET,null
            );
            if (connection.getResponseCode()!=200){
                System.out.println("Error getting vehicles"+ connection.getResponseCode());
                return Collections.emptyList();
            }
            String result =ApiUtil.readResponse(connection);

            JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
            for (int i=0;i<jsonArray.size();i++) {
                JsonObject vehicleObject = jsonArray.get(i).getAsJsonObject();
                int vehicleId = vehicleObject.get(JsonFieldConstants.VEHICLE_ID).getAsInt();
               vehicles.add(vehicleId);
            }
            return vehicles;
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
    //public static List<City> getAllCities(){
      //  List<City> cities=new ArrayList<>();
        //return  cities;
    //}

    public static List<ShipmentDisplayDTO>  getShipmentsByComplete(boolean isComplete){
        List<ShipmentDisplayDTO> shipments = new ArrayList<>();
        List<Integer> ordersIds = new ArrayList<Integer>();
        HttpURLConnection connection = null;
        try {
            connection =ApiUtil.fetchApi(
                    "/api/v1/shipment/complete/"+ isComplete,ApiUtil.RequestMethod.GET,null
            );
            if (connection.getResponseCode()!=200){
                System.out.println("Error getting shipments by complete"+ connection.getResponseCode());
                return Collections.emptyList();
            }
            String result =ApiUtil.readResponse(connection);

            JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
            for (int i=0;i<jsonArray.size();i++) {

                JsonObject shipmentObject = jsonArray.get(i).getAsJsonObject();
                int shipmentId = shipmentObject.get(JsonFieldConstants.SHIPMENT_ID).getAsInt();
                  JsonObject driverObject = shipmentObject.get(JsonFieldConstants.DRIVER).getAsJsonObject();
                   int driverId = driverObject.get(JsonFieldConstants.DRIVER_ID).getAsInt();
                   String driverName = driverObject.get(JsonFieldConstants.DRIVER_NAME).getAsString();
                    JsonObject vehicleObject = shipmentObject.get(JsonFieldConstants.VEHICLE).getAsJsonObject();
                    int vehicleId = vehicleObject.get(JsonFieldConstants.VEHICLE_ID).getAsInt();

                    JsonObject cityObject = shipmentObject.get(JsonFieldConstants.CITY).getAsJsonObject();
                    int cityId = cityObject.get(JsonFieldConstants.CITY_ID).getAsInt();
                   String cityName = cityObject.get(JsonFieldConstants.CITY_NAME).getAsString();
                   JsonObject CountryObject = cityObject.get(JsonFieldConstants.COUNTRY).getAsJsonObject();
                   int countryId = CountryObject.get(JsonFieldConstants.COUNTRY_ID).getAsInt();

                String countryName = CountryObject.get(JsonFieldConstants.COUNTRY_NAME).getAsString();

                if (shipmentObject.has(JsonFieldConstants.ORDERS_LIST) && !shipmentObject.get(JsonFieldConstants.ORDERS_LIST).isJsonNull()){
                    JsonArray orderListArray = shipmentObject.get(JsonFieldConstants.ORDERS_LIST).getAsJsonArray();
                    for ( int x=0 ;x<orderListArray.size();x++){
                        JsonObject orderObject = orderListArray.get(x).getAsJsonObject();
                        int orderId = orderObject.get(JsonFieldConstants.ORDER_ID).getAsInt();
                        ordersIds.add(orderId);
                    }
                }
                JsonObject adminObject = shipmentObject.get(JsonFieldConstants.ADMIN).getAsJsonObject();
                int adminId = adminObject.get(JsonFieldConstants.ADMIN_ID).getAsInt();
                int totalWeight = shipmentObject.get(JsonFieldConstants.TOTAL_WEIGHT).getAsInt();
                String shippingDate =shipmentObject.get(JsonFieldConstants.SHIPPING_DATE).getAsString();
                Country country=new Country(countryId,countryName);
                City city=new City(cityId,cityName,country);
                DriverObj driver=new DriverObj(driverId,driverName);
                ShipmentDisplayDTO shipment = new ShipmentDisplayDTO(shipmentId,
                        totalWeight,
                        shippingDate,
                        cityName,
                        countryName,
                        isComplete  ,
                        cityId,
                        driverId,
                        vehicleId,city,driver
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

    public static List<ShipmentDisplayDTO> getShipmentById(int shipmentId){
        List<ShipmentDisplayDTO> shipments = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            connection = ApiUtil.fetchApi(
                    "/api/v1/shipment/"+shipmentId, ApiUtil.RequestMethod.GET, null
            );
            if (connection.getResponseCode() != 200) {
                System.out.println("Error getting  Shipment by id"  + connection.getResponseCode());
                return null;
            }
        String result =ApiUtil.readResponse(connection);
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        int totalWeight = jsonObject.get(JsonFieldConstants.TOTAL_WEIGHT).getAsInt();
        String shippingDate =jsonObject.get(JsonFieldConstants.SHIPPING_DATE).getAsString();
        boolean complete = jsonObject.get(JsonFieldConstants.IS_COMPLETE).getAsBoolean();
        JsonObject cityObject = jsonObject.get(JsonFieldConstants.CITY).getAsJsonObject();
        int cityId = cityObject.get(JsonFieldConstants.CITY_ID).getAsInt();
        String cityName = cityObject.get(JsonFieldConstants.CITY_NAME).getAsString();
        JsonObject CountryObject = cityObject.get(JsonFieldConstants.COUNTRY).getAsJsonObject();
        int countryId = CountryObject.get(JsonFieldConstants.COUNTRY_ID).getAsInt();
        String countryName = CountryObject.get(JsonFieldConstants.COUNTRY_NAME).getAsString();
        JsonObject driverObject = jsonObject.get(JsonFieldConstants.DRIVER).getAsJsonObject();
        int driverId = driverObject.get(JsonFieldConstants.DRIVER_ID).getAsInt();
        String driverName = driverObject.get(JsonFieldConstants.DRIVER_NAME).getAsString();
        JsonObject vehicleObject = jsonObject.get(JsonFieldConstants.VEHICLE).getAsJsonObject();
        int vehicleId = vehicleObject.get(JsonFieldConstants.VEHICLE_ID).getAsInt();


        Country country=new Country(countryId,countryName);
        City city=new City(cityId,cityName,country);
        DriverObj driver=new DriverObj(driverId,driverName);
        ShipmentDisplayDTO shipmentRequest = new ShipmentDisplayDTO
                (shipmentId,totalWeight,shippingDate,cityName,countryName, complete, cityId,
                        driverId,
                        vehicleId,city,driver);
        shipments.add(shipmentRequest);
        return shipments;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (connection!=null){
                connection.disconnect();
            }
        }
        return null;

    }

    private static List<ShipmentDisplayDTO> getShipmentsByCriteria(String endpoint) {
        List<ShipmentDisplayDTO> shipments = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            connection = ApiUtil.fetchApi(endpoint, ApiUtil.RequestMethod.GET, null);
            if (connection.getResponseCode() != 200) {
                return Collections.emptyList();
            }
            String result = ApiUtil.readResponse(connection);
            JsonArray jsonArray = JsonParser.parseString(result).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject shipmentObject = jsonArray.get(i).getAsJsonObject();
                ShipmentDisplayDTO shipment = extractShipmentFromJson(shipmentObject);
                if (shipment != null) {
                    shipments.add(shipment);
                }
            }
            return shipments;
        } catch (IOException e) {
            System.err.println("Error in getShipmentsByCriteria: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    private static ShipmentDisplayDTO extractShipmentFromJson(JsonObject shipmentObject) {
       try {
            int shipmentId = shipmentObject.get(JsonFieldConstants.SHIPMENT_ID).getAsInt();
            int totalWeight = shipmentObject.get(JsonFieldConstants.TOTAL_WEIGHT).getAsInt();
            String shippingDate = shipmentObject.get(JsonFieldConstants.SHIPPING_DATE).getAsString();
            boolean complete = shipmentObject.get(JsonFieldConstants.IS_COMPLETE).getAsBoolean();
            JsonObject cityObject = shipmentObject.get(JsonFieldConstants.CITY).getAsJsonObject();
            int cityId = cityObject.get(JsonFieldConstants.CITY_ID).getAsInt();
            String cityName = cityObject.get(JsonFieldConstants.CITY_NAME).getAsString();
            JsonObject countryObject = cityObject.get(JsonFieldConstants.COUNTRY).getAsJsonObject();
            int countryId = countryObject.get(JsonFieldConstants.COUNTRY_ID).getAsInt();
            String countryName = countryObject.get(JsonFieldConstants.COUNTRY_NAME).getAsString();
            JsonObject driverObject = shipmentObject.get(JsonFieldConstants.DRIVER).getAsJsonObject();
            int driverId = driverObject.get(JsonFieldConstants.DRIVER_ID).getAsInt();
            String driverName = driverObject.get(JsonFieldConstants.DRIVER_NAME).getAsString();
            JsonObject vehicleObject = shipmentObject.get(JsonFieldConstants.VEHICLE).getAsJsonObject();
            int vehicleId = vehicleObject.get(JsonFieldConstants.VEHICLE_ID).getAsInt();
            Country country=new Country(countryId,countryName);
            City city=new City(cityId,cityName,country);
            DriverObj driver=new DriverObj(driverId,driverName);
            return new ShipmentDisplayDTO(shipmentId, totalWeight, shippingDate,
                    cityName, countryName, complete ,cityId,
                    driverId,
                    vehicleId,city,driver);
        } catch (Exception e) {
            System.err.println("Error extracting shipment from JSON: " + e.getMessage());
            return null;
        }
    }
    public static List<ShipmentDisplayDTO> getShipmentsByCityId(int cityId) {
        return getShipmentsByCriteria("/api/v1/shipment/city/" + cityId);
    }

    public static List<ShipmentDisplayDTO> getShipmentsByVehicleId(int vehicleId) {
        return getShipmentsByCriteria("/api/v1/shipment/vehicle/" + vehicleId);
    }

    public static List<ShipmentDisplayDTO> getShipmentsByAdminID(int adminId) {
        return getShipmentsByCriteria("/api/v1/shipment/admin/" + adminId);
    }

    public static List<ShipmentDisplayDTO> getShipmentsByDriverId(int driverId) {
        return getShipmentsByCriteria("/api/v1/shipment/driver/" + driverId);
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
               int orderId = jsonObject.get(JsonFieldConstants.ORDER_ID).getAsInt();
               int orderWeight = jsonObject.get(JsonFieldConstants.ORDER_WEIGHT).getAsInt();
               int orderPrice = jsonObject.get(JsonFieldConstants.ORDER_PRICE).getAsInt();
                OrderTable orderTable = new OrderTable(orderId,orderPrice,orderWeight);
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
                    "/api/v1/shipment/"+shipmentId+"/orders", ApiUtil.RequestMethod.GET, null
            );
            if (connection.getResponseCode() != 200) {
                //System.out.println("Error getting orders in shipment by id" + connection.getResponseCode());
                return Collections.emptyList();
            }
            String result =ApiUtil.readResponse(connection);
            JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
            for (int i=0;i<jsonArray.size();i++){
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                int orderId = jsonObject.get(JsonFieldConstants.ORDER_ID).getAsInt();
                int orderWeight = jsonObject.get(JsonFieldConstants.ORDER_WEIGHT).getAsInt();
                int orderPrice = jsonObject.get(JsonFieldConstants.ORDER_PRICE).getAsInt();
                OrderTable orderTable = new OrderTable(orderId,orderPrice,orderWeight);
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
    public static boolean createShipment(ShipmentCreateDTO shipmentCreateDTO) {
        HttpURLConnection connection = null;
        try {
            Gson gson = new Gson();
            JsonObject json = new JsonObject();
            json.addProperty(JsonFieldConstants.VEHICLE_ID, shipmentCreateDTO.getVehicleId());
            json.addProperty(JsonFieldConstants.DRIVER_ID, shipmentCreateDTO.getDriverId());
            json.addProperty(JsonFieldConstants.CITY_ID, shipmentCreateDTO.getCityId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date= LocalDate.parse(shipmentCreateDTO.getShippingDate(), formatter);
            json.addProperty(JsonFieldConstants.SHIPPING_DATE, date.format(formatter));
            json.addProperty(JsonFieldConstants.ADMIN_ID, shipmentCreateDTO.getAdminId());
            json.addProperty(JsonFieldConstants.TOTAL_WEIGHT,shipmentCreateDTO.getTotalWeight());
            JsonArray ordersArray = new JsonArray();
            for (Integer orderId : shipmentCreateDTO.getOrderIds()) {
                ordersArray.add(orderId);
            }
            json.add("orderIds", ordersArray);

            connection = ApiUtil.fetchApi(
                    "/api/shipments", ApiUtil.RequestMethod.POST, json
            );
            if (connection.getResponseCode() != 200) {
               // System.out.println("Error creating a shipment" + connection.getResponseCode());
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
public static boolean updateShipment(ShipmentUpdateDTO updateDTO ) {
    HttpURLConnection connection = null;
    try {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty(JsonFieldConstants.VEHICLE_ID, updateDTO.getVehicleId());
        json.addProperty(JsonFieldConstants.DRIVER_ID, updateDTO.getDriverId());
        json.addProperty(JsonFieldConstants.CITY_ID,updateDTO.getCityId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date= LocalDate.parse(updateDTO.getShippingDate(), formatter);
        json.addProperty(JsonFieldConstants.SHIPPING_DATE, date.format(formatter));
        json.addProperty(JsonFieldConstants.TOTAL_WEIGHT,updateDTO.getTotalWeight());


        connection = ApiUtil.fetchApi(
                "/api/v1/shipment/update"+updateDTO.getShipmentId(), ApiUtil.RequestMethod.PUT, json
        );
        if (connection.getResponseCode() != 200) {
           // System.out.println("Error updating shipment" + connection.getResponseCode());
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
                "/api/v1/shipment/"+shipmentId,ApiUtil.RequestMethod.PATCH,null
        );
        if (connection.getResponseCode()!=200){
           // System.out.println("Error manually set shipment as completed by id"+ connection.getResponseCode());
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
                "/api/v1/shipment/"+shipmentId,ApiUtil.RequestMethod.DELETE,null
        );
        if (connection.getResponseCode()!=200){
           // System.out.println("Error deleting shipment by id"+ connection.getResponseCode());
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
