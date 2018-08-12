package com.idnow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.idnow.dao.CompanyDaoIf;
import com.idnow.dao.IdentificationDaoIf;
import com.idnow.exception.CompanyNotFoundException;
import com.idnow.exception.DuplicateCompanyException;
import com.idnow.exception.DuplicateIdentificationException;
import com.idnow.model.Company;
import com.idnow.model.Identification;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/v1")
public class RestController {

	private final IdentificationDaoIf identificationDao;
	private final CompanyDaoIf companyDao;

	public RestController(IdentificationDaoIf identificationDao,CompanyDaoIf companyDao) {
		this.identificationDao = identificationDao;
		this.companyDao = companyDao;
	}

	@PostMapping(path= {"/startIdentification"})
	@ResponseStatus(HttpStatus.CREATED)
	public Identification startIdentification(@Valid @RequestBody Identification identification)
			throws DuplicateIdentificationException, CompanyNotFoundException {
		return identificationDao.createIdentification(identification);
	}

	@GetMapping(path= {"/pendingIdentifications"})
	@ResponseStatus(HttpStatus.OK)
	public List<Identification> pendingIdentifications() {
		return identificationDao.findAllSortedIdentifications();
	}
	
	@PostMapping(path= {"/createCompany"})
	@ResponseStatus(HttpStatus.CREATED)
	public Company createCompany(@Valid @RequestBody  Company company) throws DuplicateCompanyException {
		return companyDao.createCompany(company);
	}
	
	@GetMapping(path= {"/sortedCompanyList"})
	@ResponseStatus(HttpStatus.OK)
	public List<Company> findAllSortedCompanies(){
		return companyDao.findAllSortedCompanies();
	}

}
