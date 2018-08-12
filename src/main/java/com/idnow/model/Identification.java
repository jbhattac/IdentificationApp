/**
 * 
 */
package com.idnow.model;



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
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(of= {"id"})
@NoArgsConstructor
@Builder
public class Identification {
	@Min(value = 0, message = "Identification Id cannot be less than zero")
	int id;
	@NotNull(message = "Identification name cannot be null")
	String name;
	@Min(value = 0, message = "Identification time cannot be less than zero")
	int time;
	@Min(value = 0, message = "Identification waitingTime cannot be less than zero")
	int waitingTime;
	@Min(value = 0, message = "Identification companyId cannot be less than zero")
	int companyId;

	
	
	
	
}
