package ecust.enterprise.librarysearch.business.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "user")
public class User implements UserDetails
{

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String userName;
  private String password;
  private String roles;
  @Transient
  private List<GrantedAuthority> authorities;

  public User() {}
  
  public User(String userName, String password, String roles)
  {
    this.userName = userName;
    this.password = password;
    this.roles = roles;
    this.authorities = Arrays.stream(roles.split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
  
  public User(User user)
  {
    this.userName = user.getUsername();
    this.password = user.getPassword();
    this.roles = user.getRoles();
    this.authorities = Arrays.stream(user.getRoles().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return authorities;
  }

  public void setAuthorities(List<GrantedAuthority> authorities)
  {
    this.authorities = authorities;
  }

  @Override
  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  @Override
  public String getUsername()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getRoles()
  {
    return roles;
  }

  public void setRoles(String roles)
  {
    this.roles = roles;
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }

}
