package cz.cvut.ear.private_lib1.service;

import cz.cvut.ear.private_lib1.dao.RoleDao;
import cz.cvut.ear.private_lib1.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service(value = "roleService")
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }
}
