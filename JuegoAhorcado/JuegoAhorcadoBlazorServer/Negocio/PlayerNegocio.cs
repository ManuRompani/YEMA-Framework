using JuegoAhorcadoBlazorServer.Modelo;
using JuegoAhorcadoBlazorServer.Services;

namespace JuegoAhorcadoBlazorServer.Negocio
{
    public class PlayerNegocio
    {
        private readonly Comunicador _comunicador;
        private readonly PlayerSerializer _serializer;
        private readonly PlayerDeserializer _deserializer;

        public PlayerNegocio(Comunicador comunicador)
        {
            _comunicador = comunicador;
            _serializer = new PlayerSerializer();
            _deserializer = new PlayerDeserializer();
        }

        public async Task<Player> Register(Player newPlayer)
        {
            try
            {
                string playerSerializado = _serializer.Serialize(newPlayer);

                string comando = "player/register/player=" + Uri.EscapeDataString(playerSerializado);

                string respuesta = await _comunicador.EnviarYRecibirAsync(comando);

                string respuestaDecodificada = Uri.UnescapeDataString(respuesta);

                Player playerRegistrado = _deserializer.Deserialize(respuestaDecodificada);

                return playerRegistrado;
            }
            catch (Exception ex)
            {
                throw new Exception("Error al registrar el jugador.", ex);
            }
        }
    }
}
