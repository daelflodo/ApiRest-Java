package com.dael.ApiRest.controllers;

import com.dael.ApiRest.controllers.dto.MakerDTO;
import com.dael.ApiRest.controllers.dto.ProductDto;
import com.dael.ApiRest.entities.Maker;
import com.dael.ApiRest.entities.Product;
import com.dael.ApiRest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<ProductDto> productDtoList = productService.findAll()
                .stream().map(product -> ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                ).toList();
        return ResponseEntity.ok(productDtoList);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDto productDto = ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDto productDto) throws URISyntaxException {
        if (productDto.getName().isBlank() || productDto.getPrice() == null || productDto.getMaker() ==null) {
            return ResponseEntity.badRequest().build();
        }
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .maker(productDto.getMaker())
                .build();
        productService.save(product);
        return ResponseEntity.created(new URI("/api/product/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDto productDto) {

        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();// con esto recuperamos el entity
            product.setName(productDto.getName()); //aqui setteamos el name con lo que viene en el dto
            product.setPrice(productDto.getPrice());
            product.setMaker(productDto.getMaker());
            productService.save(product);
            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            Optional<Product> productOptional = productService.findById(id);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                productService.deleteById(id);
                return ResponseEntity.ok("Registro Eliminado");
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();

    }
}
