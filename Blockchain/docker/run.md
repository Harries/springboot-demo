### Deploy a Custom Ethereum Network

```shell
docker run -d --name ethereum -p 8545:8545 -p 30303:30303 ethereum/client-go --http --http.addr="0.0.0.0" --http.api="db,eth,net,web3,personal" --http.corsdomain="*" --dev
```
### view logs

```shell
docker logs -f ethereum
```

### Create Account
```shell
docker exec -it ethereum geth attach ipc:/tmp/geth.ipc
```

```shell
Welcome to the Geth JavaScript console!

instance: Geth/v1.10.15-unstable-356bbe34/linux-amd64/go1.17.5
coinbase: 0x40f074a6c0e40f7c5167718355375c6f2c509690
at block: 0 (Thu Jan 01 1970 00:00:00 GMT+0000 (UTC))
 datadir:
 modules: admin:1.0 clique:1.0 debug:1.0 eth:1.0 miner:1.0 net:1.0 personal:1.0 rpc:1.0 txpool:1.0 web3:1.0

To exit, press ctrl-d or type exit
> personal.newAccount('ABC')
"0x9b418710ce8438e5fe585b519e8d709e1ea77aca"
> eth.accounts
["0x40f074a6c0e40f7c5167718355375c6f2c509690", "0x9b418710ce8438e5fe585b519e8d709e1ea77aca"]
```

### Send Ethers to Accounts
```shell
> eth.sendTransaction({from: eth.accounts[0], to: eth.accounts[1], value: web3.toWei(100000, 'ether')})
"0x48fe4bd2d6db424ecc1f3713809d53e103fa7fc63646f4051a6a280d5f7080ea"
> eth.getBalance(eth.accounts[1])
```


