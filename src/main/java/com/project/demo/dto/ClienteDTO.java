package com.project.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ClienteDTO {
	@ExcelProperty("Cliente")
    private String cliente;

    @ExcelProperty("Segmento")
    private String segmento;
}
