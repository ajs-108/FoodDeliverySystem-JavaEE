package controller.validation;

import java.util.regex.Pattern;

public class Validator {
    private boolean flag = false;
    private Pattern pattern;

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
