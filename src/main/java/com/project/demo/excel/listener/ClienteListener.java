package com.project.demo.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.project.demo.dto.ClienteDTO;
import com.project.demo.entity.Cliente;
import com.project.demo.repository.ClienteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClienteListener extends AnalysisEventListener<ClienteDTO>{
	
	private final ClienteRepository clienteRepository;
	
	public ClienteListener(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@Override
	public void invoke(ClienteDTO data, AnalysisContext context) {
		Cliente cliente = new Cliente();
		cliente.setCliente(data.getCliente());
		cliente.setSegmento(data.getSegmento());
		
		clienteRepository.save(cliente);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.info("✅ Lectura de clientes completada");
	}

}
