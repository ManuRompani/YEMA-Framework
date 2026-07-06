package controllers;


import containers.MemoryUserManager;
import dtos.Command;
import dtos.Credentials;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import juegoUtils.PlayerDeserializer;
import juegoUtils.PlayerSerializer;
import models.Player;
import services.ServiceLocator;
import utils.Context;

public class AuthController extends BaseController{
	private MemoryUserManager userManager;
	private PlayerDeserializer deserializer;
	private PlayerSerializer serializer;
	
	private Response register(Command command, Context context) {
	    Response response = new Response();

	    // Obtiene el jugador enviado por el cliente y lo convierte a objeto.
	    String sPlayer = command.getParameter("player");
	    Player player = deserializer.deserealize(sPlayer, Player.class);
	    
	    // Registra el jugador y recibe el objeto actualizado, por ejemplo con ID.
	    player = (Player) this.userManager.registerUser(player);

	    // Lo guarda en el contexto, es decir, queda logueado en la sesión.
	    context.setUser(player);
	    
	    // Devuelve el jugador serializado al cliente.
	    response.setMessage(this.serializer.serialize(player));
	    return response;
	}

	private Response login(Command command, Context context) {
	    Response response = new Response();

	    // Obtiene las credenciales enviadas por el cliente y las convierte a objeto.
	    Credentials credentials = new Credentials(command.getParameter("username"), command.getParameter("password"));

	    // Autentica al jugador con esas credenciales, tambien puede ser admin total lo que lo define es el rol
	    Player player = (Player) this.userManager.authenticate(credentials);

	    // Lo guarda en el contexto, es decir, queda logueado en la sesión.
	    context.setUser(player);

	    if(player == null) {
	    	response.setMessage("null");
	    }
	    else {
	    	// Devuelve el jugador serializado al cliente.
		    response.setMessage(this.serializer.serialize(player));
	    }
	    
	    return response;
	}
	
	@Override
	public Response Ejecutar(Command command, Context context) {
		switch(command.getAction()) {
			case "register":
				return this.register(command, context);
			case "login":
				return this.login(command, context);
			default:
				Response response = new Response();
				response.setMessage("Accion no reconocida: " + command.getAction());
				return response;
		}
	}

	@Override
	public void iniciarServicios(ServiceLocator sl) {
		try {
			this.userManager = sl.getService(MemoryUserManager.class);
			this.deserializer = sl.getService(PlayerDeserializer.class);
			this.serializer = sl.getService(PlayerSerializer.class);
		} catch (ServiceNotImplementedException se) {
			throw new RuntimeException("Error crítico: inicializacion del servicio " + se.getMessage() + " fallida.");
		}
		
	}
}
