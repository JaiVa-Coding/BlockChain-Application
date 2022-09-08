import java.util.Date;


public class Block {

	public String hashCode;
	public String previousHashCode;
	private String blockData;
	private long timeStamp;
	private int nonce;
	
	
	public Block( String blockData, String previousHashCode) {
		this.blockData = blockData;
		this.previousHashCode = previousHashCode;
		this.hashCode = calcHashCode();
		this.timeStamp = new Date().getTime();
	}
	
	public String calcHashCode() {
		
		String calculatedHash = StringUtil.applySHA256(previousHashCode + 
				Long.toString(timeStamp) +
				Integer.toString(nonce) +
				blockData
				);
		return calculatedHash;
	}
	
	public void mineBlock(int difficulty ) {
		String target = StringUtil.getDifficultyString(difficulty);		
		while(!hashCode.substring(0,difficulty).equals(target)) {
			nonce++;
			hashCode = calcHashCode();
		}
		System.out.println("New Block has been mined : " + hashCode);
	}
	
}
