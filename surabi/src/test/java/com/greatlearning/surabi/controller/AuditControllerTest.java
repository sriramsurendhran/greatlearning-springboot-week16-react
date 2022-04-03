package com.greatlearning.surabi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.greatlearning.surabi.entity.AuditLog;
import com.greatlearning.surabi.repository.AuditRepository;

@WebMvcTest(AuditController.class)
@AutoConfigureTestDatabase
public class AuditControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AuditRepository auditRepository;

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldGetAuditLogs() throws Exception {
		List<AuditLog> auditLogList = new ArrayList<>();
		auditLogList.add(new AuditLog(1, new Date(), "User Deleted  somu"));

		Mockito.when(auditRepository.findAll()).thenReturn(auditLogList);

		MvcResult responseObject = mockMvc.perform(get("/audit/logs")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].description", Matchers.equalTo("User Deleted  somu"))).andReturn();

	}
}
