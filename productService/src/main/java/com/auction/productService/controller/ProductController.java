package com.auction.productService.controller;

import com.auction.productService.model.Product;
import com.auction.productService.productDto.ProductDto;
import com.auction.productService.repository.ProductRepository;
import com.auction.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        if(productService.validation(productDto)){
        return new ResponseEntity(productService.addproduct(productDto),HttpStatus.CREATED);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product){
        if(productService.validation(product)){
            return new ResponseEntity(productService.updateproduct(product),HttpStatus.CREATED);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> list =productService.getAllProducts();
        if(list.isEmpty()){
            return new ResponseEntity("No data available",HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(list, HttpStatus.FOUND);
        }
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDto>> getbyName(@PathVariable String name){
        List<ProductDto> list =productService.getByName(name);
        if(list.isEmpty()){
            return new ResponseEntity("No data available",HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity(list,HttpStatus.FOUND);
        }

    }

    @GetMapping("type/{type}")
    public ResponseEntity<List<ProductDto>> getbyType(@PathVariable String type){
        List<ProductDto> list =productService.getByType(type);
        if(list.isEmpty()){
            return new ResponseEntity("No data available",HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity(list,HttpStatus.FOUND);
        }

    }
    @GetMapping("/seller/{id}")
    public ResponseEntity<List<ProductDto>> getbySellerId(@PathVariable int id){
        List<ProductDto> list =productService.getBySellerId(id);
        if(list.isEmpty()){
            return new ResponseEntity("No data available",HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity(list,HttpStatus.FOUND);
        }

    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id){
       return new ResponseEntity(productService.deleteById(id),HttpStatus.ACCEPTED);

    }

}
