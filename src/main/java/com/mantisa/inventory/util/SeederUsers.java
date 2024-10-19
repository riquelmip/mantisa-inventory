package com.mantisa.inventory.util;

import com.mantisa.inventory.model.PermissionEntity;
import com.mantisa.inventory.model.RoleEntity;
import com.mantisa.inventory.model.RoleEnum;
import com.mantisa.inventory.model.UserEntity;
import com.mantisa.inventory.repository.PermissionRepository; // Agregar este repositorio
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

    @Autowired
    private PermissionRepository permissionRepository; // Inyectar el repositorio de permisos

    public void seed() {
        logger.debug("SeederUsers: Starting to seed users and roles...");

        // Todos los permisos para el administrador
        Set<PermissionEntity> permissionsCreated = getPermissionEntities();

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


        // Crear el rol WAREHOUSE
        RoleEntity warehouseEntity = RoleEntity.builder()
                .roleName(RoleEnum.WAREHOUSE)
                .rolePermissions(
                        Set.of(
                                getOrCreatePermission("READ_PRODUCT"),
                                getOrCreatePermission("CREATE_PRODUCT"),
                                getOrCreatePermission("UPDATE_PRODUCT"),
                                getOrCreatePermission("DELETE_PRODUCT"),
                                getOrCreatePermission("READ_PRODUCT_REPORT")
                        )
                )
                .build();


        // Crear el usuario WAREHOUSE
        UserEntity warehouseUserEntity = UserEntity.builder()
                .username("warehouse")
                .password(new BCryptPasswordEncoder().encode("warehouse"))
                .roles(Set.of(warehouseEntity))
                .isEnable(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        // Guardar el usuario en la base de datos
        userRepository.save(warehouseUserEntity);

        // Crear el rol PRODUCTION
        RoleEntity productionEntity = RoleEntity.builder()
                .roleName(RoleEnum.PRODUCTION)
                .rolePermissions(
                        Set.of(
                                getOrCreatePermission("READ_PRODUCTION_LINE"),
                                getOrCreatePermission("CREATE_PRODUCTION_LINE"),
                                getOrCreatePermission("UPDATE_PRODUCTION_LINE"),
                                getOrCreatePermission("DELETE_PRODUCTION_LINE"),
                                getOrCreatePermission("READ_PRODUCTION_ORDER"),
                                getOrCreatePermission("CREATE_PRODUCTION_ORDER"),
                                getOrCreatePermission("UPDATE_PRODUCTION_ORDER"),
                                getOrCreatePermission("DELETE_PRODUCTION_ORDER"),
                                getOrCreatePermission("READ_PRODUCTION_ORDER_REPORT")
                        )
                )
                .build();

        // Crear el usuario PRODUCTION
        UserEntity productionUserEntity = UserEntity.builder()
                .username("production")
                .password(new BCryptPasswordEncoder().encode("production"))
                .roles(Set.of(productionEntity))
                .isEnable(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        // Guardar el usuario en la base de datos
        userRepository.save(productionUserEntity);

        logger.debug("SeederUsers: Admin user and roles seeded successfully.");
    }

    // Método para obtener o crear un permiso
    private PermissionEntity getOrCreatePermission(String permissionName) {
        return permissionRepository.findByPermissionName(permissionName)
                .orElseGet(() -> {
                    PermissionEntity newPermission = PermissionEntity.builder()
                            .permissionName(permissionName)
                            .build();
                    return permissionRepository.save(newPermission);
                });
    }

    private Set<PermissionEntity> getPermissionEntities() {
        List<String> permissions = List.of(
                "CREATE_USER", "READ_USER", "UPDATE_USER", "DELETE_USER",
                "READ_ROLE", "CREATE_ROLE", "UPDATE_ROLE", "DELETE_ROLE",
                "READ_PRODUCT", "CREATE_PRODUCT", "UPDATE_PRODUCT", "DELETE_PRODUCT",
                "READ_PRODUCTION_LINE", "CREATE_PRODUCTION_LINE", "UPDATE_PRODUCTION_LINE", "DELETE_PRODUCTION_LINE",
                "READ_PRODUCTION_ORDER", "CREATE_PRODUCTION_ORDER", "UPDATE_PRODUCTION_ORDER", "DELETE_PRODUCTION_ORDER",
                "READ_PRODUCTION_ORDER_REPORT", "READ_PRODUCT_REPORT"
        );
        Set<PermissionEntity> permissionsCreated = new HashSet<>();
        permissions.forEach(permission -> permissionsCreated.add(getOrCreatePermission(permission)));
        return permissionsCreated;
    }
}
