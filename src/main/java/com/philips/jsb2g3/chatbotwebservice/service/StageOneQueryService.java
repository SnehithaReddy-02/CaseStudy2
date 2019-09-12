/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;

public interface StageOneQueryService {

  public List<StageOneQuery> askQuery();
  public StageOneQuery addNewQuery(StageOneQuery q);
  public StageOneQuery findQueryById(int id);
  public void setSelector(int serialNo,int queryListSize);
  public int findQueryBySelector(boolean selector);
  public int findQueryBySerialNo(int sno);

  public void resetSelectors() ;


}
