package ecust.enterprise.librarysearch.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nolightleft
 *
 */
@Entity
@Table(name = "physical_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalBook
{
  private String index;
  private String title;
  private String author;
  private String publisher;
  private String publocation;
  private String pubdate;
  private int pagenum;
  @Id
  private String isbn;
  private int price;
  private int copynum;
  private String type;
  private String language;
  private String subtitle;
}
