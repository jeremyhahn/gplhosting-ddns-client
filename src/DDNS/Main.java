package DDNS;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

       public static Timer timer;
       public static Service svc    = new Service();
       public static Networking net = new Networking();
       public static FileSystem fs  = new FileSystem();

       public static int Interval = 0;
      
       public static String DomainDataPath = "";
       public static String ConfigDataPath = "";
       public static String AddressPath = "";
       public static String LogPath = "";
       public static String LineBreakChar = "";

       public static final String ServerSideURL = "http://192.168.15.10/ip.asp";
       public static final String AddressLookupURL = "http://whatismyip.com";
       public static final String ProgramIcon = "/DDNS/images/ddnsprog.gif";

       public static String OS = "";

       public static String Username = "";
       public static String Password = "";
       public static String PublicIP = "";

       public Main() {        
       }

       public static void main( String[] args ) {

              String CodeBasePath = net.getCWD();
              DomainDataPath = CodeBasePath + "data";
              ConfigDataPath = CodeBasePath + "ddns.conf";
              AddressPath    = CodeBasePath + "ip.dat";
              LogPath        = CodeBasePath + "logs";

              if( System.getProperty( "os.name" ).toString().contains( "Windows" ) ) {
                  Main.OS = "win32";
                  Main.LineBreakChar = "\\r\\n";
              }
              else {
                  Main.OS = "nix";
                  Main.LineBreakChar = "\\n";
              }

              if( args.length > 0 ) {

                  String strOption = args[0];
                  if( strOption.toLowerCase().equals( "--gui" ) ) {

                       GUI gui       = new GUI();

                       fs.Log( "Initalizing client in GUI mode." );

                       gui.setVisible( true );
                       Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                       gui.setLocation( (d.width-gui.getWidth() ) / 2, ( d.height-gui.getHeight() )/ 2 );

                       fs.Log( "Retrieving current IP address and setting application configuration variables." );

                       // Sets the current IP and poll interval.
                       svc.setGlobalConfigs();

                       // Start the service.
                       svc.run();

                   } else if( strOption.toLowerCase().equals( "--encrypt" ) ) {

                              if( args.length == 2 ) {

                                  MD5 md5 = new MD5( args[1] );
                                  System.out.println( md5.hash() );
                              }

                   } else if( strOption.equalsIgnoreCase( "--console" ) ) {

                              fs.Log( "Initalizing client in service mode." );
                              fs.Log( "Retrieving current IP address and setting application configuration variables." );

                              // Sets the current IP and poll interval.
                              svc.setGlobalConfigs();

                              // Start the service thread.
                              new Thread( svc ).start();

                 } else if( strOption.equalsIgnoreCase( "--start" ) ) {

                            fs.Log( "Initalizing client in daemon mode." );
                            fs.Log( "Retrieving current IP address and setting application configuration variables." );

                            // Sets the current IP and poll interval.
                            svc.setGlobalConfigs();

                            // Starts the service thread as a daemon.
                            new Thread( svc ).setDaemon( true );
                            svc.start();

                 } else if( strOption.equalsIgnoreCase( "--stop" ) ) {

                            fs.Log( "Stopping dynamic DNS client." );

                            // Stops the service thread.
                            svc.stop();

                 } else if( strOption.equalsIgnoreCase( "--restart" ) ) {

                            fs.Log( "Stopping dynamic DNS client." );

                            // Stops the service.
                            svc.stop();

                            fs.Log( "Initalizing dynamic DNS client in daemon mode." );

                            // Starts the service thread as a daemon.
                            new Thread( svc ).setDaemon( true );                     
                            svc.start();
                  }


                } else {

                        String eol = System.getProperty( "line.separator" );
                        System.out.println( "Usage: " + eol +
                                                //    "--gui-only  Shows the graphical user interface without starting the service." + eol +
                                                      "--gui       Starts the dynamic DNS client in a graphical mode." + eol +
                                                      "--console   Starts the dynamic DNS client in console mode." + eol +
                                                      "--start     Starts the dynamic DNS client in daemon mode." + eol +
                                                      "--stop      Stops the dynamic DNS client." + eol +
                                                      "--restart   Stops the dynamic DNS client and starts up in daemon mode." + eol +
                                                      "--encrypt   Encrypts a string using the MD5 algorithm."
                                           );
                }
        }

        public static void StartTimer() {

               timer = new Timer();                      
               timer.schedule( new svcThread(), Main.Interval );
        }

        static class svcThread extends TimerTask {

            public void run() {

               new Thread( svc ).start();
            }
        }
}