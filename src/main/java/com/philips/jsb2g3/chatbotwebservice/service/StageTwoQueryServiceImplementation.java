/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.dal.StageOneQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.dal.StageTwoQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;


@Service
public class StageTwoQueryServiceImplementation implements StageTwoQueryService{

  @Autowired
  StageTwoQueryDAO dao;

  @Autowired
  StageOneQueryDAO daoone;



  @Override
  public List<StageTwoQuery> askQuery(int serialno) {

    return dao.findAll(daoone.findBySerialNo(serialno));

  }

  @Override
  public void addNewQuery(StageTwoQuery q, int stageoneID) {
    dao.save(q,stageoneID);
  }

  @Override
  public StageTwoQuery findQueryById(int id) {
    return dao.findByID(id);

  }
  @Override
  public int findQueryBySelector(boolean selector) {
    return dao.findBySelector(selector);
  }


  @Override
  public void deleteQueriesByID(int id) {
    dao.deleteQueriesByID(id);
  }

  @Override
  public void deleteQueriesBySerialNo(int sno) {
    dao.deleteBySerialNo(sno);

  }

  @Override
  public void setQuerySelector(int serialNo, int size, int foreignId) {

    for(int i=1;i<=size;i++)
    {
      if(i==serialNo)
      {
        final int id=dao.findBySerialNo(serialNo,foreignId);
        final StageTwoQuery query=dao.findByID(id);
        query.setSelector(true);
      }else {
        final int id=dao.findBySerialNo(i,foreignId);
        final StageTwoQuery query=dao.findByID(id);
        query.setSelector(false);
      }
    }


  }

}
