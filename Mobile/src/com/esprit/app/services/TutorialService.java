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
import com.esprit.app.entity.Tutorial;
import com.esprit.app.utils.DataSource;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat; 

public class TutorialService {
    public ArrayList<Tutorial> tutorial;

    public static CourseService instance = null;
    public boolean resultOk;
    private Tutorial tutorialclass = new Tutorial();
    private ConnectionRequest req;

    public TutorialService() {
	req = DataSource.getInstance().getRequest();
    }

    public boolean addTutorial(Tutorial t){
        String url = Statics.BASE_URL+"/tutorial/json/new";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("category",String.valueOf(t.getCategory()));
        req.addArgument("dateTuto",String.valueOf(t.getDateTuto()));
        req.addArgument("titre",String.valueOf(t.getTitre()));
        req.addArgument("image",String.valueOf(t.getImage()));
        req.addArgument("video",String.valueOf(t.getVideo()));
        req.addArgument("description", String.valueOf(t.getDescription()));
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
    
    public boolean updateTutorial(Tutorial t){
        String url = Statics.BASE_URL+"/tutorial/json/update/"+String.valueOf(t.getId());
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("category",String.valueOf(t.getCategory()));
        req.addArgument("image",String.valueOf(t.getImage()));
        req.addArgument("dateTuto",String.valueOf(t.getDateTuto()));
        req.addArgument("titre",String.valueOf(t.getTitre()));
        req.addArgument("video",String.valueOf(t.getVideo()));
        req.addArgument("description", String.valueOf(t.getDescription()));
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

    public ArrayList<Tutorial> parseTutorials(String jsonText) throws IOException{
        tutorial = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> tutorialsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)tutorialsListJson.get("root");
        for(Map<String,Object> obj : list){
            Tutorial t = new Tutorial();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            t.setId(id);
            String category = obj.get("category").toString();
            t.setCategory(category);
            String image = obj.get("image").toString();
            t.setImage(image);
            Map<String,Object> date_tuto = (Map<String,Object>)obj.get("dateTuto");
            t.setDateTuto(new Date((long)Float.parseFloat(date_tuto.get("timestamp").toString())*1000));
            String titre = obj.get("titre").toString();
            t.setTitre(titre);
            String video = obj.get("video").toString();
            t.setVideo(video);
            String description = obj.get("description").toString();
            t.setDescription(description);
            double prix = Double.parseDouble(obj.get("prix").toString());
            t.setPrix(prix);
            tutorial.add(t);
        }
        return tutorial;
    }
    
    public Tutorial parseTutorial(String jsonText) throws IOException{
        tutorial = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> tutorialsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        Tutorial t = new Tutorial();
        int id = (int)Float.parseFloat(tutorialsListJson.get("id").toString());
        t.setId(id);
        String category = tutorialsListJson.get("category").toString();
        t.setCategory(category);
        String image = tutorialsListJson.get("image").toString();
        t.setImage(image);
        Map<String,Object> date_tuto = (Map<String,Object>)tutorialsListJson.get("dateTuto");
        t.setDateTuto(new Date((long)Float.parseFloat(date_tuto.get("timestamp").toString())*1000));
        String titre = tutorialsListJson.get("titre").toString();
        t.setTitre(titre);
        String video = tutorialsListJson.get("video").toString();
        t.setVideo(video);
        String description = tutorialsListJson.get("description").toString();
        t.setDescription(description);
        double prix = Double.parseDouble(tutorialsListJson.get("prix").toString());
        t.setPrix(prix);
        tutorial.add(t);
        return t;
    }

    public ArrayList<Tutorial> getAllTutorials(){
        String url = Statics.BASE_URL+"/tutorial/json";
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
                    tutorial = parseTutorials(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tutorial;
    }
    
    public Tutorial getTutorial(int id){
        String url = Statics.BASE_URL+"/tutorial/json/"+id;
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
                    tutorialclass = parseTutorial(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tutorialclass;
    }

    public boolean deleteTutorial(int id){
        String url = Statics.BASE_URL+"/tutorial/json/delete/"+id;
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
