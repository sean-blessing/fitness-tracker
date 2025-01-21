package com.ft.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ft.model.Activity;

public interface ActivityRepo extends JpaRepository<Activity, Integer>{

}
