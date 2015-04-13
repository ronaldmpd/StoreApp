package com.mobidosoft.storeapp.Model;

/**
 * Created by RP on 3/17/2015.
 */
public class User {

    private Integer id;

    private String success;
    private String message;
    private String errorMessage;
    private String userAccessKey;
    private String isTokenDevice;

    private Integer adminsId;
    private String username;
    private String password;
    private Integer adminRolesId;
    private String adminRolesName;
    private String name;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String status;


    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminsId() {
        return adminsId;
    }

    public void setAdminsId(Integer adminsId) {
        this.adminsId = adminsId;
    }

    public Integer getAdminRolesId() {
        return adminRolesId;
    }

    public void setAdminRolesId(Integer adminRolesId) {
        this.adminRolesId = adminRolesId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUserAccessKey() {
        return userAccessKey;
    }

    public void setUserAccessKey(String userAccessKey) {
        this.userAccessKey = userAccessKey;
    }

    public String getIsTokenDevice() {
        return isTokenDevice;
    }

    public void setIsTokenDevice(String isTokenDevice) {
        this.isTokenDevice = isTokenDevice;
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

    public String getAdminRolesName() {
        return adminRolesName;
    }

    public void setAdminRolesName(String adminRolesName) {
        this.adminRolesName = adminRolesName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", userAccessKey='" + userAccessKey + '\'' +
                ", isTokenDevice='" + isTokenDevice + '\'' +
                ", adminsId=" + adminsId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", adminRolesId=" + adminRolesId +
                ", adminRolesName='" + adminRolesName + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
