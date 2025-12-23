package org.noteam.nextclient.models;

public class Sender {


  private Integer senderId;

  private String name;

  private String socialSecurityNumber;

  private String phone;

  private String commercialRegisterNumber;

  private String email;

  public Sender(Integer senderId, String name, String socialSecurityNumber, String phone, String commercialRegisterNumber, String email) {
    this.senderId = senderId;
    this.name = name;
    this.socialSecurityNumber = socialSecurityNumber;
    this.phone = phone;
    this.commercialRegisterNumber = commercialRegisterNumber;
    this.email = email;
  }

  public Integer getSenderId() {
    return senderId;
  }

  // public void setReceiverId(Integer receiverId) { (we don't need to set the
  // sender id)
  // this.receiverId = receiverId;
  // }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  public void setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCommercialRegisterNumber() {
    return commercialRegisterNumber;
  }

  public void setCommercialRegisterNumber(String commercialRegisterNumber) {
    this.commercialRegisterNumber = commercialRegisterNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  
}
