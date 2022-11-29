package ecust.enterprise.librarysearch.business.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;

public interface PhysicalBookRepository extends JpaRepository<PhysicalBook, String>
{
//  @Query("SELECT x FROM PhysicalBook WHERE x.isbn = ?1")
//  List<PhysicalBook> findByIsbn(String isbn);
  @Query(value = "SELECT * FROM physical_book x WHERE x.title LIKE %:keyword% "
      + "or x.author LIKE %:keyword% "
      + "or x.publisher LIKE %:keyword% "
      + "or x.publocation LIKE %:keyword% "
      + "or x.subtitle LIKE %:keyword% ", nativeQuery = true)
  List<PhysicalBook> findByKeyword(@Param("keyword") String keyword);
}
