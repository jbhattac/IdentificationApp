/**
 * 
 */
package com.idnow.model;



import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author JBhattacharjee
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(of= {"id","name"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
	@Min(value = 0, message = "Company Id cannot be less than zero")
	int id;
	@NotNull(message = "Company name cannot be null")
	String name;
	@Min(value = 0, message = "Company SlaTime cannot be less than zero")
	int slaTime;
	@Min(value = 0, message = "Company slaPercentage cannot be less than zero")
	@Max(value = 1, message = "Company slaPercentage cannot be more than one")
	float slaPercentage;
	@Min(value = 0, message = "Company currrentSlaPercentage cannot be less than zero")
	@Max(value = 1, message = "Company currrentSlaPercentage cannot be more than one")
	float currrentSlaPercentage;
	
	 /* public Company(int id, String name, int slaTime, float slaPercentage) {
		    this.id            = id;
		    this.name          = name;
		    this.slaTime       = slaTime;
		    this.slaPercentage = slaPercentage;
	  }*/


}
