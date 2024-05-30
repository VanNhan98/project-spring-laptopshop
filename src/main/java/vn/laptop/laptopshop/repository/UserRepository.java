package vn.laptop.laptopshop.repository;

import org.springframework.data.repository.CrudRepository;

import vn.laptop.laptopshop.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User save(User nhan);

}
