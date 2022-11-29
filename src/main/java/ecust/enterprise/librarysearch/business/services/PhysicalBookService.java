package ecust.enterprise.librarysearch.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.PhysicalBookRepository;
import ecust.enterprise.librarysearch.business.util.PhysicalSearchBy;

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
  
  public List<PhysicalBook> getBySimpleSearch(String keyword, PhysicalSearchBy psBy)
  {
    List<PhysicalBook> retList = null;
    retList = physicalBookRepository.findByFieldInclude(keyword, psBy.toString());
    return retList;
  }
  
}
