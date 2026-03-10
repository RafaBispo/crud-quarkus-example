package com.rboliveira.controller;

import com.rboliveira.dto.UserDTO;
import com.rboliveira.service.UserService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response getAllUsers(@QueryParam("page") @DefaultValue("0") Integer page,
                                @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var users = userService.findAll(page, pageSize);
        return Response.ok(users).build();
    }

    @POST
    public Response createUser(UserDTO userDTO) {
        return Response.ok(userService.createUser(userDTO)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") UUID userId, UserDTO userDTO) {
        return Response.ok(userService.updateUser(userId, userDTO)).build();
    }


    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID userId) {
        return Response.ok(userService.findById(userId)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") UUID userId) {
        var userDeleted = userService.deleteById(userId);
        return Response.status(userDeleted ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND).build();
    }
}
