/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */


package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.philips.jsb2g3.chatbotwebservice.domain.StageOneQuery;
import com.philips.jsb2g3.chatbotwebservice.domain.StageTwoQuery;
import com.philips.jsb2g3.chatbotwebservice.service.StageOneQueryService;
import com.philips.jsb2g3.chatbotwebservice.service.StageTwoQueryService;

@Service
public class QueryImplementation implements Query {

  StageOneQueryService daoOne;
  StageTwoQueryService daoTwo;


  @Autowired
  public void setDaoOne(StageOneQueryService daoOne) {
    this.daoOne = daoOne;
  }

  @Autowired
  public void setDaoTwo(StageTwoQueryService daoTwo) {
    this.daoTwo = daoTwo;
  }

  @Override
  public void setStageOneQueries(List<StageOneQuery> q) {
    for(final StageOneQuery query:q)
    {
      daoOne.addNewQuery(query);
    }

  }



  @Override
  public void setStageTwoQueries(List<Integer> idList,List<StageTwoQuery> q) {

    //final Iterator<Integer> il=idList.iterator();
    //final Iterator<StageTwoQuery> iqIterator=q.iterator();
    int i=5;
    while (i>=0) {

      daoTwo.addNewQuery(q.get(i),idList.get(i));
      i--;
    }

  }


}


