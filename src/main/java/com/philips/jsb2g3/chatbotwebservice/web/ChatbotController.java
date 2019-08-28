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
import org.springframework.web.bind.annotation.RestController;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;
import com.philips.jsb2g3.chatbotwebservice.service.MonitorService;
import com.philips.jsb2g3.chatbotwebservice.service.StageOneQueryService;
import com.philips.jsb2g3.chatbotwebservice.service.StageTwoQueryService;

@RestController
public class ChatbotController {

  @Autowired
  StageOneQueryService service;

  @Autowired
  StageTwoQueryService servicetwo;

  @Autowired
  MonitorService monitorService;


  @GetMapping(value="/api/stageones")
  public ResponseEntity<List<StageOneQuery>> getStageOneQueries() {

    final List<StageOneQuery> qList= service.askQuery();

    if(qList!=null)
    {
      return new ResponseEntity<>(qList,HttpStatus.OK);

    }else {
      return new ResponseEntity<>(qList,HttpStatus.NOT_FOUND);

    }

  }

  @GetMapping(value="/api/stageones/{option}")
  public ResponseEntity<List<StageTwoQuery>> getResponseforQueries
  (@PathVariable("option") int option) {

    final int queryListSize=service.askQuery().size();

    if(option>0 && option<=queryListSize)
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
        return new ResponseEntity<>(queries,HttpStatus.BAD_REQUEST);

      }
    }else {

      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }



  @GetMapping(value="/api/stageones/{option1}/{option2}")
  public ResponseEntity<List<String>> filterMonitors
  (@PathVariable("option1") int option1,
      @PathVariable("option2") int option2 ) {

    final int queryListSize=service.askQuery().size();
    List<String> listStrings=new ArrayList<>();


    if(option1>0 && option1<=queryListSize)
    {
      service.setSelector(option1,queryListSize);
      final int stageOneId=service.findQueryBySerialNo(option1);
      final List<StageTwoQuery> list=servicetwo.askQuery(option1);
      servicetwo.setQuerySelector(option2, list.size(), stageOneId);



      for(final StageTwoQuery query:list)
      {
        if(query.getSelector())
        {
          final int serialNumber=query.getSno();
          if(serialNumber==1)
          {
            listStrings=monitorService.getBrands();
          }else if(serialNumber==2)
          {
            listStrings=monitorService.getScreenTypes();
          }else if(serialNumber==3){
            listStrings=monitorService.getSizes();
          }else {
            final List<Monitor> mlist=monitorService.findAll();
            for(final Monitor m:mlist)
            {
              listStrings.add(m.getName());
            }
          }

        }
      }

      return new ResponseEntity<>(listStrings,HttpStatus.OK);

    }else
    {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


  }

  @PostMapping(value="/api/stageones/{option1}/{option2}")
  public ResponseEntity<Monitor> getSearchMonitorByName(@PathVariable("option1") int option1,
      @PathVariable("option2") String name){

    final Monitor p= monitorService.findByName(name);
    if(p!=null)
    {
      return new ResponseEntity<>(p,HttpStatus.OK);
    }else
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }








}

