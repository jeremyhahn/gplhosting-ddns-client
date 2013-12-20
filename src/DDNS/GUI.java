package DDNS;

import java.io.*;
import java.awt.*;
import java.awt.Image;


public class GUI extends javax.swing.JFrame {

    public static final boolean IsLoaded = true;

    public GUI() {
        initComponents();

        this.txtUsername.setText( Main.svc.getConfigValue( "username" ) );

        this.menuFileAdd.setText( "Add" );
        this.menuFileAddDomain.setText( "New Domain" );
        
        this.menuToolsOptions.setText( "Options" );

        this.menuHelpAbout.setText( "About" );

        this.txtWAN.setText( Main.net.getPublicIP() );

        LoadDomains();
        setIconImage( java.awt.Toolkit.getDefaultToolkit().getImage(this.getClass().getResource( Main.ProgramIcon ) ) );
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        menuPopUp = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        DomainList = new javax.swing.JList();
        imgUser = new javax.swing.JLabel();
        lblYourIP = new javax.swing.JLabel();
        lblLoggedInAs = new javax.swing.JLabel();
        txtUsername = new javax.swing.JLabel();
        txtWAN = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileAdd = new javax.swing.JMenu();
        menuFileAddDomain = new javax.swing.JMenuItem();
        menujSeparator1 = new javax.swing.JSeparator();
        menuExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuTools = new javax.swing.JMenu();
        menuToolsOptions = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuHelpAbout = new javax.swing.JMenuItem();

        menuPopUp.setName("");
        menuPopUp.getAccessibleContext().setAccessibleName("menuPopUp");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Linux Dynamic DNS Client");
        setIconImage(getIconImage());
        jScrollPane1.setViewportView(DomainList);

        imgUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DDNS/images/User.gif")));
        imgUser.setText("jLabel1");

        lblYourIP.setFont(new java.awt.Font("Verdana", 0, 10));
        lblYourIP.setText("Your IP Address:");

        lblLoggedInAs.setFont(new java.awt.Font("Verdana", 0, 10));
        lblLoggedInAs.setText("Logged in as:");

        txtUsername.setFont(new java.awt.Font("Verdana", 0, 10));
        txtUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtUsername.setText(" ");

        txtWAN.setFont(new java.awt.Font("Verdana", 0, 10));
        txtWAN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtWAN.setText(" ");
        txtWAN.getAccessibleContext().setAccessibleName("txtWAN");

        menuFile.setText("File");
        menuFileAdd.setText("Menu");
        menuFileAddDomain.setText("Item");
        menuFileAddDomain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileAddDomainActionPerformed(evt);
            }
        });

        menuFileAdd.add(menuFileAddDomain);

        menuFile.add(menuFileAdd);

        menuFile.add(menujSeparator1);

        menuExit.setIcon(new javax.swing.ImageIcon(""));
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });

        menuFile.add(menuExit);

        jMenuBar1.add(menuFile);

        menuEdit.setText("Edit");
        jMenuBar1.add(menuEdit);

        menuTools.setText("Tools");
        menuToolsOptions.setText("Item");
        menuTools.add(menuToolsOptions);

        jMenuBar1.add(menuTools);

        menuHelp.setText("Help");
        menuHelpAbout.setText("Item");
        menuHelp.add(menuHelpAbout);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .add(32, 32, 32)
                .add(imgUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(26, 26, 26)
                        .add(lblLoggedInAs))
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtUsername, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(46, 46, 46)
                        .add(lblYourIP))
                    .add(layout.createSequentialGroup()
                        .add(30, 30, 30)
                        .add(txtWAN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(28, 28, 28)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(imgUser)
                        .add(txtUsername)
                        .add(txtWAN))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(lblLoggedInAs)
                        .add(lblYourIP)))
                .add(42, 42, 42)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed

            System.exit( 0 );
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuFileAddDomainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileAddDomainActionPerformed
            
             NewDomain nd = new NewDomain();
             nd.setVisible( true );

             Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
             nd.setLocation( (dm.width-nd.getWidth() ) / 2, ( dm.height-nd.getHeight() )/ 2 );
    }//GEN-LAST:event_menuFileAddDomainActionPerformed
    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JList DomainList;
    private javax.swing.JLabel imgUser;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLoggedInAs;
    private javax.swing.JLabel lblYourIP;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuFileAdd;
    private javax.swing.JMenuItem menuFileAddDomain;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuHelpAbout;
    private javax.swing.JPopupMenu menuPopUp;
    private javax.swing.JMenu menuTools;
    private javax.swing.JMenuItem menuToolsOptions;
    private javax.swing.JSeparator menujSeparator1;
    private javax.swing.JLabel txtUsername;
    private javax.swing.JLabel txtWAN;
    // End of variables declaration//GEN-END:variables

    public static void CreateDomain( String Domain ) {

           String Filename = "";
         if( Main.OS == "win32" )
             Filename = Main.DomainDataPath + "\\" + Domain + ".dat";
         else
             Filename = Main.DomainDataPath + "/" + Domain + ".dat";
         
         File f = new File( Filename );
         
         FileOutputStream out;
	 PrintStream p;

         try {
                  out = new FileOutputStream( Filename );

                  p = new PrintStream( out );
                  p.close();                  
         }
         catch( Exception e )  { Main.fs.Log( e.getMessage() ); e.printStackTrace(); }

         Main.net.UpdateDNS( Domain );
         LoadDomains();
    }
    
    public static void LoadDomains() {
        
           DomainList.setListData( Main.fs.getDynamicDomains() );
    }
}