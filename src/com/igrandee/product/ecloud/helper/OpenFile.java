package com.igrandee.product.ecloud.helper;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

public class OpenFile extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 90240;
	public OpenFile(){

	}

	@Override
	public void init(ServletConfig config)
	{
		try{
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException
			{
		doPost(request, response);
			}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException
			{
		ResourceBundle bundle = null;
		try{
			bundle = ResourceBundle.getBundle("serversetup");
		}catch(java.util.MissingResourceException e){
			System.out.println("java.util.MissingResourceException: Can't find bundle for base name serversetup, locale en_US");
		}
		try
		{	
			String productCode = request.getParameter("r1");
			String s1 = null;
			String s2 = null;
 			if(productCode!=null){
				if(bundle!=null)
					s1 = bundle.getString(productCode).trim();
				else
					s1 = productCode;
			}
			s2 = request.getParameter("r2");
			if(s2==null || s2=="")
				s2="Empty";
			String s3 = request.getParameter("r3");
			String s4 = "txt";


			if(s2 != null && s2.indexOf(".") != -1)
				s4 = s2.substring(s2.lastIndexOf(".") + 1);

			s4=s4.toLowerCase();

			if(s3 != null && s3.equalsIgnoreCase("download")){
				String s5 = request.getParameter("r4"); 
				if(s5!=null){
					if(s5.equalsIgnoreCase("ori")){
						response.setHeader("Content-Disposition", "attachment;filename="+FilenameUtils.getBaseName(s2)+"."+s4);	 
					} else {
						response.setHeader("Content-Disposition", "attachment;filename="+s5+"." + s4);
					}
				}else{
					response.setHeader("Content-Disposition", "attachment;filename=output." + s4);
				}
				if(s4 != null && s4.equalsIgnoreCase("pdf"))
				{
					response.setContentType("application/pdf");
				}
				else if(s4 != null && s4.equalsIgnoreCase("pps"))
				{
					response.setContentType("application/vnd.ms-powerpoint");
				}
				else if(s4 != null && s4.equalsIgnoreCase("ppt"))
				{
					response.setContentType("application/vnd.ms-powerpoint");
				}
				else if(s4 != null && s4.equalsIgnoreCase("pptx"))
				{
					response.setContentType("application/vnd.ms-powerpoint");
				}
				else if(s4 != null && s4.equalsIgnoreCase("pub"))
				{
					response.setContentType("application/vnd.ms-publisher");
				}
				else if(s4 != null && s4.equalsIgnoreCase("swf"))
				{
					response.setContentType("application/vnd.swf");
				}
				else if(s4 != null && s4.equalsIgnoreCase("xls"))
				{
					response.setContentType("application/vnd.ms-excel");
				}
				else if(s4 != null && s4.equalsIgnoreCase("xlsx"))
				{
					response.setContentType("application/vnd.ms-excel");
				}
				else if(s4 != null && s4.equalsIgnoreCase("htm"))
				{
					response.setContentType("text/htm");
				}
				else if(s4 != null && s4.equalsIgnoreCase("html"))
				{
					response.setContentType("text/html");
				}
				else if(s4 != null && s4.equalsIgnoreCase("xml"))
				{
					response.setContentType("text/xml");
				}
				else if(s4 != null && s4.equalsIgnoreCase("jsp"))
				{
					response.setContentType("text/html");
				}
				else if(s4 != null && s4.equalsIgnoreCase("doc"))
				{
					response.setContentType("application/vnd.word");
				}
				else if(s4 != null && s4.equalsIgnoreCase("docx"))
				{
					response.setContentType("application/vnd.word");
				}
				else if(s4 != null && s4.equalsIgnoreCase("txt"))
				{
					response.setContentType("text/plain");
				}
				else if(s4 != null && s4.equalsIgnoreCase("pdf"))
				{
					response.setContentType("application/pdf");
				}
				else if(s4 != null && s4.equalsIgnoreCase("rtf"))
				{
					response.setContentType("application/vnd.rtf");
				}
				else if(s4 != null && s4.equalsIgnoreCase("gif"))
				{
					response.setContentType("image/gif");
				}
				else if(s4 != null && s4.equalsIgnoreCase("jpg"))
				{
					response.setContentType("image/jpeg");
				}
				else if(s4 != null && s4.equalsIgnoreCase("tif"))
				{
					response.setContentType("image/tif");
 				}
				else if(s4 != null && s4.equalsIgnoreCase("tiff"))
				{
					response.setContentType("image/tiff");
 				}
				else if(s4 != null && s4.equalsIgnoreCase("png"))
				{
					response.setContentType("image/png");
				}

				else if(s4 != null && s4.equalsIgnoreCase("log"))
				{
					response.setContentType("text/log");
				}
				else if(s4 != null && (s4.equalsIgnoreCase("dcm") ||  s4.equalsIgnoreCase("dicom"))){
					response.setContentType("application/dicom");
				}
				else
				{
					response.setContentType("application/octet-stream");
				}
			}
			else
			{
				response.setHeader("Content-disposition", "inline;filename=output." + s4);
				if(s4 != null && s4.equalsIgnoreCase("pdf"))
				{
					response.setContentType("application/pdf");
				}
				else if(s4 != null && s4.equalsIgnoreCase("pps"))
				{
					response.setContentType("application/vnd.ms-powerpoint");
				}
				else if(s4 != null && s4.equalsIgnoreCase("ppt"))
				{
					response.setContentType("application/vnd.ms-powerpoint");
				}
				else if(s4 != null && s4.equalsIgnoreCase("pptx"))
				{
					response.setContentType("application/vnd.ms-powerpoint");
				}

				else if(s4 != null && s4.equalsIgnoreCase("pub"))
				{
					response.setContentType("application/mspublisher");
				}
				else if(s4 != null && s4.equalsIgnoreCase("swf"))
				{
					response.setContentType("application/swf");
				}
				else if(s4 != null && s4.equalsIgnoreCase("xls"))
				{
					response.setContentType("application/vnd.ms-excel");
				}
				else if(s4 != null && s4.equalsIgnoreCase("xlsx"))
				{
					response.setContentType("application/vnd.ms-excel");
				}
				else if(s4 != null && s4.equalsIgnoreCase("htm"))
				{
					response.setContentType("text/html");
				}
				else if(s4 != null && s4.equalsIgnoreCase("html"))
				{
					response.setContentType("text/html");
				}
				else if(s4 != null && s4.equalsIgnoreCase("xml"))
				{
					response.setContentType("text/xml");
				}
				else if(s4 != null && s4.equalsIgnoreCase("jsp"))
				{
					response.setContentType("text/html");
				}
				else if(s4 != null && s4.equalsIgnoreCase("doc"))
				{
					response.setContentType("application/msword");
				}
				else if(s4 != null && s4.equalsIgnoreCase("docx"))
				{
					response.setContentType("application/vnd.word");
				}

				else if(s4 != null && s4.equalsIgnoreCase("txt"))
				{
					response.setContentType("text/plain");
				}
				else if(s4 != null && s4.equalsIgnoreCase("pdf"))
				{
					response.setContentType("application/pdf");
				}
				else if(s4 != null && s4.equalsIgnoreCase("rtf"))
				{
					response.setContentType("application/msword");
				}
				else if(s4 != null && s4.equalsIgnoreCase("gif"))
				{
					response.setContentType("image/gif");
				}
				else if(s4 != null && s4.equalsIgnoreCase("jpg"))
				{
					response.setContentType("image/jpeg");
				}
				else if(s4 != null && s4.equalsIgnoreCase("tif"))
				{
 					response.setContentType("image/tif");
				}
				else if(s4 != null && s4.equalsIgnoreCase("tiff"))
				{
					response.setContentType("image/tiff");
 				}
				else if(s4 != null && s4.equalsIgnoreCase("png"))
				{
					response.setContentType("image/png");
				}
				else if(s4 != null && s4.equalsIgnoreCase("mp3"))
				{
					response.setContentType("audio/mp3");
				}
				else if(s4 != null && s4.equalsIgnoreCase("mpg"))
				{
					response.setContentType("video/mpg");
				}
				else if(s4 != null && s4.equalsIgnoreCase("wma"))
				{
					response.setContentType("audio/wma");
				}
				else if(s4 != null && s4.equalsIgnoreCase("mp4"))
				{
					response.setContentType("video/mp4");
				}
				else if(s4 != null && s4.equalsIgnoreCase("ogg"))
				{
					response.setContentType("video/ogg");
				}
				else if(s4 != null && s4.equalsIgnoreCase("wmv"))
				{
					response.setContentType("video/wmv");
				}
				else if(s4 != null && s4.equalsIgnoreCase("wmv"))
				{
					response.setContentType("video/wmv");
				}
				else if(s4 != null && s4.equalsIgnoreCase("avi"))
				{
					response.setContentType("video/avi");
				}
				else if(s4 != null && s4.equalsIgnoreCase("3gp"))
				{
					response.setContentType("video/3gpp");
				}
				else if(s4 != null && s4.equalsIgnoreCase("3gp"))
				{
					response.setContentType("video/3gp");
				}
				else if(s4 != null && s4.equalsIgnoreCase("f4v"))
				{
					response.setContentType("video/x-f4v");
				}
				else if(s4 != null && s4.equalsIgnoreCase("log"))
				{
					response.setContentType("text/log");
				}
				else if(s4 != null && (s4.equalsIgnoreCase("dcm") ||  s4.equalsIgnoreCase("dicom"))){
					response.setContentType("application/dicom");
				}
			}
			BufferedInputStream  input			= null;
			BufferedOutputStream output 		= null;
			try{
 				File fileFound = new File(s1 + s2);                
				if(!fileFound.exists())
					s2 = "nophoto.jpg";
				input				= new BufferedInputStream(new FileInputStream(s1 + s2));
				output 				= new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
				byte[] buffer 		= new byte[DEFAULT_BUFFER_SIZE];
				int length1 		= 0;
				while ((length1 = input.read(buffer)) > 0)	{
					output.write(buffer, 0, length1);
				}
				output.flush();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally
			{
				if(output != null)
					output.close();
				if(input != null)
					input.close();
			}
		}
		catch(Exception exception) { 
			exception.printStackTrace();
		}
			}

	@Override
	public String getServletInfo(){
		return "OpenFile";
	}
}