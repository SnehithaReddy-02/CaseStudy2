/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;

public interface Query {

  public void setStageOneQueries(List<StageOneQuery> q);
  public void setStageTwoQueries(List<Integer> idList,List<StageTwoQuery> q);

}
