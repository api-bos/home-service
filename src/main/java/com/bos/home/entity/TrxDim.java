package com.bos.home.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Builder
@Table(name = "transaction", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TrxDim {
    @Id
    @Column(name = "id_transaction")
    private Integer idTransaction;

    @Column(name = "id_buyer")
    private Integer idBuyer;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "payment_time")
    private Date paymentTime;

    @Column(name = "payment_account")
    private String paymentAccount;

    @Column(name = "shipping_code")
    private String shippingCode;

    @Column(name = "shipping_fee")
    private Integer shippingFee;

    @Column(name = "shipping_agent")
    private String shippingAgent;

    @Column(name = "shipping_time")
    private Date shippingTime;

    @Column(name = "shipping_address")
    private Integer shippingAddress;

    @Column(name = "total_payment")
    private Integer totalPayment;

    @Column(name = "confirmation_time")
    private Date confirmationTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "id_seller")
    private Integer idSeller;
}
