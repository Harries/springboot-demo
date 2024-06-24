package com.et.bc.service;

import com.et.bc.model.Lottery;
import com.et.bc.model.Player;
import com.et.bc.properties.LotteryProperties;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class LotteryService {

    private final String contractAddress;
    private final Web3j web3j;
    private final LotteryProperties config;

    public LotteryService(String contractAddress, Web3j web3j, LotteryProperties config) {
        this.contractAddress = contractAddress;
        this.web3j = web3j;
        this.config = config;
    }

    public BigInteger getBalance() throws IOException {
        return web3j.ethGetBalance(contractAddress, DefaultBlockParameterName.LATEST).send().getBalance();
    }

    public void join(Player player) throws Exception {
        Lottery lottery = loadContract(player.getAddress());
        lottery.enter(Convert.toWei(player.getEthers(), Unit.ETHER).toBigInteger()).send();
    }

    @SuppressWarnings("unchecked")
    public List<String> getPlayers(String ownerAddress) throws Exception {
        Lottery lottery = loadContract(ownerAddress);
        return lottery.getPlayers().send();
    }

    public void pickWinner(String ownerAddress) throws Exception {
        Lottery lottery = loadContract(ownerAddress);
        lottery.pickWinner().send();
    }

    private Lottery loadContract(String accountAddress) {
        return Lottery.load(contractAddress, web3j, txManager(accountAddress), config.gas());
    }

    private TransactionManager txManager(String accountAddress) {
        return new ClientTransactionManager(web3j, accountAddress);
    }
}
