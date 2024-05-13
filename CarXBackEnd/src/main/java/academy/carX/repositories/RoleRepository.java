package academy.carX.repositories;

import java.util.Optional;

import academy.carX.models.ERole;
import academy.carX.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}