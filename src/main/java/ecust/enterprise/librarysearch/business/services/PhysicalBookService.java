package ecust.enterprise.librarysearch.business.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.PhysicalBookRepository;
import ecust.enterprise.librarysearch.business.util.Filter;
import ecust.enterprise.librarysearch.business.util.TextFilter;

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
    return physicalBookRepository.findByKeywordInclude(keyword);
  }
  
  public List<PhysicalBook> getBySimpleSearch(String keyword, Filter psBy)
  {
    return physicalBookRepository.findByFieldInclude(keyword, psBy.toString());
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
  
  public List<PhysicalBook> getByTextSearch(String keyword, TextFilter textFilter)
  {
    List<PhysicalBook> retList = new ArrayList<PhysicalBook>();
  
    switch (textFilter.toString())
    {
      case "ACCURATE":
        retList = physicalBookRepository.findByKeywordAccurate(keyword);
        break;
      case "BEGIN":
        retList = physicalBookRepository.findByKeywordBegin(keyword);
        break;
      case "INCLUDE":
        retList = physicalBookRepository.findByKeywordInclude(keyword);
        break;
      default:
        break;
    }
    return retList;
  }
}
