package com.et.bc.config;

import com.et.bc.model.Lottery;
import com.et.bc.properties.LotteryProperties;
import com.et.bc.service.LotteryService;
import okhttp3.OkHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

@Configuration
public class LotteryConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(LotteryConfiguration.class);

    @Value("${lottery.contract.owner-address}")
    private String ownerAddress;

    @Value("${web3j.client-address}")
    private String clientAddress;

    @Autowired
    private LotteryProperties config;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(clientAddress, new OkHttpClient.Builder().build()));
    }

    @Bean
    public LotteryService contract(Web3j web3j, @Value("${lottery.contract.address:}") String contractAddress)
            throws Exception {
        if (StringUtils.isEmpty(contractAddress)) {
            Lottery lottery = deployContract(web3j);
            return initLotteryService(lottery.getContractAddress(), web3j);
        }

        return initLotteryService(contractAddress, web3j);
    }

    private LotteryService initLotteryService(String contractAddress, Web3j web3j) {
        return new LotteryService(contractAddress, web3j, config);
    }

    private Lottery deployContract(Web3j web3j) throws Exception {
        LOG.info("About to deploy new contract...");
        Lottery contract = Lottery.deploy(web3j, txManager(web3j), config.gas()).send();
        LOG.info("Deployed new contract with address '{}'", contract.getContractAddress());
        return contract;
    }

    private TransactionManager txManager(Web3j web3j) {
        return new ClientTransactionManager(web3j, ownerAddress);
    }

}
