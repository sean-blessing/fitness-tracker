package com.ft.model;

import java.sql.Date;

public class ExerciseDayReport {
	private long rowNbr;
	private String email;
	private int weekNumber;
	private Date exerciseDate;
	private String activities;
	
	public ExerciseDayReport(long rowNbr, String email, int weekNumber, Date exerciseDate, String activities) {
		super();
		this.rowNbr = rowNbr;
		this.email = email;
		this.weekNumber = weekNumber;
		this.exerciseDate = exerciseDate;
		this.activities = activities;
	}

	public long getRowNbr() {
		return rowNbr;
	}

	public void setRowNbr(long rowNbr) {
		this.rowNbr = rowNbr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Date getExerciseDate() {
		return exerciseDate;
	}

	public void setExerciseDate(Date exerciseDate) {
		this.exerciseDate = exerciseDate;
	}

	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}
	
	
}
