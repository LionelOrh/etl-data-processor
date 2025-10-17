package com.project.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.excel.ExcelReaderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {
	private final ExcelReaderService excelReaderService;

    /**
     * Carga el Excel desde una ruta específica
     * @param ruta Ruta del archivo Excel
     * @return Respuesta con resumen
     */
    @PostMapping("/cargar")
    public ResponseEntity<String> cargarExcel(@RequestParam("ruta") String ruta) {
        try {
            excelReaderService.readExcelFile(ruta);
            return ResponseEntity.ok("✅ Excel cargado correctamente desde: " + ruta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error al cargar el Excel: " + e.getMessage());
        }
    }
}	
