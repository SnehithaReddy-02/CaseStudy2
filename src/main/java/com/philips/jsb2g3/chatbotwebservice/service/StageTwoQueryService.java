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
  public int findQueryBySelector(boolean selector,int foreignID);
  public void deleteQueriesByID(int id);
  public void deleteQueriesBySerialNo(int sno) ;
  public void setQuerySelector(List<Integer> serialNo,int foreignId);
  public void resetSelectors();
  public int getQuerySerialNoByID(int id);
}
