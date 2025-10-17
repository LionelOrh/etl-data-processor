package com.project.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class ProductoDTO {
 @ExcelProperty("Producto")
 private String producto;
 
 @ExcelProperty("Categoria")
 private String categoria;

 @ExcelProperty("Marca")
 private String marca;

 @ExcelProperty("Comercializador")
 private String comercializador;

}
