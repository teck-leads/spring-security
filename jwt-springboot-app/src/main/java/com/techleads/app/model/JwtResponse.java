package com.techleads.app.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String jwttoken;

}
