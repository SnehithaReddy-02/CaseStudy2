/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.Monitor;

public interface MonitorDAO {

  Monitor save(Monitor m);

  Monitor findById(int id);

  Monitor findByName(String name);

  List<Monitor> findAll();

  void deleteById(int id);

  List<String> getAllBrands();

  List<String> getAllSizes();

  List<String> getAllScreenTypes();

  List<Monitor> findByBrands(String brand, List<Monitor> monitors );

  List<Monitor> findBySizes(String size, List<Monitor> monitors );

  List<Monitor> findByScreenType(String screenType, List<Monitor> monitors );



}
