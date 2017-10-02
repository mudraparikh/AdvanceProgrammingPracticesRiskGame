package util;
/**
 * This class will have common utility methods 
 * for example  checking null string or blank string
 * @author prashantp95
 *
 */
public class RiskGameUtil {
	/**
	 * to check null or empty string
	 * input String , output boolean(true/false)
	 */
	public static boolean checkNullString(String name) {
		if(name!="" && name!=null && !name.isEmpty()) {
			return true;
		}return false;
	}
}
