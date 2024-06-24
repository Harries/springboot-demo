pragma solidity >0.4.99 <0.6.0;

contract Lottery {
    address public manager;
    address payable[] private players;

    constructor() public {
        manager = msg.sender;
    }
    
    function enter() public payable {
        require(msg.value > .01 ether, "Must have at least 0.01 ether");
        
        players.push(msg.sender);
    }
    
    function pickWinner() public restricted {
        uint index = random() % players.length;
        address payable winner = address(players[index]);
        address contest = address(this);
        winner.transfer(contest.balance);
        players = new address payable[](0);
    }
    
    function getPlayers() public restricted view returns (address payable[] memory) {
        return players;
    }
    
    function random() private view returns (uint) {
        return uint(keccak256(abi.encodePacked(block.difficulty, now, players)));
    }
    
    modifier restricted() {
        require(manager == msg.sender, "Only Manager can pick the winner");
        _;
    }
}