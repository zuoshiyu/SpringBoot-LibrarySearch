package ecust.enterprise.librarysearch.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.services.DigitalBookService;
import ecust.enterprise.librarysearch.business.services.HotWordService;
import ecust.enterprise.librarysearch.business.services.SearchService;
import ecust.enterprise.librarysearch.business.util.Filter;
import ecust.enterprise.librarysearch.business.util.ListWrapper;
import ecust.enterprise.librarysearch.business.util.TextFilter;

@Controller
public class SearchController
{
  @Autowired
  private SearchService searchService;
  @Autowired
  private HotWordService hotWordService;

  @GetMapping("/")
  public String redirect()
  {
    return "redirect:/simplesearch";
  }
  
  @GetMapping("/simplesearch")
  public ModelAndView simpleSearch(String keyword, Filter filter)  {
    ModelAndView mav = new ModelAndView("search/simple-search-view");
    
    mav.addObject("filters", EnumSet.allOf(Filter.class));
    if (keyword == null)
    {
      mav.addObject("physicalBooks", searchService.getAll());
    } 
    else
    {
      mav.addObject("physicalBooks",
          searchService.getBySimpleSearch(keyword, filter));
      mav.addObject("keyword", keyword);
      hotWordService.update(keyword);
    }
    return mav;
  }
  
  @GetMapping("/advancedsearch")
  public ModelAndView showAdvancedSearch()
  {
    ModelAndView mav = new ModelAndView("search/advanced-search-view");
    mav.addObject("physicalBooks", searchService.getAll());
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
    ModelAndView mav = new ModelAndView("search/advanced-search-view");
    mav.addObject("physicalBooks",
        searchService.getByAdvancedSearch(keyword,
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
    ModelAndView mav = new ModelAndView("search/text-search-view");

    if (keyword == null)
    {
      mav.addObject("physicalBooks", searchService.getAll());
    } 
    else 
    {
      mav.addObject("physicalBooks", searchService.getByTextSearch(keyword, textFilter));
      mav.addObject("keyword", keyword);
      hotWordService.update(keyword);
    }
    mav.addObject("textFilters", EnumSet.allOf(TextFilter.class));

    return mav;
  }
  
  @GetMapping("/hotwordsearch")
  public ModelAndView hotwordSearch(String keyword)
  {
    ModelAndView mav = new ModelAndView("search/hotword-search-view");
    
    if (keyword == null)
    {
      mav.addObject("physicalBooks", searchService.getAll());
    } 
    else
    {
      mav.addObject("physicalBooks", searchService.getByKeyword(keyword));
      mav.addObject("keyword", keyword);
      hotWordService.update(keyword);
    }
    mav.addObject("hotwords", hotWordService.getWithinMonth());
    
    return mav;
  }
  
  @GetMapping("/oversearch")
  public ModelAndView showOverSearch()
  {
    ModelAndView mav = new ModelAndView("search/over-search-view");
    mav.addObject("physicalBooks", searchService.getAll());
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
    ModelAndView mav = new ModelAndView("search/over-search-view");
    mav.addObject("physicalBooks",
        searchService.getByOverSearch(keyword,
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
  
  @GetMapping("/specifiedsearch")
  public ModelAndView showSpecifiedSearch()
  {
    ModelAndView mav = new ModelAndView("search/specified-search-view");
    
    return mav;
  }
  
  @PostMapping("/specifiedsearch")
  public ModelAndView specifiedSearch(@RequestParam Map<String, String> filterMap)  // use @RequestParam to receive a map
  {
    ModelAndView mav = new ModelAndView("search/specified-search-view");
    mav.addObject("physicalBooks",
        searchService.getBySpecifiedSearch(filterMap));
    
    return mav;
  }
  
  private int convertDateToYear(String date)
  {
    return Integer.valueOf(date.substring(0, 3));
  }
}
