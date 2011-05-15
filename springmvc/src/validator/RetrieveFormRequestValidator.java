package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import domain.RetrieveFormRequest;

public class RetrieveFormRequestValidator {

	public void validate( RetrieveFormRequest request, Errors errors ) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "token", "error.field.required");
	}
}
