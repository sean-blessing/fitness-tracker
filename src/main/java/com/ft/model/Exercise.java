package com.ft.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="UserId")
	private User user;
	@ManyToOne
	@JoinColumn(name="ActivityId")
	private Activity activity;
	@ManyToOne
	@JoinColumn(name="WeekId")
	private Week week;
	private LocalDate exerciseDate;
	private double miles;
	private int ascent;
	private int caloriesBurned;
	private int elapsedTimeSeconds;
	
	public Exercise() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public LocalDate getExerciseDate() {
		return exerciseDate;
	}

	public void setExerciseDate(LocalDate exerciseDate) {
		this.exerciseDate = exerciseDate;
	}

	public double getMiles() {
		return miles;
	}

	public void setMiles(double miles) {
		this.miles = miles;
	}

	public int getAscent() {
		return ascent;
	}

	public void setAscent(int ascent) {
		this.ascent = ascent;
	}

	public int getCaloriesBurned() {
		return caloriesBurned;
	}

	public void setCaloriesBurned(int caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}

	public int getElapsedTimeSeconds() {
		return elapsedTimeSeconds;
	}

	public void setElapsedTimeSeconds(int elapsedTimeSeconds) {
		this.elapsedTimeSeconds = elapsedTimeSeconds;
	}

}
