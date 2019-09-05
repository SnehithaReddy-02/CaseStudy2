/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.web;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;
import com.philips.jsb2g3.chatbotwebservice.service.MonitorService;
import com.philips.jsb2g3.chatbotwebservice.service.StageOneQueryService;
import com.philips.jsb2g3.chatbotwebservice.service.StageTwoQueryService;

public class ChatbotControllerTest {

  @Mock
  private StageOneQueryService service;

  private MockMvc mvc;

  @InjectMocks
  private ChatbotController controller;


  @Before
  public void init(){
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders
        .standaloneSetup(controller)
        .build();
  }




  @Test
  public void getAllStageOneQuestionsWhenNotNull() throws FileNotFoundException {
    final ChatbotController qc = new ChatbotController();
    final StageOneQueryService qr = Mockito.mock(StageOneQueryService.class);
    final List<StageOneQuery> list=setStageOneQueries();
    Mockito.when(qr.askQuery()).thenReturn(list);
    qc.setService(qr);

    final ResponseEntity<List<StageOneQuery>> output = qc.displayStartUpMenu();
    if (output.getStatusCode()==HttpStatus.OK){
      assertEquals(output.getBody(), list);
    }



  }




  @Test
  public void getAllStageOneQuestionsWhenNull() throws FileNotFoundException {
    final ChatbotController qc = new ChatbotController();
    final StageOneQueryService qr = Mockito.mock(StageOneQueryService.class);

    final List<StageOneQuery> list=null;


    Mockito.when(qr.askQuery()).thenReturn(list);
    qc.setService(qr);

    final ResponseEntity<List<StageOneQuery>> output = qc.displayStartUpMenu();
    if (output.getStatusCode()==HttpStatus.NOT_FOUND){
      assertEquals(output.getBody(), null);
    }


  }


  @Test
  public void displayFilterMenuForValidOption()
  {
    final ChatbotController qc = new ChatbotController();
    final StageTwoQueryService qr = Mockito.mock(StageTwoQueryService.class);
    final StageOneQueryService sone = Mockito.mock(StageOneQueryService.class);
    qc.setServicetwo(qr);
    qc.setService(sone);
    Mockito.when(sone.askQuery()).thenReturn(setStageOneQueries());

    final List<StageTwoQuery> listOne=setStageTwoQueries(2, sone.findQueryById(2));

    final List<StageTwoQuery> listTwo=setStageTwoQueries(1, sone.findQueryById(1));


    final List<StageTwoQuery> listThree=setStageTwoQueries(3, sone.findQueryById(3));


    Mockito.when(qr.askQuery(2)).thenReturn(listOne);
    final ResponseEntity<List<StageTwoQuery>> output = qc.displayFilterMenu(2);
    if (output.getStatusCode() == HttpStatus.OK){
      assertEquals(output.getBody(), listOne);
    }else {
      assertEquals(output.getBody(), null);
    }



    Mockito.when(qr.askQuery(1)).thenReturn(listTwo);
    final ResponseEntity<List<StageTwoQuery>> outputone = qc.displayFilterMenu(1);
    if (outputone.getStatusCode() == HttpStatus.OK){
      assertEquals(outputone.getBody(), listTwo);
    }else {
      assertEquals(outputone.getBody(), null);
    }


    Mockito.when(qr.askQuery(3)).thenReturn(listThree);
    final ResponseEntity<List<StageTwoQuery>> outputtwo = qc.displayFilterMenu(3);
    if (outputtwo.getStatusCode() == HttpStatus.OK){
      assertEquals(outputtwo.getBody(), listThree);
    }else {
      assertEquals(outputtwo.getBody(), null);
    }
  }



  @Test
  public void displayFilterMenuForInValidOption()
  {

    final ChatbotController qc = new ChatbotController();
    final StageTwoQueryService qr = Mockito.mock(StageTwoQueryService.class);
    final StageOneQueryService sone = Mockito.mock(StageOneQueryService.class);
    qc.setServicetwo(qr);
    qc.setService(sone);


    Mockito.when(sone.askQuery()).thenReturn(setStageOneQueries());

    final StageOneQuery stageOneQuery=sone.findQueryById(1);
    final List<StageTwoQuery> listOne=setStageTwoQueries(-1, stageOneQuery);

    Mockito.when(qr.askQuery(-1)).thenReturn(listOne);
    final ResponseEntity<List<StageTwoQuery>> output = qc.displayFilterMenu(-1);
    if (output.getStatusCode() == HttpStatus.OK){
      assertEquals(output.getBody(), listOne);
    }else {
      assertEquals(output.getBody(), null);    }


    final List<StageTwoQuery> listTwo=setStageTwoQueries(-1, stageOneQuery);
    Mockito.when(qr.askQuery(7)).thenReturn(listTwo);
    final ResponseEntity<List<StageTwoQuery>> outputone = qc.displayFilterMenu(7);
    if (outputone.getStatusCode() == HttpStatus.OK){
      assertEquals(outputone.getBody(), listTwo);
    }else {
      assertEquals(outputone.getBody(), null);
    }

    final List<StageTwoQuery> listThree=setStageTwoQueries(-1, stageOneQuery);
    Mockito.when(qr.askQuery(9)).thenReturn(listThree);
    final ResponseEntity<List<StageTwoQuery>> outputtwo = qc.displayFilterMenu(9);
    if (outputtwo.getStatusCode() == HttpStatus.OK){
      assertEquals(outputtwo.getBody(), listThree);
    }else {
      assertEquals(outputtwo.getBody(), null);
    }

  }

  @Test
  public void displayFilterMenuForQuestionSetEqualsNull()
  {
    final ChatbotController qc = new ChatbotController();
    final StageTwoQueryService qr = Mockito.mock(StageTwoQueryService.class);
    final StageOneQueryService sone = Mockito.mock(StageOneQueryService.class);
    qc.setServicetwo(qr);
    qc.setService(sone);

    Mockito.when(sone.askQuery()).thenReturn(setStageOneQueries());
    final List<StageTwoQuery> listOne=null;
    Mockito.when(qr.askQuery(2)).thenReturn(listOne);
    final ResponseEntity<List<StageTwoQuery>> output = qc.displayFilterMenu(2);
    if (output.getStatusCode() == HttpStatus.OK){
      assertEquals(output.getBody(), listOne);
    }else {
      assertEquals(output.getBody(), null);
    }

  }

  @Test
  public void Search_For_Monitor_If_Present_Returns_Monitor_Name_Else_Returns_Null()
  {
    final ChatbotController qc = new ChatbotController();
    final StageTwoQueryService qr = Mockito.mock(StageTwoQueryService.class);
    final StageOneQueryService sone = Mockito.mock(StageOneQueryService.class);
    final MonitorService mService=Mockito.mock(MonitorService.class);

    qc.setServicetwo(qr);
    qc.setService(sone);
    qc.setMonitorService(mService);



    Mockito.when(sone.askQuery()).thenReturn(setStageOneQueries());

    final List<StageTwoQuery> listOne=setStageTwoQueries(2, sone.findQueryById(2));
    final List<StageTwoQuery> listTwo=setStageTwoQueries(1, sone.findQueryById(1));
    final List<StageTwoQuery> listThree=setStageTwoQueries(3, sone.findQueryById(3));



    Mockito.when(qr.askQuery(2)).thenReturn(listOne);
    Mockito.when(qr.askQuery(1)).thenReturn(listTwo);
    Mockito.when(qr.askQuery(3)).thenReturn(listThree);

    final List<Monitor> monitors=setMonitors();

    Mockito.when(mService.findByName("G30E")).thenReturn( monitors.get(0));

    final ResponseEntity<List<String>> output = qc.inputFilterResponses(2,"G30E");
    if (output.getStatusCode() == HttpStatus.OK){
      assertEquals(output.getBody().size(), 1);
    }



    final ResponseEntity<List<String>> outputone = qc.inputFilterResponses(2,"xxx");
    if (outputone.getStatusCode() == HttpStatus.NOT_FOUND){
      assertEquals(outputone.getBody(), null);
    }

  }



  @Test
  public void Show_Filtered_Menu_For_Valid_Options_Entered_Else_Return_Null()
  {
    final ChatbotController qc = new ChatbotController();
    final StageTwoQueryService qr = Mockito.mock(StageTwoQueryService.class);
    final StageOneQueryService sone = Mockito.mock(StageOneQueryService.class);
    final MonitorService mService=Mockito.mock(MonitorService.class);

    qc.setServicetwo(qr);
    qc.setService(sone);
    qc.setMonitorService(mService);
    Mockito.when(sone.askQuery()).thenReturn(setStageOneQueries());
    final List<StageTwoQuery> listOne=setStageTwoQueries(2, sone.findQueryById(2));
    final List<StageTwoQuery> listTwo=setStageTwoQueries(1, sone.findQueryById(1));
    final List<StageTwoQuery> listThree=setStageTwoQueries(3, sone.findQueryById(3));
    Mockito.when(qr.askQuery(2)).thenReturn(listOne);
    Mockito.when(qr.askQuery(1)).thenReturn(listTwo);
    Mockito.when(qr.askQuery(3)).thenReturn(listThree);

    listTwo.get(3).setSelector(true);
    listTwo.get(3).getSno();
    final List<Monitor> monitors=setMonitors();

    Mockito.when(mService.findAll()).thenReturn(monitors);
    final ResponseEntity<List<String>> output1 = qc.inputFilterResponses(1,"4");
    if (output1.getStatusCode() == HttpStatus.OK){
      assertEquals(output1.getBody().size(), mService.findAll().size());
    }



    listTwo.get(0).setSelector(true);
    listTwo.get(3).setSelector(false);
    listTwo.get(0).getSno();
    final List<String> brandList=new ArrayList<>();
    brandList.add("Goldway");
    brandList.add("Efficia");
    Mockito.when(mService.getBrands()).thenReturn(brandList);
    final ResponseEntity<List<String>> output = qc.inputFilterResponses(1,"1");
    if (output.getStatusCode() == HttpStatus.OK){
      assertEquals(output.getBody().size(), mService.getBrands().size());
    }

    listTwo.get(2).setSelector(true);
    listTwo.get(0).setSelector(false);
    listTwo.get(2).getSno();
    final List<String> sizeList=new ArrayList<>();
    sizeList.add("10");
    sizeList.add("15");
    sizeList.add("12");
    Mockito.when(mService.getSizes()).thenReturn(sizeList);
    final ResponseEntity<List<String>> outputone = qc.inputFilterResponses(1,"3");
    if (outputone.getStatusCode() == HttpStatus.OK){
      assertEquals(outputone.getBody().size(), mService.getSizes().size());
    }


    listTwo.get(1).setSelector(true);
    listTwo.get(2).setSelector(false);
    listTwo.get(1).getSno();
    final List<String> typeList=new ArrayList<>();
    typeList.add("Touch");
    typeList.add("Non-Touch");
    Mockito.when(mService.getScreenTypes()).thenReturn(typeList);
    final ResponseEntity<List<String>> outputtwo = qc.inputFilterResponses(1,"2");
    if (outputtwo.getStatusCode() == HttpStatus.OK){
      assertEquals(outputtwo.getBody().size(), mService.getScreenTypes().size());
    }


    final ResponseEntity<List<String>> outputtt = qc.inputFilterResponses(111,"111");
    if (outputtt.getStatusCode() == HttpStatus.BAD_REQUEST){
      assertEquals(outputtt.getBody(), null);
    }
  }

  @Test
  public void input_Three_Responses_And_Return_FilteredMenu_If_Response_IsValid_Else_Return_Null()
  {

    final ChatbotController qc = new ChatbotController();
    final StageTwoQueryService qr = Mockito.mock(StageTwoQueryService.class);
    final StageOneQueryService sone = Mockito.mock(StageOneQueryService.class);
    final MonitorService mService=Mockito.mock(MonitorService.class);

    qc.setServicetwo(qr);
    qc.setService(sone);
    qc.setMonitorService(mService);
    Mockito.when(sone.askQuery()).thenReturn(setStageOneQueries());
    final List<StageTwoQuery> listOne=setStageTwoQueries(2, sone.findQueryById(2));
    final List<StageTwoQuery> listTwo=setStageTwoQueries(1, sone.findQueryById(1));
    final List<StageTwoQuery> listThree=setStageTwoQueries(3, sone.findQueryById(3));
    Mockito.when(qr.askQuery(2)).thenReturn(listOne);
    Mockito.when(qr.askQuery(1)).thenReturn(listTwo);
    Mockito.when(qr.askQuery(3)).thenReturn(listThree);

    final List<String> tyList=new ArrayList<>();
    tyList.add("Non-Touch");
    tyList.add("Touch");

    final List<String> brandList=new ArrayList<>();
    brandList.add("Goldway");
    brandList.add("Efficia");


    final List<String> tList=new ArrayList<>();
    tList.add("Press 1 for Non-Touch screen monitors");
    tList.add("Press 2 for Touch screen monitors");

    listTwo.get(0).setSelector(true);
    listTwo.get(1).setSelector(false);
    listTwo.get(2).setSelector(false);
    listTwo.get(3).setSelector(false);

    final List<Integer> list=new ArrayList<>();
    list.add(1);

    Mockito.when(sone.findQueryBySerialNo(1)).thenReturn(1);
    Mockito.when(mService.getBrands()).thenReturn(brandList);
    Mockito.when(mService.getScreenTypes()).thenReturn(tyList);
    doNothing().when(qr).setQuerySelector(list,1);
    Mockito.when(qr.findQueryBySelector(true, 1)).thenReturn(2);
    Mockito.when(qr.getQuerySerialNoByID(2)).thenReturn(1);


    final ResponseEntity<List<String>> output = qc.inputThreeFilterResponses(1, "1", 1);
    if (output.getStatusCode() == HttpStatus.OK){
      assertEquals(output.getBody().size(), tList.size());
    }


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



  private List<Monitor> setMonitors()
  {

    final List<Monitor> monitors=new ArrayList<>();
    final Monitor monitor=new Monitor("G30E","Goldway","10","Non-Touch");
    monitor.setId(1);
    final Monitor monitor1=new Monitor("G40E","Goldway","12","Non-Touch");
    monitor.setId(2);
    final Monitor monitor2=new Monitor("CM10","Efficia","10","Non-Touch");
    monitor.setId(3);
    final Monitor monitor3=new Monitor("CM12","Efficia","12","Non-Touch");
    monitor.setId(4);
    final Monitor monitor4=new Monitor("CM100","Efficia","10","Touch");
    monitor.setId(5);
    final Monitor monitor5=new Monitor("CM120","Efficia","12","Touch");
    monitor.setId(6);
    final Monitor monitor6=new Monitor("CM150","Efficia","15","Touch");
    monitor.setId(7);

    monitors.add(monitor);
    monitors.add(monitor1);
    monitors.add(monitor2);
    monitors.add(monitor3);
    monitors.add(monitor4);
    monitors.add(monitor5);
    monitors.add(monitor6);

    return monitors;

  }



}
