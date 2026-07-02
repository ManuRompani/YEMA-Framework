using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class BackendResponseDeserializer
    {
        public BackendResponse Deserialize(string response)
        {
            if (string.IsNullOrWhiteSpace(response))
                return BackendResponse.Bad("El servidor no devolvió respuesta.");

            response = response.Trim();

            if (response.Equals("ok", StringComparison.OrdinalIgnoreCase))
                return BackendResponse.Ok();

            if (response.Equals("repetida", StringComparison.OrdinalIgnoreCase))
                return BackendResponse.Repeated();

            if (response.StartsWith("bad=", StringComparison.OrdinalIgnoreCase))
            {
                string message = response.Substring("bad=".Length);
                return BackendResponse.Bad(message);
            }

            return BackendResponse.Bad("Respuesta desconocida del servidor: " + response);
        }
    }
}
