/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.wishlist;

import com.codename1.components.MultiButton;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.app.entity.Transport;
import com.esprit.app.entity.User;
import com.esprit.app.entity.Wishlist;
import com.esprit.app.gui.Transport.ShowTransportForm;
import com.esprit.app.services.TransportService;
import com.esprit.app.services.UserService;
import com.esprit.app.services.WishlistService;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

public class WishlistForm extends Form{
    public ArrayList<Wishlist> wishlists;
    @SuppressWarnings("unused")
    private Resources theme;
    
    public WishlistForm(Form previous, Resources res)throws IOException{
        super("Wishlist", GridLayout.autoFit());
        this.theme = theme;
        wishlists = new WishlistService().getAllWishlists(1);//Change with current user
        Container list = new Container(BoxLayout.y());
                list.setScrollableY(true);
                for (Wishlist wishlist : wishlists) {
                    Transport t = new TransportService().getTransport(wishlist.getTransport_id());
                    User u = new UserService().getUser(wishlist.getUser_id());
                    MultiButton mb = new MultiButton(t.getMarque());
                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                    Image i = URLImage.createToStorage(placeholder, t.getImage() != null ? Statics.BASE_URL+"/uploads/produit_image/"+t.getImage() : Statics.BASE_URL+"/uploads/produit_image/no_image.png", t.getImage() != null ? Statics.BASE_URL+"/uploads/produit_image/"+t.getImage() : Statics.BASE_URL+"/uploads/produit_image/no_image.png");
                    mb.setIcon(i.fill(200, 200));
                    mb.setTextLine2(t.getModel());
                    mb.addActionListener((evt) -> {
                        new ShowTransportForm(this.getComponentForm(), theme, t.getId()).show();
                    });
                    list.add(mb);
		}
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                    previous.showBack();
                });
                this.add(list);
    }
}
