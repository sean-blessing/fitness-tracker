package com.ft.db;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ft.model.Week;

public interface WeekRepo extends JpaRepository<Week, Integer>{
	Week findByEndDateGreaterThanEqualAndStartDateLessThanEqual(LocalDate exerciseDate1, LocalDate exerciseDate2);
}
