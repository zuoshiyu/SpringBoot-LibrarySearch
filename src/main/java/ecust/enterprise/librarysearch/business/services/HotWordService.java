package ecust.enterprise.librarysearch.business.services;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.Hotword;
import ecust.enterprise.librarysearch.business.entities.repositories.HotwordRepository;

@Service
public class HotWordService
{
  @Autowired
  private HotwordRepository hotwordRepository;
  
  public List<Hotword> getAll()
  {
    return hotwordRepository.findAll().stream().sorted().toList();
  }
  
  public List<Hotword> getWithinMonth()
  {
    return hotwordRepository
        .findWithinMonth(new Date(System.currentTimeMillis())).stream()
        .sorted().collect(Collectors.toList());
  }
  
  public void update(String keyword)
  {
    Optional<Hotword> temp = hotwordRepository.findById(keyword);
    // This is why you need an optional... don't rely on try catch with jpa repo find
    
    if (temp.isPresent())
    {
      temp.get().update();
    }
    else {
      hotwordRepository.save(new Hotword(keyword, 1, new Date(System.currentTimeMillis())));
    }
  }
}
