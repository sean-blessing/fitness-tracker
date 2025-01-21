package com.ft.model;

public class ExerciseWeekReport {
	private String email;
	private int weekNumber;
	private long count;
	
	public ExerciseWeekReport(String email, int weekNumber, long count) {
		super();
		this.email = email;
		this.weekNumber = weekNumber;
		this.count = count;
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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
