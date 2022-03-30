/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.esprit.app.entity.Transport;
import com.esprit.app.utils.DataSource;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TransportService {
    public ArrayList<Transport> transport;

    public static TransportService instance = null;
    public boolean resultOk;
    private Transport transportclass = new Transport();
    private ConnectionRequest req;

    public TransportService() {
	req = DataSource.getInstance().getRequest();
    }

    public boolean addTransport(Transport t){
        String url = Statics.BASE_URL+"/transport/json/new";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("matricule",String.valueOf(t.getMatricule()));
        req.addArgument("marque",String.valueOf(t.getMarque()));
        req.addArgument("modele",String.valueOf(t.getModel()));
        req.addArgument("nbsiege",String.valueOf(t.getNbsiege()));
        req.addArgument("image",String.valueOf(t.getImage()));
        req.addArgument("categorie", String.valueOf(t.getCategorie()));
        req.addArgument("user", String.valueOf(t.getUser()));
        req.addArgument("prix", String.valueOf(t.getPrix()));

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
    
    public boolean updateTransport(Transport t){
        String url = Statics.BASE_URL+"/transport/json/update/"+String.valueOf(t.getId());
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("matricule",String.valueOf(t.getMatricule()));
        req.addArgument("marque",String.valueOf(t.getMarque()));
        req.addArgument("modele",String.valueOf(t.getModel()));
        req.addArgument("nbsiege",String.valueOf(t.getNbsiege()));
        req.addArgument("image",String.valueOf(t.getImage()));
        req.addArgument("categorie", String.valueOf(t.getCategorie()));
        req.addArgument("user", String.valueOf(t.getUser()));
        req.addArgument("prix", String.valueOf(t.getPrix()));
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

    public ArrayList<Transport> parseTransports(String jsonText) throws IOException{
        transport = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> transportsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)transportsListJson.get("root");
        for(Map<String,Object> obj : list){
            Transport t = new Transport();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            t.setId(id);
            String marque = obj.get("marque").toString();
            t.setMarque(marque);
            String matricule = obj.get("matricule").toString();
            t.setMatricule(matricule);
            String model = obj.get("modele").toString();
            t.setModel(model);
            String image = obj.get("image").toString();
            t.setImage(image);
            Map<String,Object> categorie = (Map<String,Object>)obj.get("categorie");
            t.setCategorie((int)Float.parseFloat(categorie.get("id").toString()));
            Map<String,Object> user = (Map<String,Object>)obj.get("user");
            if(user == null){
                t.setUser(-1);
            }else{
                t.setUser((int)Float.parseFloat(user.get("id").toString()));
            }
            float prix = Float.parseFloat(obj.get("prix").toString());
            t.setPrix(prix);
            int nbsiege = (int)Float.parseFloat(obj.get("nbsiege").toString());
            t.setNbsiege(nbsiege);
            transport.add(t);
        }
        return transport;
    }
    
    public Transport parseTransport(String jsonText) throws IOException{
        transport = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> transportsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        Transport t = new Transport();
        int id = (int)Float.parseFloat(transportsListJson.get("id").toString());
        t.setId(id);
        String marque = transportsListJson.get("marque").toString();
        t.setMarque(marque);
        String matricule = transportsListJson.get("matricule").toString();
        t.setMatricule(matricule);
        String model = transportsListJson.get("modele").toString();
        t.setModel(model);
        String image = transportsListJson.get("image").toString();
        t.setImage(image);
        Map<String,Object> categorie = (Map<String,Object>)transportsListJson.get("categorie");
        t.setCategorie((int)Float.parseFloat(categorie.get("id").toString()));
        Map<String,Object> user = (Map<String,Object>)transportsListJson.get("user");
        if(user == null){
            t.setUser(-1);
        }else{
            t.setUser((int)Float.parseFloat(user.get("id").toString()));
        }
        float prix = Float.parseFloat(transportsListJson.get("prix").toString());
        t.setPrix(prix);
        int nbsiege = (int)Float.parseFloat(transportsListJson.get("nbsiege").toString());
        t.setNbsiege(nbsiege);
        transport.add(t);
        return t;
    }

    public ArrayList<Transport> getAllTransports(){
        String url = Statics.BASE_URL+"/transport/json";
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
                    transport = parseTransports(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return transport;
    }
    
    public Transport getTransport(int id){
        String url = Statics.BASE_URL+"/transport/json/"+id;
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
                    transportclass = parseTransport(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return transportclass;
    }

    public boolean deleteTransport(int id){
        String url = Statics.BASE_URL+"/transport/json/delete/"+id;
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
