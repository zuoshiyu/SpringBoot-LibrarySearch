package ecust.enterprise.librarysearch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.entities.DigitalBook;
import ecust.enterprise.librarysearch.business.services.DigitalBookService;

@Controller
public class DigitalBookController {
	
	@Autowired
	private DigitalBookService digitalBookService;
	
	@GetMapping("/showDigitalBooks")
	public ModelAndView showDigitalBooks() {
		ModelAndView mav = new ModelAndView("digitalBook/showDigitalBooks");
		mav.addObject("digitalBooks", digitalBookService.getAll());
		
		return mav;
	}
	
	@GetMapping("/addDigitalBookForm")
	public ModelAndView addDigitalBookForm() {
		ModelAndView mav = new ModelAndView("digitalBook/addDigitalBookForm");
		DigitalBook newDigitalBook = new DigitalBook();
		mav.addObject("newDigitalBook", newDigitalBook);
		
		return mav;
	}
	
	@PostMapping("/addDigitalBook")
	public String addDigitalBook(
			@ModelAttribute DigitalBook newDigitalBook) {
		digitalBookService.add(newDigitalBook);
		// At least redirect! Or there's nothing to show.
		return "redirect:/showDigitalBooks";
	}
	
	@GetMapping("/deleteDigitalBook")
	public String deleteDigitalBook(
			@RequestParam("digitalBookId") String digitalBookId) {
		digitalBookService.deleteById(digitalBookId);
		return "redirect:/showDigitalBooks";
	}
	
	@GetMapping("/updateDigitalBookForm")
	public ModelAndView updateDigitalBookForm(
			@RequestParam("digitalBookId") String digitalBookId) {
		ModelAndView mav = new ModelAndView("digitalBook/updateDigitalBookForm");
		DigitalBook digitalBook = digitalBookService.getById(digitalBookId);
		mav.addObject("digitalBook", digitalBook);
		
		return mav;
	}
	
	@PostMapping("/updateDigitalBook")
	public String updateDigitalBook(
			@ModelAttribute("digitalBook") DigitalBook digitalBook) {
		digitalBookService.add(digitalBook);
		return "redirect:/showDigitalBooks";
	}
	
}
