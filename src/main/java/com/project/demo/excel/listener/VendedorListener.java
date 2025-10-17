package com.project.demo.excel.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.project.demo.dto.VendedorDTO;
import com.project.demo.entity.Territorio;
import com.project.demo.entity.Vendedor;
import com.project.demo.entity.VendedorTerritorio;
import com.project.demo.repository.TerritorioRepository;
import com.project.demo.repository.VendedorRepository;
import com.project.demo.repository.VendedorTerritorioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VendedorListener extends AnalysisEventListener<VendedorDTO> {

	private final VendedorRepository vendedorRepository;
	private final TerritorioRepository territorioRepository;
	private final VendedorTerritorioRepository vendedorTerritorioRepository;

	private final Map<String, Vendedor> vendedoresMap = new HashMap<>();
	private final Map<String, Territorio> territoriosMap = new HashMap<>();

	private final List<Vendedor> nuevosVendedores = new ArrayList<>();
	private final List<Territorio> nuevosTerritorios = new ArrayList<>();
	private final List<VendedorTerritorio> nuevasRelaciones = new ArrayList<>();

	public VendedorListener(VendedorRepository vendedorRepository, TerritorioRepository territorioRepository,
			VendedorTerritorioRepository vendedorTerritorioRepository) {
		this.vendedorRepository = vendedorRepository;
		this.territorioRepository = territorioRepository;
		this.vendedorTerritorioRepository = vendedorTerritorioRepository;

		// Cargar data existente al iniciar
		vendedorRepository.findAll().forEach(v -> vendedoresMap.put(v.getVendedor().trim().toLowerCase(), v));
		territorioRepository.findAll().forEach(t -> territoriosMap.put(t.getTerritorio().trim().toLowerCase(), t));
	}

	@Override
	public void invoke(VendedorDTO data, AnalysisContext context) {

		if (data == null || data.getVendedor() == null)
			return;

		String nombreVendedor = data.getVendedor().trim().toLowerCase();
		String territoriosTexto = data.getTerritorio();

		// Buscar o crear vendedor
		Vendedor vendedor = vendedoresMap.get(nombreVendedor);
		if (vendedor == null) {
			vendedor = new Vendedor();
			vendedor.setVendedor(data.getVendedor().trim());
			nuevosVendedores.add(vendedor);
			vendedoresMap.put(nombreVendedor, vendedor);
		}
		
		
		// Procesar territorios (separados por "y")
        String[] territorios = territoriosTexto.split("(?i)\\s*y\\s*");
        for (String nombre : territorios) {
            String key = nombre.trim().toLowerCase();
            Territorio territorio = territoriosMap.get(key);

            if (territorio == null) {
                territorio = new Territorio();
                territorio.setTerritorio(nombre.trim());
                nuevosTerritorios.add(territorio);
                territoriosMap.put(key, territorio);
            }

            // Crear relación vendedor-territorio
            VendedorTerritorio relacion = new VendedorTerritorio();
            relacion.setVendedor(vendedor);
            relacion.setTerritorio(territorio);
            nuevasRelaciones.add(relacion);
        }

	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.info("✅ Lectura completada. Guardando datos en batch...");

        vendedorRepository.saveAll(nuevosVendedores);
        territorioRepository.saveAll(nuevosTerritorios);
        vendedorTerritorioRepository.saveAll(nuevasRelaciones);

        log.info("✅ Guardado completado correctamente.");
	}

}
