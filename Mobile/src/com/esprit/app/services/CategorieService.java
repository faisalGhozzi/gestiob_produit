package com.esprit.app.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.esprit.app.entity.Categorie;
import com.esprit.app.utils.DataSource;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class CategorieService {
    public ArrayList<Categorie> categories;

    public static CategorieService instance = null;
    public boolean resultOk;
    public Categorie courseclass = new Categorie();
    private ConnectionRequest req;

    public CategorieService() {
	req = DataSource.getInstance().getRequest();
    }

    public boolean addCategorie(Categorie c){
        String url = Statics.BASE_URL+"/categorie/json/new";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("nom",String.valueOf(c.getNom()));
        req.addArgument("boiteVitesse",String.valueOf(c.getBoitevitesse()));
        InfiniteProgress prog = new InfiniteProgress();
        Dialog d = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(d);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
    public boolean updateCategorie(Categorie c){
        String url = Statics.BASE_URL+"/categorie/json/update/"+String.valueOf(c.getId());
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("nom",String.valueOf(c.getNom()));
        req.addArgument("boiteVitesse",String.valueOf(c.getBoitevitesse()));
        InfiniteProgress prog = new InfiniteProgress();
        Dialog d = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(d);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public ArrayList<Categorie> parseCategories(String jsonText) throws IOException, ParseException{
        categories = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> coursesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)coursesListJson.get("root");
        for(Map<String,Object> obj : list){
            Categorie c = new Categorie();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            c.setId(id);
            String nom = obj.get("nom").toString();
            c.setNom(nom);
            String boiteVitesse = obj.get("boiteVitesse").toString();
            c.setBoitevitesse(boiteVitesse);
            categories.add(c);
        }
        return categories;
    }
    
    public Categorie parseCategorie(String jsonText) throws IOException, ParseException{
        categories = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        Categorie c = new Categorie();
        int id = (int)Float.parseFloat(categoriesListJson.get("id").toString());
        c.setId(id);
        String nom = categoriesListJson.get("nom").toString();
        c.setNom(nom);
        String boiteVitesse = categoriesListJson.get("boiteVitesse").toString();
        c.setBoitevitesse(boiteVitesse);
        categories.add(c);
        return c;
    }

    public ArrayList<Categorie> getAllCategories(){
        String url = Statics.BASE_URL+"/categorie/json";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog d = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(d);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    categories = parseCategories(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
    
    public Categorie findCategorie(int id){
        String url = Statics.BASE_URL+"/categorie/json/"+id;
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog d = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(d);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    courseclass = parseCategorie(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return courseclass;
    }

    public boolean deleteCategorie(int id){
        String url = Statics.BASE_URL+"/categorie/json/delete/"+id;
        req.setUrl(url);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog d = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(d);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
}
