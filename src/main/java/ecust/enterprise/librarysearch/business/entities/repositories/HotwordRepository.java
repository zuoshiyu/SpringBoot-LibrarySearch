package ecust.enterprise.librarysearch.business.entities.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecust.enterprise.librarysearch.business.entities.Hotword;

public interface HotwordRepository extends JpaRepository<Hotword, String>
{
  @Query(value = "SELECT * "
      + "FROM hot_word "
      + "WHERE latest_use >= (:date - INTERVAL 1 MONTH )", nativeQuery = true)
  List<Hotword> findWithinMonth(@Param("date") Date date);
}
