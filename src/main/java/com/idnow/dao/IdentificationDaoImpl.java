package com.idnow.dao;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.idnow.exception.CompanyNotFoundException;
import com.idnow.exception.DuplicateIdentificationException;
import com.idnow.exception.IdentificationNotFoundException;
import com.idnow.model.Company;
import com.idnow.model.Identification;

@Component
public class IdentificationDaoImpl implements IdentificationDaoIf {

	private final CompanyDaoIf companyDao;

	public IdentificationDaoImpl(CompanyDaoIf companyDao) {
		this.companyDao = companyDao;
	}

	private static Map<Integer, Identification> identificationStorage = new HashMap<>();

	/* (non-Javadoc)
	 * @see com.idnow.dao.IdentificationDaoIf#createIdentification(com.idnow.model.Identification)
	 */
	@Override
	public Identification createIdentification(@Validated Identification identification)
			throws DuplicateIdentificationException, CompanyNotFoundException {
		// the company should already exist for the Identification to be persisted.
		companyDao.getCompanyById(identification.getCompanyId());
		if (null == identificationStorage.put(identification.getId(), identification)) {
			return identificationStorage.get(identification.getId());
		} else {
			throw new DuplicateIdentificationException(
					 " ID:  " + identification.getId());
		}

	}

	/* (non-Javadoc)
	 * @see com.idnow.dao.IdentificationDaoIf#findIdentificationById(int)
	 */
	@Override
	public Identification findIdentificationById(int identificationId) throws IdentificationNotFoundException {
		Identification identification = identificationStorage.get(identificationId);
		if (null != identification) {
			return identification;
		} else {
			throw new IdentificationNotFoundException(" " + identificationId);

		}
	}

	/* (non-Javadoc)
	 * @see com.idnow.dao.IdentificationDaoIf#findAllSortedIdentifications()
	 */
	@Override
	public List<Identification> findAllSortedIdentifications() {
							return			 companyDao.findAllSortedCompanies()
															.stream()
															.map(Company::getId)
															.map(id->findAllSortedIdentificationAssociatedWithCompanyId(id))
															.flatMap(List::stream)
															.collect(Collectors.toList());
	
	}
	

	
	/**
	 * Return a list of companies associated with a company id.
	 * @param companyId
	 * @return
	 */
	 private  List<Identification> findAllSortedIdentificationAssociatedWithCompanyId(int companyId) {
		    						return	identificationStorage.values()
		    														.stream()
		    														.filter(id->(id.getCompanyId() == companyId))
		    														.sorted(Comparator.comparing(Identification::getWaitingTime).reversed())
		    														.collect(Collectors.toList());
	 }

	
	public void clearAllIdentification() {
		identificationStorage.clear();
		
	}

}
