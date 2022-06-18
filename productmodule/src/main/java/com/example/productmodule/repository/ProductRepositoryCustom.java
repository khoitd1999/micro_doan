package com.example.productmodule.repository;

import com.example.productmodule.dto.BranchCategoryDTO;
import com.example.productmodule.dto.ProductDTO;
import com.example.productmodule.dto.SearchTermDTO;
import com.example.productmodule.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> loadAllData(SearchTermDTO searchTermDTO, Pageable pageable);

    List<Product> loadAllDataForReceipt();

    List<ProductDTO> loadProductDefaultForWelcome(List<BranchCategoryDTO> listID);

    ProductDTO findOneById(Long id);
}
