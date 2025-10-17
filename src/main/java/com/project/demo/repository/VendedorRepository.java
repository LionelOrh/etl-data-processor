package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.demo.entity.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

}
