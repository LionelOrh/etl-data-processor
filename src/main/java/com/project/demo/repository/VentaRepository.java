package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.demo.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

}
