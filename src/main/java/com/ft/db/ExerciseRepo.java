package com.ft.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ft.model.Exercise;
import com.ft.model.ExerciseDayReport;
import com.ft.model.ExerciseWeekReport;

public interface ExerciseRepo extends JpaRepository<Exercise, Integer>{
	@Query(value = "select CAST(row_number()  over() AS SIGNED INTEGER) as RowNbr, u.Email, WeekNumber, ExerciseDate, group_concat(a.Name SEPARATOR ', ') as Activities"
			+ " from exercise e"
			+ " join user u on u.id = e.UserId"
			+ " join activity a on a.id = e.activityid"
			+ " join week w on w.id = e.weekid"
			+ " group by u.email, WeekNumber, ExerciseDate;", nativeQuery = true)
	List<ExerciseDayReport> findExerciseDayReport();
	
	@Query(value = " select Email, WeekNumber, count(distinct(e.id)) as Count"
			+ "   from exercise e"
			+ "   right join week w on w.id = e.WeekId"
			+ "   join User u on u.id = e.UserId"
			+ "  group by Email, WeekNumber;", nativeQuery = true)
	List<ExerciseWeekReport> findExerciseWeekReport();
}
