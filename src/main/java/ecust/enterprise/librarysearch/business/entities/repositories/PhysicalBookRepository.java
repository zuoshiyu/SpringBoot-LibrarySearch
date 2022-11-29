package ecust.enterprise.librarysearch.business.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;

public interface PhysicalBookRepository extends JpaRepository<PhysicalBook, String>
{
  static final String CASE_STRING = "\nCASE\n"
      + " WHEN :field = 'title' THEN title\n"
      + " WHEN :field = 'author' THEN author\n"
      + " WHEN :field = 'isbn' THEN isbn\n"
      + " WHEN :field = 'publisher' THEN publisher\n"
      + " ELSE NULL\n"
      + " END\n";
  
  @Query(nativeQuery = true, value = "SELECT * FROM physical_book x WHERE x.title LIKE %:keyword% "
      + "or x.author LIKE %:keyword% "
      + "or x.publisher LIKE %:keyword% "
      + "or x.subtitle LIKE %:keyword% ")
  List<PhysicalBook> findByKeyword(@Param("keyword") String keyword);
  

  @Query(value = "SELECT * FROM physical_book x WHERE " + CASE_STRING + " LIKE %:keyword%", nativeQuery = true)
  List<PhysicalBook> findByFieldAccurate(@Param("keyword") String keyword, @Param("field") String field);

  @Query(value = "SELECT * FROM physical_book x WHERE " + CASE_STRING + " LIKE %:keyword%", nativeQuery = true)
  List<PhysicalBook> findByFieldBegin(@Param("keyword") String keyword, @Param("field") String field);
  
  @Query(value = "SELECT * FROM physical_book x WHERE " + CASE_STRING + " LIKE %:keyword%", nativeQuery = true)
  List<PhysicalBook> findByFieldInclude(@Param("keyword") String keyword, @Param("field") String field);
  // use native query, and you use actual table name 
}
