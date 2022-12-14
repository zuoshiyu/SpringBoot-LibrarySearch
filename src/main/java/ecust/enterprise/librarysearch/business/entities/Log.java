package ecust.enterprise.librarysearch.business.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String searchString;
  private Date date;
  
  public Log(String searchString, Date date)
  {
    this.searchString = searchString;
    this.date = date;
  }
  
}
