/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.dal.StageOneQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;



@Service
public class StageOneQueryServiceImplementation implements StageOneQueryService{

  StageOneQueryDAO dao;



  @Autowired
  public void setDao(StageOneQueryDAO dao) {
    this.dao = dao;
  }


  @Override
  public StageOneQuery addNewQuery(StageOneQuery q) {
    return dao.save(q);
  }



  @Override
  public List<StageOneQuery> askQuery() {
    return dao.findAll();

  }

  @Override
  public StageOneQuery findQueryById(int id) {
    return dao.findByID(id);

  }


  @Override
  public int findQueryBySelector(boolean selector) {
    return dao.findBySelector(selector);
  }


  @Override
  public int findQueryBySerialNo(int sno) {
    return dao.findBySerialNo(sno);
  }




  @Override
  public void setSelector(int serialNo,int size) {

    for(int i=1;i<=size;i++)
    {
      if(i==serialNo)
      {
        final int id=dao.findBySerialNo(serialNo);
        dao.setSelector(id, true);


      }else {
        final int id=dao.findBySerialNo(i);
        dao.setSelector(id, false);


      }
    }


  }


  @Override
  public void resetSelectors() {
    final List<StageOneQuery> list=dao.findAll();
    for(final StageOneQuery query:list)
    {
      query.setSelector(false);
    }
  }

}
