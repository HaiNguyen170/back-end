package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.*;


@Repository
public interface FileRepository extends JpaRepository<File, String>{
	
	/*-Now we can use FileRepository with JpaRepository's method such as : save(File)
	 * ,findById(id),findAll()*/
	
}
