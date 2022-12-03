package ecust.enterprise.librarysearch.business.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.User;
import ecust.enterprise.librarysearch.business.entities.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService
{
  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException
  {
    Optional<User> userOptional = userRepository.findByName(username);

    userOptional.orElseThrow(
        () -> new UsernameNotFoundException(username + "Not found."));

    return userOptional.map(User::new).get();
  }
}
