/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.philips.jsb2g3.chatbotwebservice.domain.UserDetails;

@Transactional
@Repository
@SuppressWarnings(value = {"unchecked"})
public class UserDetailsDAOImplementation implements UserDetailsDAO {

  @PersistenceContext
  EntityManager em;

  @Override
  public UserDetails save(UserDetails ud) {
    em.persist(ud);
    return ud;
  }

  @Override
  public UserDetails findById(int id) {
    return em.find(UserDetails.class, id);
  }

  @Override
  public List<UserDetails> findAll() {
    return em.createQuery("select ud from userdetails ud")
        .getResultList();
  }

  @Override
  public UserDetails findByName(String name) {
    return (UserDetails) em.createQuery("select m from userdetails m where m.name=:name ")
        .setParameter("name", name)
        .getSingleResult();
  }

  @Override
  public void deleteById(int id) {
    em.createQuery("delete from userdetails m where m.id=:id").setParameter("id", id)
    .executeUpdate();
  }
}