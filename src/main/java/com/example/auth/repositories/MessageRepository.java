package com.example.auth.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.auth.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{

}
