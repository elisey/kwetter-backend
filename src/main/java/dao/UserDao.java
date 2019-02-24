/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Kweet;
import domain.User;

public interface UserDao {

    User getUser(String name);
    void addUser(User user);
    int count();
    void update(User user);
}
