package ecust.enterprise.librarysearch.business.services;

import java.util.List;
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
  
  public List<User> getAll() {
    return userRepository.findAll();
  }
  
  public User getById(Long id){
    Optional<User> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(() -> new IllegalStateException("User doesn't exist!"));
    return userOptional.get();
  }
  
  public void add(User user) {
    userRepository.save(user);
  }
  
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
  
  public void update(User user) {
    Optional<User> userOptional = userRepository.findById(user.getId());
    userOptional.orElseThrow(() -> new IllegalStateException("User doesn't exist!"));
    
    userRepository.save(user);
  }
}
