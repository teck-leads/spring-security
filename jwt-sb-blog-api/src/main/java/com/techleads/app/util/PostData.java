package com.techleads.app.util;

import java.util.List;

import com.techleads.app.dto.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostData {
	private PostDTO data;
	private String username;
}
