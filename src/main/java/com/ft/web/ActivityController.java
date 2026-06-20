package com.ft.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ft.db.ActivityRepo;
import com.ft.model.Activity;

@CrossOrigin
@RestController
@RequestMapping("/api/activities")
public class ActivityController {
	
	private final ActivityRepo activityRepo;
	
	public ActivityController(ActivityRepo activityRepo) {
		this.activityRepo = activityRepo;
	}
	
	@GetMapping("/")
	public List<Activity> getAllActivities() {
		return activityRepo.findAll();
	}
	
	// getById  - "/api/activities/{id}
	//			- return: Activity 
	@GetMapping("/{id}")
	public Optional<Activity> getActivityById(@PathVariable int id) {
		// check if activity exists for id
		// if yes, return activity
		// if no, return NotFound
		Optional<Activity> a = activityRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Activity not found for id: "+id);
		}
	}
	
	// post 	- "/api/activities/" (activity will be in the RequestBody)
	//			- return: Activity
	@PostMapping("")
	public Activity addActivity(@RequestBody Activity activity) {
		return activityRepo.save(activity);
	}
	
	// put 		- "/api/activities/{id} (activity passed in RB)
	//			- return: NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putActivity(@PathVariable int id, @RequestBody Activity activity) {
		// check id passed vs id in instance
		// - BadRequest if not exist
		if (id != activity.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Activity id mismatch vs URL.");
		}
		// if activity exists, update, else not found
		else if (activityRepo.existsById(activity.getId())) {
			activityRepo.save(activity);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Activity not found for id: "+id);
		}
	}
	
	
	// delete 	- "/api/activities/{id}
	//			- return: NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)	
	public void deleteActivity(@PathVariable int id) {
		// check existence, then remove activity
		// return NotFound if not exist
		if (activityRepo.existsById(id)) {
			activityRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Activity not found for id: "+id);
		}
	}

}
