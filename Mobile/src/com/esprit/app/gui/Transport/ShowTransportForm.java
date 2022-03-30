/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.Transport;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Categorie;
import com.esprit.app.entity.Transport;
import com.esprit.app.entity.User;
import com.esprit.app.entity.Wishlist;
import com.esprit.app.gui.wishlist.WishlistForm;
import com.esprit.app.services.TransportService;
import com.esprit.app.services.UserService;
import com.esprit.app.services.CategorieService;
import com.esprit.app.services.WishlistService;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

public class ShowTransportForm extends Form{
    @SuppressWarnings("unused")
    private Resources theme;
    private TransportService ts = new TransportService();
    private UserService us = new UserService();
    private CategorieService cs = new CategorieService();
    private WishlistService ws = new WishlistService();
    private ArrayList<Wishlist> wishlists;
    private Transport t = new Transport();
    private boolean itemExists = false;
    private int id_if_exists;
    
    
    public ShowTransportForm(Form previous,Resources theme,int id){
        super("Transport Details",BoxLayout.y());
        t = new TransportService().getTransport(id);
        
        // Verify if this product is in current user's wishlist
        wishlists = ws.getAllWishlists(1); // change with current user
        for(Wishlist wishlist: wishlists){
            if (id == wishlist.getTransport_id()){
                itemExists = true;
                id_if_exists = wishlist.getId();
                break;
            }
        }
        Button update = new Button("Update");
        Button wishlist = new Button(itemExists ? "Remove from wishlist" : "Add to wishlist");
        Button rent = new Button(t.getUser() == -1 ? "Rent car" : "Unrent car");
        EncodedImage enc = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight()/3), true);
        String url = Statics.BASE_URL+"/uploads/produit_image/"+t.getImage();
        ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url, url));
        img.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Categorie cat = cs.findCategorie(t.getCategorie());
        
        SpanLabel marque = new SpanLabel("Make \t: "+t.getMarque());
        SpanLabel modele = new SpanLabel("Model \t: "+t.getModel());
        SpanLabel matricule = new SpanLabel("Plate \t: "+t.getMatricule());
        SpanLabel nbsieges = new SpanLabel("Seats \t: "+String.valueOf(t.getNbsiege()));
        SpanLabel price = new SpanLabel("Price \t: "+String.valueOf(t.getPrix()));
        SpanLabel user = new SpanLabel(t.getUser() == -1 ? "Not rented yet!" : "Renter \t: "+us.getUser(t.getUser()).getNomComplet());
        SpanLabel categorie = new SpanLabel("Category \t: "+cat.getNom());
        SpanLabel boitevitesse = new SpanLabel("Speed \t: "+cat.getBoitevitesse());


        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AddTransportForm(previous, theme, t.getId(), false).show();
            }
        });
        
        rent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(t.getUser() == -1){
                    new AddTransportForm(previous, theme, t.getId(), true).show();
                }else{
                    t.setUser(-1);
                    ts.updateTransport(t);
                    new ShowTransportForm(previous, theme, id).show();
                }
            }
        });
        
        wishlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (itemExists){
                    ws.deleteWishlist(id_if_exists);
                }else{
                    Wishlist w = new Wishlist();
                    w.setTransport_id(t.getId());
                    w.setUser_id(1); // change with current user id
                    ws.addWishlist(w);
                }
                try {
                    new WishlistForm(previous, theme).show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        this.addAll(img, marque, modele, matricule, nbsieges, price, user, categorie, boitevitesse, update, rent, wishlist);
        
        


        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
        
        this.getToolbar().addCommandToRightBar("Delete", null , (evt) -> {
            cs.deleteCategorie(id);
            previous.showBack();
        });
    }
}
