package com.learnmicroservice.bookservice.command.controller;

import com.learnmicroservice.bookservice.command.command.CreateBookCommand;
import com.learnmicroservice.bookservice.command.command.DeleteBookCommand;
import com.learnmicroservice.bookservice.command.command.UpdateBookCommand;
import com.learnmicroservice.bookservice.command.model.BookRequestModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookCommandController {

    private final CommandGateway commandGateway;

    public BookCommandController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
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
}
