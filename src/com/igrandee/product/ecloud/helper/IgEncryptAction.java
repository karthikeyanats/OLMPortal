package com.igrandee.product.ecloud.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FilenameUtils;

public class IgEncryptAction {


	public String encryptVideo(String folderlocation,String filename,InputStream uploadedInputStream) throws IOException{
		try{

			ResourceBundle resourceBundle = ResourceBundle.getBundle("serversetup");
			String serverPath = resourceBundle.getString("serverpath");
			final int IV_LENGTH = 16; // Default length with Default 128
			// key AES encryption
			int DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE = 1024;

			final String ALGO_RANDOM_NUM_GENERATOR = "SHA1PRNG";
			final String ALGO_VIDEO_ENCRYPTOR = "AES/CBC/PKCS5Padding";

			folderlocation=folderlocation.replaceAll(" ","");
			String fullpath=serverPath+folderlocation;
			File f1 = new File(fullpath);
			if (!f1.exists()) {
				f1.mkdirs();
			}
			String changedfilename=FilenameUtils.removeExtension(filename) + ".igst";
			changedfilename=changedfilename.replaceAll(" ","");
			File fileToCreate = new File(f1, changedfilename);
			if (!fileToCreate.exists()) {
				fileToCreate.createNewFile();
			}

			OutputStream out = new FileOutputStream(fileToCreate);

			final String strPassword = "passwOrd00000000";  
			SecretKeySpec key = new SecretKeySpec(strPassword.getBytes(), "AES");  

			byte[] iv = new byte[IV_LENGTH];
			SecureRandom.getInstance(ALGO_RANDOM_NUM_GENERATOR).nextBytes(iv); // If
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(strPassword.getBytes()); 

			Cipher c = Cipher.getInstance(ALGO_VIDEO_ENCRYPTOR);
			c.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			OutputStream out1 = new CipherOutputStream(out, c);
			int count = 0;
			byte[] buffer = new byte[DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE];
			while ((count = uploadedInputStream.read(buffer)) >= 0) {
				out1.write(buffer, 0, count);
			}
			out.close();
			out1.close();
			return folderlocation+"/"+changedfilename;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}