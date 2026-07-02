using JuegoAhorcadoBlazorServer.Modelo;
using JuegoAhorcadoBlazorServer.Services;

namespace JuegoAhorcadoBlazorServer.Negocio
{
    public class PlayerNegocio
    {
        private readonly Comunicador _comunicador;
        private readonly PlayerSerializer _playerSerializer;
        private readonly PlayerDeserializer _playerDeserializer;
        private readonly CredentialsSerializer _credentialsSerializer;

        public PlayerNegocio(Comunicador comunicador)
        {
            _comunicador = comunicador;
            _playerSerializer = new PlayerSerializer();
            _playerDeserializer = new PlayerDeserializer();
            _credentialsSerializer = new CredentialsSerializer();
        }

        public async Task<Player> Register(Player newPlayer)
        {
            try
            {
                string playerSerializado = _playerSerializer.Serialize(newPlayer);

                string comando = "auth/register/player=" + Uri.EscapeDataString(playerSerializado);

                string respuesta = await _comunicador.EnviarYRecibirAsync(comando);

                string respuestaDecodificada = Uri.UnescapeDataString(respuesta);

                Player playerRegistrado = _playerDeserializer.Deserialize(respuestaDecodificada);

                return playerRegistrado;
            }
            catch (Exception ex)
            {
                throw new Exception("Error al registrar el jugador.", ex);
            }
        }

        public async Task<Player> Login(Credentials cred)
        {
            try
            {
                string comando = "auth/login/username="
                    + Uri.EscapeDataString(cred.Username)
                    + "&password="
                    + Uri.EscapeDataString(cred.Pass);

                string respuesta = await _comunicador.EnviarYRecibirAsync(comando);

                string respuestaDecodificada = Uri.UnescapeDataString(respuesta);

                Player playerLogueado = _playerDeserializer.Deserialize(respuestaDecodificada);

                return playerLogueado;
            }
            catch (Exception ex)
            {
                throw new Exception("Error al iniciar sesión.", ex);
            }
        }
    }
}