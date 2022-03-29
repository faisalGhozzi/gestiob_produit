/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.tutorial;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Tutorial;
import com.esprit.app.services.TutorialService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddTutorialForm extends Form{
@SuppressWarnings("unused")
    private Resources theme;
    private TutorialService ts = new TutorialService();
    private Tutorial t = new Tutorial();
    
    public AddTutorialForm(Form previous, Resources theme, int id){
        super("Tutorial Details",BoxLayout.y());
        if (id != 0){
            t = ts.getTutorial(id);
        }
        Label imgName = new Label();
        Label vidName = new Label();
        
        Button uploadImage = new Button("Upload Image");
        Label lbl_Image = new Label();
        Button uploadVideo = new Button("Upload Video");
        Label lbl_vid = new Label();
        Button add = new Button(id == 0 ? "Create": "Update");        
        Picker categoryPicker = new Picker();
        categoryPicker.setType(Display.PICKER_TYPE_STRINGS);
        String [] lst = {"Starters", "Main Dishes", "Side Dished", "Deserts"};
        
        categoryPicker.setStrings(lst);
        categoryPicker.setSelectedString(id == 0 ? "Starters" : t.getCategory());
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setDate(id == 0 ? new Date() : t.getDateTuto());
        TextField title = new TextField(id == 0 ? "" : t.getTitre(), "Title");
        TextField description = new TextField(id == 0 ? "" : t.getDescription(), "Description");
        TextField price = new TextField(id == 0 ? "" : String.valueOf(t.getPrix()),"Price",5,TextArea.NUMERIC);
        
        uploadImage.addActionListener((evt) -> {
            String path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if(path != null){
                try {
                    Image img = Image.createImage(path);
                    lbl_Image.setIcon(img);
                    imgName.setText(path);
                    this.revalidate();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            }
        });
        
        uploadVideo.addActionListener((evt) -> {
            String path = Capture.captureVideo();
            if(path != null){
                vidName.setText(path);
                lbl_vid.setText("Video Ready");
                this.revalidate();
            }
        });
        
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Tutorial t = new Tutorial("no_video.png", "no_image.png", datePicker.getDate(), categoryPicker.getText(), title.getText(), description.getText(), Double.parseDouble(price.getText()));
                if (id == 0){
                    ts.addTutorial(t);
                }else{
                    t.setId(id);
                    ts.updateTutorial(t);
                }   
                /*try {
                    new TutorialForm(previous, theme).show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/
            }
        });
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            try {
                new TutorialForm(previous, theme).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        addAll(new Label("Category : "), categoryPicker,title,description,price,new Label("Release Date :"), datePicker, uploadImage, lbl_Image, uploadVideo, lbl_vid, add);
        
    }
}
