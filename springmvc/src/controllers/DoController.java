package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DoController {

	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public String getLogin() {
		return "login";
	}

	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public String handleLogin() {
		// this happens in spring security.
		return "redirect:/controlPanel.do";
	}
	
	@RequestMapping(value="/controlPanel.do")
	public String getControlPanel() {
		return "controlPanel";
	}
	
	@RequestMapping(value="/apps/viewer.do")
	public String getViewer() {
		return "apps/viewer";
	}

	@RequestMapping(value="/apps/admin.do")
	public String getAdmin() {
		return "apps/admin";
	}	
	
	@RequestMapping(value="/logout.do")
	public String handleLogout() {
		// this happens in spring security
		return "redirect:/login.do";
	}	
	
	@RequestMapping(value="/apps/exploreImplicitVariables.do")
	public String handleExploreImplicitVariables() {
		// this happens in spring security
		return "apps/exploreImplicitVariables";
	}	
}
