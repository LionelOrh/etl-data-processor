package com.project.demo.excel.listener;

import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.project.demo.dto.LocalDTO;
import com.project.demo.entity.Cliente;
import com.project.demo.entity.Local;
import com.project.demo.entity.Territorio;
import com.project.demo.repository.LocalRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalListener extends AnalysisEventListener<LocalDTO> {

    private final LocalRepository localRepository;
    private final Map<String, Cliente> clientesMap;
    private final Map<String, Local> localMap;
    private final Map<String, Territorio> territoriosMap;

    public LocalListener(
            LocalRepository localRepository,
            Map<String, Cliente> clientesMap,
            Map<String, Local> localMap,
            Map<String, Territorio> territoriosMap) {
        this.localRepository = localRepository;
        this.clientesMap = clientesMap;
        this.localMap = localMap;
        this.territoriosMap = territoriosMap;
    }

    @Override
    public void invoke(LocalDTO data, AnalysisContext context) {

        // Normalizamos los valores
        String clienteKey = data.getCliente().trim().toLowerCase();
        String territorioKey = data.getTerritorio().trim().toLowerCase();

        Cliente cliente = clientesMap.get(clienteKey);
        Territorio territorio = territoriosMap.get(territorioKey);

        log.info("📄 Leyendo fila: Local='{}', Cliente='{}', Territorio='{}'",
                data.getLocal(), data.getCliente(), data.getTerritorio());

        log.debug("🔑 Claves normalizadas → clienteKey='{}' | territorioKey='{}'", clienteKey, territorioKey);

        if (cliente == null || territorio == null) {
            System.err.println("⚠️ Faltan referencias para la fila del Excel: " + data);

            // Mostramos cuál exactamente está faltando
            System.err.println("   → Cliente encontrado? " + (cliente != null));
            System.err.println("   → Territorio encontrado? " + (territorio != null));

            // Si quieres ver los que sí existen, puedes loguear sus IDs
            if (cliente != null) {
                System.err.println("   ✔ ID Cliente: " + cliente.getIdCliente());
            }
            if (territorio != null) {
                System.err.println("   ✔ ID Territorio: " + territorio.getIdTerritorio());
            }

            return;
        }

        String key = (data.getLocal().trim() + "-" + cliente.getIdCliente()).toLowerCase();

        // Log para saber qué clave se usa al crear locales
        log.debug("🧩 Clave de búsqueda del local: {}", key);

        Local local = localMap.get(key);
        if (local == null) {
            local = new Local();
            local.setLocal(data.getLocal().trim());
            local.setCliente(cliente);
            local.setTerritorio(territorio);

            localRepository.save(local);
            localMap.put(key, local);

            log.info("✅ Nuevo Local guardado: '{}' (Cliente: '{}', Territorio: '{}')",
                    local.getLocal(), cliente.getCliente(), territorio.getTerritorio());
        } else {
            log.info("⚙️ Local ya existente: '{}'", key);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("✅ Hoja de Local procesada completamente.");
    }
}
