package com.example.auth.services;


import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.models.Event;
import com.example.auth.models.Message;
import com.example.auth.models.User;
import com.example.auth.repositories.EventRepository;
import com.example.auth.repositories.MessageRepository;
import com.example.auth.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private MessageRepository messageRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    public UserService(UserRepository userRepository, EventRepository eventRepository,  MessageRepository messageRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.messageRepository = messageRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    // 1
    public void saveUser(User user) {
		// bcrypt is working before saving the user to the db, otherwise, will have a error 
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
	}
     
   
    public User findByEmail(String email) {
        return userRepository.findByUsername(email);
    }




	public void addEvent(Event event) {
		eventRepository.save(event);
	}


	public List<Event> allEvents() {
		return (List<Event>) eventRepository.findAll();
	}


	public Event find(Long id) {
		return eventRepository.findOne(id);
	}

	
	public void updateEvent(Event event) {
//		Event event = eventRepository.findOne(id);
//		
		
		eventRepository.save(event);
	}


	public void delete(Long id) {
		eventRepository.delete(id);	
	}


	

// convert username to email which setting in model
	public User findByUserEmail(String email) {
		return userRepository.findByUsername(email);
	}


	public void createMessage(Message message) {
		messageRepository.save(message);
		
	}




	public Event detail(Long id) {
		return eventRepository.findOne(id);
	}



	public void eventJoin(Long id, Long user_id) {
		//find that specific event to join 
		Event event = eventRepository.findOne(id);
		//the user who joins the event
		User user = userRepository.findOne(user_id);
		event.getUsers().add(user);
		eventRepository.save(event);

	}


	public void cancelEvent(Long id, Long user_id) {
		Event event = eventRepository.findOne(id);
		User user = userRepository.findOne(user_id);
		user.getEvents().remove(event);
		userRepository.save(user);
		
		
		
	}
	
    
    
    
    
    
    
    
    
    
}

