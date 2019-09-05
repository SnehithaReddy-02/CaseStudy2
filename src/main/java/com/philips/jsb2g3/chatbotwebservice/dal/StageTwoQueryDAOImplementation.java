/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;
import com.philips.jsb2g3.chatbotwebservice.service.StageOneQueryService;

@Transactional
@Repository
public class StageTwoQueryDAOImplementation implements StageTwoQueryDAO {

  @PersistenceContext
  EntityManager em;

  @Autowired
  StageOneQueryService service;

  @Override
  public StageTwoQuery save(StageTwoQuery q, int oneId) {
    final StageOneQuery query = em.find(StageOneQuery.class, oneId);
    q.setStageOneQuery(query);
    em.persist(q);

    return q;
  }



  @Override
  public StageTwoQuery findByID(int id) {

    return (StageTwoQuery) em.createQuery("select b from StageTwoQuery b where b.id=:id")
        .setParameter("id", id)
        .getSingleResult();
  }

  @Override
  public void deleteQueriesByID(int id) {

    em.createQuery("delete from StageTwoQuery q  where q.id=:id")
    .setParameter("id", id)
    .executeUpdate();

  }

  @Override
  public List<StageTwoQuery> findAll(int oneid) {
    final StageOneQuery query = em.find(StageOneQuery.class, oneid);
    return em.createQuery("select q from StageTwoQuery q where q.stageOneQuery=:stageOneQuery")
        .setParameter("stageOneQuery", query)
        .getResultList();
  }

  @Override
  public int findBySerialNo(int sno,int foreignId) {
    final StageTwoQuery sq= (StageTwoQuery) em.createQuery("select q from StageTwoQuery q where q.sno=:sno and q.stageOneQuery=:stageOneQuery")
        .setParameter("sno", sno)
        .setParameter("stageOneQuery", service.findQueryById(foreignId))
        .getSingleResult();
    return sq.getId();
  }

  @Override
  public void deleteBySerialNo(int sno) {
    em.createQuery("delete from StageTwoQuery q  where q.sno=:sno")
    .setParameter("sno", sno)
    .executeUpdate();

  }

  @Override
  public int findBySelector(boolean selector, int foreignID) {
    final StageTwoQuery sq= (StageTwoQuery) em.createQuery("select q from StageTwoQuery q where q.selector=:selector and q.stageOneQuery=:stageOneQuery")
        .setParameter("selector", selector)
        .setParameter("stageOneQuery", service.findQueryById(foreignID))
        .getSingleResult();
    return sq.getId();
  }



  @Override
  public List<StageTwoQuery> findAllStageTwoQueries() {
    return em.createQuery("select q from StageTwoQuery q")
        .getResultList();
  }










}
