package com.ludogoriehack24.ludogoriehack24.constants;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");
    String label;
    Role (String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
