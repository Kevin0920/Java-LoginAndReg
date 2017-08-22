package com.example.auth.controllers;

import java.security.Principal;
import java.util.*;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.auth.models.Event;
import com.example.auth.models.Message;
import com.example.auth.models.User;
import com.example.auth.services.UserService;
import com.example.auth.validator.UserValidator;




@Controller
public class Users {
    private UserService userService;
    private UserValidator userValidator;
    
    public Users(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    
    private String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA",
            "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
            "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
            "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

    
    
    // using principle to get the current username 
    @RequestMapping(value = {"/", "/home"})
    public String home(@ModelAttribute("message") Message message, @ModelAttribute("event") Event event, @ModelAttribute("user") User user, Principal principal, Model model) {
    	
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUserEmail(username));
        model.addAttribute("states", states);
        return "homePage";
    }
    

    
    @PostMapping(value="/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "loginPage";
        }
        model.addAttribute("states", states);
        userService.saveUser(user);
        return "redirect:/";
    }
    
    
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(@Valid @ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
        model.addAttribute("states", states);
        return "loginPage";
    }
    
   
    
  @RequestMapping(value="/events", method = RequestMethod.GET)
  public String events(@ModelAttribute("event") Event event, Principal principal, Model model) {
	  String username = principal.getName();
	  
//	  	System.out.println(event);    		
	  	
		model.addAttribute("currentUser", userService.findByUserEmail(username));
		model.addAttribute("states", states);
	    model.addAttribute("events", userService.allEvents());
	   		return "homePage";
	  }
  
    // create one new event 
   @PostMapping(value="/event")
    public String newEvent(@ModelAttribute("event") Event event, BindingResult result, Model model) {
		userService.addEvent(event);
		return "redirect:/events";
    }
   
   
   
  // all events with each id can access to this page with message display
   @RequestMapping(value="/events/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model, @ModelAttribute("message") Message message, BindingResult result) {
	   	model.addAttribute("states", states);
		model.addAttribute("event", userService.find(id));
		return "detail";
	}
   
  
   
   
   // one event for editing 
   @RequestMapping(value="/events/edit/{id}", method = RequestMethod.GET)
	public String editEvent(@PathVariable("id") Long id, Model model, @ModelAttribute("event") Event event, BindingResult result, Principal principal) {
	   	String username = principal.getName();
		if (userService.findByUserEmail(username).getId() != userService.detail(id).getHost().getId()) {
			return "redirect: events";
		}
		else {
			model.addAttribute("event", event);
			model.addAttribute("states", states);
			return "editEvent";
		}
	}
   
   @PostMapping(value="/events/edit/{id}")
   public String update(@Valid @ModelAttribute("event") Event event, BindingResult result, @PathVariable("id") Long id) {
	   if (result.hasErrors()) {
           return "editEvent";
       }else{
           userService.updateEvent(event);
           return "redirect:/events";
       }
   }
   
   @PostMapping(value="/message")
   public String newMessage(@ModelAttribute("message") Message message, Model model) {
	   model.addAttribute("message", message);
	   userService.createMessage(message);
	   return "redirect:/events/" + message.getEvent().getId();
   }
   

   
  // delete route 
  @RequestMapping(value="/events/delete/{id}", method = RequestMethod.GET)
  public String delete(@PathVariable("id") Long id) {
	  userService.delete(id);
	  return "redirect:/events";
  }

  @RequestMapping(value="/join/{id}")
  public String eventJoin(@PathVariable(value="id") Long id, Principal principal) {
	  userService.eventJoin(id, userService.findByUserEmail(principal.getName()).getId());
	  return "redirect:/events";
  }
  
  @RequestMapping(value="/cancel/{id}")
  public String cancelEvent(@PathVariable(value="id") Long id, Principal principal) {
	  userService.cancelEvent(id, userService.findByUserEmail(principal.getName()).getId());
	  return "redirect:/events";

  }

  
    
    
    

    

}
