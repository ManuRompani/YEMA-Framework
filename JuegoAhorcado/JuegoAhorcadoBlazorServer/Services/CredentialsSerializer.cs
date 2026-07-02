using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class CredentialsSerializer
    {
        public string Serialize(Credentials credentials)
        {
            string values = "";

            values += credentials.Username;
            values += "|" + credentials.Pass;

            return values;
        }
    }
}
