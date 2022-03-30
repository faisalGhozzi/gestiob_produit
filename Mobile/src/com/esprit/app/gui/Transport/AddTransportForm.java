/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.Transport;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Categorie;
import com.esprit.app.entity.Transport;
import com.esprit.app.entity.User;
import com.esprit.app.services.CategorieService;
import java.io.IOException;
import com.esprit.app.services.TransportService;
import com.esprit.app.services.UserService;
import com.esprit.app.utils.Statics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddTransportForm extends Form{
    @SuppressWarnings("unused")
    private Resources theme;
    private TransportService ts = new TransportService();
    private Transport t = new Transport();
    
    public AddTransportForm(Form previous, Resources theme, int id, boolean add_renter){
        super("Transport Details",BoxLayout.y());
        if (id != 0){
            t = ts.getTransport(id);
        }
        Label imgName = new Label();
        Label vidName = new Label();
        
        Button uploadImage = new Button("Upload Image");
        Label lbl_Image = new Label();
        Button add = new Button(id == 0 ? "Create": "Update");     
        
        EncodedImage enc = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight()/3), true);
        String url = Statics.BASE_URL+"/uploads/produit_image/"+t.getImage();
        ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url, url));
        img.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        
        if(!add_renter){
            List<Categorie> categories = new CategorieService().getAllCategories();
            List<Map<String, Object>> entries = new ArrayList<>();
            for(Categorie categorie : categories){
                Map<String, Object> entry = new HashMap<>();
                entry.put(String.valueOf(categorie.getId()), categorie.getNom());
                entry.put("Line2", categorie.getNom());
                entries.add(entry);
            }
            ComboBox<Map<String, Object>> cb_category = new ComboBox<>(entries.toArray()); 
            cb_category.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
            TextField marque = new TextField(id == 0 ? "" : t.getMarque(), "Make");
            TextField model = new TextField(id == 0 ? "" : t.getModel(), "Model");
            TextField matricule = new TextField(id == 0 ? "" : t.getMatricule(), "Plates");
            TextField nbsiege = new TextField(id == 0 ? "" : String.valueOf(t.getNbsiege()), "Number of seats");
            TextField prix = new TextField(id == 0 ? "" : String.valueOf(t.getPrix()), "Price");
            
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    String selected = cb_category.getSelectedItem().entrySet().toArray()[0].toString();
                    int category_id = Integer.valueOf(selected.substring(0, selected.indexOf("=")));
                    Transport t = new Transport(matricule.getText(), marque.getText(), model.getText(), Integer.parseInt(nbsiege.getText()), "no_image.png", Float.parseFloat(prix.getText()) , category_id, -1);
                    if (id == 0){
                        ts.addTransport(t);
                    }else{
                        t.setId(id);
                        ts.updateTransport(t);
                    }   
                    try {
                        new TransportForm(previous, theme).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            addAll(new Label("Category"), cb_category, marque, model, matricule, nbsiege, prix, uploadImage, add);
        }else{
            List<User> users = new UserService().getAllUsers();
            List<Map<String, Object>> entries = new ArrayList<>();
            for(User user : users){
                Map<String, Object> entry = new HashMap<>();
                entry.put(String.valueOf(user.getId()), user.getNomComplet());
                entry.put("Line2", user.getNomComplet());
                entries.add(entry);
            }
            ComboBox<Map<String, Object>> cb_users = new ComboBox<>(entries.toArray());
            cb_users.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
            SpanLabel marque = new SpanLabel(t.getMarque());
            SpanLabel model = new SpanLabel(t.getModel());
            Categorie cat = new CategorieService().findCategorie(t.getCategorie());
            SpanLabel categorie = new SpanLabel(cat.getNom());
            SpanLabel boitevitesse = new SpanLabel(cat.getBoitevitesse());
            SpanLabel matricule = new SpanLabel(t.getMatricule());
            SpanLabel nbsiege = new SpanLabel(String.valueOf(t.getNbsiege()));
            SpanLabel prix = new SpanLabel(String.valueOf(t.getPrix()));    
            
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    String selected = cb_users.getSelectedItem().entrySet().toArray()[0].toString();
                    int user_id = Integer.valueOf(selected.substring(0, selected.indexOf("=")));
                    Transport nt = new Transport(id, matricule.getText(), marque.getText(), model.getText(), Integer.parseInt(nbsiege.getText()), t.getImage(), Float.parseFloat(prix.getText()) , t.getCategorie(), user_id);
                    ts.updateTransport(nt);       
                    try {
                        new TransportForm(previous, theme).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            addAll(img, new Label("Make : "), marque, new Label("Model : "), model,new Label("Category : "),new Label("Transmission : "), boitevitesse, categorie, new Label("Plates : "), matricule,new Label("Number of seats : "), nbsiege,new Label("Price : "), prix, cb_users, add);
        }
        
        uploadImage.addActionListener((evt) -> {
            String path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if(path != null){
                try {
                    Image img2 = Image.createImage(path);
                    lbl_Image.setIcon(img2);
                    imgName.setText(path);
                    this.revalidate();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            }
        });
        
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            try {
                new TransportForm(previous, theme).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
