package br.ufscar.dc.pooa.domain.users;

abstract class DefaultUser extends Person {
    private String username;
    private String password;
    private boolean isSuperUser;
    private boolean isActive;
    private String email;

    // Getters and Setters

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

    public boolean isSuperUser() {
        return isSuperUser;
    }

    public void setSuperUser(int superUser) {
        boolean isSuperUser = superUser == 1;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(int active) {
        boolean isActive = active == 1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}