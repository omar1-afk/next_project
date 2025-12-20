package org.noteam.nextclient.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.noteam.nextclient.dto.Order;
import org.noteam.nextclient.dto.State;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderController implements Initializable {
    @FXML
    private BorderPane orderPane;
    @FXML
    private Button syncOrderBtn;
    @FXML
    private Button createOrderBtn;
    @FXML
    private SVGPath cerateOrderIcon;
    @FXML
    private TextField searchFeild;
    @FXML
    private AnchorPane allPane;
    @FXML
    private AnchorPane pickedPane;
    @FXML
    private AnchorPane packagingPane;
    @FXML
    private AnchorPane shippingPane;
    @FXML
    private AnchorPane deliveredPane;
    @FXML
    private AnchorPane returnedPane;
    @FXML
    private TableView<OrderRow> orderTable;
    @FXML
    private TableColumn<OrderRow,Integer> orderIdCol;
    @FXML
    private TableColumn<OrderRow,Double> weightCol;
    @FXML
    private TableColumn<OrderRow,String> cityCol;
    @FXML
    private TableColumn<OrderRow,String> statusCol;
    @FXML
    private TableColumn<OrderRow,Double> priceCol;
    @FXML
    private TableColumn<OrderRow,String> shipmentCol;
    @FXML
    private TableColumn<OrderRow,String> createdAtCol;
    private ObservableList<OrderRow> observableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Order> orderList = Arrays.asList(
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Alexanderia","s","a",true,false,50,State.PICKED,30, LocalDate.now(),0,4,5,4,LocalDateTime.now(),LocalDate.now()),
                new Order(1,"Egypt","Cairo","s","a",true,false,50,State.PICKED,30, LocalDate.now(),1,2,3,5,LocalDateTime.now(),LocalDate.now())
        );
        setOrderTableView(orderList);
    }
    public void setOrderTableView(List<Order> orderList){
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("Weight"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        shipmentCol.setCellValueFactory(new PropertyValueFactory<>("Shipment"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("CreatedAt"));
        observableList = FXCollections.observableArrayList();
        orderList.forEach(order->{
            if(order.shipment()==0){
            observableList.add(new OrderRow(order.id(),order.weight(),order.city(),order.state(),order.price(),order.createdAt())) ;
            }
            else {
            observableList.add(new OrderRow(order.id(),order.weight(),order.city(),order.state(),order.price(),order.createdAt(),order.shipment())) ;

            }
                }
                );
        orderTable.setItems(observableList);
        System.out.println(orderTable.getItems());
    }

    @FXML
    public void onCreateOrderBtnMouseEnter(){
        createOrderBtn.setTextFill(Color.rgb(217,217,217));
        cerateOrderIcon.setStroke(Color.rgb(217,217,217));
        cerateOrderIcon.setFill(Color.rgb(217,217,217));
    }
    @FXML
    public void onCreateOrderBtnMouseExit(){
        createOrderBtn.setTextFill(Color.WHITE);
        cerateOrderIcon.setStroke(Color.WHITE);
        cerateOrderIcon.setFill(Color.WHITE);
//        syncOrderBtn.setStyle("-fx-background-color: #F1F1F1; -fx-background-radius: 8px; -fx-font-weight: bold;");
    }
    @FXML
    public void onSearchFeildKeyTyped(){
        if (searchFeild.getText().length() > 15){
            searchFeild.deleteText(15, searchFeild.getText().length());
        }
    }
    @FXML
    public void getAllOrders(){
        makeStateActive(allPane);
    }
    @FXML
    public void getPickedOrders(){
        makeStateActive(pickedPane);
    }
    @FXML
    public void getPackagingOrders(){
        makeStateActive(packagingPane);
    }
    @FXML
    public void getShippingOrders(){
        makeStateActive(shippingPane);
    }
    @FXML
    public void getDeliveredOrders(){
        makeStateActive(deliveredPane);
    }
    @FXML
    public void getReturnedOrders(){
        makeStateActive(returnedPane);
    }

    protected void makeStateActive(AnchorPane statePane){
        if(!statePane.getStyle().equals("-fx-background-color: #F1F1F1; -fx-background-radius: 8px;")){
            deactivateAllStates();
            statePane.setStyle("-fx-background-color: #F1F1F1; -fx-background-radius: 8px;");
        }
    }
    protected void deactivateAllStates(){
                allPane.setStyle("-fx-background-radius: 8px;");
                pickedPane.setStyle("-fx-background-radius: 8px;");
                packagingPane.setStyle("-fx-background-radius: 8px;");
                shippingPane.setStyle("-fx-background-radius: 8px;");
                deliveredPane.setStyle("-fx-background-radius: 8px;");
                returnedPane.setStyle("-fx-background-radius: 8px;");
    }


    protected boolean isStateActive(AnchorPane statePane){
        return statePane.getStyle().equals("-fx-background-radius: 8px; -fx-background-color:#F1F1F1");
    }
    public BorderPane getOrderPane(){
        return orderPane;
    }

    public class OrderRow{
        SimpleIntegerProperty orderID;
        SimpleStringProperty weight;
        SimpleStringProperty city;
        SimpleStringProperty status;
        SimpleStringProperty price;
        SimpleObjectProperty shipment;
        SimpleStringProperty createdAt;

        public OrderRow(Integer orderID, double weight, String city, State status, double price, LocalDateTime createdAt, int shipment) {
            this.orderID = new SimpleIntegerProperty(orderID);
            this.weight = new SimpleStringProperty(weight+ "kg");
            this.city = new SimpleStringProperty(city);
            this.status = new SimpleStringProperty(status.toString());
            this.price = new SimpleStringProperty(price+" LE");
            this.shipment = new SimpleObjectProperty(shipmentContent(shipment));
            this.createdAt = new SimpleStringProperty(createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")));
        }
        public OrderRow(Integer orderID, double weight, String city, State status, double price, LocalDateTime createdAt) {
            this.orderID = new SimpleIntegerProperty(orderID);
            this.weight = new SimpleStringProperty(weight+" kg");
            this.city = new SimpleStringProperty(city);
            this.status = new SimpleStringProperty(status.toString());
            this.price = new SimpleStringProperty(price+" LE");
            this.shipment = new SimpleObjectProperty(emptyShipmentView());
            this.createdAt = new SimpleStringProperty(createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")));
        }
        private HBox shipmentContent(int shipment){
            String shipmentTitle= "Shipment "+shipment;
            Label label = new Label(shipmentTitle);
            HBox hBox = new HBox(label,filledShipmentView());
            hBox.setSpacing(20);
            hBox.setAlignment(Pos.CENTER_LEFT);
            return hBox;
        }

        private HBox emptyShipmentView(){
            SVGPath icon = new SVGPath();
            icon.setContent("M6.41663 7.58317H2.91663V6.4165H6.41663V2.9165H7.58329V6.4165H11.0833V7.58317H7.58329V11.0832H6.41663V7.58317Z");
            icon.setFill(Color.rgb(35,90,59));
            Button attachShipment = new Button("",icon);
            attachShipment.setStyle("-fx-background-color:#DCFBE3;-fx-border-radius:6px;-fx-background-radius:6px;");
            attachShipment.setTranslateX(25);
            attachShipment.setCursor(Cursor.HAND);
            HBox hBox= new HBox(attachShipment);
            hBox.setAlignment(Pos.CENTER);
            return hBox;
        }
        private Button filledShipmentView(){
            SVGPath icon = new SVGPath();
            icon.setContent("M18.364 5.63604C19.9926 7.26472 21 9.51472 21 12C21 16.9706 16.9706 21 12 21C9.51472 21 7.26472 19.9926 5.63604 18.364M18.364 5.63604C16.7353 4.00736 14.4853 3 12 3C7.02944 3 3 7.02944 3 12C3 14.4853 4.00736 16.7353 5.63604 18.364M18.364 5.63604L5.63604 18.364");
            icon.setFill(Color.TRANSPARENT);
            icon.setStroke(Color.WHITE);
            icon.setScaleX(0.8);
            icon.setScaleY(0.8);
            icon.setStrokeWidth(1.5);
            Button deattachShipment = new Button("",icon);
            deattachShipment.setStyle("-fx-background-color:#ff3b3b;-fx-border-radius:6px;-fx-background-radius:6px;-fx-padding:3px");
            deattachShipment.setCursor(Cursor.HAND);
            return deattachShipment;
        }

        public int getOrderID() {
            return orderID.get();
        }

        public SimpleIntegerProperty orderIDProperty() {
            return orderID;
        }

        public void setOrderID(int orderID) {
            this.orderID.set(orderID);
        }

        public String getWeight() {
            return weight.get();
        }

        public SimpleStringProperty weightProperty() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight.set(weight+ " kg");
        }

        public String getCity() {
            return city.get();
        }

        public SimpleStringProperty cityProperty() {
            return city;
        }

        public void setCity(String city) {
            this.city.set(city);
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

        public String getPrice() {
            return price.get();
        }

        public SimpleStringProperty priceProperty() {
            return price;
        }

        public void setPrice(double price) {
            this.price.set(price+ " LE");
        }

        public Object getShipment() {
            return shipment.get();
        }

        public SimpleObjectProperty shipmentProperty() {
            return shipment;
        }

        public void setShipment(Object shipment) {
            this.shipment.set(shipment);
        }

        public String getCreatedAt() {
            return createdAt.get();
        }

        public SimpleStringProperty createdAtProperty() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt.set(createdAt);
        }
    }

    public class CreateOrderPopupController{

    }

}
