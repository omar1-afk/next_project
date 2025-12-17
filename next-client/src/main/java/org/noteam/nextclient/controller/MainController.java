package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
  @FXML
  private Circle imageClipCircle;
  @FXML
  private Rectangle fleetsBG;
  @FXML
  private Rectangle dashboardBG;
  @FXML
  private Rectangle driversBG;
  @FXML
  private Rectangle shipmentsBG;
  @FXML
  private Rectangle settingsBG;
  @FXML
  private Label dashTabLabel;
  @FXML
  private Label fleetsTabLabel;
  @FXML
  private Label shipmentsTabLabel;
  @FXML
  private Label driversTabLabel;
  @FXML
  private Label settingsTabLabel;
  @FXML
  private SVGPath dashboardIcon;
  @FXML
  private SVGPath fleetsIcon;
  @FXML
  private SVGPath shipmentsIcon;
  @FXML
  private SVGPath driversIcon;
  @FXML
  private SVGPath settingsIcon;
  @FXML
  private StackPane viewPane;

  @FXML
  public void onDashboardMouseEnter() {
    if (!isTabActive(dashboardBG)) {
      dashboardBG.setFill(Color.rgb(193, 191, 191));
    }
  }

  @FXML
  public void onDashboardClick() {
    if (!isTabActive(dashboardBG)) {
      deactivateAllTabs();
      makeTabActive(dashboardBG, dashTabLabel);
      dashboardIcon.setStroke(Color.WHITE);
      showOrders();
    }
  }

  @FXML
  public void onDashboardMouseExit() {
    if (!isTabActive(dashboardBG)) {
      dashboardBG.setFill(Color.rgb(217, 217, 217));
    }
  }

  @FXML
  public void onFleetsMouseEnter(MouseEvent event) {
    if (!isTabActive(fleetsBG)) {
      fleetsBG.setFill(Color.rgb(193, 191, 191));
    }
  }

  @FXML
  public void onFleetsMouseExit(MouseEvent event) {
    if (!isTabActive(fleetsBG)) {
      fleetsBG.setFill(Color.rgb(217, 217, 217));
    }
  }

  @FXML
  public void onFleetsClick() {
    if (!isTabActive(fleetsBG)) {
      deactivateAllTabs();
      makeTabActive(fleetsBG, fleetsTabLabel);
      fleetsIcon.setFill(Color.WHITE);
      showFleets();
    }
  }

  @FXML
  public void onDriversMouseEnter(MouseEvent event) {
    if (!isTabActive(driversBG)) {
      driversBG.setFill(Color.rgb(193, 191, 191));
    }
  }

  @FXML
  public void onDriversMouseExit(MouseEvent event) {
    if (!isTabActive(driversBG)) {
      driversBG.setFill(Color.rgb(217, 217, 217));
    }
  }

  @FXML
  public void onDriversClick() {
    if (!isTabActive(driversBG)) {
      deactivateAllTabs();
      makeTabActive(driversBG, driversTabLabel);
      driversIcon.setFill(Color.WHITE);
      showDrivers();
    }
  }

  @FXML
  public void onShipmentsMouseEnter(MouseEvent event) {
    if (!isTabActive(shipmentsBG)) {
      shipmentsBG.setFill(Color.rgb(193, 191, 191));
    }
  }

  @FXML
  public void onShipmentsMouseExit(MouseEvent event) {
    if (!isTabActive(shipmentsBG)) {
      shipmentsBG.setFill(Color.rgb(217, 217, 217));
    }
  }

  @FXML
  public void onShipmentsClick() {
    if (!isTabActive(shipmentsBG)) {
      deactivateAllTabs();
      makeTabActive(shipmentsBG, shipmentsTabLabel);
      shipmentsIcon.setStroke(Color.WHITE);
      showShipments();
    }
  }

  @FXML
  public void onSettingsMouseEnter(MouseEvent event) {
    if (!isTabActive(settingsBG)) {
      settingsBG.setFill(Color.rgb(193, 191, 191));
    }
  }

  @FXML
  public void onSettingsMouseExit(MouseEvent event) {
    if (!isTabActive(settingsBG)) {
      settingsBG.setFill(Color.rgb(217, 217, 217));
    }
  }

  @FXML
  public void onSettingsClick() {
    if (!isTabActive(settingsBG)) {
      deactivateAllTabs();
      makeTabActive(settingsBG, settingsTabLabel);
      settingsIcon.setFill(Color.WHITE);

      // 3. Get the controller instance from the loader

      // 4. Now this will work
    }
  }

  protected void makeTabActive(Rectangle tabBG, Label tabLabel) {
    tabBG.setFill(Color.rgb(36, 30, 30));
    tabLabel.setTextFill(Color.WHITE);
  }

  protected void deactivateAllTabs() {
    dashboardBG.setFill(Color.rgb(217, 217, 217));
    dashTabLabel.setTextFill(Color.rgb(36, 30, 30));
    dashboardIcon.setStroke(Color.rgb(36, 30, 30));
    fleetsBG.setFill(Color.rgb(217, 217, 217));
    fleetsTabLabel.setTextFill(Color.rgb(36, 30, 30));
    fleetsIcon.setFill(Color.rgb(36, 30, 30));
    shipmentsBG.setFill(Color.rgb(217, 217, 217));
    shipmentsTabLabel.setTextFill(Color.rgb(36, 30, 30));
    shipmentsIcon.setStroke(Color.rgb(36, 30, 30));
    driversBG.setFill(Color.rgb(217, 217, 217));
    driversTabLabel.setTextFill(Color.rgb(36, 30, 30));
    driversIcon.setFill(Color.rgb(36, 30, 30));
    settingsBG.setFill(Color.rgb(217, 217, 217));
    settingsTabLabel.setTextFill(Color.rgb(36, 30, 30));
    settingsIcon.setFill(Color.rgb(36, 30, 30));
  }

  protected boolean isTabActive(Rectangle tabBG) {
    return tabBG.getFill().equals(Color.rgb(36, 30, 30));
  }

  private void showOrders() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/orders-view.fxml"));
      Parent root = loader.load();
      OrderController orderController = loader.getController();
      viewPane.getChildren().removeAll();
      viewPane.getChildren().add(orderController.getOrderPane());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void showShipments() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/shipments-view.fxml"));
      Parent root = loader.load();
      ShipmentController shipmentController = loader.getController();
      viewPane.getChildren().removeAll();
      viewPane.getChildren().add(shipmentController.getShipmentPane());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void showFleets() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/fleets-view.fxml"));
      Parent root = loader.load();
      FleetController fleetController = loader.getController();
      viewPane.getChildren().removeAll();
      viewPane.getChildren().add(fleetController.getFleetPane());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void showDrivers() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/noteam/nextclient/scene/drivers-view.fxml"));
      Parent root = loader.load();
      DriverController driverController = loader.getController();
      viewPane.getChildren().removeAll();
      viewPane.getChildren().add(driverController.getDriverPane());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Image image = new Image(
        Objects.requireNonNull(getClass().getResourceAsStream("/org/noteam/nextclient/assets/105.png")));
    imageClipCircle.setFill(new ImagePattern(image));
    showOrders();
    ;
  }
}
