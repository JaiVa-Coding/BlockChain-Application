import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.GsonBuilder;
public class JavaChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	public static Wallet walletA;
	public static Wallet walletB;
	public static int minimumTransaction;
	
	public static void main(String[] args) {
		/*
		blockchain.add(new Block("Hi this is the first block : ", "0"));
		System.out.println("Trying to mine Block1");
		blockchain.get(0).mineBlock(difficulty);
		blockchain.add(new Block("Hi this is the second block : ", blockchain.get(blockchain.size()-1).hashCode));
		System.out.println("Trying to mine Block2");
		blockchain.get(1).mineBlock(difficulty);
		blockchain.add(new Block("Hi this is the third block : ",blockchain.get(blockchain.size()-1).hashCode));
		System.out.println("Trying to mine Block3");
		blockchain.get(2).mineBlock(difficulty);
		
		
		System.out.println("The Blockchain is Valid : " + isChainValid());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("This is the Blockchain : ");
		System.out.println(blockchainJson);
		*/
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		//Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified : ");
		System.out.println(transaction.verifySignature());
	}
	
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i = 1 ; i < blockchain.size();i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			
			if(!currentBlock.hashCode.equals(currentBlock.calcHashCode())) {
				System.out.println("CurrentHashes are not equal");
				return false;
			}
			if(!previousBlock.hashCode.equals(currentBlock.previousHashCode)) {
				System.out.println("PreviousHashes are not equal");
				return false;
			}
			
			if(!currentBlock.hashCode.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block has not been mined yet, go mine it!");
				return false;
			}
		}
		return true;
		
	}

	
}
