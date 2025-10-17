package com.project.demo.excel.listener;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.project.demo.dto.VentaDTO;
import com.project.demo.entity.Cliente;
import com.project.demo.entity.Local;
import com.project.demo.entity.Producto;
import com.project.demo.entity.Venta;
import com.project.demo.repository.ProductoRepository;
import com.project.demo.repository.VentaRepository;
import com.project.demo.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VentaListener extends AnalysisEventListener<VentaDTO> {
	private final AtomicInteger contador = new AtomicInteger(0);

	private final VentaRepository ventaRepository;
	private final ProductoRepository productoRepository;
	private final Map<String, Local> localesMap; // solo locales
	private final Map<String, Cliente> clientesMap; 

	public VentaListener(VentaRepository ventaRepository, ProductoRepository productoRepository,
			Map<String, Local> localesMap,
			Map<String, Cliente> clientesMap
			) {
		this.ventaRepository = ventaRepository;
		this.productoRepository = productoRepository;
		this.localesMap = localesMap;
		this.clientesMap = clientesMap;
	}

	@Override
	public void invoke(VentaDTO data, AnalysisContext context) {
		
		int numRegistro = contador.incrementAndGet();
		// Buscar el producto directamente en la base de datos por nombre y marca
		Producto producto = productoRepository.findByProductoAndMarca(data.getProducto(), data.getMarca());
		String clienteKey = data.getCliente().trim().toLowerCase();
		Cliente cliente = clientesMap.get(clienteKey);
		
		if (producto == null) {
			System.err.println("❌ Producto no encontrado para la venta: " + data);
			return;
		}
		
		// Local ya incluye la referencia al cliente
		String keyLocal = (data.getLocal().trim() + "-" + data.getCliente()).toLowerCase(); // si quieres, puedes usar la
																						// combinación local-clienteId
		log.info("🔍 Buscando local con clave: " + data.getLocal() + "-" + data.getCliente());
		Local local = localesMap.get(keyLocal);
		if (local == null) {
			System.err.println("⚠️ Local no encontrado para la venta: " + data);
			return;
		}

		// Crear la venta usando el local y producto
		Venta venta = new Venta();
		venta.setProducto(producto); 
		venta.setLocal(local);
		venta.setCliente(cliente);
		venta.setFecha(DateUtil.parseToLocalDate(data.getFecha()));
		venta.setPrecioUsd(BigDecimal.valueOf(data.getPrecioUsd()));
		venta.setCantidad(data.getCantidad());
		venta.setMontoUsd(BigDecimal.valueOf(data.getMontoUsd()));

		ventaRepository.save(venta);

		log.info("✅ [{}] Venta registrada correctamente:", numRegistro);
        log.info("   🧾 Producto: {}", producto.getProducto());
        log.info("   📍 Local: {}", local.getLocal());
        log.info("   🧍 Cliente: {}", cliente.getCliente());
        log.info("   💰 Monto: ${}", data.getMontoUsd());
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		System.out.println("✅ Hoja de Ventas procesada correctamente.");
	}
}
