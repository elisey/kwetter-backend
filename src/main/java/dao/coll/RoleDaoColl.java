/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.coll;

import dao.interfaces.RoleDao;
import domain.Role;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoColl implements RoleDao {

    private List<Role> roles = new ArrayList<>();

    public RoleDaoColl() {
    }

    @Override
    public void create(Role role) {
        roles.add(role);
    }

}
