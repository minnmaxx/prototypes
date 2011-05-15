package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import validator.RetrieveFormRequestValidator;
import validator.UserValidator;
import domain.Company;
import domain.RetrieveFormRequest;
import domain.User;

/**
 * Controller that handles basic account operations: 
 * 1. Create Account
 *    a. Initiate Registration
 *    b. Retrieve Registration Form
 *    c. Complete Registration
 * 2. Reset Password
 *    a. Initiate Password Reset
 *    b. Retrieve Password Reset Form
 *    c. Complete Password Reset
 * 3. Retrieve a User Profile
 * 
 * @author Ariss Zhao
 *
 * task list:
 * 1. messsages as resources - done
 * 2. handle missing parameters such as token or email - done
 * 3. check the advantage of spring form
 * 4. generate token - done
 * 5. validate token - done
 * 6. create company domain list - 30%
 * 7. handle existing token? expire existing tokens? - no
 * 8. find out how to install a validator, consider apache common's validator.
 *    reference spring in action page 516
 */
@Controller
@RequestMapping("/account")
public class RegisterController {
	
	private static final RetrieveFormRequestValidator 
		RETRIEVE_FORM_REQUEST_VALIDATOR = new RetrieveFormRequestValidator();
	private static final UserValidator USER_VALIDATOR =
		new UserValidator();
	
	private Map<String,User> users = new HashMap<String,User>();
	
//	@InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.setValidator(new UserValidator());
//    }
	
	@RequestMapping(value="/home.do",method=RequestMethod.GET)
	public String getHome() {		
		return "account/home";
	}
		
	@RequestMapping(value="/register.do",method=RequestMethod.GET)
	public String getEmailForm( Model model ) {
		model.addAttribute( "action", "submitEmailToRegister.do" );
		return "account/emailForm";
	}

	@RequestMapping(value="/resetPassword.do",method=RequestMethod.GET)
	public String getResetPasswordForm( Model model ) {
		model.addAttribute( "action", "submitEmailToResetPassword.do" );
		return "account/emailForm";
	}

	
	@RequestMapping(value="/submitEmailToRegister.do",method=RequestMethod.POST,params="email")
	public String handleEmailForRegistration( @RequestParam("email") String email,
			HttpServletRequest servletRequest,
			Model model ) {
		
				
		if( users.containsKey( email ) ) {
			model.addAttribute( "key", "error.existing.user" );
		} else if( isEmailValid( email ) ) {

			String serverHost = servletRequest.getServerName();
			int serverPort = servletRequest.getServerPort();
			String scheme = servletRequest.getScheme();
			String servletContext = servletRequest.getContextPath();
						
			String link = scheme + "://" + serverHost;
			if( serverPort != 80 ) {
				link += ":" + serverPort;
			}
			link += servletContext;
			link += "/account/getRegistrationForm.do";
			link += "?email=" + email;
			link += "&token=123";

			model.addAttribute( "link", link );
			model.addAttribute( "sender", "initiation" );
			model.addAttribute( "key", "initiation.complete" );
		} else {
			model.addAttribute( "sender", "initiation" );
			model.addAttribute( "key", "error.invalid.email.domain" );
		}
		
		return "displayStatus";
		
	}
	
	@RequestMapping(value="/submitEmailToResetPassword.do",method=RequestMethod.POST,
					params={"email"})
	public String handleEmailForPasswordReset( @RequestParam("email") String email,
			Model model ) {
		
		User user = users.get( email );
		if( user == null ) {
			model.addAttribute( "key", "error.non.existing.user" );
		} else {
			String link = "http://localhost:8080/springmvc/account/getPasswordResetForm.do";
			link += "?email=" + email;
			link += "&token=123";
			model.addAttribute( "link", link );
			model.addAttribute( "key", "initiation.complete" );
			model.addAttribute( "sender", "initiation" );
		}
		
		return "displayStatus";
	}
	
	@RequestMapping(value="/getPasswordResetForm.do",method=RequestMethod.GET,
					params={"email","token"})
	public String getPasswordResetForm( @RequestParam("email") String email,
									   @RequestParam("token") String token, 
									   Model model ) {

		System.out.println( email );
		System.out.println( token );		

		User user = users.get( email );
		if( user == null ) {
			
			model.addAttribute( "key", "error.non.existing.user" );
			return "displayStatus";			
			
		} else if( isRequestValid( email, token) ) {
			
			model.addAttribute( "user", user );
			model.addAttribute( "token", token );

			return "account/resetPasswordForm";
		} else {
			
			model.addAttribute( "key", "error.invalid.token" );
			return "displayStatus";
		}
	}
	
	
//	@RequestMapping(value="/getRegistrationForm.do",method=RequestMethod.GET,
//					params={"email","token"})

//	public String getRegistrationForm( 
//			@RequestParam("email") String email,
//			@RequestParam("token") String token, 
//			Model model ) {
	
	@RequestMapping(value="/getRegistrationForm.do",method=RequestMethod.GET)
	public String getRegistrationForm( 
			@ModelAttribute( "retrieveFormRequest" ) RetrieveFormRequest retrieveFormRequest,
			BindingResult result,
			Model model ) {
		
		RETRIEVE_FORM_REQUEST_VALIDATOR.validate(retrieveFormRequest,result);
		
		if( result.hasErrors() ) {
			model.addAttribute( "key", "error.form.retrieval.request" );
			return "displayStatus";
		}

		String email = retrieveFormRequest.getEmail();
		String token = retrieveFormRequest.getToken();
		
		// check for existing user
		if( isRequestValid( email, token) ) {
			
			Company company = new Company();
			company.setName( "Test Company" );
			
			User user = new User();
			user.setCompany(company);
			user.setEmail(email);
			user.setToken(token);
		
			model.addAttribute( "user", user );

			return "account/registrationForm";
		} else {
			
			model.addAttribute( "key", "error.invalid.token" );
			return "displayStatus";
		}
	}
	
	@RequestMapping(value="/submitRegistration.do",method=RequestMethod.POST)
	public String handleRegistration( 
			@ModelAttribute("user") User user,
			BindingResult result,
			Model model ) {
		
		USER_VALIDATOR.validate(user,result);
		
		// check for existing user
		if( result.hasErrors() ) {
			return "account/registrationForm";
		} else if( !isRequestValid( user.getEmail(), user.getToken() ) ) {
			model.addAttribute( "key", "error.invalid.token" );
		} else if( !isUserInformationValid( user ) ) {
			model.addAttribute( "message", "invalid user information" );		
		} else {
			users.put( user.getEmail(), user );
			model.addAttribute( "key", "registration.complete" );
		}
		
		return "displayStatus";
	}
	
	@RequestMapping(value="/submitPasswordReset.do",method=RequestMethod.POST)
	public String handlePasswordReset( 
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("token") String token,
			Model model ) {
		
		User user = users.get( email );
		
		if( user == null || !isRequestValid( email, token ) ) {
			model.addAttribute( "key", "error.invalid.token" );
		} else {
			user.setPassword(password);
			model.addAttribute( "key", "password.reset.complete" );
		}
		
		return "displayStatus";
	}	
	
	@RequestMapping(value="/profile.do")
	public String getUserProfile( @RequestParam("email") String email, Model model ) {
		
		User user = users.get(email);
		if( user != null ) {
			model.addAttribute( "user", user );
			return "account/profile";
		} else {
			model.addAttribute( "message", "user not found" );
			return "displayStatus";
		}
	}
	
	
	private boolean isEmailValid( String email ) {
		return email.endsWith(".com");
	}
	
	private boolean isRequestValid( String email, String token ) {
		return isEmailValid(email) && (token.length() == 3);
	}
	
	private boolean isUserInformationValid( User user ) {
		return user.getFirstName() != null &&
			user.getLastName() != null &&
			user.getEmail() != null &&
			user.getPassword() != null;
	}
}
