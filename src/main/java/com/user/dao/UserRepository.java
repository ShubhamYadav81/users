package com.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.user.model.User;

public interface UserRepository extends CrudRepository<User, String>{

}
