package DDNS;
import java.io.*;
import java.util.*;

public class FileSystem {

  public String getContents(File aFile) {

    StringBuffer contents = new StringBuffer();
    BufferedReader input = null;
    try {
      //use buffering
      //this implementation reads one line at a time
      //FileReader always assumes default encoding is OK!
      input = new BufferedReader( new FileReader( aFile ) );
      String line = null; //not declared within while loop
      while (( line = input.readLine()) != null){
        contents.append( line );
        contents.append( System.getProperty( "line.separator" ) );
      }
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex){
      ex.printStackTrace();
    }
    finally {
      try {
        if (input!= null) {
          //flush and close both "input" and its underlying FileReader
          input.close();
        }
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return contents.toString().trim();
  }
  
  public void setContents(File aFile, String aContents, int Mode )
                                 throws FileNotFoundException, IOException {
    if (aFile == null) {
      throw new IllegalArgumentException("File should not be null.");
    }
    if (!aFile.exists()) {
      throw new FileNotFoundException ("File does not exist: " + aFile);
    }
    if (!aFile.isFile()) {
      throw new IllegalArgumentException("Should not be a directory: " + aFile);
    }
    if (!aFile.canWrite()) {
      throw new IllegalArgumentException("File cannot be written: " + aFile);
    }

    //declared here only to make visible to finally clause; generic reference
    Writer output = null;
    try {
      //use buffering
      //FileWriter always assumes default encoding is OK!
      switch( Mode ) {
          
          case 0:
              output = new BufferedWriter( new FileWriter(aFile, true) );
              break;
              
          case 1:
              output = new BufferedWriter( new FileWriter(aFile) );
              break;
              
          default:
              output = new BufferedWriter( new FileWriter(aFile) );
              break;
      }   
      output.write( aContents );
    }
    finally {
      //flush and close both "output" and its underlying FileWriter
      if (output != null) output.close();
    }
  }
  
  public Vector getDynamicDomains() {

                File dir = new File( Main.DomainDataPath );
                Vector vc = new Vector();
                String FileSlash = "";

                if( dir.isDirectory() ) {

                    String s[] = dir.list();
                    for( int i=0; i<s.length; i++ ) {

                         if( Main.OS.equalsIgnoreCase( "win32" ) )
                             FileSlash = "\\";
                         else
                             FileSlash = "/";

                         File thisName = new File( Main.DomainDataPath + FileSlash + s[i] );

                         if( thisName.isFile() && thisName.getName().contains( ".dat" ) )
                                 vc.add( thisName.getName().replace( ".dat", "" ) );
                    }
                }
                else {
                    
                    Log( "The specified directory (" + Main.DomainDataPath + ") does not exist." );
                }
                return( vc );
  }
  
  public String getStoredIP() {
      
         String Filename = Main.AddressPath;

         // Create a file object and check to make sure that the file exists.
         File f = new File( Filename );
         if( f.exists() == false ) {         

             // The file doesnt exist, so lets create one.
             FileOutputStream out; // declare a file output object
	     PrintStream p;        // declare a print stream object

             try {
                  out = new FileOutputStream( Filename );

                  // Connect print stream to the output stream and write a null string to the file.
                  p = new PrintStream( out );                  
                  p.println( "127.0.0.1" );
                  p.close();                  
             }
             catch( Exception e )  { Log( "FileSystem.getStoredIP() :: Error writing to file " + Filename + "." + e.getMessage() ); e.printStackTrace(); }
             return( Main.PublicIP );

         } else {
           
           return( getContents( f ) );
         }         
  }
  
  public void Log( String msg ) {

         // Create a calendar object
         Calendar cal = Calendar.getInstance( TimeZone.getDefault() );

         // Format the date
         String DATE_FORMAT = "yyyy-MM-dd";         
         java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat( DATE_FORMAT );
         sdf.setTimeZone( TimeZone.getDefault() ); 
         
         // Format the time
         String TIME_FORMAT = "HH:mm:ss";
         java.text.SimpleDateFormat stf = new java.text.SimpleDateFormat( TIME_FORMAT );
         stf.setTimeZone( TimeZone.getDefault() );

         String Filename = "";

         // Set a file name with the format 'yyyy-mm-dd.log'
         if( Main.OS == "win32" ) {
             Filename = Main.LogPath + "\\" + sdf.format( cal.getTime() ) + ".log";
         } else {
             Filename = Main.LogPath + "/" + sdf.format( cal.getTime() ) + ".log";
         }

         // Create a file object and check to make sure that the file exists.
         File f = new File( Filename );
         if( f.exists() == false ) {         

             // The file doesnt exist, so lets create one.
             FileOutputStream out; // declare a file output object
	     PrintStream p;        // declare a print stream object

             try {
                  out = new FileOutputStream( Filename );

                  // Connect print stream to the output stream and write the log message.
                  p = new PrintStream( out );

                  // Create log entry using [DATE][TAB][MSG] format.
                  p.println( "[" + stf.format(cal.getTime() ) + "]\t" + msg );
                  p.close();
             }
             catch( Exception e )  { System.err.println( "FileSystem.Log() :: Error writing to file " + Filename + "." + e.getMessage() ); e.printStackTrace(); }
          } else {

             // The log file already exists, so lets just go ahead and append this data string.
             try {

                 // Create log entry using [DATE][TAB][MSG] format.
                 setContents( f, "[" + stf.format(cal.getTime() ) + "]\t" + msg + System.getProperty( "line.separator" ), 0 );
             }
             catch( FileNotFoundException e ) { System.err.println( e.getMessage() ); e.printStackTrace(); }
             catch( java.io.IOException ioe ) { System.err.println( ioe.getMessage() ); ioe.printStackTrace(); }
         }
  }
  
  /** A convenience method to throw an exception */
  private static void abort(String msg) throws IOException { 
    throw new IOException(msg); 
  }
}