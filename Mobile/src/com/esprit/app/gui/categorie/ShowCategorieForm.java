/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.categorie;

import com.codename1.ui.Button;
import com.codename1.ui.util.Resources;
import com.codename1.components.SpanLabel;


import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.app.entity.Categorie;
import com.esprit.app.services.CategorieService;

public class ShowCategorieForm extends Form{
    @SuppressWarnings("unused")
    private Resources theme;
    private CategorieService cs = new CategorieService();
    private Categorie c = new Categorie();
    
    
    public ShowCategorieForm(Form previous,Resources theme,int id){
        super("Category Details",BoxLayout.y());
        c = new CategorieService().findCategorie(id);
        Button update = new Button("Update");

        SpanLabel nom = new SpanLabel("Nom : "+c.getNom());
        SpanLabel boitevitesse = new SpanLabel("Boite vitesse : "+c.getBoitevitesse());
        
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AddCategorieForm(previous, theme, c.getId()).show();
            }
        });
        
        this.addAll(nom, boitevitesse, update);
        
        


        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
        
        this.getToolbar().addCommandToRightBar("Delete", null , (evt) -> {
            cs.deleteCategorie(id);
            previous.showBack();
        });
    }
}
