package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import domain.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.field.required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.field.required");
		User user = (User) target;
		if( !user.getPassword().equals( user.getConfirmPassword() ) ) {
			errors.rejectValue("confirmPassword", "error.unmatched.passwords");
		}
		
	}

	
}
