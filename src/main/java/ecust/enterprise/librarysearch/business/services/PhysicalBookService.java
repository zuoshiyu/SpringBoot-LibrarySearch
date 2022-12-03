package ecust.enterprise.librarysearch.business.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.entities.repositories.PhysicalBookRepository;

@Service
public class PhysicalBookService
{
  private final PhysicalBookRepository physicalBookRepository;

  @Autowired
  public PhysicalBookService(
      PhysicalBookRepository physicalBookRepository)
  {
    this.physicalBookRepository = physicalBookRepository;
  }

  public List<PhysicalBook> getAll()
  {
    return physicalBookRepository.findAll();
  }

  public PhysicalBook getById(String id)
  {
    Optional<PhysicalBook> physicalBookOptional = physicalBookRepository
        .findById(id);
    physicalBookOptional.orElseThrow(
        () -> new IllegalStateException("PhysicalBook doesn't exist!"));
    return physicalBookOptional.get();
  }

  public void add(PhysicalBook physicalBook)
  {
    physicalBookRepository.save(physicalBook);
  }

  public void deleteById(String id)
  {
    physicalBookRepository.deleteById(id);
  }

  public void update(PhysicalBook physicalBook)
  {
    Optional<PhysicalBook> physicalBookOptional = physicalBookRepository
        .findById(physicalBook.getIsbn());
    physicalBookOptional.orElseThrow(
        () -> new IllegalStateException("PhysicalBook doesn't exist!"));

    physicalBookRepository.save(physicalBook);
  }
}