package com.bos.home.service;

import bca.bit.proj.library.base.ResultEntity;
import bca.bit.proj.library.enums.ErrorCode;
import com.bos.home.dto.ByrSum;
import com.bos.home.dto.PrdSum;
import com.bos.home.dto.TrxGraph;
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
                "where date_part('day',current_date() - confirmation_time) <= 30 AND t.id_seller = "+idSeller;

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
    }

    public ResultEntity<List<TrxSum>> getSumTrxByDate(Integer idSeller, String startDt, String endDt){

        String query = "select count(t.id_seller) as sumTrx, sum(t.total_payment) as nomTrx " +
                "from transaction t " +
                "where t.id_seller = "+idSeller+" AND " +
                "confirmation_time between '"+startDt+"' and (date '"+endDt+"' + interval '1 day')";

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
    }

    public ResultEntity<List<PrdSum>> getSumPrd(Integer idSeller){
        String query = "select prd.id_product, prd.product_name, prd.image_path, SUM(td.quantity) as qty " +
                "from transaction_detail td " +
                "left join product prd on td.id_product = prd.id_product " +
                "where td.id_transaction " +
                "in (select id_transaction " +
                "from transaction " +
                "where id_seller= " + idSeller + " AND date_part('day',current_date() - confirmation_time) <= 30) " +
                "group by prd.id_product, prd.product_name " +
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

        String query = "select prd.id_product, prd.product_name, prd.image_path, SUM(td.quantity) as qty " +
                "from transaction_detail td " +
                "left join product prd on td.id_product = prd.id_product " +
                "where td.id_transaction " +
                "in (select id_transaction " +
                    "from transaction " +
                    "where id_seller = " + idSeller +  " AND " +
                    "confirmation_time between '"+startDt+"' and (date '"+endDt+"' + interval '1 day')) "+
                "group by prd.id_product, prd.product_name " +
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

    public ResultEntity<List<ByrSum>> getBuyer(Integer idSeller){
        String query = "select t.id_buyer as idBuyer, b.buyer_name as buyerName, b.phone, count(t.id_buyer) as sumTrx\n" +
                "from transaction t\n" +
                "left join buyer b on t.id_buyer = b.id_buyer \n" +
                "where t.id_seller = "+idSeller+" AND date_part('day',current_date() - confirmation_time) <= 30\n" +
                "group by t.id_buyer, b.buyer_name, b.phone \n" +
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

    public ResultEntity<List<TrxGraph>> getTrxGraph(Integer idSeller){
        String query = "select to_char(date_trunc('month', t.confirmation_time), 'yyyy-mm') as date,\n" +
                "sum(t.total_payment ) as nomTrx\n" +
                "from transaction t\n" +
                "where date_part('day',current_date() - confirmation_time) <= 30 AND t.id_seller = "+idSeller+"\n"+
                "group by 1\n" +
                "order by 1";

        List<TrxGraph> data = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(TrxGraph.class));

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

    public ResultEntity<List<TrxGraph>> getTrxGraphByDate(Integer idSeller, String startDt, String endDt){
        String query = "select to_char(date_trunc('month', t.confirmation_time), 'yyyy-mm') as date,\n" +
                "sum(t.total_payment ) as nomTrx\n" +
                "from transaction t\n" +
                "where t.id_seller = "+idSeller+" AND\n" +
                "t.confirmation_time between '"+startDt+"' and (date '"+endDt+"' + interval '1 day') \n" +
                "group by 1\n" +
                "order by 1";

        List<TrxGraph> data = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(TrxGraph.class));

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
