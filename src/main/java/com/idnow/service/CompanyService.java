package com.idnow.service;


import java.util.List;

import com.idnow.exception.CompanyNotFoundException;
import com.idnow.exception.DuplicateCompanyException;
import com.idnow.model.Company;


/**
 * 
 * @author JBhattacharjee
 * 
 * This represents the Service in here we will be putting all the 
 * details logic about the Company  , we will expose convenience 
 * methods for the upper layers to access it.
 * 
 */

public interface CompanyService {

	/**
	 * Creates  a Company if there is no other Company existing with same Id .
	 * @param company
	 * @return
	 * @throws DuplicateCompanyException
	 */
	Company createCompany(Company company) throws DuplicateCompanyException;

	/**
	 * Return a company with the id provided if that exist.
	 * @param id
	 * @return
	 * @throws CompanyNotFoundException
	 */
	Company getCompanyById(int id) throws CompanyNotFoundException;
	/**
	 * Returns a List of sorted company by the 
	 * following properties slaTime , slaPercentage
	 * @return List<Company>
	 */
	List<Company> findAllSortedCompanies();

	/**
	 * Removes all the Company stored in the Repository.
	 */
	void clearAllCompany();

}