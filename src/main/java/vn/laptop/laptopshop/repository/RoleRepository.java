package vn.laptop.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.laptop.laptopshop.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}