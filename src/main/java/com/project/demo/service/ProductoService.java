package com.project.demo.service;

import java.util.List;
import java.util.Optional;

import com.project.demo.entity.Producto;

public interface ProductoService {
	public List<Producto> listarTodos();

	public Optional<Producto> buscarPorId(Long id);

	public Producto registrar(Producto producto);

	public Producto actualizar(Long id, Producto producto);

	public void eliminar(Long id);
}
