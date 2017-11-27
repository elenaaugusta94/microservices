package com.elena.application.MsSaleSpring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Logic {
//	public String executa(HttpServletRequest req) throws Exception;
	public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
