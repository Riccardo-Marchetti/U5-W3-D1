package riccardo.U5W3D1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riccardo.U5W3D1.entities.Device;
import riccardo.U5W3D1.entities.Employee;
import riccardo.U5W3D1.exceptions.BadRequestException;
import riccardo.U5W3D1.payloads.DeviceDTO;
import riccardo.U5W3D1.payloads.EmployeeDTO;
import riccardo.U5W3D1.services.DeviceService;

import java.util.UUID;

@RestController
@RequestMapping ("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    private Page<Device> getAllDevice (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size, @RequestParam (defaultValue = "type") String sortBy){
        return this.deviceService.getAllDevice(page, size, sortBy);
    }

    @GetMapping ("/me")
    public Device getDevice (@AuthenticationPrincipal Device currentDevice){
        return currentDevice;
    }

    @PutMapping ("/me")
    public Device updateDevice (@AuthenticationPrincipal Device currentDevice, @RequestBody @Validated DeviceDTO body ){
        return this.deviceService.findDeviceByIdAndUpdate(currentDevice.getId(), body);
    }

    @DeleteMapping ("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice (@AuthenticationPrincipal Device currentDevice){
        this.deviceService.deleteDevice(currentDevice.getId());
    }

    @GetMapping ("/{deviceId}")
    private Device getDeviceById (@PathVariable UUID deviceId){
        return this.deviceService.getDeviceById(deviceId);
    }

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    private Device saveDevice(@RequestBody @Validated DeviceDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.saveDevice(body);
    }

    @PutMapping ("/{deviceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private Device findDeviceAndUpdate (@PathVariable UUID deviceId, @RequestBody @Validated DeviceDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.findDeviceByIdAndUpdate(deviceId, body);
    }

    @DeleteMapping ("/{deviceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    private void deleteDevice (@PathVariable UUID deviceId){
        this.deviceService.deleteDevice(deviceId);
    }

    @PutMapping ("/{deviceId}/assign/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private Device assignToDevice (@PathVariable UUID deviceId, @PathVariable UUID employeeId) {
        return this.deviceService.assignToDevice(deviceId, employeeId);
    }

    @PatchMapping ("/dismiss/{deviceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private Device dismissFromDevice (@PathVariable UUID deviceId) {
        return this.deviceService.dismissFromDevice(deviceId);
    }

    @PatchMapping ("{deviceId}/maintenance/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    private Device maintenanceDevice (@PathVariable UUID deviceId, @PathVariable UUID employeeId) {
        return this.deviceService.maintenanceDevice(deviceId, employeeId);
    }
}
