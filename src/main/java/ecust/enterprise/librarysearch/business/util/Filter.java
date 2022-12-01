package ecust.enterprise.librarysearch.business.util;

public enum Filter
{
  title("title"),
  author("author"),
  isbn("isbn"),
  publisher("publisher"),;

  final private String value;
  
  Filter()
  {
    this.value = "";
  }
  
  Filter(String value)  {
    this.value = value;}
  
  public String getValue()
  {
    return value;
  }
}
