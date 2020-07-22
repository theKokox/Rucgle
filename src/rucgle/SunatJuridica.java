/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rucgle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public SunatJuridica(String nruc) {
        super(nruc);
        //llamamos al método de conexión una sola vez en el constructor
        //con esto, consumismos el API una sola vez por solicitud
        //El JSONObject "o" recibirá toda la infor obtenida del API
        this.o = new JSONObject(super.getApiContent()); 
    }

    //Con el método "parse", cramos un List que alojará a dos interfaces Map
    //El método recibe la información el JSONObject "o" y la trata
    //La persona jurídica tiene algunos campos más que la persona natural
    @Override
    public List<Map<Integer, Map<String, String>>> parse() {   
        
        //Se crea una lista acorde al método abstracto de la clase Sunat
        List<Map<Integer, Map<String, String>>> data = new ArrayList<>();
        //Se crea una interface Map para estructurar los datos principales de la empresa
        Map<String, String> mapInfoEmp = new HashMap<>();
        //Se crea una inteface Map que recibirá un integral como clave
        Map<Integer, Map<String, String>> nMapInfoEmpresa = new HashMap<>();
        
        //Crea un tipo JSONObject usuario de la data obtenida en "o"
        JSONObject usuario = o.getJSONObject("result");              
        
        //Este Map recibe dos tipos de datos, ambos String
        if (usuario.getBoolean("successResult") == true){
            //Crea un tipo JSONArray repLegales del nodo "representantesLegales"
            JSONArray repLegales = usuario.getJSONArray("representantesLegales");             
            
            mapInfoEmp.put("successResult", Boolean.toString(usuario.getBoolean("successResult")));
            mapInfoEmp.put("ruc", usuario.getString("ruc"));
            mapInfoEmp.put("razonSocial", usuario.getString("razonSocial"));
            mapInfoEmp.put("tipoDePersona", usuario.getString("tipoDePersona"));
            mapInfoEmp.put("condicion", usuario.getString("condicion"));
            mapInfoEmp.put("nombreComercial", usuario.getString("nombreComercial"));
            mapInfoEmp.put("tipo", usuario.getString("tipo"));
            mapInfoEmp.put("inscripcion", usuario.getString("inscripcion"));
            mapInfoEmp.put("estado", usuario.getString("estado"));
            mapInfoEmp.put("direccion", usuario.getString("direccion"));
            mapInfoEmp.put("sistemaEmision", usuario.getString("sistemaEmision"));
            mapInfoEmp.put("actividadExterior", usuario.getString("actividadExterior"));
            mapInfoEmp.put("sistemaContabilidad", usuario.getString("sistemaContabilidad"));
            mapInfoEmp.put("emisionElectronica", usuario.getString("emisionElectronica"));
            mapInfoEmp.put("ple", usuario.getString("ple"));
            
            //Como valor recibirá otro Map (el que creamos previamente)
            //Esto se hace cona finalidad de que calce con el tipo de devolución del método abstracto           
            nMapInfoEmpresa.put(0, mapInfoEmp);


            //Los representantes legales pueden de 0 o más
            //Los representantes legales se alojan en un tipo JSONArray
            //Para esto, se crea una interface Map que recibirá como clave un Integer
            //Como valor recibirá otro Map que se creará a través de un bucle "for"
            Map<Integer, Map<String, String>> nMapLegales = new HashMap<>();

            //Se obtiene la cantidad de rep legales y se hace un bucle
            for (int i = 0; i < repLegales.length(); i++){
                JSONObject rep = repLegales.getJSONObject(i); //Se crea un JSONObject por cada nodo del array

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

            //Se agregan ambos Maps a la Lista
            data.add(0, nMapInfoEmpresa);
            data.add(1, nMapLegales);
        } else {
            mapInfoEmp.put("successResult", Boolean.toString(usuario.getBoolean("successResult")));
            mapInfoEmp.put("apiMsg", usuario.getString("apiMsg"));
            nMapInfoEmpresa.put(0, mapInfoEmp);
            
            //Se agrega mapInfoEmpresa a la ista
            data.add(0, nMapInfoEmpresa);
        }
        
        return data;        
    }   
}
