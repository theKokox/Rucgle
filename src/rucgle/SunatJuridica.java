/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rucgle;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Kokox (Jorge Aguilar)
 */
public class SunatJuridica extends Sunat {
    
    //declaramos el tipo JSONObject
    JSONObject o;  
    JSONObject usuario;
    
    //atributos
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
    
    //declaramos el tipo JSONArray
    private JSONArray repLegales;

    public SunatJuridica(String nruc) {
        super(nruc);
        //llamamos al método de conexión una sola vez en el constructor
        //con esto, consumismos el API una sola vez por solicitud
        //El JSONObject "o" recibirá toda la infor obtenida del API
        this.o = new JSONObject(super.getApiContent());
        this.usuario = o.getJSONObject("result");
    }
    
    //obtiene el estado de búsqueda - true o false
    @Override
    public String getSuccessResult() {
        this.successResult = Boolean.toString(usuario.getBoolean("successResult"));
        return successResult;
    }
    
    //obtiene el ruc
    @Override
    public String getRuc() {
        this.ruc = usuario.getString("ruc");
        return ruc;
    }

    //obtiene la razón social
    @Override
    public String getRazonSocial() {
        this.razonSocial = usuario.getString("razonSocial");
        return razonSocial;
    }

    //obtiene el tipo de persona
    @Override
    public String getTipoDePersona() {
        this.tipoDePersona = usuario.getString("tipoDePersona");
        return tipoDePersona;
    }

    //obtiene la condicion
    @Override
    public String getCondicion() {
        this.condicion = usuario.getString("condicion");
        return condicion;
    }

    @Override
    public String getNombreComercial() {
        this.nombreComercial = usuario.getString("nombreComercial");
        return nombreComercial;
    }

    //obtiene el tipo
    @Override
    public String getTipo() {
        this.tipo = usuario.getString("tipo");
        return tipo;
    }

    //obtiene la inscripcion
    @Override
    public String getInscripcion() {
        this.inscripcion = usuario.getString("inscripcion");
        return inscripcion;
    }

    //obtiene el estado
    @Override
    public String getEstado() {
        this.estado = usuario.getString("estado");
        return estado;
    }

    //obtiene la direccion
    @Override
    public String getDireccion() {
        this.direccion = usuario.getString("direccion");
        return direccion;
    }

    //obtiene el sistema de emision
    @Override
    public String getSistemaEmision() {
        this.sistemaEmision = usuario.getString("sistemaEmision");
        return sistemaEmision;
    }

    //obtiene la actividad exterior
    @Override
    public String getActividadExterior() {
        this.actividadExterior = usuario.getString("actividadExterior");
        return actividadExterior;
    }

    //obtiene el sistema de contabilidad
    @Override
    public String getSistemContabilidad() {
        this.sistemContabilidad = usuario.getString("sistemaContabilidad");
        return sistemContabilidad;
    }

    //obtiene la emision electrónica
    @Override
    public String getEmisionElectronica() {
        this.emisionElectronica = usuario.getString("emisionElectronica");
        return emisionElectronica;
    }

    //obtiene el ple
    @Override
    public String getPle() {
        this.ple = usuario.getString("ple");
        return ple;
    }

    //obtiene el mensaje de la API
    @Override
    public String getApiMsg() {
        this.apiMsg = usuario.getString("apiMsg");
        return apiMsg;
    }
    
    //obtiene los representantes legales
    @Override
    public Map<Integer, Map<String, String>> getRepLegales(){
        Map<Integer, Map<String, String>> nMapLegales = new HashMap<>();
        this.repLegales = usuario.getJSONArray("representantesLegales");
        
        for (int i = 0; i < repLegales.length(); i++){
            JSONObject rep = repLegales.getJSONObject(i);
            
            //por cada vuelta del bucle se creará un mapLegal con la siguiente estrucura
            Map<String, String> mapLegal = new HashMap<>(); 
            mapLegal.put("tipodoc", rep.getString("tipodoc"));
            mapLegal.put("numdoc", rep.getString("numdoc"));
            mapLegal.put("nombre", rep.getString("nombre"));
            mapLegal.put("cargo", rep.getString("cargo"));
            mapLegal.put("desde", rep.getString("desde"));

            //se almacenará en nMapLegales con su índice correspondiente
            nMapLegales.put(i, mapLegal);            
        }
        return nMapLegales;
    }
}
