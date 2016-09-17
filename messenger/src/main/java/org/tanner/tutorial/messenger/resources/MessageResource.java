package org.tanner.tutorial.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

import org.tanner.tutorial.messenger.resources.beans.MessageFilterBean;
import org.tanner.tutorial.messenger.service.MessageService;
import org.tanner.tutorial.model.Message;


@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();

    @GET
    public List<Message> getMessages(@BeanParam MessageFilterBean bean) {
    	if(bean.getYear() > 0)
    		return messageService.getAllMessagesForYear(bean.getYear());
    	if(bean.getStart() >= 0 && bean.getSize() >= 0)
    		return messageService.getAllMessagesPaginated(bean.getStart(), bean.getSize());
        return messageService.getAllMessages();
    }

    @POST //See Inject Demo for UriInfo stuff
    public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException{
    	
    	Message newMessage = messageService.addMessage(message);
    	String newId = String.valueOf(newMessage.getId());
    	URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
    	return Response.created(uri)
    			.entity(newMessage)
    			.build();
    }
    
    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long messageId, Message message){
    	message.setId(messageId);
    	return messageService.updateMessage(message);
    }
    
    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long id){
    	messageService.removeMessage(id);
    }

    
    @GET
    @Path("/{messageId}")
    public Message getMessage(@PathParam("messageId") long messageId){
    	return messageService.getMessage(messageId);
    }
    
    @Path("/{messageId}/comments")
    public CommentResource getCommentResource(){
    	return new CommentResource();
    }
    
}