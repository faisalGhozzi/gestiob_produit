/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.Transport;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Transport;
import com.esprit.app.services.TransportService;
import com.esprit.app.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

public class TransportForm extends Form{
    public ArrayList<Transport> transports;
    @SuppressWarnings("unused")
    private Resources theme;
    
    public TransportForm(Form previous, Resources res)throws IOException{
        super("Transports List", GridLayout.autoFit());
        this.theme = theme;
        this.revalidate();
        transports = new TransportService().getAllTransports();
		Container list = new Container(BoxLayout.y());
                list.setScrollableY(true);
                for (Transport transport : transports) {
                    MultiButton mb = new MultiButton(transport.getMarque() + ' ' + transport.getModel());
                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
                    Image i = URLImage.createToStorage(placeholder, transport.getImage() != null ? Statics.BASE_URL+"/uploads/produit_image/"+transport.getImage() : Statics.BASE_URL+"/uploads/produit_image/no_image.png", transport.getImage() != null ? Statics.BASE_URL+"/uploads/produit_image/"+transport.getImage() : Statics.BASE_URL+"/uploads/produit_image/no_image.png");
                    mb.setIcon(i.fill(200, 200));
                    mb.setTextLine2(transport.getPrix()  + " TND");
                    mb.addActionListener((evt) -> {
                        new ShowTransportForm(this, theme, transport.getId()).show();
                    });
                    list.add(mb);
		}
                this.getToolbar().addCommandToRightBar("Add", null, (evt) -> {
                    new AddTransportForm(this, theme, 0, false).show();
                });
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                    previous.show();
                });
                this.add(list);
        
    }
}
