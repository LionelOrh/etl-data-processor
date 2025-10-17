package com.project.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class LocalDTO {
	@ExcelProperty("Local")
    private String local;

    @ExcelProperty("Cliente")
    private String cliente;

    @ExcelProperty("Territorio")
    private String territorio;
}
