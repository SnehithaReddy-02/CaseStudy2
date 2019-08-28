/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;

public interface StageTwoQueryService {


  public List<StageTwoQuery> askQuery(int serialno);
  public void addNewQuery(StageTwoQuery q,int stageoneID);
  public StageTwoQuery findQueryById(int id);
  public int findQueryBySelector(boolean selector);
  public void deleteQueriesByID(int id);
  public void deleteQueriesBySerialNo(int sno) ;
  public void setQuerySelector(int serialNo,int queryListSize,int foreignId);


}
