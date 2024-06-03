package vn.laptop.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.laptop.laptopshop.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User nhan);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    User findById(long id);

    // List<User> findByAddressAndEmail(String address, String email);

    // in ra 1 email
    // List<User> findFirtByEmail(String email);

}
