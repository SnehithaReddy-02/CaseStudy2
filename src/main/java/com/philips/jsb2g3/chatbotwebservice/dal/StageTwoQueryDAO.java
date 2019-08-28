/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;

public interface StageTwoQueryDAO {

  StageTwoQuery save(StageTwoQuery q, int oneid);

  List<StageTwoQuery> findAll(int oneid);

  StageTwoQuery findByID(int id);

  int findBySerialNo(int sno,int foreignId);

  int findBySelector(boolean selector);

  void deleteQueriesByID(int id);

  void deleteBySerialNo(int sno);

}
