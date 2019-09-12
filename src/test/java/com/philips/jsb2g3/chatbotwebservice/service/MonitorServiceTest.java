/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import com.philips.jsb2g3.chatbotwebservice.dal.MonitorDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

public class MonitorServiceTest {




  @Test
  public void addNewMonitorTest() {
    final MonitorServiceImplementation monitorServiceImplementation =
        new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);

    final Monitor monitor = new Monitor("G30E", "Goldway", "10", "Non-Touch");
    monitor.setId(1);
    Mockito.when(monitorDAO.save(monitor)).thenReturn(monitor);


    final int on = monitorServiceImplementation.addNewMonitor(monitor);
    assertEquals(1,on);


  }


  @Test
  public void findByIdTest() {
    final MonitorServiceImplementation monitorServiceImplementation =
        new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);

    final Monitor monitor = new Monitor("G30E", "Goldway", "10", "Non-Touch");
    monitor.setId(1);
    Mockito.when(monitorDAO.findById(1)).thenReturn(monitor);


    final Monitor on = monitorServiceImplementation.findById(1);
    assertEquals(on, monitor);



  }

  @Test
  public void findByNameTest() {
    final MonitorServiceImplementation monitorServiceImplementation =
        new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);

    final Monitor monitor = new Monitor("G30E", "Goldway", "10", "Non-Touch");
    monitor.setId(1);
    Mockito.when(monitorDAO.findByName("G30E")).thenReturn(monitor);


    final Monitor on = monitorServiceImplementation.findByName("G30E");
    assertEquals(on, monitor);



  }



  @Test
  public void findAllTest() {


    final MonitorServiceImplementation monitorServiceImplementation =
        new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);
    Mockito.when(monitorDAO.findAll()).thenReturn(setMonitors());
    final List<Monitor> on = monitorServiceImplementation.findAll();
    assertEquals(on.size(), setMonitors().size());



  }


  @Test
  public void getAllBrandsTest() {


    final MonitorServiceImplementation monitorServiceImplementation =  new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);

    final List<String> brandList=new ArrayList<>();
    brandList.add("Goldway");
    brandList.add("Efficia");
    Mockito.when(monitorDAO.getAllBrands()).thenReturn(brandList);
    final List<String> bList=monitorServiceImplementation.getBrands();
    assertEquals(bList, brandList);

  }


  @Test
  public void getAllSizesTest()
  {

    final MonitorServiceImplementation monitorServiceImplementation =  new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);

    final List<String> sizeList=new ArrayList<>();
    sizeList.add("10");
    sizeList.add("15");
    sizeList.add("12");
    Mockito.when(monitorDAO.getAllSizes()).thenReturn(sizeList);
    final List<String> bList=monitorServiceImplementation.getSizes();
    assertEquals(bList, sizeList);
  }

  @Test
  public void getAllTypesTest()
  {

    final MonitorServiceImplementation monitorServiceImplementation =  new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);

    final List<String> typeList=new ArrayList<>();
    typeList.add("Touch");
    typeList.add("Non-Touch");

    Mockito.when(monitorDAO.getAllScreenTypes()).thenReturn(typeList);
    final List<String> bList=monitorServiceImplementation.getScreenTypes();
    assertEquals(bList, typeList);
  }

  @Test
  public void findByGivenFiltersTest()
  {

    final MonitorServiceImplementation monitorServiceImplementation =  new MonitorServiceImplementation();
    final MonitorDAO monitorDAO = Mockito.mock(MonitorDAO.class);
    monitorServiceImplementation.setDao(monitorDAO);

    final List<Monitor> monitors=setMonitors();

    final List<Monitor> mList=new ArrayList<>();
    mList.add(monitors.get(0));




    Mockito.when(monitorDAO.findByGivenBrandGivenSizeGivenScreenType("Goldway", "10", "Non-Touch")).thenReturn(mList);
    final List<Monitor> bList=monitorServiceImplementation.findByGivenFilters("Goldway", "10", "Non-Touch");
    assertEquals(bList, mList);
  }





  private List<Monitor> setMonitors() {

    final List<Monitor> monitors = new ArrayList<>();
    final Monitor monitor = new Monitor("G30E", "Goldway", "10", "Non-Touch");
    monitor.setId(1);
    final Monitor monitor1 = new Monitor("G40E", "Goldway", "12", "Non-Touch");
    monitor.setId(2);
    final Monitor monitor2 = new Monitor("CM10", "Efficia", "10", "Non-Touch");
    monitor.setId(3);
    final Monitor monitor3 = new Monitor("CM12", "Efficia", "12", "Non-Touch");
    monitor.setId(4);
    final Monitor monitor4 = new Monitor("CM100", "Efficia", "10", "Touch");
    monitor.setId(5);
    final Monitor monitor5 = new Monitor("CM120", "Efficia", "12", "Touch");
    monitor.setId(6);
    final Monitor monitor6 = new Monitor("CM150", "Efficia", "15", "Touch");
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
