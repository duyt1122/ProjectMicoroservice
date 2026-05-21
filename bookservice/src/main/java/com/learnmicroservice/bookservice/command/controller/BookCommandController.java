package com.learnmicroservice.bookservice.command.controller;

import com.learnmicroservice.bookservice.command.command.CreateBookCommand;
import com.learnmicroservice.bookservice.command.command.DeleteBookCommand;
import com.learnmicroservice.bookservice.command.command.UpdateBookCommand;
import com.learnmicroservice.bookservice.command.model.BookRequestModel;
import com.ltfullstack.commonservice.service.KafkaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
@Slf4j
public class BookCommandController {

    private final CommandGateway commandGateway;

    private final KafkaService kafkaService;

    public BookCommandController(CommandGateway commandGateway, KafkaService kafkaService){
        this.commandGateway = commandGateway;
        this.kafkaService = kafkaService;
    }

    @PostMapping
    public String addBook(@Valid @RequestBody BookRequestModel model){
        CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
        return commandGateway.sendAndWait(command);
    }
    @PutMapping("/{bookId}")
    public String updateBook(@RequestBody BookRequestModel model, @PathVariable("bookId") String bookId){
        UpdateBookCommand command = new UpdateBookCommand(bookId, model.getName(), model.getAuthor(), model.getIsRealy());
       return commandGateway.sendAndWait(command);
    }
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") String bookId){
        DeleteBookCommand command = new DeleteBookCommand(bookId);
        return commandGateway.sendAndWait(command);
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message){
     kafkaService.sendMessage("test", message);
     log.info("Message : " + message);
    }
}
