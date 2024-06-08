package vn.laptop.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.laptop.laptopshop.domain.Role;

import vn.laptop.laptopshop.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

}
