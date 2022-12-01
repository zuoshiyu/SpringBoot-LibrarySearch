package ecust.enterprise.librarysearch;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer
{

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
}
