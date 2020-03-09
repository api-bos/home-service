package com.bos.home.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.home.entity.TrxDim;
import com.bos.home.repository.TrxRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServices {
    @Autowired
    TrxRepo trxRepo;

    public ResultEntity<List<TrxDim>> getAllTrx(){
        List<TrxDim> data = trxRepo.findAll();

        if (data.size() > 0){
            System.out.println("Success");
            return new ResultEntity<>(data, ErrorCode.BIT_000);
        }
        else{
            System.out.println("Failed");
            return new ResultEntity<>(data, ErrorCode.BIT_999);
        }
    }
}
