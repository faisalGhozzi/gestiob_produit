/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.categorie;

import com.codename1.components.MultiButton;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Categorie;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.app.services.CategorieService;
import java.io.IOException;
import java.util.ArrayList;

public class CategorieForm extends Form{
    public ArrayList<Categorie> categories;
    @SuppressWarnings("unused")
    private Resources theme;
    
    public CategorieForm(Form previous, Resources res)throws IOException{
        super("Categories List", GridLayout.autoFit());
        this.theme = theme;
        categories = new CategorieService().getAllCategories();
		Container list = new Container(BoxLayout.y());
                list.setScrollableY(true);
                for (Categorie categorie : categories) {
                    MultiButton mb = new MultiButton(categorie.getNom());
                    mb.setTextLine2(categorie.getBoitevitesse());
                    mb.addActionListener((evt) -> {
                        new ShowCategorieForm(this.getComponentForm(), theme, categorie.getId()).show();
                    });
                    list.add(mb);
		}
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                    previous.showBack();
                });
                this.getToolbar().addCommandToRightBar("New", null , (evt) -> {
                    new AddCategorieForm(this.getComponentForm(), theme, 0).show();
                });
                this.add(list);
        
    }    
}
