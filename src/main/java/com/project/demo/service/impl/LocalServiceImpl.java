package com.project.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.demo.entity.Cliente;
import com.project.demo.entity.Local;
import com.project.demo.entity.Territorio;
import com.project.demo.repository.ClienteRepository;
import com.project.demo.repository.LocalRepository;
import com.project.demo.repository.TerritorioRepository;
import com.project.demo.service.LocalService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalServiceImpl implements LocalService {
	private final LocalRepository localRepository;
	private final ClienteRepository clienteRepository;
	private final TerritorioRepository territorioRepository;

	@Override
	public List<Local> listar() {
		return localRepository.findAll();
	}

	@Override
	public Optional<Local> buscarPorId(Long id) {
		return localRepository.findById(id);
	}

	@Override
	public Local registrar(Local local) {
		if (localRepository.existsByLocal(local.getLocal())) {
			throw new RuntimeException("El local ya existe: " + local.getLocal());
		}
		Long idCliente = local.getCliente().getIdCliente();
		Long idTerritorio = local.getTerritorio().getIdTerritorio();

		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente));
		Territorio territorio = territorioRepository.findById(idTerritorio)
				.orElseThrow(() -> new RuntimeException("Territorio no encontrado con ID: " + idTerritorio));

		local.setCliente(cliente);
		local.setTerritorio(territorio);

		return localRepository.save(local);
	}

	@Transactional
	public Local actualizar(Long id, Local localActualizado) {
		return localRepository.findById(id).map(existing -> {
			if (localActualizado.getLocal() != null) {
				existing.setLocal(localActualizado.getLocal());
			}

			// Actualizar cliente si se envía
			if (localActualizado.getCliente() != null && localActualizado.getCliente().getIdCliente() != null) {
				Cliente cliente = clienteRepository.findById(localActualizado.getCliente().getIdCliente())
						.orElseThrow(() -> new RuntimeException(
								"Cliente no encontrado con ID: " + localActualizado.getCliente().getIdCliente()));
				existing.setCliente(cliente);
			}

			// Actualizar territorio si se envía
			if (localActualizado.getTerritorio() != null
					&& localActualizado.getTerritorio().getIdTerritorio() != null) {
				Territorio territorio = territorioRepository
						.findById(localActualizado.getTerritorio().getIdTerritorio())
						.orElseThrow(() -> new RuntimeException("Territorio no encontrado con ID: "
								+ localActualizado.getTerritorio().getIdTerritorio()));
				existing.setTerritorio(territorio);
			}

			return localRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Local no encontrado con ID: " + id));
	}

	@Override
	public void eliminar(Long id) {
		 if (!localRepository.existsById(id)) {
	            throw new RuntimeException("No existe el local con ID: " + id);
	        }
	        localRepository.deleteById(id);
	}

}
