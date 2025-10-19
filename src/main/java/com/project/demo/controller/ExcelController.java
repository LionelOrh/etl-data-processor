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

	@PostMapping("/cargar")
	public ResponseEntity<String> cargarExcel(@RequestParam("nombreArchivo") String nombreArchivo) {
	    try {
	        excelReaderService.readExcelFile(nombreArchivo);
	        return ResponseEntity.ok("✅ Excel cargado correctamente: " + nombreArchivo);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(500)
	                .body("❌ Error al cargar el Excel: " + e.getMessage());
	    }
	}

}	
