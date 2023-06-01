package com.example.quickyes_project1;

public class allpostretrivedataclass {
    String Post1,Post2,Post3,Email,Post_Description,Post_ID,Profile_Image,User_Name,Reason;

    public allpostretrivedataclass() {
    }

    public allpostretrivedataclass(String post1, String post2, String post3, String email, String post_Description, String post_ID, String profile_Image, String user_Name, String reason) {
        Post1 = post1;
        Post2 = post2;
        Post3 = post3;
        Email = email;
        Post_Description = post_Description;
        Post_ID = post_ID;
        Profile_Image = profile_Image;
        User_Name = user_Name;
        Reason = reason;
    }

    public String getPost1() {
        return Post1;
    }

    public void setPost1(String post1) {
        Post1 = post1;
    }

    public String getPost2() {
        return Post2;
    }

    public void setPost2(String post2) {
        Post2 = post2;
    }

    public String getPost3() {
        return Post3;
    }

    public void setPost3(String post3) {
        Post3 = post3;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPost_Description() {
        return Post_Description;
    }

    public void setPost_Description(String post_Description) {
        Post_Description = post_Description;
    }

    public String getPost_ID() {
        return Post_ID;
    }

    public void setPost_ID(String post_ID) {
        Post_ID = post_ID;
    }

    public String getProfile_Image() {
        return Profile_Image;
    }

    public void setProfile_Image(String profile_Image) {
        Profile_Image = profile_Image;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
}
