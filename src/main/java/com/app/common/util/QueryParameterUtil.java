package com.app.common.util;

import com.app.common.Message;
import com.app.common.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class QueryParameterUtil {
    public static void checkQueryParameters(HttpServletRequest request, String... str) throws ApplicationException {
        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> parameterNamesList = new ArrayList<>();
        int count = 0;
        while (parameterNames.hasMoreElements()) {
            parameterNamesList.add(parameterNames.nextElement());
        }
        for (String pName : parameterNamesList) {
            for (String requiredParameters : str) {
                if (Objects.equals(pName, requiredParameters)) {
                    count++;
                }
            }
        }
        if (str.length != count) {
            throw new ApplicationException(Message.Error.NOT_A_PARAMETER);
        }
    }
}
