package pojo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class User {
    
    private Integer userId;
    
    @Size(min = 6, max = 16)
    private String username;
    
    @Email
    private String email;
    
    @Size(min = 32)
    private String password;
    
    @Size(min = 1, max = 16)
    private String nickname;
    
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
