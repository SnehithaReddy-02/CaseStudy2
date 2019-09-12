/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;

@Transactional
@Repository
@SuppressWarnings(value = {"unchecked"})
public class StageOneQueryDAOImplementation implements StageOneQueryDAO {

  @PersistenceContext
  EntityManager em;


  @Override
  public StageOneQuery save(StageOneQuery q) {
    em.persist(q);
    return q;
  }

  @Override
  public void deleteQueriesByID(int id) {
    em.createQuery("delete from StageOneQuery q  where q.id=:id")
    .setParameter("id", id)
    .executeUpdate();
  }



  @Override
  public StageOneQuery findByID(int id) {
    return (StageOneQuery) em.createQuery("select q from StageOneQuery q where q.id=:id")
        .setParameter("id", id)
        .getSingleResult();

  }


  @Override
  public List<StageOneQuery> findAll() {
    return em.createQuery("select q from StageOneQuery q")
        .getResultList();
  }


  @Override
  public int findBySerialNo(int sno) {
    final StageOneQuery sq= (StageOneQuery) em.createQuery("select q from StageOneQuery q where q.sno=:sno")
        .setParameter("sno", sno)
        .getSingleResult();
    return sq.getId();
  }


  @Override
  public void deleteQueriesBySerialNo(int sno) {
    em.createQuery("delete from StageOneQuery q  where q.sno=:sno")
    .setParameter("sno", sno)
    .executeUpdate();
  }


  @Override
  public int findBySelector(boolean selector) {
    final StageOneQuery sq= (StageOneQuery) em.createQuery("select q from StageOneQuery q where q.selector=:selector")
        .setParameter("selector", selector)
        .getSingleResult();
    return sq.getId();
  }

  @Override
  public void setSelector(int id, boolean selector) {
    final StageOneQuery sq= (StageOneQuery) em.createQuery("select q from StageOneQuery q where q.id=:id")
        .setParameter("id", id)
        .getSingleResult();

    sq.setSelector(selector);
    em.persist(sq);
    em.close();
  }






}
