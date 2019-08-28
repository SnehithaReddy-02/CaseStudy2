/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.dal.MonitorDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

public interface MonitorService {

  void setDao(MonitorDAO dao);

  int addNewMonitor(Monitor toBeAdded);

  Monitor findById(int id);

  Monitor findByName(String name);

  List<Monitor> findAll();

  void deleteById(int id);

  List<String> getBrands();

  List<String> getSizes();

  List<String> getScreenTypes();

}
