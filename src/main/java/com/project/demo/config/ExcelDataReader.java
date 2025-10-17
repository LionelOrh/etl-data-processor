package com.project.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.demo.excel.ExcelReaderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExcelDataReader implements CommandLineRunner{
	
	private final ExcelReaderService excelReaderService;

    @Override
    public void run(String... args) throws Exception {
        String filePath = "src/main/resources/excel/Data (1) (3).xlsx";
        excelReaderService.readExcelFile(filePath);
        System.out.println("✅ Carga de Excel completada.");
    }
}
