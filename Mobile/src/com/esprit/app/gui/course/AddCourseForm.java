/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.course;

import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Course;
import com.esprit.app.services.CourseService;
import java.util.Date;

public class AddCourseForm extends Form{
    @SuppressWarnings("unused")
    private Resources theme;
    private CourseService cs = new CourseService();
    private Course c = new Course();
    
    public AddCourseForm(Form previous, Resources theme, int id){
        super("Course Details",BoxLayout.y());
        if (id != 0){
            c = cs.findCourse(id);
        }     
        Button add = new Button(id == 0 ? "Create": "Update");
        Picker modePicker = new Picker();
        modePicker.setType(Display.PICKER_TYPE_STRINGS);
        modePicker.setStrings("Online", "On-site");
        modePicker.setSelectedString(id == 0 ? "Online" : c.getMode());
        TextField duration = new TextField(id == 0 ? "" : c.getDuree(), "Duration");
        TextField description = new TextField(id == 0 ? "" : c.getDescription(), "Description");
        TextField price = new TextField(id == 0 ? "" : String.valueOf(c.getPrice()),"Price",5,TextArea.NUMERIC);
        Picker datePickerStart = new Picker();
        datePickerStart.setType(Display.PICKER_TYPE_DATE);
        datePickerStart.setDate(id == 0 ? new Date() : c.getDateDebut());
        Picker datePickerEnd = new Picker();
        datePickerEnd.setType(Display.PICKER_TYPE_DATE);
        datePickerEnd.setDate(id == 0 ? new Date() : c.getDateFin());
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Course c = new Course((int)Float.parseFloat(price.getText()), modePicker.getText(), datePickerStart.getDate(), datePickerEnd.getDate(), duration.getText(), description.getText());
                if ( id == 0 ){
                    cs.addCourse(c);
                }else{
                    c.setId(id);
                    cs.updateCourse(c);
                }             
                previous.showBack();
            }
        });
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
        addAll(new Label("Course mode :"), modePicker, duration, description, price, new Label("Starting date :"), datePickerStart, new Label("Ending date :"), datePickerEnd, add);
        
    }
}
