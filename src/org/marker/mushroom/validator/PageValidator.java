package org.marker.mushroom.validator;

import org.marker.mushroom.beans.Page;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



public class PageValidator implements Validator {

	public boolean supports(Class<?> clzz) {
		return Page.class.equals(clzz);
	}

	public void validate(Object obj, Errors e) {
		System.out.println("page");
		ValidationUtils.rejectIfEmpty(e, "currentPageNo", "email.empty");
	}

}
