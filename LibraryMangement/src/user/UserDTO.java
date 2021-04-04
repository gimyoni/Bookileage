package user;
 
import java.sql.Date;
 
public class UserDTO implements java.io.Serializable{
    private String id;
    private String name;
    private String password;
    private int borrow_cnt;
    private int return_cnt;
    private int overdue_cnt;
    private int maximum;
    private String email;
    
    public UserDTO() {
         
    }
     
    public UserDTO(String id, String name, String password, int borrow_cnt, int return_cnt, int overdue_cnt, int maximum, String email) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.borrow_cnt = borrow_cnt;
        this.return_cnt = return_cnt;
        this.overdue_cnt = overdue_cnt;
        this.maximum = maximum;
        this.email = email;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBorrow_cnt() {
		return borrow_cnt;
	}

	public void setBorrow_cnt(int borrow_cnt) {
		this.borrow_cnt = borrow_cnt;
	}

	public int getReturn_cnt() {
		return return_cnt;
	}

	public void setReturn_cnt(int return_cnt) {
		this.return_cnt = return_cnt;
	}

	public int getOverdue_cnt() {
		return overdue_cnt;
	}

	public void setOverdue_cnt(int overdue_cnt) {
		this.overdue_cnt = overdue_cnt;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	} 
}