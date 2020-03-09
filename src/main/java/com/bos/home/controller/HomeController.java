package com.bos.home.controller;

import bca.bit.proj.library.base.ResultEntity;
import com.bos.home.entity.TrxDim;
import com.bos.home.service.HomeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/home")
public class HomeController {
    @Autowired
    HomeServices services;

    @GetMapping(value = "/trx", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultEntity<List<TrxDim>> getAllTrx(){
        System.out.println("Try Get All Trx");
        return services.getAllTrx();
    }
}
