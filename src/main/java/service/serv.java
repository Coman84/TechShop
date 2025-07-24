package service;

import domain.cumparaturi;
import repo.DuplicateIDException;
import repo.MemoryRepository;
import repo.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class serv {
    private MemoryRepository<cumparaturi>listaCumparaturi;
    public serv(MemoryRepository<cumparaturi>listaCumparaturi) {
        this.listaCumparaturi = listaCumparaturi;
    }
    public void add(cumparaturi c) throws DuplicateIDException, RepositoryException {
        listaCumparaturi.add(c);
    }
    public void remove(int id) throws RepositoryException {
        listaCumparaturi.remove(id);
    }
    public cumparaturi get(int id) throws RepositoryException {
        return listaCumparaturi.find(id);
    }
    public List<cumparaturi> getAll() throws RepositoryException {
        return new ArrayList<>(listaCumparaturi.getAll());
    }
    public String aff() throws RepositoryException {
        String rez="";
        for(cumparaturi i:listaCumparaturi.getAll()){
            rez=rez+i.toString()+"\n";
        }
        return rez;
    }
    public String crescator() throws RepositoryException {
        String rez="";
        List<cumparaturi>lista=new ArrayList<>(listaCumparaturi.getAll());
        for(int i=0;i<lista.size();i++){
            for(int j=i+1;j<lista.size();j++){
                if(lista.get(i).getId()>lista.get(j).getId()){
                    cumparaturi c=lista.get(i);
                    lista.set(i,lista.get(j));
                    lista.set(j,c);
                }
            }
        }
        for(cumparaturi i:lista){
            if(i.getCantitate()==0){
                rez=rez+i.getId()+","+i.getMarca()+","+i.getNume()+","+i.getPret()+"n/a"+"\n";
            }
            else
                rez=rez+i.toString()+"\n";
        }
        return rez;
    }
    public String Filtrare(String p1,String p2) throws RepositoryException {
        String rez="";
        if(p1==null && p2==null) {
            rez=aff();
            return rez;
        }
        if(p1==null) {
            int p=Integer.parseInt(p1);
            for(cumparaturi i:listaCumparaturi.getAll()){
                if(i.getPret()>=p){
                    rez=rez+i.toString()+"\n";
                }
            }
            return rez;
        }
        if(p2==null) {
            int pp=Integer.parseInt(p2);
            for(cumparaturi i:listaCumparaturi.getAll()){
                if(i.getPret()<=pp){
                    rez=rez+i.toString()+"\n";
                }
            }
            return rez;
        }
        int p=Integer.parseInt(p1);
        int pp=Integer.parseInt(p2);
        for(cumparaturi i:listaCumparaturi.getAll()){
            if(i.getPret()>=p && i.getPret()<=pp){
                rez=rez+i.toString()+"\n";
            }
        }
        return rez;
    }

    public void scadere(int id) throws RepositoryException {
        cumparaturi c=listaCumparaturi.find(id);
        listaCumparaturi.update(new cumparaturi(id,c.getMarca(),c.getNume(),c.getPret(),c.getCantitate()-1));
    }
    public int id_next() throws RepositoryException {
        int m=0;
        for(cumparaturi c:listaCumparaturi.getAll()){
            if(c.getId()>m){
                m=c.getId();
            }
        }
        return m;
    }

    public String filtrare2(String search) throws RepositoryException {
        String rez="";
        for(cumparaturi c:listaCumparaturi.getAll()){
            if(c.getNume().contains(search) || c.getMarca().contains(search)){
                rez=rez+c.toString()+"\n";
            }
        }
        return rez;
    }
}
