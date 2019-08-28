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
@Table(name = "monitor")
public class Monitor {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  int id;

  @Column(name="name")
  String name;


  @Column(name="brand")
  String brand;

  @Column(name="size")
  String size;

  @Column(name="Type")
  String type;



  public Monitor() {

  }

  public Monitor(String name, String brand, String size, String type) {
    super();
    this.name = name;
    this.brand = brand;
    this.size = size;
    this.type=type;
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

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


}