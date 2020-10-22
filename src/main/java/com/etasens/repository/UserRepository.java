package com.etasens.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.etasens.entities.User;
import com.etasens.exception.UserException;

@Repository
public class UserRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<User> getUsers() {
		String sql = "Select id,FirstName,LastName,Password,Email,City,Country,DateOfBirth from User ORDER BY FirstName";
		List<User> users = new ArrayList<User>();
		users.addAll(jdbcTemplate.query(sql, new UserRowMapper()));
		return users;
	}

	public User findById(Integer id) {
		String sql2 = "SELECT * FROM User WHERE ID=?";
		try {
			return (User) this.jdbcTemplate.queryForObject(sql2, new Object[] { id }, new UserRowMapper());
		} catch (UserException e) {
			return null;
		}
	}

	public Boolean saveUser(User user) {
		String sql3 = "Insert into User values(?,?,?,?,?,?,?,?)";

		return jdbcTemplate.execute(sql3, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, user.getId());
				ps.setString(2, user.getFirstName());
				ps.setString(3, user.getLastName());
				ps.setString(4, user.getPassword());
				ps.setString(5, user.getEmail());
				ps.setString(6, user.getCity());
				ps.setString(7, user.getCountry());
				ps.setString(8, user.getDateOfBirth());
				return ps.execute();
			}
		});
	}

	public Integer updateUser(User user) {
		String sql5 = "update User set FirstName=?,LastName=?,Password=?,Email=?,City=?,Country=?,DateOfBirth=? where id=?";
		Object[] params = { user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(),
				user.getCity(), user.getCountry(), user.getDateOfBirth(), user.getId() };

		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.INTEGER };
		return jdbcTemplate.update(sql5, params, types);
	}

	public Integer deleteUserById(Integer id) {

		return jdbcTemplate.update("delete from user where id=?", id);
	}
}
