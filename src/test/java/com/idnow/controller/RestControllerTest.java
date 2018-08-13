package com.idnow.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.idnow.dao.CompanyDaoIf;
import com.idnow.dao.IdentificationDaoIf;
import com.idnow.model.Company;
import com.idnow.model.Identification;
import com.idnow.service.CompanyService;
import com.idnow.service.IdentificationService;

@RunWith(SpringRunner.class)
@SpringBootTest(value = "IdNowApp.class")
@AutoConfigureMockMvc
public class RestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	IdentificationService identificationService;

	@Autowired
	CompanyService companyService;

	/**
	 * Example 1:
	 * 
	 * One company with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
	 * Identification 1: Waiting_time=30 Identification 2: Waiting_time=45 Expected
	 * order: Identification 2, Identification 1 (since Ident 2 has waited longer)
	 * 
	 * 
	 * @throws Exception
	 * 
	 * 
	 * 
	 */
	@Test
	public void testcase1_sameCompany_differntWaitingTime() throws Exception {
		Company comp1 = Company.builder()
								.id(1)
								.name("Company-1")
								.slaPercentage(0.9f)
								.slaTime(60)
								.currrentSlaPercentage(0.95f)
								.build();
		Identification id1 = Identification.builder()
											.id(1)
											.name("Identification-1")
											.time(1521315317)
											.waitingTime(30)
											.companyId(1)
											.build();
		Identification id2 = Identification.builder()
											.id(2)
											.name("Identification-2")
											.time(141314357)
											.waitingTime(45)
											.companyId(1)
											.build();

		companyService.createCompany(comp1);
		identificationService.createIdentification(id1);
		identificationService.createIdentification(id2);

		mvc.perform(get("/api/v1/pendingIdentifications")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(id2.getName())))
				.andExpect(jsonPath("$[1].name", is(id1.getName())));
		clearData();

	}

	/**
	 * Example 2:
	 * 
	 * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
	 * Company 2 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.90
	 * Identification 1 belonging to Company1: Waiting_time=30 Identification 2
	 * belonging to Company2: Waiting_time=30 Expected order: Identification 2,
	 * Identification 1 (since Company 2 already has a lower current SLA percentage
	 * in this month, so its identifications have higher prio)
	 * 
	 * @throws Exception
	 * 
	 */

	@Test
	public void testcase2_differntCurrentSlaPercentage() throws Exception {
		Company comp1 = Company.builder()
								.id(1)
								.name("Company-1")
								.slaPercentage(0.9f)
								.slaTime(60)
								.currrentSlaPercentage(0.95f)
								.build();
		Company comp2 = Company.builder()
								.id(2)
								.name("Company-2")
								.slaPercentage(0.9f)
								.slaTime(60)
								.currrentSlaPercentage(0.90f)
								.build();
		Identification id1 = Identification.builder()
											.id(1)
											.name("Identification-1")
											.time(1521315317)
											.waitingTime(30)
											.companyId(1)
											.build();
		Identification id2 = Identification.builder()
											.id(2)
											.name("Identification-2")
											.time(141314357)
											.waitingTime(30)
											.companyId(2)
											.build();

		companyService.createCompany(comp1);
		companyService.createCompany(comp2);
		identificationService.createIdentification(id1);
		identificationService.createIdentification(id2);

		mvc.perform(get("/api/v1/pendingIdentifications")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(id2.getName())))
				.andExpect(jsonPath("$[1].name", is(id1.getName())));

		clearData();

	}

	private void clearData() {
		companyService.clearAllCompany();
		identificationService.clearAllIdentification();
	}

	/**
	 * Example 3:
	 * 
	 * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
	 * Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.95
	 * Identification 1 belonging to Company1: Waiting_time=30 Identification 2
	 * belonging to Company2: Waiting_time=30 Expected order: Identification 1,
	 * Identification 2 (since company 1 has a lower, more urgent SLA)
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testcase3_differntSlaProcessingRate() throws Exception {
		Company comp1 = Company.builder()
								.id(1)
								.name("Company-1")
								.slaPercentage(0.9f)
								.slaTime(60)
								.currrentSlaPercentage(0.95f)
								.build();
		Company comp2 = Company.builder()
								.id(2)
								.name("Company-2")
								.slaPercentage(0.8f)
								.slaTime(120)
								.currrentSlaPercentage(0.95f)
								.build();
		Identification id1 = Identification.builder()
											.id(1)
											.name("Identification-1")
											.time(1521315317)
											.waitingTime(30)
											.companyId(1)
											.build();
		Identification id2 = Identification.builder()
											.id(2)
											.name("Identification-2")
											.time(141314357)
											.waitingTime(30)
											.companyId(2)
											.build();

		companyService.createCompany(comp1);
		companyService.createCompany(comp2);
		identificationService.createIdentification(id1);
		identificationService.createIdentification(id2);

		mvc.perform(get("/api/v1/pendingIdentifications")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(id1.getName())))
				.andExpect(jsonPath("$[1].name", is(id2.getName())));

		clearData();

	}

	/**
	 * 
	 * Example 4:
	 * 
	 * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
	 * Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.80
	 * Identification 1 belonging to Company1: Waiting_time=45 Identification 2
	 * belonging to Company2: Waiting_time=30 What is the expected order here?
	 * 
	 * Identification1 and then Identification2.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testcase4_differntSlaProcessingRate_waitingtime() throws Exception {
		Company comp1 = Company.builder()
								.id(1)
								.name("Company-1")
								.slaPercentage(0.9f)
								.slaTime(60)
								.currrentSlaPercentage(0.95f)
								.build();
		Company comp2 = Company.builder()
								.id(2)
								.name("Company-2")
								.slaPercentage(0.8f)
								.slaTime(120)
								.currrentSlaPercentage(0.8f)
								.build();
		Identification id1 = Identification.builder()
											.id(1)
											.name("Identification-1")
											.time(1521315317)
											.waitingTime(45)
											.companyId(1)
											.build();
		Identification id2 = Identification.builder()
											.id(2)
											.name("Identification-2")
											.time(141314357)
											.waitingTime(30)
											.companyId(2)
											.build();

		companyService.createCompany(comp1);
		companyService.createCompany(comp2);
		identificationService.createIdentification(id1);
		identificationService.createIdentification(id2);

		mvc.perform(get("/api/v1/pendingIdentifications")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(id1.getName())))
				.andExpect(jsonPath("$[1].name", is(id2.getName())));

		clearData();

	}
}
