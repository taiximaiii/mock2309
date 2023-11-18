package com.vti.repository;

import com.vti.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISizeRepository extends JpaRepository<Size,Integer> {
    @Query("SELECT s FROM Size s WHERE s.id IN (:sizeIds)")
    List<Size> findByIdIn(@Param("sizeIds") List<Integer> sizeIds);
}
