package com.example.server.models;

import java.util.List;

public class UserProfile {
    private User user;
    private Skill skill;
    private Contact contact;
    private List<Education> educations;

    public UserProfile(User user, Skill skill, Contact contact, List<Education> educations) {
        this.user = user;
        this.skill = skill;
        this.contact = contact;
        this.educations = educations;
    }

    public UserProfile(Skill skill, Contact contact, List<Education> educations) {
        this.skill = skill;
        this.contact = contact;
        this.educations = educations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }
}

