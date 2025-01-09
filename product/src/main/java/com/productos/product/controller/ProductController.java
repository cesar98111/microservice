package com.productos.product.controller;

import com.productos.product.entitys.Product;
import com.productos.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/hola")
    public String hola(){
        return "hola";
    }
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAll(){
        return buildResponseEntity(productRepository.findAll());
    }

    private ResponseEntity<List<Product>> buildResponseEntity(List<Product> productList){
        if(productList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }else{
            return new ResponseEntity<>(productList,HttpStatus.OK);
        }
    }
}
