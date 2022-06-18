package com.example.productmodule.repository.impl;

import com.example.productmodule.dto.BranchCategoryDTO;
import com.example.productmodule.dto.ProductDTO;
import com.example.productmodule.dto.SearchTermDTO;
import com.example.productmodule.entity.Product;
import com.example.productmodule.repository.ProductRepositoryCustom;
import com.example.productmodule.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Page<Product> loadAllData(SearchTermDTO searchTermDTO, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        List<Product> lst = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append(" From Product where 1 = 1 ");
        if (Boolean.FALSE.equals(searchTermDTO.getIsAdmin())) {
            sql.append(" and status = 1 ");
        }
        if (searchTermDTO.getNameSearch() != null && !searchTermDTO.getNameSearch().trim().isEmpty()) {
            sql.append(" and name_pro like :name");
            params.put("name", "%" + searchTermDTO.getNameSearch() + "%");
        }
        if (searchTermDTO.getIdCat() != null) {
            sql.append(" and id_cat = :idCat");
            params.put("idCat", searchTermDTO.getIdCat());
        }
        if (searchTermDTO.getIdBra() != null) {
            sql.append(" and id_bra = :idBra");
            params.put("idBra", searchTermDTO.getIdBra());
        }
        if (searchTermDTO.getNameSearch() != null && !searchTermDTO.getNameSearch().isEmpty()) {
            sql.append(" and name_pro like :nameSearch ");
            params.put("nameSearch", "%" + searchTermDTO.getNameSearch() + "%");
        }
        if (searchTermDTO.getPriceFilter() != null) {
            switch (searchTermDTO.getPriceFilter()) {
                case 1:
                    sql.append(" and price < 15000000 ");
                    break;
                case 2:
                    sql.append(" and price >= 15000000 and price <= 20000000 ");
                    break;
                case 3:
                    sql.append(" and price >= 20000000 and price <= 25000000 ");
                    break;
                case 4:
                    sql.append(" and price >= 25000000 and price <= 30000000 ");
                    break;
                case 5:
                    sql.append(" and price >= 30000000 ");
                    break;
            }
        }
        Query countQuerry = entityManager.createNativeQuery("SELECT Count(1) " + sql.toString());
        Common.setParams(countQuerry, params);
        Number total = (Number) countQuerry.getSingleResult();
        if (total.longValue() > 0) {
            Query query = entityManager.createNativeQuery("SELECT id, name_pro namePro, price, id_cat idCat, id_bra idBra, description, " +
                    "screen,  os, ram, battery, date, image, status, rate" + sql.toString() + " ORDER BY date desc ", "ProductDTO");
            Common.setParamsWithPageable(query, params, pageable, total);
            List<ProductDTO> lst1 = query.getResultList();
            for (ProductDTO t: lst1) {
                Product product = new Product();
                product.setId(t.getId());
                product.setNamePro(t.getNamePro());
                product.setPrice(t.getPrice());
                product.setIdCat(t.getIdCat());
                product.setIdBra(t.getIdBra());
                product.setDescription(t.getDescription());
                product.setScreen(t.getScreen());
                product.setOs(t.getOs());
                product.setRam(t.getRam());
                product.setBattery(t.getBattery());
                product.setDate(t.getDate());
                product.setImage(t.getImage());
                product.setStatus(t.getStatus());
                lst.add(product);
            }
            for (Product product: lst) {
                if (product.getImage() != null && product.getImage().length > 0) {
                    product.setImage(Common.decompressBytes(product.getImage()));
                }
            }
            if (searchTermDTO.getSizeCurrent() != null) {
                if (searchTermDTO.getSizeCurrent() + lst.size() >= total.longValue()) {
                    lst.get(0).setIsLoadMore(false);
                } else {
                    lst.get(0).setIsLoadMore(true);
                }
            }
        }
        return new PageImpl<>(lst, pageable, total.longValue());
    }

    @Override
    public List<Product> loadAllDataForReceipt() {
        StringBuilder sql = new StringBuilder();
        List<Product> lst = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append(" From Product where 1 = 1 ");
        Query query = entityManager.createNativeQuery("SELECT id, name_pro namePro, price, id_cat idCat, id_bra idBra, description " + sql.toString() + " ORDER BY date desc ", "ProductDTOForReceipt");
        List<ProductDTO> lst1 = query.getResultList();
        for (ProductDTO t : lst1) {
            Product product = new Product();
            product.setId(t.getId());
            product.setNamePro(t.getNamePro());
            product.setPrice(t.getPrice());
            product.setIdBra(t.getIdBra());
            product.setDescription(t.getDescription());
            lst.add(product);
        }
        return lst;
    }

    @Override
    public List<ProductDTO> loadProductDefaultForWelcome(List<BranchCategoryDTO> listID) {
        StringBuilder sql = new StringBuilder();
        List<ProductDTO> lst = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append("(select id, name_pro namePro, price, id_cat idCat, id_bra idBra, image from product where id_bra = :idBra0 and id_cat = :idCat0 ").append(" and status = 1 order by date desc limit 6) ");
        params.put("idBra0", listID.get(0).getIdBra());
        params.put("idCat0", listID.get(0).getIdCat());
        for (int i = 1; i < listID.size(); i++) {
            sql.append(" union ");
            sql.append("(select id, name_pro namePro, price, id_cat idCat, id_bra idBra, image from product where id_bra = :idBra").append(i).append(" and id_cat = :idCat").append(i).append(" and status = 1 order by date desc limit 6) ");
            params.put("idBra" + i, listID.get(i).getIdBra());
            params.put("idCat" + i, listID.get(i).getIdCat());
        }
        Query query = entityManager.createNativeQuery(sql.toString() , "ProductDTOForWelcome");
        Common.setParams(query, params);
        lst = query.getResultList();
        for (ProductDTO product: lst) {
            if (product.getImage() != null && product.getImage().length > 0) {
                product.setImage(Common.decompressBytes(product.getImage()));
            }
        }
        return lst;
    }

    @Override
    public ProductDTO findOneById(Long id) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sql.append(" From Product where id = :id ");
        params.put("id", id);
        Query query = entityManager.createNativeQuery("SELECT id, namePro, price, idCat, idBra, description, " +
                " screen,  os, ram, battery, date, image, status, rate " + sql.toString() , "ProductDTO");
        Common.setParams(query, params);
        ProductDTO productDTO = (ProductDTO) query.getSingleResult();
        if (productDTO.getImage() != null && productDTO.getImage().length > 0) {
            productDTO.setImage(Common.decompressBytes(productDTO.getImage()));
        }
        return productDTO;
    }
}
