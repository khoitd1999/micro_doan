package com.example.ordermodule.service.impl;

import com.example.ordermodule.dto.BillDTO;
import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.enity.Bill;
import com.example.ordermodule.enity.Cart;
import com.example.ordermodule.repository.BillRepository;
import com.example.ordermodule.repository.CartRepository;
import com.example.ordermodule.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Bill save(Bill bill) {
        bill.setFromDate(LocalDate.now());
        List<Cart> carts = bill.getCarts();
        bill.setCarts(null);
        bill = billRepository.save(bill);
        cartRepository.updateCart(bill.getId(), carts.stream().map(Cart::getId).collect(Collectors.toList()));
        return bill;
    }

    @Override
    public void cancelOrder(Bill bill) {
        billRepository.cancelOrder(bill.getStatus(), bill.getId());
    }

    @Override
    public Page<BillDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable) {
        return billRepository.loadPagination(searchTermDTO, pageable);
    }

    @Override
    public Page<CartDTO> loadDetailPagination(SearchTermDTO searchTermDTO, Pageable pageable) {
        return billRepository.loadDetailPagination(searchTermDTO, pageable);
    }

    @Override
    public Bill findOne(Long id) {
        return billRepository.findById(id).get();
    }
}
