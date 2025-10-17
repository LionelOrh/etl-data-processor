package com.project.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class VendedorDTO {
	@ExcelProperty("Vendedor")
	private String vendedor;

	@ExcelProperty("Territorio")
	private String territorio;
}
