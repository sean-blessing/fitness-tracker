package com.ft.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ft.db.UserRepo;
import com.ft.model.User;
import com.ft.model.UserLogin;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserRepo userRepo;

	public UserController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@GetMapping("/")
	public List<User> getAllUsers() {
		System.out.println("UserController.getAllUsers()");

		List<User> users = userRepo.findAll();
		System.out.println("got some users... "+ users.size() + " of 'em!");
		return users;
	}
	
	// getById  - "/api/users/{id}
	//			- return: User 
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable int id) {
		// check if user exists for id
		// if yes, return user
		// if no, return NotFound
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u;
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "User not found for id: "+id);
		}
	}
	
	// post 	- "/api/users/" (user will be in the RequestBody)
	//			- return: User
	@PostMapping("")
	public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}
	
	// put 		- "/api/users/{id} (user passed in RB)
	//			- return: NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putUser(@PathVariable int id, @RequestBody User user) {
		// check id passed vs id in instance
		// - BadRequest if not exist
		if (id != user.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id mismatch vs URL.");
		}
		// if user exists, update, else not found
		else if (userRepo.existsById(user.getId())) {
			userRepo.save(user);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "User not found for id: "+id);
		}
	}
	
	
	// delete 	- "/api/users/{id}
	//			- return: NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)	
	public void deleteUser(@PathVariable int id) {
		// check existence, then remove user
		// return NotFound if not exist
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "User not found for id: "+id);
		}
	}
	
	// login  - "/api/users/login (user will be in the RequestBody)
	//			- return: User 
	@PostMapping("/login")
	public Optional<User> login(@RequestBody UserLogin userLogin) {
		// call DB to verify email and pwd exists
		Optional<User> u = userRepo.findByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());
		if (u.isPresent()) {
			return u;
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "User not found for email and pwd combo.");
		}
	}
	
}
