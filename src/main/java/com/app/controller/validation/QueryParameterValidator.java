package com.app.controller.validation;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class QueryParameterValidator {

    private QueryParameterValidator() {
    }

    public static void validate(HttpServletRequest request, String... parameters) throws ApplicationException {
        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> parameterNamesList = new ArrayList<>();
        int count = 0;
        while (parameterNames.hasMoreElements()) {
            parameterNamesList.add(parameterNames.nextElement());
        }
        for (String pName : parameterNamesList) {
            for (String requiredParameters : parameters) {
                if (Objects.equals(pName, requiredParameters)) {
                    count++;
                }
            }
        }
        if (parameters.length != parameterNamesList.size() || parameters.length != count) {
            throw new ApplicationException(Message.Error.INVALID_PARAMETER);
        }
    }
}
