package principal;
//import conectores.util.Conexion;
import conectores.util.Utils;
import java.awt.Component;
//import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.swing.DefaultListModel;
//import javax.swing.Icon;
//import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//import javax.swing.filechooser.FileNameExtensionFilter;

public class Inicio extends javax.swing.JFrame {

    ArrayList lista = new ArrayList();
    DefaultListModel modelo = new DefaultListModel();
    File fichero = null;
    FileController conexion = new FileController();
    Utils util = new Utils();
   static String  usuario = null;
   KeyPair keys;
   String privada;
   String publica;
   PublicKey clavePub;
   PrivateKey clavePriv;
   static String hash2;
   String rutaServidor = ".\\src\\main\\java\\servidor\\imagenes\\";
   
    static int xloca,yloca;
    
    public Inicio(String email,int x, int y,String hashm2) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        usuario=email;
            hash2=hashm2;
           xloca=x;
           yloca=y;
        ResultSet llaves=conexion.getUserByEmail(email);
         KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        this.keys = keyGen.generateKeyPair();
        if(llaves.next()){
            
            try {
                //si hay llaves en la base de datos se usan
                privada=llaves.getString("privada");
                publica=llaves.getString("publica");
                
                byte[] publicKeyBytes = Base64.getDecoder().decode(publica);
                X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                clavePub = keyFactory.generatePublic(spec);
                
                //convertimos la mitad 2 del hash del pashhword en bytes
                byte[]hashBytes=hash2.getBytes();
                byte[] privateKeyBytes = Utils.decryptMessage(privada,hashBytes);
                
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                KeyFactory fact = KeyFactory.getInstance("RSA");
                
                clavePriv = fact.generatePrivate(keySpec);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
               
        }
           

        initComponents();
        setLocation(x,y);
        
        listImages.setModel(modelo);
        nombreUsuario.setText("Hola " + usuario);
        renderList();
        
        //prueba del metodo que on un nombre nos devuelve un usuario
        ResultSet prueba = conexion.getUserByEmail(usuario);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        botonInsertar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listImages = new javax.swing.JList<>();
        inputText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        botonDescifrar = new javax.swing.JButton();
        textoInformativo = new javax.swing.JLabel();
        botonImagen = new javax.swing.JButton();
        errorMensaje = new javax.swing.JLabel();
        usuarioCompartir = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        botonCompartir = new javax.swing.JButton();
        exitoMensaje = new javax.swing.JLabel();
        nombreUsuario = new javax.swing.JLabel();
        refrescar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botonInsertar.setText("subir archivo");
        botonInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInsertarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(listImages);

        jLabel2.setText("Nombre del archivo");

        botonDescifrar.setText("Descargar");
        botonDescifrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDescifrarActionPerformed(evt);
            }
        });

        textoInformativo.setText("Para descargar una imagen seleccionala");

        botonImagen.setText("Cargar archivo");
        botonImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonImagenActionPerformed(evt);
            }
        });

        errorMensaje.setForeground(new java.awt.Color(255, 0, 0));

        jLabel3.setText("Compartir con :");

        botonCompartir.setText("Compartir");
        botonCompartir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCompartirActionPerformed(evt);
            }
        });

        exitoMensaje.setForeground(new java.awt.Color(0, 255, 0));

        nombreUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        refrescar.setText("Refrescar");
        refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarActionPerformed(evt);
            }
        });

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputText, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(botonImagen)
                            .addComponent(botonInsertar)
                            .addComponent(nombreUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonDescifrar)
                            .addComponent(usuarioCompartir, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCompartir)
                            .addComponent(textoInformativo)
                            .addComponent(refrescar)
                            .addComponent(eliminar))
                        .addGap(0, 109, Short.MAX_VALUE))
                    .addComponent(exitoMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(errorMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonImagen)
                        .addGap(18, 18, 18)
                        .addComponent(botonInsertar)
                        .addGap(0, 240, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(refrescar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textoInformativo)
                        .addGap(18, 18, 18)
                        .addComponent(botonDescifrar)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usuarioCompartir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonCompartir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eliminar)
                        .addGap(142, 142, 142)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(errorMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exitoMensaje)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void renderList(){
        try {
            
            ResultSet rs = conexion.getFilesNameByUser(usuario);
            modelo.removeAllElements();
           
            while(rs.next()){
                modelo.addElement(rs.getString("nombre"));
                lista.add(rs.getString("nombre"));
            }
           
            
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void botonInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInsertarActionPerformed
        
        String dato = inputText.getText();
        //comprobamos que haya algun nombre para la foto o salimos
        if(dato.length() == 0){
            errorMensaje.setText("No se ha introducido un nombre en la imagen. Recuerda que no se pueden repetir los nombres");
            exitoMensaje.setText("");
            return;
        }
        //comprobamos que haya fichero para subir o salimos
        if(fichero == null){
            errorMensaje.setText("No se ha subido una imagen");
            
            exitoMensaje.setText("");
            return;
        }
        
        if(lista.contains(dato)){
            errorMensaje.setText("Ya existe ese nombre para una imagen guardada");
            exitoMensaje.setText("");
            return;
        }
        
        //borramos mensaje de error
        errorMensaje.setText("");
        
        try {
            //ruta del archivo subido
            String rutaArchivo = fichero.getAbsolutePath();
            
            //genero contrasenya
            String encryptionKeyString = Utils.getAlphaNumericString();
            // la convierto en array de bytes
            byte[] keyBytes = encryptionKeyString.getBytes();
               
            //codificamos archivo
            byte[] archivoCodificado = Utils.codificar(rutaArchivo);
            
            //encriptamos el archivo con AES y la clave generada
            String archivoEncriptado = Utils.encryptMessage(archivoCodificado, keyBytes);
            
            //Encriptamos la clave con RSA 2048
            String ClaveAESEncriptada = Utils.encriptarRSA(keyBytes,clavePub);

            //introducimos datos en la base de datos
            int idArchivo = conexion.insertFile(dato, ClaveAESEncriptada, "el pepe", usuario);
            
            //algo ha ido mal durante la subida de la foto
            if(idArchivo == -1){
               errorMensaje.setText("Algo ha ocurrido y no se ha podido subir la imagen");
               exitoMensaje.setText("");
            }
            
            
            else{
                //guardamos archivo encriptado como txt en el directorio servidor imagenes
                 String nombreFichero = idArchivo + ".txt";
                
                
                String rutaFichero = rutaServidor + nombreFichero;
                System.out.println(rutaFichero);
               
                File file = new File(rutaFichero);

                if (!file.exists()) {
                    file.createNewFile();
                }
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(archivoEncriptado);
                    bw.close();
               boolean actualizado = conexion.updateFile(idArchivo, nombreFichero);
                
            }
            
            //añadimos fichero a la interfaz y actualizamos lista
            lista.add(dato);
            modelo.removeAllElements();
            for(int i = 0; i < lista.size(); i++){
                modelo.addElement(lista.get(i));
            }
            
            fichero=null;
            inputText.setText("");
            Inicio aux=new Inicio(usuario,getLocation().x,getLocation().y,hash2);
            aux.setVisible(true);
            this.setVisible(false);
        
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }//GEN-LAST:event_botonInsertarActionPerformed

    private void botonDescifrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDescifrarActionPerformed
        
         
        try {
            
            //creamos ventana para seleccionar carpeta
            JFileChooser carpeta = new JFileChooser();
            //activamos modo solo directorios
            carpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //para guardar el componente actual
            Component parent = null;
            int returnVal = carpeta.showSaveDialog(parent);
            
            //en el caso de que cumpla la condicion de directorio
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                // obtener ruta donde se descargara el archivo
                String selectPath = carpeta.getSelectedFile().getPath();
                System.out.println ("El directorio que elija es:" + selectPath);
                
                 //recupero nombre seleccionado y consigo su informacion de la base de datos
                String mensaje= listImages.getSelectedValue(); 
                ResultSet rs = conexion.getFileByName(mensaje, usuario);       
                if(rs.next()){
                    String clave = rs.getString("clave");
                    String rutaArchivo = rs.getString("archivo");
                    
                    //creamos una ruta y leemos el fichero
                    Path aux = Paths.get(rutaServidor + rutaArchivo);
                    String archivoGuardado = Files.readString(aux, StandardCharsets.UTF_8);
                    
                    byte[] claveArray = Utils.desencriptarRSA(clave,clavePriv); //Desencriptamos con la clave Privada
                    //ahora vamos a decodificar el archivo
                    byte[] archivoDescifrado = Utils.decryptMessage(archivoGuardado, claveArray);
                    //ahora vamos a decodificar el archivo y guardarlo en el dispossitivo en la ruta indicada
                    Utils.decodificar(archivoDescifrado, selectPath + "\\" + mensaje);
                    exitoMensaje.setText("La descarga se ha completado");
                    errorMensaje.setText("");
                }
            }
            else{
                errorMensaje.setText("no se ha seleccionado una carpeta");
                exitoMensaje.setText("");
            }
  
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }//GEN-LAST:event_botonDescifrarActionPerformed

    
    
    private void botonImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonImagenActionPerformed

        int resultado;
        
        CargarFoto ventana = new CargarFoto();
        
        resultado= ventana.jfchCargarfoto.showOpenDialog(null);

        if (JFileChooser.APPROVE_OPTION == resultado){

            //pillo el nombre de la imagen subida y la pongo en el input
            fichero = ventana.jfchCargarfoto.getSelectedFile();
            inputText.setText(fichero.getName());     
        }
    }//GEN-LAST:event_botonImagenActionPerformed

    private void botonCompartirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCompartirActionPerformed
        try {
            //obtenemos datos de la interfaz
            String usuario2 = usuarioCompartir.getText();
            //comprobamos que haya algun nombre para el usuario
            if(usuario2.length() == 0){
                errorMensaje.setText("No se ha indicado a que usuario se comparte");
                exitoMensaje.setText("");
                return;
            }
            
            //obtenemos el usuario
            ResultSet usu2 = conexion.getUserByEmail(usuario2);
            
            if(usu2.next()){  //si hay usuario
                
                String ficheroNom= listImages.getSelectedValue();
                
                
                //buscamos al usuario que quiere compartir y la foto que quiere compartir
                ResultSet fl = conexion.getFileByName(ficheroNom, usuario);
                ResultSet us = conexion.getUserByEmail(usuario);
                
                if(us.next() && fl.next() ){  //aqui habria un if para ver si existen
                    //Pasamos las claves de String a formato clave
                    byte[] publicKeyBytes = Base64.getDecoder().decode(usu2.getString("publica"));
                    X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    PublicKey cPub = keyFactory.generatePublic(spec);

                    // desencriptamos la clave AES del fichero con la clave privada del usuario logueado
                    byte[] aes =Utils.desencriptarRSA(fl.getString("clave"),clavePriv);
                    // encriptamos la clave AES del fichero con la clave publica del usuario al que queremos compartir
                    String AESRSA= Utils.encriptarRSA(aes,cPub);

                    //compartimos el archivo en la base de datos
                    conexion.shareFile(usu2.getString("email"),fl.getInt("id"),AESRSA);
                    exitoMensaje.setText("La imagen ha compartido con exito");
                    errorMensaje.setText("");
                }else{
                    errorMensaje.setText("Ha ocurrido un problema con los usuarios");
                    exitoMensaje.setText("");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_botonCompartirActionPerformed

    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        try {
            Inicio aux=new Inicio(usuario,getLocation().x,getLocation().y,hash2);
            aux.setVisible(true);
            this.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refrescarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
                 
       //recupero nombre del fichero seleccionado y consigo su informacion 
       String mensaje= listImages.getSelectedValue(); 
       
        try {
            if (conexion.deleteFile(usuario, mensaje)){
                try {
                    Inicio aux2=new Inicio(usuario,getLocation().x,getLocation().y,hash2);
                    aux2.setVisible(true);
                    this.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            else{
                errorMensaje.setText("No se ha podido eliminar el archivo");
                exitoMensaje.setText("");
            }} catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }//GEN-LAST:event_eliminarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Inicio(usuario,xloca,yloca,hash2).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCompartir;
    private javax.swing.JButton botonDescifrar;
    private javax.swing.JButton botonImagen;
    private javax.swing.JButton botonInsertar;
    private javax.swing.JButton eliminar;
    private javax.swing.JLabel errorMensaje;
    private javax.swing.JLabel exitoMensaje;
    private javax.swing.JTextField inputText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listImages;
    private javax.swing.JLabel nombreUsuario;
    private javax.swing.JButton refrescar;
    private javax.swing.JLabel textoInformativo;
    private javax.swing.JTextField usuarioCompartir;
    // End of variables declaration//GEN-END:variables
}
