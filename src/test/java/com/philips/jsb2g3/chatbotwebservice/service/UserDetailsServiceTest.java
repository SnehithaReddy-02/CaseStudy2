/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.service;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import com.philips.jsb2g3.chatbotwebservice.dal.UserDetailsDAO;
import com.philips.jsb2g3.chatbotwebservice.domain.UserDetails;

public class UserDetailsServiceTest {
  @Test
  public void addNewUserTest() {

    final UserDetailsServiceImplementation userService = new UserDetailsServiceImplementation();

    final UserDetailsDAO queryDAO = Mockito.mock(UserDetailsDAO.class);
    userService.setDao(queryDAO);
    final List<UserDetails> qList=setUserDetails();
    Mockito.when(queryDAO.save(qList.get(0))).thenReturn(qList.get(0));
    final UserDetails query=userService.addNewUser(qList.get(0));
    assertEquals(qList.get(0), query);
  }

  @Test
  public void  findByIdTest() {

    final UserDetailsServiceImplementation serviceImplementation = new UserDetailsServiceImplementation();

    final UserDetailsDAO userDetailsDAO = Mockito.mock(UserDetailsDAO.class);
    serviceImplementation.setDao(userDetailsDAO);
    final List<UserDetails> userList=setUserDetails();
    Mockito.when(userDetailsDAO.findById(1)).thenReturn(userList.get(0));
    final UserDetails uDetails=serviceImplementation.findById(1);
    assertEquals(uDetails, userList.get(0));

  }

  @Test
  public void findAllUserTest()
  {
    final UserDetailsServiceImplementation serviceImplementation = new UserDetailsServiceImplementation();

    final UserDetailsDAO userDetailsDAO = Mockito.mock(UserDetailsDAO.class);
    serviceImplementation.setDao(userDetailsDAO);
    final List<UserDetails> userList=setUserDetails();


    Mockito.when(userDetailsDAO.findAll()).thenReturn(userList);
    final List<UserDetails> list=serviceImplementation.findAll();
    assertEquals(list, userList);

  }







  private List<UserDetails> setUserDetails()
  {
    final List<UserDetails> list=new ArrayList<>();

    final UserDetails uDetails=new UserDetails("bunnyreddy","9302882543", "reddy@gmail.com", "Karimnagar");
    final UserDetails uDetails1=new UserDetails("bunnyreddy","93028823", "reddygmailcom", "Karimnagar");

    list.add(uDetails);
    list.add(uDetails1);


    return list;
  }



}
