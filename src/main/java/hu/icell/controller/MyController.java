package hu.icell.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hu.icell.dao.AuthDao;
import hu.icell.entities.User;

@Controller
public class MyController {
    
    @Inject
    private AuthDao authDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView indexAction(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
		    return new ModelAndView("redirect:/login");
		}
		ModelAndView model = new ModelAndView("index");
	
		return model;

	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView loginAction(HttpServletRequest request, @RequestParam(name="username", required=false) String username,
			@RequestParam(name="password", required=false) String password) {
	    String msg = "";
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			User user = authDao.getUserByUsernameAndPassword(username, password);
			if (user == null) {
			    msg = "Hibás felhasználónév vagy jelszó!";
			} else {
			    HttpSession session = request.getSession(true);
			    session.setAttribute("user", user);
			    return new ModelAndView("redirect:/");
			}
		}
		ModelAndView model = new ModelAndView("login");
		model.addObject("msg", msg);
		
		return model;

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutAction(HttpServletRequest request) {
        
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.setAttribute("user", null);
	        session = null;
	    }
	    
	    return new ModelAndView("redirect:/login");

    }
	
	@RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView registerAction(@RequestParam(name="username", required=false) String username,
            @RequestParam(name="password", required=false) String password, @RequestParam(name="password2", required=false) String password2) {
	    
	    String msg = "";
	    if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(password2)) {
	        if (authDao.getUserByUsername(username) != null) {
                msg = "Ez a felhasználónév már létezik!";
            }
	        if (!password.equals(password2)) {
	            msg = "A jelszó és megerősítése nem egyezik!";
	        }
	        if (msg.isEmpty()) {
	            authDao.saveUser(username, password);
	            return new ModelAndView("redirect:/login");
	        }
	    }
		ModelAndView model = new ModelAndView("register");
		model.addObject("msg", msg);
		
		return model;

	}

}