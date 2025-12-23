package org.noteam.nextclient.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.noteam.nextclient.dto.Order;
import org.noteam.nextclient.dto.State;
import org.noteam.nextclient.models.Country;
import org.noteam.nextclient.models.Receiver;
import org.noteam.nextclient.models.Sender;
import org.noteam.nextclient.utils.SqlUtil;

import java.time.LocalDate;
import java.util.List;

public class CreateOrderPopupController{
  @FXML
  BorderPane orderDetailsPane;
  @FXML
  TextField countryFeild;
  @FXML
  ContextMenu countryMenu;
  @FXML
  TextField cityFeild;
  @FXML
  ContextMenu cityMenu;
  @FXML
  TextField regionFeild;
  @FXML
  TextField addressFeild;
  @FXML
  TextField senderNameFeild;
  @FXML
  TextField senderEmailFeild;
  @FXML
  TextField senderScnFeild;
  @FXML
  TextField senderPhoneFeild;
  @FXML
  TextField receiverNameFeild;
  @FXML
  TextField receiverEmailFeild;
  @FXML
  TextField receiverScnFeild;
  @FXML
  TextField receiverPhoneFeild;
  @FXML
  TextField crnFeild;
  @FXML
  TextField boxesNumberFeild;
  @FXML
  TextField priceFeild;
  @FXML
  TextField weightFeild;
  @FXML
  CheckBox isFlammable;
  @FXML
  CheckBox isBreakable;
  @FXML
  Button cancelOrderBtn;
  @FXML
  Button createOrderBtn;
  public BorderPane getOrderDetailsPopup() {
    return orderDetailsPane;
  }
  @FXML
  public void onCancelOrderBtnClick(MouseEvent event){
    Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
    currentStage.close();
  }
  @FXML
  public void  onCreateOrderBtn(MouseEvent event){
    Sender sender = new Sender(null,senderNameFeild.getText(),senderScnFeild.getText(),senderPhoneFeild.getText(),crnFeild.getText(),senderEmailFeild.getText());
    SqlUtil.createSender(sender);
    Receiver receiver = new Receiver(null,receiverNameFeild.getText(),senderScnFeild.getText(),senderPhoneFeild.getText(),senderEmailFeild.getText());
    SqlUtil.createReceiver(receiver);
    List<Country> countries=SqlUtil.getAllCountries();
    Country country = countries.getFirst();
    Order newOrder = new Order(0,country.getCities().get(0).getName()
      ,3
      ,regionFeild.getText()
      ,addressFeild.getText()
      ,isFlammable.isSelected()
      ,isBreakable.isSelected()
      ,Integer.parseInt(priceFeild.getText())
      , State.PICKED
      ,Integer.parseInt(weightFeild.getText())
      ,0,
      receiver.getEmail()
      ,sender.getEmail(),Integer.parseInt(boxesNumberFeild.getText()), LocalDate.now());
    Alert alert;
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    if(SqlUtil.createNewOrder(newOrder)){
      alert=new Alert(Alert.AlertType.INFORMATION,"Order Created Successully!");
      alert.setContentText("");
      alert.showAndWait();
      currentStage.close();
    }else{
      alert=new Alert(Alert.AlertType.WARNING,"Error: Order is not created");
      currentStage.close();
    }

  }
}