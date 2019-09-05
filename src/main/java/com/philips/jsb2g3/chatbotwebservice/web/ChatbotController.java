/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.UserDetails;
import com.philips.jsb2g3.chatbotwebservice.service.MonitorService;
import com.philips.jsb2g3.chatbotwebservice.service.StageOneQueryService;
import com.philips.jsb2g3.chatbotwebservice.service.StageTwoQueryService;
import com.philips.jsb2g3.chatbotwebservice.service.UserDetailsService;

@RestController
public class ChatbotController {


  StageOneQueryService service;


  StageTwoQueryService servicetwo;

  MonitorService monitorService;


  UserDetailsService userService;

  @Autowired
  public void setService(StageOneQueryService service) {
    this.service = service;
  }

  @Autowired
  public void setServicetwo(StageTwoQueryService servicetwo) {
    this.servicetwo = servicetwo;
  }

  @Autowired
  public void setMonitorService(MonitorService monitorService) {
    this.monitorService = monitorService;
  }

  @Autowired
  public void setUserService(UserDetailsService userService) {
    this.userService = userService;
  }


  private static List<String> monitorfeatures=new ArrayList<>();;


  @GetMapping(value="/api/startUp")
  public ResponseEntity<List<StageOneQuery>> displayStartUpMenu() {


    service.resetSelectors();
    final List<StageOneQuery> qList= service.askQuery();

    if(qList!=null)
    {
      return new ResponseEntity<>(qList,HttpStatus.OK);

    }else {
      return new ResponseEntity<>(qList,HttpStatus.NOT_FOUND);

    }
  }


  @GetMapping(value="/api/startUp/{option}")
  public ResponseEntity<List<StageTwoQuery>> displayFilterMenu
  (@PathVariable("option") int option) {

    resetSelectors();
    final int queryListSize=service.askQuery().size();
    if(rangeCheck(option, queryListSize))
    {
      service.setSelector(option,queryListSize);
      final List<StageTwoQuery> queries=servicetwo.askQuery(option);


      if(queries!=null)
      {
        final HttpHeaders headers=new HttpHeaders();
        headers.setLocation(URI.create("/api/stageones/"+option));
        return new ResponseEntity<>(queries,HttpStatus.OK);
      }
      else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

      }
    }else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

  }



  @GetMapping(value="/api/startUp/{option1}/{option2}")
  public ResponseEntity<List<String>> inputFilterResponses
  (@PathVariable("option1") int option1,
      @PathVariable("option2") String option2 ) {

    resetSelectors();
    final int queryListSize=service.askQuery().size();
    service.setSelector(option1,queryListSize);

    if(rangeCheck(option1, queryListSize))
    {
      List<String> listStrings;
      if(option1==1 && tryParse(option2)!=-1 )
      {

        listStrings=filterMenu(option1,tryParse(option2));
        if(listStrings!=null)
        {
          return new ResponseEntity<>(listStrings,HttpStatus.OK);
        }
      }else if(option1 == 2) {


        final Monitor p= monitorService.findByName(option2);
        if(p!=null)
        {
          listStrings=new ArrayList<>();
          listStrings.add("Press "+(listStrings.size()+1)+" to get features of "+p.getName()+" monitor");
          return new ResponseEntity<>(listStrings,HttpStatus.OK);
        }

      }
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
  }





  @GetMapping(value="/api/startUp/{option1}/{option2}/{option3}")
  public ResponseEntity<List<String>> inputThreeFilterResponses
  (@PathVariable("option1") int option1,
      @PathVariable("option2") String option2,
      @PathVariable("option3") int option3)
  {
    resetSelectors();
    List<String> listStrings=new ArrayList<>();
    final int queryListSize=service.askQuery().size();
    service.setSelector(option1,queryListSize);
    if(rangeCheck(option1, queryListSize))
    {
      switch(option1)
      {
        case 1:
          final int opt=tryParse(option2);
          if(opt!=-1 && rangeCheck(opt, servicetwo.askQuery(option1).size()))
          {
            final int stageOneId=service.findQueryBySerialNo(option1);
            final List<Integer> trueList=new ArrayList<>();
            trueList.add(opt);
            servicetwo.setQuerySelector(trueList,stageOneId);
            final int id=servicetwo.findQueryBySelector(true,stageOneId);
            final int sno=servicetwo.getQuerySerialNoByID(id);
            listStrings=firstFilter(sno,option3);


          }else {
            listStrings=null;
          }
          break;
        case 2:
          listStrings=searchMonitorByNameReturnDetails(option2,option3);
          break;

        default:
          listStrings=null;
          break;
      }
    }else {
      listStrings=null;
    }

    if(listStrings != null)
    {
      return new ResponseEntity<>(listStrings,HttpStatus.OK);
    }else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }


  public List<String> searchMonitorByNameReturnDetails(String option2, int option3) {
    final List<String> listStrings=new ArrayList<>();
    try{
      final Monitor p= monitorService.findByName(option2);
      if(option3==1)
      {
        listStrings.add("Monitor: "+p.getName()+" ScreenType: "+p.getType()+" ScreenSize: "+p.getSize()+" Brand: "+p.getBrand());
      }else {
        listStrings.clear();
      }

    }catch (final Exception e) {
      listStrings.clear();
    }

    return listStrings;
  }



  public List<String> firstFilter(int opt, int option3)
  {

    List<String> listStrings=new ArrayList<>();
    switch(opt)
    {

      case 1:
        final List<String> brList=monitorService.getBrands();
        final int size=brList.size();
        if (rangeCheck(option3, size)) {
          final List<String> screenList=monitorService.getScreenTypes();
          for(final String s:screenList)
          {
            listStrings.add("Press "+(listStrings.size()+1)+" for "+s+" screen monitors");
          }
        }else {
          listStrings=null;
        }

        break;


      case 2:
        if(rangeCheck(option3, monitorService.getScreenTypes().size()))
        {
          final List<String> sizeList=monitorService.getSizes();
          for(final String s:sizeList)
          {
            listStrings.add("Press "+(listStrings.size()+1)+" to select screen size(in inches) "+s);
          }
        }else {
          listStrings=null;
        }
        break;

      case 3:

        if(rangeCheck(option3, monitorService.getSizes().size()))
        {
          final List<String> brandsList=monitorService.getBrands();
          for(final String s:brandsList)
          {
            listStrings.add("Press "+(listStrings.size()+1)+" for "+s+" monitors");
          }
        }else {
          listStrings=null;
        }
        break;

      case 4:
        if(rangeCheck(option3, monitorService.findAll().size()))
        {
          final List<Monitor> mList=monitorService.findAll();
          final Monitor monitor=mList.get(option3-1);
          listStrings.add("Monitor: "+monitor.getName()+" ScreenType: "+monitor.getType()+" ScreenSize: "+monitor.getSize()+" Brand: "+monitor.getBrand());
        }
        else {
          listStrings=null;
        }
        break;

    }

    return listStrings;




  }




  @PostMapping(value="/api/stageones/3/1")
  public ResponseEntity<String> getResponseforQueri
  (@RequestBody UserDetails toBeSaved) {
    try {
      final UserDetails userDetails=userService.addNewUser(toBeSaved);
      final String msgString="Thank you "+userDetails.getName()+", our team will contact you shortly!!";
      return new ResponseEntity<>(msgString,HttpStatus.CREATED);
    }
    catch (final IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

  }







  @GetMapping(value="/api/startUp/{option1}/{option2}/{option3}/{option4}")
  public ResponseEntity<List<String>> filterMonitorfurther
  (@PathVariable("option1") int option1,
      @PathVariable("option2") String option2,
      @PathVariable("option3") int option3,
      @PathVariable("option4") int option4)
  {
    final int opt=tryParse(option2);
    final List<String> listStrings=new ArrayList<>();

    if(opt!=-1)
    {
      if(validateTillTwoResponses(option1, opt))
      {
        resetSelectors();
        final int queryListSize=service.askQuery().size();
        service.setSelector(option1,queryListSize);

        final int stageOneId=service.findQueryBySerialNo(option1);
        final List<Integer> trueList=new ArrayList<>();
        trueList.add(opt);
        trueList.add(4);
        if(opt<3)
        {
          trueList.add(opt+1);
        }else
        {
          trueList.add(1);
        }
        servicetwo.setQuerySelector(trueList,  stageOneId);

        final int id=servicetwo.findQueryBySelector(false,stageOneId);
        final StageTwoQuery query=servicetwo.findQueryById(id);

        switch(query.getSno())
        {
          case 1:
            try
            {

              if(rangeCheck(option4, monitorService.getSizes().size()))
              {

                listStrings.clear();

                final List<String> brandList=monitorService.getBrands();

                for(final String s:brandList)
                {
                  listStrings.add("Press "+(listStrings.size()+1)+" for "+s+" monitors");
                }

                return new ResponseEntity<>(listStrings,HttpStatus.OK);

              }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
              }
            }
            catch (final Exception e) {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


          case 2:
            try
            {
              if(rangeCheck(option4, monitorService.getBrands().size()))
              {
                listStrings.clear();
                final List<String> typeList=monitorService.getScreenTypes();
                for(final String s:typeList)
                {
                  listStrings.add("Press "+(listStrings.size()+1)+" for "+s+" monitors");
                }
                return new ResponseEntity<>(listStrings,HttpStatus.OK);

              }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

              }


            }catch(final Exception exception)
            {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

          case 3:
            try
            {
              if(rangeCheck(option4, monitorService.getScreenTypes().size()))
              {
                listStrings.clear();
                final List<String> sizeList=monitorService.getSizes();
                for(final String s:sizeList)
                {
                  listStrings.add("Press "+(listStrings.size()+1)+" for "+s+" monitors");
                }
                return new ResponseEntity<>(listStrings,HttpStatus.OK);

              }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

              }

            }catch(final Exception exception)
            {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
        }
      }else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }else {

      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    return new ResponseEntity<>(listStrings,HttpStatus.BAD_REQUEST);

  }



  @GetMapping(value="/api/startUp/{option1}/{option2}/{option3}/{option4}/{option5}")
  public ResponseEntity<List<Monitor>> filterMonitorf
  (@PathVariable("option1") int option1,
      @PathVariable("option2") String option2,
      @PathVariable("option3") int option3,
      @PathVariable("option4") int option4,
      @PathVariable("option5") int option5)
  {
    resetSelectors();
    List<Monitor> monitors;
    final int opt=tryParse(option2);
    if(opt !=-1)
    {
      if(validateTillTwoResponses(option1,opt))
      {

        try
        {
          final ResponseEntity<List<String>> responseEntity1=inputThreeFilterResponses(option1,option2,option3);
          final ResponseEntity<List<String>> responseEntity=filterMonitorfurther(option1,option2,option3,option4);

          if(rangeCheck(option4, responseEntity1.getBody().size()) &&
              rangeCheck(option5, responseEntity.getBody().size()))
          {

            switch(opt)
            {

              case 1:
                monitorfeatures.clear();
                monitorfeatures.add("The selected features are:");
                monitorfeatures.add(monitorService.getBrands().get(option3-1));
                monitorfeatures.add(monitorService.getScreenTypes().get(option4-1));
                monitorfeatures.add(monitorService.getSizes().get(option5-1));

                monitors=extractMonitors(monitorfeatures.get(1), monitorfeatures.get(3), monitorfeatures.get(2));


                if(monitors.isEmpty())
                {
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }else {
                  return new ResponseEntity<>(monitors,HttpStatus.OK);
                }
              case 2:
                monitorfeatures.clear();
                monitorfeatures.add("The selected features are:");

                monitorfeatures.add(monitorService.getScreenTypes().get(option3-1));
                monitorfeatures.add(monitorService.getSizes().get(option4-1));
                monitorfeatures.add(monitorService.getBrands().get(option5-1));

                monitors=extractMonitors(monitorfeatures.get(3),monitorfeatures.get(2),monitorfeatures.get(1));

                if(monitors.isEmpty())
                {
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }else {
                  return new ResponseEntity<>(monitors,HttpStatus.OK);
                }
              case 3:
                monitorfeatures.clear();
                monitorfeatures.add("The selected features are:");

                monitorfeatures.add(monitorService.getSizes().get(option3-1));
                monitorfeatures.add(monitorService.getBrands().get(option4-1));
                monitorfeatures.add(monitorService.getScreenTypes().get(option5-1));

                monitors=extractMonitors(monitorfeatures.get(2),monitorfeatures.get(1),monitorfeatures.get(3));
                if(monitors.isEmpty())
                {
                  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }else {
                  return new ResponseEntity<>(monitors,HttpStatus.OK);
                }
            }


          }else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


          }
        }catch (final Exception e) {

          return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }


      }else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


      }


    }else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);



    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);


  }


  private List<Monitor> extractMonitors(String brand, String size, String type) {

    List<Monitor> monitors=new ArrayList<>();
    try {

      monitors= monitorService.findByGivenFilters(brand, size, type);

    } catch (final Exception e) {
      monitors.clear();
    }


    return monitors;

  }

  private Boolean  rangeCheck(int index,int size) {
    if (index >size || index <= 0) {
      return false;
    }else {
      return true;
    }
  }

  private void resetSelectors()
  {
    service.resetSelectors();
    servicetwo.resetSelectors();

  }

  private int tryParse(String option) {
    try {
      return Integer.parseInt(option);
    } catch (final NumberFormatException e) {
      return -1;
    }
  }

  private List<String> filterMenu(int option1, int option2) {
    List<String> listStrings=new ArrayList<>();

    if(validateTillTwoResponses(option1, option2))
    {
      final int stageOneId=service.findQueryBySerialNo(option1);
      final List<StageTwoQuery> list=servicetwo.askQuery(option1);
      final List<Integer> trueList=new ArrayList<>();
      trueList.add(option2);

      servicetwo.setQuerySelector(trueList, stageOneId);
      for(final StageTwoQuery query:list)
      {
        if(query.getSelector())
        {
          switch(query.getSno()) {
            case 1:
              final List<String> brandList=monitorService.getBrands();
              for(final String s:brandList)
              {
                listStrings.add("Press "+(listStrings.size()+1)+" for "+s+" monitors");
              }

              break;
            case 2:
              final List<String> screenList=monitorService.getScreenTypes();
              for(final String s:screenList)
              {
                listStrings.add("Press "+(listStrings.size()+1)+" for "+s+" monitors");
              }

              break;
            case 3:
              final List<String> sizeList=monitorService.getSizes();
              for(final String s:sizeList)
              {
                listStrings.add("Press "+(listStrings.size()+1)+" for Screensize(in inches): "+s);
              }

              break;

            case 4:

              final List<Monitor> mlist=monitorService.findAll();
              for(final Monitor m:mlist)
              {
                listStrings.add("Press "+(listStrings.size()+1)+" for "+m.getName());
              }

              break;

            default:
              listStrings=null;
              break;
          }
        }
      }
    }else {
      listStrings=null;
    }

    return listStrings;
  }


  private boolean validateTillTwoResponses(int o1, int o2){
    final int queryListSize1=service.askQuery().size();
    final boolean checked1=rangeCheck(o1, queryListSize1);
    final int queryListSize2=servicetwo.askQuery(o1).size();
    final boolean checked2=rangeCheck(o2, queryListSize2);
    return (checked1 && checked2);

  }



}












