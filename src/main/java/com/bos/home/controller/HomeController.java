package com.bos.home.controller;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.home.dto.PrdSum;
import com.bos.home.dto.TrxSum;
import com.bos.home.service.HomeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/home")
@CrossOrigin(origins = "*")
public class HomeController {
    @Autowired
    HomeServices services;

    @GetMapping(value = "/trx", params = "seller", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultEntity<TrxSum> getSumTrx(@RequestParam("seller") Integer idSeller){
        try{
            System.out.println("Try Get Sum Trx");
            System.out.println("seller: " + idSeller);

            ResultEntity hasil = services.getSumTrx(idSeller);
            System.out.println("Request Succeeded");
            return hasil;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResultEntity<>(null, ErrorCode.BIT_999);
        }
    }

    @GetMapping(value = "/trx", params = {"seller", "start-dt", "end-dt"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultEntity<TrxSum> getSumTrxByDate(@RequestParam("seller") Integer idSeller,
                                                @RequestParam("start-dt") String startDt,
                                                @RequestParam("end-dt") String endDt){
        try {
            System.out.println("Try Get Sum Trx by Date");
            System.out.println("seller: " + idSeller);
            System.out.println("start-dt: " + startDt);
            System.out.println("end-dt: " + endDt);

            ResultEntity hasil = services.getSumTrxByDate(idSeller,startDt,endDt);
            System.out.println("Request Succeeded");
            return hasil;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResultEntity<>(null, ErrorCode.BIT_999);
        }

    }


    @GetMapping(value = "/prd", params = "seller", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultEntity<List<PrdSum>> getPrdSum(@RequestParam("seller") Integer idSeller){
        try{
            System.out.println("Try Get Sum Prd");
            System.out.println("seller: " + idSeller);

            ResultEntity hasil = services.getSumPrd(idSeller);
            System.out.println("Request Succeeded");
            return hasil;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResultEntity<>(null, ErrorCode.BIT_999);
        }
    }

    @GetMapping(value = "/prd", params = {"seller", "start-dt", "end-dt"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultEntity<TrxSum> getSumPRdByDate(@RequestParam("seller") Integer idSeller,
                                                @RequestParam("start-dt") String startDt,
                                                @RequestParam("end-dt") String endDt){
        try {
            System.out.println("Try Get Sum Prd by Date");
            System.out.println("seller: " + idSeller);
            System.out.println("start-dt: " + startDt);
            System.out.println("end-dt: " + endDt);

            ResultEntity hasil = services.getSumPrdByDate(idSeller,startDt,endDt);
            System.out.println("Request Succeeded");
            return hasil;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResultEntity<>(null, ErrorCode.BIT_999);
        }

    }

    @GetMapping(value = "/byr", params = "seller", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultEntity<List<PrdSum>> getBuyer(@RequestParam("seller") Integer idSeller){
        try{
            System.out.println("Try Get Sum Byr");
            System.out.println("seller: " + idSeller);

            ResultEntity hasil = services.getBuyer(idSeller);
            System.out.println("Request Succeeded");
            return hasil;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ResultEntity<>(null, ErrorCode.BIT_999);
        }
    }
}
