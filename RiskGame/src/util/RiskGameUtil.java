package util;

import java.util.Objects;

/**
 * This class will have common utility methods
 * for example  checking null string or blank string
 *
 * @author prashantp95
 */
public class RiskGameUtil {
    /**
     * to check null or empty string
     * input String , output boolean(true/false)
     *
     * @param name: String to be check.
     * @return true or false based on validation
     */
    public static boolean checkNullString(String name) {
        return !Objects.equals(name, "") && name != null && !name.isEmpty();
    }
}
