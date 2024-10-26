package validation;

import java.util.regex.Pattern;

public class CustomerValidator {

    // Regular expressions for validation
    private static final String CUST_ID_REGEX = "^C\\d{3}$"; // Alphanumeric characters
    private static final String TITLE_REGEX = "^(Mr|Ms|Miss)$";
    private static final String NAME_REGEX = "^[A-Za-z\\s]+$"; // Letters and spaces
    private static final String ADDRESS_REGEX = "^[A-Za-z0-9\\s,]+$"; // Alphanumeric, spaces, and commas
    private static final String PHONE_REGEX = "^\\d{10}$"; // 10-digit phone number
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Simple email format

    public boolean validateCustId(String custId) {
        return Pattern.matches(CUST_ID_REGEX, custId);
    }

    public boolean validateTitle(String title) {
        return Pattern.matches(TITLE_REGEX, title);
    }

    public boolean validateName(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }

    public boolean validateAddress(String address) {
        return Pattern.matches(ADDRESS_REGEX, address);
    }

    public boolean validatePhone(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }

    public boolean validateEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean validateNotEmpty(String input) {
        return !input.trim().isEmpty(); // Check for non-empty strings
    }

    public boolean validateCustomerData(String custId, String title, String name, String address, String phone, String email) {
        return validateCustId(custId)
                && validateNotEmpty(title) && validateTitle(title)
                && validateNotEmpty(name) && validateName(name)
                && validateNotEmpty(address) && validateAddress(address)
                && validatePhone(phone)
                && validateEmail(email);
    }

}
