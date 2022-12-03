package ecust.enterprise.librarysearch.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import ecust.enterprise.librarysearch.business.entities.DigitalBook;
import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.services.DigitalBookService;
import ecust.enterprise.librarysearch.business.services.HotWordService;
import ecust.enterprise.librarysearch.business.services.PhysicalBookService;
import ecust.enterprise.librarysearch.business.util.QRCodeCreator;

@Controller
public class InfoController
{
  @Autowired
  private PhysicalBookService physicalBookService;
  @Autowired
  private DigitalBookService digitalBookService;
  @Autowired
  private HotWordService hotWordService;
  
  @GetMapping("/book-info")
  public ModelAndView showBookInfo(String bookId)
  {
    ModelAndView mav = new ModelAndView("book-info-view");
    PhysicalBook physicalBook = physicalBookService.getByISBN(bookId);
    mav.addObject("book", physicalBook);
    DigitalBook digitalBook = digitalBookService.getByISBN(bookId);
    if (digitalBook == null)
    {
      mav.addObject("digitalLink", "There's no online version for this book yet");
    }
    else 
    {
      mav.addObject("digitalLink", digitalBook.getBookurl());
    }
    
    
    try
    {
      Path uploadPath = new File("qrcode").toPath();
      if (!Files.exists(uploadPath))
      {
        Files.createDirectories(uploadPath);
      }
      QRCodeCreator.createQRCodeForObject(physicalBook, physicalBook.getQRCodePath());
    }
    catch (WriterException | IOException e)
    {
      e.printStackTrace();
    }
    return mav;
  }
  
  @GetMapping("/There's no online version for this book yet")
  public String noOnlineBook()
  {
    return "redirect:simplesearch";
  }
}
