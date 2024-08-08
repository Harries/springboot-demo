package com.et.seata.balance.service;

import com.et.seata.balance.dao.AccountDao;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
 

    @Autowired
    private AccountDao accountDao;
 
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW) // <1> 开启新事物
    public void reduceBalance(Long userId, Integer price) throws Exception {
        log.info("[reduceBalance] 当前 XID: {}", RootContext.getXID());
 
        // <2> 检查余额
        checkBalance(userId, price);
 
        log.info("[reduceBalance] 开始扣减用户 {} 余额", userId);
        // <3> 扣除余额
        int updateCount = accountDao.reduceBalance(price);
        // 扣除成功
        if (updateCount == 0) {
            log.warn("[reduceBalance] 扣除用户 {} 余额失败", userId);
            throw new Exception("余额不足");
        }
        log.info("[reduceBalance] 扣除用户 {} 余额成功", userId);
    }
 
    private void checkBalance(Long userId, Integer price) throws Exception {
        log.info("[checkBalance] 检查用户 {} 余额", userId);
        Integer balance = accountDao.getBalance(userId);
        if (balance < price) {
            log.warn("[checkBalance] 用户 {} 余额不足，当前余额:{}", userId, balance);
            throw new Exception("余额不足");
        }
    }
 
}