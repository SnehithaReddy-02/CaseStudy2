/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.philips.jsb2g3.chatbotwebservice.service.MonitorServiceTest;
import com.philips.jsb2g3.chatbotwebservice.service.StageOneQueryServiceTest;
import com.philips.jsb2g3.chatbotwebservice.service.StageTwoQueryServiceTest;
import com.philips.jsb2g3.chatbotwebservice.service.UserDetailsServiceTest;
import com.philips.jsb2g3.chatbotwebservice.web.ChatbotControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ChatbotControllerTest.class,MonitorServiceTest.class,StageOneQueryServiceTest.class,StageTwoQueryServiceTest.class,UserDetailsServiceTest.class})
public class TestSuite {

}
