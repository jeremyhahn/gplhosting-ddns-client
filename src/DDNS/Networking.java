package DDNS;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Networking {
    
       Timer timer;

    public String getHTML( String HTTP_LOCATION ) throws Exception {

           String HTTP_Response = new String();

           try {

                // Create an URL instance
                URL url = new URL( HTTP_LOCATION );

                // Get an input stream for reading
                InputStream in = url.openStream();

                // Create a buffered input stream for efficency
                BufferedInputStream bufIn = new BufferedInputStream(in);

                // Repeat until end of file
                for( ;; ) {

                    int data = bufIn.read();
                    // Check for EOF
                    if( data == -1 )
                        break;
                    else
                        HTTP_Response += (char) data;                        
                }
           }
           catch( MalformedURLException mue ) {

                  Main.fs.Log( "Invalid URL passed to getHTML()" + mue.getMessage() );
                  mue.printStackTrace();
                  
           }
           catch( IOException ioe ) {

                  Main.fs.Log( "I/O Error - " + ioe.getMessage() );
                  ioe.printStackTrace();
           }           
           return( HTTP_Response );
    }
    
    public String getPublicIP() {
           
           String Response = "";
           
           try {
               Response = getHTML( Main.AddressLookupURL );
            }
            catch ( Exception IOE ) { }

            int OffSet = Response.indexOf( "<title>" );
            int EndPoint = Response.indexOf( "</title>" );
            int RealOffSet = OffSet + 24;
            
            return( Response.substring( RealOffSet, EndPoint ) );
    }

    public String getCWD() {
        
           String DataPath = "";
           URL codebase = getClass().getProtectionDomain().getCodeSource().getLocation();
           Pattern p = Pattern.compile( "/" );
           String[] arrPath = p.split( codebase.toString() );

           for( int i=0; i<arrPath.length; i++ ) {

                Pattern fp = Pattern.compile( ".jar$" );                        
                Matcher m = fp.matcher( arrPath[i] );
                boolean result = m.find();                        

                if( !result && i != 0 )
                    DataPath += arrPath[i] + "/";
           }
           DataPath = DataPath.replace( "%20", " " );

           if( Main.OS.equalsIgnoreCase( "win32" ) )
               DataPath = DataPath.replace( "/", "\\" );
           
           else {

               String prefix = "/";
               DataPath = prefix + DataPath;
           }
           return( DataPath );
    }
    
    // Accepts a string of comma delimited domain(s) to update.
    public void UpdateDNS( String Domains ) {

           String Response = new String();

           try {
               // Construct data
               String data = URLEncoder.encode( "Username", "UTF-8") + "=" + URLEncoder.encode( Main.Username, "UTF-8" );
                      data += "&" + URLEncoder.encode( "Password", "UTF-8" ) + "=" + URLEncoder.encode( Main.Password, "UTF-8" );
                      data += "&" + URLEncoder.encode( "Domains", "UTF-8" ) + "=" + URLEncoder.encode( Domains, "UTF-8" );
                      data += "&" + URLEncoder.encode( "IP", "UTF-8" ) + "=" + URLEncoder.encode( Main.PublicIP, "UTF-8" );

               // Send data via POST
               URL url = new URL( Main.ServerSideURL );
               URLConnection conn = url.openConnection();
               conn.setDoOutput( true );
               OutputStreamWriter wr = new OutputStreamWriter( conn.getOutputStream() );
               wr.write( data );
               wr.flush();

               // Get the response
               BufferedReader rd = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
               String line;
               while( (line = rd.readLine() ) != null ) {

                       Response += line;
               }
               wr.close();
               rd.close();
           } catch( Exception e ) { Main.fs.Log( e.getMessage() ); e.printStackTrace(); }

           Main.fs.Log( "Attempting connection to update server." );
           Main.fs.Log( "Server response: " + Response );
           
           Main.svc.stop();
           Main.StartTimer();
    }
}