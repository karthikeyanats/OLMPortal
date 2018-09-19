<%@ page import = "java.sql.*, java.util.*, java.util.zip.*" %>

<%!
	
	String verifyChecksum(String MerchantId , String OrderId, String Amount, String AuthDesc, String WorkingKey, String CheckSum) throws Exception 	{
		String str = MerchantId+"|"+OrderId+"|"+Amount+"|"+AuthDesc+"|"+WorkingKey;
		
		Adler32  adl = new Adler32();                 
		adl.update(str.getBytes());
		long adler = adl.getValue();

		String newChecksum = String.valueOf(adl.getValue());
		return (newChecksum.equals(CheckSum)) ? "true" : "false";
		
	}


	String getChecksum(String MerchantId, String OrderId, String Amount, String redirectUrl, String WorkingKey) throws Exception {
		String str = MerchantId + "|" + OrderId + "|" + Amount + "|" + redirectUrl + "|" + WorkingKey;

		Adler32  adl = new Adler32();
		adl.update(str.getBytes());
		return String.valueOf(adl.getValue());
	}


%>
