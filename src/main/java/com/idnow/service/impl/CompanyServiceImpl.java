package com.idnow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnegative;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.idnow.exception.CompanyNotFoundException;
import com.idnow.exception.DuplicateCompanyException;
import com.idnow.model.Company;
import com.idnow.service.CompanyService;

@Component
public class CompanyServiceImpl implements CompanyService {
	
	private  static Map<Integer,Company> companyStorage = new HashMap<>(); 
	private Comparator<Company> companyComparator
    = (company1,company2)-> {
    	// if the company slatime are equal then we find the rate = slapercent - currentslapercentage
    	if(company1.getSlaTime() == company2.getSlaTime()) {
    		float rate1 = company1.getSlaPercentage() - company1.getCurrrentSlaPercentage();
    		float rate2 = company2.getSlaPercentage() - company2.getCurrrentSlaPercentage();
    		return Float.compare(rate2,rate1);
    	}else {
    		// if the company sla time are not equal we create the comparison based on sla time
    		return Integer.compare(company1.getSlaTime(),company2.getSlaTime());
    	}
    };
	/* (non-Javadoc)
	 * @see com.idnow.dao.CompanyDaoIf#createCompany(com.idnow.model.Company)
	 */
	@Override
	public Company createCompany(@Validated Company company) throws DuplicateCompanyException {
		if(null == companyStorage.put(company.getId(),company)) {
			return companyStorage.get(company.getId());
		}else {
			throw new DuplicateCompanyException( " ID:  "+company.getId());
		}
	
	}
	
	/* (non-Javadoc)
	 * @see com.idnow.dao.CompanyDaoIf#getCompanyById(int)
	 */
	@Override
	public Company getCompanyById(@Nonnegative int id) throws CompanyNotFoundException {
		Company comp =  companyStorage.get(id);
		if(null != comp) {
			return comp;
		}else {
			throw new CompanyNotFoundException(" "+id);
			
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.idnow.dao.CompanyDaoIf#findAllSortedCompanies(int)
	 */
	public List<Company> findAllSortedCompanies(){
		List<Company> sortedCompList = new ArrayList<>(companyStorage.values());
		Collections.sort(sortedCompList, companyComparator);
		return sortedCompList;
		
	}

	@Override
	public void clearAllCompany() {
		companyStorage.clear();
	}

}
