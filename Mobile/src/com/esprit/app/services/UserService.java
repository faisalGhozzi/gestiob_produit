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
import com.esprit.app.entity.User;
import com.esprit.app.utils.DataSource;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {
    public ArrayList<User> users;

    public static CourseService instance = null;
    public boolean resultOk;
    private ConnectionRequest req;

    public UserService() {
	req = DataSource.getInstance().getRequest();
    }

    public boolean addCourse(User u){
        String url = Statics.BASE_URL+"/users/json/new";
        req.setUrl(url);
        req.addArgument("id",String.valueOf(u.getId()));
        req.addArgument("email",String.valueOf(u.getEmail()));
        req.addArgument("firstname",String.valueOf(u.getFirstname()));
        req.addArgument("lastname",String.valueOf(u.getLastname()));
        req.addArgument("verified",String.valueOf(u.getIsVerified()));
        req.addArgument("password",String.valueOf(u.getPassword()));
        //req.addArgument("roles", );
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

    public ArrayList<User> parseUser(String jsonText) throws IOException{
        users = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)usersListJson.get("root");
        for(Map<String,Object> obj : list){
            System.out.println("started parsing ");
            User u = new User();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            u.setId(id);
            String email = obj.get("email").toString();
            u.setEmail(email);
            String password = obj.get("password").toString();
            u.setPassword(password);
            String firstname = obj.get("firstname").toString();
            u.setFirstname(firstname);
            String lastname = obj.get("lastname").toString();
            u.setLastname(lastname);
            boolean verified = Boolean.parseBoolean(obj.get("verified").toString());
            u.setIsVerified(verified);
            users.add(u);
        }
        System.out.println("parsed");
        return users;
    }

    public ArrayList<User> getAllUsers(){
        String url = Statics.BASE_URL+"/users/json";
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
                    users = parseUser(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }

    public boolean deleteUser(int id){
        String url = Statics.BASE_URL+"/users/json/delete/"+id;
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
