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

import com.ft.db.ExerciseRepo;
import com.ft.db.WeekRepo;
import com.ft.model.Exercise;
import com.ft.model.ExerciseDayReport;
import com.ft.model.ExerciseWeekReport;
import com.ft.model.Week;

@CrossOrigin
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
	
	@Autowired
	private ExerciseRepo exerciseRepo;
	@Autowired
	private WeekRepo weekRepo;
	
	@GetMapping("/")
	public List<Exercise> getAllExercises() {
		return exerciseRepo.findAll();
	}
	
	@GetMapping("/day-report/")
	public List<ExerciseDayReport> getExerciseDayReport() {
		return exerciseRepo.findExerciseDayReport();
	}
	
	@GetMapping("/week-report/")
	public List<ExerciseWeekReport> getExerciseWeekReport() {
		return exerciseRepo.findExerciseWeekReport();
	}
	
	// getById  - "/api/exercises/{id}
	//			- return: Exercise 
	@GetMapping("/{id}")
	public Optional<Exercise> getExerciseById(@PathVariable int id) {
		// check if exercise exists for id
		// if yes, return exercise
		// if no, return NotFound
		Optional<Exercise> a = exerciseRepo.findById(id);
		if (a.isPresent()) {
			return a;
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Exercise not found for id: "+id);
		}
	}
	
	// post 	- "/api/exercises/" (exercise will be in the RequestBody)
	//			- return: Exercise
	@PostMapping("")
	public Exercise addExercise(@RequestBody Exercise exercise) {
		setWeekNum(exercise);
		return exerciseRepo.save(exercise);
	}
	
	// put 		- "/api/exercises/{id} (exercise passed in RB)
	//			- return: NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putExercise(@PathVariable int id, @RequestBody Exercise exercise) {
		// check id passed vs id in instance
		// - BadRequest if not exist
		if (id != exercise.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exercise id mismatch vs URL.");
		}
		// if exercise exists, update, else not found
		else if (exerciseRepo.existsById(exercise.getId())) {
			setWeekNum(exercise);
			exerciseRepo.save(exercise);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Exercise not found for id: "+id);
		}
	}
	
	private void setWeekNum(Exercise ex) {
		// set week based on date of exercise
		Week w = weekRepo.findByEndDateGreaterThanEqualAndStartDateLessThanEqual(ex.getExerciseDate(), ex.getExerciseDate());
		ex.setWeek(w);
	}
	
	
	// delete 	- "/api/exercises/{id}
	//			- return: NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)	
	public void deleteExercise(@PathVariable int id) {
		// check existence, then remove exercise
		// return NotFound if not exist
		if (exerciseRepo.existsById(id)) {
			exerciseRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Exercise not found for id: "+id);
		}
	}

}
