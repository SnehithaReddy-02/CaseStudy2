/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package com.philips.jsb2g3.chatbotwebservice.dal;

import java.util.List;
import com.philips.jsb2g3.chatbotwebservice.domain.UserDetails;

public interface UserDetailsDAO {

  UserDetails save(UserDetails ud);

  UserDetails findById(int id);

  List<UserDetails> findAll();

  UserDetails findByName(String name);

  void deleteById(int id);

}
