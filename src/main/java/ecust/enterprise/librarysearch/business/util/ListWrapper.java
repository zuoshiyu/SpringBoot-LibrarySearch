package ecust.enterprise.librarysearch.business.util;

import java.util.List;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;

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
