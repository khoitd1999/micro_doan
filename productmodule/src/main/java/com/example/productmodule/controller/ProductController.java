package com.example.productmodule.controller;

import com.example.productmodule.dto.BranchCategoryDTO;
import com.example.productmodule.dto.ProductDTO;
import com.example.productmodule.dto.Result;
import com.example.productmodule.dto.SearchTermDTO;
import com.example.productmodule.entity.Brand;
import com.example.productmodule.entity.Comment;
import com.example.productmodule.entity.Product;
import com.example.productmodule.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get-brand-category")
    public ResponseEntity<Result<List<Object>>> getBrandAndCategory() throws JsonProcessingException {
        try {
            Result<List<Object>> rs = new Result<>();
            rs.setBody(productService.getBrandAndCategory());
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-brand")
    public ResponseEntity<List<Brand>> getBrand(@RequestParam String idCat) throws JsonProcessingException {
        try {
            List<Brand> rs = productService.getBrand(idCat);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Result<Product>> save(@RequestParam(required = false) MultipartFile imageFile, @RequestParam String productString) throws IOException {
        Result<Product> result = new Result<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productString, Product.class);
        product = productService.save(product, imageFile);
        if (product == null) {
            result.setMessage("Tên sản phẩm đã tồn tại");
        }
        result.setBody(product);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Result<List<Object>>> loadDataAll(Pageable pageable, @RequestParam String searchTerm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            Page<Product> pageTust = productService.loadAllData(searchTermDTO, pageable);
            Result<List<Object>> result = new Result<>();
            List<Object> list = new ArrayList<>();
            list.add(pageTust.getContent());
            list.add(pageTust.getTotalElements());
            if (searchTermDTO.getSizeCurrent() != null) {
                if (pageTust.getTotalElements() > 0) {
                    result.setIsLoadMore(pageTust.getContent().get(0).getIsLoadMore());
                } else {
                    result.setIsLoadMore(false);
                }
            }
            result.setBody(list);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/load-all")
    public ResponseEntity<Result<List<Product>>> loadDataAll() {
        try {
            List<Product> pageTust = productService.loadAll();
            Result<List<Product>> result = new Result<>();
            result.setBody(pageTust);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-product-default-for-welcome")
    public ResponseEntity<List<ProductDTO>> loadProductDefaultForWelcome(@RequestBody List<BranchCategoryDTO> listID) {
        try {
            List<ProductDTO> pageTust = productService.loadProductDefaultForWelcome(listID);
            return new ResponseEntity<>(pageTust, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<ProductDTO> findByID(@RequestParam Long id) {
        try {
            ProductDTO pageTust = productService.findById(id);
            return new ResponseEntity<>(pageTust, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all-comment")
    public ResponseEntity<List<Comment>> getAllComment(@RequestParam Long id) {
        try {
            List<Comment> pageTust = productService.getAllComment(id);
            return new ResponseEntity<>(pageTust, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/submit-comment")
    public ResponseEntity<Double> submitCommit(@RequestBody Comment comment) {
        try {
            Double rs = productService.submitComment(comment);
            return new ResponseEntity<>(rs, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
