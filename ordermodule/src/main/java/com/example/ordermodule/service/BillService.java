package com.example.ordermodule.service;

import com.example.ordermodule.dto.BillDTO;
import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.enity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {
    Bill save(Bill bill);

    void cancelOrder(Bill bill);

    Page<BillDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable);

    Page<CartDTO> loadDetailPagination(SearchTermDTO searchTermDTO, Pageable pageable);

    Bill findOne(Long id);
}
