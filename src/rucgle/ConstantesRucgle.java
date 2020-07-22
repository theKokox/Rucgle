/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rucgle;

/**
 *
 * @author Kokox (Jorge Aguilar)
 */
public interface ConstantesRucgle {
    
    //CONSTANTES PARA CONEXIÓN A REST API
    public static final String ENDPOINT_URL = "https://mobsu.codigo91.com/api/search/";
    public static final String NRUC_PARAM = "?nruc=";
    public static final String NLIC_PARAM = "&nlic=";
    public static final String LICENSE = "5b9ffcdd81c21";
    
    //TIEMPOS DE CONEXION Y LECTURA
    public static final int CONNECT_TIMEOUT = 8000;
    public static final int READ_TIMEOUT = 8000;  
}
