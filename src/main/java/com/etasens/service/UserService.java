package com.etasens.service;

import java.util.List;

import com.etasens.entities.User;

public interface UserService {
	public List<User> getUsers();

	public User findById(Integer id);

	public Boolean saveUser(User user);

	public Integer updateUser(User user);

	public Integer deleteUserById(Integer id);

}
