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
import com.esprit.app.entity.Course;
import com.esprit.app.utils.DataSource;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;


public class CourseService {
    public ArrayList<Course> course;

    public static CourseService instance = null;
    public boolean resultOk;
    public Course courseclass = new Course();
    private ConnectionRequest req;

    public CourseService() {
	req = DataSource.getInstance().getRequest();
    }

    public boolean addCourse(Course c){
        String url = Statics.BASE_URL+"/formation/json/new";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("price",String.valueOf(c.getPrice()));
        req.addArgument("mode",String.valueOf(c.getMode()));
        req.addArgument("dateDebut",String.valueOf(c.getDateDebut()));
        req.addArgument("dateFin",String.valueOf(c.getDateFin()));
        req.addArgument("duree",String.valueOf(c.getDuree()));
        req.addArgument("description", String.valueOf(c.getDescription()));
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
    
    public boolean updateCourse(Course c){
        String url = Statics.BASE_URL+"/formation/json/update/"+String.valueOf(c.getId());
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("price",String.valueOf(c.getPrice()));
        req.addArgument("mode",String.valueOf(c.getMode()));
        req.addArgument("dateDebut",String.valueOf(c.getDateDebut()));
        req.addArgument("dateFin",String.valueOf(c.getDateFin()));
        req.addArgument("duree",String.valueOf(c.getDuree()));
        req.addArgument("description", String.valueOf(c.getDescription()));
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

    public ArrayList<Course> parseCourses(String jsonText) throws IOException, ParseException{
        course = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> coursesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)coursesListJson.get("root");
        for(Map<String,Object> obj : list){
            Course c = new Course();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            c.setId(id);
            int price = (int)Float.parseFloat(obj.get("price").toString());
            c.setPrice(price);
            Map<String,Object> dateDebut = (Map<String,Object>)obj.get("dateDebut");
            c.setDateDebut(new Date((long)Float.parseFloat(dateDebut.get("timestamp").toString())*1000));
            Map<String,Object> dateFin = (Map<String,Object>)obj.get("dateFin");
            c.setDateFin(new Date((long)Float.parseFloat(dateFin.get("timestamp").toString())*1000));
            String duree = obj.get("duree").toString();
            c.setDuree(duree);
            String description = obj.get("description").toString();
            c.setDescription(description);
            String mode = obj.get("mode").toString();
            c.setMode(mode);
            course.add(c);
        }
        return course;
    }
    
    public Course parseCourse(String jsonText) throws IOException, ParseException{
        course = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> coursesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        Course c = new Course();
        int id = (int)Float.parseFloat(coursesListJson.get("id").toString());
        c.setId(id);
        int price = (int)Float.parseFloat(coursesListJson.get("price").toString());
        c.setPrice(price);
        Map<String,Object> dateDebut = (Map<String,Object>)coursesListJson.get("dateDebut");
        c.setDateDebut(new Date((long)Float.parseFloat(dateDebut.get("timestamp").toString())*1000));
        Map<String,Object> dateFin = (Map<String,Object>)coursesListJson.get("dateFin");
        c.setDateFin(new Date((long)Float.parseFloat(dateFin.get("timestamp").toString())*1000));
        String duree = coursesListJson.get("duree").toString();
        c.setDuree(duree);
        String description = coursesListJson.get("description").toString();
        c.setDescription(description);
        String mode = coursesListJson.get("mode").toString();
        c.setMode(mode);
        course.add(c);
        return c;
    }

    public ArrayList<Course> getAllCourses(){
        String url = Statics.BASE_URL+"/formation/json";
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
                    course = parseCourses(new String(req.getResponseData()));
                }catch(IOException ex){
                    ex.printStackTrace();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return course;
    }
    
    public Course findCourse(int id){
        String url = Statics.BASE_URL+"/formation/json/"+id;
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
                    courseclass = parseCourse(new String(req.getResponseData()));
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

    public boolean deleteCourse(int id){
        String url = Statics.BASE_URL+"/formation/json/delete/"+id;
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
