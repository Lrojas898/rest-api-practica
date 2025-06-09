package co.icesi.taskManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.icesi.taskManager.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    
}
