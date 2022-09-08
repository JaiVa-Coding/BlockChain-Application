import java.security.MessageDigest;

public class StringUtil {

	public static String applySHA356(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashCode = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hashCode.length; i++) {
				String hex = Integer.toHexString(0xff & hashCode[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
			
		}
		
		catch(Exception e) {
			throw new RuntimeException();
		}
	}
}
