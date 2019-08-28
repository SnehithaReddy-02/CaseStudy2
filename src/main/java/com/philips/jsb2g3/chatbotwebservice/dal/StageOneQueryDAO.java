/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;

public interface StageOneQueryDAO {

  StageOneQuery save(StageOneQuery q);

  List<StageOneQuery> findAll();

  int findBySerialNo(int sno);

  int findBySelector(boolean selector);

  StageOneQuery findByID(int id);

  void deleteQueriesByID(int id);

  void deleteQueriesBySerialNo(int sno);


}
