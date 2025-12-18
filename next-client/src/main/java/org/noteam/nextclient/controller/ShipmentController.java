package org.noteam.nextclient.controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.noteam.nextclient.dto.ShipmentRequest;
import org.noteam.nextclient.models.Shipment;
import org.noteam.nextclient.utils.SqlUtil;

import java.util.Date;
import java.util.List;


public class ShipmentController {
    @FXML
    private TableView<ShipmentRequest> completedShipmentTable;
    @FXML
    private TableView<ShipmentRequest> inCompletedShipmentTable;
    @FXML
    private TableColumn<ShipmentRequest, Integer> completedShipmentIdColumn;
    @FXML
    private TableColumn<ShipmentRequest, String> completedCityColumn;
    @FXML
    private TableColumn<ShipmentRequest, Integer> completedWeightColumn;
    @FXML
    private TableColumn<ShipmentRequest, Date> completedShippingDateColumn;
    @FXML
    private TableColumn<ShipmentRequest, Integer> incompletedShipmentIdColumn;
    @FXML
    private TableColumn<ShipmentRequest, String> incompletedCityColumn;
    @FXML
    private TableColumn<ShipmentRequest, Integer> incompletedWeightColumn;
    @FXML
    private TableColumn<ShipmentRequest, Date> incompletedShippingDateColumn;
    @FXML
    private TableColumn<ShipmentRequest, Void> completedUpdateButtonColumn;
    @FXML
    private TableColumn<ShipmentRequest, Void> completedDeleteButtonColumn;
    @FXML
    private TableColumn<ShipmentRequest, Void> incompletedUpdateButtonColumn;
    @FXML
    private TableColumn<ShipmentRequest, Void> incompletedDeleteButtonColumn;
    @FXML
    private Button completedPrevBtn, completedNextBtn,incompletedPrevBtn, incompletedNextBtn ,createShipmentBtn;
    @FXML
    private Label completedPageLabel;
    @FXML
    private Label inCompletedPageLabel;
     @FXML
    private ObservableList<ShipmentRequest> completeTableData
            = FXCollections.observableArrayList();
     @FXML
    private ObservableList<ShipmentRequest> inCompleteTableData
            = FXCollections.observableArrayList();

    private List<ShipmentRequest> completedShipment;
    private List<ShipmentRequest> inCompletedShipment;
    private int completedCurrentPage = 0;
    private int inCompletedCurrentPage = 0;
    private final int PAGE_SIZE = 10;
    private ObservableList<Shipment> observableList = FXCollections.observableArrayList();
    private ShipmentController shipmentController = new ShipmentController();
    @FXML
        public void initialize() {
        createShipmentBtn.setOnAction(event -> {
            handleCreate();
        });
         completedShipmentIdColumn.setCellValueFactory(
                 new PropertyValueFactory<>("shipmentId")
         );
         completedCityColumn.setCellValueFactory(
                 new PropertyValueFactory<>("cityName")
         );
         completedCityColumn.setCellValueFactory(
                 new PropertyValueFactory<>("totalWeight")
         );
        completedShippingDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("shippingDate")
        );
        incompletedShipmentIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("shipmentId")
        );
        incompletedCityColumn.setCellValueFactory(
                new PropertyValueFactory<>("cityName")
        );
        incompletedCityColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalWeight")
        );
        incompletedShippingDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("shippingDate")
        );
        setupUpdateColumn();
        setupDeleteColumn();
            completedShipmentTable.setItems(completeTableData);
            inCompletedShipmentTable.setItems(inCompleteTableData);

            completedShipment = SqlUtil.getShipmentsByComplete(true);
            inCompletedShipment=SqlUtil.getShipmentsByComplete(false);
            completedPage(0);
            inCompletedPage(0);
        }

        private void completedPage(int page) {
            int fromIndex = page * PAGE_SIZE;
            int toIndex = Math.min(fromIndex + PAGE_SIZE, completedShipment.size());
            if (fromIndex >= completedShipment.size()) return;
            List<ShipmentRequest> pageData =
                    completedShipment.subList(fromIndex, toIndex);

            completeTableData.setAll(pageData);
            completedCurrentPage = page;
            completedPageLabel.setText("Page " + (completedCurrentPage + 1));
            completedPrevBtn.setDisable(completedCurrentPage == 0);
            completedNextBtn.setDisable(toIndex >=  completedShipment.size());
        }

    private void inCompletedPage(int page) {
        int fromIndex = page * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, inCompletedShipment.size());
        if (fromIndex >= inCompletedShipment.size()) return;
        List<ShipmentRequest> pageData =
                inCompletedShipment.subList(fromIndex, toIndex);

        inCompleteTableData.setAll(pageData);

        inCompletedCurrentPage = page;
        inCompletedPageLabel.setText("Page " + (inCompletedCurrentPage + 1));

        completedPrevBtn.setDisable(completedCurrentPage == 0);
        completedNextBtn.setDisable(toIndex >=  inCompletedShipment.size());
    }

    private void setupUpdateColumn() {
        completedUpdateButtonColumn.setCellFactory(col -> new TableCell<>() {
            private final Button updateBtn = new Button("Update");
            {
                updateBtn.setOnAction(e -> {
                    ShipmentRequest shipment = getTableView()
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
                updateBtn.setOnAction(e -> {
                    ShipmentRequest shipment = getTableView()
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

    private void handleUpdate(ShipmentRequest shipment ,boolean completed) {
        System.out.println("Update shipment id = " + shipment.getShipmentId());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/update-shipment.fxml"));
            Parent parent=loader.load();
            CreateAndUpdateShipmentController controller=loader.getController();
            controller.setShipment(shipment ,false);
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
        }
    }
    private void handleCreate() {
        System.out.println("create shipment" );
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/create-shipment.fxml"));
            Parent parent=loader.load();
            CreateAndUpdateShipmentController controller=loader.getController();
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
        }
    }

    private void setupDeleteColumn() {
        completedDeleteButtonColumn.setCellFactory(col -> new TableCell<>() {

            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setOnAction(e -> {
                    ShipmentRequest shipment = getTableView()
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
                deleteBtn.setOnAction(e -> {
                    ShipmentRequest shipment = getTableView()
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

    private void handleDelete(ShipmentRequest shipment , boolean completed) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Shipment");
        alert.setContentText("Are you sure?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
              if(SqlUtil.deleteShipment(shipment.getShipmentId())) {
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
    private void completedNextPage() {
        completedPage(completedCurrentPage + 1);
    }
    @FXML
    private void incompletedNextPage() {
        inCompletedPage(inCompletedCurrentPage + 1);
    }

    @FXML
    private void completedPrevPage() {
        inCompletedPage(completedCurrentPage - 1);
    }
    @FXML
    private void incompletedPrevPage() {
        inCompletedPage(inCompletedCurrentPage - 1);
    }



}