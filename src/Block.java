import java.util.Date;


public class Block {

	public String hashCode;
	public String previousHashCode;
	private String blockData;
	private long timeStamp;
	
	public Block( String blockData, String previousHashCode) {
		this.blockData = blockData;
		this.previousHashCode = previousHashCode;
		this.hashCode = calcHashCode();
		this.timeStamp = new Date().getTime();
	}
	
	public String calcHashCode() {
		
		String calculatedHash = StringUtil.applySHA356(previousHashCode + 
				Long.toString(timeStamp) +
				blockData
				);
		return calculatedHash;
	}
	
}
