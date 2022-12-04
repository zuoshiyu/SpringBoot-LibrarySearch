package ecust.enterprise.librarysearch.business.util;

import java.util.List;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;

/**
 * Wrapper class for wrapping list inside http request body
 * @author Nolightleft
 *
 * @param <T>
 * 
 */
public class ListWrapper<T>
{
  private List<T> list;
  
  public ListWrapper(List<T> list)
  {
    this.list = list;
  }

  public void setList(List<T> list)
  {
    this.list = list;
  }
  
  public List<T> getList()
  {
    return list;
  }
}
