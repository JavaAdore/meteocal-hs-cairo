package com.general.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.meteocal.entity.Member;
import com.meteocal.general.Constants;

public class WebUtils {

	public static final String WELCOME = "Welcome";

	public static Map<String, String> objectToMap(Object object) {
		Map<String, String> map = new TreeMap();
		if (object != null) {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					Object obj = field.get(object);
					if (obj != null) {
						map.put(camelCasingStylingToNormal(field.getName()),
								String.valueOf(obj));

					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return map;
	}

	public static String camelCasingStylingToNormal(String camelStyledString) {
		StringBuilder stringBuilder = new StringBuilder();
		int counter = 0;
		if (camelStyledString != null) {
			char[] chars = camelStyledString.toCharArray();
			for (int i = 0; i < camelStyledString.toCharArray().length; i++) {
				char currentChar = chars[i];
				if (i == 0) {
					stringBuilder.append(String.valueOf(currentChar)
							.toUpperCase());
					continue;
				}
				if (((int) currentChar) >= 65 && ((int) currentChar) <= 90) {
					stringBuilder.append(" ");
					stringBuilder.append(String.valueOf(currentChar)
							.toUpperCase());
					counter++;
				} else {
					stringBuilder.append(String.valueOf(currentChar)
							.toLowerCase());

				}
			}
		}
		return stringBuilder.toString();
	}

	public static void fireErrorMessage(String key) {
		String messageToDisplay = extractFromBundle(key);
		fireMessage(FacesMessage.SEVERITY_ERROR, messageToDisplay);
	}

	public static void fireInfoMessage(String key) {
		String messageToDisplay = extractFromBundle(key);
		fireMessage(FacesMessage.SEVERITY_INFO, messageToDisplay);

	}

	public static void fireMessage(FacesMessage.Severity severity,
			String messageToDisplay) {
		FacesContext
				.getCurrentInstance()
				.getCurrentInstance()
				.addMessage("",
						new FacesMessage(severity, messageToDisplay, ""));

	}

	public static String extractFromBundle(String key) {
		ResourceBundle bundle = FacesContext.getCurrentInstance()
				.getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "loc");
		try {
			return bundle.getString(key);
		} catch (Exception ex) {
			return key;
		}
	}

	public static void redirectTo(String homePage) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(homePage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String perpareWelcomeMessage(String firstName) {

		String welcome = extractFromBundle(WELCOME);
		return String.format("%s ,", welcome);
	}

	public static String getWarPath() {
		String targetedLocation = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/");
		return targetedLocation;

	}

	public static Member getCurrentUser() {
		return (Member) extractFromSession(Constants.CURRENT_LOGGED_USER);
	}

	public static void invokeJavaScriptFunction(String functionWithParameter) {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute(functionWithParameter);
	}

	public static void fireDetailsIntoMessage(String key, String details) {
		String messageToDisplay = extractFromBundle(key);
		fireDetailedMessage(FacesMessage.SEVERITY_INFO, messageToDisplay,
				details);

	}

	private static void fireDetailedMessage(Severity severity, String title,
			String details) {
		FacesContext.getCurrentInstance().getCurrentInstance()
				.addMessage("", new FacesMessage(severity, title, details));

	}

	public static void invalidateSession() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.invalidate();

	}

	public static String getRealPath(String str) {
		String url = null;
		try {
			ExternalContext ext = FacesContext.getCurrentInstance()
				    .getExternalContext();
				String path = ext.getRequestContextPath();
				path += path.endsWith("/") ? str : "/"+str;
				 url = ext.encodeResourceURL(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return url;
	}

	public static boolean isFileExistInWar(String filePath) {
		String realFilePath = getRealPath( filePath);
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
			    .getResourceAsStream("/"+filePath);

		return stream !=null;
	}

	public static void injectIntoSession(String key, Object objectToInject) {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put(key, objectToInject);
	}

	public static Object extractFromSession(String key) {
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Object objectToReturn = sessionMap.get(key);
		return objectToReturn;

	}

	public static void validateCurrentUser() {
		if (getCurrentUser() == null) {

			redirectTo(Constants.LOGIN_PAGE);

		}
		;
	}

	public static void fireExactInfoMessage(String message) {

		FacesContext
				.getCurrentInstance()
				.getCurrentInstance()
				.addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								message));

	}

	public static void fireExactInfoMessage(String name, String details) {
		FacesContext
				.getCurrentInstance()
				.getCurrentInstance()
				.addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO, name,
								details));

	}

	public static Object extractFromRequest(String key) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Object attributeValue = request.getAttribute(key);
		return attributeValue;
	}

	public static void injectIntoRequest(String key, Object value) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		request.setAttribute(key, value);

	}
	
	
	public static void fireInfoMessageAndKeep(String key, boolean keep)
	{
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(keep);
		context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,extractFromBundle(key),""));
	}

	public static void setKeepMessage(boolean keep) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(keep);
		
	}
	
	
	public static void hideFacesMessage(final int sleep)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					sleep(sleep);
					
					setKeepMessage(false);
				}catch(Exception ex)
				{
					// do Absolutely Nothing 
				}
			}
		}.start();
		
	}

	public static void invokeJavaScriptFunctionLater(final String key, final int sleep) {
		new Thread()
		{
			public void run()
			{
				try
				{
					sleep(sleep);
					
					fireErrorMessage(key);
					
					setKeepMessage(false);
				}catch(Exception ex)
				{
					// do Absolutely Nothing 
				}
			}
		}.start();
		
	}
}
