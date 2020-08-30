
package com.techleads.app.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDTO implements Serializable{
    
 
	private static final long serialVersionUID = 1L;

	String title;
  
    String body;
    
    Integer post_id;

    
}
