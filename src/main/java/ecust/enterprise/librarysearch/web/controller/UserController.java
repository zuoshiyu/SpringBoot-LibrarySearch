package ecust.enterprise.librarysearch.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ecust.enterprise.librarysearch.business.entities.User;
import ecust.enterprise.librarysearch.business.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/showUsers")
	public ModelAndView showUsers() {
		ModelAndView mav = new ModelAndView("user/showUsers");
		mav.addObject("users", userService.getAll());
		
		return mav;
	}
	
	@GetMapping("/addUserForm")
	public ModelAndView addUserForm() {
		ModelAndView mav = new ModelAndView("user/addUserForm");
		User newUser = new User();
		mav.addObject("newUser", newUser);
		
		return mav;
	}
	
	@PostMapping("/addUser")
	public String addUser(
			@ModelAttribute User newUser) {
		userService.add(newUser);
		
		return "redirect:/showUsers";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(
			@RequestParam("userId") Long userId) {
		userService.deleteById(userId);
		return "redirect:/showUsers";
	}
	
	@GetMapping("/updateUserForm")
	public ModelAndView updateUserForm(
			@RequestParam("userId") Long userId) {
		ModelAndView mav = new ModelAndView("user/updateUserForm");
		User user = userService.getById(userId);
		mav.addObject("user", user);
		
		return mav;
	}
	
	@PostMapping("/updateUser")
	public String updateUser(
			@ModelAttribute("user") User user) {
		userService.add(user);
		return "redirect:/showUsers";
	}
	
}
