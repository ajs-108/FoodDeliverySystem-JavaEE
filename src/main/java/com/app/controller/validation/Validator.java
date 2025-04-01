package com.app.controller.validation;

import java.util.regex.Pattern;

public class Validator {
    private boolean flag = false;
    private Pattern pattern;

    public boolean checkPassword(String password) {
        flag = false;
        pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[`~!@#$%^&*()_\\-+={}\\[\\]'\":;?/<>,.|]).{8,16}$");
        if (pattern.matcher(password).matches()) {
            flag = true;
        }
        return flag;
    }

    public boolean checkPhoneNo(String contactNo) {
        flag = false;
        pattern = Pattern.compile("[1-9][\\d]{9}");
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

    public boolean isPositiveInteger(String number) {
        flag = false;
        pattern = Pattern.compile("\\d+");
        if (pattern.matcher(number).matches()) {
            flag = true;
        }
        return flag;
    }

    public boolean isBoolean(String bool) {
        flag = false;
        pattern = Pattern.compile("(?i)true|false");
        if (pattern.matcher(bool).matches()) {
            flag = true;
        }
        return flag;
    }
}
