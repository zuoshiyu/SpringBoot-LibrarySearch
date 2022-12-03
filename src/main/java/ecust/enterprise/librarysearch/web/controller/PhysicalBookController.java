package ecust.enterprise.librarysearch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.entities.PhysicalBook;
import ecust.enterprise.librarysearch.business.services.PhysicalBookService;
import ecust.enterprise.librarysearch.business.services.SearchService;

@Controller
public class PhysicalBookController {
	
	@Autowired
	private PhysicalBookService physicalBookService;
	
	@GetMapping("/showPhysicalBooks")
	public ModelAndView showPhysicalBooks() {
		ModelAndView mav = new ModelAndView("physicalBook/showPhysicalBooks");
		mav.addObject("physicalBooks", physicalBookService.getAll());
		
		return mav;
	}
	
	@GetMapping("/addPhysicalBookForm")
	public ModelAndView addPhysicalBookForm() {
		ModelAndView mav = new ModelAndView("physicalBook/addPhysicalBookForm");
		PhysicalBook newPhysicalBook = new PhysicalBook();
		mav.addObject("newPhysicalBook", newPhysicalBook);
		
		return mav;
	}
	
	@PostMapping("/addPhysicalBook")
	public String addPhysicalBook(
			@ModelAttribute PhysicalBook newPhysicalBook) {
		physicalBookService.add(newPhysicalBook);
		// At least redirect! Or there's nothing to show.
		return "redirect:/showPhysicalBooks";
	}
	
	@GetMapping("/deletePhysicalBook")
	public String deletePhysicalBook(
			@RequestParam("physicalBookId") String physicalBookId) {
		physicalBookService.deleteById(physicalBookId);
		return "redirect:/showPhysicalBooks";
	}
	
	@GetMapping("/updatePhysicalBookForm")
	public ModelAndView updatePhysicalBookForm(
			@RequestParam("physicalBookId") String physicalBookId) {
		ModelAndView mav = new ModelAndView("physicalBook/updatePhysicalBookForm");
		PhysicalBook physicalBook = physicalBookService.getById(physicalBookId);
		mav.addObject("physicalBook", physicalBook);
		
		return mav;
	}
	
	@PostMapping("/updatePhysicalBook")
	public String updatePhysicalBook(
			@ModelAttribute("physicalBook") PhysicalBook physicalBook) {
		physicalBookService.add(physicalBook);
		return "redirect:/showPhysicalBooks";
	}
	
}