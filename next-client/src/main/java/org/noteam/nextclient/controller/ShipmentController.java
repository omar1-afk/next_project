package org.noteam.nextclient.controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.noteam.nextclient.dto.shipment.ShipmentDisplayDTO;
import org.noteam.nextclient.utils.SqlUtil;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class ShipmentController {
    @FXML
    private BorderPane shipmentPane;
    public BorderPane getShipmentPane(){
        return shipmentPane;
    }
    private AdminController adminController;

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }
    @FXML
    private TableView<ShipmentDisplayDTO> completedShipmentTable;
    @FXML
    private TableView<ShipmentDisplayDTO> inCompletedShipmentTable;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Integer> completedShipmentIdColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, String> completedCityColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Integer> completedWeightColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, String> completedShippingDateColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Integer> incompletedShipmentIdColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, String> incompletedCityColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Integer> incompletedWeightColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, String> incompletedShippingDateColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Void> completedUpdateButtonColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Void> completedDeleteButtonColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Void> incompletedUpdateButtonColumn;
    @FXML
    private TableColumn<ShipmentDisplayDTO, Void> incompletedDeleteButtonColumn;
    @FXML
    private Button completedPrevBtn, completedNextBtn, incompletedPrevBtn,
            incompletedNextBtn, createShipmentBtn, syncTableBtn;
    @FXML
    private ComboBox searchComboBox;
    @FXML
    private TextField searchField;
    // @FXML
    // private Button searchBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Label completedPageLabel, completedLabel, incompletedLabel;

    @FXML
    private Label inCompletedPageLabel;
     @FXML
    private ObservableList<ShipmentDisplayDTO> completeTableData
            = FXCollections.observableArrayList();
     @FXML
    private ObservableList<ShipmentDisplayDTO> inCompleteTableData
            = FXCollections.observableArrayList();


    private ObservableList<ShipmentDisplayDTO> completedShipment;
    private ObservableList<ShipmentDisplayDTO> inCompletedShipment;
    private ObservableList<ShipmentDisplayDTO> originalCompletedShipment;
    private ObservableList<ShipmentDisplayDTO> originalInCompletedShipment;

    private int completedCurrentPage = 0;
    private int inCompletedCurrentPage = 0;

    private final int PAGE_SIZE = 14;


    @FXML
    public void initialize() {
        completedShipmentTable.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(getClass()
                        .getResource("/org/noteam/nextclient/shipmentsTables.css").toExternalForm());
            }
        });
        searchComboBox.getItems().addAll(
                "Completed",
                "Shipment ID",
                "Driver ID",
                "Admin ID",
                "City ID",
                "Vehicle ID");
        createShipmentBtn.setOnAction(event -> {
            handleCreate();
        });
        syncTableBtn.setOnAction(event -> {
            syncTableData();
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                searchField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

         completedShipmentIdColumn.setCellValueFactory(c->
                 c.getValue().shipmentIdProperty().asObject()

         );
         completedCityColumn.setCellValueFactory(
                 c->
                         c.getValue().cityNameProperty()
         );
         completedWeightColumn.setCellValueFactory(c->
                 c.getValue().totalWeightProperty().asObject()

         );
        completedShippingDateColumn.setCellValueFactory(c->
                c.getValue().shippingDateProperty()
        );
        incompletedShipmentIdColumn.setCellValueFactory(c->
                c.getValue().shipmentIdProperty().asObject()
        );
        incompletedCityColumn.setCellValueFactory(c->
                c.getValue().cityNameProperty()
        );
        incompletedWeightColumn.setCellValueFactory(c->
                c.getValue().totalWeightProperty().asObject()
        );
        incompletedShippingDateColumn.setCellValueFactory(c->
                c.getValue().shippingDateProperty()
        );
        setupUpdateColumn();
        setupDeleteColumn();
        originalCompletedShipment = FXCollections.observableArrayList(SqlUtil.getShipmentsByComplete(true));
        originalInCompletedShipment = FXCollections.observableArrayList(SqlUtil.getShipmentsByComplete(false));
        completedShipment = FXCollections.observableArrayList();
        completedShipment.setAll(originalCompletedShipment);
        inCompletedShipment = FXCollections.observableArrayList();
        inCompletedShipment.setAll(originalInCompletedShipment);
        completedPage(0);
        inCompletedPage(0);
        completedShipmentTable.setFixedCellSize(55);
        inCompletedShipmentTable.setFixedCellSize(55);
        completedShipmentTable.setItems(completeTableData);
        inCompletedShipmentTable.setItems(inCompleteTableData);
    }

    private void completedPage(int page) {
        int fromIndex = page * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, completedShipment.size());
        if (completedShipment.isEmpty()) {
            completeTableData.clear();
            return;
        } else if (fromIndex >= completedShipment.size()) {
            return;
        }
        List<ShipmentDisplayDTO> pageData = completedShipment.subList(fromIndex, toIndex);

            completeTableData.setAll(pageData);
            completedCurrentPage = page;
            completedPageLabel.setText("" + (completedCurrentPage + 1));
            completedPrevBtn.setDisable(completedCurrentPage == 0);
            completedNextBtn.setDisable(toIndex >=  completedShipment.size());
        }

    private void inCompletedPage(int page) {
        int fromIndex = page * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, inCompletedShipment.size());
        if (inCompletedShipment.isEmpty()) {
            inCompleteTableData.clear();
            return;
        } else if (fromIndex >= inCompletedShipment.size()) {
            return;
        }
        List<ShipmentDisplayDTO> pageData = inCompletedShipment.subList(fromIndex, toIndex);

        inCompleteTableData.setAll(pageData);

        inCompletedCurrentPage = page;
        inCompletedPageLabel.setText("" + (inCompletedCurrentPage + 1));

        incompletedPrevBtn.setDisable(inCompletedCurrentPage == 0);
        incompletedNextBtn.setDisable(toIndex >=  inCompletedShipment.size());
    }

    private void setupUpdateColumn() {
        completedUpdateButtonColumn.setCellFactory(col -> new TableCell<>() {
            private final Button updateBtn = new Button("Update");
            {
                updateBtn.getStyleClass().add("table-button");
                updateBtn.setOnAction(e -> {
                    ShipmentDisplayDTO shipment = getTableView()
                            .getItems()
                            .get(getIndex());

                    handleUpdate(shipment , true);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateBtn);
                }
            }
        });
        incompletedUpdateButtonColumn.setCellFactory(col -> new TableCell<>() {
            private final Button updateBtn = new Button("Update");
            {
                updateBtn.getStyleClass().add("table-button");
                updateBtn.setOnAction(e -> {
                    ShipmentDisplayDTO shipment = getTableView()
                            .getItems()
                            .get(getIndex());

                    handleUpdate(shipment,false);
                });
            }
            @Override
          protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateBtn);
                }
            }
        });
        }


    public void handleUpdate(ShipmentDisplayDTO shipment ,boolean completed) {
       // System.out.println("Update shipment id = " + shipment.getShipmentId());
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/create-shipment.fxml"));
            Parent parent=loader.load();
            CreateAndUpdateShipmentController controller=loader.getController();
            controller.setShipment(shipment );
            controller.fillData(false);
            //MainController mainController=loader.getController();
            //mainController.getViewPane().getChildren().add(controller.getCreateShipmentPane());
            Stage stage = new Stage();
            stage.setTitle("Update Shipment");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (completed) {completedShipmentTable.refresh();}
            else{inCompletedShipmentTable.refresh();}
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    private void syncTableData()
    {
        completedShipmentTable.refresh();
        inCompletedShipmentTable.refresh();
    }

    private void handleCreate() {
       // System.out.println("create shipment" );
        try {
            URL url=getClass().getResource("/org/noteam/nextclient/scene/create-shipment.fxml");
            System.out.println(url);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/create-shipment.fxml"));
            Parent parent=loader.load();
            CreateAndUpdateShipmentController controller=loader.getController();
            controller.setAdminController(this.adminController);
            controller.setShipment(null);
            controller.fillData(true);
            Stage stage = new Stage();
            stage.setTitle("Create new Shipment");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            completedShipmentTable.refresh();
            inCompletedShipmentTable.refresh();
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

   private void setupDeleteColumn() {
        completedDeleteButtonColumn.setCellFactory(col -> new TableCell<>() {

            private final Button deleteBtn = new Button("Delete");

            { deleteBtn.getStyleClass().addAll("table-button","delete-button");
                deleteBtn.setOnAction(e -> {
                    ShipmentDisplayDTO shipment = getTableView()
                            .getItems()
                            .get(getIndex());

                    handleDelete(shipment,true);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
        incompletedDeleteButtonColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.getStyleClass().addAll("table-button","delete-button");
                deleteBtn.setOnAction(e -> {
                    ShipmentDisplayDTO shipment = getTableView()
                            .getItems()
                            .get(getIndex());

                    handleDelete(shipment,false);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });
    }

    private void handleDelete(ShipmentDisplayDTO shipment , boolean completed) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Shipment");
        alert.setContentText("Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
              if(SqlUtil.deleteShipment(shipment.shipmentIdProperty().get())) {
                    if(completed) {
                        completedShipment.remove(shipment);
                        completeTableData.remove(shipment);
                    }
                    else {
                     inCompletedShipment.remove(shipment);
                     inCompleteTableData.remove(shipment);
                    }
                }
              // pop up couldn't delete
            }
        });
    }
    @FXML
    private void handleSearch(ActionEvent event ) {
       String selected = searchComboBox.getValue().toString();
       if(selected== null) { return; }
        if (selected.equals("Completed")) {
            searchField.clear();
            searchField.setDisable(true);
            completedShipment.setAll(originalCompletedShipment);
            inCompletedShipment.setAll(originalInCompletedShipment);
            completedPage(0);
            inCompletedPage(0);
        }
        else {
           searchField.setDisable(false);
        }
       String text= searchField.getText();
       if(text==null ||text.isBlank()){
               return;
       }
       try {

           List<ShipmentDisplayDTO> shipmentRequests = List.of();
           int id = Integer.parseInt(text);
           completedShipment.clear();
           inCompletedShipment.clear();
           boolean complete;
           if (selected.equals("Shipment ID")) {
               shipmentRequests = SqlUtil.getShipmentById(id);

           } else if (selected.equals("Driver ID")) {
               shipmentRequests = SqlUtil.getShipmentsByDriverId(id);

           } else if (selected.equals("Admin ID")) {
               shipmentRequests = SqlUtil.getShipmentsByAdminID(id);

           } else if (selected.equals("Vehicle ID")) {
               shipmentRequests = SqlUtil.getShipmentsByVehicleId(id);

           } else if (selected.equals("City ID")) {
               shipmentRequests = SqlUtil.getShipmentsByCityId(id);
           }

           for (ShipmentDisplayDTO s : shipmentRequests) {
               if (s.isComplete()) {
                   completedShipment.add(s);
               } else {
                   inCompletedShipment.add(s);
               }
           }

           setupUpdateColumn();
           setupDeleteColumn();
           completedPage(0);
           inCompletedPage(0);
           completedShipmentTable.setItems(completeTableData);
           inCompletedShipmentTable.setItems(inCompleteTableData);

       }
       catch (Exception e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("Error");
           alert.setContentText(e.getMessage());
           alert.showAndWait();
       }
    }


    @FXML
    private void completedNextPage() {
        completedPage(completedCurrentPage + 1);
    }
    @FXML
    private void incompletedNextPage() {
        inCompletedPage(inCompletedCurrentPage + 1);
    }

    @FXML
    private void completedPrevPage() {
        completedPage(completedCurrentPage - 1);
    }
    @FXML
    private void incompletedPrevPage() {
        inCompletedPage(inCompletedCurrentPage - 1);
    }


}