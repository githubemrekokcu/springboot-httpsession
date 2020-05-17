package com.kokcuemre.springsession.springhttpsession.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/home")
public class HomeController {

	private final HttpServletRequest req;

	@Autowired
	public HomeController(HttpServletRequest req) {
		this.req = req;
	}

	@GetMapping("/create-session")
	private ResponseEntity<Map<String, Object>> createSession() { // create Session
		final Map<String, Object> result = new HashMap<String, Object>();
		if (req.getSession().isNew()) {
			result.put("Message", "Created New Session");

		} else {
			result.put("Message", "Active Session");
		}
		result.put("Session Key", req.getSession().getId());
		result.put("Session Key Encode",  Base64.getEncoder().encodeToString(req.getSession().getId().getBytes()));
		return ResponseEntity.ok(result);
	}

	@GetMapping("/invalidate-session")
	private ResponseEntity<Map<String, Object>> invalidateSession() { // invalidate Session
		final Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = req.getSession(false);
		if (session != null) {
			req.getSession().invalidate();
			result.put("Message", "Session Invalidated");
		}
		return ResponseEntity.ok(result);
	}
}
