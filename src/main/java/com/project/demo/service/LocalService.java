package com.project.demo.service;

import java.util.List;
import java.util.Optional;

import com.project.demo.entity.Local;

public interface LocalService {
	public List<Local> listar();
	public Optional<Local> buscarPorId(Long id);
	public Local registrar(Local local);
	public Local actualizar(Long id, Local localActualizado);
	public void eliminar(Long id);
}
