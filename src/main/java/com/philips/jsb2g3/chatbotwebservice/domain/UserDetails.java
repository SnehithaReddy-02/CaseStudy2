/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userdetails")
public class UserDetails {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  int id;

  @Column(name="Name")
  String name;

  @Column(name="Contact_No")
  int contactNo;

  @Column(name="Email")
  String email;

  @Column(name="Hospital_Address")
  String hospitalAddress;

  public UserDetails() {

  }

  public UserDetails(String name, int contactNo, String email, String hospitalAddress) {
    super();
    this.name = name;
    this.contactNo = contactNo;
    this.email = email;
    this.hospitalAddress = hospitalAddress;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getContactNo() {
    return contactNo;
  }

  public void setContactNo(int contactNo) {
    this.contactNo = contactNo;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHospitalAddress() {
    return hospitalAddress;
  }

  public void setHospitalAddress(String hospitalAddress) {
    this.hospitalAddress = hospitalAddress;
  }

}