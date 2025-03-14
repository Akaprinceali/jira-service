package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.PrivilegeDTO;
import com.fireflink.jiraservice.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privilege")
@RequiredArgsConstructor
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    @PostMapping("/save-privilege")
    public ResponseEntity<ApiResponseDTO<String>> savePrivilege(@RequestBody PrivilegeDTO privilegeDTO, @RequestParam String email){

       return privilegeService.savePrivilege(privilegeDTO,email);

    }

    @GetMapping("/get-privilege-names")
    public ResponseEntity<ApiResponseDTO<List<String>>> getPrivilegeNames(){

        return privilegeService.getPrivilegeNames();

    }


    @GetMapping("/get-all-privilege")
    public ResponseEntity<ApiResponseDTO<List<PrivilegeDTO>>> getAllPrivileges(){

        return privilegeService.getAllPrivileges();

    }

    @PutMapping("/get-all-privilege")
    public ResponseEntity<ApiResponseDTO<String>> updatePrivilege(@RequestBody PrivilegeDTO privilegeDTO,@RequestParam String email){

        return privilegeService.updatePrivilege(privilegeDTO,email);

    }

    @DeleteMapping("/delete-privilege-id")
    public ResponseEntity<ApiResponseDTO<String>> deletePrivilegeById(@RequestParam String email,@RequestParam String privilegeId){

        return privilegeService.deletePrivilegeById(email,privilegeId);

    }



}
