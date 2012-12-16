/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author itakenami
 */
import swing.model.DefaultModel;
import swing.annotation.GridHeader;
import api.wadl.annotation.XMLCast;
import client.crud.Service;
import client.exception.ValidationException;
import client.request.ApacheRequest;
import client.request.SimpleRequest;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import swing.ui.ModelField;

/**
 *
 * @author itakenami
 */
@XMLCast(thisClassFrom = "models.Cargo")
public class Cargo implements DefaultModel<Cargo> {

    @GridHeader(name = "ID", size = 10)
    public Long id;
    @GridHeader(name = "Nome", size = 300)
    public String nome;
    
    //public static Service<Cargo> service = new Service<Cargo>(new ApacheRequest("http://localhost:9001/api/cargos"), Cargo.class, new TypeToken<List<Cargo>>() {}.getType());
    public static Service<Cargo> service = new Service<Cargo>(new ApacheRequest("http://gplayapi.herokuapp.com/api/cargos"), Cargo.class, new TypeToken<List<Cargo>>() {}.getType());
    //public static Service<Cargo> service = new Service<Cargo>(new ApacheRequest("http://localhost:8080/gplayee/api/cargos"), Cargo.class, new TypeToken<List<Cargo>>() {}.getType());

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public List<Cargo> findStart() {
        //return service.findAll();
        return new LinkedList<Cargo>();
    }
    
    @Override
    public List<Cargo> filterGrid(String filter) {
        
        List<Cargo> lista = service.findAll();
        
        if("".equals(filter) || filter.equals("*")){
            return lista;
        }
        
        List<Cargo> filtro = new ArrayList<Cargo>();
        for (Cargo cargo : lista) {
            if(cargo.nome.toUpperCase().startsWith(filter.toUpperCase())){
                filtro.add(cargo);
            }
        }
        return filtro;
        
    }

    @Override
    public Cargo findById(Long id) {
        return service.findById(id);
    }

    @Override
    public Cargo save(Long id, HashMap<String, Object> map) throws ValidationException {
        HashMap<String, String> vo = new HashMap<String, String>();
        vo.put("cargo.nome", map.get("Nome").toString());
        return service.save(id, vo);
    }

    @Override
    public boolean delete(Long id) {
        return service.delete(id);
    }

    @Override
    public ModelField getGridFields() {
        ModelField gf = new ModelField();
        gf.addField(id);
        gf.addField(nome);
        return gf;
    }

    @Override
    public ModelField getViewFields() {
        ModelField ff = new ModelField();
        ff.addField("Nome", nome);
        return ff;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public List<? extends DefaultModel> getObj(String campo) {
        return null;
    }
}
