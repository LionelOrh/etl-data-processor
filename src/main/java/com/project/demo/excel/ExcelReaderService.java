package com.project.demo.excel;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alibaba.excel.EasyExcel;
import com.project.demo.dto.ClienteDTO;
import com.project.demo.dto.LocalDTO;
import com.project.demo.dto.ProductoDTO;
import com.project.demo.dto.VendedorDTO;
import com.project.demo.dto.VentaDTO;
import com.project.demo.entity.Cliente;
import com.project.demo.entity.Local;
import com.project.demo.entity.Territorio;
import com.project.demo.excel.listener.ClienteListener;
import com.project.demo.excel.listener.LocalListener;
import com.project.demo.excel.listener.ProductoListener;
import com.project.demo.excel.listener.VendedorListener;
import com.project.demo.excel.listener.VentaListener;
import com.project.demo.repository.ClienteRepository;
import com.project.demo.repository.LocalRepository;
import com.project.demo.repository.ProductoRepository;
import com.project.demo.repository.TerritorioRepository;
import com.project.demo.repository.VendedorRepository;
import com.project.demo.repository.VendedorTerritorioRepository;
import com.project.demo.repository.VentaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelReaderService {

	private final VendedorRepository vendedorRepository;
	private final ClienteRepository clienteRepository;
	private final LocalRepository localRepository;
	private final ProductoRepository productoRepository;
	private final VentaRepository ventaRepository;
	private final TerritorioRepository territorioRepository;
	private final VendedorTerritorioRepository vendedorTerritorioRepository;

	@Transactional
	public void readExcelFile(String filePath) {

		// Hoja 1: Vendedores
		EasyExcel
				.read(filePath, VendedorDTO.class,
						new VendedorListener(vendedorRepository, territorioRepository, vendedorTerritorioRepository))
				.sheet("Vendedores").doRead();

		// Hoja 2: Clientes
		EasyExcel.read(filePath, ClienteDTO.class, new ClienteListener(clienteRepository)).sheet("Clientes").doRead();

		Map<String, Cliente> clientesMap = clienteRepository.findAll().stream()
				.collect(Collectors.toMap(c -> c.getCliente().trim().toLowerCase(), c -> c));

		Map<String, Territorio> territoriosMap = territorioRepository.findAll().stream()
				.collect(Collectors.toMap(t -> t.getTerritorio().trim().toLowerCase(), t -> t));

		Map<String, Local> localMap = new HashMap<>();

		// Hoja 3: Locales
		EasyExcel
				.read(filePath, LocalDTO.class,
						new LocalListener(localRepository, clientesMap, localMap, territoriosMap))
				.sheet("Locales").doRead();

		// Hoja 4: Productos
		EasyExcel.read(filePath, ProductoDTO.class, new ProductoListener(productoRepository)).sheet("Producto")
				.doRead();

		// Hoja 5: Ventas

		Map<String, Local> localesMap = localRepository.findAll().stream().collect(
				Collectors.toMap(l -> (l.getLocal() + "-" + l.getCliente().getCliente()).trim().toLowerCase(), l -> l));

		EasyExcel
				.read(filePath, VentaDTO.class,
						new VentaListener(ventaRepository, productoRepository, localesMap, clientesMap))
				.sheet("Ventas").doRead();
	}
}
