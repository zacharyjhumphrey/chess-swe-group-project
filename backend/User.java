package backend;

import java.io.Serializable;

public class User implements Serializable
{
	  // Private data fields for the username and password.
	  private int id;
	  private String username;
	  private String password;
	  
	  // Getters for the username and password.
	  public String getUsername()
	  {
	    return username;
	  }
	  public String getPassword()
	  {
	    return password;
	  }
	  public String getId()
	  {
	    return Integer.toString(id);
	  }
	  public void setId(int id) {
		  this.id = id;
	  }
	  
	  // Setters for the username and password.
	  public void setUsername(String username)
	  {
	    this.username = username;
	  }
	  public void setPassword(String password)
	  {
	    this.password = password;
	  }
	  
	  
	  
	  // Constructor that initializes the username and password.
	  public User(String username, String password)
	  {
	    setUsername(username);
	    setPassword(password);
	    id = (int)Math.floor(Math.random()*999999999);
	  }
	}
