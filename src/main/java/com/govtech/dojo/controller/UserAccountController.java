package com.govtech.dojo.controller;

import com.govtech.dojo.model.UserAccount;
import com.govtech.dojo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserAccountController {

    @Autowired
    private UserAccountService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        model.setViewName("user_account/login");
        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        UserAccount userAccount = new UserAccount();
        model.addObject("userAccount", userAccount);
        model.setViewName("user_account/signup");

        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createUser(@Valid UserAccount userAccount, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        UserAccount userAccountExists = userService.findUserByEmail(userAccount.getEmail());

        if (userAccountExists != null) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
            model.setViewName("user_account/signup");
        } else {
            userService.saveUser(userAccount);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("user", new UserAccount());
            model.setViewName("user_account/signup");
        }

        return model;
    }

	@RequestMapping(value = { "/signin" }, method = RequestMethod.POST)
	public ModelAndView loginPage(@Valid UserAccount userAccount, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		UserAccount userAccountExists = userService.findUserByEmail(userAccount.getEmail());

		if (userAccountExists == null) {
			model.addObject("msg", "This account doesn't exist!");
			model.setViewName("user_account/login");
		} else {
			model.addObject("userName", userAccountExists.getFirstname());
			model.setViewName("user_account/welcome");
		}

		return model;
	}

	@RequestMapping(value = { "/users/logout" }, method = RequestMethod.GET)
	public ModelAndView logout(ModelMap model) {
    	model.addAttribute("successMsg", "Successfully logged out");
		return new ModelAndView("redirect:/", model);
	}

    @RequestMapping(value = {"/users/welcome"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();
        model.setViewName("user_account/welcome");
        return model;
    }

    @RequestMapping(value = {"/access_denied"}, method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        return model;
    }
}