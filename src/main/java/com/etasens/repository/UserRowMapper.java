package com.etasens.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.etasens.entities.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("Id"));
		user.setFirstName(rs.getString("FirstName"));
		user.setLastName(rs.getString("LastName"));

		user.setPassword(rs.getString("Password"));
		user.setEmail(rs.getString("Email"));
		user.setCity(rs.getString("City"));
		user.setCountry(rs.getString("Country"));
		user.setDateOfBirth("dateOfBirth");

		return user;

	}

}
