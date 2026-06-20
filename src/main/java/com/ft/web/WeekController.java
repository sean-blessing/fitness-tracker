package com.ft.web;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.ft.db.WeekRepo;
import com.ft.model.Week;

@CrossOrigin
@RestController
@RequestMapping("/api/weeks")
public class WeekController {
	
	private final WeekRepo weekRepo;

	WeekController(WeekRepo weekRepo) {
		this.weekRepo = weekRepo;
	}
	
	@GetMapping("/")
	public List<Week> getAllWeeks() {
		return weekRepo.findAll();
	}
	
	// getById  - "/api/weeks/{id}
	//			- return: Week 
	@GetMapping("/{id}")
	public Optional<Week> getWeekById(@PathVariable int id) {
		// check if week exists for id
		// if yes, return week
		// if no, return NotFound
		Optional<Week> w = weekRepo.findById(id);
		if (w.isPresent()) {
			return w;
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Week not found for id: "+id);
		}
	}
	
	@GetMapping("/by-ex-date/{exDate}")
	public Week getWeekByExDate(@PathVariable LocalDate exDate) {
		Week w = weekRepo.findByEndDateGreaterThanEqualAndStartDateLessThanEqual(exDate, exDate);
		if (w!=null) {
			return w;
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Week not found for date: "+exDate);
		}
	}

	// post 	- "/api/weeks/" (week will be in the RequestBody)
	//			- return: Week
	@PostMapping("")
	public Week addWeek(@RequestBody Week week) {
		return weekRepo.save(week);
	}
	
	// put 		- "/api/weeks/{id} (week passed in RB)
	//			- return: NoContent()
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putWeek(@PathVariable int id, @RequestBody Week week) {
		// check id passed vs id in instance
		// - BadRequest if not exist
		if (id != week.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Week id mismatch vs URL.");
		}
		// if week exists, update, else not found
		else if (weekRepo.existsById(week.getId())) {
			weekRepo.save(week);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Week not found for id: "+id);
		}
	}
	
	
	// delete 	- "/api/weeks/{id}
	//			- return: NoContent()
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)	
	public void deleteWeek(@PathVariable int id) {
		// check existence, then remove week
		// return NotFound if not exist
		if (weekRepo.existsById(id)) {
			weekRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Week not found for id: "+id);
		}
	}
	
	@PostMapping("/{year}")
	public void generateWeeksForYear(@PathVariable int year) {
		// weeks start on Mondays
		// given a date of year-01-01, get dayOfWeek
		// if dayOfWeek != Monday, go back a few days to priorMonday - that is first day of week 1
		
		String dayOneString = year + "-01-01";
		LocalDate dayOneDate = LocalDate.parse(dayOneString);
		//System.out.println("dayOneDate="+dayOneDate);
		int dayOfWeek = dayOneDate.getDayOfWeek().ordinal();
		//System.out.println("day of week ordinal: "+ dayOfWeek);
		int diff = 0 - dayOfWeek;
		LocalDate firstMondayOfYearDate = dayOneDate.minusDays(diff * -1);
		//System.out.println("First Monday of Year = "+firstMondayOfYear);
		LocalDate startDate = firstMondayOfYearDate;
		// create a Week object for each week of the year: 
		List<Week> weeks = new ArrayList<>();
		for (int i=1; i <= 52; i++) {
			int weekNum = i;
			// Create a Week object: year, weekNumber, startDate , endDate
			LocalDate endDate = startDate.plusDays(6);
			Week w = new Week(year, weekNum, startDate, endDate);
			weeks.add(w);
			startDate = startDate.plusDays(7);
		}
//		System.out.println("ID\tYear\tWeek#\tStartDate\tEndDate");
//		for (Week w: weeks) {
//			System.out.println(w.getId()+"\t"+w.getYear()+"\t"+w.getWeekNumber()+"\t"+w.getStartDate()+"\t"+w.getEndDate());
//			
//		}
		weekRepo.saveAll(weeks);
		
	}
	
	
}
