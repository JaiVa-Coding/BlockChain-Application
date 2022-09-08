import java.util.ArrayList;
import com.google.gson.GsonBuilder;
public class JavaChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static void main(String[] args) {
		blockchain.add(new Block("Hi this is the first block : ", "0"));
		blockchain.add(new Block("Hi this is the second block : ", blockchain.get(blockchain.size()-1).hashCode));
		blockchain.add(new Block("Hi this is the third block : ",blockchain.get(blockchain.size()-1).hashCode));
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(blockchainJson);
	}
	
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		
		for(int i = 0 ; i < blockchain.size();i++) {
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
		}
		return true;
		
	}
}
