package rest;

import domain.Kweet;
import exceptions.KweetNotFoundException;
import exceptions.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.KweetService;

@Api("Kweet")
@Path("kweet")
@Produces(MediaType.APPLICATION_JSON)
public class KweetResource {

    @Inject
    KweetService kweetService;

    @POST
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a kweet for the given user")
    public Response add(Kweet kweet, @PathParam("username") String username) {
        try {
            kweetService.createKweet(kweet, username);
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
        URI id = URI.create(kweet.getCreatedBy().getUsername());
        return Response.created(id).build();
    }

    @GET
    @Path("{content}")
    @ApiOperation(value = "Find a kweet by it's content")
    public Response findByContent(@PathParam("content") String content) {
        return Response.ok(kweetService.findByContent(content)).build();
    }

    @GET
    @Path("{username}/mentions")
    @ApiOperation(value = "Retrieve the kweets in which the given user is mentioned")
    public Response getMentions(@PathParam("username") String username) {
        try {
            return Response.ok(kweetService.getMentions(username)).build();
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("{id}/like/{username}")
    @ApiOperation(value = "Like a kweet for the given user")
    public Response likeKweet(@PathParam("id") long id, @PathParam("username") String username) {
        try {
            if (kweetService.likeKweet(id, username)) {
                return Response.ok().build();
            }
        } catch (UserNotFoundException | KweetNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
        throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }

    @GET
    @Path("{id}/likes")
    @ApiOperation(value = "Get the users who like the given kweet")
    public Response getKweetLikes(@PathParam("id") long id) {
        try {
            return Response.ok(kweetService.getKweetLikes(id)).build();
        } catch (KweetNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(),Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Delete kweet by id")
    public Response deleteKweet(@PathParam("id") long id) {
        try {
            kweetService.deleteKweet(id);
            return Response.ok().build();
        } catch (KweetNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("{username}/timeline")
    @ApiOperation(value = "Retrieve the timeline(=his own kweets, and the kweets of people he follows) for a user")
    public Response getUserTimeline(@PathParam("username") String username) {
        try {
            return Response.ok(kweetService.getTimeline(username)).build();
        } catch (UserNotFoundException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

}
