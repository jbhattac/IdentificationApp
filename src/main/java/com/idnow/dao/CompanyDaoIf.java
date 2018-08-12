package com.idnow.dao;


import java.util.Collection;
import java.util.List;

import com.idnow.exception.CompanyNotFoundException;
import com.idnow.exception.DuplicateCompanyException;
import com.idnow.model.Company;
import com.idnow.model.Identification;

/**
 * 
 * @author JBhattacharjee
 * 
 * This represents the Repository in here we will be putting all the 
 * details about how the object is stored , we will expose convenience 
 * methods for the upper layers to access this.
 * 
 */

public interface CompanyDaoIf {

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