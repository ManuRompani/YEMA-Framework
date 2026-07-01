using System.Net.Sockets;
using System.Text;

namespace Cliente.JuegoAhorcado.Services
{
    public class Comunicador
    {
        private readonly string _host = "localhost";
        private readonly int _puerto = 80;

        public async Task<string> EnviarYRecibirAsync(string mensaje)
        {
            using TcpClient cliente = new TcpClient();

            await cliente.ConnectAsync(_host, _puerto);

            using NetworkStream stream = cliente.GetStream();

            using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
            using StreamWriter writer = new StreamWriter(stream, Encoding.UTF8)
            {
                AutoFlush = true
            };

            await writer.WriteLineAsync(mensaje);

            string? respuesta = await reader.ReadLineAsync();

            if (respuesta == null)
                throw new IOException("El servidor cerró la conexión sin responder.");

            return respuesta;
        }
    }
}
