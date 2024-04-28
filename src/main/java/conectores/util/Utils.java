package conectores.util;

//los paquetes de java para las excepciones
import java.io.IOException;
import java.math.BigInteger;

//Librerias para el manejo de rutas y creación de archivos
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.*;

//Librerías de java para pasarlo a base64
import java.util.*;
import java.util.Base64;
//Librerías para la clave
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
//Librerías para encriptar y desencriptar
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
    
    //Función para codificar
    public static byte[] codificar(String archivo) throws IOException {
        //Los archivos los convierte a cadena de bytes y lo convertimos a base64
        byte[] bytet = Files.readAllBytes(Paths.get(archivo));
        byte[] byte2 = Base64.getEncoder().encode(bytet);
        return byte2;
    }
    //Función para decodificar
    public static void decodificar(byte[] base64S, String path) throws IOException {
        //Se decodifica el archivo mediante base64, creando una cadena de bytes y el archivo se crea en la ruta mandada
        byte[] normalBytet = Base64.getDecoder().decode(base64S);
        Files.write(Paths.get(path), normalBytet);
    }

    //Función para encriptar el archivo
    public static String encryptMessage(byte[] message, byte[] keyBytes)
            throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException {
        try {
            //Se inicializa el cipher con el método AES.
            Cipher cipher = Cipher.getInstance("AES");

            //Se crea una clave para AES a partir de la clave que le pasa por parámetro
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            
            //Se inicializa con la clave y el modo encriptar
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //devolvemos el String encriptado
            return Base64.getEncoder().encodeToString(cipher.doFinal(message));
            
            //manejo de excepciones
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException e) {
            return null;
        }
    }

    
    public static byte[] decryptMessage(String encryptedMessage, byte[] keyBytes)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        try {
            //El mensaje encriptado se descodifica en base64
            byte[] decode = Base64.getDecoder().decode(encryptedMessage);
            //Se inicializa el cipher con el método AES.
            Cipher cipher = Cipher.getInstance("AES");
            //Se crea una clave para AES a partir de la clave que le pasa por parámetro
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            //Se inicializa con la clave y el modo desencriptar
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //Se devuelve el archivo desencriptado
            return cipher.doFinal(decode);
            
           //Manejo de excepciones
        } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException e) {
            System.out.println(e);
            return null;
        }
    }
    
     public static String getAlphaNumericString() {

        // tamaño de 256 
        int n=16;
        byte[] array = new byte[128];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));

        // crear buffer para guardar resultado
        StringBuffer r = new StringBuffer();

        // Agrega los 16 primeros caracteres random
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);
            // Comprobación
            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9')
                    || (ch >= 35  && ch <= 38)
                    || (ch >= 40  && ch <= 43)
                    || (ch >= 40  && ch <= 43)
                    || (ch >= 45  && ch <= 46)
                    || (ch >= 58 && ch <= 64)
                    || (ch >= 93  && ch <= 95)
                    || (ch >= 123  && ch <= 126))

                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        // Devuelve el numero aleatorio
        return r.toString();
    }
     
     
     public static String encriptarRSA(byte[] claveAES,PublicKey llave) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
  
         //inicializamos el cifrador en modo encriptar
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, llave);
        //encriptamos y pasamos a String
        byte[] encrypted =cipher.doFinal(claveAES);
        String ClaveAESCodificada = Base64.getEncoder().encodeToString(encrypted);
     
        return  ClaveAESCodificada;
     }
     
      public static byte[] desencriptarRSA(String ClaveAESCodificada,PrivateKey llave) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        
        //inicializamos el cifrador en modo desencriptar
        byte[] decode = Base64.getDecoder().decode(ClaveAESCodificada);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, llave);
          //desncriptamos y pasamos a array de bytes
        byte[] AESdesencriptada =cipher.doFinal(decode);
       
        return AESdesencriptada;
     }

      //metodo para hacer un hash a una cadena
     public static String hash(String palabra){

        MessageDigest crypt;
        String hash="";
        try {
            //definimos metodo para hacer hash
            crypt = MessageDigest.getInstance("SHA3-256");
            crypt.update(palabra.getBytes(StandardCharsets.UTF_8));
            //Hacemos el hash
            byte[] bytes = crypt.digest();
            BigInteger bi = new BigInteger(1, bytes);
            hash = String.format("%0" + (bytes.length << 1) + "x", bi);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hash;
     }
      
}
