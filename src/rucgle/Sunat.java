/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rucgle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Kokox (Jorge Aguilar)
 */
public abstract class Sunat implements ConstantesRucgle {
    
    //atributos de clase
    private static HttpURLConnection conexion;
    private BufferedReader lector;
    private String linea;
    private final StringBuilder respContenido = new StringBuilder();
    
    //atributos para el constructor
    private String nruc;

    //constructor
    public Sunat(String nruc) {
        this.nruc = nruc;
    }

    //getters y setters
    public String getNruc() {
        return nruc;
    }

    public void setNruc(String nruc) {
        this.nruc = nruc;  
    }
    
    //este metodo se conecta a la API, obtiene el JSON y lo pasa a String
    protected String getApiContent(){
        try{
            URL url = new URL(ENDPOINT_URL + NRUC_PARAM + this.nruc + NLIC_PARAM + LICENSE);
            conexion = (HttpURLConnection) url.openConnection();
            
            //solicita conexion por GET
            //timeout de conexion y lectura
            conexion.setRequestMethod("GET");
            conexion.setConnectTimeout(CONNECT_TIMEOUT);
            conexion.setReadTimeout(READ_TIMEOUT); 
            
            int estado = conexion.getResponseCode();
            
            //verifica que el estado de conexión sea exitoso - 200
            if (estado > 299) {              
                lector = new BufferedReader(new InputStreamReader(conexion.getErrorStream()));
                while((linea = lector.readLine()) != null){
                    respContenido.append(linea);
                }
                lector.close();
            } else {
                lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                while((linea = lector.readLine()) != null){
                    respContenido.append(linea);
                }
                lector.close();                
            }
            
            //retorno de contenido en el tipo StringBuilder y pasado a String
            return respContenido.toString();
            
        } catch (MalformedURLException e){
            e.printStackTrace(System.out);
        } catch (IOException ex) {
            Logger.getLogger(Sunat.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Hubo un error al intentar conectarse a la REST API. Por favor, verifique su conexión de Internet.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
        } finally {
            conexion.disconnect();
        }
        return null;
    }
    
    //clase abstracta que será implementada
    public abstract List<Map<Integer, Map<String, String>>> parse();
    
}
