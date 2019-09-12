/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.dal.StageOneQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.dal.StageTwoQueryDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;

@Transactional
@Service
@Repository
public class StageTwoQueryServiceImplementation implements StageTwoQueryService{


  StageTwoQueryDAO dao;

  StageOneQueryDAO daoone;

  @PersistenceContext
  EntityManager em;



  @Autowired
  public void setDao(StageTwoQueryDAO dao) {
    this.dao = dao;
  }

  @Autowired
  public void setDaoone(StageOneQueryDAO daoone) {
    this.daoone = daoone;
  }

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
  public int findQueryBySelector(boolean selector, int foreignID) {


    return dao.findBySelector(selector,foreignID);
  }






  @Override
  public void resetSelectors() {
    final List<StageTwoQuery> list=dao.findAllStageTwoQueries();
    for(final StageTwoQuery query:list)
    {
      query.setSelector(false);
    }
  }

  @Override
  public void setQuerySelector(List<Integer> serialNo,int foreignId) {

    for(final int sno: serialNo)
    {
      final int id=dao.findBySerialNo(sno, foreignId);

      final StageTwoQuery q = em.find(StageTwoQuery.class, id);
      q.setSelector(true);
      em.persist(q);
      em.close();

    }

  }


  @Override
  public int getQuerySerialNoByID(int id) {
    final StageTwoQuery query=dao.findByID(id);
    return query.getSno();
  }


}





