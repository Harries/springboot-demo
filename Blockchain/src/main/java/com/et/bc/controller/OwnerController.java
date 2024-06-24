package com.et.bc.controller;

import com.et.bc.model.Balance;
import com.et.bc.service.LotteryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.io.IOException;
import java.util.List;

@RestController
public class OwnerController {

    @Value("${lottery.contract.owner-address}")
    private String ownerAddress;

    @Autowired
    private Web3j web3j;

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/owner")
    public String getAddress() {
        return ownerAddress;
    }

    @GetMapping("/owner/balance")
    public Balance getBalance() throws IOException {
        EthGetBalance wei = web3j.ethGetBalance(ownerAddress, DefaultBlockParameterName.LATEST).send();

        return new Balance(wei.getBalance());
    }

    @GetMapping("/owner/lottery/players")
    public List<String> getPlayers() throws Exception {
        return lotteryService.getPlayers(ownerAddress);
    }

    @GetMapping("/owner/lottery/pickWinner")
    public void pickWinner() throws Exception {
        lotteryService.pickWinner(ownerAddress);
    }
}
