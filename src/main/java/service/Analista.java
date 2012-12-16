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
import java.util.*;
import swing.ui.ModelField;


/**
 *
 * @author itakenami
 */
@XMLCast(thisClassFrom="models.Analista")
public class Analista implements DefaultModel<Analista> {
    
    @GridHeader(name="ID",size=10)
    public Long id;
    @GridHeader(name="Nome",size=200)
    public String nome;
    @GridHeader(name="Especialidade",size=50)
    public String especialidade;
    public Cargo cargo;
    
    //public static Service<Analista> service = new Service<Analista>(new ApacheRequest("http://localhost:9001/api/analistas"), Analista.class, new TypeToken<List<Analista>>(){}.getType());
    public static Service<Analista> service = new Service<Analista>(new ApacheRequest("http://gplayapi.herokuapp.com/api/analistas"), Analista.class, new TypeToken<List<Analista>>(){}.getType());
    //public static Service<Analista> service = new Service<Analista>(new ApacheRequest("http://localhost:8080/gplayee/api/analistas"), Analista.class, new TypeToken<List<Analista>>(){}.getType());
    
    
    
    @Override
    public String toString(){
        return nome;
    }

    @Override
    public List<Analista> findStart() {
        return new LinkedList<Analista>();
        
    }
    
    @Override
    public List<Analista> filterGrid(String filter) {
        
        List<Analista> lista = service.findAll();
        
        if("".equals(filter) || filter.equals("*")){
            return lista;
        }
        
        List<Analista> filtro = new ArrayList<Analista>();
        for (Analista analista : lista) {
            if(analista.nome.toUpperCase().startsWith(filter.toUpperCase())){
                filtro.add(analista);
            }
        }
        return filtro;
        
    }
    
    @Override
    public Analista save(Long id, HashMap<String, Object> map) throws ValidationException {
        HashMap<String, String> vo = new HashMap<String, String>();
        vo.put("analista.nome", map.get("Nome").toString());
        vo.put("analista.especialidade", map.get("Especialidade").toString());
        vo.put("analista.cargo.id", map.get("Cargo").toString());
        return service.save(id, vo);
    }
    
    @Override
    public boolean delete(Long id) {
        return service.delete(id);
    }
    
    @Override
    public Analista findById(Long id) {
        return service.findById(id);
    }

    @Override
    public ModelField getGridFields() {
        ModelField gf = new ModelField();
        gf.addField(id);
        gf.addField(nome);
        gf.addField(especialidade);
        return gf;
    }

    @Override
    public ModelField getViewFields() {
        ModelField ff = new ModelField();
        //ff.addField("ID", id);
        ff.addField("Nome", nome);
        ff.addField("Especialidade", especialidade);
        ff.addField("Cargo", cargo, ModelField.COMBO);
        return ff;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public List<? extends DefaultModel> getObj(String campo) {
        if(campo.equals("Cargo")){
            return Cargo.service.findAll();
        }
        return null;
    }

    
}
