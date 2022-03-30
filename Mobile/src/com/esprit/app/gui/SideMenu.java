package com.esprit.app.gui;

import java.io.IOException;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.esprit.app.gui.Transport.TransportForm;
import com.esprit.app.gui.categorie.CategorieForm;
import com.esprit.app.gui.user.UserForm;
import com.esprit.app.gui.wishlist.WishlistForm;

public abstract class SideMenu extends Form {
    public SideMenu(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenu(String title) {
        super(title);
    }

    public SideMenu() {
    }

    public SideMenu(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("user.png");
       // Image mask = res.getImage("round-mask.png");
       // mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        //profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("Hello User", profilePic, "SideMenuTitle");
       // profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        /*Label u = new Label("Hello User");
        sidemenuTop.add(BorderLayout.CENTER, u);*/
        
        sidemenuTop.setUIID("SidemenuTop");


        
        getToolbar().addComponentToSideMenu(sidemenuTop);
    
        getToolbar().addCommandToSideMenu("  Categories", null, e -> {
            try {
                new CategorieForm(this, res).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        getToolbar().addCommandToSideMenu("  Transport", null, e -> {
            try {
                new TransportForm(this, res).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        getToolbar().addCommandToSideMenu("  Wishlist", null, e -> {
            try {
                new WishlistForm(this, res).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        getToolbar().addCommandToSideMenu("  Logout", null,  e -> new SignInForm(res).show());
    }

    protected abstract void showOtherForm(Resources res);
}
