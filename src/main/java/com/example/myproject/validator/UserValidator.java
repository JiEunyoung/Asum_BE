package com.example.myproject.validator;

import com.example.myproject.model.ProUserBean;
import com.example.myproject.model.UserBean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
 
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserBean.class.isAssignableFrom(clazz) || ProUserBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String beanName = errors.getObjectName();

        if (target instanceof UserBean && "joinUserBean".equals(beanName)) {
            UserBean userBean = (UserBean) target;
            if (!userBean.isUserEmailExist()) {
                errors.rejectValue("user_email", "DonCheckuserIdExist");
            }
        } else if (target instanceof ProUserBean && "joinProuserBean".equals(beanName)) {
            ProUserBean proUserBean = (ProUserBean) target;
            if (!proUserBean.isProuserEmailExist()) {
                errors.rejectValue("pro_email", "DonCheckProuserIdExist");
            }
        }
    }
}