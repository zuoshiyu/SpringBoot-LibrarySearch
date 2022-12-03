package ecust.enterprise.librarysearch.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.services.DigitalBookService;
import ecust.enterprise.librarysearch.business.services.HotWordService;
import ecust.enterprise.librarysearch.business.services.PhysicalBookService;
import ecust.enterprise.librarysearch.business.util.Filter;
import ecust.enterprise.librarysearch.business.util.ListWrapper;
import ecust.enterprise.librarysearch.business.util.TextFilter;

@Controller
public class SearchController
{
  @Autowired
  private PhysicalBookService physicalBookService;
  @Autowired
  private DigitalBookService digitalBookService;
  @Autowired
  private HotWordService hotWordService;

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
      mav.addObject("keyword", keyword);
      hotWordService.update(keyword);
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
                .stream().map(Filter::toString).toList())));
    return mav;
  }
  
  @PostMapping(value = "/advancedsearch")
  public ModelAndView advancedSearch(String keyword,
      @ModelAttribute("filterListWrapper") ListWrapper<String> filterListWrapper)
  {
    ModelAndView mav = new ModelAndView("advanced-search-view");
    mav.addObject("physicalBooks",
        physicalBookService.getByAdvancedSearch(keyword,
            filterListWrapper.getList().stream().map(Filter::valueOf)
                .toList()));
    mav.addObject("filterListWrapper",
        new ListWrapper<String>(
            new ArrayList<String>(EnumSet.allOf(Filter.class)
                .stream().map(Filter::toString).toList())));
    mav.addObject("keyword", keyword);
    hotWordService.update(keyword);
    
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
      mav.addObject("keyword", keyword);
      hotWordService.update(keyword);
    }
    mav.addObject("textFilters", EnumSet.allOf(TextFilter.class));

    return mav;
  }
  
  @GetMapping("/hotwordsearch")
  public ModelAndView hotwordSearch(String keyword)
  {
    ModelAndView mav = new ModelAndView("hotword-search-view");
    
    if (keyword == null)
    {
      mav.addObject("physicalBooks", physicalBookService.getAll());
    } 
    else
    {
      mav.addObject("physicalBooks", physicalBookService.getByKeyword(keyword));
      mav.addObject("keyword", keyword);
      hotWordService.update(keyword);
    }
    mav.addObject("hotwords", hotWordService.getWithinMonth());
    
    return mav;
  }
  
  @GetMapping("/oversearch")
  public ModelAndView showOverSearch()
  {
    ModelAndView mav = new ModelAndView("over-search-view");
    mav.addObject("physicalBooks", physicalBookService.getAll());
    mav.addObject("filterListWrapper",
        new ListWrapper<String>(
            new ArrayList<String>(EnumSet.allOf(Filter.class)
                .stream().map(Filter::toString).toList())));
    mav.addObject("textFilters", EnumSet.allOf(TextFilter.class));
    mav.addObject("hotwords", hotWordService.getWithinMonth());
    
    return mav;
  }
  
  @PostMapping("/oversearch")
  public ModelAndView overSearch(String keyword,
      @ModelAttribute("filterListWrapper") ListWrapper<String> filterListWrapper,
      TextFilter textFilter, String date)
  {
    ModelAndView mav = new ModelAndView("over-search-view");
    mav.addObject("physicalBooks",
        physicalBookService.getByOverSearch(keyword,
            filterListWrapper.getList().stream().map(Filter::valueOf).toList(), textFilter,
            // the request consists of only string type objects so you need to convert manually
            convertDateToYear(date)));
    mav.addObject("filterListWrapper",
        new ListWrapper<String>(
            new ArrayList<String>(EnumSet.allOf(Filter.class)
                .stream().map(Filter::toString).toList())));
    mav.addObject("textFilters", EnumSet.allOf(TextFilter.class));
    mav.addObject("keyword", keyword);
    mav.addObject("hotwords", hotWordService.getWithinMonth());
    
    return mav;
  }
  
  private int convertDateToYear(String date)
  {
    return Integer.valueOf(date.substring(0, 3));
  }
}
