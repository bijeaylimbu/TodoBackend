package com.assignment.TodoApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.TodoApp.Model.*;


@Repository
public interface TodoRepo extends JpaRepository<Todo,Long > {

}
