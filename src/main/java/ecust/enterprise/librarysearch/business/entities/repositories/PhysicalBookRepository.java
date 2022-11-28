package ecust.enterprise.librarysearch.business.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;

public interface PhysicalBookRepository extends JpaRepository<PhysicalBook, String>
{
//  @Query("SELECT x FROM PhysicalBook WHERE x.isbn = ?1")
//  List<PhysicalBook> findByIsbn(String isbn);
}
