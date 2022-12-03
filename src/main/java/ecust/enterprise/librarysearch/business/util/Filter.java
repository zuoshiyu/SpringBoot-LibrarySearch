package ecust.enterprise.librarysearch.business.util;

public enum Filter
{
  title,
  author,
  isbn,
  publisher,
  type,
  language,
  subtitle;
  
  private boolean enabled = false;
  
  public boolean isEnabled()
  {
    return enabled;
  }
  
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }
}
