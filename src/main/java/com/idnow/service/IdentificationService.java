package com.idnow.service;

import java.util.List;



import com.idnow.exception.CompanyNotFoundException;
import com.idnow.exception.DuplicateIdentificationException;
import com.idnow.exception.IdentificationNotFoundException;
import com.idnow.model.Identification;

/**
 * 
 * @author JBhattacharjee
 * 
 * This represents the Service in here we will be putting all the 
 * details logic about the Identification  , we will expose convenience 
 * methods for the upper layers to access it.
 * 
 */
public interface IdentificationService {

	/**
	 * This method will create an identification and save it provided the the
	 * associated company to which it belongs must already exist .
	 * 
	 * @param identification
	 * @return
	 * @throws DuplicateIdentificationException
	 * @throws CompanyNotFoundException
	 */
	Identification createIdentification(Identification identification)
			throws DuplicateIdentificationException, CompanyNotFoundException;

	/**
	 *  Return a Identification with the id provided if that exist.
	 * @param identificationId
	 * @return
	 * @throws IdentificationNotFoundException
	 */
	Identification findIdentificationById(int identificationId) throws IdentificationNotFoundException;

	/**
	 * 	Returns the set of all the Identification according to following sorted order
	 *  Associated companies are sorted according to the  slaTime , slaPercentage and 
	 *  then we sort by the waitingTime of the Identification.
	 * @return
	 */
	List<Identification> findAllSortedIdentifications();
	/**
	 * Removes all the identification stored in the Repository.
	 */
	void clearAllIdentification();
	

}