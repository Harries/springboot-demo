package com.et.bc.model;

import com.et.bc.utils.ConvertUtils;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
public class Balance {

    private final BigInteger wei;
    private final BigDecimal ether;

    public Balance(BigInteger wei) {
        this.wei = wei;
        this.ether = ConvertUtils.toEther(wei);
    }
}
