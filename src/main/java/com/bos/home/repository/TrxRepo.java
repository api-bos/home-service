package com.bos.home.repository;

import com.bos.home.dto.TrxSum;
import com.bos.home.entity.TrxDim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrxRepo extends JpaRepository <TrxDim,Integer> {
    List<TrxDim> findAll();

    @Query(nativeQuery = true,
           value = "select count(t.id_seller), sum(t.total_payment) " +
                   "from transaction t " +
                   "where extract (day from current_date() - payment_time) <= 30 AND t.id_seller=:idSeller")
    String[] sumByIdSeller(Integer idSeller);
    
}
