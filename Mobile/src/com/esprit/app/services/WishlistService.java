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
import com.esprit.app.entity.Wishlist;
import com.esprit.app.utils.DataSource;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WishlistService {
    public ArrayList<Wishlist> wishlists;

    public static WishlistService instance = null;
    public boolean resultOk;
    private Wishlist wishlistclass = new Wishlist();
    private ConnectionRequest req;

    public WishlistService() {
	req = DataSource.getInstance().getRequest();
    }

    public boolean addWishlist(Wishlist t){
        String url = Statics.BASE_URL+"/wishlist/json/user/new";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("user", String.valueOf(t.getUser_id()));
        req.addArgument("transport", String.valueOf(t.getTransport_id()));
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

    public ArrayList<Wishlist> parseWishlists(String jsonText) throws IOException{
        wishlists = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> wishlistsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)wishlistsListJson.get("root");
        for(Map<String,Object> obj : list){
            Wishlist w = new Wishlist();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            w.setId(id);
            Map<String,Object> user = (Map<String,Object>)obj.get("user");
            w.setUser_id((int)Float.parseFloat(user.get("id").toString()));
            Map<String,Object> transport = (Map<String,Object>)obj.get("transport");
            w.setTransport_id((int)Float.parseFloat(transport.get("id").toString()));
            wishlists.add(w);
        }
        return wishlists;
    }
    
    public Wishlist parseWishlist(String jsonText) throws IOException{
        JSONParser j = new JSONParser();
        Map<String,Object> transportsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        Wishlist w = new Wishlist();
        int id = (int)Float.parseFloat(transportsListJson.get("id").toString());
        w.setId(id);
        Map<String,Object> user = (Map<String,Object>)transportsListJson.get("user");
        w.setUser_id((int)Float.parseFloat(user.get("id").toString()));
        Map<String,Object> transport = (Map<String,Object>)transportsListJson.get("transport");
        w.setUser_id((int)Float.parseFloat(transport.get("id").toString()));
        return w;
    }

    public ArrayList<Wishlist> getAllWishlists(int user_id){
        String url = Statics.BASE_URL+"/wishlist/json/user/"+user_id;
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
                    wishlists = parseWishlists(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return wishlists;
    }
    
    public Wishlist getWishlist(int id){
        String url = Statics.BASE_URL+"/wishlist/json/"+id;
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
                    wishlistclass = parseWishlist(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return wishlistclass;
    }

    public boolean deleteWishlist(int id){
        String url = Statics.BASE_URL+"/wishlist/json/delete/"+id;
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
