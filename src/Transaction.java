import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
	
	
	public String transactionId;
	public PublicKey sender;
	public PublicKey reciever;
	public float value;
	public byte[] signature;
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence;

	public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
		super();
		this.sender = from;
		this.reciever = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	private String calcHash() {
		
		sequence++;
		
		return StringUtil.applySHA256(
				StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(reciever) +
				Float.toString(value) +
				sequence
				);
				
	}
	
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciever) + Float.toString(value);
	    signature = StringUtil.applyECDSASig(privateKey,data);
	}
	
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciever) + Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
		
	}
	
	public boolean processTransaction() {
		
		if(verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		
		for(TransactionInput i : inputs) {
			i.UTXO = JavaChain.UTXOs.get(i.transactionOutputID);
		}
		
		if(getInputsValue()  < JavaChain.minimumTransaction) {
			System.out.println("Transaction Inputs too small :" + getInputsValue());
			return false;
		}
		
		float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
		transactionId = calcHash();
		outputs.add(new TransactionOutput( this.reciever, value,transactionId)); //send value to recipient
		outputs.add(new TransactionOutput( this.sender, leftOver,transactionId));
		
		for(TransactionOutput o : outputs) {
			JavaChain.UTXOs.put(o.id , o);
		}
		
		
	}
	
	

}
