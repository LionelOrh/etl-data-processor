package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.entity.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
	boolean existsByLocal(String local);

}
