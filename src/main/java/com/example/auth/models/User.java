package com.example.auth.models;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue
    private Long id;
    // email
    @Size(min=3)
    private String username;
    
    @Size(min=5, message="Your password has to be 5 characters")
    private String password;
    @Size(min=1, message="Your first name has to be 2 characters")
    private String firstName;
    @Size(min=1, message="Your last name has to be 2 characters")
    private String lastName;
    private String location;
    private String state; 
    @Transient
    private String passwordConfirmation;
    private Date createdAt;
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_events", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;
    @OneToMany(mappedBy="host", fetch = FetchType.LAZY)
    private List<Event> eventHosted;
    
    
    public List<Event> getEventHosted() {
		return eventHosted;
	}

	public void setEventHosted(List<Event> eventHosted) {
		this.eventHosted = eventHosted;
	}

	public User() {
    }
    
	public List<Event> getEvents() {
		return events;
	}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    
    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@PrePersist
    protected void onCreate(){
    this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    this.setUpdatedAt(new Date());
    }


}
