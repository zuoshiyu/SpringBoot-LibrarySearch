package ecust.enterprise.librarysearch.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
  private String bookdex;
  private String title;
  private String author;
  private String publisher;
  private String publocation;
  private String pubdate;
  private int pagenum;
  @Id
  private String isbn;
  private double price;
  private int copynum;
  private String type;
  private String language;
  private String subtitle;
  private String library;
  private String info;
  private String authorInfo;
  
  @Transient
  public String getQRCodePath()
  {
    return "img/qrcode/" + isbn + ".png";
  }
  
  @Transient
  public String getRelevanceString() 
  {
    return this.title + this.subtitle + this.author + this.publisher + this.publocation;
  }
  
  @Transient
  public String getCoverPath()
  {
    return "img/cover/" + isbn + ".png";
  }
}
