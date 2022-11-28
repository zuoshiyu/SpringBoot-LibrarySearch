package ecust.enterprise.librarysearch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.services.DigitalBookService;

@Controller
public class DigitalBookController
{
  private final DigitalBookService digitalBookService;
  
  @Autowired
  public DigitalBookController(DigitalBookService digitalBookService)
  {
    this.digitalBookService = digitalBookService;
  }
  
  @GetMapping("/show-digital-books")
  public ModelAndView showDigitalBooks()
  {
    ModelAndView mav = new ModelAndView("show-digital-books");
    
    mav.addObject("digitalBooks", digitalBookService.getAll());
    
    return mav;
  }
}
