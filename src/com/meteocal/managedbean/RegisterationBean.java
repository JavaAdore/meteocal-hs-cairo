package com.meteocal.managedbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.UploadedFile;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.facade.MemberFacade;
import com.meteocal.entity.Member;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@ManagedBean
@ViewScoped
public class RegisterationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	MemberFacade memberFacade;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmPass;
	private UploadedFile userImage;
	
	private String imageExtension;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public UploadedFile getUserImage() {
		return userImage;
	}

	public void setUserImage(UploadedFile userImage) {
		this.userImage = userImage;
	}

	public void register() {

		try {
			validatePasswords();
			String pictureURL = saveFileIfExist();
			Member member = memberFacade.register(firstName, lastName, email, password,
					confirmPass, pictureURL);
			WebUtils.fireInfoMessage(Constants.USER_ADDED_SUCCESSFULLY);
			WebUtils.injectIntoSession(Constants.CURRENT_LOGGED_USER, member);
			WebUtils.redirectTo(Constants.HOME_PAGE);

		} catch (MeteocalExceltion e) {

			WebUtils.fireErrorMessage(e.getMessageKey());
		}

	}

	private String saveFileIfExist() throws MeteocalExceltion {

		if (userImage != null && Util.isNotEmpty(userImage.getFileName())) {
			String pathToSaveImage = WebUtils.getWarPath();
			File imagesDirectorydirectory = new File(pathToSaveImage + File.separator
					+ Constants.STORE_FOLDER_NAME );
			if( !imagesDirectorydirectory.exists())
			{
				imagesDirectorydirectory.mkdirs();
			}
			imageExtension = Util
					.extractExtenstionFromFileName(userImage
							.getFileName());
			File file = new File(imagesDirectorydirectory.getPath()+"/" + email+imageExtension);
			
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

				try (InputStream is = userImage.getInputstream();
						OutputStream os = new FileOutputStream(file)) {

					int i = -1;
					byte bytes[] = new byte[1024];
					while ((i = is.read(bytes)) != -1) {
						os.write(bytes);
						os.flush();
					}

					;
					return email + imageExtension;

				} catch (IOException e) {

					throw Constants.UNABLE_TO_UPLOAD_IMAGE;
				}
			

		}
		return null;
	}

	private void validatePasswords() throws MeteocalExceltion {

		if (!password.equals(confirmPass)) {
			throw new MeteocalExceltion(
					Constants.REGISTRATION_PASSWORD_AND_CONFIRMATION_SHOULD_BE_MATCHED);
		}

	}
	
}
