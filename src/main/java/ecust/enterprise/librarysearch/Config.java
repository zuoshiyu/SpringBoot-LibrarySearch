package ecust.enterprise.librarysearch;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ecust.enterprise.librarysearch.business.services.UserService;

@Configuration
@EnableWebSecurity
public class Config implements WebMvcConfigurer
{
  @Autowired
  UserService userService;
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry)
  {
    exposeDirectory("qrcode", registry);
    // The end of directory must not be "/"
  }

  /**
   * Add a resource handler for a folder
   * @param dirName
   * @param registry
   */
  private void exposeDirectory(String dirName, ResourceHandlerRegistry registry)
  {
    Path uploadDir = Paths.get(dirName);
    String uploadPath = uploadDir.toFile().getAbsolutePath();

    if (dirName.startsWith("../"))
      dirName = dirName.replace("../", "");

    registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + uploadPath + "/");
  }
  
//  @Bean
//  public UserDetailsService userDetailsService() {
//    return userService;
//  }
  
  /**
   * Sets up authorization rules
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
    .authorizeHttpRequests()
    .requestMatchers("/show*", "/add*", "/update*", "/delete*").hasRole("ADMIN") 
    .requestMatchers("/*search", "/book-info*", "/qrcode/*").hasRole("USER")
    .requestMatchers("/", "/css/*", "/js/*", "/img/*").permitAll()
    .and().formLogin();
    return http.build();
  }
  
  @Bean
  PasswordEncoder getPasswordEncoder() {
      return NoOpPasswordEncoder.getInstance();
  }
}
