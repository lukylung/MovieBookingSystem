package myservice.info.DAO;

import java.util.List;
import java.util.Map;
import myservice.info.Model.User;;

public interface AccountDAO {
	List<Map<String, Object>> getAcoountByUserName(String username);
	void setAcoountByUser(User user);
}