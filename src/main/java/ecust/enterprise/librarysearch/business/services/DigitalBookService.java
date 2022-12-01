package ecust.enterprise.librarysearch.business.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.DigitalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.DigitalBookRepository;

@Service
public class DigitalBookService
{
  @Autowired
  private DigitalBookRepository digitalBookRepository;
  
  public List<DigitalBook> getAll()
  {
    return digitalBookRepository.findAll();
  }
  
  public DigitalBook getByISBN(String isbn)
  {
    Optional<DigitalBook> bookOptional = digitalBookRepository.findById(isbn);
    return bookOptional.isPresent() ? bookOptional.get() : null;
  }
}
