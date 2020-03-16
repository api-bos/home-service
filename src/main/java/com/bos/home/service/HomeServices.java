package com.bos.home.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.home.dto.ByrSum;
import com.bos.home.dto.PrdSum;
import com.bos.home.dto.TrxSum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServices {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ResultEntity<List<TrxSum>> getSumTrx(Integer idSeller){
        String query = "select count(t.id_seller) as sumTrx, sum(t.total_payment) as nomTrx " +
                "from transaction t " +
                "where date_part('day',current_date() - payment_time) <= 30 AND t.id_seller = "+idSeller;

        List<TrxSum> data = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(TrxSum.class));

        if(data.size() > 0){
            System.out.println("Data is not NULL");
            return new ResultEntity<>(data, ErrorCode.BIT_000);
        }
        else
        {
            System.out.println("Data is NULL");
            return new ResultEntity<>(data, ErrorCode.BIT_999);
        }

        /*
        String[] trxSumArr = trxRepo.sumByIdSeller(idSeller);
        String temp = trxSumArr[0];
        String tempArr[] = temp.split(",");

        TrxSum trxSum = new TrxSum();
        trxSum.setSumTrx(tempArr[0]);
        trxSum.setNomTrx(tempArr[1]);

        if (trxSum.equals(null)){
            System.out.println("Failed");
            return new ResultEntity<>(trxSum, ErrorCode.BIT_999);
        }
        else{
            System.out.println("Success");
            return new ResultEntity<>(trxSum, ErrorCode.BIT_000);
        }

         */
    }

    public ResultEntity<List<TrxSum>> getSumTrxByDate(Integer idSeller, String startDt, String endDt){
        String sDt = startDt + " 00:00:00";
        String eDt = endDt + " 23:59:59";

        String query = "select count(t.id_seller) as sumTrx, sum(t.total_payment) as nomTrx " +
                "from transaction t " +
                "where t.id_seller = "+idSeller+" AND " +
                "payment_time between '"+sDt+"' and (date '"+eDt+"' + interval '1 day')";

        List<TrxSum> data = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(TrxSum.class));

        if(data.size() > 0){
            System.out.println("Data is not NULL");
            return new ResultEntity<>(data, ErrorCode.BIT_000);
        }
        else
        {
            System.out.println("Data is NULL");
            return new ResultEntity<>(data, ErrorCode.BIT_999);
        }

        /*
        String[] trxSumArr = trxRepo.sumByIdSellerAndDate(idSeller, sDt, eDt);
        String temp = trxSumArr[0];
        String tempArr[] = temp.split(",");

        TrxSum trxSum = new TrxSum();
        trxSum.setSumTrx(tempArr[0]);
        trxSum.setNomTrx(tempArr[1]);

        if (trxSum.equals(null)){
            System.out.println("Failed");
            return new ResultEntity<>(trxSum, ErrorCode.BIT_999);
        }
        else{
            System.out.println("Success");
            return new ResultEntity<>(trxSum, ErrorCode.BIT_000);
        }

         */
    }

    public ResultEntity<List<PrdSum>> getSumPrd(Integer idSeller){
        String query = "select prd.product_name as productName, sum(td.quantity) as qty " +
                "from transaction_detail td " +
                "left join product prd on td.id_product = prd.id_product " +
                "where td.id_transaction " +
                "in (select id_transaction " +
                "from transaction " +
                "where id_seller= " + idSeller + " AND date_part('day',current_date() - payment_time) <= 30) " +
                "group by prd.product_name " +
                "order by sum(td.quantity) desc " +
                "limit 5";

        List<PrdSum> prdSums = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PrdSum.class));
        System.out.println("Query Success");

        if(prdSums.size() > 0){
            System.out.println("Data is not NULL");
            return new ResultEntity<>(prdSums, ErrorCode.BIT_000);
        }
        else
        {
            System.out.println("Data is NULL");
            return new ResultEntity<>(prdSums, ErrorCode.BIT_999);
        }

    }

    public ResultEntity<List<PrdSum>> getSumPrdByDate(Integer idSeller, String startDt, String endDt){
        String sDt = startDt + " 00:00:00";
        String eDt = endDt + " 23:59:59";

        String query = "select prd.product_name, SUM(td.quantity) as qty " +
                "from transaction_detail td " +
                "left join product prd on td.id_product = prd.id_product " +
                "where td.id_transaction " +
                "in (select id_transaction " +
                    "from transaction " +
                    "where id_seller = " + idSeller +  " AND " +
                    "payment_time between '"+sDt+"' and (date '"+eDt+"' + interval '1 day')) "+
                "group by prd.product_name " +
                "order by sum(td.quantity) desc " +
                "limit 5";

        List<PrdSum> prdSums = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PrdSum.class));
        System.out.println("Query Success");

        if(prdSums.size() > 0){
            System.out.println("Data is not NULL");
            return new ResultEntity<>(prdSums, ErrorCode.BIT_000);
        }
        else
        {
            System.out.println("Data is NULL");
            return new ResultEntity<>(prdSums, ErrorCode.BIT_999);
        }
        /*
        String sDt = startDt + " 00:00:00";
        String eDt = endDt + " 23:59:59";

        String[] prdSumArr = detailRepo.sumPrdByIdSellerAndDate(idSeller, sDt, eDt);

        List<PrdSum> prdSumList = new ArrayList<>();

        for(int i = 0; i < prdSumArr.length; i++){
            String temp = prdSumArr[i];
            String[] tempArr = temp.split(",");
            PrdSum prdSum = new PrdSum();
            prdSum.setProductName(tempArr[0]);
            prdSum.setQty(Integer.valueOf(tempArr[1]));
            prdSumList.add(prdSum);
        }

        if (prdSumList.isEmpty()){
            return new ResultEntity<>(null, ErrorCode.BIT_999);
        }else return new ResultEntity<>(prdSumList, ErrorCode.BIT_000);

         */
    }

    public ResultEntity<List<ByrSum>> getBuyer(Integer idSeller){
        String query = "select b.buyer_name as name, b.phone as mobileNum, count(t.id_buyer) as sumTrx\n" +
                "from transaction t\n" +
                "left join buyer b on t.id_buyer = b.id_buyer \n" +
                "where t.id_seller = "+idSeller+" AND date_part('day',current_date() - payment_time) <= 30\n" +
                "group by b.buyer_name, b.phone \n" +
                "order by count(t.id_buyer) desc";

        List<ByrSum> data = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ByrSum.class));
        System.out.println("Query Success");

        if(data.size() > 0){
            System.out.println("Data is not NULL");
            return new ResultEntity<>(data, ErrorCode.BIT_000);
        }
        else
        {
            System.out.println("Data is NULL");
            return new ResultEntity<>(data, ErrorCode.BIT_999);
        }
    }
}
