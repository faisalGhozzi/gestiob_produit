/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.user;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.User;
import com.esprit.app.services.UserService;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

public class UserForm extends Form{
    public ArrayList<User> users;
    @SuppressWarnings("unused")
    private Resources theme;
    
    public UserForm(Form previous, Resources res)throws IOException{
        super("Users List", GridLayout.autoFit());
        this.theme = theme;
        
        users = new UserService().getAllUsers();
        //this.add(new SpanLabel(new ProduitsService().getAllProducts().toString()));
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        for (User user : users) {
            MultiButton mb = new MultiButton(user.getEmail());
            mb.setTextLine2(user.getFirstname()+' '+user.getLastname());
            /*mb.addActionListener((evt) -> {
                new CourseDetailsForm(this, theme, course).show();
            });*/
            list.add(mb);
            /*img.addPointerReleasedListener((evt)->{
                   new ProductDetailsForm(this, theme,prod.getId()).show();

           });		*/
        }
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
        this.add(list);
        
    }
}
