/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="stageonequerytable")
public class StageOneQuery {

  @Id
  @Column(name="id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  int id;

  @Column(name="serialno")
  int sno;

  @Column(name="question")
  String question;

  @Column(name="selector")
  Boolean selector;

  @JsonIgnore
  @OneToMany(mappedBy="stageOneQuery",fetch=FetchType.EAGER)
  List<StageTwoQuery> queryList = new ArrayList<>();

  public StageOneQuery()
  {

  }

  public StageOneQuery(int sno, String question) {
    super();


    this.sno=sno;
    this.question = question;
    this.selector = false;
  }


  public List<StageTwoQuery> getQueryList() {
    return queryList;
  }

  public void setQueryList(List<StageTwoQuery> queryList) {
    this.queryList = queryList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Boolean getSelector() {
    return selector;
  }

  public void setSelector(Boolean selector) {
    this.selector = selector;
  }

  public int getSno() {
    return sno;
  }

  public void setSno(int sno) {
    this.sno = sno;
  }



}
