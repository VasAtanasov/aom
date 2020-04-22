package bg.autohouse.service.services;

import java.util.List;

public interface AdminService {
  void bulkRegisterUsers(String adminId, List<String> usernames);
}
