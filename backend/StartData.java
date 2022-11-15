package backend;
import java.io.Serializable;

public class StartData implements Serializable{
private User user;

public User getUser() {
	return user;
}
public void setUser(User u) {
	user = u;
}
public StartData(User u)
{
  setUser(u);
}
}
