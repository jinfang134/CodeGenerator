package com.jinfang.jpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jinfang.jpa.domain.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);
}

