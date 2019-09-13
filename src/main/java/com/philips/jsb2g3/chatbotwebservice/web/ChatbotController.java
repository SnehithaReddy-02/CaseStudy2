/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
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


  StageOneQueryService stageOneService;
  StageTwoQueryService stageTwoService;
  MonitorService monitorService;
  UserDetailsService userService;

  static final String STR1 ="Press ";
  static final String STR2 =" for ";
  static final String MONITOR_STRING = " monitors";

  @Autowired
  public void setService(StageOneQueryService service) {
    this.stageOneService = service;
  }

  @Autowired
  public void setServicetwo(StageTwoQueryService servicetwo) {
    this.stageTwoService = servicetwo;
  }

  @Autowired
  public void setMonitorService(MonitorService monitorService) {
    this.monitorService = monitorService;
  }

  @Autowired
  public void setUserService(UserDetailsService userService) {
    this.userService = userService;
  }


  private static List<String> monitorfeatures=new ArrayList<>();



  @GetMapping(value="/api/startUp")
  public ResponseEntity<List<StageOneQuery>> displayStartUpMenu() {

    stageOneService.resetSelectors();
    final List<StageOneQuery> qList= stageOneService.askQuery();

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
    final int queryListSize=stageOneService.askQuery().size();
    if(rangeCheck(option, queryListSize))
    {
      stageOneService.setSelector(option,queryListSize);
      final List<StageTwoQuery> queries=stageTwoService.askQuery(option);
      if(queries!=null)
      {
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
    final int queryListSize=stageOneService.askQuery().size();
    stageOneService.setSelector(option1,queryListSize);
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
        try {
          final Monitor p= monitorService.findByName(option2);
          if(p!=null)
          {
            listStrings=new ArrayList<>();
            listStrings.add(STR1+(listStrings.size()+1)+" to get features of "+p.getName()+" monitor");
            return new ResponseEntity<>(listStrings,HttpStatus.OK);
          }
        }catch (final Exception e) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    List<String> listStrings;
    final int queryListSize=stageOneService.askQuery().size();
    stageOneService.setSelector(option1,queryListSize);
    if(rangeCheck(option1, queryListSize))
    {
      switch(option1)
      {
        case 1:
          final int opt=tryParse(option2);
          if(opt!=-1 && rangeCheck(opt, stageTwoService.askQuery(option1).size()))
          {
            final int stageOneId=stageOneService.findQueryBySerialNo(option1);
            final List<Integer> trueList=new ArrayList<>();
            trueList.add(opt);
            stageTwoService.setQuerySelector(trueList,stageOneId);
            final int id=stageTwoService.findQueryBySelector(true,stageOneId);
            final int sno=stageTwoService.getQuerySerialNoByID(id);
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
    List<String> listStrings=new ArrayList<>();
    try{
      final Monitor p= monitorService.findByName(option2);
      if(option3==1)
      {
        listStrings.add("Monitor: "+p.getName()+" ScreenType: "+p.getType()+" ScreenSize: "+p.getSize()+" Brand: "+p.getBrand());
      }else {
        listStrings=null;
      }
    }catch (final Exception e) {
      listStrings=null;
    }
    return listStrings;
  }



  public List<String> firstFilter(int opt, int option3)
  {

    List<String> listStrings=new ArrayList<>();
    switch(opt)
    {
      case 1:
        listStrings=getBrandsAndScreenTypeMenu(option3);
        break;

      case 2:
        listStrings=getScreenTypeAndScreenSizeMenu(option3);
        break;

      case 3:
        listStrings=getScreenSizeAndBrandMenu(option3);
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

      default:
        listStrings=null;
        break;
    }
    return listStrings;
  }


  public List<String> getBrandsAndScreenTypeMenu(int option3)
  {
    List<String> listStrings=new ArrayList<>();
    final List<String> brList=monitorService.getBrands();
    final int size=brList.size();
    if (rangeCheck(option3, size)) {
      final List<String> screenList=monitorService.getScreenTypes();
      for(final String s:screenList)
      {
        listStrings.add(STR1+(listStrings.size()+1)+STR2+s+" screen monitors");
      }
    }else {
      listStrings=null;
    }
    return listStrings;
  }



  public List<String> getScreenTypeAndScreenSizeMenu(int option3) {
    List<String> listStrings=new ArrayList<>();
    if(rangeCheck(option3, monitorService.getScreenTypes().size()))
    {
      final List<String> sizeList=monitorService.getSizes();
      for(final String s:sizeList)
      {
        listStrings.add(STR1+(listStrings.size()+1)+" to select screen size(in inches) "+s);
      }
    }else {
      listStrings=null;
    }
    return listStrings;
  }


  public List<String> getScreenSizeAndBrandMenu(int option3)
  {
    List<String> listStrings=new ArrayList<>();
    if(rangeCheck(option3, monitorService.getSizes().size()))
    {
      final List<String> brandsList=monitorService.getBrands();
      for(final String s:brandsList)
      {
        listStrings.add(STR1+(listStrings.size()+1)+STR2+s+" brand's monitors");
      }
    }else {
      listStrings=null;
    }
    return listStrings;
  }



  @PostMapping(value="/api/startUp/3/1")
  public ResponseEntity<String> getUserDetails
  (@RequestBody UserDetails toBeSaved) {
    try {

      if(isValidPhoneNo(toBeSaved.getContactNo()) && isValidEmail(toBeSaved.getEmail()))
      {

        final UserDetails userDetails=userService.addNewUser(toBeSaved);
        final String msgString="Thank you "+userDetails.getName()+", our team will contact you shortly!!";
        return new ResponseEntity<>(msgString,HttpStatus.CREATED);
      }
      else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }
    catch (final IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }


  public  boolean isValidPhoneNo(String phoneString) {
    final Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
    final Matcher m = p.matcher(phoneString);
    return (m.find() && m.group().equals(phoneString));
  }

  public boolean isValidEmail(String email)
  {
    final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";

    final Pattern pat = Pattern.compile(emailRegex);
    return pat.matcher(email).matches();
  }



  @GetMapping(value="/api/startUp/{option1}/{option2}/{option3}/{option4}")
  public ResponseEntity<List<String>> inputTillFourResponses
  (@PathVariable("option1") int option1,
      @PathVariable("option2") String option2,
      @PathVariable("option3") int option3,
      @PathVariable("option4") int option4)
  {

    final int opt=tryParse(option2);
    List<String> listStrings;

    if(validateTillTwoResponses(option1, opt) && opt !=-1 && opt!=4 && option1!=3)
    {
      resetSelectors();
      final int queryListSize=stageOneService.askQuery().size();
      stageOneService.setSelector(option1,queryListSize);
      final int stageOneId=stageOneService.findQueryBySerialNo(option1);
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
      stageTwoService.setQuerySelector(trueList,  stageOneId);
      final int id=stageTwoService.findQueryBySelector(false,stageOneId);
      final int sno=stageTwoService.getQuerySerialNoByID(id);
      listStrings=secondFilter(sno,option4);

      if(listStrings!=null)
      {
        return new ResponseEntity<>(listStrings,HttpStatus.OK);
      }else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }



  public List<String> secondFilter(int sno, int option4) {
    List<String> listStrings=new ArrayList<>();
    switch(sno)
    {
      case 1:
        if(rangeCheck(option4, monitorService.getSizes().size()))          {
          final List<String> brandList=monitorService.getBrands();
          listStrings=printListOfStrings(brandList);
        }else {
          listStrings=null;
        }
        break;

      case 2:
        if(rangeCheck(option4, monitorService.getBrands().size()))
        {
          final List<String> typeList=monitorService.getScreenTypes();
          listStrings=printListOfStrings(typeList);
        }else {
          listStrings=null;
        }
        break;

      case 3:
        if(rangeCheck(option4, monitorService.getScreenTypes().size()))
        {
          final List<String> sizeList=monitorService.getSizes();
          for(final String s:sizeList)
          {
            listStrings.add(STR1+(listStrings.size()+1)+STR2+s+" inches monitors");
          }
        }else {
          listStrings=null;
        }
        break;

      default:
        listStrings=null;
        break;
    }
    return listStrings;
  }


  public  List<String> printListOfStrings(List<String> list) {

    final List<String> listStrings=new ArrayList<>();
    for(final String s:list)
    {
      listStrings.add(STR1+(listStrings.size()+1)+STR2+s+MONITOR_STRING);
    }
    return listStrings;
  }



  @GetMapping(value="/api/startUp/{option1}/{option2}/{option3}/{option4}/{option5}")
  public ResponseEntity<List<Monitor>> inputFiveResponses
  (@PathVariable("option1") int option1,
      @PathVariable("option2") String option2,
      @PathVariable("option3") int option3,
      @PathVariable("option4") int option4,
      @PathVariable("option5") int option5)
  {
    resetSelectors();
    final List<Monitor> monitors;
    final int opt=tryParse(option2);
    if(validateTillTwoResponses(option1,opt) && opt!=-1)
    {

      try
      {
        final ResponseEntity<List<String>> responseEntity2=inputFilterResponses(option1, option2);
        final ResponseEntity<List<String>> responseEntity1=inputThreeFilterResponses(option1,option2,option3);
        final ResponseEntity<List<String>> responseEntity=inputTillFourResponses(option1,option2,option3,option4);

        if(rangeCheck(option4, responseEntity1.getBody().size()) &&  rangeCheck(option5, responseEntity.getBody().size()) && rangeCheck(option3, responseEntity2.getBody().size()) )
        {
          monitors= enableStackOfChoices(opt,option3,option4,option5);
          return new ResponseEntity<>(monitors,HttpStatus.OK);
        }else {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
      }catch (final Exception e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }



  public List<Monitor> enableStackOfChoices( int opt, int option3, int option4, int option5) {

    List<Monitor> monitors=new ArrayList<>();
    final String string="The selected features are:";
    switch(opt)
    {

      case 1:
        monitorfeatures.clear();
        monitorfeatures.add(string);
        monitorfeatures.add(monitorService.getBrands().get(option3-1));
        monitorfeatures.add(monitorService.getScreenTypes().get(option4-1));
        monitorfeatures.add(monitorService.getSizes().get(option5-1));
        monitors=extractMonitors(monitorfeatures.get(1), monitorfeatures.get(3), monitorfeatures.get(2));
        break;


      case 2:
        monitorfeatures.clear();
        monitorfeatures.add(string);
        monitorfeatures.add(monitorService.getScreenTypes().get(option3-1));
        monitorfeatures.add(monitorService.getSizes().get(option4-1));
        monitorfeatures.add(monitorService.getBrands().get(option5-1));
        monitors=extractMonitors(monitorfeatures.get(3),monitorfeatures.get(2),monitorfeatures.get(1));
        break;


      case 3:
        monitorfeatures.clear();
        monitorfeatures.add(string);
        monitorfeatures.add(monitorService.getSizes().get(option3-1));
        monitorfeatures.add(monitorService.getBrands().get(option4-1));
        monitorfeatures.add(monitorService.getScreenTypes().get(option5-1));
        monitors=extractMonitors(monitorfeatures.get(2),monitorfeatures.get(1),monitorfeatures.get(3));
        break;


      default:
        monitors.clear();
    }
    if(monitors.isEmpty())
    {
      monitors=null;
    }
    return monitors;
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
    return (!(index >size || index <= 0));
  }



  private void resetSelectors()
  {
    stageOneService.resetSelectors();
    stageTwoService.resetSelectors();
  }



  private int tryParse(String option) {
    try {
      return Integer.parseInt(option);
    } catch (final NumberFormatException e) {
      return -1;
    }
  }



  private List<String> filterMenu(int option1, int option2)  {
    List<String> listStrings=new ArrayList<>();

    if(validateTillTwoResponses(option1, option2))
    {
      final int stageOneId=stageOneService.findQueryBySerialNo(option1);
      final List<StageTwoQuery> list=stageTwoService.askQuery(option1);
      final List<Integer> trueList=new ArrayList<>();
      trueList.add(option2);

      stageTwoService.setQuerySelector(trueList, stageOneId);
      for(final StageTwoQuery query:list)
      {
        if(query.getSelector())
        {
          switch(query.getSno()) {
            case 1:
              listStrings=getBrandsMenuList();
              break;

            case 2:
              listStrings=getScreenTypeMenuList();
              break;

            case 3:
              listStrings=getScreenSizesMenuList();
              break;

            case 4:
              listStrings=getAllMonitorsMenuList();
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



  public List<String> getAllMonitorsMenuList() {
    final List<String> listStrings=new ArrayList<>();
    final List<Monitor> mlist=monitorService.findAll();
    for(final Monitor m:mlist)
    {

      listStrings.add(STR1+(listStrings.size()+1)+STR2+m.getName());

    }
    return listStrings;
  }



  public List<String> getBrandsMenuList()
  {
    List<String> listStrings=new ArrayList<>();
    final List<String> brandList=monitorService.getBrands();
    for(final String s:brandList)
    {
      if(brandList!=null && !brandList.isEmpty()) {
        if(listStrings!=null)
        {
          listStrings.add(STR1+(listStrings.size()+1)+STR2+s+MONITOR_STRING);
        }
      }else {
        listStrings=null;
      }
    }
    return listStrings;
  }



  public List<String> getScreenTypeMenuList()
  {
    List<String> listStrings=new ArrayList<>();
    final List<String> screenList=monitorService.getScreenTypes();
    for(final String s:screenList)
    {
      if(screenList!=null && !screenList.isEmpty()) {
        if(listStrings!=null)
        {
          listStrings.add(STR1+(listStrings.size()+1)+STR2+s+MONITOR_STRING);
        }
      }else {
        listStrings=null;
      }
    }
    return listStrings;
  }



  public List<String> getScreenSizesMenuList() {
    List<String> listStrings=new ArrayList<>();
    final List<String> sizeList=monitorService.getSizes();
    for(final String s:sizeList)
    {
      if(sizeList!=null && !sizeList.isEmpty()) {

        if(listStrings!=null)
        {
          listStrings.add(STR1+(listStrings.size()+1)+" for Screensize(in inches): "+s);
        }

      }else {
        listStrings=null;
      }
    }
    return listStrings;
  }



  public boolean validateTillTwoResponses(int o1, int o2){
    final int queryListSize1=stageOneService.askQuery().size();
    final boolean checked1=rangeCheck(o1, queryListSize1);
    final int queryListSize2=stageTwoService.askQuery(o1).size();
    final boolean checked2=rangeCheck(o2, queryListSize2);
    return (checked1 && checked2);
  }



}












