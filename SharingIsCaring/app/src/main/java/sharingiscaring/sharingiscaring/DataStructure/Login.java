package sharingiscaring.sharingiscaring.DataStructure;

/**
 * @author hoye
 * @version 0.1
 * @since 12/03/16
 *
 * This class holds two string variables, the username and a password for a potential user of the
 * PokePeddler app.  This class makes it easy to pass information in to the Elasticsearch query
 * generator that is responsible for validating the user's input.
 *
 */

public class Login {
    private String uname;
    private String pword;

    public Login(String uname, String pword){
        this.uname = uname;
        this.pword = pword;
    }

    public String getUsername() {
        return uname;
    }

    public void setUsername(String username) {
        this.uname = username;
    }

    public String getPassword() {
        return pword;
    }

    public void setPassword(String password) {
        this.pword = password;
    }

    public String toString(){
        return "{" + this.uname + ": " + this.pword + "}";
    }
}
