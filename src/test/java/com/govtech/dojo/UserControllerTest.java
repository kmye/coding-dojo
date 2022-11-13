package com.govtech.dojo;

import com.govtech.dojo.model.UserAccount;
import com.govtech.dojo.service.UserAccountService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes=DojoApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserAccountService userService;


	private List<UserAccount> userAccountList;

	@Before("")
	public void setUp() { }
	
	@Test
	public void testHome() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/html;charset=UTF-8"))
		.andExpect(view().name("user_account/login"))
		.andExpect(content().string(containsString("Welcome to the Login Form Demo!")));
	}
	
	@Test
	public void testHomeSignup() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/html;charset=UTF-8"))
		.andExpect(view().name("user_account/signup"))
		.andExpect(content().string(containsString("Signup for an account")));}

	@Test
	public void testCreateAccount() throws Exception {
		UserAccount userAccount = new UserAccount();
		mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf()).content(
				"{\"email\":\"demo@example.com\",\"firstName\":\"xxx\",\"lastName\":\"xxx\",\"password\":\"xxx\"}"))
				.andDo(print()).andExpect(status().isCreated()).andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("Signup for an account")));
				//.andExpect(content().source());
	}

	@Test
	public void testWelcomePage() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/users/welcome"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/html;charset=UTF-8"))
		.andExpect(view().name("user_account/welcome"))
		.andExpect(content().string(containsString("welcome to coding dojo")));
	}

	@Test
	public void testLoginPage() throws Exception {
		UserAccount userAccount = new UserAccount();
		userAccount.setEmail("email@example.com");
		userAccount.setPassword("xxx");
		userService.saveUser(userAccount);

		mockMvc.perform(post("/signin").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(
				"email=email@example.com&password=xxx"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("welcome to coding dojo")));
	}

	@Test
	public void testLogout() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/users/logout"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/?successMsg=Successfully+logged+out"));
	}
}