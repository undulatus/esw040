package com.pointwest.workforce.planner.constants;

import java.util.Arrays;
import java.util.List;

public interface ESWConstants {
	/*	Google Sheet API*/
		/**
	   * Be sure to specify the name of your application. If the application name is {@code null} or
	   * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
	   */
	  static final String APPLICATION_NAME = "e-Stimate Worksheet/0.4.0";

	  /** OAuth 2.0 scopes. */
	  static final List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds");

	  static final String SPREADSHEETS_FEED =
		      "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	  
	  /** Directory to store user credentials. */
	  static final java.io.File DATA_STORE_DIR =
	      new java.io.File(System.getProperty("admin.home"), ".store/oauth2_3_sample");

	  static final String CLIENT_SECRETS = "/client_secrets.json";
}
