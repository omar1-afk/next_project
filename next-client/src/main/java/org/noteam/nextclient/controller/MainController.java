package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Circle imageClipCircle;
    @FXML private Rectangle fleetsBG, dashboardBG, driversBG, shipmentsBG, settingsBG;
    @FXML private Label dashTabLabel, fleetsTabLabel, shipmentsTabLabel, driversTabLabel, settingsTabLabel;
    @FXML private SVGPath dashboardIcon, fleetsIcon, shipmentsIcon, driversIcon, settingsIcon;
    @FXML private StackPane viewPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // تحميل صورة المستخدم داخل الدائرة
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/noteam/nextclient/assets/105.png")));
        imageClipCircle.setFill(new ImagePattern(image));

        // فتح واجهة الطلبات تلقائياً عند بدء التطبيق
        showOrders();
        // تفعيل تاب Dashboard
        makeTabActive(dashboardBG, dashTabLabel);
        dashboardIcon.setStroke(Color.WHITE);
    }

    // ------------------ Tabs interaction ------------------
    protected void makeTabActive(Rectangle tabBG, Label tabLabel){
        tabBG.setFill(Color.rgb(36,30,30));
        tabLabel.setTextFill(Color.WHITE);
    }

    protected void deactivateAllTabs(){
        dashboardBG.setFill(Color.rgb(217,217,217));
        dashTabLabel.setTextFill(Color.rgb(36,30,30));
        dashboardIcon.setStroke(Color.rgb(36,30,30));

        fleetsBG.setFill(Color.rgb(217,217,217));
        fleetsTabLabel.setTextFill(Color.rgb(36,30,30));
        fleetsIcon.setFill(Color.rgb(36,30,30));

        shipmentsBG.setFill(Color.rgb(217,217,217));
        shipmentsTabLabel.setTextFill(Color.rgb(36,30,30));
        shipmentsIcon.setStroke(Color.rgb(36,30,30));

        driversBG.setFill(Color.rgb(217,217,217));
        driversTabLabel.setTextFill(Color.rgb(36,30,30));
        driversIcon.setFill(Color.rgb(36,30,30));

        settingsBG.setFill(Color.rgb(217,217,217));
        settingsTabLabel.setTextFill(Color.rgb(36,30,30));
        settingsIcon.setFill(Color.rgb(36,30,30));
    }

    protected boolean isTabActive(Rectangle tabBG){
        return tabBG.getFill().equals(Color.rgb(36, 30, 30));
    }

    // ------------------ Tab Mouse Events ------------------
    @FXML public void onDashboardMouseEnter(){ if(!isTabActive(dashboardBG)) dashboardBG.setFill(Color.rgb(193,191,191)); }
    @FXML public void onDashboardMouseExit(){ if(!isTabActive(dashboardBG)) dashboardBG.setFill(Color.rgb(217,217,217)); }
    @FXML public void onDashboardClick(){
        if(!isTabActive(dashboardBG)){
            deactivateAllTabs();
            makeTabActive(dashboardBG, dashTabLabel);
            dashboardIcon.setStroke(Color.WHITE);
            showOrders();
        }
    }

    @FXML public void onFleetsMouseEnter(MouseEvent e){ if(!isTabActive(fleetsBG)) fleetsBG.setFill(Color.rgb(193,191,191)); }
    @FXML public void onFleetsMouseExit(MouseEvent e){ if(!isTabActive(fleetsBG)) fleetsBG.setFill(Color.rgb(217,217,217)); }
    @FXML public void onFleetsClick(){
        if(!isTabActive(fleetsBG)){
            deactivateAllTabs();
            makeTabActive(fleetsBG, fleetsTabLabel);
            fleetsIcon.setFill(Color.WHITE);
            showFleets();
        }
    }

    @FXML public void onDriversMouseEnter(MouseEvent e){ if(!isTabActive(driversBG)) driversBG.setFill(Color.rgb(193,191,191)); }
    @FXML public void onDriversMouseExit(MouseEvent e){ if(!isTabActive(driversBG)) driversBG.setFill(Color.rgb(217,217,217)); }
    @FXML public void onDriversClick(){
        if(!isTabActive(driversBG)){
            deactivateAllTabs();
            makeTabActive(driversBG, driversTabLabel);
            driversIcon.setFill(Color.WHITE);
            showDrivers();
        }
    }

    @FXML public void onShipmentsMouseEnter(MouseEvent e){ if(!isTabActive(shipmentsBG)) shipmentsBG.setFill(Color.rgb(193,191,191)); }
    @FXML public void onShipmentsMouseExit(MouseEvent e){ if(!isTabActive(shipmentsBG)) shipmentsBG.setFill(Color.rgb(217,217,217)); }
    @FXML public void onShipmentsClick(){
        if(!isTabActive(shipmentsBG)){
            deactivateAllTabs();
            makeTabActive(shipmentsBG, shipmentsTabLabel);
            shipmentsIcon.setStroke(Color.WHITE);
            showShipments();
        }
    }

    @FXML public void onSettingsMouseEnter(MouseEvent e){ if(!isTabActive(settingsBG)) settingsBG.setFill(Color.rgb(193,191,191)); }
    @FXML public void onSettingsMouseExit(MouseEvent e){ if(!isTabActive(settingsBG)) settingsBG.setFill(Color.rgb(217,217,217)); }
    @FXML public void onSettingsClick(){
        if(!isTabActive(settingsBG)){
            deactivateAllTabs();
            makeTabActive(settingsBG, settingsTabLabel);
            settingsIcon.setFill(Color.WHITE);
        }
    }

    // ------------------ Show views ------------------
    private void showOrders(){
        loadView("/org/noteam/nextclient/scene/orders-view.fxml");
    }

    private void showFleets(){
        loadView("/org/noteam/nextclient/scene/fleets-view.fxml");
    }

    private void showDrivers(){
        loadView("/org/noteam/nextclient/scene/drivers-view.fxml");
    }

    private void showShipments(){
        loadView("/org/noteam/nextclient/scene/shipments-view.fxml");
    }

    private void loadView(String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            viewPane.getChildren().clear();
            viewPane.getChildren().add(root);
        } catch (IOException e){
            throw new RuntimeException("Failed to load FXML: " + fxmlPath, e);
        }
    }
}
