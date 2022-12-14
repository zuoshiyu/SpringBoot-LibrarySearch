package ecust.enterprise.librarysearch.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
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

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.services.DigitalBookService;
import ecust.enterprise.librarysearch.business.services.HotWordService;
import ecust.enterprise.librarysearch.business.services.LogService;
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
  @Autowired
  private LogService logService;

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
      logService.log(keyword);
    }
    
    return mav;
  }
  
  @GetMapping("/advancedsearch")
  public ModelAndView showAdvancedSearch()
  {
    ModelAndView mav = new ModelAndView("search/advanced-search-view");
    
    return mav;
  }
  
  @PostMapping(value = "/advancedsearch")
  public ModelAndView advancedSearch(@RequestParam Map<String, String> filterMap)
  {
    ModelAndView mav = new ModelAndView("search/advanced-search-view");
    mav.addObject("physicalBooks",
        searchService.getByFilterMap(filterMap, TextFilter.INCLUDE));
    filterMap.values().forEach(hotWordService::update);
    logService.log(getNoEmptyValMap(filterMap).values());
    
    return mav;
  }
  
  @GetMapping("/textsearch")
  public ModelAndView showTextSearch()
  {
    ModelAndView mav = new ModelAndView("search/text-search-view");
    mav.addObject("textFilters", EnumSet.allOf(TextFilter.class));

    return mav;
  }
  
  @PostMapping("/textsearch")
  public ModelAndView textSearch(@RequestParam Map<String, String> filterMap)
  {
    ModelAndView mav = new ModelAndView("search/text-search-view");
    
    TextFilter textFilter = TextFilter.valueOf(filterMap.get("textFilter"));
    filterMap.remove("textFilter");
    
    mav.addObject("physicalBooks", searchService.getByFilterMap(filterMap, textFilter));
    mav.addObject("textFilters", EnumSet.allOf(TextFilter.class));
    filterMap.values().forEach(hotWordService::update);
    logService.log(getNoEmptyValMap(filterMap).values());

    return mav;
  }
  
  @GetMapping("/hotwordsearch")
  public ModelAndView hotwordSearch(String keyword)
  {
    ModelAndView mav = new ModelAndView("search/hotword-search-view");
    
    if (keyword == null)
    {
    } 
    else
    {
      mav.addObject("physicalBooks", searchService.getByKeyword(keyword));
      mav.addObject("keyword", keyword);
      hotWordService.update(keyword);
      logService.log(keyword);
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
    logService.log(keyword);
    
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
        searchService.getByFilterMap(filterMap, TextFilter.INCLUDE));
    filterMap.values().forEach(hotWordService::update);
    logService.log(filterMap.values());
    
    return mav;
  }
  
  private int convertDateToYear(String date)
  {
    return Integer.valueOf(date.substring(0, 3));
  }
  
  private Map<String, String> getNoEmptyValMap(Map<String, String> map)
  {
    Map<String, String> retMap = new LinkedHashMap<>();
    for (Map.Entry<String, String> entry : map.entrySet())
    {
      String key = entry.getKey();
      String val = entry.getValue();
      
      if (!val.isEmpty())
      {
        retMap.put(key, val);
      }
    }
    return retMap;
  }
  
//  private List<PhysicalBook> getRecommended(List<PhysicalBook> physicalBooks)
//  {
//    
//  }
}
