package org.marker.mushroom.validator;

import org.marker.mushroom.beans.Chip;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



/**
 * 表单验证
 * 
 * */
public class ChipValidator implements Validator {

	
	public boolean supports(Class<?> clzz) { 
		boolean a = Chip.class.equals(clzz);
		return a;
	}

	
	public void validate(Object obj, Errors err) {
		System.out.println("dsadsads");
		ValidationUtils.rejectIfEmpty(err, "name", "name.empty");
 
		
		
		
	}

}
