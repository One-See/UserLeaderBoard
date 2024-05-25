package com.crio.board.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.board.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
