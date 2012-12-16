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
@XMLCast(thisClassFrom="models.Projeto")
public class Projeto implements DefaultModel<Projeto>{
    
    @GridHeader(name="ID",size=10)
    public Long id;
    @GridHeader(name="Nome",size=100)
    public String nome;
    public String descricao;
    @GridHeader(name="Data Início",size=50)
    public Date data_inicio;
    @GridHeader(name="Data Fim",size=50)
    public Date data_fim;
    public Set<Analista> analistas;
   
    //public static Service<Projeto> service = new Service<Projeto>(new ApacheRequest("http://localhost:9001/api/projetos"), Projeto.class, new TypeToken<List<Projeto>>(){}.getType());
    public static Service<Projeto> service = new Service<Projeto>(new ApacheRequest("http://gplayapi.herokuapp.com/api/projetos"), Projeto.class, new TypeToken<List<Projeto>>(){}.getType());
    //public static Service<Projeto> service = new Service<Projeto>(new ApacheRequest("http://localhost:8080/gplayee/api/projetos"), Projeto.class, new TypeToken<List<Projeto>>(){}.getType());
    
    @Override
    public String toString(){
        return nome;
    }

    @Override
    public List<Projeto> findStart() {
        //return service.findAll();
        return new LinkedList<Projeto>();
    }
    
    @Override
    public Projeto findById(Long id) {
        return service.findById(id);
    }
    
    @Override
    public Projeto save(Long id, HashMap<String, Object> map) throws ValidationException {
        
        HashMap<String, String> vo = new HashMap<String, String>();
        
        vo.put("projeto.nome", map.get("Nome").toString());
        vo.put("projeto.descricao", map.get("Descrição").toString());
        vo.put("projeto.data_inicio", map.get("Data Inicio").toString());
        vo.put("projeto.data_fim", map.get("Data Fim").toString());
        
        
        String[] sel = (String[]) map.get("Analistas");
        
        for(int x=0;x<sel.length;x++){
            vo.put("projeto.analistas["+sel[x]+"].id", sel[x]);
        }
        
        return service.save(id, vo);
    }

    @Override
    public ModelField getGridFields() {
        ModelField gf = new ModelField();
        gf.addField(id);
        gf.addField(nome);
        
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        
        gf.addField(sdf.format(data_inicio));
        gf.addField(sdf.format(data_fim));
        return gf;
    }

    @Override
    public ModelField getViewFields() {
        ModelField ff = new ModelField();
        //ff.addField("ID", id);
        ff.addField("Nome", nome);
        ff.addField("Descrição", descricao);
        ff.addField("Data Inicio", data_inicio, ModelField.DATE);
        ff.addField("Data Fim", data_fim, ModelField.DATE);
        ff.addField("Analistas", analistas, ModelField.LISTBOX);
        return ff;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public List<? extends DefaultModel> getObj(String campo) {
        if(campo.equals("Analistas")){
            return Analista.service.findAll();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return service.delete(id);
    }

    @Override
    public List<Projeto> filterGrid(String filter) {
        
        List<Projeto> lista = service.findAll();
        
        if("".equals(filter) || filter.equals("*")){
            return lista;
        }
        
        List<Projeto> filtro = new ArrayList<Projeto>();
        
        for (Projeto projeto : lista) {
            if(projeto.nome.toUpperCase().startsWith(filter.toUpperCase())){
                filtro.add(projeto);
            }
        }
        return filtro;
        
    }

}
