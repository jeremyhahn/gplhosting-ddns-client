package DDNS;

import com.sun.swing.internal.plaf.metal.resources.metal;
import java.io.*;
import java.util.*;
import java.lang.String;
import java.lang.*;
import java.util.regex.*;

public class Service extends Thread {

       public Service() {
       }

       public void run() {

              File f = new File( Main.AddressPath );

              Main.fs.Log( "Retrieving public IP address." );
              String StoredIP  = Main.fs.getStoredIP();
              String CurrentIP = Main.net.getPublicIP();

              Pattern p = Pattern.compile( "^" + StoredIP + "$" );
              Matcher m = p.matcher( CurrentIP );
              
              if( !m.find() ) {

                  Main.PublicIP = CurrentIP;
                  Main.fs.Log( "New IP address " + CurrentIP + " detected." );
                  DoDynamicUpdate();

                  try {
                      Main.fs.setContents( f, CurrentIP.trim(), 1 );
                  }
                  catch( java.io.IOException ioe ) { Main.fs.Log( ioe.getMessage() ); ioe.printStackTrace(); }

              } else {

                  Main.fs.Log( "IP current. Sleeping for " + ( Main.Interval / 1000 ) / 60 + " minutes." );
                  
                  // Stop the service and call the timer function from main().
                  Main.svc.stop();
                  Main.StartTimer();
              }
       }
       
       public void setGlobalConfigs() {

              FileSystem fs = new FileSystem();

              // Set config file variables
              Vector vc = new Vector();
              vc = ParseConfigs( getConfigs() );              

              for( int i=0; i<vc.size(); i++ ) {

                   String[] Variable = null;
                   
                   // Grabs the element in this iteration.
                   Variable = vc.get( i ).toString().split( "," );

                   // Sets the poll interval.
                   if( Variable[0].toString().equalsIgnoreCase( "interval" ) ) {

                       // Create log entry.
                       fs.Log( "Setting poll interval to " + Variable[1] + " minutes." );
                       
                       int Val = Integer.parseInt( Variable[1] );
                       Main.Interval = (Val * 1000) * 60;
                   }
                   
                   // Sets the current username.
                   if( Variable[0].toString().equalsIgnoreCase( "username" ) ) {

                       Main.Username = Variable[1].toString().trim();
                   }
                  

                   // Sets the password used to authenticate the account.
                   if( Variable[0].toString().equalsIgnoreCase( "password" ) ) {

                       Main.Password = Variable[1].toString().trim();
                   }
              }
       }

       public String getConfigs() {

              FileSystem fs = new FileSystem();
              File conf = new File( Main.ConfigDataPath );

              String Configs = fs.getContents( conf );

              return( Configs );
       }

       public Vector ParseConfigs( String strConfigs ) {

              Vector vc = new Vector();
              String[] strResult = null;
              int Counter = 0;

              String eol = System.getProperty( "line.separator" );
              strResult = strConfigs.split( eol );

              for( int i=0; i<strResult.length; i++ ) {

                  String[] temp = null;

                  if( !strResult[i].toString().contains( "#" ) && strResult[i].toString().contains( "=" ) ) {
                       
                      temp = strResult[i].split( "=" );
                      if( temp.length >= 2 ) {
                          
                          vc.add( Counter, temp[0].toString().trim() + "," + temp[1].toString().trim() );
                          Counter++;
                      }
                  }
              }
              return( vc );
       }

       public String getConfigValue( String strConfigName ) {

              // Set config file variables
              Vector vc = new Vector();
              vc = ParseConfigs( getConfigs() );

              for( int i=0; i<vc.size(); i++ ) {

                   String[] temp = null;
                   String Value = vc.get( i ).toString();
                   temp = Value.split( "," );

                   if( temp[0].toString().equalsIgnoreCase( strConfigName ) )
                       return( temp[1].toString().trim() );
              }
              return( "N/A" );
       }

       public void DoDynamicUpdate() {

              FileSystem fs = new FileSystem();
              Vector vc = fs.getDynamicDomains();
              Networking net = new Networking();
              String arrDomains = "";

              if( vc.size() > 1 ) {

                  for( int i=0; i<vc.size(); i++ ) {

                       arrDomains += vc.get( i ).toString().trim() + ",";
                  }
                  arrDomains = arrDomains.substring( 0, arrDomains.length() - 1 );
              } else if( vc.size() == 1 ) {
                  
                  arrDomains = vc.get( 0 ).toString().trim();
              }
              net.UpdateDNS( arrDomains );
       }
}