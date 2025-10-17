package com.project.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.entity.Local;
import com.project.demo.service.LocalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocalController {
	private final LocalService localService;
 
	@GetMapping
	public ResponseEntity<List<Local>> listar() {
		return ResponseEntity.ok(localService.listar());
	}

	@PostMapping
	public ResponseEntity<Local> registrar(@RequestBody Local local) {
		Local nuevo = localService.registrar(local);
		return ResponseEntity.ok(nuevo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Local> actualizar(@PathVariable Long id, @RequestBody Local local) {
		Local actualizado = localService.actualizar(id, local);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
		localService.eliminar(id);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "✅ Local eliminado exitosamente");
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis());
		return ResponseEntity.ok(response);
	}
}
