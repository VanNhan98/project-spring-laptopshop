package vn.laptop.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.laptop.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User nhan);

    User deleteById(long id);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    User findById(long id);

    boolean existsByEmail(String email);

    // List<User> findByAddressAndEmail(String address, String email);

    // in ra 1 email
    // List<User> findFirtByEmail(String email);

}
