package com.project.demo.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.project.demo.dto.ProductoDTO;
import com.project.demo.entity.Producto;
import com.project.demo.repository.ProductoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductoListener extends AnalysisEventListener<ProductoDTO>{
	
	private final ProductoRepository productoRepository;
	
	public ProductoListener(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}
	
	@Override
	public void invoke(ProductoDTO data, AnalysisContext context) {

		Producto producto = new Producto();
		producto.setProducto(data.getProducto());
		producto.setCategoria(data.getCategoria());
		producto.setComercializador(data.getComercializador());
		producto.setMarca(data.getMarca());
		
		productoRepository.save(producto);
		
		
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.info("✅ Lectura de productos completada");
		
	}

}
