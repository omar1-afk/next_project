package org.noteam.nextclient.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.noteam.nextclient.dto.OrderTable;
import org.noteam.nextclient.dto.ShipmentRequest;
import org.noteam.nextclient.models.*;
import org.noteam.nextclient.utils.SqlUtil;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CreateAndUpdateShipmentController {

        @FXML
        private Label shipmentId,TotalWeight , Driver ,Vehicle,ShippingDate ,Completed ,pageLabel;
        @FXML
        private TextField totalWeightField;
        @FXML
        private ComboBox<Vehicle> vehicleCombo;
        @FXML
        private ComboBox<Driver> driverCombo;
        @FXML
        private ComboBox<City> cityCombo;
        @FXML
        private DatePicker shippingDatePicker;
        @FXML
       private CheckBox completedCheck;
        @FXML
        private TableView<OrderTable> ordersTable;
        @FXML
        private TableColumn<OrderTable, Integer> orderIdColumn;
        @FXML
        private TableColumn<OrderTable, Integer> weightColumn;
        @FXML
        private TableColumn<OrderTable, Integer> priceColumn;
        @FXML
        private TableColumn<OrderTable, Boolean> addToShipmentColumn;
        @FXML
        private Button createButton,updateButton, ordersPrevBtn, ordersNextBtn;;
        @FXML
        private ObservableList<OrderTable> ordersTableData
                = FXCollections.observableArrayList();
        private List<OrderTable> allOrders;
        private List <OrderTable> shipmentOrders;
        private int currentPage = 0;
        private final int PAGE_SIZE = 10;
        private ShipmentRequest shipment;
        private int totalWeight = 0;

        public void setShipment(ShipmentRequest shipment ,boolean create) {
            this.shipment = shipment;
            fillData(create);
        }

        public void fillData(boolean create) {

            shipmentId.setText(String.valueOf(shipment.getShipmentId()));
            vehicleCombo.setConverter(new javafx.util.StringConverter<Vehicle>(){
                @Override
                public String toString(Vehicle vehicle){
                    if(vehicle==null)  return null;
                    return  vehicle.getVehicleId()+"-"+ vehicle.getVehicleType() ;
                }
                @Override
                public Vehicle fromString(String string){
                    return null;
                }
            });
            driverCombo.setConverter(new  javafx.util.StringConverter<Driver>(){
                @Override
                public String toString(Driver driver){
                    if(driver==null)  return null;
                    return driver.getDriverId() +"-"+ driver.getName();
                }
                @Override
                public Driver fromString(String string){
                    return null;
                }
            });
              cityCombo.setConverter(new   javafx.util.StringConverter<City>(){
                @Override
                public String toString(City city){
                    if(city ==null)  return null;
                    return  city.getName()+"-"+ city.getCountry().getName() ;
                }
                @Override
                public City fromString(String string){
                    return null;
                }
            });
            LocalDate shippingDate = LocalDate.parse(shipment.getShippingDate());
            shippingDatePicker.setValue(shippingDate);

            completedCheck.setSelected(shipment.isComplete()); // add logic prevent edit
            orderIdColumn.setCellValueFactory(
                    new PropertyValueFactory<>("orderId")
            );
            weightColumn.setCellValueFactory(
                    new PropertyValueFactory<>("weight")
            );
            priceColumn.setCellValueFactory(
                    new PropertyValueFactory<>("price")
            );
           addToShipmentColumn.setCellValueFactory(cellData->cellData.getValue().selectedProperty());
           addToShipmentColumn.setCellFactory(CheckBoxTableCell.forTableColumn(addToShipmentColumn));
           ordersTable.setItems(ordersTableData);
           allOrders = SqlUtil.getAllOrders();
           shipmentOrders=SqlUtil.getShipmentOrders(shipment.getShipmentId());
           ordersPage(0);
           handelCheckBox(allOrders,shipmentOrders);
           if(create){
               shipmentId.setText("");
               vehicleCombo.setPromptText("Select Vehicle");
               driverCombo.setPromptText("Select Driver");
               cityCombo.setPromptText("Select City");
               shippingDatePicker.setPromptText("Select Shipping Date");
               completedCheck.setSelected(false);
               shipmentOrders= Collections.emptyList();
               ordersPage(0);
               handelCheckBox(allOrders,shipmentOrders);

           }
        }
    private void ordersPage(int page) {
        int fromIndex = page * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE,allOrders.size());
        if (fromIndex >= allOrders.size()) return;
        List<OrderTable> pageData =
                allOrders.subList(fromIndex, toIndex);

        ordersTableData.setAll(pageData);
        currentPage = page;
        pageLabel.setText("Page " + (currentPage + 1));
        ordersPrevBtn.setDisable(currentPage == 0);
        ordersNextBtn.setDisable(toIndex >=  allOrders.size());
    }
    private void handelCheckBox(List<OrderTable> allOrders , List<OrderTable> shipmentOrders) {
        for (OrderTable order : allOrders) {
            if (shipmentOrders.contains(order)) {
                order.setSelected(true);
                totalWeight+=order.getOrderWeight();
            }
            else  {
                order.setSelected(false);
            }
            order.selectedProperty().addListener((observable, wasSelected, isNowSelected)
                    -> {
                if(isNowSelected) {
                    totalWeight += order.getOrderWeight();
                }
                else {

                    totalWeight -= order.getOrderWeight();
                    totalWeight= Math.max(0,totalWeight);
                }
                totalWeightField.setText(totalWeight+"kg");
            });
        }
        totalWeightField.setText(totalWeight+"kg");
    }
    @FXML
    private void ordersNextPage() {
        ordersPage(currentPage + 1);
    }
    @FXML
    private void ordersPrevPage() {
        ordersPage(currentPage - 1);
    }
    @FXML
    private void updateShipment() {
            List<OrderTable> selectedOrders = allOrders.stream().filter(OrderTable::isSelected).toList();
            List<Integer> ordersIds = new ArrayList<>();
            for(OrderTable order : selectedOrders) {
                order.getOrderId();
                ordersIds.add(order.getOrderId());
            }
           // if(completedCheck.isSelected()) {
            //    SqlUtil.setShipmentAsCompleted(shipment.getShipmentId());
           // }
            shipment.setOrderIds(ordersIds);
            shipment.setVehicleId(vehicleCombo.getValue().getVehicleId());
            shipment.setDriverId(driverCombo.getValue().getDriverId());
            shipment.setShippingDate(shippingDatePicker.getValue().toString());
            shipment.setTotalWeight(totalWeight);
           shipment.setCityId(cityCombo.getValue().getCity_id());
           if(SqlUtil.updateShipment(shipment)) {
               Stage stage = (Stage) updateButton.getScene().getWindow();
               stage.close();
           }

        }


}
