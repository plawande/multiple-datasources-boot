package com.plawande.multipledatasources.repository.mysql;

import com.plawande.multipledatasources.model.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
