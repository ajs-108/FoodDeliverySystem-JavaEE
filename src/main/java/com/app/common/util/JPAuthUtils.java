package com.app.common.util;

import com.app.common.Message;
import com.app.common.enums.Roles;
import com.app.common.exception.ApplicationException;
import com.app.dto.jpa.JPAUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class JPAuthUtils {
    public static void checkAuthentication(HttpServletRequest request) throws ApplicationException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ApplicationException(Message.Error.UNAUTHORIZED_ACCESS);
        }
    }

    public static HttpSession getCurrentUserSession(HttpServletRequest request) throws ApplicationException {
        checkAuthentication(request);
        return request.getSession(false);
    }

    public static JPAUserDTO getCurrentUser(HttpServletRequest request) throws ApplicationException {
        return (JPAUserDTO) getCurrentUserSession(request).getAttribute("user");
    }

    public static boolean isAdmin(HttpServletRequest request) throws ApplicationException {
        JPAUserDTO currentUserDTO = getCurrentUser(request);
        return currentUserDTO.getRole().getRoleId() == Roles.ROLE_SUPER_ADMIN.getRoleId();
    }

    public static boolean isDeliveryPerson(HttpServletRequest request) throws ApplicationException {
        JPAUserDTO currentUserDTO = getCurrentUser(request);
        return currentUserDTO.getRole().getRoleId() == Roles.ROLE_DELIVERY_PERSON.getRoleId();
    }

    public static boolean isCustomer(HttpServletRequest request) throws ApplicationException {
        JPAUserDTO currentUserDTO = getCurrentUser(request);
        return currentUserDTO.getRole().getRoleId() == Roles.ROLE_CUSTOMER.getRoleId();
    }
}
