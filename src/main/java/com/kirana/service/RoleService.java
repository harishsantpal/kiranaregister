package com.kirana.service;

import com.kirana.model.Role;

public interface RoleService {
    Role findOrCreateRole(String roleName);
}
