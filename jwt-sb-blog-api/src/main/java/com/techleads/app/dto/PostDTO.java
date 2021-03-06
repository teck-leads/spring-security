
package com.techleads.app.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int post_id;
	private String title;

	private String body;
	private String data;
	private String created_by;
	
}
