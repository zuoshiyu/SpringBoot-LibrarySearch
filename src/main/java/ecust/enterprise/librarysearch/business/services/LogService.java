package ecust.enterprise.librarysearch.business.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecust.enterprise.librarysearch.business.entities.Log;
import ecust.enterprise.librarysearch.business.entities.repositories.LogRepository;

@Service
public class LogService
{
  @Autowired
  private LogRepository logRepository;
  
  public List<Log> getAll()
  {
    return logRepository.findAll();
  }
  
  public void log(String keyword)
  {
    logRepository.save(new Log(keyword,
        new Date(System.currentTimeMillis())));
  }
  
  public void log(Collection<String> keywords)
  {
    logRepository
    .save(new Log(keywords.toString(),
        new Date(System.currentTimeMillis())));
  }
}
