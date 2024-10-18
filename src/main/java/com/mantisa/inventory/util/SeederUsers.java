package com.mantisa.inventory.util;

import com.mantisa.inventory.model.PermissionEntity;
import com.mantisa.inventory.model.RoleEntity;
import com.mantisa.inventory.model.RoleEnum;
import com.mantisa.inventory.model.UserEntity;
import com.mantisa.inventory.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class SeederUsers {

    private final Logger logger = LoggerFactory.getLogger(SeederUsers.class);

    @Autowired
    private UserRepository userRepository;

    public void seed() {
        logger.debug("SeederUsers: Starting to seed users and roles...");

        // Crear los permisos
        List<String> permissions = List.of("CREATE_USER", "READ_USER", "UPDATE_USER", "DELETE_USER");
        Set<PermissionEntity> permissionsCreated = new HashSet<>();
        permissions.forEach(permission -> {
            PermissionEntity permissionEntity = PermissionEntity.builder()
                    .permissionName(permission)
                    .build();
            permissionsCreated.add(permissionEntity);
        });

        // Crear el rol ADMIN
        RoleEntity adminEntity = RoleEntity.builder()
                .roleName(RoleEnum.ADMIN)
                .rolePermissions(permissionsCreated)
                .build();

        // Crear el usuario ADMIN
        UserEntity userEntity = UserEntity.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles(Set.of(adminEntity))
                .isEnable(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        // Guardar el usuario en la base de datos
        userRepository.save(userEntity);

        logger.debug("SeederUsers: Admin user and roles seeded successfully.");
    }
}
