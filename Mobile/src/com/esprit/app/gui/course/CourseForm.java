/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.app.gui.course;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import com.esprit.app.entity.Course;
import com.esprit.app.services.CourseService;
import java.io.IOException;
import java.util.ArrayList;

public class CourseForm extends Form{
    public ArrayList<Course> courses;
    @SuppressWarnings("unused")
    private Resources theme;
    
    public CourseForm(Form previous, Resources res)throws IOException{
        super("Courses List", GridLayout.autoFit());
        this.theme = theme;
        courses = new CourseService().getAllCourses();
		//this.add(new SpanLabel(new ProduitsService().getAllProducts().toString()));
		Container list = new Container(BoxLayout.y());
                list.setScrollableY(true);
                for (Course course : courses) {
                    MultiButton mb = new MultiButton(course.getDescription());
                    mb.setTextLine2(course.getPrice() > 0 ? String.valueOf(course.getPrice()) + " TND" : "FREE");
                    mb.addActionListener((evt) -> {
                        new ShowCourseForm(this.getComponentForm(), theme, course.getId()).show();
                    });
                    list.add(mb);
		}
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                    previous.showBack();
                });
                this.getToolbar().addCommandToRightBar("New", null , (evt) -> {
                    new AddCourseForm(this.getComponentForm(), theme, 0).show();
                });
                this.add(list);
        
    }    
}
