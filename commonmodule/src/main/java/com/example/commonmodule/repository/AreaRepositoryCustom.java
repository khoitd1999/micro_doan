package com.example.commonmodule.repository;

import com.example.commonmodule.dto.SearchTermDTO;
import com.example.commonmodule.entity.Area;

import java.util.List;

public interface AreaRepositoryCustom {
    List<Area> getAllArea(SearchTermDTO searchTermDTO);
}
