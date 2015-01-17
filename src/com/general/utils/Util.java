package com.general.utils;

import java.awt.Container;
import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.meteocal.general.Marker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author orcl
 */
public class Util {

	public final static int BUTTON_WIDTH = 100;
	public final static int BUTTON_HEIGHT = 30;
	public final static String TYPE1 = "Type 1 Diabetic .";
	public final static String TYPE2 = "Type 2 Diabetic .";
	public final static String TYPE3 = "Gestational Diabetes.";
	public final static String day = "Day";

	public final static String beforeBreakfast = "beforeBreakfast";
	public final static String breakfast = "breakFast";
	public final static String lunch = "lunch";
	public final static String dinner = "dinner";
	public final static String beforeSleep = "beforeSleep";
	public final static String suddenDrop = "suddenDrop";
	public final static String[] times = { day, beforeBreakfast, breakfast,
			lunch, dinner, beforeSleep, suddenDrop };

	public static String MALE = "MALE";
	public static String FEMALE = "FEMALE";

	public static final String Saturda = "Saturday";
	public static final String Sunda = "Sunday";
	public static final String Monda = "Monday";
	public static final String Tuesda = "Tuesday";
	public static final String Wednesda = "Wednesday";
	public static final String Thursda = "Thursday";
	public static final String Frida = "Friday";

	public static final int Saturday = 0;
	public static final int Sunday = 1;
	public static final int Monday = 2;
	public static final int Tuesday = 3;
	public static final int Wednesday = 4;
	public static final int Thursday = 5;
	public static final int Friday = 6;

	public static final String[] weekDays = new String[] { "Saturday",
			"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
	public static String OVER_AVERAGE = "Over than Average";
	public static String NORMAL = "Normal";
	public static String DOWN_AVERAGE = "Lower Than Average";
	public static String NO_CHANGES_HAPPEND = "No Changes Happend";

	public static void initializeForm(JFrame frame) {
		setFrameIcon(frame);
		frame.setLocationRelativeTo(null);

	}

	public static boolean displayDialogMessage(Container c, String message) {

		int result = JOptionPane.showConfirmDialog(c, message);
		return result == JOptionPane.YES_OPTION;
	}

	public static void displayMessage(Container c, String message) {
		JOptionPane.showMessageDialog(c, message);
	}

	public static boolean isAString(String str) {
		return str != null && str.trim().length() != 0;
	}

	public static boolean isEmail(String email) {
		return isAString(email);
	}

	public static void hideAndShow(JFrame toHide, JFrame toShow) {

		toHide.setVisible(false);
		toShow.setVisible(true);
	}

	public static boolean areDatesEqualsEachOthers(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(date2);

			trimTimeFromCalendar(c1);
			trimTimeFromCalendar(c2);

			return c1.getTime().compareTo(c2.getTime()) == 0;
		}
		return false;

	}

	public static boolean areTimesEqualsEachOthers(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date1);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(date2);

			return c1.getTime().compareTo(c2.getTime()) == 0;
		}
		return false;

	}

	public static boolean areDatesEqualsEachOthers(XMLGregorianCalendar date1,
			Date date2) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, date1.getDay());
		c.set(Calendar.MONTH, date1.getMonth() - 1);
		c.set(Calendar.YEAR, date1.getYear());
		return areDatesEqualsEachOthers(c.getTime(), date2);
	}

	public static boolean areTimesEqualsEachOthers(XMLGregorianCalendar date1,
			Date date2) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 0);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.YEAR, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.HOUR_OF_DAY, date1.getHour());
		c.set(Calendar.MINUTE, date1.getMinute());
		c.set(Calendar.SECOND, 0);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		int h = c2.get(Calendar.HOUR_OF_DAY);
		int m = c2.get(Calendar.MINUTE);

		return (date1.getHour() == h) && (date1.getMinute()) == m;

	}

	public static void trimTimeFromCalendar(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}

	public static boolean isNotEmpty(List list) {
		return list != null && list.size() > 0;
	}

	public static JSpinner createDatePicker(Date date) {
		JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());

		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner,
				"hh:mm a");

		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(date);
		return timeSpinner;
	}

	public static JSpinner createDatePicker(XMLGregorianCalendar date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, date.getHour());
		calendar.set(Calendar.MINUTE, date.getMinute());
		return createDatePicker(calendar.getTime());

	}

	public static Date constructDateOfFormat(String dateAsText,
			SimpleDateFormat format) {

		try {
			Date date = format.parse(dateAsText);
			return date;
		} catch (ParseException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}

	}

	public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		try {
			XMLGregorianCalendar result = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
			return result;
		} catch (DatatypeConfigurationException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}

	}

	public static boolean isDouble(String text) {

		try {
			double x = Double.parseDouble(text);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	public static void restartThread(Thread th) {
		if (th != null) {
			try {
				th.stop();
				th = th.getClass().newInstance();
				th.start();
			} catch (InstantiationException ex) {
				Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null,
						ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null,
						ex);
			}
		}
	}

	public static String toTimeString(XMLGregorianCalendar xmlGregorianCalendar) {

		return String.format("%s : %s : %s", xmlGregorianCalendar.getHour(),
				xmlGregorianCalendar.getMinute(),
				xmlGregorianCalendar.getSecond());
	}

	public static String timeCurrentTimeAsString() {
		Calendar c = Calendar.getInstance();
		return String.format("%s : %s : %s", c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

	}

	public static boolean areTimesEqualsEachOthersBySecond(
			XMLGregorianCalendar date1, Date date2) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 0);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.YEAR, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.HOUR_OF_DAY, date1.getHour());
		c.set(Calendar.MINUTE, date1.getMinute());
		c.set(Calendar.SECOND, date1.getSecond());
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		int h = c2.get(Calendar.HOUR_OF_DAY);
		int m = c2.get(Calendar.MINUTE);
		int s = c2.get(Calendar.SECOND);

		return (date1.getHour() == h) && (date1.getMinute()) == m
				&& (date1.getSecond()) == s;
	}

	public static void removeFromMap(Map map, String key) {

		if (map != null && key != null) {
			map.remove(key);
		}
	}

	public static boolean isDateBetween(Date date, Date minDate, Date maxDate) {

		Date dateToCheck = date;
		minDate = trimTimeFromCalendar(minDate);
		maxDate = trimTimeFromCalendar(maxDate);
		dateToCheck = trimTimeFromCalendar(dateToCheck);
		boolean result = ((dateToCheck.after(minDate) || dateToCheck
				.compareTo(minDate) == 0) && (dateToCheck.before(maxDate) || dateToCheck
				.compareTo(maxDate) == 0));
		return result;

	}

	private static Date trimTimeFromCalendar(Date date1) {

		Calendar c = Calendar.getInstance();
		c.setTime(date1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();

	}

	public static int extractDayNumberFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public static int extractDayNumberFromDate(XMLGregorianCalendar date) {

		return extractDayNumberFromDate(date.toGregorianCalendar().getTime());
	}

	public static void replaceInMap(int key, Object value, Map map) {
		if (value != null && map != null) {
			map.remove(key);
			map.put(key, value);

		}

	}

	public static String getNotNullValue(String str) {
		return (str != null) ? str : "";
	}

	public static String getNotNullValue(Double value) {
		return (value != null) ? String.valueOf(value) : "";
	}

	public static String getFullDate(XMLGregorianCalendar measurementDate) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(measurementDate.toGregorianCalendar().getTime());

	}

	public static String refineWidth(String c) {

		if (c == null) {
			c = new String();
		}

		if (c.length() < 20) {
			int remaining = 20 - c.length();
			for (int i = 0; i < remaining; i++) {

				c += " ";
			}

		}

		return c;
	}

	public static String refineWidth(String c, int width) {

		if (c == null) {
			c = new String();
		}

		if (c.length() < width) {
			int remaining = 20 - c.length();
			for (int i = 0; i < remaining; i++) {

				c += " ";
			}

		}

		return c;
	}

	public static String getDayName(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("EEEE");
		return format.format(date);
	}

	public static void fireEmail(final String toEmail, final String body) {
		new Thread() {

			public void run() {
				try {

					final String emailusername = "apcdiabetic@gmail.com";
					final String emailpassword = "01277526990";

					Properties props = new Properties();

					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.starttls.enable", "true");
					props.put("mail.imap.ssl.enable", "true");
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.port", "587");
					/*
					 * props.put("mail.smtp.host", "smtp.gmail.com");
					 * props.put("mail.smtp.socketFactory.port", "465");
					 * props.put("mail.smtp.socketFactory.class",
					 * "javax.net.ssl.SSLSocketFactory");
					 * props.put("mail.smtp.auth", "true");
					 * props.put("mail.smtp.port", "465");
					 */

					Session session = Session.getInstance(props,
							new javax.mail.Authenticator() {
								@Override
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(
											emailusername, emailpassword);
								}
							});

					MimeMessage msg = new MimeMessage(session);
					// set message headers
					msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
					msg.addHeader("format", "flowed");
					msg.addHeader("Content-Transfer-Encoding", "8bit");

					msg.setFrom(new InternetAddress("apcdiabetic@gmail.com",
							"NoReply"));

					msg.setReplyTo(InternetAddress.parse(
							"apcdiabetic@gmail.com", false));

					msg.setSubject("APC Diabetic Measurements", "UTF-8");

					msg.setText(body, "UTF-8");

					msg.setSentDate(new Date());

					msg.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(toEmail, false));
					System.out.println("Message is ready");
					Transport.send(msg);

					displayMessage(null, "Mail has been successfuly sent");
				} catch (Exception e) {
					e.printStackTrace();
					displayMessage(null,
							"Sorry some error happend during sending mail please contact adminstrator");

				}
			}
		}.start();

	}

	private static void setFrameIcon(JFrame frame) {
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				frame.getClass().getClassLoader()
						.getResource("images/apcLogo.png")));
	}

	public static <K, V> Map<K, V> ListToMap(List list,
			String objectFieldToBeKey, String objectFieldToBeValue) {
		Map<K, V> result = new HashMap();
		if (Util.isNotEmpty(list)) {
			Class<? extends Object> listElementsClass = list.get(0).getClass();
			Field declaredFieldtoBeKey = null;
			Field declaredFieldtoBeValue = null;
			try {
				declaredFieldtoBeKey = listElementsClass
						.getDeclaredField(objectFieldToBeKey);
				declaredFieldtoBeValue = listElementsClass
						.getDeclaredField(objectFieldToBeValue);
				if (declaredFieldtoBeKey != null
						&& declaredFieldtoBeValue != null) {
					declaredFieldtoBeKey.setAccessible(true);
					declaredFieldtoBeValue.setAccessible(true);
					for (Object obj : list) {
						Object valueOfKeyField = declaredFieldtoBeKey.get(obj);
						Object valueOfFieldValue = declaredFieldtoBeValue
								.get(obj);
						if (valueOfKeyField != null
								&& valueOfFieldValue != null) {
							result.put((K) valueOfKeyField,
									(V) valueOfFieldValue);
						}
					}
				}

			} catch (Exception ex) {
				Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null,
						ex);
			} finally {
				return result;
			}
		}
		return result;

	}

	public static <K, V> V extractFromMap(K key, Map<K, V> map) {
		if (key != null && map != null) {

			return map.get(key);

		}
		return null;

	}

	public static boolean areStringsEqualEachOther(String str1, String str2) {
		if (str1 != null && str2 != null) {
			try {
				double firstNumber = Double.parseDouble(str1);
				double secondNumber = Double.parseDouble(str2);
				return firstNumber == secondNumber;
			} catch (Exception ex) {
				// Do nothing , in case of the two strings are numbers deal with
				// them as a number
				// if not go a head and continue;
			}
			return str1.trim().equalsIgnoreCase(str2.trim());

		}
		return false;
	}

	public static String camelCasingStylingToNormal(String camelStyledString) {
		StringBuilder stringBuilder = new StringBuilder();
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
					stringBuilder.insert(i, " ");
					stringBuilder.append(String.valueOf(currentChar)
							.toUpperCase());
				} else {
					stringBuilder.append(String.valueOf(currentChar)
							.toLowerCase());

				}
			}
		}
		return stringBuilder.toString();
	}

	public static void setDatePickerWithTime(JSpinner timeSpinner, Date date) {

		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner,
				"hh:mm a");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(date);
	}

	public static void setDatePickerWithTime(JSpinner timeSpinner,
			XMLGregorianCalendar date) {

		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner,
				"hh:mm a");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(date.toGregorianCalendar().getTime());
	}

	public static String toDateString(XMLGregorianCalendar date) {
		if (date != null) {
			return String
					.format("%s-%s-%s ", (date.getDay() >= 10) ? date.getDay()
							: "0" + date.getDay(), date.getMonth(), date
							.getYear());
		}
		return "";
	}

	public static String extractExtenstionFromFileName(String fileName) {

		if (isNotEmpty(fileName)) {
			int indexOfDot = fileName.lastIndexOf(".");
			if (indexOfDot != -1) {
				return fileName.substring(indexOfDot, fileName.length());
			}
		}
		return "";

	}

	public static boolean isNotEmpty(String string) {
		return string != null && string.trim().length() > 0;
	}

	public static String getLat(String str) {
		if (isNotEmpty(str)) {
			String latLon[] = str.split(",");
			return latLon[0];
		}
		return "";
	}

	public static String getLon(String str) {
		if (isNotEmpty(str)) {
			String latLon[] = str.split(",");
			return latLon[1];
		}
		return "";
	}

	public static <T extends Marker> T extractMarkerFromList(List<T> list,
			T marked) {
		for (Marker currentMarkedObject : list) {
			if (currentMarkedObject.getID() == marked.getID()) {
				return (T) currentMarkedObject;
			}
		}
		return null;
	}

	public static <T extends Marker> T extractMarkerFromList(List<T> list,
			Long ID) {
		for (Marker currentMarkedObject : list) {
			if (currentMarkedObject.getID() == ID) {
				return (T) currentMarkedObject;
			}
		}
		return null;
	}

	public static void removeFromList(List list, Object object) {

		if (isNotEmpty(list) && isNotEmpty(object)) {
			list.remove(object);
		}
	}

	public static void addIntoList(List list, Object object) {

		if (isNotEmpty(list) && isNotEmpty(object)) {
			list.add(object);
		}
	}

	public static boolean isNotEmpty(Object object) {
		return object != null;
	}

	public static List<Long> BigIntegerToLong(List<BigInteger> list) {

		List<Long> result = new ArrayList();
		for (BigInteger bigDecimal : list) {
			result.add(bigDecimal.longValue());
		}
		return result;
	}

	public static String encrypt(String convertme) {
	    final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    byte[] buf = md.digest(convertme.getBytes());
	    char[] chars = new char[2 * buf.length];
	    for (int i = 0; i < buf.length; ++i) {
	        chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
	        chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
	    }
	    return new String(chars);
	}

}
