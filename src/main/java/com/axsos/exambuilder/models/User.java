package com.axsos.exambuilder.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Size(min=3, message="Username must be greater than 3 characters")

    private String username;
    @Size(min=3, message="Username must be greater than 3 characters")

    private String password;
    @Transient
    private String passwordConfirmation;
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
