package com.example.ordermodule.repository;

import com.example.ordermodule.dto.BillDTO;
import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillRepositoryCustom {
    Page<BillDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable);

    Page<CartDTO> loadDetailPagination(SearchTermDTO searchTermDTO, Pageable pageable);
}
