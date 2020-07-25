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
    
    //atributos getters
    private String successResult;
    private String ruc;
    private String razonSocial;
    private String tipoDePersona;
    private String condicion;
    private String nombreComercial;
    private String tipo;
    private String inscripcion;
    private String estado;
    private String direccion;
    private String sistemaEmision;
    private String actividadExterior;
    private String sistemContabilidad;
    private String emisionElectronica;
    private String ple;
    private String apiMsg;  

    //constructor
    public Sunat(String nruc) {
        this.nruc = nruc;
    }

    //getters y setters
    public void setNruc(String nruc) {
        this.nruc = nruc;  
    }
    
    public String getSuccessResult() {
        return successResult;
    }

    public String getRuc() {
        return ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getTipoDePersona() {
        return tipoDePersona;
    }

    public String getCondicion() {
        return condicion;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getTipo() {
        return tipo;
    }

    public String getInscripcion() {
        return inscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getSistemaEmision() {
        return sistemaEmision;
    }

    public String getActividadExterior() {
        return actividadExterior;
    }

    public String getSistemContabilidad() {
        return sistemContabilidad;
    }

    public String getEmisionElectronica() {
        return emisionElectronica;
    }

    public String getPle() {
        return ple;
    }

    public String getApiMsg() {
        return apiMsg;
    }

    //clase abstracta que deberá ser implementada
    public abstract Map<Integer, Map<String, String>> getRepLegales();
    
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
            
            int status = conexion.getResponseCode();
            
            //verifica que el estado de conexión sea exitoso - 200
            if (status > 299) {              
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
}
