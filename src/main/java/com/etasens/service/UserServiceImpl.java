package com.etasens.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.etasens.entities.User;
import com.etasens.repository.UserRepository;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	public User findById(Integer id) {
		return userRepository.findById(id);
	}

	public Boolean saveUser(User user) {
		return userRepository.saveUser(user);
	}

	public Integer updateUser(User user) {
		return userRepository.updateUser(user);
	}

	public Integer deleteUserById(Integer id) {
		return userRepository.deleteUserById(id);
	}

}
