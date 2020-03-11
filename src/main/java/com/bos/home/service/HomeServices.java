package com.bos.home.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.home.dto.TrxSum;
import com.bos.home.entity.TrxDim;
import com.bos.home.repository.TrxRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServices {
    @Autowired
    TrxRepo trxRepo;

    public ResultEntity<TrxSum> getSumTrx(Integer idSeller){
//        System.out.println(trxRepo.sumByIdSeller(idSeller);;
        String[] trxSumArr = trxRepo.sumByIdSeller(idSeller);


        String temp = trxSumArr[0];
        String tempArr[] = temp.split(",");

        TrxSum trxSum = new TrxSum();
        trxSum.setSum(Integer.valueOf(tempArr[0]));
        trxSum.setSumNom(Float.valueOf(tempArr[1]));

        if (trxSum.equals(null)){
            System.out.println("Failed");
            return new ResultEntity<>(trxSum, ErrorCode.BIT_999);
        }
        else{
            System.out.println("Success");
            return new ResultEntity<>(trxSum, ErrorCode.BIT_000);
        }
    }

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
