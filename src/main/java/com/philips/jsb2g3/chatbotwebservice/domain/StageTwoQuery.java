/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */

package com.philips.jsb2g3.chatbotwebservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="stagetwoquerytable")
public class StageTwoQuery {

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
  @ManyToOne
  @JoinColumn(name="one_id")
  StageOneQuery stageOneQuery;

  public StageTwoQuery()
  {

  }

  public StageTwoQuery(int sno, String question) {
    super();

    this.sno = sno;
    this.question = question;
    this.selector=false;



  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSno() {
    return sno;
  }

  public void setSno(int sno) {
    this.sno = sno;
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


  public StageOneQuery getStageOneQuery() {
    return stageOneQuery;
  }

  public void setStageOneQuery(StageOneQuery stageOneQuery) {
    this.stageOneQuery = stageOneQuery;
  }



}
