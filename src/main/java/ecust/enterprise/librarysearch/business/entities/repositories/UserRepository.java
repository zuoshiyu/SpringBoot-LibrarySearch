package ecust.enterprise.librarysearch.business.entities.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecust.enterprise.librarysearch.business.entities.User;

public interface UserRepository extends JpaRepository<User, Long>
{
  @Query(nativeQuery = true, value = "SELECT * FROM user x WHERE x.username = :name")
  Optional<User> findByName(@Param("name") String name);
}
