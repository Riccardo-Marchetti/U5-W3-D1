package riccardo.U5W3D1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riccardo.U5W3D1.entities.Device;
import riccardo.U5W3D1.exceptions.BadRequestException;
import riccardo.U5W3D1.payloads.DeviceDTO;
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
    private Device findDeviceAndUpdate (@PathVariable UUID deviceId, @RequestBody @Validated DeviceDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.deviceService.findDeviceByIdAndUpdate(deviceId, body);
    }

    @DeleteMapping ("/{deviceId}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    private void deleteDevice (@PathVariable UUID deviceId){
        this.deviceService.deleteDevice(deviceId);
    }

    @PutMapping ("/{deviceId}/assign/{employeeId}")
    private Device assignToDevice (@PathVariable UUID deviceId, @PathVariable UUID employeeId) {
        return this.deviceService.assignToDevice(deviceId, employeeId);
    }

    @PatchMapping ("/dismiss/{deviceId}")
    private Device dismissFromDevice (@PathVariable UUID deviceId) {
        return this.deviceService.dismissFromDevice(deviceId);
    }

    @PatchMapping ("/maintenance/{deviceId}")
    private Device maintenanceDevice (@PathVariable UUID deviceId) {
        return this.deviceService.maintenanceDevice(deviceId);
    }
}
