package admin;
 
import java.sql.Date;
 
public class AdminDTO implements java.io.Serializable{
    private String id;
    private String name;
    private String password;
    private int admin_code;
    
    public AdminDTO() {
         
    }
     
    public AdminDTO(String name, String id, String password, int admin_code) {
        super();
        this.name = name;
        this.id = id;
        this.password = password;
        this.admin_code = admin_code;
 
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

	public int getAdmin_code() {
		return admin_code;
	}

	public void setAdmin_code(int admin_code) {
		this.admin_code = admin_code;
	}

	
}