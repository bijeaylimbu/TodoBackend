package com.assignment.TodoApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.assignment.TodoApp.Model.*;
import com.assignment.TodoApp.repo.*;
import com.assignment.TodoApp.exception.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",maxAge = 3600)
public class TodoController {
	
	
	@Autowired
	private  TodoRepo todoRepo;
	
	@GetMapping("/todo")
	public List<Todo> getAllTodo(){
		return todoRepo.findAll();
		
	}
	
	@Configuration
	@EnableWebMvc
	public class WebConfig extends WebMvcConfigurerAdapter {

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**");
	    }
	}
	
	
	@CrossOrigin(origins = "*")
	@PostMapping(value="/todo", consumes={"application/json"})
	public Todo createTodo(@RequestBody Todo todo) {
		
		return todoRepo.save(todo);
	}
	
	@GetMapping("todo/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable(value="id") long id ) throws ResourcesNotFoundException{
	Todo todo=	todoRepo.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Not found"+ id));
		return ResponseEntity.ok().body(todo);
		
	}
	
	@PutMapping("/todo/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable(value="id") long id, @RequestBody Todo todoDetail)
			throws ResourcesNotFoundException {
		Todo todo=	todoRepo.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Not found"+ id));
		todo.setTitle(todoDetail.getTitle());
		
		todoRepo.save(todo);
		return ResponseEntity.ok().body(todo);
		
		
		
		
		
	}
	
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable(value="id") long id)  throws ResourcesNotFoundException {
		Todo todo=	todoRepo.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Todo Not found"+ id));
		todoRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}
	

}
