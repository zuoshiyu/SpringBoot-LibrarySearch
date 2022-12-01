package ecust.enterprise.librarysearch.business.util;

import java.io.File;

public class FileUtil
{
  public static void createFolder(String path)
  {
    try
    {
      new File(path).mkdirs();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static boolean isFolderExist(String path)
  {
    File folder = new File(path);
    return folder.exists() && folder.isDirectory();
  }
}
