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

  public DigitalBook getById(String id)
  {
    Optional<DigitalBook> digitalBookOptional = digitalBookRepository
        .findById(id);
    return digitalBookOptional.isPresent() ? digitalBookOptional.get() : null;
  }

  public void add(DigitalBook digitalBook)
  {
    digitalBookRepository.save(digitalBook);
  }

  public void deleteById(String id)
  {
    digitalBookRepository.deleteById(id);
  }

  public void update(DigitalBook digitalBook)
  {
    Optional<DigitalBook> digitalBookOptional = digitalBookRepository
        .findById(digitalBook.getIsbn());
    digitalBookOptional.orElseThrow(
        () -> new IllegalStateException("DigitalBook doesn't exist!"));

    digitalBookRepository.save(digitalBook);
  }
}