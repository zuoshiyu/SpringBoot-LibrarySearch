package ecust.enterprise.librarysearch.business.entities;

import java.util.Date;

import org.apache.catalina.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hot_word")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotword implements Comparable<Hotword>
{
  @Id
  private String name;
  private int useCount;
  private Date latestUse;
  
  public void update()
  {
    useCount++;
    latestUse = new Date(System.currentTimeMillis());
  }
  
  @Override
  public int compareTo(Hotword o)
  {
//    return Integer.compare(this.useCount, o.useCount);  // sorted from small to bigger useCount
    return Integer.compare(o.useCount, this.useCount);  // sorted from bigger useCOunt
  }
}
