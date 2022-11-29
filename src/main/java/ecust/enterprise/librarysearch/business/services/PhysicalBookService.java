package ecust.enterprise.librarysearch.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.PhysicalBookRepository;

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
}
