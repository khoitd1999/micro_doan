package com.example.commonmodule.repository;

import com.example.commonmodule.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long>, AreaRepositoryCustom {

//    @Query(value = "select * from Area from parentcode = ?1 and name like ?2", nativeQuery = true)
//    Page<Area> getAllArea(String code, Pageable pageable);
}
