package riccardo.U5W3D1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import riccardo.U5W3D1.entities.Device;

import java.util.UUID;


@Repository
public interface DeviceDAO extends JpaRepository<Device, UUID> {
}
