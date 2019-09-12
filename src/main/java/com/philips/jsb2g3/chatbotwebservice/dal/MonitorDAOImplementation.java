/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

@Transactional
@Repository
@SuppressWarnings(value = {"unchecked"})
public class MonitorDAOImplementation implements MonitorDAO {

  @PersistenceContext
  EntityManager em;

  public static final String STRING="select m from Monitor m";


  @Override
  public Monitor save(Monitor m) {
    em.persist(m);
    return m;
  }

  @Override
  public Monitor findById(int id) {
    return em.find(Monitor.class, id);
  }

  @Override
  public Monitor findByName(String name) {
    return (Monitor) em.createQuery("select m from Monitor m where m.name=:name ")
        .setParameter("name", name)
        .getSingleResult();

  }


  @Override
  public List<Monitor> findAll() {
    return em.createQuery(STRING)
        .getResultList();
  }

  @Override
  public void deleteById(int id) {
    em.createQuery("delete from Monitor m where m.id=:id").setParameter("id", id)
    .executeUpdate();

  }

  @Override
  public List<String> getAllBrands() {
    final List<String> brandsList=new ArrayList<>();
    final List<Monitor> monitors=em.createQuery(STRING)
        .getResultList();

    for(final Monitor monitor:monitors)
    {
      if (!brandsList.contains(monitor.getBrand())) {
        brandsList.add(monitor.getBrand());
      }
    }
    return brandsList;
  }

  @Override
  public List<String> getAllSizes() {

    final List<String> screenSizesList=new ArrayList<>();
    final List<Monitor> monitors=em.createQuery(STRING)
        .getResultList();

    for(final Monitor monitor:monitors)
    {
      if (!screenSizesList.contains(monitor.getSize())) {
        screenSizesList.add(monitor.getSize());
      }
    }
    return screenSizesList;

  }

  @Override
  public List<String> getAllScreenTypes() {
    final List<String> screenTypesList=new ArrayList<>();
    final List<Monitor> monitors=em.createQuery(STRING)
        .getResultList();

    for(final Monitor monitor:monitors)
    {
      if (!screenTypesList.contains(monitor.getType())) {
        screenTypesList.add(monitor.getType());
      }
    }
    return screenTypesList;
  }

  @Override
  public List<Monitor> findByGivenBrandGivenSizeGivenScreenType(String brand, String size, String screenType) {

    return em.createQuery("select m from Monitor m where m.size=:size and m.brand=:brand and m.type=:type")
        .setParameter("brand", brand)
        .setParameter("size",size)
        .setParameter("type", screenType)
        .getResultList();


  }



}
