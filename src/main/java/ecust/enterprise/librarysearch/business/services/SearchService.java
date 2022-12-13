package ecust.enterprise.librarysearch.business.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.PhysicalBookRepository;
import ecust.enterprise.librarysearch.business.util.Filter;
import ecust.enterprise.librarysearch.business.util.TextFilter;

@Service
public class SearchService
{
  @Autowired
  private PhysicalBookRepository physicalBookRepository;
  
  public List<PhysicalBook> getAll() 
  {
    return physicalBookRepository.findAll();
  }
  
  public List<PhysicalBook> getByKeyword(String keyword)
  {
    return getSortedByRule(physicalBookRepository.findByKeywordInclude(keyword), keyword);
  }
  
  public List<PhysicalBook> getBySimpleSearch(String keyword, Filter psBy)
  {
    return getSortedByRule(physicalBookRepository.findByFieldInclude(keyword, psBy.toString()), keyword);
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
    return getSortedByRule(retList, keyword);
  }
  
  /**
   * Given a map of filter(<field, value>), return the filtered list of books
   * @param filterMap
   * @param textFilter
   * @return list of filtered books
   */
  public List<PhysicalBook> getByFilterMap(Map<String, String> filterMap, TextFilter textFilter)
  {
    List<PhysicalBook> retList = new ArrayList<PhysicalBook>(), temp = new ArrayList<PhysicalBook>();
    
    for (Map.Entry<String, String> entry : filterMap.entrySet())
    {
      if (entry.getValue() != "")
      {
        // entry: <field, keyword> 
        switch (textFilter.toString())
        {
          case "INCLUDE":
            temp = physicalBookRepository.findByFieldInclude(entry.getValue(), entry.getKey());
            break;
          case "ACCURATE":
            temp = physicalBookRepository.findByFieldAccurate(entry.getValue(), entry.getKey());
            break;
          case "BEGIN":
            temp = physicalBookRepository.findByFieldBegin(entry.getValue(), entry.getKey());
            break;
          default:
            break;
        }
      }
      
      if (!temp.isEmpty())
      {
        if (retList.isEmpty())
        {
          retList.addAll(temp);
        }
        else 
        {
          retList.retainAll(temp);
        }
      }
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
          temp = physicalBookRepository.findByFieldBegin(keyword, filter.toString());
          break;
        case "INCLUDE":
          temp = physicalBookRepository.findByFieldInclude(keyword, filter.toString());
          break;
        default:
          break;
      }
      if (!temp.isEmpty())
      {
        retList.addAll(temp);
      }
    }
    return getSortedByRule(filerByYear(retList, year), keyword);
  }
  
  public PhysicalBook getByISBN(String isbn)
  {
    Optional<PhysicalBook> bookOptional = physicalBookRepository.findById(isbn);
    return bookOptional.isEmpty() ? null : bookOptional.get();  // Optional throws exception so you need this
  }
  
  /**Helper method filtering books by publish year<p>
   * Only those published after the certain year are returned
   * @param physicalBooks
   * @param year
   * @return list of filtered books
   */
  private List<PhysicalBook> filerByYear(List<PhysicalBook> physicalBooks, int year)
  {
    List<PhysicalBook> retList = new ArrayList<PhysicalBook>();
    if (!physicalBooks.isEmpty())
    {
      for (PhysicalBook physicalBook : physicalBooks)
      {
        if (physicalBook.getPubdate().equals(String.valueOf(year)))
        {
          retList.add(physicalBook);
        }
      }
    }
    return retList;
  }
  
  /**
   * Get books sorted with a specified rule
   * TODO Refactor so more rules can be specified
   * @param physicalBooks
   * @param keyword
   * @return
   */
  private List<PhysicalBook> getSortedByRule(List<PhysicalBook> physicalBooks, String keyword)
  {
    Map<PhysicalBook, Integer> unsortedMap = new HashMap<PhysicalBook, Integer>();
    // LinkedHashMap preserve the ordering of elements in which they are inserted
    LinkedHashMap<PhysicalBook, Integer> sortedMap = new LinkedHashMap<PhysicalBook, Integer>();
    for (PhysicalBook physicalBook : physicalBooks)
    {
      // Define your rule of relevance as generating value of the map here
      unsortedMap.put(physicalBook,
          physicalBook.getRelevanceString().indexOf(keyword));
    }
    
    unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue())  // Add Comparator.reverseOrder() if reverse
        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
    
    return new LinkedList<PhysicalBook>(sortedMap.keySet());
  }
  
}
