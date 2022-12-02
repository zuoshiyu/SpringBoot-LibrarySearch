package ecust.enterprise.librarysearch.business.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
  
  public List<PhysicalBook> getByOverSearch(String keyword, List<Filter> filters, TextFilter textFilter, int year)
  {
    List<PhysicalBook> retList = new ArrayList<PhysicalBook>(), temp = null;
    
    for (Filter filter : filters)
    {
      switch (textFilter.toString())
      {
        case "ACCURATE":
          temp = physicalBookRepository.findByFieldAccurate(keyword, filter.toString());
          break;
        case "BEGIN":
          temp = physicalBookRepository.findByKeywordBegin(keyword);
          break;
        case "INCLUDE":
          temp = physicalBookRepository.findByKeywordInclude(keyword);
          break;
        default:
          break;
      }
      if (!temp.isEmpty())
      {
        retList.addAll(temp);
      }
    }
    
    retList = filterAfterDate(retList, year);
    return retList;
  }
  
  public PhysicalBook getByISBN(String isbn)
  {
    Optional<PhysicalBook> bookOptional = physicalBookRepository.findById(isbn);
    return bookOptional.isEmpty() ? null : bookOptional.get();  // Optional throws exception so you need this
  }
  
  public List<PhysicalBook> filterAfterDate(List<PhysicalBook> list, int year)
  {
    List<PhysicalBook> retList = new ArrayList<PhysicalBook>();
    if (!list.isEmpty())
    {
      for (PhysicalBook physicalBook : list)
      {
        if (physicalBook.getPubdate() > year)
        {
          retList.add(physicalBook);
        }
      }
    }
    return retList;
  }
  
}
