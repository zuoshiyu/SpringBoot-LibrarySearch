package ecust.enterprise.librarysearch.business.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecust.enterprise.librarysearch.business.entities.DigitalBook;

public interface DigitalBookRepository extends JpaRepository<DigitalBook, String>
{
//  @Query("SELECT s FROM DigitalBook s WHERE s.isbn = ?1")
//  List<DigitalBook> findByIsbn(String isbn);
}
