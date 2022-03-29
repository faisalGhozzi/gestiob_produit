/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.tutorial;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Tutorial;
import com.esprit.app.gui.HomeForm;
import com.esprit.app.services.TutorialService;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

public class TutorialForm extends Form{
    public ArrayList<Tutorial> tutorials;
    @SuppressWarnings("unused")
    private Resources theme;
    
    public TutorialForm(Form previous, Resources res)throws IOException{
        super("Tutorials List", GridLayout.autoFit());
        this.theme = theme;
        this.revalidate();
        tutorials = new TutorialService().getAllTutorials();
		Container list = new Container(BoxLayout.y());
                list.setScrollableY(true);
                for (Tutorial tutorial : tutorials) {
                    MultiButton mb = new MultiButton(tutorial.getTitre());
                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                    Image i = URLImage.createToStorage(placeholder, tutorial.getImage() != null ? tutorial.getImage() : "no_image",tutorial.getImage() != null ? Statics.BASE_URL+"/uploads/"+tutorial.getImage() : Statics.BASE_URL+"/uploads/no_image.png");
                    mb.setIcon(i.fill(200, 200));
                    mb.setTextLine2(tutorial.getPrix() > 0 ? String.valueOf(tutorial.getPrix()) + " TND" : "FREE");
                    mb.addActionListener((evt) -> {
                        new ShowTutorialForm(this, theme, tutorial.getId()).show();
                    });
                    list.add(mb);
		}
                this.getToolbar().addCommandToRightBar("Add", null, (evt) -> {
                    new AddTutorialForm(this, theme, 0).show();
                });
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                    previous.show();
                });
                this.add(list);
        
    }
}
