package com.ra.service;

import com.ra.entity.RoleName;
import com.ra.entity.Roles;

public interface IRoleService {
    Roles findByRoleName(RoleName roleName);
}
