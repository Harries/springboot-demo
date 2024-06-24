package com.et.bc.utils;

import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ConvertUtils {
    public static BigDecimal toEther(BigInteger wei) {
        return Convert.fromWei(new BigDecimal(wei), Unit.ETHER);
    }
}
