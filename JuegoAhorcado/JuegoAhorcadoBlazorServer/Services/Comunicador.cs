using System.Net.Sockets;
using System.Text;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class Comunicador : IDisposable
    {
        private readonly string _host = "localhost";
        private readonly int _puerto = 80;

        private TcpClient? _cliente;
        private NetworkStream? _stream;

        private readonly Encoding _encoding = new UTF8Encoding(false);

        public async Task ConectarAsync()
        {
            if (_cliente != null && _cliente.Connected && _stream != null)
                return;

            _cliente = new TcpClient();

            await _cliente.ConnectAsync(_host, _puerto);

            _stream = _cliente.GetStream();
        }

        public async Task<string> EnviarYRecibirAsync(string mensaje)
        {
            await ConectarAsync();

            if (_stream == null)
                throw new IOException("No hay conexión activa con el servidor.");

            mensaje = mensaje.Replace("\uFEFF", "").Trim();

            byte[] datosEnviar = _encoding.GetBytes(mensaje + "\n");

            await _stream.WriteAsync(datosEnviar, 0, datosEnviar.Length);
            await _stream.FlushAsync();

            string respuesta = await LeerRespuestaAsync();

            if (string.IsNullOrWhiteSpace(respuesta))
                throw new IOException("El servidor cerró la conexión sin responder.");

            return respuesta.Replace("\uFEFF", "").Trim();
        }

        private async Task<string> LeerRespuestaAsync()
        {
            if (_stream == null)
                throw new IOException("No hay conexión activa con el servidor.");

            byte[] buffer = new byte[1024];

            int bytesLeidos = await _stream.ReadAsync(buffer, 0, buffer.Length);

            if (bytesLeidos == 0)
                return "";

            return Encoding.UTF8.GetString(buffer, 0, bytesLeidos);
        }

        public void Cerrar()
        {
            _stream?.Close();
            _cliente?.Close();

            _stream = null;
            _cliente = null;
        }

        public void Dispose()
        {
            Cerrar();
        }
    }

}