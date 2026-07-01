using JuegoAhorcadoBlazorServer.Modelo;

namespace JuegoAhorcadoBlazorServer.Services
{
    public class PlayerDeserializer
    {
        public Player Deserialize(string text)
        {
            if (string.IsNullOrWhiteSpace(text))
                throw new ArgumentException("El texto del Player está vacío.");

            string[] values = text.Split('|');

            if (values.Length != 7)
                throw new Exception("Formato inválido para Player. Se esperaban 7 valores.");

            return new Player
            {
                Id = int.Parse(values[0]),
                Name = values[1],
                Password = values[2],
                Role = new Role
                {
                    Id = int.Parse(values[3]),
                    Name = values[4]
                },
                Rounds = int.Parse(values[5]),
                Points = int.Parse(values[6])
            };
        }
    }
}
