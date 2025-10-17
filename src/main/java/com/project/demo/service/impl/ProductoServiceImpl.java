package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.demo.entity.Producto;
import com.project.demo.repository.ProductoRepository;
import com.project.demo.service.ProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

	public final ProductoRepository productoRepository;

	@Override
	public List<Producto> listarTodos() {
		return productoRepository.findAll();
	}

	@Override
	public Optional<Producto> buscarPorId(Long id) {
		return productoRepository.findById(id); 
				
	}

	@Override
	public Producto registrar(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Producto actualizar(Long id, Producto producto) {
		return productoRepository.findById(id)
                .map(existing -> {
                    existing.setProducto(producto.getProducto());
                    existing.setMarca(producto.getMarca());
                    existing.setCategoria(producto.getCategoria());
                    existing.setComercializador(producto.getComercializador());
                    return productoRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));	
		}

	@Override
	public void eliminar(Long id) {
		if (!productoRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el producto con ID: " + id);
        }
        productoRepository.deleteById(id);
	}

}
