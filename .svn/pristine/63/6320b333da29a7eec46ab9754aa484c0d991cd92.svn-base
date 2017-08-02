package com.pointwest.workforce.planner.security;

import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.pointwest.workforce.planner.constants.ESWConstants;
import com.pointwest.workforce.planner.service.impl.UploadDataServiceImpl;

@Component
public class GoogleServiceAuthorization {
	
	@Value("${gsheet.port}")
	public static String gsheetPort;
	
	private static FileDataStoreFactory dataStoreFactory;

	private static GoogleClientSecrets clientSecrets;
	
	public GoogleService init() throws Exception {
		Credential credential = authorize();
		 
		GoogleService googleService = new SpreadsheetService(ESWConstants.APPLICATION_NAME);
		googleService.setOAuth2Credentials(credential);

	    return googleService;
	}
	
	
	private static Credential authorize() throws Exception {
		System.out.println("port" + gsheetPort);
		    clientSecrets = GoogleClientSecrets.load(new JacksonFactory(),
		        new InputStreamReader(UploadDataServiceImpl.class.getResourceAsStream(ESWConstants.CLIENT_SECRETS)));

		    dataStoreFactory = new FileDataStoreFactory(ESWConstants.DATA_STORE_DIR);

		    GoogleAuthorizationCodeFlow flow =
		        new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), new JacksonFactory(), clientSecrets, ESWConstants.SCOPES)
		            .setDataStoreFactory(dataStoreFactory).build();

		    AuthorizationCodeInstalledApp app = new AuthorizationCodeInstalledApp(flow,
		        new LocalServerReceiver.Builder().setPort(8084).build());

		    return app.authorize("admin");
		  }
}
