package org.noteam.nextclient.models;



public class Receiver {

  private Integer receiverId;

  private String name;

  private String socialSecurityNumber;

  private String phone;

  private String email;

  // Constructor
  public Receiver() {
  }

  public Integer getReceiverId() {
    return receiverId;
  }

  // public void setReceiverId(Integer receiverId) { (we don't need to set the
  // receiver id)
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
