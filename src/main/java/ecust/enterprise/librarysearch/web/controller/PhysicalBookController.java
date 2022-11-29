package ecust.enterprise.librarysearch.web.controller;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.services.PhysicalBookService;
import ecust.enterprise.librarysearch.business.util.PhysicalSearchBy;

@Controller
public class PhysicalBookController
{
  @Autowired
  private PhysicalBookService physicalBookService;
  private ModelAndView simpleSearchMAV = new ModelAndView("simple-search-view");

  @GetMapping("/simplesearch")
  public ModelAndView simpleSearch(String keyword, PhysicalSearchBy psBy)
  {
    simpleSearchMAV.addObject("psBys", EnumSet.allOf(PhysicalSearchBy.class));
    if (keyword == null)
    {
      simpleSearchMAV.addObject("physicalBooks", physicalBookService.getAll());
    } 
    else
    {
      simpleSearchMAV.addObject("physicalBooks",
          physicalBookService.getBySimpleSearch(keyword, psBy));
    }
    return simpleSearchMAV;
  }

}
