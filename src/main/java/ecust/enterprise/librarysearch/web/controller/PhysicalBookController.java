package ecust.enterprise.librarysearch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.services.PhysicalBookService;

@Controller
public class PhysicalBookController
{
  @Autowired
  private PhysicalBookService physicalBookService;

  @RequestMapping("/search")
  public ModelAndView search(String keyword)
  {
    ModelAndView mav = new ModelAndView("search-view");
    if (keyword == null)
    {
      mav.addObject("physicalBooks", physicalBookService.getAll());
    } 
    else
    {
      mav.addObject("physicalBooks",
          physicalBookService.getByKeyword(keyword));
    }
    return mav;
  }

}
