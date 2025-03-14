package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.TicketDTO;
import com.fireflink.jiraservice.service.TicketService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
@Validated
@Slf4j
public class TicketController {


    private final TicketService ticketService;


    @GetMapping("/get-all-tickets/{pageNumber}/{pageSize}")
    public ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> findAllTickets(@PathVariable int pageNumber, @PathVariable int pageSize){

        return ticketService.findAllTickets(pageNumber,pageSize);

    }

    @GetMapping("/get-tickets-id/{pageNumber}/{pageSize}")
    public ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> findTicketsByEmail(@PathVariable int pageNumber, @PathVariable int pageSize,@RequestParam String email){

        return ticketService.findTicketsById(pageNumber,pageSize,email);

    }



    @PostMapping("/assign-ticket")
    public ResponseEntity<ApiResponseDTO<String>> assignTicket(@RequestBody List<String> ticketIds, @RequestParam String userEmail, @RequestParam  String adminEmail) {

        return ticketService.assignTicket(ticketIds,userEmail,adminEmail);

    }

    @DeleteMapping("/delete-ticket-id")
    public ResponseEntity<ApiResponseDTO<String>> deleteTicketById(@RequestParam @NotBlank(message = "please enter ticket id") String ticketId, @RequestParam @NotBlank(message = "please enter admin email") String adminEmail) {

        return ticketService.deleteTicketById(ticketId,adminEmail);

    }

    @DeleteMapping("/delete-all-ticket")
    public ResponseEntity<ApiResponseDTO<String>> deleteAllTicket() {

        return ticketService.deleteAllTicket();

    }

    @GetMapping("/search/{pageNumber}/{pageSize}")
    public ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> searchTicket(@RequestParam String searchItem,@PathVariable int pageNumber,@PathVariable int pageSize) {

        return ticketService.searchTicket(searchItem,pageNumber,pageSize);

    }


}
