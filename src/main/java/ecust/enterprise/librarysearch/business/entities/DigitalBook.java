package ecust.enterprise.librarysearch.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "digital_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DigitalBook
{
  private String title;
  private String author;
  private String publisher;
  private String pubdate;
  @Id
  private String isbn;
  private String bookurl;
  private String source;
}
