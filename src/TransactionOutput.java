import java.security.PublicKey;

public class TransactionOutput {

	public String id;
	public PublicKey reciever; //also known as the new owner of these coins.
	public float value; //the amount of coins they own
	public String parentTransactionId;
	public TransactionOutput(PublicKey reciever, float value, String parentTransactionId) {
		
		this.id = StringUtil.applySHA256(StringUtil.getStringFromKey(reciever) + Float.toString(value) + parentTransactionId);
		this.reciever = reciever;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
	}
	
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciever);
	}
}
