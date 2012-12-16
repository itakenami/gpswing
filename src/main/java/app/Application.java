package app;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import swing.ui.DefaultList;
import service.Analista;
import service.Cargo;
import service.Projeto;

/**
 *
 * @author itakenami
 */
public class Application {
    
    private static Application instance;
    public DefaultList listAnalista;
    public DefaultList listCargo;
    public DefaultList listProjeto;
    public Main main;
    
    private Application(){
        main = new Main();
        listCargo = new DefaultList(main,new Cargo());
        listAnalista = new DefaultList(main,new Analista());
        listProjeto = new DefaultList(main,new Projeto());
        
    }
    
    public static Application getInstance(){
        if(instance==null){
            instance = new Application();
        }
        return instance;
    }
    
}
