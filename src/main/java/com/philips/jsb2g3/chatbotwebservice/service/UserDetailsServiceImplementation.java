/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.philips.jsb2g3.chatbotwebservice.dal.UserDetailsDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.UserDetails;

public class UserDetailsServiceImplementation implements UserDetailsService{

  UserDetailsDAO dao;


  @Autowired
  public void setDao(UserDetailsDAO dao) {
    this.dao = dao;
  }

  @Override
  public int addNewUser(UserDetails toBeAdded) {
    final UserDetails saved = dao.save(toBeAdded);
    return saved.getId();
  }

  @Override
  public UserDetails findById(int id) {
    return dao.findById(id);
  }

  @Override
  public UserDetails findByName(String name) {
    return dao.findByName(name);
  }

  @Override
  public List<UserDetails> findAll() {
    return dao.findAll();
  }

  @Override
  public void deleteById(int id) {
    dao.deleteById(id);
  }
}
