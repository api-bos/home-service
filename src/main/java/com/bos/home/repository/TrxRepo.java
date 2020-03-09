package com.bos.home.repository;

import com.bos.home.entity.TrxDim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrxRepo extends JpaRepository <TrxDim,Integer> {
    List<TrxDim> findAll();
}
