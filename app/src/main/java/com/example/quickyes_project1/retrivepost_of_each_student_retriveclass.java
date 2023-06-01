package com.example.quickyes_project1;

public class retrivepost_of_each_student_retriveclass {
    String Post1,Post_ID;

    public retrivepost_of_each_student_retriveclass() {
    }

    public retrivepost_of_each_student_retriveclass(String post1, String post_ID) {
        Post1 = post1;
        Post_ID = post_ID;
    }

    public String getPost_ID() {
        return Post_ID;
    }

    public void setPost_ID(String post_ID) {
        Post_ID = post_ID;
    }

    public String getPost1() {
        return Post1;
    }

    public void setPost1(String post1) {
        Post1 = post1;
    }
}
