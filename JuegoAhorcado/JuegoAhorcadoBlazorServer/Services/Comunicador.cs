using System.Net.Sockets;
using System.Text;

namespace JuegoAhorcadoBlazorServer.Services
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

            Encoding utf8SinBom = new UTF8Encoding(false);

            mensaje = mensaje.Replace("\uFEFF", "").Trim();

            byte[] datosEnviar = utf8SinBom.GetBytes(mensaje + "\n");

            await stream.WriteAsync(datosEnviar, 0, datosEnviar.Length);
            await stream.FlushAsync();

            string respuesta = await LeerRespuestaAsync(stream);

            if (string.IsNullOrWhiteSpace(respuesta))
                throw new IOException("El servidor cerró la conexión sin responder.");

            return respuesta.Replace("\uFEFF", "").Trim();
        }

        private async Task<string> LeerRespuestaAsync(NetworkStream stream)
        {
            byte[] buffer = new byte[1024];
            using MemoryStream memoria = new MemoryStream();

            using CancellationTokenSource timeout = new CancellationTokenSource(
                TimeSpan.FromSeconds(10)
            );

            int bytesLeidos = await stream.ReadAsync(
                buffer,
                0,
                buffer.Length,
                timeout.Token
            );

            if (bytesLeidos == 0)
                return "";

            memoria.Write(buffer, 0, bytesLeidos);

            return Encoding.UTF8.GetString(memoria.ToArray());
        }
    }
}