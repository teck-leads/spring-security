package com.techleads.app.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

public class AppErrorController implements ErrorController {

	@GetMapping(value = "/error")
	public String handleErrors(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(String.valueOf(status));

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-401";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-500";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error-403";
			}
		}

		return "error";
	}

	@Override
	public String getErrorPath() {

		return "/error";
	}

}
