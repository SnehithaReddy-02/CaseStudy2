/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.dal.MonitorDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

@Service
public class MonitorServiceImplementation implements MonitorService {

  MonitorDAO dao;

  @Override
  @Autowired
  public void setDao(MonitorDAO dao) {
    this.dao = dao;
  }

  @Override
  public int addNewMonitor(Monitor toBeAdded) {
    final Monitor saved = dao.save(toBeAdded);
    return saved.getId();
  }
  @Override
  public Monitor findById(int id) {
    return dao.findById(id);
  }

  @Override
  public Monitor findByName(String name) {
    return dao.findByName(name);
  }

  @Override
  public List<Monitor> findAll() {
    return dao.findAll();
  }

  @Override
  public void deleteById(int id) {
    dao.deleteById(id);
  }

  @Override
  public List<String> getBrands() {
    return dao.getAllBrands();
  }

  @Override
  public List<String> getSizes() {
    return dao.getAllSizes();
  }

  @Override
  public List<String> getScreenTypes() {
    return dao.getAllScreenTypes();
  }

}
