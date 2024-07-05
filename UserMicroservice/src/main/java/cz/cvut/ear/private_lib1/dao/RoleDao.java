package cz.cvut.ear.private_lib1.dao;


import cz.cvut.ear.private_lib1.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, Long>{

    Role findRoleByName(String name);
}
