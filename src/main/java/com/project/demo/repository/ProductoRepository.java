package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.demo.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	@Query("SELECT p FROM Producto p WHERE p.producto = :producto AND p.marca = :marca")
	Producto findByProductoAndMarca(@Param("producto") String producto, @Param("marca") String marca);

	boolean existsById(Long idProducto);

}
