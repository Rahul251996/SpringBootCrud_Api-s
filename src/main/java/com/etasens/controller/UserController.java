package com.etasens.controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etasens.entities.User;
import com.etasens.exception.UserException;
import com.etasens.service.UserService;
import com.etasens.util.Constants;

@RestController
@RequestMapping("/restApi/assignment/test")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping
	public String home() {
		return "Welcome to Our Api";
	}

	@GetMapping(value = "/users", produces = MediaType.ALL_VALUE)
	public List<User> getAllUser() {
		return userService.getUsers();
	}

	@GetMapping(value = "/users/{id}", produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> getUser(@PathVariable Integer id) {
		User user = userService.findById(id);

		if (user == null) {
			return new ResponseEntity<String>("No user found with this " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping(value = "/users", produces = MediaType.ALL_VALUE)
	public ResponseEntity<String> createUser(@RequestBody User user) throws SQLIntegrityConstraintViolationException {
		if (userService.findById(user.getId()) != null) {
			return new ResponseEntity<String>("Duplicate Entry" + user.getId(), HttpStatus.IM_USED);
		}
		userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping(value = "/users", produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		// Integer id =user.getId();
		if (userService.findById(user.getId()) == null) {
			return new ResponseEntity<String>("unable to updatenas userId" + user.getId() + "not found",
					HttpStatus.NOT_FOUND);
		}

		userService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "/users/{id}", produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		User user = userService.findById(id);

		if (user == null) {
			return new ResponseEntity<String>("unable to delete user id" + id + "not found", HttpStatus.NOT_FOUND);

		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Object> handleException(UserException e) throws IOException {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(Constants.KEY_STATUS, Constants.STATUS_FAILURE);
		resultMap.put(Constants.KEY_ERROR, e.getMessage());
		return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
	}
}
