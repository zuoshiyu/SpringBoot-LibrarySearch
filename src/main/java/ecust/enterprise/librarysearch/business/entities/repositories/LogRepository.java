package ecust.enterprise.librarysearch.business.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecust.enterprise.librarysearch.business.entities.Log;

public interface LogRepository extends JpaRepository<Log, Long>
{
  
}
