package com.example.productmodule.service;

import com.example.productmodule.dto.BranchCategoryDTO;
import com.example.productmodule.dto.ProductDTO;
import com.example.productmodule.dto.SearchTermDTO;
import com.example.productmodule.entity.Brand;
import com.example.productmodule.entity.Comment;
import com.example.productmodule.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Object> getBrandAndCategory();

    List<Brand> getBrand(Long idCat);

    Product save(Product product, MultipartFile multipartFile) throws IOException;

    Page<Product> loadAllData(SearchTermDTO searchTermDTO, Pageable pageable);

    List<Product> loadAll();

    List<ProductDTO> loadProductDefaultForWelcome(List<BranchCategoryDTO> listID);

    Double submitComment(Comment comment);

    ProductDTO findById(Long id);

    List<Comment> getAllComment(Long id);
}
