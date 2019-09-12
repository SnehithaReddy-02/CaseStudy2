/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.mockito.Mockito;
import com.philips.jsb2g3.chatbotwebservice.dal.StageOneQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.dal.StageTwoQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;

public class StageTwoQueryServiceTest {


  @Test
  public void  askQueryTest() {
    final StageTwoQueryServiceImplementation serviceImplementation = new StageTwoQueryServiceImplementation();

    final StageOneQueryDAO queryoneDAO = Mockito.mock(StageOneQueryDAO.class);
    final StageTwoQueryDAO querytwoDAO = Mockito.mock(StageTwoQueryDAO.class);
    serviceImplementation.setDaoone(queryoneDAO);
    serviceImplementation.setDao(querytwoDAO);



    final List<StageTwoQuery> listOne=setStageTwoQueries(2, queryoneDAO.findByID(2));


    Mockito.when(queryoneDAO.findBySerialNo(1)).thenReturn(1);
    Mockito.when(querytwoDAO.findAll(1)).thenReturn(listOne);

    final List<StageTwoQuery> queries=serviceImplementation.askQuery(1);
    assertEquals(queries, listOne);

  }



  @Test
  public void addNewQueryTest()
  {
    final StageTwoQueryServiceImplementation serviceImplementation = new StageTwoQueryServiceImplementation();

    final StageOneQueryDAO queryoneDAO = Mockito.mock(StageOneQueryDAO.class);
    final StageTwoQueryDAO querytwoDAO = Mockito.mock(StageTwoQueryDAO.class);
    serviceImplementation.setDaoone(queryoneDAO);
    serviceImplementation.setDao(querytwoDAO);
    final List<StageTwoQuery> listOne=setStageTwoQueries(2, queryoneDAO.findByID(2));
    Mockito.when(querytwoDAO.save(listOne.get(0), 2)).thenReturn(listOne.get(0));

    serviceImplementation.addNewQuery(listOne.get(0), 2);

  }

  @Test
  public void findQueryByIdTest()
  {

    final StageTwoQueryServiceImplementation serviceImplementation = new StageTwoQueryServiceImplementation();

    final StageOneQueryDAO queryoneDAO = Mockito.mock(StageOneQueryDAO.class);
    final StageTwoQueryDAO querytwoDAO = Mockito.mock(StageTwoQueryDAO.class);
    serviceImplementation.setDaoone(queryoneDAO);
    serviceImplementation.setDao(querytwoDAO);
    final List<StageTwoQuery> listOne=setStageTwoQueries(1, queryoneDAO.findByID(1));
    Mockito.when(querytwoDAO.findByID(2)).thenReturn(listOne.get(0));

    final StageTwoQuery query= serviceImplementation.findQueryById(2);
    assertEquals(query, listOne.get(0));

  }

  @Test
  public void findQueryBySelectorTest() {

    final StageTwoQueryServiceImplementation serviceImplementation = new StageTwoQueryServiceImplementation();

    final StageOneQueryDAO queryoneDAO = Mockito.mock(StageOneQueryDAO.class);
    final StageTwoQueryDAO querytwoDAO = Mockito.mock(StageTwoQueryDAO.class);
    serviceImplementation.setDaoone(queryoneDAO);
    serviceImplementation.setDao(querytwoDAO);

    Mockito.when(querytwoDAO.findBySelector(false, 1)).thenReturn(2);



  }

  @Test
  public void resetSelectorsTest()
  {
    final StageTwoQueryServiceImplementation serviceImplementation = new StageTwoQueryServiceImplementation();

    final StageOneQueryDAO queryoneDAO = Mockito.mock(StageOneQueryDAO.class);
    final StageTwoQueryDAO querytwoDAO = Mockito.mock(StageTwoQueryDAO.class);
    serviceImplementation.setDaoone(queryoneDAO);
    serviceImplementation.setDao(querytwoDAO);

    final List<StageTwoQuery> listOne=setStageTwoQueries(2, queryoneDAO.findByID(2));
    Mockito.when(querytwoDAO.findAllStageTwoQueries()).thenReturn(listOne);
    serviceImplementation.resetSelectors();


  }

  @Test
  public void setQuerySelectorTest()
  {
    final StageTwoQueryServiceImplementation serviceImplementation = new StageTwoQueryServiceImplementation();

    final StageOneQueryDAO queryoneDAO = Mockito.mock(StageOneQueryDAO.class);
    final StageTwoQueryDAO querytwoDAO = Mockito.mock(StageTwoQueryDAO.class);
    final EntityManager eManager=Mockito.mock(EntityManager.class);


    serviceImplementation.setDaoone(queryoneDAO);
    serviceImplementation.setDao(querytwoDAO);

    final List<StageTwoQuery> listOne=setStageTwoQueries(1, queryoneDAO.findByID(1));

    Mockito.when(eManager.find(StageTwoQuery.class, 1)).thenReturn(listOne.get(0));

    Mockito.when(querytwoDAO.findBySerialNo(1, 1)).thenReturn(1);
    final List<Integer> integers=new ArrayList<>();
    integers.add(1);



  }


  @Test
  public void getQuerySerialNoByIDTest() {

    final StageTwoQueryServiceImplementation serviceImplementation = new StageTwoQueryServiceImplementation();

    final StageOneQueryDAO queryoneDAO = Mockito.mock(StageOneQueryDAO.class);
    final StageTwoQueryDAO querytwoDAO = Mockito.mock(StageTwoQueryDAO.class);

    serviceImplementation.setDaoone(queryoneDAO);
    serviceImplementation.setDao(querytwoDAO);
    final List<StageTwoQuery> listOne=setStageTwoQueries(1, queryoneDAO.findByID(1));

    Mockito.when(querytwoDAO.findByID(2)).thenReturn(listOne.get(0));
    final int i=serviceImplementation.getQuerySerialNoByID(2);
    assertEquals(i, listOne.get(0).getSno());



  }




  private List<StageTwoQuery> setStageTwoQueries(int opt,StageOneQuery stageOneQuery) {

    final List<StageTwoQuery> qList=new ArrayList<>();
    switch(opt)
    {
      case 1:
        final StageTwoQuery query1=new StageTwoQuery(1,"Press 1 to select monitors by Brand");
        query1.setId(2);
        query1.setStageOneQuery(stageOneQuery);


        final StageTwoQuery query2=new StageTwoQuery(2,"Press 2 to select monitors by Screen-Type");
        query2.setId(3);
        query2.setStageOneQuery(stageOneQuery);

        final StageTwoQuery query3=new StageTwoQuery(3,"Press 3 to select monitors by Screen-Size");
        query3.setId(4);
        query3.setStageOneQuery(stageOneQuery);

        final StageTwoQuery query4=new StageTwoQuery(4,"Press 4 to see all the available monitors");
        query4.setId(5);
        query4.setStageOneQuery(stageOneQuery);

        qList.add(query1);
        qList.add(query2);
        qList.add(query3);
        qList.add(query4);


        break;
      case 2:
        final StageTwoQuery query=new StageTwoQuery(1,"Enter the monitor's name");
        query.setId(1);
        query.setStageOneQuery(stageOneQuery);

        qList.add(query);
        break;

      case 3:
        final StageTwoQuery query5=new StageTwoQuery(1,"Enter the following details");
        query5.setId(6);
        query5.setStageOneQuery(stageOneQuery);

        qList.add(query5);
        break;
      default:
        break;
    }
    return qList;

  }




}
