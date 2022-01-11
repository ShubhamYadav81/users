package com.user;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.controller.UserController;
import com.user.dto.UserDto;
import com.user.service.UserService;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = { UserController.class })
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	UserService userServices;

	private UserDto userDto;

	@BeforeEach
	void setupAccountDto() {
		userDto = new UserDto();
		userDto.setUsername("shubham");
		userDto.setEmail("shubham@email.com");
		userDto.setName("shubham");

	}

	@Test
	void shouldReturnCreatedStatusWhenUserInsterted() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String masterAccountDtoInJsonForm = mapper.writeValueAsString(userDto);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON).content(masterAccountDtoInJsonForm);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	}

	@Test
	void shouldReturnOkStatusDtoWhenUserDtoIsPresent() throws Exception {

		when(userServices.getRecord("shubham")).thenReturn(new UserDto());
		mockMvc.perform(get("/users/shubham")).andExpect(status().isOk());

	}

	@Test
	void shouldReturnOkStatusWhenUsersDtoListIsPresent() throws Exception {

		when(userServices.getAllRecords()).thenReturn(new ArrayList<UserDto>());
		mockMvc.perform(get("/users")).andExpect(status().isOk());

	}

	@Test
	void shouldReturnNoContentStatusWhenUserDeleted() throws Exception {
		when(userServices.delete("shubham")).thenReturn(true);
		mockMvc.perform(delete("/users/shubham")).andExpect(status().isNoContent());
	}


	@Test
	void shouldReturnOKStatusWhenUserUpdated() throws Exception {

		when(userServices.updateDetails("shubham", userDto)).thenReturn(userDto);

		ObjectMapper mapper = new ObjectMapper();
		
		String userDtoInJsonForm = mapper.writeValueAsString(userDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/shubham")
				.contentType(MediaType.APPLICATION_JSON).content(userDtoInJsonForm);
		
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
}
