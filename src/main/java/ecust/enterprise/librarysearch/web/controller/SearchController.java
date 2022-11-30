package ecust.enterprise.librarysearch.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.services.PhysicalBookService;
import ecust.enterprise.librarysearch.business.util.ListWrapper;
import ecust.enterprise.librarysearch.business.util.TextFilter;
import ecust.enterprise.librarysearch.business.util.Filter;

@Controller
public class SearchController
{
  @Autowired
  private PhysicalBookService physicalBookService;

  @GetMapping("/simplesearch")
  public ModelAndView simpleSearch(String keyword, Filter filter)  {
    ModelAndView mav = new ModelAndView("simple-search-view");
    mav.addObject("filters", EnumSet.allOf(Filter.class));
    if (keyword == null)
    {
      mav.addObject("physicalBooks", physicalBookService.getAll());
    } 
    else
    {
      mav.addObject("physicalBooks",
          physicalBookService.getBySimpleSearch(keyword, filter));
    }
    return mav;
  }
  
  @GetMapping("/advancedsearch")
  public ModelAndView showAdvancedSearch()
  {
    ModelAndView mav = new ModelAndView("advanced-search-view");
    mav.addObject("physicalBooks", physicalBookService.getAll());
    mav.addObject("filterListWrapper",
        new ListWrapper<String>(
            new ArrayList<String>(EnumSet.allOf(Filter.class)
                .stream().map(Filter::getValue).toList())));
    return mav;
  }
  
  @PostMapping(value = "/advancedsearch")
  public ModelAndView advancedSearch(String keyword, @ModelAttribute ListWrapper<String> filterListWrapper)
  {
    ModelAndView mav = new ModelAndView("advanced-search-view");
    mav.addObject("physicalBooks",
        physicalBookService.getByAdvancedSearch(keyword,
            filterListWrapper.getList().stream().map(Filter::valueOf)
                .toList()));
    mav.addObject("filterListWrapper",
        new ListWrapper<String>(
            new ArrayList<String>(EnumSet.allOf(Filter.class)
                .stream().map(Filter::getValue).toList())));
    
    return mav;
  }
  
  @GetMapping("/textsearch")
  public ModelAndView textSearch(String keyword, TextFilter textFilter)
  {
    ModelAndView mav = new ModelAndView("text-search-view");

    if (keyword == null)
    {
      mav.addObject("physicalBooks", physicalBookService.getAll());
    } 
    else 
    {
      mav.addObject("physicalBooks", physicalBookService.getByTextSearch(keyword, textFilter));
    }
    mav.addObject("textFilters", EnumSet.allOf(TextFilter.class));

    return mav;
  }
}
