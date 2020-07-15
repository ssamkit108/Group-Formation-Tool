package com.dal.catmeclone.model;

public class User {

    private String bannerId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role userRoles;
    private String token;

    public User() {
        super();
    }

    public User(String bannerId, String firstName, String lastName, String password, String email, Role userRoles) {
        super();
        this.bannerId = bannerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.userRoles = userRoles;
    }

    public User(String bannerId, String firstName, String lastName, String password, String email) {
        super();
        this.bannerId = bannerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public User(String bannerId, String firstName, String lastName, String email) {
        super();
        this.bannerId = bannerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String bannerId) {
        super();
        this.bannerId = bannerId;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Role userRoles) {
        this.userRoles = userRoles;
    }

    public String gettoken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bannerId == null) ? 0 : bannerId.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (bannerId == null) {
            if (other.bannerId != null)
                return false;
        } else if (!bannerId.equals(other.bannerId))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }
}
