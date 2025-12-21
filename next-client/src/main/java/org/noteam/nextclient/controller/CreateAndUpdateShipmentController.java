package org.noteam.nextclient.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.noteam.nextclient.dto.OrderTable;
import org.noteam.nextclient.dto.shipment.ShipmentCreateDTO;
import org.noteam.nextclient.dto.shipment.ShipmentDisplayDTO;
import org.noteam.nextclient.dto.shipment.ShipmentUpdateDTO;
import org.noteam.nextclient.models.*;
import org.noteam.nextclient.utils.SqlUtil;

import java.time.LocalDate;
import java.util.*;


public class CreateAndUpdateShipmentController {

    @FXML
    private AnchorPane createShipmentPane;

    public AnchorPane getCreateShipmentPane() {
        return createShipmentPane;
    }

    private AdminController adminController;

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

       @FXML
        private Label shipmentId,TotalWeight , Driver ,Vehicle,ShippingDate ,pageLabel;
        @FXML
        private TextField totalWeightField;
        @FXML
        private ComboBox<Integer> vehicleCombo;
        @FXML
        private ComboBox<DriverObj> driverCombo;
       // @FXML
       // private ComboBox<City> cityCombo;
        @FXML
        private DatePicker shippingDatePicker;
      //  @FXML
      // private CheckBox completedCheck;
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
        private Button updateButton, ordersPrevBtn, ordersNextBtn;;
        @FXML
        private ObservableList<OrderTable> ordersTableData
                = FXCollections.observableArrayList();
        private List<OrderTable> allOrders;
        private List <OrderTable> shipmentOrders;
        private List<Integer> vehicles;
        private List<City> cities;
        private List<DriverObj> drivers;
        private int currentPage = 0;
        private final int PAGE_SIZE = 10;
        private ShipmentUpdateDTO  shipment =null;
        private ShipmentDisplayDTO  shipmentDisplay = null;
        private int totalWeight = 0;
    private boolean listenersInitialized = false;
    private final List<ChangeListener<Boolean>> orderListeners = new ArrayList<>();
    private boolean isCreateMode;

    public void setShipment(ShipmentDisplayDTO shipmentDisplay) {
            if (shipmentDisplay != null) {
                this.shipmentDisplay = shipmentDisplay;
                this.shipment= new ShipmentUpdateDTO.Builder()
                        .shipmentId(shipmentDisplay.shipmentIdProperty().get())
                        .totalWeight(shipmentDisplay.totalWeightProperty().get())
                        .build();
            } else {
                this.shipment = null;
            }

        }

        public void fillData(boolean create) {
            this.isCreateMode = create;
            orderIdColumn.setCellValueFactory(c->
                    c.getValue().getOrderId().asObject()
            );
            weightColumn.setCellValueFactory(c->
                    c.getValue().getOrderWeight().asObject()
            );
            priceColumn.setCellValueFactory(c->
                    c.getValue().getOrderPrice().asObject()
            );
            addToShipmentColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
            addToShipmentColumn.setCellFactory(CheckBoxTableCell.forTableColumn(addToShipmentColumn));

            allOrders = SqlUtil.getAllOrders();
            if (create) {
                shipmentId.setText("");
                vehicleCombo.setValue(null);
                vehicleCombo.setPromptText("Select Vehicle");
                driverCombo.setValue(null);
                driverCombo.setPromptText("Select Driver");
                // cityCombo.setValue(null);
                // cityCombo.setPromptText("Select City");
                shippingDatePicker.setValue(null);
                shippingDatePicker.setPromptText("Select Shipping Date");
                //completedCheck.setSelected(false);
                updateButton.setText("Create Shipment");
                this.shipmentDisplay = null;
            } else {

                if (shipment != null && shipment.getShipmentId() > 0) {
                    shipmentOrders = SqlUtil.getShipmentOrders(shipment.getShipmentId());
                    shipmentId.setText(String.valueOf(shipment.getShipmentId()));

                } else {
                    shipmentOrders = Collections.emptyList();
                }
                updateButton.setText("Update Shipment");

                if (shipmentOrders == null) {
                    shipmentOrders = Collections.emptyList();
                }

                int shipmentVehicleId = shipmentDisplay.getVehicleId();
                for (int v : vehicles) {
                    if (v == shipmentVehicleId) {
                        vehicleCombo.setValue(v);
                        break;
                    }
                }
                DriverObj shipmentDriver = shipmentDisplay.getDriver();
                if (shipmentDriver != null) {
                    for (DriverObj d : drivers) {
                        if (d.getDriverId() == shipmentDriver.getDriverId()) {
                            driverCombo.setValue(d);
                            break;
                        }
                    }
                }
                City city = shipmentDisplay.getCity();
                //if (city != null) {
                //  for (City c : cities) {
                // if (c.getCity_id() == city.getCity_id()) {
                //   cityCombo.setValue(c);
                //   break;
                // }
                // }
                // }

                LocalDate shippingDate = LocalDate.parse(shipment.getShippingDate());
                shippingDatePicker.setValue(shippingDate);

            }
            applyInitialSelection(allOrders, shipmentOrders);
            initializeOrderListeners();
            vehicleCombo.setConverter(new StringConverter<>() {
                @Override
                public String toString(Integer vehicleId) {
                    return vehicleId == null ? "" : "Vehicle #" + vehicleId.toString();
                }

                @Override
                public Integer fromString(String string) {
                    return null;
                }
            });
            vehicles = SqlUtil.getAvailableVehicles();
            vehicleCombo.getItems().setAll(vehicles);
            driverCombo.setConverter(new StringConverter<>() {
                @Override
                public String toString(DriverObj driver) {
                    return driver == null ? "" : driver.getDriverId() + "-" + driver.getName();
                }

                @Override
                public DriverObj fromString(String string) {
                    return null;
                }
            });
            drivers = SqlUtil.getAvailableAndNotBusyDrivers();
            driverCombo.getItems().setAll(drivers);
            /*
            cityCombo.setConverter(new   javafx.util.StringConverter<City>(){
                @Override
                public String toString(City city){

                    return city==null ? "" : city.getName()+"-"+ city.getCountry().getName() ;
                }
                @Override
                public City fromString(String string){
                    return null;
                }
            });
*/

//cities=SqlUtil.getAllCities();
// cityCombo.getItems().setAll(cities);
            //completedCheck.setSelected(shipment.isComplete()); // add logic prevent edit
           // handelCheckBox(allOrders,shipmentOrders);
            ordersPage(0);
            ordersTable.setItems(ordersTableData);


        }

    private void applyInitialSelection(
            List<OrderTable> allOrders,
            List<OrderTable> shipmentOrders
    ) {
        totalWeight = 0;

        if (allOrders == null) return;

        // Create
        if (isCreateMode) {
            for (OrderTable order : allOrders) {
                order.setSelected(false);
            }
        }
        // Update
        else {
            if (shipmentOrders == null) return;

            Set<Integer> shipmentIds = new HashSet<>();
            for (OrderTable shippedOrder : shipmentOrders) {
                shipmentIds.add(shippedOrder.getOrderId().get());
            }
            for (OrderTable order : allOrders) {
                if (shipmentIds.contains(order.getOrderId())) {
                    order.setSelected(true);
                    totalWeight += order.getOrderWeight().get();
                } else {
                    order.setSelected(false);
                }
            }
        }

        updateTotalWeightField();
    }


    private void initializeOrderListeners() {
        if (allOrders == null) return;

        for (OrderTable order : allOrders) {
            order.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {

                if (isNowSelected) {
                    totalWeight += order.getOrderWeight().get();
                } else {
                    totalWeight -= order.getOrderWeight().get();
                }

                totalWeight = Math.max(0, totalWeight);
                updateTotalWeightField();
            });
        }
    }

    private void updateTotalWeightField() {
        totalWeightField.setText(totalWeight + "");
    }



    private void ordersPage(int page) {
        int fromIndex = page * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE,allOrders.size());
        if (fromIndex >= allOrders.size()) return;
        List<OrderTable> pageData =
                allOrders.subList(fromIndex, toIndex);

        ordersTableData.setAll(pageData);
        currentPage = page;
        pageLabel.setText("" + (currentPage + 1));
        ordersPrevBtn.setDisable(currentPage == 0);
        ordersNextBtn.setDisable(toIndex >=  allOrders.size());
    }


    private void removeOrderListeners() {
        if (allOrders != null && !orderListeners.isEmpty()) {
            for (int i = 0; i < Math.min(allOrders.size(), orderListeners.size()); i++) {
                allOrders.get(i).selectedProperty().removeListener(orderListeners.get(i));
            }
        }
        orderListeners.clear();
        listenersInitialized = false;
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
    private void createAndUpdateShipment(ActionEvent event) {
        boolean create =
                updateButton.getText().equals("Create Shipment");
            List<OrderTable> selectedOrders = allOrders.stream().filter(OrderTable::isSelected).toList();
            List<Integer> ordersIds = new ArrayList<>();
            for(OrderTable order : selectedOrders) {
                order.getOrderId();
                ordersIds.add(order.getOrderId().get());
            }
           // if(completedCheck.isSelected()) {
            //    SqlUtil.setShipmentAsCompleted(shipment.getShipmentId());
           // }
        if (vehicleCombo.getValue() == null || driverCombo.getValue() == null ||
              /*  cityCombo.getValue() == null || */shippingDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Input required fields");
            alert.showAndWait();
            return;
        }
            //ShipmentRequest shipmentRequest;
             int shipmentId = create?  0  :  shipment.getShipmentId();
             int vehicleId=vehicleCombo.getValue();
             int driverId=driverCombo.getValue().getDriverId();
             int cityId=1;
             String shippingDate=shippingDatePicker.getValue().toString();
            // shipmentRequest=new ShipmentRequest(ordersIds,shipmentId,vehicleId,driverId,totalWeight,shippingDate,cityId);
           if (create) {
               ShipmentCreateDTO createDTO = new ShipmentCreateDTO.Builder()
                       .orderIds(ordersIds)
                       .vehicleId(vehicleId)
                       .driverId(driverId)
                       .cityId(cityId)
                       .shippingDate(shippingDate)
                       .adminId(adminController.getAdmin().getAdminId())
                       .totalWeight(totalWeight)
                       .build();

               if(SqlUtil.createShipment(createDTO)){
                  Stage stage = (Stage) updateButton.getScene().getWindow();
                  stage.close();
              }
              else {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setContentText("Could not Create Shipment");
                  alert.showAndWait().ifPresent(response -> {
                      if (response == ButtonType.OK) {
                          Stage stage = (Stage) updateButton.getScene().getWindow();

                      }
                  });
           }}
           else {
               ShipmentUpdateDTO updateDTO = new ShipmentUpdateDTO.Builder()
                       .shipmentId(shipmentId)
                       .orderIds(ordersIds)
                       .vehicleId(vehicleId)
                       .driverId(driverId)
                       .cityId(cityId)
                       .shippingDate(shippingDate)
                       .totalWeight(totalWeight)
                       .build();
           if(SqlUtil.updateShipment(updateDTO)) {
               Stage stage = (Stage) updateButton.getScene().getWindow();
               stage.close();
           }
           else {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error");
               alert.setContentText("Could not Update Shipment");
               alert.showAndWait().ifPresent(response -> {
                   if (response == ButtonType.OK) {
                       Stage stage = (Stage) updateButton.getScene().getWindow();

                   }
               });

           }

           }

        }


}
