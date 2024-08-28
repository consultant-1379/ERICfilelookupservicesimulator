package com.ericsson.oss.services.fls.endpoint.rest;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.FAILURE_CODE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.FAILURE_MESSAGE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.HAL_JSON;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.LOGIN_CONTEXT_PATH;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.SECURITY_DOMAIN_NAME;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.SUCCESS_CODE;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.security.AuthenticationManager;
import org.jboss.security.SimplePrincipal;
import org.picketbox.factories.SecurityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.endpoint.api.ResponseError;
//import javax.ws.rs.core.Response.ResponseBuilder;
//import javax.ws.rs.core.Response.Status;
@Path(LOGIN_CONTEXT_PATH)
public class LoginPage {
	private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
	
	@POST
	@Produces({ HAL_JSON })
	public Response doLogin(@QueryParam("IDToken1") final String userName,
			@QueryParam("IDToken2") final String password) throws Exception {

		boolean isValid = isValidCredentials(userName, password);

		if (isValid) {
			//If authentication is successful then send status code 302 and dummy cookie entries
			
			return Response
					.status(SUCCESS_CODE)
					.cookie(new NewCookie("AMAuthCookie", "LOGOUT"),
							new NewCookie("amlbcookie", "01"),
							new NewCookie("iPlanetDirectoryPro","S1~AQIC5wM2LY4SfczW300mq8ytNoPX3wybmhUDqqp7eraBrMU.*AAJTSQACMDIAAlNLABM3MDY2OTcwNjU4NjM1MjU4NDMwAAJTMQACMDE.*"),
							new NewCookie("ssocookie", "ssocookie-2")).build();
		}else{
			return Response
				.status(FAILURE_CODE)
				.entity(new ResponseError(Status.OK,FAILURE_MESSAGE)).build();
		}
	}
	
	/**
	 * @param userName
	 * @param password
	 * @return true if username and password are successfully validated from JBOSS application_users.properties else it will return false. 
	 */
	private boolean isValidCredentials(String userName, String password) {
		boolean isValid=false;
		try {
			
			AuthenticationManager authenticationManager = SecurityFactory.getAuthenticationManager(SECURITY_DOMAIN_NAME);
			if (null!=authenticationManager){
				isValid = authenticationManager.isValid(new SimplePrincipal(userName), password);
			}else{
			
				logger.error("Authentication Manager is null");
			}
			
		} 
		catch(Exception exception) 
		{
			logger.error(exception.toString());
		}
		finally {
			SecurityFactory.release();
		}
		return isValid;
	}
}
