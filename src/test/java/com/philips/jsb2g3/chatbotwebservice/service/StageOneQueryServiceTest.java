/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import com.philips.jsb2g3.chatbotwebservice.dal.StageOneQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;

public class StageOneQueryServiceTest {


  @Test
  public void addNewQueryTest() {

    final StageOneQueryServiceImplementation serviceImplementation = new StageOneQueryServiceImplementation();

    final StageOneQueryDAO queryDAO = Mockito.mock(StageOneQueryDAO.class);
    serviceImplementation.setDao(queryDAO);
    final List<StageOneQuery> qList=setStageOneQueries();
    Mockito.when(queryDAO.save(qList.get(0))).thenReturn(qList.get(0));
    final StageOneQuery query=serviceImplementation.addNewQuery(qList.get(0));
    assertEquals(qList.get(0), query);
  }


  private List<StageOneQuery> setStageOneQueries()
  {
    final StageOneQuery q=new StageOneQuery(1,"Press 1 to select monitors of your choice");
    q.setId(1);

    final StageOneQuery q1=new StageOneQuery(2,"Press 2 to search for monitors");
    q1.setId(2);

    final StageOneQuery q2=new StageOneQuery(3,"Press 3, if you have any query");
    q2.setId(3);


    final List<StageOneQuery> list=new ArrayList<>();
    list.add(q);
    list.add(q1);
    list.add(q2);


    return list;
  }


  @Test
  public void askQueryTest() {

    final StageOneQueryServiceImplementation serviceImplementation = new StageOneQueryServiceImplementation();
    final StageOneQueryDAO queryDAO = Mockito.mock(StageOneQueryDAO.class);
    serviceImplementation.setDao(queryDAO);


    final List<StageOneQuery> qList=setStageOneQueries();
    Mockito.when(queryDAO.findAll()).thenReturn(qList);
    final List<StageOneQuery> list=serviceImplementation.askQuery();
    assertEquals(list, qList);


  }

  @Test
  public void  findQueryByIdTest() {

    final StageOneQueryServiceImplementation serviceImplementation = new StageOneQueryServiceImplementation();

    final StageOneQueryDAO queryDAO = Mockito.mock(StageOneQueryDAO.class);
    serviceImplementation.setDao(queryDAO);
    final List<StageOneQuery> qList=setStageOneQueries();
    Mockito.when(queryDAO.findByID(1)).thenReturn(qList.get(0));
    final StageOneQuery query=serviceImplementation.findQueryById(1);
    assertEquals(query, qList.get(0));

  }

  @Test
  public void findQueryBySelectorTest() {

    final StageOneQueryServiceImplementation serviceImplementation = new StageOneQueryServiceImplementation();

    final StageOneQueryDAO queryDAO = Mockito.mock(StageOneQueryDAO.class);
    serviceImplementation.setDao(queryDAO);
    final List<StageOneQuery> qList=setStageOneQueries();
    Mockito.when(queryDAO.findBySelector(false)).thenReturn(qList.get(0).getId());
    final int queryid=serviceImplementation.findQueryBySelector(false);
    assertEquals(queryid, qList.get(0).getId());

  }


  @Test
  public void  findQueryBySerialNoTest() {


    final StageOneQueryServiceImplementation serviceImplementation = new StageOneQueryServiceImplementation();

    final StageOneQueryDAO queryDAO = Mockito.mock(StageOneQueryDAO.class);
    serviceImplementation.setDao(queryDAO);
    final List<StageOneQuery> qList=setStageOneQueries();

    Mockito.when(queryDAO.findBySerialNo(1)).thenReturn(qList.get(0).getId());

    final int idInteger=serviceImplementation.findQueryBySerialNo(1);
    assertEquals(idInteger, qList.get(0).getId());

  }






  @Test
  public void setSelectorTest() {


    final StageOneQueryServiceImplementation serviceImplementation = new StageOneQueryServiceImplementation();

    final StageOneQueryDAO queryDAO = Mockito.mock(StageOneQueryDAO.class);
    serviceImplementation.setDao(queryDAO);
    final List<StageOneQuery> qList=setStageOneQueries();
    Mockito.when(queryDAO.findBySerialNo(1)).thenReturn(qList.get(0).getId());
    doNothing().when(queryDAO).setSelector(qList.get(0).getId(), true);
    serviceImplementation.setSelector(1, 3);



  }


  @Test
  public void resetSelectorsTest() {


    final StageOneQueryServiceImplementation serviceImplementation = new StageOneQueryServiceImplementation();
    final StageOneQueryDAO queryDAO = Mockito.mock(StageOneQueryDAO.class);
    serviceImplementation.setDao(queryDAO);
    final List<StageOneQuery> qList=setStageOneQueries();
    Mockito.when(queryDAO.findAll()).thenReturn(qList);
    serviceImplementation.resetSelectors();
    assertEquals(false, qList.get(0).getSelector());

  }



}
