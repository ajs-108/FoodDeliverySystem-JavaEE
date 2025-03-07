package validation;

import java.util.regex.Pattern;

/**
 * Validator class is used for validating the incoming form fields such as password, email, contact no.
 * It ensures that no wrong data is being inserted into the database.
 */
public class Validator {
    private boolean flag = false;
    private Pattern pattern;

    public static void main(String[] args) {
        Validator v = new Validator();
        String e = "admin@email.com";
        System.out.println(v.checkEmail(e));
    }

    public boolean checkPassword(String password) {
        flag = false;
        pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[`~!@#$%^&*()_\\-+={}\\[\\]'\":;?/<>,.|]).{8,41}$");

        if (pattern.matcher(password).matches()) {
            flag = true;
        }
        return flag;
    }

    public boolean checkContactNo(String contactNo) {
        flag = false;
        pattern = Pattern.compile("[1-9][0-9]{9}");
        if (pattern.matcher(contactNo).matches()) {
            flag = true;
        }

        return flag;
    }

    public boolean checkEmail(String email) {
        flag = false;
        pattern = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+[.]?[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$");

        if (pattern.matcher(email).matches()) {
            flag = true;
        }
        return flag;
    }
}
