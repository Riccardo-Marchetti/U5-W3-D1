package riccardo.U5W3D1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import riccardo.U5W3D1.entities.Device;
import riccardo.U5W3D1.entities.Employee;
import riccardo.U5W3D1.exceptions.NotFoundException;
import riccardo.U5W3D1.payloads.DeviceDTO;
import riccardo.U5W3D1.repositories.DeviceDAO;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


@Service
public class DeviceService {
    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private EmployeeService employeeService;

    public Page<Device> getAllDevice (int page, int size, String sortBy){
        if (size > 30) size = 30;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.deviceDAO.findAll(pageable);
    }

    public Device getDeviceById (UUID id){
        return this.deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device saveDevice (DeviceDTO body){
        Device device = new Device(body.type());
        return this.deviceDAO.save(device);
    }

    public Device findDeviceByIdAndUpdate (UUID id, DeviceDTO body){
        Device device = this.deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        device.setType(body.type());
        return this.deviceDAO.save(device);
    }

    public void deleteDevice (UUID id){
        Device device = this.deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        this.deviceDAO.delete(device);
    }

    public Device assignToDevice (UUID deviceId, UUID employeeId){
        Device device = this.deviceDAO.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
        Employee employee = this.employeeService.findEmployeeById(employeeId);
        device.setEmployee(employee);
        device.setStatus("Assigned");
        if (device.getMaintenanceStartDate() != null){
            device.setMaintenanceStartDate(null);
        }
        if (device.getMaintenanceEndDate() != null){
            device.setMaintenanceEndDate(null);
        }
        return this.deviceDAO.save(device);
    }

    public Device dismissFromDevice (UUID deviceId){
        Device device = this.deviceDAO.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
        if (device.getEmployee() != null) {
            device.setEmployee(null);
        }
        if (device.getMaintenanceStartDate() != null){
            device.setMaintenanceStartDate(null);
        }
        if (device.getMaintenanceEndDate() != null){
            device.setMaintenanceEndDate(null);
        }
        device.setStatus("Dismissed");
        return this.deviceDAO.save(device);
    }
    public Device maintenanceDevice (UUID deviceId, UUID employeeId){
        Device device = this.deviceDAO.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
        Employee employee = this.employeeService.findEmployeeById(employeeId);
        if (device.getMaintenanceEndDate() != null && device.getMaintenanceEndDate().isBefore(LocalDate.now())) {
            device.setStatus(null);
            device.setMaintenanceStartDate(null);
            device.setMaintenanceEndDate(null);
            device.setEmployee(employee);
        } else {
            if (device.getEmployee() != null) {
                device.setEmployee(null);
            }
            if (!Objects.equals(device.getStatus(), "Under Maintenance")) {
                device.setStatus("Under Maintenance");
            }
            if (device.getMaintenanceStartDate() == null) {
                device.setMaintenanceStartDate(LocalDate.now());
            }
            if (device.getMaintenanceEndDate() == null && device.getType().equalsIgnoreCase("smartphone")) {
                device.setMaintenanceEndDate(LocalDate.now().plusWeeks(1));
            }
            if (device.getMaintenanceEndDate() == null && device.getType().equalsIgnoreCase("Tablet")) {
                device.setMaintenanceEndDate(LocalDate.now().plusDays(10));
            }
            if (device.getMaintenanceEndDate() == null && device.getType().equalsIgnoreCase("Laptop")) {
                device.setMaintenanceEndDate(LocalDate.now().plusWeeks(2));
            }
        }
        return this.deviceDAO.save(device);
    }
}
