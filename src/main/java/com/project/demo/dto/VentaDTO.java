package com.project.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class VentaDTO {
	@ExcelProperty("Fecha")
    private String fecha;

    @ExcelProperty("Producto")
    private String producto;

    @ExcelProperty("Marca")
    private String marca;

    @ExcelProperty("Cliente")
    private String cliente;
    
    @ExcelProperty("Comercializador")
    private String comercializador;

    @ExcelProperty("Local")
    private String local;
    
    @ExcelProperty("Precio (USD)")
    private Double precioUsd;

    @ExcelProperty("Cantidad")
    private Integer cantidad;

    @ExcelProperty("Monto (USD)")
    private Double montoUsd;
}
