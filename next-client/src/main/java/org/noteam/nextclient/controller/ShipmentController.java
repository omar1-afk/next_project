package org.noteam.nextclient.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.noteam.nextclient.dto.Order;
import org.noteam.nextclient.dto.ShipmentDetails;
import org.noteam.nextclient.dto.State;
import org.noteam.nextclient.scene.DataEntryWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ShipmentController {
    @FXML
    private BorderPane shipmentPane;

    public BorderPane getShipmentPane() {
        return shipmentPane;
    }

    @FXML
    private Button openPopUp;

    @FXML
    protected void showPopUp(ActionEvent e) throws IOException {
        DataEntryWindow window = new DataEntryWindow<>("Total", (Stage) ((Node) e.getSource()).getScene().getWindow(),
                1280,
                720);
        LocalDate d = LocalDate.of(2025, 12, 31);
        Order order = new Order(
                0, "EG", "Alex", "Borg", "District", false, true, 100, State.PICKED, 50, LocalDate.of(2025, 12, 24),
                0, 1, 1, 2,
                LocalDateTime.now(),
                LocalDate.now() // Use LocalDate.now() here to match the record
        );
        Order order2 = new Order(
                1, "EG", "Alex", "Borg", "District", false, true, 100, State.DELEVERED, 50, d, 0, 0, 0, 1,
                LocalDateTime.now(),
                LocalDate.now() // Use LocalDate.now() here to match the record
        );
        Order order3 = new Order(
                2, "EG", "Alex", "Borg", "District", false, true, 100, State.DELEVERED, 50,
                LocalDate.of(2025, 12, 24), 0, 0, 0, 1,
                LocalDateTime.now(),
                LocalDate.now() // Use LocalDate.now() here to match the record
        );
        ArrayList<Order> orders = new ArrayList<>() {
            {
                add(order);
                add(order2);
                add(order3);
            }
        };
        ShipmentDetails shipment = new ShipmentDetails(
                0,
                orders);
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/noteam/nextclient/scene/shipmentDetails-scene.fxml"));

        Parent layout = loader.load();

        window.setContent((Region) layout);

        ShipmentDetailsController controller = loader.getController();
        controller.setShipmentDetails(shipment);

        Optional<Object> s = window.showAndWaitForResult();
    }

}
