package DDNS;

import java.security.*;

public class MD5 {

    private String inStr;
    private MessageDigest md5;

    public MD5( String inStr ) {

        this.inStr = inStr;
        try {
	    this.md5 = MessageDigest.getInstance( "MD5" );
        }
	catch (Exception e) {

	    System.out.println(e.toString());
	    e.printStackTrace();
        }
    }

    public String hash() {
	// convert input String to a char[]
	// convert that char[] to byte[]
	// get the md5 digest as byte[]
	// bit-wise AND that byte[] with 0xff
	// prepend "0" to the output StringBuffer to make sure that we don't end up with
	// something like "e21ff" instead of "e201ff"

	char[] charArray = this.inStr.toCharArray();	
	byte[] byteArray = new byte[charArray.length];

	for (int i=0; i<charArray.length; i++)
	    byteArray[i] = (byte) charArray[i];
	
	byte[] md5Bytes = this.md5.digest( byteArray );
        StringBuffer hexValue = new StringBuffer();

	for( int i=0; i<md5Bytes.length; i++ ) {
	    int val = ((int) md5Bytes[i] ) & 0xff; 
	    if (val < 16) hexValue.append("0");
	    hexValue.append(Integer.toHexString(val));
	}
	return hexValue.toString();
    }
}