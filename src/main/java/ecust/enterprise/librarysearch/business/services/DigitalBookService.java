package ecust.enterprise.librarysearch.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.DigitalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.DigitalBookRepository;

@Service
public class DigitalBookService
{
  private final DigitalBookRepository digitalBookRepository;
  
  @Autowired
  public DigitalBookService(DigitalBookRepository digitalBookRepository)
  {
    this.digitalBookRepository = digitalBookRepository;
  }
  
  public List<DigitalBook> getAll()
  {
    return digitalBookRepository.findAll();
  }
}
