import java.util.ArrayList;
import java.util.Date;


public class Block {

	public String hashCode;
	public String previousHashCode;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public String merkleRoot;
	private long timeStamp;
	private int nonce;
	
	
	public Block( String previousHashCode) {
		
		this.previousHashCode = previousHashCode;
		this.hashCode = calcHashCode();
		this.timeStamp = new Date().getTime();
	}
	
	public String calcHashCode() {
		
		String calculatedHash = StringUtil.applySHA256(previousHashCode + 
				Long.toString(timeStamp) +
				Integer.toString(nonce) +
				merkleRoot
				);
		return calculatedHash;
	}
	
	public void mineBlock(int difficulty ) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = StringUtil.getDifficultyString(difficulty);		
		while(!hashCode.substring(0,difficulty).equals(target)) {
			nonce++;
			hashCode = calcHashCode();
		}
		System.out.println(" Block has been mined : " + hashCode);
	}
	
	public boolean addTransaction(Transaction transaction) {
		if(transaction == null) 
			return false;
		if(previousHashCode != "0") {
			if(transaction.processTransaction() != true) {
				System.out.println("Transaction has failed to process, discarded");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction has been successfully added to the block");
		return true;
		
	}
	}

