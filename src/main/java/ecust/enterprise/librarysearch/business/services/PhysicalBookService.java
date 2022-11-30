package ecust.enterprise.librarysearch.business.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.PhysicalBookRepository;
import ecust.enterprise.librarysearch.business.util.Filter;

@Service
public class PhysicalBookService
{
  @Autowired
  private PhysicalBookRepository physicalBookRepository;
  
  public List<PhysicalBook> getAll() 
  {
    return physicalBookRepository.findAll();
  }
  
  public List<PhysicalBook> getByKeyword(String keyword)
  {
    return physicalBookRepository.findByKeyword(keyword);
  }
  
  public List<PhysicalBook> getBySimpleSearch(String keyword, Filter psBy)
  {
    List<PhysicalBook> retList = null;
    retList = physicalBookRepository.findByFieldInclude(keyword, psBy.toString());
    return retList;
  }
  
  public List<PhysicalBook> getByAdvancedSearch(String keyword, List<Filter> filters)
  {
    List<PhysicalBook> retList = new ArrayList<PhysicalBook>(), temp;
    for (Filter filter : filters)
    {
      temp = getBySimpleSearch(keyword, filter);
      if (!temp.isEmpty())
      {
        retList.addAll(temp);
      }
    }
    if (!retList.isEmpty())
    {
      retList = new ArrayList<>(new HashSet<>(retList));  // Remove duplicates
    }
    return retList;
  }
  
}
