package com.lei.activiti.jpa;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository{

    Person findByUsername(String username);

}
